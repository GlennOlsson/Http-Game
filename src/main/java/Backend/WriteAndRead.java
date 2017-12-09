package Backend;

import com.google.gson.*;
import spark.Response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class WriteAndRead {
	
	private static String BOT_FILE = "Bots.json";
	private static String BOARD_FILE = "Board.json";
	
	/**
	 *
	 */
	public static Response newBot(String name, String email, Response response){
		try {
			String contentOfFile = getContentOfFile(BOT_FILE);
			JsonObject fullObject = JSON.parseStringToJSON(contentOfFile);
			JsonArray botsArray = fullObject.get("bots").getAsJsonArray();
			
			for(JsonElement jsonElement : botsArray) {
				JsonObject thisObject = jsonElement.getAsJsonObject();
				String thisEmail = thisObject.get("email").getAsString();
				String thisNickname = thisObject.get("nickname").getAsString();
				
				if(thisEmail.equals(email)) {
					//Already exists a bot with that email
					response.status(403);
					response.body("A bot with that email exist");
					return response;
				} else if(thisNickname.equals(name)) {
					response.status(403);
					response.body("There already exists a bot with that name. The bot must have an unique name");
					return response;
				}
			}
			//No bot with that email exist, so bot is being added
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-mm-yyyy HH:mm:ss");
			String currentDate = simpleDateFormat.format(calendar.getTime());
			
			String token = generateToken(name, email);
			int numberOfLoggin = 0;
			
			JsonObject newBotObject = new JsonObject();
			newBotObject.addProperty("token", token);
			newBotObject.addProperty("nickname", name);
			newBotObject.addProperty("email", email);
			newBotObject.addProperty("creation", currentDate);
			newBotObject.addProperty("loginCount", numberOfLoggin);
			
			botsArray.add(newBotObject);
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			
			saveToFile(gson.toJson(fullObject), BOT_FILE);
			
			response.status(200);
			response.body("Bot successfully created! Token: " + token);
			
			return response;
			
		}
		catch (IOException e){
			response.status(500);
			response.body("Caught IOException. Possibly because error in file reading. Contact moderator");
			return response;
		}
	}
	
	/**
	 * Generates a somewhat unique token that will be used for communication by
	 * the clients
	 * @param name the name that the clients wants
	 * @param email the email of the client
	 * @return a unique token
	 */
	private static String generateToken(String name, String email){
		long currentMillis = System.currentTimeMillis();
		//Removing everything but the last 5 characters, as they are almost random
		String millis = Long.toString(currentMillis).substring(8);
		
		if(email.length() < 5){
			email += "ABCDE";
		}
		
		if(email.length() > 5) {
			email = email.substring(0, 5);
		}
		
		String emailCharCodes = "";
		for (int i = 0; i < email.length(); i++) {
			Character thisCharacter = email.charAt(i);
			emailCharCodes += (int) thisCharacter;
		}
		
		if(name.length() < 5){
			name += "FGHIJ";
		}
		if(name.length() > 5){
			name = name.substring(0, 5);
		}
		
		String nameCharCodes = "";
		for (int i = 0; i < name.length(); i++) {
			Character thisCharacter = name.charAt(i);
			nameCharCodes += (int) thisCharacter;
		}
		
		String beforeConversion = millis + emailCharCodes + nameCharCodes;
		
		System.out.println(beforeConversion.length());
		
		//Charcodes between 35 and 123 are going to be used. Therefor,
		//taking 3 and 2 digits of the text, using modulo with 88 (123-35)
		//then adding 35, and finally converting those to characters will make an almost
		//unique token
		String token = "";
		
		//The length / 3 will give the amount of times it can divide by three (obv), and then
		//the final loop should use the remaining chars
		int lengthMod3 = 34/3;
		
		for (int i = 0; i < lengthMod3; i++) {
			String first3 = beforeConversion.substring(0, 3);
			int first3AsInt = Integer.parseInt(first3);
			first3AsInt %= 88;
			first3AsInt += 35;
			
			beforeConversion = beforeConversion.substring(3);
			
			token += (char) first3AsInt;
		}
		if(beforeConversion.length() > 0) {
			int finalPart = Integer.parseInt(beforeConversion);
			finalPart %= 88;
			finalPart += 35;
			
			token += (char) finalPart;
		}
		
		return token;
	}
//
//	/**
//	 * Used as a response for file editing. For example, if an error is caught,
//	 * the method should return the Error enum. A text can be added to the enum,
//	 * so the response can be evaluated. This is not a must, but is encouraged!
//	 * Use
//	 *  setText(String)
//	 * in that case
//	 */
//	public enum Response{
//		Success, Error, BotExists;
//
//		private String text;
//
//		/**
//		 * Set text of the enum, to for example describe the error
//		 * @param text the text that should be assigned
//		 */
//		public void setText(String text) {
//			this.text = text;
//		}
//
//		/**
//		 * Get the text of the enum, that has been set in the setText(String) method.
//		 * Will return an empty string if there has not been any text set
//		 * @return the text of the enum
//		 */
//		public String getText() {
//			return text;
//		}
//	}
	
	/**
	 * Returns the content of the file as a string, utilizing java.nio
	 * @param fileName the path of the file, relative to the root. Not absolute path
	 * @return the full content of the file
	 */
	private static String getContentOfFile(String fileName) throws IOException {
		byte[] fileInBytes = Files.readAllBytes(Paths.get(getPath() + fileName));
		String contentOfFile = new String(fileInBytes);
		
		return contentOfFile;
	}
	
	/**
	 * Tries to save the content parameter to the file with the file name
	 * @param content the content to save
	 * @param fileName the path of the file, relative to the root. Not absolute path
	 * @return true if success, false if not
	 */
	private static boolean saveToFile(String content, String fileName){
		try{
			Files.write(Paths.get(getPath() + fileName), content.toString().getBytes());
			return true;
		}
		catch (Exception e){
			return false;
		}
	}
	
	private static String getPath(){
		if(System.getProperty("os.name").toLowerCase().contains("linux")) {
			return "/NAS/NASDisk/Glenn/Http game/";
		}
		else if(System.getProperty("os.name").toLowerCase().contains("mac os x")) {
			return "/Volumes/NASDisk/Glenn/Http game/";
		}
		else {
			System.err.println("ERROR WITH OS NAME");
			return "";
		}
	}
}
