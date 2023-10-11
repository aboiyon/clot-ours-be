package models;

import java.util.Objects;

public class Tour {
    private int id;
    private String name;
    private String description;
    private String  imageUrl;
    private int imageId;
    private int price;


    public Tour(String name, String description, String imageUrl, int imageId, int price) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.imageId = imageId;
        this.price = price;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tour)) return false;
        Tour tour = (Tour) o;
        return id == tour.id &&
                imageId == tour.imageId &&
                price == tour.price &&
                name.equals(tour.name) &&
                description.equals(tour.description) &&
                imageUrl.equals(tour.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, imageUrl, imageId, price);
    }

}
