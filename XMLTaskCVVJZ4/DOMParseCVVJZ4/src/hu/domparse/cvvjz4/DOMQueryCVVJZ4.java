package hu.domparse.cvvjz4;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;

public class DOMQueryCVVJZ4 {
	// A fő metódus, ami meghívja a queryXMLDocument metódust a megadott XML fájllal
	public static void main(String[] args) {
		// Metódus meghívása	
		queryXMLDocument("./XMLCVVJZ4.xml");			
	}

	// Metódus, amely az XML fájl beolvasására és feldolgozására szolgál
	private static void queryXMLDocument(String filePath) {
		try {
			File xmlFile = new File("XMLCVVJZ4.xml");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = factory.newDocumentBuilder();
			Document document = dBuilder.parse(xmlFile);
			document.getDocumentElement().normalize();
			
			//1. Az összes Dolgozó adatainak kiírása
			queryXMLDolgozokALL(document,"Dolgozó");
			//2. Ár és forgalmazók kiírása
			queryXMLGyogyszerkiadasARForgalmazo(document,"Gyógyszerkiadás");
			//3. Magas árú gyógyszerek
			queryFilterHighPriceMedicines(document,"Gyógyszer", 1500);
			//4. Tid=3 gyógyszertári dolgozók neve és szül.dátum
			queryEmpsInGyogyszertar(document,"Dolgozó", 3);
			//5. Bankártyával is fizetett kiadások
			queryBankkartyasGyogyszerkiadasok(document,"Gyógyszerkiadás");
	                  		     
		} catch(Exception e) {
            e.printStackTrace();
        }
	}

	private static void queryXMLGyogyszerkiadasARForgalmazo(Document document, String elementName) {
	    System.out.println("--A-gyógyszerkiadások-ára-és-forgalmazója--");
	    NodeList gyogyszerkiadasNodeList = document.getElementsByTagName(elementName);

	    for (int i = 0; i < gyogyszerkiadasNodeList.getLength(); i++) {
	        Node gyogyszerkiadasNode = gyogyszerkiadasNodeList.item(i);

	        if (gyogyszerkiadasNode.getNodeType() == Node.ELEMENT_NODE && gyogyszerkiadasNode.getNodeName().equals("Gyógyszerkiadás")) {
	            // attribútum létrehozása és megadása
	            Element gyogyszerkiadasElem = (Element) gyogyszerkiadasNode;

	            // Ár adatainak kiolvasása
	            Node arNode = gyogyszerkiadasElem.getElementsByTagName("Ár").item(0);
	            String ar = arNode.getTextContent();
	            System.out.println("Ár: " + ar);

	            // Forgalmazó adatainak kiolvasása
	            Node forgalmazoNode = gyogyszerkiadasElem.getElementsByTagName("Forgalmazó").item(0);
	            String forgalmazo = forgalmazoNode.getTextContent();
	            System.out.println("Forgalmazó: " + forgalmazo);

	            System.out.println("------------------------------------------");
	        }
	    }
	}


	private static void queryFilterHighPriceMedicines(Document document, String elementName, int minPrice) {
	    System.out.println("--Magas árú gyógyszerek--");
	    NodeList gyogyszerNodeList = document.getElementsByTagName(elementName);

	    for (int i = 0; i < gyogyszerNodeList.getLength(); i++) {
	        Node gyogyszerNode = gyogyszerNodeList.item(i);

	        if (gyogyszerNode.getNodeType() == Node.ELEMENT_NODE && gyogyszerNode.getNodeName().equals("Gyógyszer")) {
	            Element gyogyszerElem = (Element) gyogyszerNode;
	            
	            // Ár adatainak kiolvasása
	            Node arNode = gyogyszerElem.getElementsByTagName("Ár").item(0);
	            int ar = Integer.parseInt(arNode.getTextContent());

	            if (ar >= minPrice) {
	                // Gyógyszer adatainak kiolvasása
	                String gyid = gyogyszerElem.getAttribute("Gyid");
	                String hatok = getHatoanyagok(gyogyszerElem);
	                String venykoteles = gyogyszerElem.getElementsByTagName("Vényköteles").item(0).getTextContent();
	                String kiszerelés = gyogyszerElem.getElementsByTagName("Kiszerelés").item(0).getTextContent();
	                String nev = gyogyszerElem.getElementsByTagName("Név").item(0).getTextContent();

	                // Kiírjuk az adatokat
	                System.out.println("Gyógyszer kódja: " + gyid);
	                System.out.println("Ár: " + ar);
	                System.out.println("Hatóanyagok: " + hatok);
	                System.out.println("Vényköteles: " + venykoteles);
	                System.out.println("Kiszerelés: " + kiszerelés);
	                System.out.println("Név: " + nev);
	                System.out.println("------------------------------------------");
	            }
	        }
	    }
	}

