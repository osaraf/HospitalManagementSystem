package application.repository;

import middleware.proto.HospitalOuterClass;

import java.util.ArrayList;

public interface IRepo {

    boolean createHospital(HospitalOuterClass.Hospital newHospital);

    boolean updateAvailableBeds(String host, int port, int availableBeds);

    int getHospitalId(String host, int port);

    HospitalOuterClass.Hospital get(int i);
    void saveToJSONFileOnlyThisHospital(String configFilepath);

    ArrayList<HospitalOuterClass.Hospital> getRepo() ;


}
