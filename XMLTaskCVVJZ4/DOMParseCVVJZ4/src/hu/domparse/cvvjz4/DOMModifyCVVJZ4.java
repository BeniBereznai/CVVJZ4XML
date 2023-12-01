
package hu.domparse.cvvjz4;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DOMModifyCVVJZ4 {

    public static void main(String[] args) {
        try {
            // XML fájl elérési útja
            String inputFilePath = "XMLCVVJZ4.xml";

            // XML fájl betöltése
            File xmlFile = new File(inputFilePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            // Kiszerelés módosítása
            modifyPackaging(doc, "12325", "14", "30");

            // Gyógyszertár módosítása
            modifyGyogyszertar(doc, "Miskolc", "99");

            // Dolgozók hónapjának módosítása
            modifyBirthMonth(doc, 1977, 10);

            // Gyógyszerárak módosítása
            modifyMedicinPrices(doc, 0.85);

            // Árukészlet módosítása
            modifyInventory(doc, "124", 120, 130, "2023.09.20");

            // Kiírás a konzolra a teljes fa struktúráról
            System.out.println("A módosítások utáni fa struktúra:");
            printDocument(doc.getDocumentElement(), "");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printDocument(Element element, String indent) {
        // Print the current element and its children
        System.out.println(indent + element.getTagName() + ": ");

        NodeList children = element.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                printDocument((Element) child, indent + "    ");
            } else if (child.getNodeType() == Node.TEXT_NODE) {
                String text = child.getTextContent().trim();
                if (!text.isEmpty()) {
                    System.out.println(indent + "  " + text);
                }
            }
        }
    }

    private static void modifyGyogyszertar(Document doc, String targetVaros, String newHazszam) {
        NodeList gyogyszertarList = doc.getElementsByTagName("Gyógyszertár");
        for (int i = 0; i < gyogyszertarList.getLength(); i++) {
            Element gyogyszertarElement = (Element) gyogyszertarList.item(i);

            // Ellenőrzés, hogy a város értéke "Miskolc"
            Element varosElement = (Element) gyogyszertarElement.getElementsByTagName("Város").item(0);
            if (varosElement.getTextContent().equals(targetVaros)) {
                // Gyógyszertár megtalálva, házszám módosítása
                Element hazszamElement = (Element) gyogyszertarElement.getElementsByTagName("Házszám").item(0);
                hazszamElement.setTextContent(newHazszam);
                break; // Kilépünk a ciklusból, mert már megtaláltuk a célt
            }
        }
    }

    private static void modifyBirthMonth(Document doc, int targetYear, int newMonth) {
        NodeList dolgozoList = doc.getElementsByTagName("Dolgozó");
        for (int i = 0; i < dolgozoList.getLength(); i++) {
            Element dolgozoElement = (Element) dolgozoList.item(i);

            // Ellenőrzés, hogy a születési év 1977
            Element szuletesiDatumElement = (Element) dolgozoElement.getElementsByTagName("SzületésiDátum").item(0);
            Element evElement = (Element) szuletesiDatumElement.getElementsByTagName("Év").item(0);
            if (Integer.parseInt(evElement.getTextContent()) == targetYear) {
                // Születési hónap módosítása
                Element honapElement = (Element) szuletesiDatumElement.getElementsByTagName("Hónap").item(0);
                honapElement.setTextContent(String.valueOf(newMonth));
            }
        }
    }

    private static void modifyPackaging(Document doc, String gyId, String targetPackaging, String newPackaging) {
        NodeList gyogyszerList = doc.getElementsByTagName("Gyógyszer");
        for (int i = 0; i < gyogyszerList.getLength(); i++) {
            Element gyogyszerElement = (Element) gyogyszerList.item(i);

            // Ellenőrzés, hogy a Gyid egyezik
            if (gyogyszerElement.getAttribute("Gyid").equals(gyId)) {
                // Kiszerelés módosítása
                Element kiszerelésElement = (Element) gyogyszerElement.getElementsByTagName("Kiszerelés").item(0);
                if (kiszerelésElement.getTextContent().equals(targetPackaging)) {
                    kiszerelésElement.setTextContent(newPackaging);
                }
            }
        }
    }

    private static void modifyMedicinPrices(Document doc, double discountPercentage) {
        NodeList gyogyszerList = doc.getElementsByTagName("Gyógyszer");
        for (int i = 0; i < gyogyszerList.getLength(); i++) {
            Element gyogyszerElement = (Element) gyogyszerList.item(i);

            // Ár lekérdezése és módosítása
            Element arElement = (Element) gyogyszerElement.getElementsByTagName("Ár").item(0);
            double currentPrice = Double.parseDouble(arElement.getTextContent());
            double discountedPrice = currentPrice * discountPercentage;

            // Módosított ár beállítása
            arElement.setTextContent(String.valueOf(discountedPrice));
        }
    }

    private static void modifyInventory(Document doc, String gyId, int lowerLimit, int upperLimit, String targetDate) {
        NodeList kivantTermekList = doc.getElementsByTagName("kívánttermék");
        for (int i = 0; i < kivantTermekList.getLength(); i++) {
            Element kivantTermekElement = (Element) kivantTermekList.item(i);

            // Ellenőrzés, hogy a Gyid egyezik
            if (kivantTermekElement.getAttribute("Gyid").equals(gyId)) {
                // Árukészlet lekérdezése és módosítása
                Element arukeszletElement = (Element) kivantTermekElement.getElementsByTagName("Árukészlet").item(0);
                int currentInventory = Integer.parseInt(arukeszletElement.getTextContent());

                // Beérkezés dátumának ellenőrzése
                Element beErkezesElement = (Element) kivantTermekElement.getElementsByTagName("Beérkezés").item(0);
                String currentDate = beErkezesElement.getTextContent();

                if (currentInventory >= lowerLimit && currentInventory <= upperLimit && currentDate.equals(targetDate)) {
                    // Módosított árukészlet beállítása
                    arukeszletElement.setTextContent(String.valueOf(currentInventory + 5));
                }
            }
        }
    }
}
