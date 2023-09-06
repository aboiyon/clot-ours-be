import com.google.gson.Gson;
import sql2o.Sql2oBeverages;
import sql2o.Sql2oClothes;
import sql2o.Sql2oLinks;
import sql2o.Sql2oTours;


import java.sql.Connection;

import static spark.Spark.get;

public class App {
    public static void main(String[] args) {
        get("/hello", (request, response) -> {
            response.type("text/html");
            return "<html><body><h1>Hello Friend!</h1></body></html>";

        });
    }
    Sql2oBeverages departments;
    Sql2oClothes clothes;
    Sql2oLinks links;
    Sql2oTours tours;
    Connection conn;
    Gson gson = new Gson();
}
