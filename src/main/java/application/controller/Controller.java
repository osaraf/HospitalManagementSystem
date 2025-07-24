package application.controller;

import application.model.HospitalInitializer;
import application.model.IHospitalInitializer;
import utils.ProjectLogger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;

import static application.setup.AppConfig.*;

public class Controller implements IController {
    private static final ExecutorService threadPool = THREAD_POOL; //  threadPool is accessible
    IHospitalInitializer hospitalInitializer = HospitalInitializer.getInstanceHI();




    public void sendJsonFile(String targetHost, int targetPort, String jsonFilePath) throws IOException {
        try {
            String targetUrl = "http://" + targetHost + ":" + targetPort + "/updateAllHospitals";
            URL url = new URL(targetUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");

            // Enable output for the connection
            connection.setDoOutput(true);

            // Set the Content-Type header to application/json
            connection.setRequestProperty("Content-Type", "application/json; utf-8");

            // Check if the file exists and is readable
            File jsonFile = new File(jsonFilePath);
            if (!jsonFile.exists() || !jsonFile.canRead()) {
               ProjectLogger.logError("The JSON file does not exist or cannot be read.");
                return;
            }

            // Read the content of the JSON file line by line
            StringBuilder jsonData = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new FileReader(jsonFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    jsonData.append(line);
                }
            }

            // Convert the JSON data to a byte array
            byte[] data = jsonData.toString().getBytes("UTF-8");

            // Get the OutputStream from the connection and write the data
            try (OutputStream os = connection.getOutputStream()) {
                os.write(data, 0, data.length);
            }

            int responseCode = connection.getResponseCode();
            ProjectLogger.logInfo("Server Response Code: " + responseCode);

            // Close the connection
            connection.disconnect();
        } catch (Exception e) {
            throw e;
        }
    }
    public void SendToAggregationServer() {


            if (hospitalInitializer.getLeader()) {
                try {
                    sendJsonFile(AGGREGATION_SERVER_HOST, AGGREGATION_SERVER_PORT, HOSPITALS_INFOS);
                    ProjectLogger.logInfo("Sending Data to aggregation server");

                } catch (IOException e) {
                   ProjectLogger.logError("Aggregation server is currently not availble on port:"+AGGREGATION_SERVER_PORT+".... Data got changed locally");
                }
            }

    }



}
