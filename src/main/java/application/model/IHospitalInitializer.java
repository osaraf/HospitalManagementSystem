package application.model;

import middleware.proto.HospitalOuterClass;

public interface IHospitalInitializer {
    HospitalOuterClass.Hospital getMyHospital();
    void setLeader(boolean isLeader);
    boolean getLeader();
    void initialize();
    void updateAvailableBeds(int newAvailableBeds);

    void setLeaderID(int senderId);
    //HospitalInitializer getInstance();

    void setMyHospital(HospitalOuterClass.Hospital hospital);
    }
