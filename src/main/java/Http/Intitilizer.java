package Http;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import static Backend.JSON.*;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import static spark.Spark.*;

public class Intitilizer {
	public Intitilizer(){
		
		port(8080);
		
		post("/bot", ((request, response) -> {
			JsonObject mainObject = parseStringToJSON(request.body());
			
			JsonElement helloVal = mainObject.get("hello");
			
			System.out.println(helloVal.toString());
			
			return helloVal.getAsString();
		}));
	}

	
}
