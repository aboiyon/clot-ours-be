package models;

import java.math.BigDecimal;

public class Man extends Product{

    public Man(String name, String description, String imageUrl, BigDecimal price, int quantity, String color) {
        super(name, description, imageUrl, price, quantity, color);
    }
}
