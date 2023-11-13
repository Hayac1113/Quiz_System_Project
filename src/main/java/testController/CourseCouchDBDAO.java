package testController;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javacouchdb.AbstractCouchDBDAO;
import javacouchdb.CouchDBaccess;
import model.Course;

public class CourseCouchDBDAO extends AbstractCouchDBDAO {
    private static final Gson gson = new Gson();

    public CourseCouchDBDAO(CouchDBaccess couchDBaccess) {
        super(couchDBaccess);
    }

    public String saveSingleCourse(Course course) {
        try {
            JsonObject jsonObject = gson.toJsonTree(course).getAsJsonObject();
            return saveDocument(jsonObject);
        } catch (Exception e) {
            throw new DAOException("Fout met opslaan in CouchDB", e);
        }
    }

    public static class DAOException extends RuntimeException {
        public DAOException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
