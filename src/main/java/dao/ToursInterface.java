package dao;

import models.Clothes;
import models.Tours;

import java.util.List;

public interface ToursInterface {

    //    create
    void create(Tours tours);

    //    read
    List<Tours> findAll();
    Tours findById(int id);

    //    delete
    void  deleteAll(Tours tours);
    void deleteById(int id);
}
