package xpathCVVJZ41122;

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

public class xPathModify {
    public static void main(String[] args) {
        try {
            // XML dokumentum betöltése
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse("orarendCVVJZ4.xml");

            // 1. Módosítás: Töröljük az első "ora" elemet
            deleteNode(doc, "/CVVJZ4_orarend/orarend/ora[@id='1']");

            // 2. Módosítás: Módosítsuk az időpontot a második "ora" elemnél
            modifyNodeValue(doc, "/CVVJZ4_orarend/orarend/ora[@id='2']/idopont", "kedd");

            // 3. Módosítás: Adjunk hozzá egy új "ora" elemet
            addNewNode(doc, "/CVVJZ4_orarend/orarend", "ora", "id", "4");

            // XML dokumentum kiírása a konzolra
            printXML(doc);

            // XML dokumentum kiírása fájlba
            writeXMLToFile(doc, "orarendCVVJZ41.xml");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void deleteNode(Document doc, String xpathExpression) throws Exception {
        NodeList nodes = getNodeList(doc, xpathExpression);
        if (nodes.getLength() > 0) {
            Node nodeToRemove = nodes.item(0);
            nodeToRemove.getParentNode().removeChild(nodeToRemove);
            System.out.println("1. Módosítás: Töröltük az első 'ora' elemet.");
        } else {
            System.out.println("1. Módosítás: Nincs elem a megadott XPath szerint.");
        }
    }

    private static void modifyNodeValue(Document doc, String xpathExpression, String newValue) throws Exception {
        NodeList nodes = getNodeList(doc, xpathExpression);
        if (nodes.getLength() > 0) {
            Node nodeToModify = nodes.item(0);
            nodeToModify.setTextContent(newValue);
            System.out.println("2. Módosítás: Az időpontot módosítottuk a második 'ora' elemnél.");
        } else {
            System.out.println("2. Módosítás: Nincs elem a megadott XPath szerint.");
        }
    }

    private static void addNewNode(Document doc, String parentXpath, String nodeName, String attributeName, String attributeValue) throws Exception {
        Node parentNode = getNode(doc, parentXpath);
        Element newElement = doc.createElement(nodeName);
        newElement.setAttribute(attributeName, attributeValue);
        parentNode.appendChild(newElement);
        System.out.println("3. Módosítás: Hozzáadtunk egy új 'ora' elemet.");
    }

    private static NodeList getNodeList(Document doc, String xpathExpression) throws Exception {
        return (NodeList) XPathFactory.newInstance().newXPath().compile(xpathExpression).evaluate(doc, XPathConstants.NODESET);
    }

    private static Node getNode(Document doc, String xpathExpression) throws Exception {
        return (Node) XPathFactory.newInstance().newXPath().compile(xpathExpression).evaluate(doc, XPathConstants.NODE);
    }

    private static void printXML(Document doc) throws Exception {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(new DOMSource(doc), new StreamResult(System.out));
    }

    private static void writeXMLToFile(Document doc, String fileName) throws Exception {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(new DOMSource(doc), new StreamResult(fileName));
        System.out.println("XML dokumentum kiírva a fájlba: " + fileName);
    }
}
