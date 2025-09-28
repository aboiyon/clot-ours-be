package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class WomanTest {
    @Before
    public void setUp() throws Exception {}

    @After
    public  void tearDown() throws Exception {}

    @Test
    public void checkIfWomanInstantiatesCorrectly() {
        Woman woman = new Woman("","","", 0);
        assertEquals(true, woman instanceof Woman);
    }

    @Test
    public void setWomanId(){
        Woman testWoman =  new Woman("", "", "", 0);
        testWoman.setId(1);
        assertEquals(1, testWoman.getId());
    }

    @Test
    public void setName(){
        Woman testWoman = new Woman("", "", "", 0);
        testWoman.setName("dera");
        assertEquals("dera", testWoman.getName());
    }

    @Test
    public void setDescription(){
        Woman testWoman = new Woman("","","",0);
        testWoman.setDescription("xxxx");
        assertEquals("xxxx", testWoman.getDescription());
    }

    @Test
    public void setImageUrl(){
        Woman testWoman = new Woman("","","", 0);
        testWoman.setImageUrl("www.com");
        assertEquals("www.com", testWoman.getImageUrl());
    }

    @Test
    public void setPrice(){
        Woman testWoman = new Woman("","","", 0);
        testWoman.setPrice(70);
        assertEquals(70.0, testWoman.getPrice());
    }
}
