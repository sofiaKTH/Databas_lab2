package model;

import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

class DatabaseTest {
    public static void main(String args[]){
        // Creating a Mongo client
        MongoClient mongo = MongoClients.create("mongodb://host1:27017");

        // Creating Credentials
        MongoCredential credential;
        credential = MongoCredential.createCredential("sampleUser", "Test",
                "password".toCharArray());
        System.out.println("Connected to the database successfully");

        // Accessing the database
        MongoDatabase database = mongo.getDatabase("Test");
        System.out.println("Credentials ::"+ credential);

        //Creating a collection
        database.createCollection("sampleCollection");
        System.out.println("Collection created successfully");
    }
}

