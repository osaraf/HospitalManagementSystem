import application.model.HospitalInitializer;
import application.model.IHospitalInitializer;
import application.service.bully.BullyBase;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import middleware.proto.Bully;
import middleware.proto.BullyServiceGrpc;
import middleware.proto.HospitalOuterClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BullyBaseTest {
    private Server server1;
    private Server server2;
    private ManagedChannel channel1;
    private ManagedChannel channel2;

    @BeforeEach
    void setUp() throws Exception {
        // Start server1
        server1 = ServerBuilder.forPort(2500) // Use different ports
                .addService(new BullyBase())
                .build()
                .start();

        // Start server2
        server2 = ServerBuilder.forPort(3500) // Use different ports
                .addService(new BullyBase())
                .build()
                .start();

        // Create channels for clients
        channel1 = ManagedChannelBuilder.forAddress("localhost", 2500)
                .usePlaintext()
                .build();
        channel2 = ManagedChannelBuilder.forAddress("localhost", 3500)
                .usePlaintext()
                .build();

        HospitalOuterClass.Hospital hospital = HospitalOuterClass.Hospital.newBuilder().setId(1).build();
        IHospitalInitializer hospitalInitializer = HospitalInitializer.getInstanceHI();
        hospitalInitializer.setMyHospital(hospital);
    }

    @Test
    void testElection() throws Exception {
        // Create stubs (clients) for each server
        BullyServiceGrpc.BullyServiceBlockingStub client1 = BullyServiceGrpc.newBlockingStub(channel1);
        BullyServiceGrpc.BullyServiceBlockingStub client2 = BullyServiceGrpc.newBlockingStub(channel2);

        Bully.ElectionRequest request = Bully.ElectionRequest.newBuilder().build();
        Bully.ElectionResponse response1 = client1.startElection(request);
        Bully.ElectionResponse response2 = client2.startElection(request);

        // Assertions
        assertNotNull(response1, "Response from server 1 should not be null");
        assertNotNull(response2, "Response from server 2 should not be null");

    }

    @Test
    void testSendCoordinator() {
        // Create a stub (client) for the server
        BullyServiceGrpc.BullyServiceBlockingStub client = BullyServiceGrpc.newBlockingStub(channel1);

        // Prepare the request
        int testSenderId = 123; // Example sender ID
        Bully.CoordinatorRequest request = Bully.CoordinatorRequest.newBuilder().setSenderId(testSenderId).build();

        // Send the request and receive the response
        Bully.CoordinatorResponse response = client.sendCoordinator(request);

        // Assertions
        assertNotNull(response, "The response should not be null");

        // Optionally, verify the change in HospitalInitializer
        assertEquals(testSenderId, HospitalInitializer.leaderID, "The leader ID should be updated");
    }


    @AfterEach
    void tearDown() throws Exception {
        if (channel1 != null) {
            channel1.shutdown();
        }
        if (channel2 != null) {
            channel2.shutdown();
        }
        if (server1 != null) {
            server1.shutdown();
        }
        if (server2 != null) {
            server2.shutdown();
        }
    }
}
