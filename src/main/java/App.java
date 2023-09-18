import com.google.gson.Gson;
import models.Beverages;
import models.Clothes;
import models.Tours;
import org.sql2o.Sql2o;
import sql2o.Sql2oBeverageDao;
import sql2o.Sql2oClotheDao;
import sql2o.Sql2oLinks;
import sql2o.Sql2oTourDao;


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
            beverageDao.create(beverage);
            res.status(201);
            res.type("application/json");
            return gson.toJson(beverage);
        });

        get("/beverages", "application/json", (req, res) -> {
            res.type("application/json");
            return gson.toJson(beverageDao.findAll());
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
            Tours tour = gson.fromJson(req.body(), Tours.class);
            tourDao.create(tour);
            res.status(201);
            res.type("application/json");
            return gson.toJson(tourDao);
        });

        get("/tours", "application/json", (req, res) -> {
            res.type("application/json");
            return gson.toJson(tourDao.findAll());
        });
    }

}
