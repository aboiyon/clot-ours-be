package sql2o;

import dao.ManService;
import models.Man;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class ManHelper implements ManService {
    private final Sql2o sql2o;
    public ManHelper(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Man man) {
        String sql = "INSERT INTO men (name, description, imageUrl, price) VALUES (:name, :description, :imageUrl, :price)";
        try (Connection connection = sql2o.open()){
            int id = (int) connection.createQuery(sql, true)
                    .bind(man)
                    .executeUpdate()
                    .getKey();
            man.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Man> getAll() {
        try (Connection connection = sql2o.open()) {
            return connection.createQuery("SELECT * FROM men")
                    .executeAndFetch(Man.class);
        }
    }

    @Override
    public Man findById(int id) {
        try (Connection connection = sql2o.open()){
            return connection.createQuery("SELECT * FROM men WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Man.class);
        }
    }

    @Override
    public void update(int id, String name, String description, String imageUrl, double price) {
        String sql = "UPDATE men SET (name, description, imageUrl, price) = (:name, :description, :imageUrl, :price) WHERE id = :id";
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
        String sql = "DELETE from  men";
        try (Connection connection = sql2o.open()){
            connection.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }

    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from men WHERE id = :id";
        try (Connection connection = sql2o.open()){
            connection.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }

    }
}
