package com.nelel.clothing.controllers.product;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nelel.clothing.dao.ProductDao;
import com.nelel.clothing.exceptions.InsufficientStockException;
import com.nelel.clothing.models.product.Product;
import com.nelel.clothing.sql2o.Sql2oProductDao;
import exceptions.ApiException;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ProductController {
    private final ProductDao productDao;
    private final Gson gson;

    public ProductController(ProductDao productDao) {
        this.productDao = productDao;

        // Register custom adapters for LocalDate and LocalDateTime
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, (com.google.gson.JsonSerializer<LocalDate>)
                        (src, typeOfSrc, context) -> src == null ? null :
                                new com.google.gson.JsonPrimitive(src.toString()))
                .registerTypeAdapter(LocalDate.class, (com.google.gson.JsonDeserializer<LocalDate>)
                        (json, typeOfT, context) -> LocalDate.parse(json.getAsString()))
                .registerTypeAdapter(LocalDateTime.class, (com.google.gson.JsonSerializer<LocalDateTime>)
                        (src, typeOfSrc, context) -> src == null ? null :
                                new com.google.gson.JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                .registerTypeAdapter(LocalDateTime.class, (com.google.gson.JsonDeserializer<LocalDateTime>)
                        (json, typeOfT, context) -> LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .create();
    }

    public String addProduct(Request req, Response res) {
        Product product = gson.fromJson(req.body(), Product.class);
        if (product.getName() == null || product.getName().isEmpty()) {
            throw new ApiException(400, "Product name cannot be empty.");
        }
        productDao.add(product);
        res.status(201); // CREATED
        return gson.toJson(product);
    }

    public String getAllProducts(Request req, Response res) {
        try {
            List<Product> products = productDao.getAll();
            return gson.toJson(products);
        } catch (RuntimeException ex) {
            throw new ApiException(500, "Error retrieving products: " + ex.getMessage());
        }
    }

    public String getProductById(Request req, Response res) {
        try {
            int productId = Integer.parseInt(req.params("id"));
            Product product = productDao.findById(productId);
            if (product == null) {
                throw new ApiException(404, "Product with id " + productId + " not found.");
            }
            return gson.toJson(product);
        } catch (NumberFormatException ex) {
            throw new ApiException(400, "Invalid product ID format.");
        } catch (RuntimeException ex) {
            throw new ApiException(500, "Error retrieving product: " + ex.getMessage());
        }
    }

    public String updateProduct(Request req, Response res) {
        try {
            int productId = Integer.parseInt(req.params("id"));
            Product product = gson.fromJson(req.body(), Product.class);
            product.setId(productId);
            Product existing = productDao.findById(productId);
            if (existing == null) {
                throw new ApiException(404, "Product with id " + productId + " not found.");
            }
            productDao.update(product);
            return gson.toJson(product);
        } catch (NumberFormatException ex) {
            throw new ApiException(400, "Invalid product ID format.");
        } catch (RuntimeException ex) {
            throw new ApiException(500, "Error updating product: " + ex.getMessage());
        }
    }

    public String deleteProduct(Request req, Response res) {
        try {
            int productId = Integer.parseInt(req.params("id"));
            Product product = productDao.findById(productId);
            if (product == null) {
                throw new ApiException(404, "Product with id " + productId + " not found.");
            }
            productDao.deleteById(productId);
            res.status(204); // NO CONTENT
            return "";
        } catch (NumberFormatException ex) {
            throw new ApiException(400, "Invalid product ID format.");
        } catch (RuntimeException ex) {
            throw new ApiException(500, "Error deleting product: " + ex.getMessage());
        }
    }

    // reserve stock
    public String reserveStock(Request req, Response res) {
        try {
            int productId = Integer.parseInt(req.params("id"));
            int quantity = Integer.parseInt(req.queryParams("quantity"));
            ((Sql2oProductDao) productDao).reserveStock(productId, quantity);
            return gson.toJson(new SuccessResponse("Stock reserved successfully"));
        } catch (NumberFormatException ex) {
            throw new ApiException(400, "Invalid product ID or quantity format.");
        } catch (InsufficientStockException ex) {
            throw new ApiException(400, ex.getMessage());
        } catch (RuntimeException ex) {
            throw new ApiException(500, "Error reserving stock: " + ex.getMessage());
        }
    }

    // release reserved stock
    public String releaseReservedStock(Request req, Response res) {
        try {
            int productId = Integer.parseInt(req.params("id"));
            int quantity = Integer.parseInt(req.queryParams("quantity"));
            ((Sql2oProductDao) productDao).releaseReservedStock(productId, quantity);
            return gson.toJson(new SuccessResponse("Stock released successfully"));
        } catch (NumberFormatException ex) {
            throw new ApiException(400, "Invalid product ID or quantity format.");
        } catch (RuntimeException ex) {
            throw new ApiException(500, "Error releasing stock: " + ex.getMessage());
        }
    }
}

