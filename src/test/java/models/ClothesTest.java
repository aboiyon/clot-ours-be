package models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ClothesTest {
    @Test
    public void clothesInstantiatesCorrectly(){
        Clothes clothes = new Clothes("","","",0);
        assertEquals(true, clothes instanceof Clothes);
    }

    @Test
    public void setClotheIdCorrectly(){
        Clothes clothes = new Clothes("","","",0);
        clothes.setId(0);
        assertEquals(0, clothes.getId());
    }

    @Test
    public void setClotheName(){
        Clothes clothes = new Clothes("","","",0);
        clothes.setName("dera");
        assertEquals("dera", clothes.getName());
    }

    @Test
    public void setClotheDescriptionCheck(){
        Clothes clothes = new Clothes("","","",0);
        clothes.setDescription("Women's clothes");
        assertEquals("Women's clothes", clothes.getDescription());
    }

    @Test
    public void setClothesImageUrl(){
        Clothes clothes = new Clothes("","","",0);
        clothes.setImageUrl("wwwww");
        assertEquals("wwwww", clothes.getImageUrl());
    }

    @Test
    public void setClothesPrice(){
        Clothes clothes = new Clothes("","","",0);
        clothes.setPrice(30);
        assertEquals(30, clothes.getPrice());
    }
}
