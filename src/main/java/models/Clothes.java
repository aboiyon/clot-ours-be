package models;

import java.util.Objects;

public class Clothes {
    private int id;
    private String clotheName;
    private String clotheDescription;
    private String  imageUrl;
    private int price;

    public Clothes (String clotheName, String clotheDescription, String imageUrl, int price){
        this.clotheName = clotheName;
        this.clotheDescription = clotheDescription;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClotheName() {
        return clotheName;
    }

    public void setClotheName(String clotheName) {
        this.clotheName = clotheName;
    }

    public String getClotheDescription() {
        return clotheDescription;
    }

    public void setClotheDescription(String clotheDescription) {
        this.clotheDescription = clotheDescription;
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
        Clothes clothes = (Clothes) o;
        return id == clothes.id &&
                price == clothes.price &&
                clotheName.equals(clothes.clotheName) &&
                clotheDescription.equals(clothes.clotheDescription) &&
                imageUrl.equals(clothes.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clotheName, clotheDescription, imageUrl, price);
    }

}
