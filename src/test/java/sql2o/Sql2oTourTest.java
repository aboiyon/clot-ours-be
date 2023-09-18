package sql2o;

import models.Tour;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Sql2o;

import java.sql.Connection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class Sql2oTourTest {
    private static Connection conn;
    private static Sql2oTourDao tourDao;
    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/utalii_test";  //connect to postgres test database
        Sql2o sql2o = new Sql2o(connectionString, "cheruiyot", "1234"); //changed user and pass to null
        tourDao = new Sql2oTourDao(sql2o);
//        foodtypeDao = new Sql2oFoodtypeDao(sql2o);
//        reviewDao = new Sql2oReviewDao(sql2o);
        conn = (Connection) sql2o.open();        //open connection once before this test file is run
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("clearing database");
        tourDao.clearAll(); //clear all restaurants after every test
    }

    @AfterClass     //changed to @AfterClass (run once after all tests in this file completed)
    public static void shutDown() throws Exception{ //changed to static
        conn.close(); // close connection once after this entire test file is finished
        System.out.println("connection closed");
    }

    @Test
    public void addingTourSetsId() throws Exception {
        Tour testTour = setupTour();
        int originaTourId = testTour.getId();
        tourDao.add(testTour);
        assertNotEquals(originaTourId, testTour.getId());
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
        Tour testTour = setupTour();
        Tour otherTour = setupTour();
        assertEquals(testTour, tourDao.findById(testTour.getId()));
    }

    @Test
    public void updateCorrectlyUpdatesAllFields() throws Exception {
        Tour testTour = setupTour();
        tourDao.update(testTour.getId(), "a", "b", "c", 70);
        Tour foundTour = tourDao.findById(testTour.getId());
        assertEquals("a", foundTour.getTourName());
        assertEquals("b", foundTour.getTourDescription());
        assertEquals("c", foundTour.getImageUrl());
        assertEquals("d", foundTour.getPrice());
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
        Tour tour = new Tour("","","", "", 90);
        tourDao .add(tour);
        return tour;
    }
}
