package packagecvvjz4;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import java.nio.file.Files;
import java.nio.file.Paths;
public class JSONRead {
	    public static void main(String[] args) {
	        try {
	            // Beolvassuk az XML-t a fájlból
	            String xmlContent = new String(Files.readAllBytes(Paths.get("orarendNeptunkod.xml")));

	            // Átalakítjuk az XML-t JSON formátummá
	            JSONObject jsonObject = XML.toJSONObject(xmlContent);

	            // Kinyerjük az "ora" elemeket
	            JSONArray oraArray = jsonObject.getJSONObject("CVVJZ4_orarend").getJSONArray("ora");

	            // Kiírjuk a strukturált formátumban
	            for (int i = 0; i < oraArray.length(); i++) {
	                JSONObject ora = oraArray.getJSONObject(i);
	                System.out.println("Mező: " + ora.getString("ora") + ", Érték: " + ora.toString());
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}
