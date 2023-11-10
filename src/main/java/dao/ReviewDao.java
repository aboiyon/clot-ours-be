package dao;

import models.Review;

import java.util.List;

public interface ReviewDao {
    //create
    void add(Review review);

    //read
    List<Review> getAll();
    List<Review> getAllReviewsByKid(int kidId);
    List<Review> getAllReviewsByMan(int manId);
    List<Review> getAllReviewsByWoman(int womanId);
    List<Review> getAllReviewsByKidSortedNewestToOldest(int kidId);
    List<Review> getAllReviewsByManSortedNewestToOldest(int manId);
    List<Review> getAllReviewsByWomanSortedNewestToOldest(int womanId);

    //update
    //omit for now

    //delete
    void deleteById(int id);
    void clearAll();
}
