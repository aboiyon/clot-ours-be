package sql2o;

import dao.ClothesInterface;
import models.Clothes;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oClotheDao implements ClothesInterface {
    private final Sql2o sql2o;
    public Sql2oClotheDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Clothes clothes) {
        String sql = "INSERT INTO clothes (name, description, imageUrl, price) VALUES (:name, :description, :imageUrl, :price)";
        try (Connection connection = sql2o.open()){
            int id = (int) connection.createQuery(sql, true)
                    .bind(clothes)
                    .executeUpdate()
                    .getKey();
            clothes.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Clothes> getAll() {
        try (Connection connection = sql2o.open()) {
            return connection.createQuery("SELECT * FROM clothes")
                    .executeAndFetch(Clothes.class);
        }
    }

    @Override
    public Clothes findById(int id) {
        try (Connection connection = sql2o.open()){
            return connection.createQuery("SELECT * FROM clothes WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Clothes.class);
        }
    }

    @Override
    public void update(int id, String newName, String newDescription, String newImageUrl, double newPrice) {
        String sql = "UPDATE clothes  SET (name, description, imageUrl, price) = (:name, :description, :imageUrl, :price) WHERE id = :id";
        try (Connection connection = sql2o.open()){
            connection.createQuery(sql)
                    .addParameter("id", id)
                    .addParameter("name", newName)
                    .addParameter("name", newDescription)
                    .addParameter("name", newImageUrl)
                    .addParameter("name", newPrice)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from clothes WHERE id = :id";
        try (Connection connection = sql2o.open()){
            connection.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteAll(Clothes clothes) {
        String sql = "DELETE from  clothes";
        try (Connection connection = sql2o.open()){
            connection.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
}
