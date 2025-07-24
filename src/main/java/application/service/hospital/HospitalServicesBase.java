package application.service.hospital;

import io.grpc.stub.StreamObserver;
import middleware.proto.HospitalOuterClass;
import middleware.proto.HospitalServiceGrpc;

public class HospitalServicesBase extends HospitalServiceGrpc.HospitalServiceImplBase {
    IHospitalServices services = new HospitalServicesImpl();

    @Override
    public void getAvailableBeds(HospitalOuterClass.Empty request, StreamObserver<HospitalOuterClass.AvailableBedsReply> responseObserver){
        services.getAvailableBeds(request, responseObserver);
    }

    @Override
    public void getHospitalsForRegion(HospitalOuterClass.Hospital hospitalRequest, StreamObserver<HospitalOuterClass.HospitalsForRegionReply> responseObserver){
        services.getHospitalsForRegion(hospitalRequest, responseObserver);
    }

}