	private static String getHatoanyagok(Element gyogyszerElem) {
	    NodeList hatokNodeList = gyogyszerElem.getElementsByTagName("Hatóanyag");
	    StringBuilder hatok = new StringBuilder();

	    for (int i = 0; i < hatokNodeList.getLength(); i++) {
	        Node hatokNode = hatokNodeList.item(i);
	        hatok.append(hatokNode.getTextContent());

	        if (i < hatokNodeList.getLength() - 1) {
	            hatok.append(", ");
	        }
	    }

	    return hatok.toString();
	}


	private static void queryEmpsInGyogyszertar(Document document, String elementName, int tid) {
	    System.out.println("--Dolgozók a " + tid + " Gyógyszertárban--");
	    NodeList dolgozoNodeList = document.getElementsByTagName(elementName);

	    for (int i = 0; i < dolgozoNodeList.getLength(); i++) {
	        Node dolgozoNode = dolgozoNodeList.item(i);

	        if (dolgozoNode.getNodeType() == Node.ELEMENT_NODE && dolgozoNode.getNodeName().equals("Dolgozó")) {
	            Element dolgozoElem = (Element) dolgozoNode;

	            // Tid attribútum kiolvasása
	            String Tid = dolgozoElem.getAttribute("Tid");

	            if (tid==tid) {
	                // Dolgozó nevének és születési dátumának kiolvasása
	                String nev = dolgozoElem.getElementsByTagName("Név").item(0).getTextContent();
	                String szuletesiDatum = getFormattedBirthDate(dolgozoElem);

	                // Kiírjuk az adatokat
	                System.out.println("Dolgozó neve: " + nev);
	                System.out.println("Születési dátum: " + szuletesiDatum);
	                System.out.println("------------------------------------------");
	            }
	        }
	    }
	}

	private static String getFormattedBirthDate(Element dolgozoElem) {
	    String formattedDate = "";
	    Node szuletesiDatumNode = dolgozoElem.getElementsByTagName("SzületésiDátum").item(0);

	    if (szuletesiDatumNode != null && szuletesiDatumNode.getNodeType() == Node.ELEMENT_NODE) {
	        Element szuletesiDatumElem = (Element) szuletesiDatumNode;

	        String ev = szuletesiDatumElem.getElementsByTagName("Év").item(0).getTextContent();
	        String honap = szuletesiDatumElem.getElementsByTagName("Hónap").item(0).getTextContent();
	        String nap = szuletesiDatumElem.getElementsByTagName("Nap").item(0).getTextContent();

	        formattedDate = ev + "." + honap + "." + nap + ".";
	    }

	    return formattedDate;
	}

