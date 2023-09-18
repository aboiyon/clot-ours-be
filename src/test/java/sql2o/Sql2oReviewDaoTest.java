package sql2o;

import models.Tour;

public class Sql2oReviewDaoTest {
    private static Connection conn; //these variables are now static.
    private static Sql2oTourDao tourDao; //these variables are now static.

    @BeforeClass
    public static void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/jadle_test"; //connect to postgres test database
        Sql2o sql2o = new Sql2o(connectionString, "v", "1234"); //changed user and pass to null for mac users...Linux & windows need strings
        tourDao = new Sql2oTourDao(sql2o);
        reviewDao = new Sql2oReviewDao(sql2o);
        conn = sql2o.open();
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
        Review testReview = new Review("Captain Kirk", 3, "foodcoma!",testTour.getId());
        int originalReviewId = testReview.getId();
        reviewDao.add(testReview);
        assertNotEquals(originalReviewId,testReview.getId());
    }

    @Test
    public void getAll() throws Exception {
        Review review1 = setupReview();
        Review review2 = setupReview();
        assertEquals(2, reviewDao.getAll().size());
    }

    @Test
    public void getAllReviewsByTour() throws Exception {
        Tour testTour = setupTour();
        Tour otherTour = setupTour(); //add in some extra data to see if it interferes
        Review review1 = setupReviewForTour(testTour);
        Review review2 = setupReviewForTour(testTour);
        Review reviewForOtherTour = setupReviewForTour(otherTour);
        assertEquals(2, reviewDao.getAllReviewsByTour(testTour.getId()).size());
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
        Review testReview = new Review("Captain Kirk", 3,"foodcoma!", testTour.getId());
        reviewDao.add(testReview);

        long creationTime = testReview.getCreatedat();
        long savedTime = reviewDao.getAll().get(0).getCreatedat();
        String formattedCreationTime = testReview.getFormattedCreatedAt();
        String formattedSavedTime = reviewDao.getAll().get(0).getFormattedCreatedAt();
        assertEquals(formattedCreationTime,formattedSavedTime);
        assertEquals(creationTime, savedTime);
    }

    @Test
    public void reviewsAreReturnedInCorrectOrder() throws Exception {
        Tour testTour = setupTour();
        tourDao.add(testTour);
        Review testReview = new Review("Captain Kirk", 3, "foodcoma!", testTour.getId());
        reviewDao.add(testReview);
        try {
            Thread.sleep(2000);
        }
        catch (InterruptedException ex){
            ex.printStackTrace();
        }

        Review testSecondReview = new Review("Mr. Spock", 1, "passable", testTour.getId());
        reviewDao.add(testSecondReview);

        try {
            Thread.sleep(2000);
        }
        catch (InterruptedException ex){
            ex.printStackTrace();
        }

        Review testThirdReview = new Review("Scotty", 4, "bloody good grub!", testTour.getId());
        reviewDao.add(testThirdReview);

        try {
            Thread.sleep(2000);
        }
        catch (InterruptedException ex){
            ex.printStackTrace();
        }

        Review testFourthReview = new Review("Mr. Sulu", 2, "I prefer home cooking", testTour.getId());
        reviewDao.add(testFourthReview);

        assertEquals(4, reviewDao.getAllReviewsByTour(testTour.getId()).size()); //it is important we verify that the list is the same size.
        assertEquals("I prefer home cooking", reviewDao.getAllReviewsByTourSortedNewestToOldest(testTour.getId()).get(0).getContent());
    }

    //helpers

    public Review setupReview() {
        Review review = new Review("great", 4, "Kim", 555);
        reviewDao.add(review);
        return review;
    }

    public Review setupReviewForTour(Tour tour) {
        Review review = new Review("great", 4, "Kim", tour.getId());
        reviewDao.add(review);
        return review;
    }

    public Tour setupTour() {
        Tour tour = new Tour("Spice Tour", "214 NE Broadway", "http://fishwitch.com", 70);
        tourDao.add(tour);
        return tour;
    }
}
