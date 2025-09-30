package com.nelel.clothing.services;

import com.nelel.clothing.exceptions.InsufficientStockException;
import com.nelel.clothing.models.product.Product;

import java.time.Instant;

public class InventoryService {
    private void touchUpdatedAt(Product product) {
        // update timestamp
        try {
            var field = Product.class.getDeclaredField("updatedAt");
            field.setAccessible(true);
            field.set(product, Instant.now());
        } catch (Exception ignored) {}
    }

    public int getAvailableQuantity(Product product) {
        return product.trackInventory ? Math.max(0, product.stockQuantity - product.reservedQuantity) : Integer.MAX_VALUE;
    }

    public int getTotalAvailableQuantity(Product product) {
        int available = getAvailableQuantity(product);
        return product.allowBackorders ? available + product.backorderLimit : available;
    }

    public boolean canFulfill(Product product, int requestedQuantity) {
        if (!product.isActive()) return false;
        if (requestedQuantity > product.maxOrderQuantity) return false;
        return getTotalAvailableQuantity(product) >= requestedQuantity;
    }

    public void reserveStock(Product product, int quantity) {
        if (!canFulfill(product, quantity)) {
            throw new InsufficientStockException(
                    String.format("Cannot reserve %d units of product %s. Available: %d",
                            quantity, product.getName(), getTotalAvailableQuantity(product))
            );
        }
        if (product.trackInventory) {
            product.reservedQuantity += quantity;
            touchUpdatedAt(product);
        }
    }

    public void releaseReservedStock(Product product, int quantity) {
        if (product.trackInventory) {
            product.reservedQuantity = Math.max(0, product.reservedQuantity - quantity);
            touchUpdatedAt(product);
        }
    }

    public void fulfillOrder(Product product, int quantity) {
        if (product.trackInventory) {
            product.reservedQuantity = Math.max(0, product.reservedQuantity - quantity);
            product.stockQuantity = Math.max(0, product.stockQuantity - quantity);
            touchUpdatedAt(product);
        }
    }

    public void addStock(Product product, int quantity) {
        if (product.trackInventory && quantity > 0) {
            product.stockQuantity += quantity;
            touchUpdatedAt(product);
        }
    }

    public void removeStock(Product product, int quantity) {
        if (product.trackInventory && quantity > 0) {
            product.stockQuantity = Math.max(0, product.stockQuantity - quantity);
            touchUpdatedAt(product);
            // TODO: log adjustment with reason
        }
    }

}
