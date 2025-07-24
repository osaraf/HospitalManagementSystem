package application.service.bully;

import io.grpc.stub.StreamObserver;
import middleware.proto.Bully;

public interface IBullyAlgo {
    public void startElection(Bully.ElectionRequest request, StreamObserver<Bully.ElectionResponse> responseObserver);
    public void sendCoordinator(Bully.CoordinatorRequest request, StreamObserver<Bully.CoordinatorResponse> responseObserver);


    }
