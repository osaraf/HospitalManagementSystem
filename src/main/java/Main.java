import application.setup.HospitalApplication;
import application.setup.AppConfig;

public class Main {
    public static void main(String[] args) {
        // Initialize the application
        boolean isFirstHospitalInNetwork = Boolean.parseBoolean(args[0]);

        HospitalApplication hospitalApplication = new HospitalApplication(AppConfig.THREAD_POOL);
        hospitalApplication.initializeAndStart(isFirstHospitalInNetwork, args);

        Runtime.getRuntime().addShutdownHook(new Thread(hospitalApplication::shutdownThreadPool));
    }
}
