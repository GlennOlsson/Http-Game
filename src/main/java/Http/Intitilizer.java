package Http;

import Backend.WriteAndRead;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import static Backend.JSON.*;
import static Backend.WriteAndRead.*;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import spark.Response;

import static spark.Spark.*;

public class Intitilizer {
	public Intitilizer(){
		
		port(8080);
		
		post("/bot", ((request, response) -> {
			JsonObject mainObject = parseStringToJSON(request.body());
			
			String name = mainObject.get("name").getAsString();
			String email = mainObject.get("email").getAsString();
			
			if(name.length() == 0 || email.length() == 0){
				//At least one of the input values was empty, not OK!
				response.status(403);
				response.body("Invalid arguments. Must be in the form \n" +
						"{\n" +
						"  \"name\" : \"NAME_FOR_BOT\",\n" +
						"  \"email\" : \"YOUR_EMAIL\"" +
						"\n}");
			}
			else{
				response = newBot(name, email, response);
			}
			
			return response.status() + " : " + response.body();
		}));
	}

	
}
