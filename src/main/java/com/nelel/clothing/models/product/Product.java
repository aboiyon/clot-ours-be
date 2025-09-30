package com.nelel.clothing.models.product;

import com.nelel.clothing.enums.ProductCategory;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Product {
    private final int id;
    private final String sku;
    private final String name;
    private final ProductCategory category;
    private final Instant createdAt;

    private String description;
    private String imageUrl;
    private BigDecimal price;
    private String color;
    private String brand;
    private String size;
    private String material;
    private boolean active;

    // Inventory (data only)
    int stockQuantity;
    int reservedQuantity;
    int minimumStockLevel;
    boolean trackInventory;
    int maxOrderQuantity;
    boolean allowBackorders;
    int backorderLimit;

    // E-commerce
    private BigDecimal compareAtPrice;
    private boolean featured;
    private BigDecimal weight;
    private Set<String> tags;

    private Instant updatedAt;

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

}
