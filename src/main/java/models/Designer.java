package models;

import java.math.BigDecimal;
import java.util.Objects;

public class Designer {
    private int id;
    private String name;
    private String description;
    private String imageUrl;
    private String imageUrl02;
    private BigDecimal price;

    public Designer(String name, String description, String imageUrl, String imageUrl02, BigDecimal price) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.imageUrl02 = imageUrl02;
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

    public String getImageUrl02() {
        return imageUrl02;
    }

    public void setImageUrl02(String getImageUrl02) {
        this.imageUrl02 = getImageUrl02;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Designer)) return false;
        Designer designer = (Designer) o;
        return id == designer.id &&
                name.equals(designer.name) &&
                description.equals(designer.description) &&
                imageUrl.equals(designer.imageUrl) &&
                imageUrl02.equals(designer.imageUrl02) &&
                price.equals(designer.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, imageUrl, imageUrl02, price);
    }
}
