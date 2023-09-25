package sql2o;

import models.Review;
import models.Tour;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class Sql2oReviewDaoTest {
    private static Connection conn; //these variables are now static.
    private static Sql2oTourDao tourDao; //these variables are now static.
    private static Sql2oReviewDao reviewDao; //these variables are now static.

    @BeforeClass
    public static void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/utalii_test"; //connect to postgres test database
        Sql2o sql2o = new Sql2o(connectionString, "cheruiyot", "new_password"); //changed user and pass to null for mac users...Linux & windows need strings
        tourDao = new Sql2oTourDao(sql2o);
        reviewDao = new Sql2oReviewDao(sql2o);
        conn = (Connection) sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("clearing database");
        tourDao.clearAll(); //clear all tours after every test
        reviewDao.clearAll();
    }

    @AfterClass
    public static void shutDown() throws Exception{ //changed to static
        conn.close();
        System.out.println("connection closed");
    }

    @Test
    public void addingReviewSetsId() throws Exception {
        Tour testTour = setupTour();
        tourDao.add(testTour);
        Review testReview = new Review("Captain Kirk", "Frank", 5, testTour.getId(), 1, 23/05/2023, "");
        int originalReviewId = testReview.getId();
        reviewDao.add(testReview);
        assertNotEquals(originalReviewId,testReview.getId());
    }

    @Test
    public void getAll() throws Exception {
        Review review1 = setupReview();
        Review review2 = setupReview();
        assertEquals(0, reviewDao.getAll().size());
    }

    @Test
    public void getAllReviewsByTour() throws Exception {
        Tour testTour = setupTour();
        Tour otherTour = setupTour(); //add in some extra data to see if it interferes
        Review review1 = setupReviewForTour(testTour);
        Review review2 = setupReviewForTour(testTour);
        Review reviewForOtherTour = setupReviewForTour(otherTour);
        assertEquals(0, reviewDao.getAllReviewsByTour(testTour.getId()).size());
    }

    @Test
    public void deleteById() throws Exception {
        Review testReview = setupReview();
        Review otherReview = setupReview();
        assertEquals(2, reviewDao.getAll().size());
        reviewDao.deleteById(testReview.getId());
        assertEquals(1, reviewDao.getAll().size());
    }

    @Test
    public void clearAll() throws Exception {
        Review testReview = setupReview();
        Review otherReview = setupReview();
        reviewDao.clearAll();
        assertEquals(0, reviewDao.getAll().size());
    }

    @Test
    public void timeStampIsReturnedCorrectly() throws Exception {
        Tour testTour = setupTour();
        tourDao.add(testTour);
        Review testReview = new Review("Captain Kirk", "Frank", 5, 0, 1, 23/05/2023, "");
        reviewDao.add(testReview);

        long creationTime = testReview.getCreatedAt();
        long savedTime = reviewDao.getAll().get(0).getCreatedAt();
        String formattedCreationTime = testReview.getFormattedCreatedAt();
        String formattedSavedTime = reviewDao.getAll().get(0).getFormattedCreatedAt();
        assertEquals(formattedCreationTime,formattedSavedTime);
        assertEquals(creationTime, savedTime);
    }

    @Test
    public void reviewsAreReturnedInCorrectOrder() throws Exception {
        Tour testTour = setupTour();
        tourDao.add(testTour);
        Review testReview = new Review("Captain Kirk", "Frank", 5, '0', 1, 23/05/2023, "");
        reviewDao.add(testReview);
        try {
            Thread.sleep(2000);
        }
        catch (InterruptedException ex){
            ex.printStackTrace();
        }

        Review testSecondReview = new Review("Captain Kirk", "Frank", 5, '0', 1, 23/05/2023, "");
        reviewDao.add(testSecondReview);

        try {
            Thread.sleep(2000);
        }
        catch (InterruptedException ex){
            ex.printStackTrace();
        }

        Review testThirdReview = new Review("Captain Kirk", "Frank", 5, '0', 1, 23/05/2023, "");
        reviewDao.add(testThirdReview);

        try {
            Thread.sleep(2000);
        }
        catch (InterruptedException ex){
            ex.printStackTrace();
        }

        Review testFourthReview = new Review("Captain Kirk", "Frank", 5, '0', 1, 23/05/2023, "");
        reviewDao.add(testFourthReview);

        assertEquals(4, reviewDao.getAllReviewsByTour(testTour.getId()).size()); //it is important we verify that the list is the same size.
        assertEquals(4, reviewDao.getAllReviewsByTourSortedNewestToOldest(testTour.getId()).get(0).getContent());
    }

    //helpers

    public Review setupReview() {
        Review review = new Review("great", "Anet", 5, '0', 4, 22/05/2023, "");
        reviewDao.add(review);
        return review;
    }

    public Review setupReviewForTour(Tour tour) {
        Review review = new Review("great", "Joan", 5, '0', 2, 22/05/2023, "");
        reviewDao.add(review);
        return review;
    }

    public Tour setupTour() {
        Tour tour = new Tour("Spice Tour", "Spicy", "XXXXXXX", "http:image/url", 70);
        tourDao.add(tour);
        return tour;
    }
}
