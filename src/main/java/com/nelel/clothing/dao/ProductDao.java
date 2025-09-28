package com.nelel.clothing.dao;

import com.nelel.clothing.models.product.Product;

import java.util.List;

public interface ProductDao {
    /**
     * Adds a new Product to the data store.
     * The implementation should set the product's ID.
     *
     * @param product The product object to add.
     */
    void add(Product product);

    /**
     * Retrieves a list of all products from the data store.
     *
     * @return A list of all products.
     */
    List<Product> getAll();

    /**
     * Finds a specific product by its unique ID.
     *
     * @param id The ID of the product to find.
     * @return The found Product object, or null if no product is found.
     */
    Product findById(int id);

    /**
     * Updates an existing product in the data store.
     *
     * @param product The product object with updated information.
     */
    void update(Product product);

    /**
     * Deletes a product from the data store by its ID.
     *
     * @param id The ID of the product to delete.
     */
    void deleteById(int id);

    /**
     * Deletes all products from the data store.
     */
    void clearAll();
}
