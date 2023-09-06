import static spark.Spark.get;

public class App {
    public static void main(String[] args) {
        get("/hello", (request, response) -> {
            response.type("text/html");
            return "<html><body><h1>Hello Friend!</h1></body></html>";

        });
    }
}
