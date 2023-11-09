import com.google.gson.Gson;
import exceptions.ApiException;
import models.*;
import org.sql2o.Sql2o;
import sql2o.*;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        Sql2oBeverageDao beverageDao;
        Sql2oClotheDao clotheDao;
        Sql2oLinks links;
        Sql2oTourDao tourDao;
        ReviewHelper reviewDao;
        KidHelper kidService;
        WomanHelper womanService;
        Connection conn;
        Gson gson = new Gson();

        staticFileLocation("/public");
        String connectionString = "jdbc:postgresql://localhost:5432/utalii";
        Sql2o sql2o = new Sql2o(connectionString, "cheruiyot", "new_password");

        beverageDao = new Sql2oBeverageDao(sql2o);
        clotheDao = new Sql2oClotheDao(sql2o);
        links = new Sql2oLinks();
        tourDao = new Sql2oTourDao(sql2o);
        reviewDao = new ReviewHelper(sql2o);
        kidService = new KidHelper(sql2o);

        enableCORS("*", "POST,GET", "");

        post("/beverages/new", "application/json", (req, res) -> {
            Beverages beverage = gson.fromJson(req.body(), Beverages.class);
            beverageDao.add(beverage);
            res.status(201);
            res.type("application/json");
            return gson.toJson(beverage);
        });

        get("/beverages/", "application/json", (req, res) -> {
            res.type("application/json");
            return gson.toJson(beverageDao.getAll());
        });

        post("/clothes/new", "application/json", (req, res) -> {
            Clothes clothe = gson.fromJson(req.body(), Clothes.class);
            clotheDao.add(clothe);
            res.status(201);
            return gson.toJson(clothe);
        });

        get("/clothes", "application/json", (req, res) -> {
            res.type("application/json");
            return gson.toJson(clotheDao.getAll());
        });

        get("/clothes/:id", "application/json", (req, res) -> { //accept a request in format JSON from an app
            int clotheId = Integer.parseInt(req.params("id"));
            Clothes clotheToFind = clotheDao.findById(clotheId);
            if (clotheToFind == null){
                throw new ApiException(404, String.format("No clothe with the id: \"%s\" exists", req.params("id")));
            }
            return gson.toJson(clotheToFind);
        });

        post("/tours/new", "application/json", (req, res) -> {
            Tour tour = gson.fromJson(req.body(), Tour.class);
            tourDao.add(tour);
            res.status(201);
            return gson.toJson(tour);
        });

        get("/tours", "application/json", (req, res) -> {
            System.out.println(tourDao.getAll());

            if(tourDao.getAll().size() > 0){
                return gson.toJson(tourDao.getAll());
            }

            else {
                return "{\"message\":\"I'm sorry, but no tours are currently listed in the database.\"}";
            }
        });

        get("/tours/:id", "application/json", (req, res) -> { //accept a request in format JSON from an app
            int tourId = Integer.parseInt(req.params("id"));
            Tour tourToFind = tourDao.findById(tourId);
            if (tourToFind == null){
                throw new ApiException(404, String.format("No tour with the id: \"%s\" exists", req.params("id")));
            }
            return gson.toJson(tourToFind);
        });

        post("/tours/:tourId/reviews/new", "application/json", (req, res) -> {
            int tourId = Integer.parseInt(req.params("tourId"));
            Review review = gson.fromJson(req.body(), Review.class);
            review.setCreatedAt(); //I am new!
            review.setFormattedCreatedAt();
            review.setKidId(tourId); //we need to set this separately because it comes from our route, not our JSON input.
            reviewDao.add(review);
            res.status(201);
            return gson.toJson(review);
        });

        get("/tours/:id/reviews", "application/json", (req, res) -> {
            int tourId = Integer.parseInt(req.params("id"));

            Tour tourToFind = tourDao.findById(tourId);
            List<Review> allReviews;

            if (tourToFind == null){
                throw new ApiException(404, String.format("No tour with the id: \"%s\" exists", req.params("id")));
            }

            allReviews = reviewDao.getAllReviewsByKid(tourId);

            return gson.toJson(allReviews);
        });

        get("/tours/:id/sortedReviews", "application/json", (req, res) -> { //// TODO: 1/18/18 generalize this route so that it can be used to return either sorted reviews or unsorted ones.
            int tourId = Integer.parseInt(req.params("id"));
            Tour tourToFind = tourDao.findById(tourId);
            List<Review> allReviews;
            if (tourToFind == null){
                throw new ApiException(404, String.format("No tour with the id: \"%s\" exists", req.params("id")));
            }
            allReviews = reviewDao.getAllReviewsByKidSortedNewestToOldest(tourId);
            return gson.toJson(allReviews);
        });

        post("/kids/new", "application/json", (req, res) -> {
            Kid kid = gson.fromJson(req.body(), Kid.class);
            kidService.add(kid);
            res.status(201);
            return gson.toJson(kid);
        });

        get("/kids", "application/json", (req, res) -> {
            System.out.println(kidService.getAll());

            if(kidService.getAll().size() > 0){
                return gson.toJson(kidService.getAll());
            }

            else {
                return "{\"message\":\"I'm sorry, but no kids clothes are currently listed in the database.\"}";
            }
        });

        get("/kids/:id", "application/json", (req, res) -> { //accept a request in format JSON from an app
            int kidId = Integer.parseInt(req.params("id"));
            Kid kidToFind = kidService.findById(kidId);
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
            reviewDao.add(review);
            res.status(201);
            return gson.toJson(review);
        });

        get("/kids/:id/reviews", "application/json", (req, res) -> {
            int kidId = Integer.parseInt(req.params("id"));

            Kid kidToFind = kidService.findById(kidId);
            List<Review> allReviews;

            if (kidToFind == null){
                throw new ApiException(404, String.format("No tour with the id: \"%s\" exists", req.params("id")));
            }

            allReviews = reviewDao.getAllReviewsByKid(kidId);

            return gson.toJson(allReviews);
        });

        get("/kids/:id/sortedReviews", "application/json", (req, res) -> { //// TODO: 1/18/18 generalize this route so that it can be used to return either sorted reviews or unsorted ones.
            int tourId = Integer.parseInt(req.params("id"));
            Tour tourToFind = tourDao.findById(tourId);
            List<Review> allReviews;
            if (tourToFind == null){
                throw new ApiException(404, String.format("No tour with the id: \"%s\" exists", req.params("id")));
            }
            allReviews = reviewDao.getAllReviewsByKidSortedNewestToOldest(tourId);
            return gson.toJson(allReviews);
        });

        
        //FILTERS
        exception(ApiException.class, (exception, req, res) -> {
            ApiException err = exception;
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("status", err.getStatusCode());
            jsonMap.put("errorMessage", err.getMessage());
            res.type("application/json");
            res.status(err.getStatusCode());
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
