// HospitalApplication.java
package application.setup;

import application.controller.IController;
import application.model.HospitalInitializer;
import application.model.IHospitalInitializer;
import application.view.IUserKonsole;
import application.view.UserConsole;
import middleware.server.HospitalServer;
import utils.ThreadFactory;
import application.controller.Controller;
import utils.ProjectLogger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class HospitalApplication {

    private final ExecutorService threadPool;

    IHospitalInitializer hospitalInitializer = HospitalInitializer.getInstanceHI();

    IController controller = new Controller();

    public HospitalApplication(ExecutorService threadPool) {
        this.threadPool = threadPool;
    }

    public void initializeAndStart(boolean isFirstNode, String[] args) {
        initialize();
        if (isFirstNode) {

            hospitalInitializer.setLeader(true);
            hospitalInitializer.setLeaderID(hospitalInitializer.getMyHospital().getId());
            controller.SendToAggregationServer();
        }

        startServer();

        startUserConsoleInBackground();


        // Start initial client
        if (!isFirstNode) {
            if (args.length >= 3) {
                AppConfig.MY_FIRST_ACQUAINTANCE_HOST = args[1];
                AppConfig.MY_FIRST_ACQUAINTANCE_PORT = Integer.parseInt(args[2]);
                startInitialClient(AppConfig.MY_FIRST_ACQUAINTANCE_HOST, AppConfig.MY_FIRST_ACQUAINTANCE_PORT);
            } else {

              ProjectLogger.logError("Arguments Error. Exiting...");
                System.exit(1);
            }
        }

    }
    private void startUserConsoleInBackground() {
        Thread userConsoleThread = new Thread(() -> {
            IUserKonsole userConsole = new UserConsole();
            userConsole.open();
        });
        userConsoleThread.start();
    }

    private void initialize() {
        IHospitalInitializer hospitalInitializer = HospitalInitializer.getInstanceHI();
        hospitalInitializer.initialize();
    }

    private void startServer() {
        threadPool.execute(() -> {
            int serverPort = hospitalInitializer.getMyHospital().getPort();
            HospitalServer hospitalServer = new HospitalServer(serverPort);
            hospitalServer.startServer();
          ProjectLogger.logInfo("Server started on port: " + serverPort);
        });
    }

    private void startInitialClient(String host, int port) {
        ThreadFactory.startClientAndAvailableBedsThread(host,port);

    }

    public void shutdownThreadPool() {
        ProjectLogger.logInfo("Shutting down the application...");

        // Close the thread pool before exiting the program
        threadPool.shutdown();

        // Wait for the proper termination of the thread pool
        try {
            if (!threadPool.awaitTermination(5, TimeUnit.SECONDS)) {
                threadPool.shutdownNow();
            }
        } catch (Exception e) {
          ProjectLogger.logError("Thread pool did not terminate properly. Forcing shutdown...");
            threadPool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
