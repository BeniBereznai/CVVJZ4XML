import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomReadCVVJZ4 {
    public static void main(String[] args) {
        try {
            File xmlFile = new File("kurzusfelvetelNeptunkod.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            
            doc.getDocumentElement().normalize();
            
            
            System.out.println("Root Element: " + doc.getDocumentElement().getNodeName());
            
            
            Element kurzusfelvetelElement = doc.getDocumentElement();
            System.out.println("Tanév: " + kurzusfelvetelElement.getAttribute("tanév"));
            System.out.println("Egyetem: " + kurzusfelvetelElement.getAttribute("egyetem"));
            
            
            NodeList hallgatoList = kurzusfelvetelElement.getElementsByTagName("hallgato");
            Element hallgatoElement = (Element) hallgatoList.item(0);
            System.out.println("Hallgató adatai:");
            System.out.println("Név: " + hallgatoElement.getElementsByTagName("nev").item(0).getTextContent());
            System.out.println("Született: " + hallgatoElement.getElementsByTagName("szuletett").item(0).getTextContent());
            System.out.println("Szak: " + hallgatoElement.getElementsByTagName("szak").item(0).getTextContent());
            
            NodeList kurzusokList = kurzusfelvetelElement.getElementsByTagName("kurzusok");
            Element kurzusokElement = (Element) kurzusokList.item(0);
            System.out.println("\nKurzusok:");
            
            NodeList kurzusList = kurzusokElement.getElementsByTagName("kurzus");
            for (int i = 0; i < kurzusList.getLength(); i++) {
                Element kurzus = (Element) kurzusList.item(i);
                System.out.println("Kurzus " + (i + 1) + ":");
                System.out.println("Kurzusnév: " + kurzus.getElementsByTagName("kurzusnev").item(0).getTextContent());
                System.out.println("Kredit: " + kurzus.getElementsByTagName("kredit").item(0).getTextContent());
                System.out.println("Időpont: " + kurzus.getElementsByTagName("idopont").item(0).getTextContent());
                System.out.println("Hely: " + kurzus.getElementsByTagName("hely").item(0).getTextContent());
                System.out.println("Oktató: " + kurzus.getElementsByTagName("oktato").item(0).getTextContent());
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
