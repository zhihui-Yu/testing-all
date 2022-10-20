import com.google.gson.Gson;

import java.util.Map;

import static spark.Spark.get;

/**
 * @author simple
 */
public class Server {
    private static Gson gson = new Gson();

    public static void main(String[] args) {
        // see spark.Spark
        get("/customer", (req, res) -> {
            return  gson.toJson(Map.of("user_name","zhangsan"));
        });

    }
}
