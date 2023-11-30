package hu.domparse.cvvjz4;

import org.w3c.dom.*;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class DOMReadCVVJZ4 {
	 // A fő metódus, ami meghívja a readXmlDocument metódust a megadott XML fájllal
	 public static void main(String[] args) {
		 readXMLDocument("./XMLCVVJZ4.xml");
	 }

	 // Metódus, amely az XML fájl beolvasására és feldolgozására szolgál
	 private static void readXMLDocument(String filePath) {	 
		try { 
			File xmlFile = new File("XMLCVVJZ4.xml");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = factory.newDocumentBuilder();
			Document document = dBuilder.parse(xmlFile);
			document.getDocumentElement().normalize();
			
			// Kiírjuk a gyökérelem nevét a konzolra
			System.out.println("Gyökérelem: " + document.getDocumentElement().getNodeName());

			// XML elemek feldolgozása külön metódusokkal
			readXMLGyogyszertarElement(document, "Gyógyszertár");
			readXMLDolgozoElement(document, "Dolgozó");
			readXMLGyogyszerkiadasElement(document, "Gyógyszerkiadás");
			readXMLGyogyszerElement(document, "Gyógyszer");
			readXMLBetegElement(document, "Beteg");
			readXMLKivantTermekElement(document, "kívánttermék");
		} catch(Exception e) {
			 e.printStackTrace();
		}		
	}
	
	 private static void readXMLGyogyszertarElement(Document document, String elementName) {
		    System.out.println("\n-----Gyógyszertár-ELEM-----");
		    NodeList gyogyszertarNodeList = document.getElementsByTagName(elementName);

		    for (int i = 0; i < gyogyszertarNodeList.getLength(); i++) {
		        Node gyogyszertarNode = gyogyszertarNodeList.item(i);
		        System.out.println("\nJelenlegi elem: " + gyogyszertarNode.getNodeName());

		        // Ellenőrzi, hogy a Node egy ELEMENT_NODE típusú-e
		        if (gyogyszertarNode.getNodeType() == Node.ELEMENT_NODE) {
		            Element gyogyszertarElem = (Element) gyogyszertarNode;

		            // attribútumok létrehozása és megadása
		            String gyid = gyogyszertarElem.getAttribute("GYID");
		            String nev = gyogyszertarElem.getElementsByTagName("Név").item(0).getTextContent();
		            String email = gyogyszertarElem.getElementsByTagName("Email").item(0).getTextContent();
		            String telefonszam = gyogyszertarElem.getElementsByTagName("Telefonszám").item(0).getTextContent();

		            Node cimNode = gyogyszertarElem.getElementsByTagName("Cím").item(0);
		            Element cimElem = (Element) cimNode;
		            String varos = cimElem.getElementsByTagName("Város").item(0).getTextContent();
		            String utca = cimElem.getElementsByTagName("Utca").item(0).getTextContent();
		            String hazszam = cimElem.getElementsByTagName("Házszám").item(0).getTextContent();

		            // Kiíjuk a konzolra az attribútumokat és az elemeket
		            System.out.println("GYID: " + gyid);
		            System.out.println("Név: " + nev);
		            System.out.println("Email: " + email);
		            System.out.println("Telefonszám: " + telefonszam);
		            System.out.println("Város: " + varos);
		            System.out.println("Utca: " + utca);
		            System.out.println("Házszám: " + hazszam);

		            System.out.println("-----------------------------");
		        }
		    }
		}

	 private static void readXMLDolgozoElement(Document document, String elementName) {
		    System.out.println("\n-----Dolgozó-ELEM-----");
		    NodeList dolgozoNodeList = document.getElementsByTagName(elementName);

		    for (int i = 0; i < dolgozoNodeList.getLength(); i++) {
		        Node dolgozoNode = dolgozoNodeList.item(i);
		        System.out.println("\nJelenlegi elem: " + dolgozoNode.getNodeName());

		        // Ellenőrzi, hogy a Node egy ELEMENT_NODE típusú-e
		        if (dolgozoNode.getNodeType() == Node.ELEMENT_NODE) {
		            Element dolgozoElem = (Element) dolgozoNode;

		            // attribútumok létrehozása és megadása
		            String did = dolgozoElem.getAttribute("Did");
		            String tid = dolgozoElem.getAttribute("Tid");
		            String nev = dolgozoElem.getElementsByTagName("Név").item(0).getTextContent();

		            // SzületésiDátum elem feldolgozása
		            Element szuletesiDatumElem = (Element) dolgozoElem.getElementsByTagName("SzületésiDátum").item(0);
		            String ev = szuletesiDatumElem.getElementsByTagName("Év").item(0).getTextContent();
		            String honap = szuletesiDatumElem.getElementsByTagName("Hónap").item(0).getTextContent();
		            String nap = szuletesiDatumElem.getElementsByTagName("Nap").item(0).getTextContent();

		            // Lakcím elem feldolgozása
		            Element lakcimElem = (Element) dolgozoElem.getElementsByTagName("Lakcím").item(0);
		            String varos = lakcimElem.getElementsByTagName("Város").item(0).getTextContent();
		            String utca = lakcimElem.getElementsByTagName("Utca").item(0).getTextContent();
		            String hazszam = lakcimElem.getElementsByTagName("Házszám").item(0).getTextContent();

		            // Egyéb adatok feldolgozása
		            String telefonszam = dolgozoElem.getElementsByTagName("Telefonszám").item(0).getTextContent();
		            String email = dolgozoElem.getElementsByTagName("Email").item(0).getTextContent();

		            // Kiíjuk a konzolra az attribútumokat és az elemeket
		            System.out.println("Did: " + did);
		            System.out.println("Tid: " + tid);
		            System.out.println("Név: " + nev);
		            System.out.println("Születési dátum: " + ev + "-" + honap + "-" + nap);
		            System.out.println("Város: " + varos);
		            System.out.println("Utca: " + utca);
		            System.out.println("Házszám: " + hazszam);
		            System.out.println("Telefonszám: " + telefonszam);
		            System.out.println("Email: " + email);
		            System.out.println("-----------------------------");
		        }
		    }
		}
	
	 private static void readXMLGyogyszerkiadasElement(Document document, String elementName) {
		    System.out.println("\n-----Gyógyszerkiadás-ELEM-----");
		    NodeList gyogyszerkiadasNodeList = document.getElementsByTagName(elementName);

		    for (int i = 0; i < gyogyszerkiadasNodeList.getLength(); i++) {
		        Node gyogyszerkiadasNode = gyogyszerkiadasNodeList.item(i);
		        System.out.println("\nJelenlegi elem: " + gyogyszerkiadasNode.getNodeName());

		        // Ellenőrzi, hogy a Node egy ELEMENT_NODE típusú-e
		        if (gyogyszerkiadasNode.getNodeType() == Node.ELEMENT_NODE) {
		            Element gyogyszerkiadasElem = (Element) gyogyszerkiadasNode;

		            // attribútumok létrehozása és megadása
		            String kid = gyogyszerkiadasElem.getAttribute("Kid");
		            String tid = gyogyszerkiadasElem.getAttribute("Tid");
		            String ar = gyogyszerkiadasElem.getElementsByTagName("Ár").item(0).getTextContent();
		            String forgalmazo = gyogyszerkiadasElem.getElementsByTagName("Forgalmazó").item(0).getTextContent();

		            // Fizetésimód elem feldolgozása
		            Element fizetesimodElem = (Element) gyogyszerkiadasElem.getElementsByTagName("Fizetésimód").item(0);
		            String keszpenz = fizetesimodElem.getElementsByTagName("Készpénz").item(0).getTextContent();
		            String bankkartya = fizetesimodElem.getElementsByTagName("Bankártya").item(0).getTextContent();

		            // Kiíjuk a konzolra az attribútumokat és az elemeket
		            System.out.println("Kid: " + kid);
		            System.out.println("Tid: " + tid);
		            System.out.println("Ár: " + ar);
		            System.out.println("Forgalmazó: " + forgalmazo);
		            System.out.println("Készpénz: " + keszpenz);
		            System.out.println("Bankártya: " + bankkartya);
		            System.out.println("-----------------------------");
		        }
		    }
		}

	 private static void readXMLGyogyszerElement(Document document, String elementName) {
		    System.out.println("\n-----Gyógyszer-ELEM-----");
		    NodeList gyogyszerNodeList = document.getElementsByTagName(elementName);

		    for (int i = 0; i < gyogyszerNodeList.getLength(); i++) {
		        Node gyogyszerNode = gyogyszerNodeList.item(i);
		        System.out.println("\nJelenlegi elem: " + gyogyszerNode.getNodeName());

		        // Ellenőrzi, hogy a Node egy ELEMENT_NODE típusú-e
		        if (gyogyszerNode.getNodeType() == Node.ELEMENT_NODE) {
		            Element gyogyszerElem = (Element) gyogyszerNode;

		            // attribútumok létrehozása és megadása
		            String gyid = gyogyszerElem.getAttribute("Gyid");
		            String ar = gyogyszerElem.getElementsByTagName("Ár").item(0).getTextContent();
		            NodeList hatanyagNodeList = gyogyszerElem.getElementsByTagName("Hatóanyag");
		            NodeList kiszerelésNodeList = gyogyszerElem.getElementsByTagName("Kiszerelés");
		            String venykteles = gyogyszerElem.getElementsByTagName("Vényköteles").item(0).getTextContent();
		            String nev = gyogyszerElem.getElementsByTagName("Név").item(0).getTextContent();

		            // Kiíjuk a konzolra az attribútumokat és az elemeket
		            System.out.println("Gyid: " + gyid);
		            System.out.println("Ár: " + ar);
		            System.out.println("Vényköteles: " + venykteles);
		            System.out.println("Név: " + nev);

		            // Hatóanyagok kiírása
		            System.out.println("Hatóanyagok:");
		            for (int j = 0; j < hatanyagNodeList.getLength(); j++) {
		                Node hatanyagNode = hatanyagNodeList.item(j);
		                if (hatanyagNode.getNodeType() == Node.ELEMENT_NODE) {
		                    Element hatanyagElem = (Element) hatanyagNode;
		                    System.out.println("  " + hatanyagElem.getTextContent());
		                }
		            }

		            // Kiszerelések kiírása
		            System.out.println("Kiszerelések:");
		            for (int k = 0; k < kiszerelésNodeList.getLength(); k++) {
		                Node kiszerelésNode = kiszerelésNodeList.item(k);
		                if (kiszerelésNode.getNodeType() == Node.ELEMENT_NODE) {
		                    Element kiszerelésElem = (Element) kiszerelésNode;
		                    System.out.println("  " + kiszerelésElem.getTextContent());
		                }
		            }

		            System.out.println("-----------------------------");
		        }
		    }
		}

	 private static void readXMLBetegElement(Document document, String elementName) {
		    System.out.println("\n-----Beteg-ELEM-----");
		    NodeList betegNodeList = document.getElementsByTagName(elementName);

		    for (int i = 0; i < betegNodeList.getLength(); i++) {
		        Node betegNode = betegNodeList.item(i);
		        System.out.println("\nJelenlegi elem: " + betegNode.getNodeName());

		        // Ellenőrzi, hogy a Node egy ELEMENT_NODE típusú-e
		        if (betegNode.getNodeType() == Node.ELEMENT_NODE) {
		            Element betegElem = (Element) betegNode;

		            // attribútumok létrehozása és megadása
		            String bid = betegElem.getAttribute("Bid");
		            String kid = betegElem.getAttribute("Kid");
		            String nev = betegElem.getElementsByTagName("Név").item(0).getTextContent();
		            String email = betegElem.getElementsByTagName("Email").item(0).getTextContent();
		            String telefonszam = betegElem.getElementsByTagName("Telefonszám").item(0).getTextContent();

		            // Lakcím elemek feldolgozása
		            Node lakcimNode = betegElem.getElementsByTagName("Lakcím").item(0);
		            Element lakcimElem = (Element) lakcimNode;
		            String varos = lakcimElem.getElementsByTagName("Város").item(0).getTextContent();
		            String utca = lakcimElem.getElementsByTagName("Utca").item(0).getTextContent();
		            String hazszam = lakcimElem.getElementsByTagName("Házszám").item(0).getTextContent();

		            // Kiíjuk a konzolra az attribútumokat és az elemeket
		            System.out.println("Bid: " + bid);
		            System.out.println("Kid: " + kid);
		            System.out.println("Név: " + nev);
		            System.out.println("Email: " + email);
		            System.out.println("Telefonszám: " + telefonszam);
		            System.out.println("Város: " + varos);
		            System.out.println("Utca: " + utca);
		            System.out.println("Házszám: " + hazszam);

		            System.out.println("-----------------------------");
		        }
		    }
		}

	 private static void readXMLKivantTermekElement(Document document, String elementName) {
		    System.out.println("\n-----Kívánttermék-ELEM-----");
		    NodeList kivantTermekNodeList = document.getElementsByTagName(elementName);

		    for (int i = 0; i < kivantTermekNodeList.getLength(); i++) {
		        Node kivantTermekNode = kivantTermekNodeList.item(i);
		        System.out.println("\nJelenlegi elem: " + kivantTermekNode.getNodeName());

		        // Ellenőrzi, hogy a Node egy ELEMENT_NODE típusú-e
		        if (kivantTermekNode.getNodeType() == Node.ELEMENT_NODE) {
		            Element kivantTermekElem = (Element) kivantTermekNode;

		            // attribútumok létrehozása és megadása
		            String gyid = kivantTermekElem.getAttribute("Gyid");
		            String kid = kivantTermekElem.getAttribute("Kid");
		            String arukeszlet = kivantTermekElem.getElementsByTagName("Árukészlet").item(0).getTextContent();
		            String beerkezes = kivantTermekElem.getElementsByTagName("Beérkezés").item(0).getTextContent();

		            // Kiíjuk a konzolra az attribútumokat és az elemeket
		            System.out.println("Gyid: " + gyid);
		            System.out.println("Kid: " + kid);
		            System.out.println("Árukészlet: " + arukeszlet);
		            System.out.println("Beérkezés: " + beerkezes);

		            System.out.println("-----------------------------");
		        }
		    }
		}

	
}