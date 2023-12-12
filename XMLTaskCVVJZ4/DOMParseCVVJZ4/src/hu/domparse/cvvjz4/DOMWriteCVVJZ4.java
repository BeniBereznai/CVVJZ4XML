package hu.domparse.cvvjz4;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

import org.w3c.dom.*;

public class DOMWriteCVVJZ4 {
	public static void main(String[] args) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
            Element rootElement = document.createElement("Gyógyszerárusitás_CVVJZ4");
            rootElement.setAttribute("xmlns:xs", "http://www.w3.org/2001/XMLSchema-instance");
            rootElement.setAttribute("xs:noNamespaceSchemaLocation", "XMLSchemaCVVJZ4.xsd");
            document.appendChild(rootElement);

            generateGyogyszertarElements(document, rootElement);
            generateDolgozoElements(document, rootElement);
            generateGyogyszerkiadasElements(document, rootElement);
            generateGyogyszerElements(document, rootElement);
            generateBetegElements(document, rootElement);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{https://xml.apache.org/xslt}indent-amount", "4");

            // Print the generated document
            printDocument(document, "XMLCVVJZ41.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void generateGyogyszertarElements(Document document, Element rootElement) {
        List<List<String>> gyogyszertarData = Arrays.asList(
                Arrays.asList("1", "Alma Gyógyszertár", "alma.gyogyszertar@gmail.com", "06486443323", "Miskolc", "Csabai Kapu", "3"),
                Arrays.asList("2", "Rózsa Gyógyszertár", "rozsa.gyogyszertar@gmail.com", "06301234567", "Budapest", "Kossuth Lajos utca", "10"),
                Arrays.asList("3", "Tavaszi Gyógyszertár", "tavaszi.gyogyszertar@gmail.com", "06209876543", "Szeged", "Aradi vértanúk tere", "5"),
                Arrays.asList("4", "Napfény Gyógyszertár", "napfeny.gyogyszertar@gmail.com", "06525557890", "Debrecen", "Piac utca", "8"),
                Arrays.asList("5", "Szent András Gyógyszertár", "szent.andras.gyogyszertar@gmail.com", "067023331122", "Pécs", "Szent István tér", "12")
        );

        for (List<String> gyogyszertar : gyogyszertarData) {
            Element gyogyszertarElement = document.createElement("Gyógyszertár");
            gyogyszertarElement.setAttribute("GYID", gyogyszertar.get(0));

            addChildElement(document, gyogyszertarElement, "Név", gyogyszertar.get(1));
            addChildElement(document, gyogyszertarElement, "Email", gyogyszertar.get(2));
            addChildElement(document, gyogyszertarElement, "Telefonszám", gyogyszertar.get(3));

            Element cimElement = document.createElement("Cím");
            addChildElement(document, cimElement, "Város", gyogyszertar.get(4));
            addChildElement(document, cimElement, "Utca", gyogyszertar.get(5));
            addChildElement(document, cimElement, "Házszám", gyogyszertar.get(6));

            gyogyszertarElement.appendChild(cimElement);
            rootElement.appendChild(gyogyszertarElement);
        }
    }

    private static void generateDolgozoElements(Document document, Element rootElement) {
        List<List<String>> dolgozoData = Arrays.asList(
                Arrays.asList("11", "5", "Kis János", "1977", "8", "29", "Pécs", "Mókus utca", "3", "06703331322", "Janika.Kis@gmail.com"),
                Arrays.asList("12", "5", "Nagy Éva", "1985", "5", "15", "Pécs", "Mókus utca", "3", "06707654321", "EvaNagy@gmail.com"),
                Arrays.asList("13", "2", "Kiss Gábor", "1990", "11", "10", "Budapest", "Kossuth Lajos utca", "10", "06304567890", "gaborkiss@gmail.com"),
                Arrays.asList("14", "3", "Nagy Krisztina", "1982", "2", "5", "Szeged", "Aradi vértanúk tere", "5", "06205551111", "knagy@gmail.com"),
                Arrays.asList("15", "4", "Papp István", "1975", "6", "18", "Debrecen", "Piac utca", "8", "06501234567", "istvan.papp@gmail.com")
        );

        for (List<String> dolgozo : dolgozoData) {
            Element dolgozoElement = document.createElement("Dolgozó");
            dolgozoElement.setAttribute("DID", dolgozo.get(0));
            dolgozoElement.setAttribute("GYID", dolgozo.get(1));

            addChildElement(document, dolgozoElement, "Név", dolgozo.get(2));
            Element SzületésiDátumElement = document.createElement("SzületésiDátum");
            addChildElement(document, SzületésiDátumElement, "Év", dolgozo.get(3));
            addChildElement(document, SzületésiDátumElement, "Hónap", dolgozo.get(4));
            addChildElement(document, SzületésiDátumElement, "Nap", dolgozo.get(5));
            
            Element cimElement = document.createElement("Cím");
            addChildElement(document, cimElement, "Város", dolgozo.get(6));
            addChildElement(document, cimElement, "Utca", dolgozo.get(7));
            addChildElement(document, cimElement, "Házszám", dolgozo.get(8));

            dolgozoElement.appendChild(cimElement);
            addChildElement(document, dolgozoElement, "Telefonszám", dolgozo.get(9));
            addChildElement(document, dolgozoElement, "Email", dolgozo.get(10));

            rootElement.appendChild(dolgozoElement);
        }
    }

    private static void generateGyogyszerkiadasElements(Document document, Element rootElement) {
        List<List<String>> gyogyszerkiadasData = Arrays.asList(
                Arrays.asList("8751", "1", "3200", "Gyógyszerfutár kft", "3200", "0"),
                Arrays.asList("8322", "2", "2500", "Gyógyszerfutár kft", "2000", "500"),
                Arrays.asList("2753", "3", "2800", "Gyógyszerfutár kft", "0", "2800"),
                Arrays.asList("6354", "4", "1800", "Gyógyszerfutár kft", "1800", "0"),
                Arrays.asList("1755", "5", "3500", "GyógyszerExpress Kft", "3000", "500"),
                Arrays.asList("2756", "2", "4200", "MediCare Gyógyszerforgalmazás Zrt", "4000", "200"),
                Arrays.asList("1451", "3", "2900", "GyógyPatika Kft", "2500", "400"),
                Arrays.asList("3753", "5", "3800", "VitalGyógysz Kereskedelmi Bt", "3500", "300"),
                Arrays.asList("4444", "1", "2600", "GyógyPatent Kft", "2000", "600"),
                Arrays.asList("1234", "2", "3100", "MediBuy Gyógyszertár Kft", "3100", "0"),
                Arrays.asList("3333", "2", "2400", "Gyógyszertár 2000 Zrt", "2400", "0")
        );

        for (List<String> gyogyszerkiadas : gyogyszerkiadasData) {
            Element gyogyszerkiadasElement = document.createElement("Gyógyszerkiadás");
            gyogyszerkiadasElement.setAttribute("Kid", gyogyszerkiadas.get(0));
            gyogyszerkiadasElement.setAttribute("Tid", gyogyszerkiadas.get(1));

            addChildElement(document, gyogyszerkiadasElement, "Ár", gyogyszerkiadas.get(2));
            addChildElement(document, gyogyszerkiadasElement, "Forgalmazó", gyogyszerkiadas.get(3));

            Element fizetesiModElement = document.createElement("Fizetésimód");
            addChildElement(document, fizetesiModElement, "Készpénz", gyogyszerkiadas.get(4));
            addChildElement(document, fizetesiModElement, "Bankártya", gyogyszerkiadas.get(5));

            gyogyszerkiadasElement.appendChild(fizetesiModElement);

            rootElement.appendChild(gyogyszerkiadasElement);
        }
    }
    private static void generateGyogyszerElements(Document document, Element rootElement) {
        List<List<String>> gyogyszerData = Arrays.asList(
                Arrays.asList("12323", "2500", "Aszpirin", "Paracetamol", "true", "20 db", "Aspirin Plus"),
                Arrays.asList("12324", "1800", "Loratadin", "Cetirizin", "false", "10 db", "Cetirin"),
                Arrays.asList("12325", "3000", "Ranitidin", "Omeprazol", "true", "14 db", "RanitOme"),
                Arrays.asList("12326", "1200", "Simetikon", "Metilpolisziloxán", "false", "25 db", "Gas-X"),
                Arrays.asList("12327", "2700", "Diklofenák", "Naproxen", "true", "30 db", "PainAway"),
                Arrays.asList("12328", "2000", "Levocetirizin", "Fexofenadin", "false", "15 db", "LevoFex"),
                Arrays.asList("12329", "2800", "Enalapril", "Amlodipin", "true", "28 db", "EnalAmlod")
        );

        for (List<String> gyogyszer : gyogyszerData) {
            Element gyogyszerElement = document.createElement("Gyógyszer");
            gyogyszerElement.setAttribute("Gyid", gyogyszer.get(0));

            addChildElement(document, gyogyszerElement, "Ár", gyogyszer.get(1));

            for (int i = 2; i < gyogyszer.size(); i++) {
                addChildElement(document, gyogyszerElement, "Hatóanyag", gyogyszer.get(i));
            }

            addChildElement(document, gyogyszerElement, "Vényköteles", gyogyszer.get(4));
            addChildElement(document, gyogyszerElement, "Kiszerelés", gyogyszer.get(5));
            addChildElement(document, gyogyszerElement, "Név", gyogyszer.get(6));

            rootElement.appendChild(gyogyszerElement);
        }
    }

    private static void generateBetegElements(Document document, Element rootElement) {
        List<List<String>> betegData = Arrays.asList(
                Arrays.asList("53", "1234", "Kis Marika", "kicsikemarika@freemail.hu", "06201112244", "Debrecen", "Páva utca", "9"),
                Arrays.asList("54", "3333", "Nagy József", "nagyjozsef@gmail.com", "06105556677", "Budapest", "Kossuth utca", "5"),
                Arrays.asList("55", "4444", "Kovács Zsuzsanna", "kovacszsuzsi@gmail.com", "06303334455", "Szeged", "Ady utca", "10"),
                Arrays.asList("56", "3753", "Németh Péter", "nemethp@gmail.com", "06207778899", "Pécs", "Fő utca", "12"),
                Arrays.asList("57", "1451", "Tóth Éva", "totheva@gmail.com", "06306667788", "Budapest", "Bajnok utca", "20"),
                Arrays.asList("58", "2756", "Kovács János", "kovacsjanos@yahoo.com", "06204445566", "Miskolc", "Rákóczi utca", "6"),
                Arrays.asList("59", "8322", "Varga Éva", "vargaeva@gmail.com", "06305556677", "Pécs", "Széchenyi utca", "15"),
                Arrays.asList("60", "8751", "Kiss András", "kissandras@hotmail.com", "06109998877", "Szeged", "Petőfi utca", "4"),
                Arrays.asList("61", "2753", "Bakonyi Eszter", "bakonyieszter@gmail.com", "06201113366", "Debrecen", "Jókai utca", "8"),
                Arrays.asList("62", "6354", "Király Gábor", "kiralygabor@gmail.com", "06306667788", "Budapest", "Arany János utca", "11"),
                Arrays.asList("63", "1755", "Nagy Mária", "magdika@hotmail.com", "06105554433", "Miskolc", "Széchenyi utca", "18"),
                Arrays.asList("64", "1736", "Kertész József", "kerteszjozsi@gmail.com", "06307778899", "Pécs", "Arany János utca", "7")
        );

        for (List<String> beteg : betegData) {
            Element betegElement = document.createElement("Beteg");
            betegElement.setAttribute("Bid", beteg.get(0));
            betegElement.setAttribute("Kid", beteg.get(1));

            addChildElement(document, betegElement, "Név", beteg.get(2));
            addChildElement(document, betegElement, "Email", beteg.get(3));
            addChildElement(document, betegElement, "Telefonszám", beteg.get(4));

            Element lakcimElement = document.createElement("Lakcím");
            addChildElement(document, lakcimElement, "Város", beteg.get(5));
            addChildElement(document, lakcimElement, "Utca", beteg.get(6));
            addChildElement(document, lakcimElement, "Házszám", beteg.get(7));

            betegElement.appendChild(lakcimElement);
            rootElement.appendChild(betegElement);
        }
    }

    private static void generateKivantTermekElements(Document document, Element rootElement) {
        List<List<String>> kivantTermekData = Arrays.asList(
                Arrays.asList("123", "1736", "32", "2023.09.12"),
                Arrays.asList("124", "1755", "28", "2023.09.18"),
                Arrays.asList("125", "6354", "30", "2023.09.20"),
                Arrays.asList("126", "2756", "25", "2023.09.25"),
                Arrays.asList("127", "6322", "20", "2023.09.28"),
                Arrays.asList("128", "8751", "18", "2023.09.30")
        );

        for (List<String> kivantTermek : kivantTermekData) {
            Element kivantTermekElement = document.createElement("kívánttermék");
            kivantTermekElement.setAttribute("Gyid", kivantTermek.get(0));
            kivantTermekElement.setAttribute("Kid", kivantTermek.get(1));

            addChildElement(document, kivantTermekElement, "Árukészlet", kivantTermek.get(2));
            addChildElement(document, kivantTermekElement, "Beérkezés", kivantTermek.get(3));

            rootElement.appendChild(kivantTermekElement);
        }
    }

    // Add this function to your code
    private static void addChildElement(Document document, Element parentElement, String tagName, String textContent) {
        Element childElement = document.createElement(tagName);
        childElement.setTextContent(textContent);
        parentElement.appendChild(childElement);
    }
    private static void printDocument(Document document, String fileName) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{https://xml.apache.org/xslt}indent-amount", "4");

        // Output the XML document to the console or a file
        // In this example, I'll print it to the console
        transformer.transform(new DOMSource(document), new StreamResult(System.out));

        // Save the XML document to a file
        saveToFile(document, fileName);
    }
    private static void saveToFile(Document document, String fileName) throws Exception {
        try (OutputStream os = new FileOutputStream(new File(fileName));
             PrintWriter writer = new PrintWriter(os)) {

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{https://xml.apache.org/xslt}indent-amount", "4");

            // Transform the document and write it to the file
            transformer.transform(new DOMSource(document), new StreamResult(writer));
        }
    }

    }