	private static void queryBankkartyasGyogyszerkiadasok(Document document, String elementName) {
	    System.out.println("--Bankkártyával fizetett gyógyszerkiadások--");

	    NodeList gyogyszerkiadasNodeList = document.getElementsByTagName("Gyógyszerkiadás");

	    for (int i = 0; i < gyogyszerkiadasNodeList.getLength(); i++) {
	        Node gyogyszerkiadasNode = gyogyszerkiadasNodeList.item(i);

	        if (gyogyszerkiadasNode.getNodeType() == Node.ELEMENT_NODE) {
	            Element gyogyszerkiadasElem = (Element) gyogyszerkiadasNode;

	            // Fizetési mód lekérése
	            Node fizetesimodNode = gyogyszerkiadasElem.getElementsByTagName("Fizetésimód").item(0);
	            if (fizetesimodNode != null && fizetesimodNode.getNodeType() == Node.ELEMENT_NODE) {
	                Element fizetesimodElem = (Element) fizetesimodNode;

	                // Bankártya ellenőrzése
	                NodeList bankkartyaNodeList = fizetesimodElem.getElementsByTagName("Bankártya");
	                if (bankkartyaNodeList != null && bankkartyaNodeList.getLength() > 0) {
	                    Node bankkartyaNode = bankkartyaNodeList.item(0);

	                    // Bankártya érték lekérése
	                    int bankkartyaErtek = Integer.parseInt(bankkartyaNode.getTextContent());

	                    // Ellenőrzés, hogy a bankártya értéke nagyobb, mint 0
	                    if (bankkartyaErtek > 0) {
	                        // Kiírás, ha bankártyával fizetettek
	                        System.out.println("Gyógyszerkiadás kódja: " + gyogyszerkiadasElem.getAttribute("Kid"));
	                        System.out.println("Gyógyszertár kódja: " + gyogyszerkiadasElem.getAttribute("Tid"));
	                        System.out.println("Ár: " + gyogyszerkiadasElem.getElementsByTagName("Ár").item(0).getTextContent());
	                        System.out.println("Forgalmazó: " + gyogyszerkiadasElem.getElementsByTagName("Forgalmazó").item(0).getTextContent());
	                        System.out.println("------------------------------------------");
	                    }
	                }
	            }
	        }
	    }
	}


	          
	    	
	private static void queryXMLDolgozokALL(Document document, String elementName) {
	    System.out.println("--Az-összes-dolgozó-adatainak-kiírása--");
	    NodeList dolgozoNodeList = document.getElementsByTagName(elementName);

	    for (int i = 0; i < dolgozoNodeList.getLength(); i++) {
	        Node dolgozoNode = dolgozoNodeList.item(i);

	        if (dolgozoNode.getNodeType() == Node.ELEMENT_NODE && dolgozoNode.getNodeName().equals("Dolgozó")) {
	            // attribútum létrehozása és megadása
	            Element dolgozoElem = (Element) dolgozoNode;
	            String did = dolgozoElem.getAttribute("Did");
	            String tid = dolgozoElem.getAttribute("Tid");
	            System.out.println("A dolgozó kódja: " + did);
	            System.out.println("A Gyógyszertár kódja: " + tid);

	            // Születési dátum adatainak kiolvasása
	            Node szuletesiDatumNode = dolgozoElem.getElementsByTagName("SzületésiDátum").item(0);
	            Element szuletesiDatumElem = (Element) szuletesiDatumNode;
	            String ev = szuletesiDatumElem.getElementsByTagName("Év").item(0).getTextContent();
	            String honap = szuletesiDatumElem.getElementsByTagName("Hónap").item(0).getTextContent();
	            String nap = szuletesiDatumElem.getElementsByTagName("Nap").item(0).getTextContent();
	            System.out.println("Születési dátum: " + ev + "." + honap + "." + nap);

	            // Lakcím adatainak kiolvasása
	            Node lakcimNode = dolgozoElem.getElementsByTagName("Lakcím").item(0);
	            Element lakcimElem = (Element) lakcimNode;
	            String varos = lakcimElem.getElementsByTagName("Város").item(0).getTextContent();
	            String utca = lakcimElem.getElementsByTagName("Utca").item(0).getTextContent();
	            String hazszam = lakcimElem.getElementsByTagName("Házszám").item(0).getTextContent();
	            System.out.println("Lakcím: " + varos + ", " + utca + " " + hazszam);

	            // Egyéb adatok kiolvasása
	            String telefonszam = dolgozoElem.getElementsByTagName("Telefonszám").item(0).getTextContent();
	            String email = dolgozoElem.getElementsByTagName("Email").item(0).getTextContent();
	            System.out.println("Telefonszám: " + telefonszam);
	            System.out.println("Email: " + email);

	            System.out.println("------------------------------------------");
	        }
	    }
	}
}