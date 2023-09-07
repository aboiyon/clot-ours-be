package models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ToursTest {
    @Test
    public void checkToursInstantiatesCorrectly(){
        Tours tours = new Tours("", "", "", "", 0);
        assertEquals(true, tours instanceof Tours);
    }

    @Test
    public void setTourId(){
        Tours tours = new Tours("","","","",0);
        tours.setId(3);
        assertEquals(3, tours.getId());
    }

    @Test
    public void setTourName(){
        Tours tours = new Tours("","","","",0);
        tours.setTourName("city");
        assertEquals("city", tours.getTourName());
    }

    @Test
    public void setTourDescription(){
        Tours tours = new Tours("","","","",0);
        tours.setTourDescription("xxx");
        assertEquals("xxx", tours.getTourDescription());
    }

    @Test
    public void setTourImageUrl(){
        Tours tours = new Tours("","","","",0);
        tours.setImageUrl("zzz");
        assertEquals("zzz", tours.getImageUrl());
    }

    @Test
    public void setTourPrice(){
        Tours tours = new Tours("","","","",0);
        tours.setPrice(20);
        assertEquals(20, tours.getPrice());
    }
}
