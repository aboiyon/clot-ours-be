package sql2o;

import models.Tour;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class Sql2oTourDaoTest {
    private static Connection conn;
    private static Sql2oTourDao tourDao;
    private static Sql2oReviewDao reviewDao;
    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/utalii_test";  //connect to postgres test database
        Sql2o sql2o = new Sql2o(connectionString, "cheruiyot", "new_password"); //changed user and pass to null
        tourDao = new Sql2oTourDao(sql2o);
        reviewDao = new Sql2oReviewDao(sql2o);
        conn = sql2o.open();        //open connection once before this test file is run
        conn.getJdbcConnection().setAutoCommit(false);
    }

    @After
    public void tearDown() throws Exception {
        if (conn != null) {
            conn.close();
        }
        System.out.println("clearing database");
        tourDao.clearAll(); //clear all tours after every test
        reviewDao.clearAll(); //clear all tours after every test
    }

    @AfterClass     //changed to @AfterClass (run once after all tests in this file completed)
    public static void shutDown() throws Exception{ //changed to static
        conn.close(); // close connection once after this entire test file is finished
        System.out.println("connection closed");
    }

    @Test
    public void addingTourSetsId() throws Exception {
        Tour testTour = setupTour();
        int originalTourId = testTour.getId();
        tourDao.add(testTour);
        assertNotEquals(originalTourId, testTour.getId());
    }

    @Test
    public void addedToursAreReturnedFromGetAll() throws Exception {
        Tour testTour = setupTour();
        assertEquals(1, tourDao.getAll().size());
    }

    @Test
    public void tourNotReturnEmptyList() throws Exception {
        assertEquals(0, tourDao.getAll().size());
    }

    @Test
    public void findByIdReturnsCorrectTour() throws Exception {
        // Create a tour and store it in the database
        Tour testTour = setupTour();

        // Retrieve the same tour from the database using its ID
        Tour retrievedTour = tourDao.findById(testTour.getId());

        // Compare the retrieved tour with the original tour
        assertEquals(testTour, retrievedTour);
    }


    @Test
    public void updateCorrectlyUpdatesAllFields() throws Exception {
        Tour testTour = setupTour();
        tourDao.update(testTour.getId(), "a", "b", "c", 'd');
        Tour foundTour = tourDao.findById(testTour.getId());
        assertEquals("a", foundTour.getName());
        assertEquals("b", foundTour.getDescription());
        assertEquals("c", foundTour.getImageUrl());
        assertEquals('d', foundTour.getPrice());
    }

    @Test
    public void deleteByIdDeletesCorrectTour() throws Exception {
        Tour tesTour = setupTour();
        tourDao.deleteById(tesTour.getId());
        assertEquals(0, tourDao.getAll().size());
    }

    @Test
    public void clearAll() throws Exception {
        Tour testTour = setupTour();
        Tour otherTour = setupTour();
        tourDao.clearAll();
        assertEquals(0, tourDao.getAll().size());
    }

//    helpers
    public Tour setupTour (){
        Tour tour = new Tour("","","", "", imageId, 0);
        tourDao .add(tour);
        return tour;
    }
}
