package dao;

import models.Beverages;
import models.Clothes;

import java.util.List;

public interface ClothesInterface {

    //    create
    void create(Clothes clothes);

    //    read
    List<Clothes> findAll();
    Clothes findById(int id);

    //    delete
    void  deleteAll(Clothes clothes);
    void deleteById(int id);
}
