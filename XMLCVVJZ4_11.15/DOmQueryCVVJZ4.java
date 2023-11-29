package domcvvjz41115;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomQueryCVVJZ4 {
    public static void main(String[] args) {
        try {

            // a.) Kérdezze le a kurzusok nevét egy listába, majd írja a konzolra!
            File xmlFile = new File("./CVVJZ4_kurzusfelvetel.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            List<String> kurzusNevek = getKurzusNevek(doc);         
            System.out.println("Kurzusnév: " + kurzusNevek);

            //b.) Kérdezze le az orarendNeptunkod.xml dokumentum első példányát és írja ki strukturált formában a konzolra és egy fájlba.
                        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     private static List<String> getKurzusNevek(Document doc) {
        List<String> kurzusNevek = new ArrayList<>();
        NodeList kurzusNodeList = doc.getElementsByTagName("kurzusnev");

        for (int i = 0; i < kurzusNodeList.getLength(); i++) {
            Node node = kurzusNodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                kurzusNevek.add(element.getTextContent());
            }
        }

        return kurzusNevek;
    }
    
    private static void writeElementToFile(Element element, String fileName) throws IOException {
        FileWriter writer = new FileWriter(fileName);
        writeElementToFile(element, writer, 0);
        writer.close();
    }

    private static void writeElementToFile(Element element, FileWriter writer, int indent) throws IOException {
        for (int i = 0; i < indent; i++) {
            writer.append("  ");
        }
        writer.append(element.getTagName()).append(": ").append(element.getTextContent()).append("\n");

        NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                writeElementToFile((Element) node, writer, indent + 1);
            }
        }
    }

}