package fr.ensitech.optistage.repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoConnector {

    // Les coordonnées de votre conteneur Docker
    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    // Le nom de la base de données (MongoDB la créera toute seule à la volée si elle n'existe pas)
    private static final String DATABASE_NAME = "optistage_nosql";

    private static MongoClient mongoClient = null;

    // Constructeur privé pour empêcher l'instanciation (Design Pattern Singleton)
    private MongoConnector() {}

    public static MongoDatabase getDatabase() {
        if (mongoClient == null) {
            // Initialisation de la connexion au premier appel
            mongoClient = MongoClients.create(CONNECTION_STRING);
            System.out.println("⚡ Connexion au réacteur MongoDB établie avec succès.");
        }
        return mongoClient.getDatabase(DATABASE_NAME);
    }
}