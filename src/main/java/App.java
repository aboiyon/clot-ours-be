import com.google.gson.Gson;
import com.nelel.clothing.config.ConfigLoader;
import com.nelel.clothing.controllers.cart.CartController;
import com.nelel.clothing.controllers.product.ProductController;
import com.nelel.clothing.sql2o.Sql2oCartDao;
import com.nelel.clothing.sql2o.Sql2oProductDao;
import exceptions.ApiException;
import org.sql2o.Sql2o;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        port(4567);

        Gson gson = new Gson();

        staticFileLocation("/public");
        String connectionString = "jdbc:postgresql://localhost:5432/utalii";

        String dbHost = ConfigLoader.get("DB_HOST");
        String dbPort = ConfigLoader.get("DB_PORT");
        String dbName = ConfigLoader.get("DB_NAME");
        String dbUser = ConfigLoader.get("DB_USER");
        String dbPassword = ConfigLoader.get("DB_PASSWORD");

//        String connectionString = String.format("jdbc:postgresql://%s:%s", dbHost, dbPort, dbName);
        Sql2o sql2o = new Sql2o(connectionString, dbUser, dbPassword);


        enableCORS("*", "POST,GET", "");

        Sql2oProductDao productDao = new Sql2oProductDao(sql2o);
        ProductController productController = new ProductController(productDao);
        Sql2oCartDao cartDao = new Sql2oCartDao(sql2o, productDao);
        CartController cartController = new CartController(cartDao);


        path("/api", () -> {
            before("/api/*", (req, res) -> res.type("application/json"));
            path("/products", () -> {
                post("", "application/json", productController::addProduct);
                get("", "application/json", productController::getAllProducts);
                get("/:id", "application/json", productController::getProductById);
                put("/:id", "application/json", productController::updateProduct);
                delete("/:id", "application/json", productController::deleteProduct);
                post("/:id/reserve", "application/json", productController::reserveStock);
                post("/:id/release", "application/json", productController::releaseReservedStock);
            });

            path("/carts", () -> {
                post("", "application/json", cartController::createCart);
                get("", "application/json", cartController::getCart);
                post("/:cartId/items", "application/json", cartController::addItemToCart);
                delete("/:cartId/items", "application/json", cartController::removeItemFromCart);
            });
        });

        //FILTERS
        exception(ApiException.class, (exception, req, res) -> {
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("status", exception.getStatusCode());
            jsonMap.put("errorMessage", exception.getMessage());
            res.type("application/json");
            res.status(exception.getStatusCode());
            res.body(gson.toJson(jsonMap));
        });

        after((req, res) ->{
            res.type("application/json");
        });

    }

    // Enables CORS on requests. This method is an initialization method and should be called once.
    private static void enableCORS(final String origin, final String methods, final String headers) {

        options("/*", (request, response) -> {

            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", origin);
            response.header("Access-Control-Request-Method", methods);
            response.header("Access-Control-Allow-Headers", headers);
            // Note: this may or may not be necessary in your particular application
            response.type("application/json");
        });
    }

}
