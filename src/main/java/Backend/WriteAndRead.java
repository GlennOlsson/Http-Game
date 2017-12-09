package Backend;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class WriteAndRead {
	
	private static String BOT_FILE = "Bots.json";
	private static String BOARD_FILE = "Board.json";
	
	/**
	 * Tries to add a new bot to the bot file. If there is a bot already existing
	 * with that e-mail, or another error occurs, the method will return false,
	 * otherwise it will return true
	 * @return true if successful, false if not
	 */
	public static Response newBot(String name, String email){
		try {
			String contentOfFile = getContentOfFile(BOT_FILE);
			JsonObject fullObject = JSON.parseStringToJSON(contentOfFile);
			
			
		}
		catch (IOException e){
			Response errorResponse = Response.Error;
			errorResponse.setText("Caught IOException. Possibly because error in file reading");
			return errorResponse;
		}
	}
	
	/**
	 * Used as a response for file editing. For example, if an error is caught,
	 * the method should return the Error enum. A text can be added to the enum,
	 * so the response can be evaluated. This is not a must, but is encouraged!
	 * Use
	 *  setText(String)
	 * in that case
	 */
	public enum Response{
		Success, Error, BotExists;
		
		private String text;
		
		/**
		 * Set text of the enum, to for example describe the error
		 * @param text the text that should be assigned
		 */
		public void setText(String text) {
			this.text = text;
		}
		
		/**
		 * Get the text of the enum, that has been set in the setText(String) method.
		 * Will return an empty string if there has not been any text set
		 * @return the text of the enum
		 */
		public String getText() {
			return text;
		}
	}
	
	/**
	 * Returns the content of the file as a string, utilizing java.nio
	 * @param fileName the name of the file. Do not include the full path!
	 * @return the full content of the file
	 */
	private static String getContentOfFile(String fileName) throws IOException {
		byte[] fileInBytes = Files.readAllBytes(Paths.get(getPath() + fileName));
		String contentOfFile = new String(fileInBytes);
		
		return contentOfFile;
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
