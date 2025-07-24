package application.repository;

import application.controller.Controller;
import application.controller.IController;
import application.model.HospitalInitializer;
import application.model.IHospitalInitializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import utils.ProjectLogger;
import middleware.proto.HospitalOuterClass;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static application.setup.AppConfig.HOSPITALS_INFOS;


public class Repo implements IRepo {



    private static Repo instance;
    private final ArrayList<HospitalOuterClass.Hospital> repo;

    IHospitalInitializer hospitalInitializer = HospitalInitializer.getInstanceHI();

    IController controller = new Controller();

    @Override
    public ArrayList<HospitalOuterClass.Hospital> getRepo() {
        return repo;
    }

    private Repo() {
        this.repo = new ArrayList<>();
    }
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(HospitalOuterClass.Hospital.class, new HospitalSerializer())
            .setPrettyPrinting()
            .create();

    public static Repo getInstance() {
        if (instance == null) {
            instance = new Repo();
        }
        return instance;
    }

    @Override
    public HospitalOuterClass.Hospital get(int i) {
        if(!repo.isEmpty()){
            return repo.get(i);
        } else{
            throw new IllegalArgumentException("No active hospital found in repo");
        }
    }



    @Override
    public synchronized boolean createHospital(HospitalOuterClass.Hospital newHospital) {

        boolean isFirstHospital = true;

        for (HospitalOuterClass.Hospital hospital : repo) {
            if (hospital.getName().equals(newHospital.getName())) {

                ProjectLogger.logInfo("Hospital: " + newHospital.getName() + " already exists.");
                return false;
            }

            // Not the first hospital
            isFirstHospital = false;
        }

        if (!isFirstHospital) {
            ProjectLogger.logInfo("Creating Hospital: " + newHospital.getName() + "...");
        }

        HospitalOuterClass.Hospital.Builder updatedHospitalBuilder = newHospital.toBuilder();
        updatedHospitalBuilder.setHost(newHospital.getHost());
        HospitalOuterClass.Hospital updatedHospital = updatedHospitalBuilder.build();
        repo.add(updatedHospital);
        saveToJSONFile(HOSPITALS_INFOS);

        if (!isFirstHospital) {
            ProjectLogger.logInfo("Creating Hospital: " + newHospital.getName() + " is completed ");
        }

        return true;

    }

    @Override
    public synchronized boolean updateAvailableBeds(String host, int port, int availableBeds) {
        for (HospitalOuterClass.Hospital hospital : repo) {

            if (hospital.getHost().equals(host) && hospital.getPort() == port) {

                if (isAvailableBedsUpdateNeeded(hospital, availableBeds)) {
                    HospitalOuterClass.Hospital.Builder updatedHospitalBuilder = hospital.toBuilder();
                    updatedHospitalBuilder.setAvailableBeds(availableBeds);
                    HospitalOuterClass.Hospital updatedHospital = updatedHospitalBuilder.build();
                    repo.set(repo.indexOf(hospital), updatedHospital);

                    if (isSameHostAndPortAsCurrentHospital(host, port)) {
                        hospitalInitializer.updateAvailableBeds(availableBeds);
                        saveToJSONFile(HOSPITALS_INFOS);
                                                // sage server Bescheid, wenn ich der Leader bin
                        if (hospitalInitializer.getLeader()) {

                            controller.SendToAggregationServer();
                        }

                    } else {
                        saveToJSONFile(HOSPITALS_INFOS);
                    }
                    ProjectLogger.logInfo("Available beds of hospital: " + hospital.getName() + " host:" + host + "   port:" + port + " are updated");
                    return true;
                } else {
                    ProjectLogger.logInfo("No update on available beds in hospital: " + hospital.getName() + " host:" + host + "   port:" + port);
                    return false;
                }
            }
        }
        ProjectLogger.logInfo("Hospital " + host + "  " + port + " " + "existiert nicht in der Liste");
        return false;

    }

    public boolean isSameHostAndPortAsCurrentHospital(String host, int port) {
        return host.equals(hospitalInitializer.getMyHospital().getHost()) && port == hospitalInitializer.getMyHospital().getPort();
    }

    private static boolean isAvailableBedsUpdateNeeded(HospitalOuterClass.Hospital hospital, int availableBeds) {
        return hospital.getAvailableBeds() != availableBeds && availableBeds <= hospital.getTotalBeds();
    }


    public static void saveHospitalsToJSONFile(String filePath, List<HospitalOuterClass.Hospital> hospitals) {
        String json = gson.toJson(hospitals);
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveToJSONFile(String filePath) {
        saveHospitalsToJSONFile(filePath, getInstance().repo);
    }
    @Override
    public void saveToJSONFileOnlyThisHospital(String filePath) {
        ArrayList<HospitalOuterClass.Hospital> template = new ArrayList<>();
        template.add(getInstance().get(0));
        saveHospitalsToJSONFile(filePath, template);
    }


    @Override
    public synchronized int getHospitalId(String host, int port) {
        for (HospitalOuterClass.Hospital hospital : repo) {
            if (hospital.getHost().equals(host) && hospital.getPort() == port) {
                return hospital.getId();
            }
        }
        return -1; // Rückgabe eines ungültigen Werts, wenn das Krankenhaus nicht gefunden wurde
    }


}
