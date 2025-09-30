package com.nelel.clothing.models.product;

import com.nelel.clothing.enums.ProductCategory;
import com.nelel.clothing.exceptions.InsufficientStockException;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    // Inventory
    private int stockQuantity;
    private int reservedQuantity;
    private int minimumStockLevel;
    private boolean trackInventory;
    private int maxOrderQuantity;
    private boolean allowBackorders;
    private int backorderLimit;

    // E-commerce
    private BigDecimal compareAtPrice;
    private boolean featured;
    private BigDecimal weight;
    private Set<String> tags;

    private Instant updatedAt;

    // === CONSTRUCTOR ===
    public Product(int id, String sku, String name, BigDecimal price, ProductCategory category) {
        this.id = id;
        this.sku = Objects.requireNonNull(sku, "SKU must not be null");
        this.name = Objects.requireNonNull(name, "Name must not be null");
        this.price = validatePrice(price);
        this.category = Objects.requireNonNull(category, "Category must not be null");
        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
        this.active = true;
        this.trackInventory = true;
        this.minimumStockLevel = 5;
        this.maxOrderQuantity = 10;
        this.allowBackorders = false;
        this.backorderLimit = 0;
        this.weight = BigDecimal.ZERO;
        this.tags = new HashSet<>();
    }

// === INVENTORY BUSINESS LOGIC ===

    private void touchUpdatedAt() {
        this.updatedAt = Instant.now();
    }

    public int getAvailableQuantity() {
        return trackInventory ? Math.max(0, stockQuantity - reservedQuantity) : Integer.MAX_VALUE;
    }

    public int getTotalAvailableQuantity() {
        int available = getAvailableQuantity();
        return allowBackorders ? available + backorderLimit : available;
    }

    public boolean isInStock() {
        return !trackInventory || getAvailableQuantity() > 0 || (allowBackorders && backorderLimit > 0);
    }

    public boolean isLowStock() {
        return trackInventory && stockQuantity <= minimumStockLevel && stockQuantity > 0;
    }

    public boolean isOutOfStock() {
        return trackInventory && getAvailableQuantity() == 0 && (!allowBackorders || backorderLimit == 0);
    }

    public boolean canFulfill(int requestedQuantity) {
        if (!active) return false;
        if (requestedQuantity > maxOrderQuantity) return false;
        return getTotalAvailableQuantity() >= requestedQuantity;
    }

    public void reserveStock(int quantity) {
        if (!canFulfill(quantity)) {
            throw new InsufficientStockException(
                    String.format("Cannot reserve %d units of product %s. Available: %d",
                            quantity, name, getTotalAvailableQuantity())
            );
        }
        if (trackInventory) {
            reservedQuantity += quantity;
            touchUpdatedAt();
        }
    }

    public void releaseReservedStock(int quantity) {
        if (trackInventory) {
            reservedQuantity = Math.max(0, reservedQuantity - quantity);
            touchUpdatedAt();
        }
    }

    public void fulfillOrder(int quantity) {
        if (trackInventory) {
            // assume reserved stock first, then physical stock
            reservedQuantity = Math.max(0, reservedQuantity - quantity);
            stockQuantity = Math.max(0, stockQuantity - quantity);
            touchUpdatedAt();
        }
    }

    public void addStock(int quantity) {
        if (trackInventory && quantity > 0) {
            stockQuantity += quantity;
            touchUpdatedAt();
        }
    }

    public void removeStock(int quantity, String reason) {
        if (trackInventory && quantity > 0) {
            stockQuantity = Math.max(0, stockQuantity - quantity);
            touchUpdatedAt();
            // TODO: persist inventory adjustment log with reason
        }
    }

    // === PRICING ===
    public BigDecimal getDiscountPercentage() {
        if (compareAtPrice != null && compareAtPrice.compareTo(price) > 0) {
            BigDecimal discount = compareAtPrice.subtract(price);
            return discount.divide(compareAtPrice, 2, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
        }
        return BigDecimal.ZERO;
    }

    public boolean isOnSale() {
        return compareAtPrice != null && compareAtPrice.compareTo(price) > 0;
    }

    // === VALIDATION HELPERS ===
    private BigDecimal validatePrice(BigDecimal value) {
        Objects.requireNonNull(value, "Price must not be null");
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        return value;
    }

    // === GETTERS (SETTERS only where mutation is needed) ===
    public int getId() { return id; }
    public String getSku() { return sku; }
    public String getName() { return name; }
    public ProductCategory getCategory() { return category; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) {
        this.price = validatePrice(price);
        touchUpdatedAt();
    }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; touchUpdatedAt(); }

    public Set<String> getTags() { return tags; }
    public void setTags(Set<String> tags) { this.tags = new HashSet<>(tags); }

    // === EQUALITY ===
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return sku.equals(product.sku);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sku);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", sku='" + sku + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stock=" + stockQuantity +
                ", reserved=" + reservedQuantity +
                '}';
    }

}
