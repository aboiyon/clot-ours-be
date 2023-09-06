package models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BeveragesTest {
    @Test
//    String departmentName , int No_workers)
    public void beverages_instantiates_correctly() {
        Beverages beverages = new Beverages("Beer","My fav", "", 20);
        assertEquals(true, beverages instanceof Beverages);
    }

    @Test
    public void input_correctly() {
        Beverages beverages = new Beverages("engineering","My fav", "", 20);
        assertEquals("engineering",beverages.getBeverageName());
    }
}
