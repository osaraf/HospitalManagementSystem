package application.service.bully;

import application.model.HospitalInitializer;
import application.model.IHospitalInitializer;
import io.grpc.stub.StreamObserver;
import utils.ProjectLogger;
import middleware.proto.Bully.*;

import static utils.ThreadFactory.startBullyThread;


public class BullyImplAlgo implements IBullyAlgo {

    IHospitalInitializer hospitalInitializer=HospitalInitializer.getInstanceHI();


    @Override
    public void startElection(ElectionRequest request, StreamObserver<ElectionResponse> responseObserver) {

        boolean answer = true;

        // Antwort senden
        ElectionResponse response = ElectionResponse.newBuilder().setAnswer(answer).build();
        responseObserver.onNext(response);

        // asynchrone Aktion für startBullyThread
        startBullyThread();

        // Nachdem die methode startBullyThread gestartet wurde, onCompleted aufrufen
        responseObserver.onCompleted();
    }


    @Override
    public void sendCoordinator(CoordinatorRequest request, StreamObserver<CoordinatorResponse> responseObserver) {


        int senderId = request.getSenderId();
        //set leader
        hospitalInitializer.setLeaderID(senderId);
        hospitalInitializer.setLeader(false);
        ProjectLogger.logInfo("Received COORDINATOR from Process " + senderId);

        CoordinatorResponse response = CoordinatorResponse.getDefaultInstance();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}
