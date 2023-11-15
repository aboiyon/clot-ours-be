package sql2o;

import dao.DesignerService;
import models.Designer;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.math.BigDecimal;
import java.util.List;

public class DesignerHelper implements DesignerService {
    private final Sql2o sql2o;
    public DesignerHelper(Sql2o sql2o) {
        this.sql2o = sql2o;
    }
    @Override
    public void add(Designer designer) {
        String sql = "INSERT INTO designers (name, description, imageUrl01, imageUrl02, price) VALUES (:name, :description, :imageUrl01, :imageUrl02, :price)";
        try (Connection connection = sql2o.open()){
            int id = (int) connection.createQuery(sql, true)
                    .bind(designer)
                    .executeUpdate()
                    .getKey();
            designer.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Designer> getAll() {
        try (Connection connection = sql2o.open()) {
            return connection.createQuery("SELECT * FROM designers")
                    .executeAndFetch(Designer.class);
        }
    }

    @Override
    public Designer findById(int id) {
        try (Connection connection = sql2o.open()) {
            return connection.createQuery("SELECT * FROM designers WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Designer.class);
        }
    }

    @Override
    public void update(int id, String name, String description, String imageUrl01, String imageUrl02, BigDecimal price) {
        String sql = "UPDATE designers SET (name, description, imageUrl01, imageUrl02, price) = (:name, :description, :imageUrl01, :imageUrl02, :price) WHERE id = :id";
        try (Connection connection = sql2o.open()){
            connection.createQuery(sql)
                    .addParameter("id", id)
                    .addParameter("name", name)
                    .addParameter("description", description)
                    .addParameter("imageUrl01", imageUrl01)
                    .addParameter("imageUrl02", imageUrl02)
                    .addParameter("price", price)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }

    }

    @Override
    public void deleteAll() {
        String sql = "DELETE from  designers";
        try (Connection connection = sql2o.open()){
            connection.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from designers WHERE id = :id";
        try (Connection connection = sql2o.open()){
            connection.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
}
