package sql2o;

import dao.KidService;
import models.Kid;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class KidHelper implements KidService {
    private final Sql2o sql2o;
    public KidHelper(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Kid kid) {
        String sql = "INSERT INTO kids (name, description, imageUrl, price) VALUES (:name, :description, :imageUrl, :price)";
        try (Connection connection = sql2o.open()){
            int id = (int) connection.createQuery(sql, true)
                    .bind(kid)
                    .executeUpdate()
                    .getKey();
            kid.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Kid> getAll() {
        try (Connection connection = sql2o.open()) {
            return connection.createQuery("SELECT * FROM kids")
                    .executeAndFetch(Kid.class);
        }
    }

    @Override
    public Kid findById(int id) {
        try (Connection connection = sql2o.open()){
            return connection.createQuery("SELECT * FROM kids WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Kid.class);
        }
    }

    @Override
    public void update(int id, String name, String description, String imageUrl, double price) {
        String sql = "UPDATE kids  SET (name, description, imageUrl, price) = (:name, :description, :imageUrl, :price) WHERE id = :id";
        try (Connection connection = sql2o.open()){
            connection.createQuery(sql)
                    .addParameter("id", id)
                    .addParameter("name", name)
                    .addParameter("name", description)
                    .addParameter("name", imageUrl)
                    .addParameter("name", price)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }

    }

    @Override
    public void deleteAll() {
        String sql = "DELETE from  kids";
        try (Connection connection = sql2o.open()){
            connection.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }

    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from kids WHERE id = :id";
        try (Connection connection = sql2o.open()){
            connection.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }

    }
}
