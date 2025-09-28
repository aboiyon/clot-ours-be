package models;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    public int id;
    public String name;
    public String description;
    public String  imageUrl;
    public BigDecimal price;
    public int quantity;
    public String  color;

    public Product(String name, String description, String imageUrl, BigDecimal price, int quantity, String color) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
        this.quantity = quantity;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return id == product.id &&
                quantity == product.quantity &&
                name.equals(product.name) &&
                description.equals(product.description) &&
                imageUrl.equals(product.imageUrl) &&
                price.equals(product.price) &&
                color.equals(product.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, imageUrl, price, quantity, color);
    }
}
