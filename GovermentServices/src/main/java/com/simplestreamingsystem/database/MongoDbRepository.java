package com.simplestreamingsystem.database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import kotlin.Pair;
import org.bson.Document;
import static com.mongodb.client.model.Filters.eq;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class MongoDbRepository {
    private final ConnectionString connectionString = new ConnectionString("mongodb+srv://admin:admin@government.vmttxnu.mongodb.net/?retryWrites=true&w=majority");
    private final MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(connectionString).build();
    private final MongoClient mongoClient = (MongoClient) MongoClients.create(settings);
    private final MongoDatabase database = mongoClient.getDatabase("violation");
    private final MongoCollection<Document> vehicleCollection = database.getCollection("vehicle");
    private final MongoCollection<Document> penaltyCollection = database.getCollection("penalty");

    public Pair<Integer, String> getPenaltyByLicencePlate(String licencePlate) {
        List<Document> vehicles = vehicleCollection.find(eq("licence_plate", licencePlate)).into(new ArrayList<Document>());
        int totalPenalty = 0;
        List<String> listViolates = new ArrayList<>();
        if (vehicles.isEmpty()) {
            return new Pair<>(0, "Khong vi pham");
        }
        for (Document vehicle : vehicles) {
            Document penalty = penaltyCollection.find(eq("_id", new ObjectId(vehicle.get("penalty").toString()))).first();
            totalPenalty += Integer.parseInt(penalty.get("money").toString());
            listViolates.add(penalty.get("violate").toString());
        }
        return new Pair<>(totalPenalty, String.join(" + ", listViolates));
    }

}
