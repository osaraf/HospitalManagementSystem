package application.repository;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import middleware.proto.HospitalOuterClass;

import java.lang.reflect.Type;

public class HospitalSerializer implements JsonSerializer<HospitalOuterClass.Hospital> {

    private static final String FIELD_TEST = "test";
    private static final String FIELD_HOST = "host";
    private static final String FIELD_PORT = "port";
    private static final String FIELD_ID = "id";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_TOTAL_BEDS = "totalBeds";
    private static final String FIELD_AVAILABLE_BEDS = "availableBeds";

    @Override
    public JsonElement serialize(HospitalOuterClass.Hospital hospital, Type type, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(FIELD_TEST, hospital.getTest());
        jsonObject.addProperty(FIELD_HOST, hospital.getHost().toString());
        jsonObject.addProperty(FIELD_PORT, hospital.getPort());
        jsonObject.addProperty(FIELD_ID, hospital.getId());
        jsonObject.addProperty(FIELD_NAME, hospital.getName());
        jsonObject.addProperty(FIELD_TOTAL_BEDS, hospital.getTotalBeds());
        jsonObject.addProperty(FIELD_AVAILABLE_BEDS, hospital.getAvailableBeds());
        return jsonObject;
    }
}
