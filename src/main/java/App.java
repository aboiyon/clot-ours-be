import com.google.gson.Gson;
import com.nelel.clothing.config.ConfigLoader;
import com.nelel.clothing.controllers.product.ProductController;
import com.nelel.clothing.sql2o.Sql2oProductDao;
import exceptions.ApiException;
import models.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import sql2o.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        ReviewHelper reviewHelper;
        KidHelper kidHelper;
        ManHelper manHelper;
        WomanHelper womanHelper;
        DesignerHelper designerHelper;
        Sql2oOrderDao orderDao;
        Gson gson = new Gson();
        Connection connection;
        port(4567);

        staticFileLocation("/public");
        String connectionString = "jdbc:postgresql://localhost:5432/store";

        String dbHost = ConfigLoader.get("DB_HOST");
        String dbPort = ConfigLoader.get("DB_PORT");
        String dbName = ConfigLoader.get("DB_NAME");
        String dbUser = ConfigLoader.get("DB_USER");
        String dbPassword = ConfigLoader.get("DB_PASSWORD");

//        String connectionString = String.format("jdbc:postgresql://%s:%s", dbHost, dbPort, dbName);
        Sql2o sql2o = new Sql2o(connectionString, dbUser, dbPassword);


        enableCORS();

        Sql2oProductDao productDao = new Sql2oProductDao(sql2o);
        ProductController productController = new ProductController(productDao);
//        Sql2oCartDao cartDao = new Sql2oCartDao(sql2o, productDao);
//        CartController cartController = new CartController(cartDao);

        reviewHelper = new ReviewHelper(sql2o);
        kidHelper = new KidHelper(sql2o);
        manHelper = new ManHelper(sql2o);
        womanHelper = new WomanHelper(sql2o);
        designerHelper = new DesignerHelper(sql2o);
        orderDao = new Sql2oOrderDao(sql2o);
        connection = sql2o.open();


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

