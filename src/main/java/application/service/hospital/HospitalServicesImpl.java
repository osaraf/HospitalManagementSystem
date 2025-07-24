package application.service.hospital;
import application.repository.Repo;
import application.model.HospitalInitializer;
import utils.ThreadFactory;
import io.grpc.stub.StreamObserver;
import middleware.proto.HospitalOuterClass;
import application.repository.IRepo;
import application.model.IHospitalInitializer;


public class HospitalServicesImpl implements IHospitalServices {
static IRepo repo = Repo.getInstance();
IHospitalInitializer hospitalInitializer = HospitalInitializer.getInstanceHI();

    // Logic to calculate and respond with the number of available beds
    @Override
    public void getAvailableBeds(HospitalOuterClass.Empty request, StreamObserver<HospitalOuterClass.AvailableBedsReply> responseObserver) {
        int availableBedsCount = hospitalInitializer.getMyHospital().getAvailableBeds();
        HospitalOuterClass.AvailableBedsReply reply = HospitalOuterClass.AvailableBedsReply.newBuilder()
                .setMessage(availableBedsCount)
                .build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }


    // Logic to retrieve hospitals for the specified region and send them as a response
    @Override
    public void getHospitalsForRegion(HospitalOuterClass.Hospital hospitalRequest, StreamObserver<HospitalOuterClass.HospitalsForRegionReply> responseObserver) {
        HospitalOuterClass.Hospital recivedHospital = HospitalOuterClass.Hospital.newBuilder().
                setId(hospitalRequest.getId()).setHost(hospitalRequest.getHost()).setPort(hospitalRequest.getPort()).setName(hospitalRequest.getName()).setTotalBeds(hospitalRequest.getTotalBeds()).setAvailableBeds(hospitalRequest.getAvailableBeds()).setTest(hospitalRequest.getTest())
                .build();

        // Handle the received hospital using HospitalManager
        handleHospital(recivedHospital);

        HospitalOuterClass.HospitalsForRegionReply reply = HospitalOuterClass.HospitalsForRegionReply.newBuilder()
                .addAllHospital(repo.getRepo())
                .build();

        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }


    public static void handleHospital(HospitalOuterClass.Hospital hospital) {
        if (repo.createHospital(hospital)) {
            ThreadFactory.startClientAndAvailableBedsThread(hospital.getHost(), hospital.getPort());
        }
    }


}








