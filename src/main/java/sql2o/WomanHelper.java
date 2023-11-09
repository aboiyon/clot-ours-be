package sql2o;

import dao.WomanService;
import models.Woman;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class WomanHelper implements WomanService {
    private final Sql2o sql2o;
    public WomanHelper(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Woman woman) {
        String sql = "INSERT INTO women (name, description, imageUrl, price) VALUES (:name, :description, :imageUrl, :price)";
        try (Connection connection = sql2o.open()){
            int id = (int) connection.createQuery(sql, true)
                    .bind(woman)
                    .executeUpdate()
                    .getKey();
            woman.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Woman> getAll() {
        try (Connection connection = sql2o.open()) {
            return connection.createQuery("SELECT * FROM women")
                    .executeAndFetch(Woman.class);
        }
    }

    @Override
    public Woman findById(int id) {
        try (Connection connection = sql2o.open()){
            return connection.createQuery("SELECT * FROM women WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Woman.class);
        }
    }

    @Override
    public void update(int id, String name, String description, String imageUrl, double price) {

        String sql = "UPDATE women  SET (name, description, imageUrl, price) = (:name, :description, :imageUrl, :price) WHERE id = :id";
        try (Connection connection = sql2o.open()){
            connection.createQuery(sql)
                    .addParameter("id", id)
                    .addParameter("name", name)
                    .addParameter("description", description)
                    .addParameter("imageUrl", imageUrl)
                    .addParameter("price", price)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }

    }

    @Override
    public void deleteAll() {

        String sql = "DELETE from  women";
        try (Connection connection = sql2o.open()){
            connection.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }

    }

    @Override
    public void deleteById(int id) {

        String sql = "DELETE from women WHERE id = :id";
        try (Connection connection = sql2o.open()){
            connection.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }

    }
}
