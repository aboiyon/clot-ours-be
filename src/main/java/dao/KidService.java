package dao;

import models.Kid;

import java.math.BigDecimal;
import java.util.List;

public interface KidService {
    //    add
    void add(Kid kid);

    //    read
    List<Kid> getAll();
    Kid findById(int id);

    //    update
    void update(int id, String name, String description, String imageUrl, BigDecimal price, int quantity, String color);

    //    delete
    void  deleteAll();
    void deleteById(int id);
}
