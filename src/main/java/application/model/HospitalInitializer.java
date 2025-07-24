package application.model;

import application.repository.IRepo;
import com.google.gson.stream.JsonReader;
import utils.ProjectLogger;
import middleware.proto.HospitalOuterClass;
import application.repository.Repo;

import java.io.FileReader;
import java.io.IOException;

import static application.setup.AppConfig.CONFIG_FILEPATH;


public class HospitalInitializer implements IHospitalInitializer {

    private static HospitalInitializer instance;

    private static HospitalOuterClass.Hospital myHospital;

    //false as default wert
    private static boolean leader = false;
    public static int leaderID;
    private static IRepo repo = Repo.getInstance();
    private String configFilePath;

    private HospitalInitializer(IRepo repo) {
        this.configFilePath = CONFIG_FILEPATH;
        this.repo = repo;
    }

    public static synchronized HospitalInitializer getInstanceHI() {
        if (instance == null) {
            instance = new HospitalInitializer(repo);
        }
        return instance;
    }

    @Override
    public HospitalOuterClass.Hospital getMyHospital() {
        return myHospital;
    }

    @Override

    public synchronized void setLeader(boolean isLeader) {
        if (leader != isLeader) {
            ProjectLogger.logWarning("Leadership got changed from " + leader + " to " + isLeader);
        }
        HospitalInitializer.leader = isLeader;
    }

    @Override
    public synchronized boolean getLeader() {

        return leader;
    }

    @Override

    public void initialize() {
        try (FileReader reader = new FileReader(configFilePath)) {
            JsonReader jsonReader = new JsonReader(reader);
            jsonReader.beginArray(); // The JSON file contains an array of hospitals
            if (jsonReader.hasNext()) {
                jsonReader.beginObject(); // The first object in the array (hospital)

                HospitalOuterClass.Hospital.Builder hospitalBuilder = HospitalOuterClass.Hospital.newBuilder();

                while (jsonReader.hasNext()) {
                    String key = jsonReader.nextName();
                    switch (key) {
                        case "test":
                            hospitalBuilder.setTest(jsonReader.nextInt());
                            break;
                        case "id":
                            hospitalBuilder.setId(jsonReader.nextInt());
                            break;
                        case "host":
                            hospitalBuilder.setHost(jsonReader.nextString());
                            break;
                        case "port":
                            hospitalBuilder.setPort(jsonReader.nextInt());
                            break;
                        case "name":
                            hospitalBuilder.setName(jsonReader.nextString());
                            break;
                        case "totalBeds":
                            hospitalBuilder.setTotalBeds(jsonReader.nextInt());
                            break;
                        case "availableBeds":
                            hospitalBuilder.setAvailableBeds(jsonReader.nextInt());
                            break;
                        default:
                            jsonReader.skipValue(); // Unknown field, skip it
                            break;
                    }
                }

                jsonReader.endObject();
                jsonReader.endArray();

                myHospital = hospitalBuilder.build();
                repo.createHospital(myHospital);

                // Print the hospital data after initialization
                ProjectLogger.logInfo("Initialized Hospital:");
                ProjectLogger.logInfo("Name: " + myHospital.getName());
                ProjectLogger.logInfo("ID: " + myHospital.getId());
                ProjectLogger.logInfo("Host: " + myHospital.getHost());
                ProjectLogger.logInfo("Port: " + myHospital.getPort());
                ProjectLogger.logInfo("Total Beds: " + myHospital.getTotalBeds());
                ProjectLogger.logInfo("Available Beds: " + myHospital.getAvailableBeds());


            } else {
                ProjectLogger.logWarning("The JSON file contains no hospital or is empty.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateAvailableBeds(int newAvailableBeds) {
        if (myHospital != null) {
            HospitalOuterClass.Hospital.Builder hospitalBuilder = myHospital.toBuilder();
            hospitalBuilder.setAvailableBeds(newAvailableBeds);
            myHospital = hospitalBuilder.build();

            repo.saveToJSONFileOnlyThisHospital(CONFIG_FILEPATH);
            // Print the updated hospital data
            ProjectLogger.logInfo("Updated Hospital:");
            ProjectLogger.logInfo("Name: " + myHospital.getName());
            ProjectLogger.logInfo("ID: " + myHospital.getId());
            ProjectLogger.logInfo("Host: " + myHospital.getHost());
            ProjectLogger.logInfo("Port: " + myHospital.getPort());
            ProjectLogger.logInfo("Total Beds: " + myHospital.getTotalBeds());
            ProjectLogger.logInfo("Available Beds: " + myHospital.getAvailableBeds());
        } else {
            ProjectLogger.logWarning("Hospital not initialized.");
        }
    }

    @Override
    public void setLeaderID(int senderId) {
        leaderID = senderId;
    }

    public void setMyHospital(HospitalOuterClass.Hospital hospital){
        myHospital = hospital;
    }
}
