package fr.ensitech.optistage.repository;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;
import static com.mongodb.client.model.Filters.eq;

public class CvRepository implements ICvRepository {

    private MongoCollection<Document> getCollection() {
        return MongoConnector.getDatabase().getCollection("cv_documents");
    }

    @Override
    public String saveCv(String fileName, String base64Content) {
        MongoCollection<Document> collection = getCollection();

        Document cvDocument = new Document("fileName", fileName)
                .append("content", base64Content)
                .append("uploadDate", new java.util.Date());

        // L'insertion génère automatiquement un champ "_id" de type ObjectId
        collection.insertOne(cvDocument);

        // On extrait cet identifiant magique et on le renvoie en format Texte
        return cvDocument.getObjectId("_id").toHexString();
    }

    @Override
    public Document getCv(String cvId) {
        // MongoDB a besoin de transformer notre texte "64f1a2..." en vrai ObjectId pour chercher
        return getCollection().find(eq("_id", new ObjectId(cvId))).first();
    }
}