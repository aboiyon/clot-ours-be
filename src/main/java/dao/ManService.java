package dao;

import models.Man;

import java.util.List;

public interface ManService {
    //    add
    void add(Man man);

    //    read
    List<Man> getAll();
    Man findById(int id);

    //    update
    void update(int id, String name, String description, String imageUrl, double price);

    //    delete
    void  deleteAll();
    void deleteById(int id);
}
