package sql2o;

import dao.ToursDao;
import models.Tour;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oTourDao implements ToursDao {
    private final Sql2o sql2o;
    public Sql2oTourDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Tour tour) {
        String sql = "INSERT INTO tours (name, description, imageUrl, imageId, price ) VALUES (:name, :description, :imageUrl, :imageId, :price)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql, true)
                    .bind(tour)
                    .executeUpdate()
                    .getKey();
            tour.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Tour> getAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM tours")
                    .executeAndFetch(Tour.class);
        }
    }

    @Override
    public Tour findById(int id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM tours WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Tour.class);
        }
    }

    @Override
    public void update(int id, String newName, String newDescription, String newImageUrl, int newImageId, double newPrice) {
        String sql = "UPDATE tours SET (name, description, imageUrl, imageId, price) = (:name, :description, :imageUrl, :imageId, :price ) WHERE id=:id"; //CHECK!!!
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("name", newName)
                    .addParameter("description", newDescription)
                    .addParameter("imageUrl", newImageUrl)
                    .addParameter("imageId", newImageId)
                    .addParameter("price", newPrice)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM tours WHERE id = :id"; // Corrected SQL query
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }


    @Override
    public void clearAll() {
        String sql = "DELETE from tours";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

//    @Override
//    public void add(Tour tour) {
//
//    }
//
//    @Override
//    public List<Tour> getAll() {
//        return null;
//    }
//
//    @Override
//    public Tour findById(int id) {
//        return null;
//    }
//
//    @Override
//    public void deleteAll(Tour tour) {
//
//    }
//
//    @Override
//    public void deleteById(int id) {
//
//    }
//
//    @Overide
//    public void clearAll() {
////        return null;
//    }
}
