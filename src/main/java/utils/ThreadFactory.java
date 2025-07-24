package utils;

import middleware.client.bully.BullyClient;
import middleware.client.hospital.HospitalClient;

import java.util.concurrent.ExecutorService;

import static application.setup.AppConfig.*;


public class ThreadFactory implements IThreadFactory {

    private static final ExecutorService threadPool = THREAD_POOL; //  threadPool is accessible


    public static void startClientAndAvailableBedsThread(String host, int port) {
        threadPool.execute(() -> {
            try {
                HospitalClient hospitalClient = new HospitalClient(host, port);
                    hospitalClient.startClient();
                    hospitalClient.getAvailableBeds();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void startBullyThread() {

        threadPool.execute(() -> {
            try {
                BullyClient bullyClient = new BullyClient();
                bullyClient.election();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


}
