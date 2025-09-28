package com.nelel.clothing.enums;

public enum ProductCategory {
    KIDS("kids", "Kids Clothing"),
    MEN("men", "Men's Clothing"),
    WOMEN("women", "Women's Clothing"),
    DESIGNER("designer", "Designer Clothing"),
    ACCESSORIES("accessories", "Accessories"),
    SHOES("shoes", "Footwear");

    private final String code;
    private final String displayName;

    ProductCategory(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    public String getCode() { return code; }
    public String getDisplayName() { return displayName; }

    // Utility method to find by code
    public static ProductCategory fromCode(String code) {
        for (ProductCategory category : values()) {
            if (category.code.equalsIgnoreCase(code)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Unknown product category: " + code);
    }

    @Override
    public String toString() { return displayName; }
}
