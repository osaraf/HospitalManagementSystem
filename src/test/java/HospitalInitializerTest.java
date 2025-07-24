import application.model.HospitalInitializer;
import application.repository.IRepo;
import application.repository.Repo;
import com.google.gson.stream.JsonReader;
import middleware.proto.HospitalOuterClass;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import static org.junit.jupiter.api.Assertions.*;

public class HospitalInitializerTest {
    private HospitalInitializer hospitalInitializer;
    private IRepo mockRepo;

    @Before
    public void setUp() {
        mockRepo = Repo.getInstance(); // Use the actual implementation of Repo
        hospitalInitializer = HospitalInitializer.getInstanceHI();
    }

//    @Test
//    public void testInitialize() throws IOException {
//
//        // Override the JsonReader creation in HospitalInitializer with our custom one
//        HospitalInitializer instance = hospitalInitializer.getInstanceHI();
//
//        // Call the initialize method
//        hospitalInitializer.initialize();
//
//        // Verify that the hospital was created and logged
//        assertTrue(instance.getMyHospital() != null); // Check that the hospital was created
//        assertEquals("Veddel", instance.getMyHospital().getName());
//        assertEquals(20, instance.getMyHospital().getId());
//        assertEquals("localhost", instance.getMyHospital().getHost());
//        assertEquals(8999, instance.getMyHospital().getPort());
//        assertEquals(78, instance.getMyHospital().getTotalBeds());
//        assertEquals(32, instance.getMyHospital().getAvailableBeds());
//    }

    @Test
    public void testUpdateAvailableBeds() {
        HospitalOuterClass.Hospital hospital = HospitalOuterClass.Hospital.newBuilder().setAvailableBeds(20).build();
        hospitalInitializer.setMyHospital(hospital);
        mockRepo.createHospital(hospital);
        // Call the updateAvailableBeds method to update available beds
        hospitalInitializer.updateAvailableBeds(60);

        // Assert the expected results
        assertEquals(60, hospitalInitializer.getMyHospital().getAvailableBeds());

        // Add more assertions as needed to test other properties and behavior
    }
}

