package models;

import java.math.BigDecimal;

public class Kid extends Product {

    public Kid(String name, String description, String imageUrl, BigDecimal price, int quantity, String color) {
        super(name, description, imageUrl, price, quantity, color);
    }
}
