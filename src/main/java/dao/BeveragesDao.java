package dao;

import models.Beverages;

import java.util.List;

public interface BeveragesDao {
//    create
    void add(Beverages beverages);

//    read
    List<Beverages> getAll();
    Beverages findById(int id);

//    delete
    void deleteById(int id);
    void clearAll();
}
