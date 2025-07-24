package middleware.client.bully;
import application.repository.IRepo;
import application.repository.Repo;
import application.model.HospitalInitializer;
import application.model.IHospitalInitializer;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import utils.ProjectLogger;
import middleware.proto.Bully;
import middleware.proto.BullyServiceGrpc;
import middleware.proto.HospitalOuterClass;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class BullyClient implements BullyClientInterface {

    static IRepo repo =  Repo.getInstance();

    IHospitalInitializer hospitalInitializer = HospitalInitializer.getInstanceHI();


    public boolean isMyHospital(HospitalOuterClass.Hospital hospital) {
        return (hospital.getHost().equals(hospitalInitializer.getMyHospital().getHost()) &&
                hospital.getPort() == hospitalInitializer.getMyHospital().getPort() && hospital.getName().equals(hospitalInitializer.getMyHospital().getName()));
    }

    public void election() {
        boolean atLeastOneTrueResponse = false;

        for (HospitalOuterClass.Hospital hospital : repo.getRepo()) {
            if (!isMyHospital(hospital)) {
                if (hospital.getId() > hospitalInitializer.getMyHospital().getId()) {

                    CompletableFuture<Bully.ElectionResponse> electionFuture = startElectionAsync(hospital.getHost(), hospital.getPort());

                    try {
                        // Warte maximal 2 Sekunden auf die Antwort
                        Bully.ElectionResponse electionResponse = electionFuture.get(2, TimeUnit.SECONDS);

                        ProjectLogger.logInfo("Received Election Response from " + hospital.getHost() + ":" + hospital.getPort() + ": " + electionResponse.getAnswer());

                        // Wenn mindestens ein Server mit "true" antwortet, setzen Sie die Variable auf true
                        if (electionResponse.getAnswer()) {
                            atLeastOneTrueResponse = true;
                            break;
                        }
                    } catch (TimeoutException e) {
                        ProjectLogger.logWarning("Timeout waiting for Election Response from " + hospital.getHost() + ":" + hospital.getPort());

                    } catch (Exception e) {
                        ProjectLogger.logError("Error in Election Response from " + hospital.getHost() + ":" + hospital.getPort() + ": " + e.getMessage());
                    }
                }
            }
        }

        if (atLeastOneTrueResponse) {
            hospitalInitializer.setLeader(false);

        } else {
            int thisHospitalID=hospitalInitializer.getMyHospital().getId();
            for (HospitalOuterClass.Hospital hospital : repo.getRepo()) {
                if (!isMyHospital(hospital)) {
                    if (hospital.getId() < thisHospitalID) {

                        CompletableFuture<Bully.CoordinatorResponse> coordinatorFuture = sendCoordinator(hospital.getHost(), hospital.getPort(), hospitalInitializer.getMyHospital().getId());
                        try {
                            Bully.CoordinatorResponse coordinatorResponse = coordinatorFuture.get();
                            ProjectLogger.logInfo("Received Coordinator Response: ok " + coordinatorResponse);
                        } catch (Exception e) {
                            ProjectLogger.logError("Error in Coordinator Response: " + e.getMessage());
                        }
                    }
                }
            }
            hospitalInitializer.setLeader(true);
            hospitalInitializer.setLeaderID(hospitalInitializer.getMyHospital().getId());

        }
    }

    //    @Override
    public CompletableFuture<Bully.ElectionResponse> startElectionAsync(String host, int port) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
        BullyServiceGrpc.BullyServiceStub asyncStub = BullyServiceGrpc.newStub(channel);

        CompletableFuture<Bully.ElectionResponse> future = new CompletableFuture<>();
        Bully.ElectionRequest request = Bully.ElectionRequest.newBuilder().build();

        asyncStub.startElection(request, new StreamObserver<Bully.ElectionResponse>() {
            @Override
            public void onNext(Bully.ElectionResponse response) {
                future.complete(response);
            }

            @Override
            public void onError(Throwable t) {
                future.completeExceptionally(t);
            }

            @Override
            public void onCompleted() {

                channel.shutdown();
            }
        });
        return future;
    }

    //    @Override
    public CompletableFuture<Bully.CoordinatorResponse> sendCoordinator(String host, int port, int senderId) {

        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
        BullyServiceGrpc.BullyServiceStub asyncStub = BullyServiceGrpc.newStub(channel);

        CompletableFuture<Bully.CoordinatorResponse> future = new CompletableFuture<>();
        Bully.CoordinatorRequest request = Bully.CoordinatorRequest.newBuilder().setSenderId(senderId).build();

        asyncStub.sendCoordinator(request, new StreamObserver<Bully.CoordinatorResponse>() {

            @Override
            public void onNext(Bully.CoordinatorResponse coordinatorResponse) {
                future.complete(coordinatorResponse);
            }

            @Override
            public void onError(Throwable t) {
                future.completeExceptionally(t);
            }

            @Override
            public void onCompleted() {

                channel.shutdown();
            }
        });
        return future;
    }



}
