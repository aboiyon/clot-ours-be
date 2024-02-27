package sql2o;

import models.Woman;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class WomanHelperTest {
    private static Connection connection;
    private static WomanHelper womanHelper;
    private static KidHelper kidHelper;
    private static ReviewHelper reviewHelper;

    @BeforeClass
    public static void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/utalii_test";
        Sql2o sql2o = new Sql2o(connectionString, "cheruiyot", "new_password");
        womanHelper = new WomanHelper(sql2o);
        kidHelper = new KidHelper(sql2o);
        reviewHelper = new ReviewHelper(sql2o);
        connection = sql2o.open();
    }

    @After
    public  void tearDown() throws Exception {
        System.out.println("clearing database");
        womanHelper.deleteAll();
        kidHelper.deleteAll();
        reviewHelper.clearAll();
    }

    @AfterClass
    public static void shutDown() throws Exception {
        connection.close();
        System.out.println("connection closed");
    }

    @Test
    public void addingWomanSetsId() throws Exception {
        Woman testWoman = setupWoman();
        int originalWomanId = testWoman.getId();
        womanHelper.add(testWoman);
        assertNotEquals(originalWomanId, testWoman.getId());
    }

    @Test
    public void addedWomenAreReturnedFromGetAll() throws Exception {
        Woman testWoman = setupWoman();
        assertEquals(1, womanHelper.getAll().size());
    }

    @Test
    public void noWomenReturnsEmptyList() throws Exception {
        assertEquals(0, womanHelper.getAll().size());
    }

    @Test
    public void findByIdReturnsCorrectWoman () throws  Exception {
        Woman testWoman = setupWoman();
        assertEquals(testWoman, womanHelper.findById(testWoman.getId()));
    }

    @Test
    public void updateCorrectlyUpdatesAllFields() throws Exception {
        Woman testWoman = setupWoman();
        womanHelper.update(testWoman.getId(), "a","b","c", 0);
        Woman foundWoman = womanHelper.findById(testWoman.getId());
        assertEquals("a", foundWoman.getName());
        assertEquals("b", foundWoman.getDescription());
        assertEquals("c", foundWoman.getImageUrl());
        assertEquals(0.0, foundWoman.getPrice());
    }

    @Test
    public void deleteByIdDeletesCorrectWoman() throws Exception {
        Woman testWoman = setupWoman();
        womanHelper.deleteById(testWoman.getId());
        assertEquals(0, womanHelper.getAll().size());
    }

    @Test
    public void deleteAll() throws  Exception {
        Woman testWoman = setupWoman();
        womanHelper.deleteAll();
        assertEquals(0, womanHelper.getAll().size());
    }

//    helpers
    public Woman setupWoman () {
        Woman woman = new Woman("","","", 0);
        womanHelper.add(woman);
        return woman;
    }
}
