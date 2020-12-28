package org.lab2;
import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {

        System.out.println( "Hello World!" );
        // Creating a Mongo client
        MongoClient mongo = MongoClients.create("mongodb://localhost:27017");

        // Creating Credentials
        MongoCredential credential;
        credential = MongoCredential.createCredential("sampleUser", "Test",
                "password".toCharArray());
        System.out.println("Connected to the database successfully");

        // Accessing the database
        MongoDatabase database = mongo.getDatabase("Test");
        System.out.println("Credentials ::"+ credential);

        //Creating a collection
        database.createCollection("BigMike");
        System.out.println("Collection created successfully");
    }
}