//            path("/carts", () -> {
//                post("", "application/json", cartController::createCart);
//                get("", "application/json", cartController::getCart);
//                post("/:cartId/items", "application/json", cartController::addItemToCart);
//                delete("/:cartId/items", "application/json", cartController::removeItemFromCart);
//            });
        });

        post("/orders/new", "application/json", (req, res) -> {
            Order order = gson.fromJson(req.body(), Order.class);
            orderDao.add(order);
            res.status(201);
            return gson.toJson(order);
        });

        get("/orders", "application/json", (req, res) -> {
            System.out.println(orderDao.getAll());

            if(!orderDao.getAll().isEmpty()){
                return gson.toJson(orderDao.getAll());
            }

            else {
                return "{\"message\":\"I'm sorry, but no orders are currently listed in the database.\"}";
            }
        });

        get("/orders/:id", "application/json", (req, res) ->{
            int id = Integer.parseInt(req.params("id"));
            Order order = orderDao.findById(id);
            if (order == null){
                res.status(404);
                return "{\"message\":\"Order not found\"}";
            }
            res.type("application/json");
            res.status(200);
            return gson.toJson(order);
        });

        put("/orders/:id", "application/json", (req, res) ->{
            int id = Integer.parseInt(req.params("id"));
            Order order = gson.fromJson(req.body(), Order.class);
            orderDao.update(id, order.getName(), order.getAge(), order.getBirthday());
            res.status(200);
            return gson.toJson(orderDao.findById(id));
        });

        delete("/orders/:id", "application/json", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            orderDao.deleteById(id);
            res.status(204);
            return "";
        });

        post("/kids/new", "application/json", (req, res) -> {
            Kid kid = gson.fromJson(req.body(), Kid.class);
            kidHelper.add(kid);
            res.status(201);
            return gson.toJson(kid);
        });

        get("/kids", "application/json", (req, res) -> {
            System.out.println(kidHelper.getAll());

            if(!kidHelper.getAll().isEmpty()){
                return gson.toJson(kidHelper.getAll());
            }

            else {
                return "{\"message\":\"I'm sorry, but no kids clothes are currently listed in the database.\"}";
            }
        });

        get("/kids/:id", "application/json", (req, res) -> { //accept a request in format JSON from an app
            int kidId = Integer.parseInt(req.params("id"));
            Kid kidToFind = kidHelper.findById(kidId);
            if (kidToFind == null){
                throw new ApiException(404, String.format("No tour with the id: \"%s\" exists", req.params("id")));
            }
            return gson.toJson(kidToFind);
        });

        post("/kids/:kidId/reviews/new", "application/json", (req, res) -> {
            int kidId = Integer.parseInt(req.params("kidId"));
            Review review = gson.fromJson(req.body(), Review.class);
            review.setCreatedAt(); //I am new!
            review.setFormattedCreatedAt();
            review.setKidId(kidId); //we need to set this separately because it comes from our route, not our JSON input.
            reviewHelper.add(review);
            res.status(201);
            return gson.toJson(review);
        });

        get("/kids/:id/reviews", "application/json", (req, res) -> {
            int kidId = Integer.parseInt(req.params("id"));

            Kid kidToFind = kidHelper.findById(kidId);
            List<Review> allReviews;

            if (kidToFind == null){
                throw new ApiException(404, String.format("No tour with the id: \"%s\" exists", req.params("id")));
            }

            allReviews = reviewHelper.getAllReviewsByKid(kidId);

            return gson.toJson(allReviews);
        });

        get("/kids/:id/sortedReviews", "application/json", (req, res) -> { //// TODO: 1/18/18 generalize this route so that it can be used to return either sorted reviews or unsorted ones.
            int kidId = Integer.parseInt(req.params("id"));
            Woman kidToFind = womanHelper.findById(kidId);
            List<Review> allReviews;
            if (kidToFind == null){
                throw new ApiException(404, String.format("No tour with the id: \"%s\" exists", req.params("id")));
            }
            allReviews = reviewHelper.getAllReviewsByKidSortedNewestToOldest(kidId);
            return gson.toJson(allReviews);
        });

        post("/men/new", "application/json", (req, res) -> {
            Man man = gson.fromJson(req.body(), Man.class);
            manHelper.add(man);
            res.status(201);
            return gson.toJson(man);
        });

        get("/men", "application/json", (req, res) -> {
            System.out.println(manHelper.getAll());

            if(!manHelper.getAll().isEmpty()){
                return gson.toJson(manHelper.getAll());
            }

            else {
                return "{\"message\":\"I'm sorry, but no tours are currently listed in the database.\"}";
            }
        });

        get("/men/:id", "application/json", (req, res) -> { //accept a request in format JSON from an app
            int manId = Integer.parseInt(req.params("id"));
            Man manToFind = manHelper.findById(manId);
            if (manToFind == null){
                throw new ApiException(404, String.format("No tour with the id: \"%s\" exists", req.params("id")));
            }
            return gson.toJson(manToFind);
        });

        post("/men/:manId/reviews/new", "application/json", (req, res) -> {
            int manId = Integer.parseInt(req.params("manId"));
            Review review = gson.fromJson(req.body(), Review.class);
            review.setCreatedAt(); //I am new!
            review.setFormattedCreatedAt();
            review.setManId(manId); //we need to set this separately because it comes from our route, not our JSON input.
            reviewHelper.add(review);
            res.status(201);
            return gson.toJson(review);
        });

        get("/men/:id/reviews", "application/json", (req, res) -> {
            int manId = Integer.parseInt(req.params("id"));

            Man manToFind = manHelper.findById(manId);
            List<Review> allReviews;

            if (manToFind == null){
                throw new ApiException(404, String.format("No tour with the id: \"%s\" exists", req.params("id")));
            }

            allReviews = reviewHelper.getAllReviewsByMan(manId);

            return gson.toJson(allReviews);
        });

        get("/men/:id/sortedReviews", "application/json", (req, res) -> { //// TODO: 1/18/18 generalize this route so that it can be used to return either sorted reviews or unsorted ones.
            int manId = Integer.parseInt(req.params("id"));
            Man manToFind = manHelper.findById(manId);
            List<Review> allReviews;
            if (manToFind == null){
                throw new ApiException(404, String.format("No tour with the id: \"%s\" exists", req.params("id")));
            }
            allReviews = reviewHelper.getAllReviewsByManSortedNewestToOldest(manId);
            return gson.toJson(allReviews);
        });

        post("/women/new", "application/json", (req, res) -> {
            Woman woman = gson.fromJson(req.body(), Woman.class);
            womanHelper.add(woman);
            res.status(201);
            return gson.toJson(woman);
        });

        get("/women", "application/json", (req, res) -> {
            System.out.println(womanHelper.getAll());

            if(!womanHelper.getAll().isEmpty()){
                return gson.toJson(womanHelper.getAll());
            }

            else {
                return "{\"message\":\"I'm sorry, but no women clothes are currently listed in the database.\"}";
            }
        });

        get("/women/:id", "application/json", (req, res) -> { //accept a request in format JSON from an app
            int womanId = Integer.parseInt(req.params("id"));
            Woman womanToFind = womanHelper.findById(womanId);
            if (womanToFind == null){
                throw new ApiException(404, String.format("No women clothe with the id: \"%s\" exists", req.params("id")));
            }
            return gson.toJson(womanToFind);
        });

        post("/women/:womanId/reviews/new", "application/json", (req, res) -> {
            int womanId = Integer.parseInt(req.params("womanId"));
            Review review = gson.fromJson(req.body(), Review.class);
            review.setCreatedAt(); //I am new!
            review.setFormattedCreatedAt();
            review.setWomanId(womanId); //we need to set this separately because it comes from our route, not our JSON input.
            reviewHelper.add(review);
            res.status(201);
            return gson.toJson(review);
        });

        get("/women/:id/reviews", "application/json", (req, res) -> {
            int womanId = Integer.parseInt(req.params("id"));

            Woman womanToFind = womanHelper.findById(womanId);
            List<Review> allReviews;

            if (womanToFind == null){
                throw new ApiException(404, String.format("No women clothe with the id: \"%s\" exists", req.params("id")));
            }

            allReviews = reviewHelper.getAllReviewsByWoman(womanId);

            return gson.toJson(allReviews);
        });

        get("/women/:id/sortedReviews", "application/json", (req, res) -> { //// TODO: 1/18/18 generalize this route so that it can be used to return either sorted reviews or unsorted ones.
            int womanId = Integer.parseInt(req.params("id"));
            Woman womanToFind = womanHelper.findById(womanId);
            List<Review> allReviews;
            if (womanToFind == null){
                throw new ApiException(404, String.format("No women clothe with the id: \"%s\" exists", req.params("id")));
            }
            allReviews = reviewHelper.getAllReviewsByWomanSortedNewestToOldest(womanId);
            return gson.toJson(allReviews);
        });

        post("/designers/new", "application/json", (req, res) -> {
            Designer designer = gson.fromJson(req.body(), Designer.class);
            designerHelper.add(designer);
            res.status(201);
            return gson.toJson(designer);
        });

        get("/designers", "application/json", (req, res) -> {
            System.out.println(designerHelper.getAll());

            if(!designerHelper.getAll().isEmpty()){
                return gson.toJson(designerHelper.getAll());
            }

            else {
                return "{\"message\":\"I'm sorry, but no designer clothes are currently listed in the database.\"}";
            }
        });

        get("/designers/:id", "application/json", (req, res) -> { //accept a request in format JSON from an app
            int designerId = Integer.parseInt(req.params("id"));
            Designer designerToFind = designerHelper.findById(designerId);
            if (designerToFind == null){
                throw new ApiException(404, String.format("No designer clothe with the id: \"%s\" exists", req.params("id")));
            }
            return gson.toJson(designerToFind);
        });

        post("/designers/:designerId/reviews/new", "application/json", (req, res) -> {
            int designerId = Integer.parseInt(req.params("designerId"));
            Review review = gson.fromJson(req.body(), Review.class);
            review.setCreatedAt(); //I am new!
            review.setFormattedCreatedAt();
            review.setDesignerId(designerId); //we need to set this separately because it comes from our route, not our JSON input.
            reviewHelper.add(review);
            res.status(201);
            return gson.toJson(review);
        });

        get("/designers/:id/reviews", "application/json", (req, res) -> {
            int designerId = Integer.parseInt(req.params("id"));

            Designer designerToFind = designerHelper.findById(designerId);
            List<Review> allReviews;

            if (designerToFind == null){
                throw new ApiException(404, String.format("No designer clothe review with the id: \"%s\" exists", req.params("id")));
            }

            allReviews = reviewHelper.getAllReviewsByDesigner(designerId);

            return gson.toJson(allReviews);
        });

        get("/designers/:id/sortedReviews", "application/json", (req, res) -> { //// TODO: 1/18/18 generalize this route so that it can be used to return either sorted reviews or unsorted ones.
            int designerId = Integer.parseInt(req.params("id"));
            Designer designerToFind = designerHelper.findById(designerId);
            List<Review> allReviews;
            if (designerToFind == null){
                throw new ApiException(404, String.format("No designer clothe review with the id: \"%s\" exists", req.params("id")));
            }
            allReviews = reviewHelper.getAllReviewsByDesignerSortedNewestToOldest(designerId);
            return gson.toJson(allReviews);
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

        after((req, res) -> {
            res.type("application/json");
        });

    }

    // Enables CORS on requests. This method is an initialization method and should be called once.
    private static void enableCORS() {

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
            response.header("Access-Control-Allow-Origin", "http://localhost:4567");
            response.header("Access-Control-Request-Method", "POST,GET,UPDATE,DELETE");
            response.header("Access-Control-Allow-Headers", "");
            response.type("application/json");
        });
    }

}
