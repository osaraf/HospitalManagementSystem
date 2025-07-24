package application.controller;

import java.io.IOException;

public interface IController {

    void sendJsonFile(String targetHost, int targetPort, String jsonFilePath) throws IOException;

    void SendToAggregationServer();

}
