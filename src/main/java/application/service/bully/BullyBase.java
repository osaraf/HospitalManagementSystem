package application.service.bully;

import io.grpc.stub.StreamObserver;
import middleware.proto.Bully;
import middleware.proto.BullyServiceGrpc;

public class BullyBase extends BullyServiceGrpc.BullyServiceImplBase {

IBullyAlgo bullyImpl = new BullyImplAlgo();


@Override
public void startElection(Bully.ElectionRequest request, StreamObserver<Bully.ElectionResponse> responseObserver){
    bullyImpl.startElection(request, responseObserver);
}
@Override
public void sendCoordinator(Bully.CoordinatorRequest request, StreamObserver<Bully.CoordinatorResponse> responseObserver) {
    bullyImpl.sendCoordinator(request, responseObserver);
}

}
