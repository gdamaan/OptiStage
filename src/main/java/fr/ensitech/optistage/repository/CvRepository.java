package fr.ensitech.optistage.repository;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import static com.mongodb.client.model.Filters.eq;

public class CvRepository implements ICvRepository {

    private MongoCollection<Document> getCollection() {
        return MongoConnector.getDatabase().getCollection("cv_documents");
    }

    @Override
    public void saveCv(int studentId, String fileName, String base64Content) {
        MongoCollection<Document> collection = getCollection();
        collection.deleteOne(eq("studentId", studentId));

        Document cvDocument = new Document("studentId", studentId)
                .append("fileName", fileName)
                .append("content", base64Content)
                .append("uploadDate", new java.util.Date());

        collection.insertOne(cvDocument);
    }

    @Override
    public Document getCv(int studentId) {
        return getCollection().find(eq("studentId", studentId)).first();
    }
}