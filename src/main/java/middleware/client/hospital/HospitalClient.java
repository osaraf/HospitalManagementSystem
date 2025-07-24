package middleware.client.hospital;

import application.controller.IController;
import application.repository.IRepo;
import application.model.HospitalInitializer;
import application.model.IHospitalInitializer;
import io.grpc.stub.StreamObserver;
import utils.ProjectLogger;
import application.controller.Controller;
import utils.ThreadFactory;
import middleware.client.bully.BullyClient;
import middleware.proto.HospitalOuterClass;
import middleware.proto.HospitalServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import application.repository.Repo;

import java.util.Objects;
import static application.setup.AppConfig.*;

public class HospitalClient implements IHospitalClient {

    IRepo repo = Repo.getInstance();
    IHospitalInitializer hospitalInitializer = HospitalInitializer.getInstanceHI();

    IController controller = new Controller();


    String targetHost;
    int targetPort;
    BullyClient bullyClient;


    public HospitalClient(String targetHost, int targetPort) {
        this.targetHost = targetHost;
        this.targetPort = targetPort;
        bullyClient = new BullyClient();
    }



    /**
     * Steuert die Verarbeitung von Krankenhausinformationen.
     * Prüft, ob das Krankenhaus nicht mit dem eigenen identisch ist,
     * erstellt es in der Datenbank und sendet bei Bedarf eine Nachricht
     * an den Aggregationsserver, wenn das eigene Krankenhaus der Leader ist.
     * Startet einen Client-Thread, wenn das betrachtete Krankenhaus
     * nicht das ursprüngliche Bekanntschaftskrankenhaus ist und ein
     * Krankenhaus mit dem gleichen Namen bereits in der Datenbank existiert.
     * Führt anschließend eine Leader-Wahl durch.
     *
     * @param hospital Das zu verarbeitende Krankenhaus.
     */
    private boolean handleHospital(HospitalOuterClass.Hospital hospital) {


        if (Objects.equals(hospital.getName(), hospitalInitializer.getMyHospital().getName())) {
            return false;
        }
        if (!repo.createHospital(hospital)) {
            return false;
        }

        if (hospitalInitializer.getLeader()) {
            ProjectLogger.logInfo(hospital.getName() + "get in Repo   " + hospitalInitializer.getMyHospital().getName() + "   " + hospitalInitializer.getLeader());
            controller.SendToAggregationServer();
        }

        if (!(Objects.equals(hospital.getHost(), MY_FIRST_ACQUAINTANCE_HOST) && hospital.getPort() == MY_FIRST_ACQUAINTANCE_PORT)) {
            if ((repo.getRepo().stream().anyMatch(e -> Objects.equals(e.getName(), hospital.getName())))) {
                ThreadFactory.startClientAndAvailableBedsThread(hospital.getHost(), hospital.getPort());
            }
        }


        return true; // das heißt die methode ist fertig mit adding den neuen Knoten auch mit der Erstellung einen zugehörigen Thread erfolgreich

    }

    /**
     * Verarbeitet die empfangenen Krankenhausdaten für eine Region.
     *
     * @param reply Die Antwort mit den Krankenhausdaten für eine Region.
     */

    private void processHospitals(HospitalOuterClass.HospitalsForRegionReply reply) {
        boolean minOneNewHospital = false;
        for (HospitalOuterClass.Hospital hospital : reply.getHospitalList()) {
            if (handleHospital(hospital)) {
                if (minOneNewHospital == false) {
                    minOneNewHospital = true;
                }
            }
        }
        // start neu election
        if (minOneNewHospital) {
            ThreadFactory.startBullyThread();
        }

    }

    /**
     * Startet einen Client und fordert Krankenhausdaten für eine Region an.
     */

    public void startClient() {

        ManagedChannel channel = ManagedChannelBuilder.forAddress(targetHost, targetPort).usePlaintext().build();
        HospitalServiceGrpc.HospitalServiceStub asyncClientStub = HospitalServiceGrpc.newStub(channel);


        try {
            HospitalOuterClass.Hospital krankenhausRequest = hospitalInitializer.getMyHospital();
            asyncClientStub.getHospitalsForRegion(krankenhausRequest, new StreamObserver<HospitalOuterClass.HospitalsForRegionReply>() {
                @Override
                public void onNext(HospitalOuterClass.HospitalsForRegionReply reply) {
                    processHospitals(reply);
                }

                @Override
                public void onError(Throwable t) {
                    ProjectLogger.logError("Error in gRPC client: " + t.getMessage());
                    channel.shutdown();
                }

                @Override
                public void onCompleted() {
                    channel.shutdown();
                }
            });
        } catch (Exception e) {
            ProjectLogger.logInfo("Client " + hospitalInitializer.getMyHospital().getName() +
                    " failed to connect to the server:" + targetHost + " with port: " + targetPort + " - " + e.getMessage());
        }
    }


    /**
     * Aktualisiert die verfügbaren Betten durch einen Ping zum Server.
     * Führt Leader-Wahl durch, wenn die Verbindung fehlschlägt.
     */

    public void getAvailableBeds() {

        final boolean[] errorOccurred = {false};

        while (!errorOccurred[0]) {
            ManagedChannel channel = ManagedChannelBuilder.forAddress(targetHost, targetPort).usePlaintext().build();
            HospitalServiceGrpc.HospitalServiceStub asyncClientStub = HospitalServiceGrpc.newStub(channel);

            try {
                // Erstelle ein StreamObserver für die asynchrone Verarbeitung der Antwort
                StreamObserver<HospitalOuterClass.AvailableBedsReply> responseObserver = new StreamObserver<HospitalOuterClass.AvailableBedsReply>() {
                    @Override
                    public void onNext(HospitalOuterClass.AvailableBedsReply reply) {
                        if (repo.updateAvailableBeds(targetHost, targetPort, reply.getMessage())) {
                            if (hospitalInitializer.getLeader()) {
                                controller.SendToAggregationServer();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        ProjectLogger.logError("Failed to connect to the server:" + targetHost + "  " + targetPort);

                        int id = repo.getHospitalId(targetHost, targetPort);
                        if (id == HospitalInitializer.leaderID) {

                            bullyClient.election();
                            errorOccurred[0] = true;
                        }
                        channel.shutdown();
                    }

                    @Override
                    public void onCompleted() {
                        channel.shutdown();
                    }
                };

                // Starte den asynchronen gRPC-Aufruf
                asyncClientStub.getAvailableBeds(HospitalOuterClass.Empty.newBuilder().build(), responseObserver);

                // Warte, um dem asynchronen Prozess Zeit zu geben
                Thread.sleep(CLIENT_RETRY_UPDATE_CHECKER_MS);
            } catch (InterruptedException e) {
                ProjectLogger.logError("Client thread interrupted: " + e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }


}
