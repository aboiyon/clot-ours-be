import com.google.gson.Gson;
import models.Beverages;
import models.Clothes;
import models.Tours;
import org.sql2o.Sql2o;
import sql2o.Sql2oBeverages;
import sql2o.Sql2oClothes;
import sql2o.Sql2oLinks;
import sql2o.Sql2oTours;


import java.sql.Connection;

import static spark.Spark.get;
import static spark.Spark.post;

public class App {
    public static void main(String[] args) {
//        get("/hello", (request, response) -> {
//            response.type("text/html");
//            return "<html><body><h1>Hello Friend!</h1></body></html>";
//
//        });
        Sql2oBeverages beverages;
        Sql2oClothes clothes;
        Sql2oLinks links;
        Sql2oTours tours;
        Connection conn;
        Gson gson = new Gson();

        String connectionString = "";
        Sql2o  sql2o = new Sql2o(connectionString, "cheruiyot", "");

        beverages = new Sql2oBeverages();
        clothes = new Sql2oClothes();
        links = new Sql2oLinks();
        tours = new Sql2oTours();

        post("/beverage/new", "application/json", (req, res) -> {
            Beverages beverage = gson.fromJson(req.body(), Beverages.class);
            beverages.create(beverage);
            res.status(201);
            res.type("application/json");
            return gson.toJson(beverages);
        });

        get("/beverage", "application/json", (req, res) -> {
            res.type("application/json");
            return gson.toJson(beverages.findAll());
        });

        post("/clothe/new", "application/json", (req, res) -> {
            Clothes clothe = gson.fromJson(req.body(), Clothes.class);
            clothes.create(clothe);
            res.status(201);
            res.type("application/json");
            return gson.toJson(clothes);
        });

        get("/clothe", "application/json", (req, res) -> {
            res.type("application/json");
            return gson.toJson(clothes.findAll());
        });

        post("/tour/new", "application/json", (req, res) -> {
            Tours tour = gson.fromJson(req.body(), Tours.class);
            tours.create(tour);
            res.status(201);
            res.type("application/json");
            return gson.toJson(tours);
        });

        get("/tour", "application/json", (req, res) -> {
            res.type("application/json");
            return gson.toJson(tours.findAll());
        });
    }

}
