import com.google.gson.Gson;
import models.Beverages;
import models.Clothes;
import models.Tour;
import org.sql2o.Sql2o;
import sql2o.*;


import java.sql.Connection;

import static spark.Spark.get;
import static spark.Spark.post;

public class App {
    public static void main(String[] args) {
        get("/hello", (request, response) -> {
            response.type("text/html");
            return "<html><body><h1>Hello Friend!</h1></body></html>";

        });
        Sql2oBeverageDao beverageDao;
        Sql2oClotheDao clotheDao;
        Sql2oLinks links;
        Sql2oTourDao tourDao;
        Sql2oReviewDao reviewDao;
        Connection conn;
        Gson gson = new Gson();

        String connectionString = "";
        Sql2o  sql2o = new Sql2o(connectionString, "cheruiyot", "");

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

        get("/beverages", "application/json", (req, res) -> {
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
            res.type("application/json");
            return gson.toJson(tourDao.getAll());
        });
    }

}
