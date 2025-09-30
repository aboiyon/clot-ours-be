package com.nelel.clothing.sql2o;

import com.nelel.clothing.dao.ProductDao;
import com.nelel.clothing.models.product.Product;
import com.nelel.clothing.services.InventoryService;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oProductDao implements ProductDao {
    private final Sql2o sql2o;

    public Sql2oProductDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }
    public InventoryService inventoryService;

    @Override
    public void add(Product product) {
        String sql = "INSERT INTO products (name, description, imageUrl, price, color, category, brand, size, material, " +
                "isActive, stockQuantity, reservedQuantity, minimumStockLevel, trackInventory, maxOrderQuantity, " +
                "compareAtPrice, isFeatured, allowBackorders, backorderLimit, sku, weight, tags, createdAt, updatedAt) " +
                "VALUES (:name, :description, :imageUrl, :price, :color, :category, :brand, :size, :material, :isActive, " +
                ":stockQuantity, :reservedQuantity, :minimumStockLevel, :trackInventory, :maxOrderQuantity, :compareAtPrice, " +
                ":isFeatured, :allowBackorders, :backorderLimit, :sku, :weight, :tags, now(), now()";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql, true)
                    .bind(product)
                    .executeUpdate()
                    .getKey();
            product.setId(id);
        } catch (Sql2oException ex) {
            throw new RuntimeException("Error adding product", ex);
        }
    }

    @Override
    public List<Product> getAll() {
        String sql = "SELECT id, name, description,imageUrl, price, color, category, brand, size, material, " +
                "isActive,stockQuantity, reservedQuantity, " +
                "minimumStockLevel, trackInventory, maxOrderQuantity" +
                "compareAtPrice, isFeatured, allowBackorders," +
                "backorderLimit, sku, weight, tags, createdAt, updatedAt " +
                "FROM products";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Product.class);
        } catch (Sql2oException ex) {
            throw new RuntimeException("Error fetching products", ex);
        }
    }

//    public List<Product> getAll() {
//        try (Connection connection = sql2o.open()) {
//            return connection.createQuery("SELECT * FROM designers")
//                    .executeAndFetch(Product.class);
//        }
//    }

    @Override
    public Product findById(int id) {
        String sql = "SELECT id, name, description, imageUrl, price, color, category, brand, size, material, " +
                "isActive,stockQuantity, reservedQuantity, " +
                "minimumStockLevel,trackInventory, maxOrderQuantity, " +
                "compareAtPrice, isFeatured, allowBackorders, " +
                "backorderLimit, sku, weight, tags, createdAt, updatedAt " +
                "FROM products WHERE id = :id";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Product.class);
        } catch (Sql2oException ex) {
            throw new RuntimeException("Error fetching product by ID", ex);
        }
    }

    @Override
    public void update(Product product) {
        String sql = "UPDATE products SET name = :name, description = :description, imageUrl = :imageUrl, price = :price, " +
                "color = :color, category = :category, brand = :brand, size = :size, material = :material, isActive = :isActive, " +
                "stock_quantity = :stockQuantity, reserved_quantity = :reservedQuantity, minimum_stock_level = :minimumStockLevel, " +
                "trackInventory = :trackInventory, maxOrderQuantity = :maxOrderQuantity, compareAtPrice = :compareAtPrice, " +
                "isFeatured = :isFeatured, allowBackorders = :allowBackorders, backorderLimit = :backorderLimit, " +
                "sku = :sku, weight = :weight, tags = :tags, updated_at = :updatedAt WHERE id = :id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .bind(product)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            throw new RuntimeException("Error updating product", ex);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM products WHERE id = :id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            throw new RuntimeException("Error deleting product", ex);
        }
    }

    @Override
    public void clearAll() {
        String sql = "DELETE FROM products";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();
        } catch (Sql2oException ex) {
            throw new RuntimeException("Error clearing products", ex);
        }
    }

    //  reserve stock
    public void reserveStock(int productId, int quantity) {
        Product product = findById(productId);
        if (product == null) {
            throw new RuntimeException("Product with ID " + productId + " not found");
        }
//        product.reserveStock(quantity);
        this.inventoryService.reserveStock(product, quantity);
        update(product);
    }

    // release reserved stock
    public void releaseReservedStock(int productId, int quantity) {
        Product product = findById(productId);
        if (product == null) {
            throw new RuntimeException("Product with ID " + productId + " not found");
        }
//        product.releaseReservedStock(quantity);
        this.inventoryService.releaseReservedStock(product, quantity);
        update(product);
    }

    //  fulfill order
    public void fulfillOrder(int productId, int quantity) {
        Product product = findById(productId);
        if (product == null) {
            throw new RuntimeException("Product with ID " + productId + " not found");
        }
//        product.fulfillOrder(quantity);
        this.inventoryService.fulfillOrder(product, quantity);
        update(product);
    }

    //  add stock
    public void addStock(int productId, int quantity) {
        Product product = findById(productId);
        if (product == null) {
            throw new RuntimeException("Product with ID " + productId + " not found");
        }
//        product.addStock(quantity);
        this.inventoryService.addStock(product,quantity);
        update(product);
    }

    // remove stock
    public void removeStock(int productId, int quantity) {
        Product product = findById(productId);
        if (product == null) {
            throw new RuntimeException("Product with ID " + productId + " not found");
        }
//        product.removeStock(quantity, reason);
        this.inventoryService.removeStock(product, quantity);
        update(product);
    }
}