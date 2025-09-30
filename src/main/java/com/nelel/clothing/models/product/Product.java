package com.nelel.clothing.models.product;

import com.nelel.clothing.enums.ProductCategory;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Product {
    public final int id;
    public final String sku;
    public final String name;
    public final ProductCategory category;
    public final Instant createdAt;

    public String description;
    public String imageUrl;
    public BigDecimal price;
    public String color;
    private String brand;
    public String size;
    public String material;
    public boolean active;

    // Inventory (data only)
    public int stockQuantity;
    public int reservedQuantity;
    int minimumStockLevel;
    public boolean trackInventory;
    public int maxOrderQuantity;
    public boolean allowBackorders;
    public int backorderLimit;

    // E-commerce
    public BigDecimal compareAtPrice;
    public boolean featured;
    public BigDecimal weight;
    public Set<String> tags;

    public Instant updatedAt;

    public Product(int id, String sku, String name, BigDecimal price, ProductCategory category) {
        this.id = id;
        this.sku = Objects.requireNonNull(sku, "SKU must not be null");
        this.name = Objects.requireNonNull(name, "Name must not be null");
        this.price = Objects.requireNonNull(price, "Price must not be null");
        if (price.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Price cannot be negative");
        this.category = Objects.requireNonNull(category, "Category must not be null");
        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
        this.active = true;

        // defaults
        this.trackInventory = true;
        this.minimumStockLevel = 5;
        this.maxOrderQuantity = 10;
        this.allowBackorders = false;
        this.backorderLimit = 0;
        this.weight = BigDecimal.ZERO;
        this.tags = new HashSet<>();
    }

    // === PRICING ===
    public BigDecimal getCompareAtPrice() { return compareAtPrice; }
    public void setCompareAtPrice(BigDecimal compareAtPrice) { this.compareAtPrice = compareAtPrice; }
    public boolean isOnSale() { return compareAtPrice != null && compareAtPrice.compareTo(price) > 0; }

    // === GETTERS/SETTERS ===
    public int getId() { return id; }
    public String getSku() { return sku; }
    public String getName() { return name; }
    public ProductCategory getCategory() { return category; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Price cannot be negative");
        this.price = price;
        this.updatedAt = Instant.now();
    }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; this.updatedAt = Instant.now(); }

    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }

    public Set<String> getTags() { return tags; }
    public void setTags(Set<String> tags) { this.tags = new HashSet<>(tags); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product p)) return false;
        return sku.equals(p.sku);
    }

    @Override
    public int hashCode() { return Objects.hash(sku); }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", sku='" + sku + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    public void setId(int id) {

    }
}
