package models;

import java.math.BigDecimal;

public class Designer extends Product {

    public Designer(String name, String description, String imageUrl, BigDecimal price, int quantity, String color) {
        super(name, description, imageUrl, price, quantity, color);
    }
}
