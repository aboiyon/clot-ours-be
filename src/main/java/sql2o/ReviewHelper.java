package sql2o;

import dao.ReviewService;
import models.Review;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReviewHelper implements ReviewService {
    private final Sql2o sql2o;
    public ReviewHelper(Sql2o sql2o) { this.sql2o = sql2o; }

    private List<Review> sortReviewsNewestToOldest(List<Review> unsortedReviews) {
        List<Review> sortedReviews = new ArrayList<>(unsortedReviews);
        Collections.sort(sortedReviews);
        Collections.reverse(sortedReviews);
        return sortedReviews;
    }


    @Override
    public void add(Review review) {
        String sql = "INSERT INTO reviews (author, rating, content, kidId, manId, womanId, designerId, createdat ) VALUES (:author, :rating, :content, :kidId, :manId, :womanId, :designerId, :createdat)";
        try (org.sql2o.Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql, true)
                    .bind(review)
                    .executeUpdate()
                    .getKey();
            review.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Review> getAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM reviews")
                    .executeAndFetch(Review.class);
        }
    }

    @Override
    public List<Review> getAllReviewsByKid(int kidId) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM reviews WHERE kidId = :kidId")
                    .addParameter("kidId", kidId)
                    .executeAndFetch(Review.class);
        }
    }

    @Override
    public List<Review> getAllReviewsByMan(int manId) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM reviews WHERE manId = :manId")
                    .addParameter("manId", manId)
                    .executeAndFetch(Review.class);
        }
    }

    @Override
    public List<Review> getAllReviewsByWoman(int womanId) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM reviews WHERE womanId = :womanId")
                    .addParameter("womanId", womanId)
                    .executeAndFetch(Review.class);
        }
    }

    @Override
    public List<Review> getAllReviewsByDesigner(int designerId) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM reviews WHERE designerId = :designerId")
                    .addParameter("designerId", designerId)
                    .executeAndFetch(Review.class);
        }
    }

    @Override
    public List<Review> getAllReviewsByKidSortedNewestToOldest(int kidId) {
        List<Review> unsortedReviews = getAllReviewsByKid(kidId);
        return sortReviewsNewestToOldest(unsortedReviews);
    }

    @Override
    public List<Review> getAllReviewsByManSortedNewestToOldest(int manId) {
        List<Review> unsortedReviews = getAllReviewsByMan(manId);
        return sortReviewsNewestToOldest(unsortedReviews);
    }

    @Override
    public List<Review> getAllReviewsByWomanSortedNewestToOldest(int womanId) {
        List<Review> unsortedReviews = getAllReviewsByWoman(womanId);
        return sortReviewsNewestToOldest(unsortedReviews);
    }

    @Override
    public List<Review> getAllReviewsByDesignerSortedNewestToOldest(int designerId) {
        List<Review> unsortedReviews = getAllReviewsByDesigner(designerId);
        return sortReviewsNewestToOldest(unsortedReviews);
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from reviews WHERE id=:id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void clearAll() {
        String sql = "DELETE from reviews";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
}
