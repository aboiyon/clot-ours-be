package dao;

import models.Review;

import java.util.List;

public interface ReviewDao {
    //create
    void add(Review review);

    //read
    List<Review> getAll();
    List<Review> getAllReviewsByKid(int kidId);
    List<Review> getAllReviewsByKidSortedNewestToOldest(int kidId);

    //update
    //omit for now

    //delete
    void deleteById(int id);
    void clearAll();
}
