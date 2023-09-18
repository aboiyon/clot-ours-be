package models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TourTest {
    @Test
    public void checkToursInstantiatesCorrectly(){
        Tour tour = new Tour("", "", "", "", 0);
        assertEquals(true, tour instanceof Tour);
    }

    @Test
    public void setTourId(){
        Tour tour = new Tour("","","","",0);
        tour.setId(3);
        assertEquals(3, tour.getId());
    }

    @Test
    public void setTourName(){
        Tour tour = new Tour("","","","",0);
        tour.setTourName("city");
        assertEquals("city", tour.getTourName());
    }

    @Test
    public void setTourDescription(){
        Tour tour = new Tour("","","","",0);
        tour.setTourDescription("xxx");
        assertEquals("xxx", tour.getTourDescription());
    }

    @Test
    public void setTourImageUrl(){
        Tour tour = new Tour("","","","",0);
        tour.setImageUrl("zzz");
        assertEquals("zzz", tour.getImageUrl());
    }

    @Test
    public void setTourPrice(){
        Tour tour = new Tour("","","","",0);
        tour.setPrice(20);
        assertEquals(20, tour.getPrice());
    }
}
