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
        ReviewHelper reviewHelper;
        KidHelper kidHelper;
        ManHelper manHelper;
        WomanHelper womanHelper;
        DesignerHelper designerHelper;
        Connection conn;
        Gson gson = new Gson();

        staticFileLocation("/public");
        String connectionString = "jdbc:postgresql://localhost:5432/utalii";
        Sql2o sql2o = new Sql2o(connectionString, "cheruiyot", "new_password");

        reviewHelper = new ReviewHelper(sql2o);
        kidHelper = new KidHelper(sql2o);
        manHelper = new ManHelper(sql2o);
        womanHelper = new WomanHelper(sql2o);
        designerHelper = new DesignerHelper(sql2o);

        enableCORS("*", "POST,GET", "");

        post("/kids/new", "application/json", (req, res) -> {
            Kid kid = gson.fromJson(req.body(), Kid.class);
            kidHelper.add(kid);
            res.status(201);
            return gson.toJson(kid);
        });

        get("/kids", "application/json", (req, res) -> {
            System.out.println(kidHelper.getAll());

            if(kidHelper.getAll().size() > 0){
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
                throw new ApiException(404, String.format("No kid clothe with the id: \"%s\" exists", req.params("id")));
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
                throw new ApiException(404, String.format("No kid clothe review with the id: \"%s\" exists", req.params("id")));
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

            if(manHelper.getAll().size() > 0){
                return gson.toJson(manHelper.getAll());
            }

            else {
                return "{\"message\":\"I'm sorry, but no men clothes are currently listed in the database.\"}";
            }
        });

        get("/men/:id", "application/json", (req, res) -> { //accept a request in format JSON from an app
            int manId = Integer.parseInt(req.params("id"));
            Man manToFind = manHelper.findById(manId);
            if (manToFind == null){
                throw new ApiException(404, String.format("No men clothe with the id: \"%s\" exists", req.params("id")));
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
                throw new ApiException(404, String.format("No men clothe review with the id: \"%s\" exists", req.params("id")));
            }

            allReviews = reviewHelper.getAllReviewsByMan(manId);

            return gson.toJson(allReviews);
        });

        get("/men/:id/sortedReviews", "application/json", (req, res) -> { //// TODO: 1/18/18 generalize this route so that it can be used to return either sorted reviews or unsorted ones.
            int manId = Integer.parseInt(req.params("id"));
            Man manToFind = manHelper.findById(manId);
            List<Review> allReviews;
            if (manToFind == null){
                throw new ApiException(404, String.format("No men clothe review with the id: \"%s\" exists", req.params("id")));
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

            if(womanHelper.getAll().size() > 0){
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
                throw new ApiException(404, String.format("No women clothe review with the id: \"%s\" exists", req.params("id")));
            }

            allReviews = reviewHelper.getAllReviewsByWoman(womanId);

            return gson.toJson(allReviews);
        });

        get("/women/:id/sortedReviews", "application/json", (req, res) -> { //// TODO: 1/18/18 generalize this route so that it can be used to return either sorted reviews or unsorted ones.
            int womanId = Integer.parseInt(req.params("id"));
            Woman womanToFind = womanHelper.findById(womanId);
            List<Review> allReviews;
            if (womanToFind == null){
                throw new ApiException(404, String.format("No women clothe review with the id: \"%s\" exists", req.params("id")));
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

            if(designerHelper.getAll().size() > 0){
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
