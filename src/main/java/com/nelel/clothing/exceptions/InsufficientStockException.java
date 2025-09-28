package com.nelel.clothing.exceptions;

public class InsufficientStockException extends RuntimeException{
    private final int requestedQuantity;
    private final int availableQuantity;
    private final String productName;

    public InsufficientStockException(String message) {
        super(message);
        this.requestedQuantity = 0;
        this.availableQuantity = 0;
        this.productName = null;
    }

    public InsufficientStockException(String message, int requestedQuantity, int availableQuantity, String productName) {
        super(message);
        this.requestedQuantity = requestedQuantity;
        this.availableQuantity = availableQuantity;
        this.productName = productName;
    }

    public int getRequestedQuantity() { return requestedQuantity; }
    public int getAvailableQuantity() { return availableQuantity; }
    public String getProductName() { return productName; }
}
