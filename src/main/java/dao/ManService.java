package dao;

import models.Man;

import java.math.BigDecimal;
import java.util.List;

public interface ManService {
    //    add
    void add(Man man);

    //    read
    List<Man> getAll();
    Man findById(int id);

    //    update
    void update(int id, String name, String description, String imageUrl, BigDecimal price, int quantity,String color);

    //    delete
    void  deleteAll();
    void deleteById(int id);
}
