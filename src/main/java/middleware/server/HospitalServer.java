package middleware.server;

import application.model.HospitalInitializer;
import application.service.bully.BullyBase;
import application.service.hospital.HospitalServicesBase;
import application.model.IHospitalInitializer;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import utils.ProjectLogger;

import java.io.IOException;

public class HospitalServer implements IHospitalServer {

    IHospitalInitializer hospitalInitializer = HospitalInitializer.getInstanceHI();

    private final int port;

    public HospitalServer(int port) {
        this.port = port;
    }


    @Override
    public void startServer() {
        Server server = ServerBuilder.forPort(port).addService(new HospitalServicesBase()).addService(new BullyBase()).build();
        try {
            server.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
       ProjectLogger.logInfo("hms Server:" + hospitalInitializer.getMyHospital().getName() + " started at Port " + server.getPort());
       ProjectLogger.logInfo("***************************************************");

        try {
            server.awaitTermination();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
