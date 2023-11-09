package dao;

import models.Kid;

import java.util.List;

public interface KidService {
    //    add
    void add(Kid kid);

    //    read
    List<Kid> getAll();
    Kid findById(int id);

    //    update
    void update(int id, String name, String description, String imageUrl, double price);

    //    delete
    void  deleteAll();
    void deleteById(int id);
}
