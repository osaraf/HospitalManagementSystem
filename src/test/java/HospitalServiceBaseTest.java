import application.model.HospitalInitializer;
import application.model.IHospitalInitializer;
import application.service.hospital.HospitalServicesBase;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import middleware.proto.HospitalOuterClass;
import middleware.proto.HospitalServiceGrpc;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HospitalServiceBaseTest {
    public Server server;
    public ManagedChannel channel;

    @BeforeEach
    void  setUp() throws Exception{
        String serverName = InProcessServerBuilder.generateName();
        server = ServerBuilder.forPort(8080)
                .addService(new HospitalServicesBase())
                .directExecutor()
                .build()
                .start();
        channel = ManagedChannelBuilder.forAddress("localhost", 8080)
                .usePlaintext()
                .build();
        IHospitalInitializer hospitalInitializer = HospitalInitializer.getInstanceHI();
        HospitalOuterClass.Hospital hospital = HospitalOuterClass.Hospital.newBuilder().setId(2).setAvailableBeds(10).build();
        hospitalInitializer.setMyHospital(hospital);
    }

    @AfterEach
    void tearDown() throws Exception {
        if (channel != null) {
            channel.shutdown();
        }
        if (server != null) {
            server.shutdown();
        }
    }

    @Test
    void testGetAvailableBeds() {

        // Create a stub (client) for making requests
        HospitalServiceGrpc.HospitalServiceBlockingStub stub = HospitalServiceGrpc.newBlockingStub(channel);

        // Prepare the request
        HospitalOuterClass.Empty request = HospitalOuterClass.Empty.newBuilder().build();

        // Send the request and receive the response
        HospitalOuterClass.AvailableBedsReply reply = stub.getAvailableBeds(request);

        // Perform your assertions here
        assertNotNull(reply, "The reply should not be null");
        // Add more assertions based on expected values
        assertEquals(10,reply.getMessage());

    }

}
