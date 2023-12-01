package hu.domparse.cvvjz4;

import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class DOMWriteCVVJZ4 {

    public static void main(String[] args) {
        try {
            // XML fájl elérési útja
        	String inputFilePath = "XMLCVVJZ4.xml";
            String outputinputFilePath = "XMLCVVJZ41.xml";
            // XML fájl betöltése
            File xmlFile = new File(inputFilePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            // Gyógyszertár elem kiválasztása az attribútum alapján
            String gyogyszertarId = "3";
            Element gyogyszertarElement = getGyogyszertarElementById(doc, gyogyszertarId);

            // Új név beállítása
            String ujNev = "Benu gyógyszertár";
            setGyogyszertarNev(gyogyszertarElement, ujNev);
            // Új Város beállítása
            modifyCityForDolgozo(doc, "5", "Zalakaros");
            // Ha a beérkezés dátuma 09.24-29 módosítsa
            modifyBeErkezesDate(doc, "2023.09.24", "2023.09.29", "2023.09.27");
            // Hatóanyag módosítás
            modifyKiszerelésForAszpirin(doc, "Aszpirin", "60 db");
            //Csak bankártya
            processPayment(doc);
            // Módosított dokumentum mentése
            saveDocument(doc, outputinputFilePath);
            
            System.out.println("A módosítás sikeresen végrehajtva.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Gyógyszertár elem kiválasztása az ID alapján
    private static Element getGyogyszertarElementById(Document doc, String gyid) {
        NodeList gyogyszertarList = doc.getElementsByTagName("Gyógyszertár");
        for (int i = 0; i < gyogyszertarList.getLength(); i++) {
            Element gyogyszertarElement = (Element) gyogyszertarList.item(i);
            String id = gyogyszertarElement.getAttribute("GYID");
            if (id.equals(gyid)) {
                return gyogyszertarElement;
            }
        }
        return null;
    }

    // Gyógyszertár név módosítása
    private static void setGyogyszertarNev(Element gyogyszertarElement, String ujNev) {
        NodeList nevList = gyogyszertarElement.getElementsByTagName("Név");
        if (nevList.getLength() > 0) {
            Element nevElement = (Element) nevList.item(0);
            nevElement.setTextContent(ujNev);
        }
    }
    //Dolgozó módosítása
    private static void modifyCityForDolgozo(Document doc, String tid, String ujVaros) {
        NodeList dolgozoList = doc.getElementsByTagName("Dolgozó");
        for (int i = 0; i < dolgozoList.getLength(); i++) {
            Element dolgozoElement = (Element) dolgozoList.item(i);
            String dolgozoTid = dolgozoElement.getAttribute("Tid");
            if (dolgozoTid.equals(tid)) {
                // A Dolgozó elem megtalálva, város érték módosítása
                Element lakcimElement = (Element) dolgozoElement.getElementsByTagName("Lakcím").item(0);
                Element varosElement = (Element) lakcimElement.getElementsByTagName("Város").item(0);
                varosElement.setTextContent(ujVaros);
            }
        }
    }
    // Dátum módosítás
    private static void modifyBeErkezesDate(Document doc, String fromDate, String toDate, String newDate) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        Date from = dateFormat.parse(fromDate);
        Date to = dateFormat.parse(toDate);
        Date newDateValue = dateFormat.parse(newDate);

        NodeList kivantTermekList = doc.getElementsByTagName("kívánttermék");
        for (int i = 0; i < kivantTermekList.getLength(); i++) {
            Element kivantTermekElement = (Element) kivantTermekList.item(i);
            Element beErkezesElement = (Element) kivantTermekElement.getElementsByTagName("Beérkezés").item(0);

            Date currentDate = dateFormat.parse(beErkezesElement.getTextContent());
            if (currentDate.compareTo(from) >= 0 && currentDate.compareTo(to) <= 0) {
                beErkezesElement.setTextContent(dateFormat.format(newDateValue));
            }
        }
    }
 // Kiszerelés módosítása az aszpirint tartalmazó gyógyszerek esetén
    private static void modifyKiszerelésForAszpirin(Document doc, String hatóanyag, String newKiszerelés) {
        NodeList gyogyszerList = doc.getElementsByTagName("Gyógyszer");
        for (int i = 0; i < gyogyszerList.getLength(); i++) {
            Element gyogyszerElement = (Element) gyogyszerList.item(i);

            // Ellenőrzés, hogy a gyógyszer tartalmazza-e az aszpirint
            NodeList hatóanyagList = gyogyszerElement.getElementsByTagName("Hatóanyag");
            for (int j = 0; j < hatóanyagList.getLength(); j++) {
                String currentHatóanyag = hatóanyagList.item(j).getTextContent();
                if (currentHatóanyag.equals(hatóanyag)) {
                    // Az aszpirint tartalmazó gyógyszer esetén a kiszerelés módosítása
                    Element kiszerelésElement = (Element) gyogyszerElement.getElementsByTagName("Kiszerelés").item(0);
                    kiszerelésElement.setTextContent(newKiszerelés);
                    break; // Kilépünk a belső ciklusból, mivel már megtaláltuk az aszpirint
                }
            }
        }
    }
    private static void processPayment(Document doc) {
        NodeList gyogyszerkiadasList = doc.getElementsByTagName("Gyógyszerkiadás");
        for (int i = 0; i < gyogyszerkiadasList.getLength(); i++) {
            Element gyogyszerkiadasElement = (Element) gyogyszerkiadasList.item(i);
            String currentKid = gyogyszerkiadasElement.getAttribute("Kid");

            
                Element arElement = (Element) gyogyszerkiadasElement.getElementsByTagName("Ár").item(0);
                Element fizetesModElement = (Element) gyogyszerkiadasElement.getElementsByTagName("Fizetésimód").item(0);
                Element keszpenzElement = (Element) fizetesModElement.getElementsByTagName("Készpénz").item(0);
                Element bankkartyaElement = (Element) fizetesModElement.getElementsByTagName("Bankártya").item(0);

                int ar = Integer.parseInt(arElement.getTextContent());
                int keszpenz = Integer.parseInt(keszpenzElement.getTextContent());
                int bankkartya = Integer.parseInt(bankkartyaElement.getTextContent());

                // Ellenőrzés, hogy mindkét fizetési mód értéke nagyobb mint 0
                if (keszpenz > 0 && bankkartya > 0) {
                    // Készpénz kinullázása, és az ár értékének beírása a bankártya értékéhez
                    keszpenzElement.setTextContent("0");
                    bankkartyaElement.setTextContent(String.valueOf(ar));
                }
            }
        }


    // Módosított dokumentum mentése
    private static void saveDocument(Document doc, String outputinputFilePath) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");

        // Módosított dokumentum mentése a fájlba
        FileOutputStream fileOutputStream = new FileOutputStream(outputinputFilePath);
        transformer.transform(new DOMSource(doc), new StreamResult(fileOutputStream));
        fileOutputStream.close();
    }
}