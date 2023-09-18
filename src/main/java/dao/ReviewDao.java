package dao;

public interface ReviewDao {
    //create
    void add(Review review);

    //read
    List<Review> getAll();
    List<Review> getAllReviewsByTour(int restaurantId);
    List<Review> getAllReviewsByTourSortedNewestToOldest(int restaurantId);

    //update
    //omit for now

    //delete
    void deleteById(int id);
    void clearAll();
}
