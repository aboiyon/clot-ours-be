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
    public void testSetBeverageId () {
        Beverages beverages = new Beverages("","","", 0);
        beverages.setId(0);
        assertEquals(0, beverages.getId());
    }

    @Test
    public void beverage_name_set_correctly() {
        Beverages beverages = new Beverages("beer","My fav", "", 20);
        beverages.setBeverageName("beer");
        assertEquals("beer",beverages.getBeverageName());
    }

    @Test
    public void beverage_description_set_correctly() {
        Beverages beverages = new Beverages("","", "", 0);
        beverages.setBeverageDescription("Italian brewed");
        assertEquals("Italian brewed",beverages.getBeverageDescription());
    }

    @Test
    public void testSetBeverageImageUrl () {
        Beverages beverages = new Beverages("", "","",0);
        beverages.setImageUrl("www.beer.com");
        assertEquals("www.beer.com", beverages.getImageUrl());
    }

    @Test
    public void testSetBeveragePrice () {
        Beverages beverages = new Beverages("","","", 0);
        beverages.setPrice(200);
        assertEquals(200, beverages.getPrice());
    }
}
