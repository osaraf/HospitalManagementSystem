import application.model.HospitalInitializer;
import application.repository.Repo;
import middleware.proto.HospitalOuterClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RepoTest {
    private Repo repo;

    @BeforeEach
    void setUp() {
        repo = Repo.getInstance();
        HospitalOuterClass.Hospital mockHospital = HospitalOuterClass.Hospital.newBuilder()
                .setId(1)
                .setName("Test Hospital")
                .setHost("localhost")
                .setPort(1234)
                .setAvailableBeds(10)
                .build();

        // Initialize HospitalInitializer with the mockHospital
        HospitalInitializer.getInstanceHI().setMyHospital(mockHospital);
    }

    @AfterEach
    void tearDown() {
        // Clean up any created files or reset Repo's state
    }

    @Test
    void testCreateHospital() {
        HospitalOuterClass.Hospital hospital = HospitalOuterClass.Hospital.newBuilder().setId(1).setAvailableBeds(10).setHost("localhost").setPort(9000).setName("Test").setTotalBeds(200).build();
                assertTrue(repo.createHospital(hospital), "Hospital should be added successfully");

        // Further assertions to verify hospital is in repo
    }

    @Test
    void testUpdateAvailableBeds() {
        // First, add a hospital to the repo
        HospitalOuterClass.Hospital hospital = HospitalOuterClass.Hospital.newBuilder().setId(1).setAvailableBeds(10).setHost("localhost").setPort(9000).setName("Test").setTotalBeds(200).build();
                repo.createHospital(hospital);

        // Now, try to update its available beds
        assertTrue(repo.updateAvailableBeds(hospital.getHost(), hospital.getPort(), 20), "Available beds should be updated");
        assertEquals(repo.getRepo().get(0).getAvailableBeds(),20);

    }

    @Test
    void testGetHospitalId() {
        HospitalOuterClass.Hospital testHospital = HospitalOuterClass.Hospital.newBuilder()
                .setId(123) // Example ID
                .setHost("testHost")
                .setPort(1234)
                .build();
        repo.createHospital(testHospital);

        int retrievedId = repo.getHospitalId("testHost", 1234);
        assertEquals(123, retrievedId, "The retrieved ID should match the test hospital's ID.");
    }
    @Test
    void testSaveToJSONFileOnlyThisHospital() {
        // Set up a test hospital
        HospitalOuterClass.Hospital testHospital = HospitalOuterClass.Hospital.newBuilder().setId(1).setAvailableBeds(10).setHost("localhost").setPort(9000).setName("Test").setTotalBeds(200).build();

                // Add the test hospital to the repo and set it as the only hospital
                repo.createHospital(testHospital);
        repo.saveToJSONFileOnlyThisHospital("testHospital.json");

        // Check if the file is created
        File file = new File("testHospital.json");
        assertTrue(file.exists(), "File should be created");

        // Read the file and verify its contents
        // This might involve parsing the JSON and comparing it with the expected hospital data
        // ...

        // Clean up the created file
        file.delete();
    }
}
