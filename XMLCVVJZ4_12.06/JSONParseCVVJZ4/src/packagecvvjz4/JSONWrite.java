package cvvjz4;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;

public class JSONWrite {
    public static void main(String[] args) {
    	 JSONParser JSONparser = new JSONParser();

         try (Reader reader = new FileReader("./orarendCVVJZ4.json")) {
            JSONObject JSONObject = new JSONObject();

            JSONArray oraArray = new JSONArray();
            oraArray.add(createOra(new String[]{"Fizika II.", "Dr. Pszota Gábor","Mérnökinformatikus","Hétfő","8:00","10:00","XXX. A1/305."}));
            oraArray.add(createOra(new String[]{"Fizika II.", "Nagy Ádám", "Mérnökinformatikus","Hétfő","10:00","12:00","A1/12."}));
            oraArray.add(createOra(new String[]{"WEB technológiák 1", "Agárdi Anita", "Mérnökinformatikus","Hétfő","14:00","16:00","XXX. előadó"}));
            oraArray.add(createOra(new String[]{"WEB technológiák 1", "Agárdi Anita", "Mérnökinformatikus","Hétfő","16:00","18:00","Inf/202"}));
            oraArray.add(createOra(new String[]{"Elektrotechnika-elektronika", "Szabó Norbert", "Mérnökinformatikus","Kedd","8:00","10:00","A1/316."}));
            oraArray.add(createOra(new String[]{"Adatkezelés XML-ben", "Dr. Bednarik László", "Mérnökinformatikus","Kedd","12:00","16:00","XXXII."}));
            oraArray.add(createOra(new String[]{"Adatkezelés XML-ben", "Dr. Bednarik László", "Mérnökinformatikus","Szerda","12:00","14:00","Inf/101"}));
            
            for (int i = 0; i < oraArray.size() ;i++) {
                JSONObject localObject = (JSONObject) oraArray.get(i);
                System.out.println("\nÓra");
                System.out.println("  Tárgy: " + localObject.get("targy"));
                System.out.println("  Oktató: " + localObject.get("oktato"));
                System.out.println("  Szak: " + localObject.get("szak"));
                System.out.println("  Időpont: ");

                JSONObject time = (JSONObject) localObject.get("idopont");
                System.out.println("    Nap: " + time.get("nap"));
                System.out.println("    Tól: " + time.get("tol"));
                System.out.println("    Ig: " + time.get("ig"));
                System.out.println("  Helyszín: " + localObject.get("helyszin"));
             }

            JSONObject oraObject = new JSONObject();
            oraObject.put("ora", oraArray);
            JSONObject.put("orarendCVVJZ4", oraObject);

            FileWriter file = new FileWriter("./orarendCVVJZ4.json");
            file.write(JSONObject.toString());
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static JSONObject createOra(String[] data) {
        JSONObject localObject = new JSONObject();

        localObject.put("targy", data[0]);
        localObject.put("oktato", data[1]);
        localObject.put("szak", data[2]);
        JSONObject timeObject = new JSONObject();
        timeObject.put("nap",data[3]);
        timeObject.put("tol",data[4]);
        timeObject.put("ig",data[5]);
        localObject.put("idopont",timeObject);
        localObject.put("helyszin", data[6]);

        return localObject;
    }
}
