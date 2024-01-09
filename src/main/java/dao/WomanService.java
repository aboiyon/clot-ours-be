package dao;

import models.Woman;

import java.math.BigDecimal;
import java.util.List;

public interface WomanService {
    //    add
    void add(Woman woman);

    //    read
    List<Woman> getAll();
    Woman findById(int id);

    //    update
    void update(int id, String name, String description, String imageUrl, BigDecimal price, int quantity, String color);

    //    delete
    void  deleteAll();
    void deleteById(int id);
}
