package models;

import java.util.Objects;

public class Beverages {
    private int id;
    private String beverageName;
    private String beverageDescription;
    private String  imageUrl;
    private int price;

    public Beverages (String beverageName, String beverageDescription, String imageUrl, int price){
        this.beverageName = beverageName;
        this.beverageDescription = beverageDescription;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBeverageName() {
        return beverageName;
    }

    public void setBeverageName(String beverageName) {
        this.beverageName = beverageName;
    }

    public String getBeverageDescription() {
        return beverageDescription;
    }

    public void setBeverageDescription(String beverageDescription) {
        this.beverageDescription = beverageDescription;
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
        if (o == null || getClass() != o.getClass()) return false;
        Beverages beverages = (Beverages) o;
        return id == beverages.id &&
                price == beverages.price &&
                beverageName.equals(beverages.beverageName) &&
                beverageDescription.equals(beverages.beverageDescription) &&
                imageUrl.equals(beverages.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, beverageName, beverageDescription, imageUrl, price);
    }
}
