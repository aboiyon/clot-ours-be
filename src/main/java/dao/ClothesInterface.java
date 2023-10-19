package dao;

import models.Clothes;

import java.util.List;

public interface ClothesInterface {

    //    add
    void add(Clothes clothes);

    //    read
    List<Clothes> getAll();
    Clothes findById(int id);

//    update
    void update(int id, String name, String description, String imageUrl, double price);

    //    delete
    void  deleteAll(Clothes clothes);
    void deleteById(int id);
}
