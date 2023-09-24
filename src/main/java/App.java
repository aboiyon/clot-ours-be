import com.google.gson.Gson;
import exceptions.ApiException;
import models.Beverages;
import models.Clothes;
import models.Tour;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import spark.Spark;
import sql2o.*;


import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
//        get("/hello", (request, response) -> {
//            response.type("text/html");
//            return "<html><body><h1>Hello Friend!</h1></body></html>";
//
//        });
        Sql2oBeverageDao beverageDao;
        Sql2oClotheDao clotheDao;
        Sql2oLinks links;
        Sql2oTourDao tourDao;
        Sql2oReviewDao reviewDao;
        Connection conn;
        Gson gson = new Gson();

        staticFileLocation("/public");
        String connectionString = "jdbc:postgresql://localhost:5432/utalii";
        Sql2o  sql2o = new Sql2o(connectionString, "cheruiyot", "new_password");

        beverageDao = new Sql2oBeverageDao(sql2o);
        clotheDao = new Sql2oClotheDao(sql2o);
        links = new Sql2oLinks();
        tourDao = new Sql2oTourDao(sql2o);

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
            clotheDao.create(clothe);
            res.status(201);
            res.type("application/json");
            return gson.toJson(clotheDao);
        });

        get("/clothes", "application/json", (req, res) -> {
            res.type("application/json");
            return gson.toJson(clotheDao.findAll());
        });

        post("/tours/new", "application/json", (req, res) -> {
            Tour tour = gson.fromJson(req.body(), Tour.class);
            tourDao.add(tour);
            res.status(201);
            res.type("application/json");
            return gson.toJson(tourDao);
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
                throw new ApiException(404, String.format("No restaurant with the id: \"%s\" exists", req.params("id")));
            }
            return gson.toJson(tourToFind);
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

}
