package hu.domparse.cvvjz4;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DOMReadCVVJZ4 {

    public static void main(String[] args) {
        try {
            File outputFile = new File("XMLReadCVVJ4.xml");
            StreamResult output = new StreamResult(outputFile);
            Document xmlDocument = parseXmlFile("XMLCVVJZ4.xml");
            writeXmlDocument(xmlDocument, output);
            System.out.println(formatXml(xmlDocument));
        } catch (IOException | SAXException | ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    // Parse XML file
    public static Document parseXmlFile(String fileName) throws SAXException, IOException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(fileName));
        cleanXmlDocument(document.getDocumentElement()); 
        return document;
    }

    // Remove empty text nodes
    private static void cleanXmlDocument(Node root) {
        NodeList nodes = root.getChildNodes();
        List<Node> toDelete = new ArrayList<>();
        for (int i = 0; i < nodes.getLength(); i++) {
            if (nodes.item(i).getNodeType() == Node.TEXT_NODE && nodes.item(i).getTextContent().strip().isEmpty()) {
                toDelete.add(nodes.item(i));
            } else {
                cleanXmlDocument(nodes.item(i));
            }
        }
        for (Node node : toDelete) {
            root.removeChild(node);
        }
    }

    // Write XML document
    public static void writeXmlDocument(Document document, StreamResult output) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(document);
        transformer.transform(source, output);
    }

    // Format XML text
    public static String formatXml(Document document) {
        return "<?xml version=\"" + document.getXmlVersion() + "\" encoding=\"" + document.getXmlEncoding() + "\" ?>\n" +
               formatXmlElement(document.getDocumentElement(), 0);
    }

    // Recursive function to format XML elements
    public static String formatXmlElement(Node node, int indent) {
        if (node.getNodeType() != Node.ELEMENT_NODE && node.getNodeType() != Node.COMMENT_NODE) {
            return "";
        }
        StringBuilder output = new StringBuilder();

        if (node.getNodeType() == Node.COMMENT_NODE) {
            output.append(getIndentation(indent)).append("<!--").append(((Comment) node).getTextContent()).append("-->\n");
            return output.toString();
        }

        output.append(getIndentation(indent)).append("<").append(((Element) node).getTagName());

        if (node.hasAttributes()) {
            for (int i = 0; i < node.getAttributes().getLength(); i++) {
                Node attribute = node.getAttributes().item(i);
                output.append(" ").append(attribute.getNodeName()).append("=\"").append(attribute.getNodeValue()).append("\"");
            }
        }

        NodeList children = node.getChildNodes();

        if (children.getLength() == 1 && children.item(0).getNodeType() == Node.TEXT_NODE) {
            output.append(">").append(children.item(0).getTextContent().trim()).append("</").append(((Element) node).getTagName()).append(">\n");
        } else {
            output.append(">\n");

            for (int i = 0; i < children.getLength(); i++) {
                output.append(formatXmlElement(children.item(i), indent + 1));
            }

            output.append(getIndentation(indent)).append("</").append(((Element) node).getTagName()).append(">\n");
        }

        return output.toString();
    }

    private static String getIndentation(int indent) {
        StringBuilder indentation = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            indentation.append("    ");
        }
        return indentation.toString();
    }
}
