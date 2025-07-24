package application.setup;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppConfig {
    public static final String CONFIG_FILEPATH = "/app/config.json";
    public static final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(30);
    public static final String HOSPITALS_INFOS = "/app/hospitalInfos.json";
    public static final int CLIENT_RETRY_UPDATE_CHECKER_MS = 5000;
    public static final String AGGREGATION_SERVER_HOST="webserver";
    public static final int AGGREGATION_SERVER_PORT=5000;
    public static String MY_FIRST_ACQUAINTANCE_HOST;
    public static int MY_FIRST_ACQUAINTANCE_PORT;
}
