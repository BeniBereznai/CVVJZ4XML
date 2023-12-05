package hu.domparse.cvvjz4;

import org.w3c.dom.*;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class DOMReadCVVJZ4 {
	    public static void main(String[] args) {
	        readXMLDocument("./XMLCVVJZ4.xml");
	    }

	    private static void readXMLDocument(String filePath) {
	        try {
	            File xmlFile = new File(filePath);
	            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	            DocumentBuilder dBuilder = factory.newDocumentBuilder();
	            Document document = dBuilder.parse(xmlFile);
	            document.getDocumentElement().normalize();

	            // Kiírjuk a gyökérelem nevét a konzolra
	            System.out.println("<" + document.getDocumentElement().getNodeName() + ">");

	            // XML elemek feldolgozása külön metódusokkal
	            readXMLGyogyszertarElement(document, "Gyógyszertár");
	            readXMLDolgozoElement(document, "Dolgozó");
	            readXMLGyogyszerkiadasElement(document, "Gyógyszerkiadás");
	            readXMLGyogyszerElement(document, "Gyógyszer");
	            readXMLBetegElement(document, "Beteg");
	            readXMLKivantTermekElement(document, "kívánttermék");

	            // Záró tag a gyökérelemnek
	            System.out.println("</" + document.getDocumentElement().getNodeName() + ">");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    private static void readXMLGyogyszertarElement(Document document, String elementName) {
	        System.out.println("  <" + elementName + ">");

	        NodeList gyogyszertarNodeList = document.getElementsByTagName(elementName);
	        for (int i = 0; i < gyogyszertarNodeList.getLength(); i++) {
	            Node gyogyszertarNode = gyogyszertarNodeList.item(i);

	            if (gyogyszertarNode.getNodeType() == Node.ELEMENT_NODE) {
	                Element gyogyszertarElem = (Element) gyogyszertarNode;

	                String gyid = gyogyszertarElem.getAttribute("GYID");
	                String nev = gyogyszertarElem.getElementsByTagName("Név").item(0).getTextContent();
	                String email = gyogyszertarElem.getElementsByTagName("Email").item(0).getTextContent();
	                String telefonszam = gyogyszertarElem.getElementsByTagName("Telefonszám").item(0).getTextContent();

	                Node cimNode = gyogyszertarElem.getElementsByTagName("Cím").item(0);
	                Element cimElem = (Element) cimNode;
	                String varos = cimElem.getElementsByTagName("Város").item(0).getTextContent();
	                String utca = cimElem.getElementsByTagName("Utca").item(0).getTextContent();
	                String hazszam = cimElem.getElementsByTagName("Házszám").item(0).getTextContent();

	                // Kiírjuk az adatokat XML formátumban
	                System.out.println("    <" + elementName + " GYID=\"" + gyid + "\">");
	                System.out.println("      <Név>" + nev + "</Név>");
	                System.out.println("      <Email>" + email + "</Email>");
	                System.out.println("      <Telefonszám>" + telefonszam + "</Telefonszám>");
	                System.out.println("      <Cím>");
	                System.out.println("        <Város>" + varos + "</Város>");
	                System.out.println("        <Utca>" + utca + "</Utca>");
	                System.out.println("        <Házszám>" + hazszam + "</Házszám>");
	                System.out.println("      </Cím>");
	                System.out.println("    </" + elementName + ">");
	            }
	        }

	        System.out.println("  </" + elementName + ">");
	    }


	    private static void readXMLDolgozoElement(Document document, String elementName) {
	        System.out.println("  <" + elementName + ">");

	        NodeList dolgozoNodeList = document.getElementsByTagName(elementName);
	        for (int i = 0; i < dolgozoNodeList.getLength(); i++) {
	            Node dolgozoNode = dolgozoNodeList.item(i);

	            if (dolgozoNode.getNodeType() == Node.ELEMENT_NODE) {
	                Element dolgozoElem = (Element) dolgozoNode;

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

	                // Kiírjuk az adatokat XML formátumban
	                System.out.println("    <" + elementName + " Did=\"" + did + "\" Tid=\"" + tid + "\">");
	                System.out.println("      <Név>" + nev + "</Név>");
	                System.out.println("      <SzületésiDátum>");
	                System.out.println("        <Év>" + ev + "</Év>");
	                System.out.println("        <Hónap>" + honap + "</Hónap>");
	                System.out.println("        <Nap>" + nap + "</Nap>");
	                System.out.println("      </SzületésiDátum>");
	                System.out.println("      <Lakcím>");
	                System.out.println("        <Város>" + varos + "</Város>");
	                System.out.println("        <Utca>" + utca + "</Utca>");
	                System.out.println("        <Házszám>" + hazszam + "</Házszám>");
	                System.out.println("      </Lakcím>");
	                System.out.println("      <Telefonszám>" + telefonszam + "</Telefonszám>");
	                System.out.println("      <Email>" + email + "</Email>");
	                System.out.println("    </" + elementName + ">");
	            }
	        }

	        System.out.println("  </" + elementName + ">");
	    }

	
	    private static void readXMLGyogyszerkiadasElement(Document document, String elementName) {
	        System.out.println("  <" + elementName + ">");

	        NodeList gyogyszerkiadasNodeList = document.getElementsByTagName(elementName);
	        for (int i = 0; i < gyogyszerkiadasNodeList.getLength(); i++) {
	            Node gyogyszerkiadasNode = gyogyszerkiadasNodeList.item(i);

	            if (gyogyszerkiadasNode.getNodeType() == Node.ELEMENT_NODE) {
	                Element gyogyszerkiadasElem = (Element) gyogyszerkiadasNode;

	                String kid = gyogyszerkiadasElem.getAttribute("Kid");
	                String tid = gyogyszerkiadasElem.getAttribute("Tid");
	                String ar = gyogyszerkiadasElem.getElementsByTagName("Ár").item(0).getTextContent();
	                String forgalmazo = gyogyszerkiadasElem.getElementsByTagName("Forgalmazó").item(0).getTextContent();

	                // Fizetésimód elem feldolgozása
	                Element fizetesimodElem = (Element) gyogyszerkiadasElem.getElementsByTagName("Fizetésimód").item(0);
	                String keszpenz = fizetesimodElem.getElementsByTagName("Készpénz").item(0).getTextContent();
	                String bankkartya = fizetesimodElem.getElementsByTagName("Bankártya").item(0).getTextContent();

	                // Kiírjuk az adatokat XML formátumban
	                System.out.println("    <" + elementName + " Kid=\"" + kid + "\" Tid=\"" + tid + "\">");
	                System.out.println("      <Ár>" + ar + "</Ár>");
	                System.out.println("      <Forgalmazó>" + forgalmazo + "</Forgalmazó>");
	                System.out.println("      <Fizetésimód>");
	                System.out.println("        <Készpénz>" + keszpenz + "</Készpénz>");
	                System.out.println("        <Bankártya>" + bankkartya + "</Bankártya>");
	                System.out.println("      </Fizetésimód>");
	                System.out.println("    </" + elementName + ">");
	            }
	        }

	        System.out.println("  </" + elementName + ">");
	    }


	    private static void readXMLGyogyszerElement(Document document, String elementName) {
	        System.out.println("  <" + elementName + ">");

	        NodeList gyogyszerNodeList = document.getElementsByTagName(elementName);
	        for (int i = 0; i < gyogyszerNodeList.getLength(); i++) {
	            Node gyogyszerNode = gyogyszerNodeList.item(i);

	            if (gyogyszerNode.getNodeType() == Node.ELEMENT_NODE) {
	                Element gyogyszerElem = (Element) gyogyszerNode;

	                String gyid = gyogyszerElem.getAttribute("Gyid");
	                String ar = gyogyszerElem.getElementsByTagName("Ár").item(0).getTextContent();
	                NodeList hatanyagNodeList = gyogyszerElem.getElementsByTagName("Hatóanyag");
	                NodeList kiszerelésNodeList = gyogyszerElem.getElementsByTagName("Kiszerelés");
	                String venykteles = gyogyszerElem.getElementsByTagName("Vényköteles").item(0).getTextContent();
	                String nev = gyogyszerElem.getElementsByTagName("Név").item(0).getTextContent();

	                // Kiírjuk az adatokat XML formátumban
	                System.out.println("    <" + elementName + " Gyid=\"" + gyid + "\">");
	                System.out.println("      <Ár>" + ar + "</Ár>");
	                System.out.println("      <Vényköteles>" + venykteles + "</Vényköteles>");
	                System.out.println("      <Név>" + nev + "</Név>");

	                // Hatóanyagok kiírása
	                System.out.println("      <Hatóanyagok>");
	                for (int j = 0; j < hatanyagNodeList.getLength(); j++) {
	                    Node hatanyagNode = hatanyagNodeList.item(j);
	                    if (hatanyagNode.getNodeType() == Node.ELEMENT_NODE) {
	                        Element hatanyagElem = (Element) hatanyagNode;
	                        System.out.println("        <Hatóanyag>" + hatanyagElem.getTextContent() + "</Hatóanyag>");
	                    }
	                }
	                System.out.println("      </Hatóanyagok>");

	                // Kiszerelések kiírása
	                System.out.println("      <Kiszerelések>");
	                for (int k = 0; k < kiszerelésNodeList.getLength(); k++) {
	                    Node kiszerelésNode = kiszerelésNodeList.item(k);
	                    if (kiszerelésNode.getNodeType() == Node.ELEMENT_NODE) {
	                        Element kiszerelésElem = (Element) kiszerelésNode;
	                        System.out.println("        <Kiszerelés>" + kiszerelésElem.getTextContent() + "</Kiszerelés>");
	                    }
	                }
	                System.out.println("      </Kiszerelések>");

	                System.out.println("    </" + elementName + ">");
	            }
	        }

	        System.out.println("  </" + elementName + ">");
	    }


	    private static void readXMLBetegElement(Document document, String elementName) {
	        System.out.println("  <" + elementName + ">");

	        NodeList betegNodeList = document.getElementsByTagName(elementName);
	        for (int i = 0; i < betegNodeList.getLength(); i++) {
	            Node betegNode = betegNodeList.item(i);

	            if (betegNode.getNodeType() == Node.ELEMENT_NODE) {
	                Element betegElem = (Element) betegNode;

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

	                // Kiírjuk az adatokat XML formátumban
	                System.out.println("    <" + elementName + " Bid=\"" + bid + "\" Kid=\"" + kid + "\">");
	                System.out.println("      <Név>" + nev + "</Név>");
	                System.out.println("      <Email>" + email + "</Email>");
	                System.out.println("      <Telefonszám>" + telefonszam + "</Telefonszám>");
	                System.out.println("      <Lakcím>");
	                System.out.println("        <Város>" + varos + "</Város>");
	                System.out.println("        <Utca>" + utca + "</Utca>");
	                System.out.println("        <Házszám>" + hazszam + "</Házszám>");
	                System.out.println("      </Lakcím>");
	                System.out.println("    </" + elementName + ">");


	            }
	        }

	        System.out.println("  </" + elementName + ">");
	    }


	    private static void readXMLKivantTermekElement(Document document, String elementName) {
	        System.out.println("  <" + elementName + ">");

	        NodeList kivantTermekNodeList = document.getElementsByTagName(elementName);
	        for (int i = 0; i < kivantTermekNodeList.getLength(); i++) {
	            Node kivantTermekNode = kivantTermekNodeList.item(i);

	            if (kivantTermekNode.getNodeType() == Node.ELEMENT_NODE) {
	                Element kivantTermekElem = (Element) kivantTermekNode;

	                String gyid = kivantTermekElem.getAttribute("Gyid");
	                String kid = kivantTermekElem.getAttribute("Kid");
	                String arukeszlet = kivantTermekElem.getElementsByTagName("Árukészlet").item(0).getTextContent();
	                String beerkezes = kivantTermekElem.getElementsByTagName("Beérkezés").item(0).getTextContent();

	                // Kiírjuk az adatokat XML formátumban
	                System.out.println("    <" + elementName + " Gyid=\"" + gyid + "\" Kid=\"" + kid + "\">");
	                System.out.println("      <Árukészlet>" + arukeszlet + "</Árukészlet>");
	                System.out.println("      <Beérkezés>" + beerkezes + "</Beérkezés>");
	                System.out.println("    </" + elementName + ">");

	           
	            }
	        }

	        System.out.println("  </" + elementName + ">");
	    }


	
}