package dao;

import models.Order;

import java.sql.Timestamp;
import java.util.List;

public interface OrderDao {
    //    add
    void add(Order order);

    //    read
    List<Order> getAll();
    Order findById(int id);

    //    update
    void update(int id, String name, int age, Timestamp birthday);

    //    delete
    void  deleteAll();
    void deleteById(int id);
}
