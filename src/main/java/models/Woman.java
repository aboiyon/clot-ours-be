package models;

import java.util.Objects;

public class Woman {
    private int id;
    private String name;
    private String description;
    private String  imageUrl;
    private double price;

    public Woman(String name, String description, String imageUrl, double price) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Woman)) return false;
        Woman woman = (Woman) o;
        return id == woman.id &&
                Double.compare(woman.price, price) == 0 &&
                name.equals(woman.name) &&
                description.equals(woman.description) &&
                imageUrl.equals(woman.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, imageUrl, price);
    }
}
