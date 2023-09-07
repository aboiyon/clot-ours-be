package models;

import java.util.Objects;

public class Tours {
    private int id;
    private String tourName;
    private String tourDescription;
    private String  imageUrl;
    private int price;


    public Tours(String name, String tourName, String tourDescription, String imageUrl, int price) {
        this.tourName = tourName;
        this.tourDescription = tourDescription;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public String getTourDescription() {
        return tourDescription;
    }

    public void setTourDescription(String tourDescription) {
        this.tourDescription = tourDescription;
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
        if (!(o instanceof Tours)) return false;
        Tours tours = (Tours) o;
        return id == tours.id &&
                price == tours.price &&
                tourName.equals(tours.tourName) &&
                tourDescription.equals(tours.tourDescription) &&
                imageUrl.equals(tours.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tourName, tourDescription, imageUrl, price);
    }
}
