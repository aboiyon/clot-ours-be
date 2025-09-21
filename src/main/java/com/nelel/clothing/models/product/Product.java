package com.nelel.clothing.models.product;

import com.nelel.clothing.enums.InventoryStatus;
import com.nelel.clothing.enums.ProductCategory;
import com.nelel.clothing.exceptions.InsufficientStockException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

public class Product {
    private int id;
    private String name;
    private String description;
    private String imageUrl;
    private BigDecimal price;
    private String color;
    private ProductCategory category;
    private String brand;
    private String size;
    private String material;
    private boolean isActive;

    // === INVENTORY MANAGEMENT ===
    private int stockQuantity;          // Total physical inventory
    private int reservedQuantity;       // Stock held in pending orders/carts
    private int minimumStockLevel;      // Reorder threshold
    private boolean trackInventory;     // Some products might not need tracking (digital goods)
    private int maxOrderQuantity;       // Max quantity per order (prevent bulk buying)

    // === E-COMMERCE FEATURES ===
    private BigDecimal compareAtPrice;  // Original price for "was $X, now $Y"
    private boolean isFeatured;         // Show on homepage
    private boolean allowBackorders;    // Accept orders when out of stock
    private int backorderLimit;         // Max backorders allowed
    private String sku;                 // Stock Keeping Unit - unique identifier
    private double weight;              // For shipping calculations
    private String tags;                // Comma-separated tags for search/filtering

