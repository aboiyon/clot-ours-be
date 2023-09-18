package dao;

import models.Tour;

import java.util.List;

public interface ToursDao {

    //    create
    void add(Tour tour);

    //    read
    List<Tour> getAll();
    Tour findById(int id);

    //update
    void update(int id, String name, String description, String imageUrl, double price);

    //    delete
    void deleteById(int id);
    void clearAll();
}
