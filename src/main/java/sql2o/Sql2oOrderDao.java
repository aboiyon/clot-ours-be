package sql2o;

import dao.OrderDao;
import models.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.sql.Timestamp;
import java.util.List;

public class Sql2oOrderDao implements OrderDao {
    private static final Logger log = LoggerFactory.getLogger(Sql2oOrderDao.class);
    private final Sql2o sql2o;
    public Sql2oOrderDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }
    @Override
    public void add(Order order) {
        String sql = "INSERT INTO orders (name, age, birthday) VALUES (:name, :age, now())";
        try (Connection connection = sql2o.open()){
            int id = (int) connection.createQuery(sql, true)
                    .bind(order)
                    .executeUpdate()
                    .getKey();
            order.setId(id);
        } catch (Sql2oException ex) {
            log.error("e: ", ex);
        }
    }

    @Override
    public List<Order> getAll() {
        try (Connection connection = sql2o.open()) {
            return connection.createQuery("SELECT * FROM orders")
                    .executeAndFetch(Order.class);
        }
    }

    @Override
    public Order findById(int id) {
        try (Connection connection = sql2o.open()){
            return connection.createQuery("SELECT * FROM orders WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Order.class);
        }
    }

    @Override
    public void update(int id, String name, int age, Timestamp birthday) {
        String sql = "UPDATE orders SET (name, age, birthday) = (:name, :age, now()) WHERE id = :id";
        try (Connection connection = sql2o.open()){
            connection.createQuery(sql)
                    .addParameter("id", id)
                    .addParameter("name", name)
                    .addParameter("age", age)
                    .addParameter("birthday", birthday)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            log.error("e: ", ex);
        }
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE from  orders";
        try (Connection connection = sql2o.open()){
            connection.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            log.error("e: ", ex);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from orders WHERE id = :id";
        try (Connection connection = sql2o.open()){
            connection.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            log.error("e: ", ex);
        }
    }
}