    // === TIMESTAMPS ===
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // === CONSTRUCTORS ===
    public Product() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.isActive = true;
        this.trackInventory = true;
        this.minimumStockLevel = 5;
        this.maxOrderQuantity = 10;
        this.allowBackorders = false;
        this.backorderLimit = 0;
    }

    public Product(String name, String description, BigDecimal price, ProductCategory category) {
        this();
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    // === INVENTORY BUSINESS LOGIC ===

    /**
     * Get quantity available for purchase (not reserved)
     */
    public int getAvailableQuantity() {
        if (!trackInventory) {
            return Integer.MAX_VALUE; // Digital products, unlimited
        }
        return Math.max(0, stockQuantity - reservedQuantity);
    }

    /**
     * Total quantity including backorders if allowed
     */
    public int getTotalAvailableQuantity() {
        int available = getAvailableQuantity();
        if (allowBackorders && available == 0) {
            return Math.max(0, backorderLimit);
        }
        return available;
    }

    /**
     * Check if product is currently in stock
     */
    public boolean isInStock() {
        return !trackInventory || getAvailableQuantity() > 0 || (allowBackorders && backorderLimit > 0);
    }

    /**
     * Check if inventory is running low
     */
    public boolean isLowStock() {
        return trackInventory && stockQuantity <= minimumStockLevel && stockQuantity > 0;
    }

    /**
     * Check if product is completely out of stock
     */
    public boolean isOutOfStock() {
        return trackInventory && getAvailableQuantity() == 0 && (!allowBackorders || backorderLimit == 0);
    }

    /**
     * Check if requested quantity can be fulfilled
     */
    public boolean canFulfill(int requestedQuantity) {
        if (!isActive) {
            return false;
        }
        if (!trackInventory) {
            return requestedQuantity <= maxOrderQuantity;
        }
        if (requestedQuantity > maxOrderQuantity) {
            return false;
        }
        return getTotalAvailableQuantity() >= requestedQuantity;
    }

    /**
     * Reserve stock for a pending order/cart
     * @param quantity Amount to reserve
     * @throws InsufficientStockException if not enough stock available
     */
    public void reserveStock(int quantity) {
        if (!canFulfill(quantity)) {
            throw new InsufficientStockException(
                    String.format("Cannot reserve %d units of product %s. Available: %d",
                            quantity, name, getTotalAvailableQuantity())
            );
        }

        if (trackInventory) {
            this.reservedQuantity += quantity;
            this.updatedAt = LocalDateTime.now();
        }
    }

    /**
     * Release previously reserved stock (cart abandonment, order cancellation)
     */
    public void releaseReservedStock(int quantity) {
        if (trackInventory) {
            this.reservedQuantity = Math.max(0, this.reservedQuantity - quantity);
            this.updatedAt = LocalDateTime.now();
        }
    }

    /**
     * Fulfill an order - remove from both stock and reserved
     */
    public void fulfillOrder(int quantity) {
        if (trackInventory) {
            this.stockQuantity = Math.max(0, this.stockQuantity - quantity);
            this.reservedQuantity = Math.max(0, this.reservedQuantity - quantity);
            this.updatedAt = LocalDateTime.now();
        }
    }

    /**
     * Add stock (receiving inventory)
     */
    public void addStock(int quantity) {
        if (trackInventory && quantity > 0) {
            this.stockQuantity += quantity;
            this.updatedAt = LocalDateTime.now();
        }
    }

    /**
     * Remove stock (damage, theft, etc.)
     */
    public void removeStock(int quantity, String reason) {
        if (trackInventory && quantity > 0) {
            this.stockQuantity = Math.max(0, this.stockQuantity - quantity);
            this.updatedAt = LocalDateTime.now();
            // TODO: Log inventory adjustment with reason
        }
    }

    /**
     * Get inventory status for display
     */
    public InventoryStatus getInventoryStatus() {
        if (!trackInventory) {
            return InventoryStatus.UNLIMITED;
        }
        if (isOutOfStock()) {
            return allowBackorders ? InventoryStatus.BACKORDER : InventoryStatus.OUT_OF_STOCK;
        }
        if (isLowStock()) {
            return InventoryStatus.LOW_STOCK;
        }
        return InventoryStatus.IN_STOCK;
    }

    /**
     * Calculate discount percentage if compare at price is set
     */
    public BigDecimal getDiscountPercentage() {
        if (compareAtPrice != null && compareAtPrice.compareTo(price) > 0) {
            BigDecimal discount = compareAtPrice.subtract(price);
            return discount.divide(compareAtPrice, 2, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal("100"));
        }
        return BigDecimal.ZERO;
    }

    /**
     * Check if product is on sale
     */
    public boolean isOnSale() {
        return compareAtPrice != null && compareAtPrice.compareTo(price) > 0;
    }

    // === STANDARD GETTERS AND SETTERS ===
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) {
        this.name = name;
        this.updatedAt = LocalDateTime.now();
    }

    public String getDescription() { return description; }
    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) {
        this.price = price;
        this.updatedAt = LocalDateTime.now();
    }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public ProductCategory getCategory() { return category; }
    public void setCategory(ProductCategory category) {
        this.category = category;
        this.updatedAt = LocalDateTime.now();
    }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public String getMaterial() { return material; }
    public void setMaterial(String material) { this.material = material; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) {
        isActive = active;
        this.updatedAt = LocalDateTime.now();
    }

    // === INVENTORY GETTERS/SETTERS ===
    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = Math.max(0, stockQuantity);
        this.updatedAt = LocalDateTime.now();
    }

    public int getReservedQuantity() { return reservedQuantity; }
    public void setReservedQuantity(int reservedQuantity) {
        this.reservedQuantity = Math.max(0, reservedQuantity);
    }

    public int getMinimumStockLevel() { return minimumStockLevel; }
    public void setMinimumStockLevel(int minimumStockLevel) {
        this.minimumStockLevel = Math.max(0, minimumStockLevel);
    }

    public boolean isTrackInventory() { return trackInventory; }
    public void setTrackInventory(boolean trackInventory) { this.trackInventory = trackInventory; }

    public int getMaxOrderQuantity() { return maxOrderQuantity; }
    public void setMaxOrderQuantity(int maxOrderQuantity) {
        this.maxOrderQuantity = Math.max(1, maxOrderQuantity);
    }

    public BigDecimal getCompareAtPrice() { return compareAtPrice; }
    public void setCompareAtPrice(BigDecimal compareAtPrice) { this.compareAtPrice = compareAtPrice; }

    public boolean isFeatured() { return isFeatured; }
    public void setFeatured(boolean featured) { isFeatured = featured; }

    public boolean isAllowBackorders() { return allowBackorders; }
    public void setAllowBackorders(boolean allowBackorders) { this.allowBackorders = allowBackorders; }

    public int getBackorderLimit() { return backorderLimit; }
    public void setBackorderLimit(int backorderLimit) {
        this.backorderLimit = Math.max(0, backorderLimit);
    }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = Math.max(0, weight); }

    public String getTags() { return tags; }
    public void setTags(String tags) { this.tags = tags; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sku='" + sku + '\'' +
                ", category=" + category +
                ", price=" + price +
                ", available=" + getAvailableQuantity() +
                ", status=" + getInventoryStatus() +
                '}';
    }
}