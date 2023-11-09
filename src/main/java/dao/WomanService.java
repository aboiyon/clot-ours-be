package dao;

import models.Woman;

import java.util.List;

public interface WomanService {
    //    add
    void add(Woman woman);

    //    read
    List<Woman> getAll();
    Woman findById(int id);

    //    update
    void update(int id, String name, String description, String imageUrl, double price);

    //    delete
    void  deleteAll();
    void deleteById(int id);
}
