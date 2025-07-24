package application.service.hospital;

import io.grpc.stub.StreamObserver;
import middleware.proto.HospitalOuterClass;

public interface IHospitalServices {

    public void getAvailableBeds(HospitalOuterClass.Empty request, StreamObserver<HospitalOuterClass.AvailableBedsReply> responseObserver);

    public void getHospitalsForRegion(HospitalOuterClass.Hospital hospitalRequest, StreamObserver<HospitalOuterClass.HospitalsForRegionReply> responseObserver);

}