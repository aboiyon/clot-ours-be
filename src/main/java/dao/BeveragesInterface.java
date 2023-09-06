package dao;

import models.Beverages;

import java.util.List;

public interface BeveragesInterface {
//    create
    void create(Beverages beverages);

//    read
    List<Beverages> findAll();
    Beverages findById(int id);

//    delete
    void  deleteAll(Beverages beverages);
    void deleteById(int id);
}
