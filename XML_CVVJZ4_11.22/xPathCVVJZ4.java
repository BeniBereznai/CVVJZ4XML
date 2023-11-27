import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class xPathCVVJZ4 {
    public static void main(String[] args) {
        try {
            // XML dokumentum betöltése
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse("studentCVVJZ4.xml");

            // XPath inicializálása
            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xpath = xPathFactory.newXPath();

            // Lekérdezések végrehajtása
            executeXPathQuery(xpath, "//student", "1. Összes student elem a class gyermekként:");
            executeXPathQuery(xpath, "//student[@id='02']", "2. Student elem az id='02' attribútummal:");
            executeXPathQuery(xpath, "//student", "3. Összes student elem:");
            executeXPathQuery(xpath, "//student[2]", "4. Második student elem a class gyermekként:");
            executeXPathQuery(xpath, "//student[last()]", "5. Utolsó student elem a class gyermekként:");
            executeXPathQuery(xpath, "//student[position()=last()-1]", "6. Utolsó előtti student elem a class gyermekként:");
            executeXPathQuery(xpath, "//student[position()<=2]", "7. Első két student elem a class gyermekként:");
            executeXPathQuery(xpath, "/class/*", "8. Class gyökérelem összes gyermek elem:");
            executeXPathQuery(xpath, "//student[@*]", "9. Student elemek legalább egy attribútummal:");
            executeXPathQuery(xpath, "//*", "10. Dokumentum összes eleme:");
            executeXPathQuery(xpath, "//student[kor>20]", "11. Class gyökérelem összes student eleme, ahol a kor >20:");
            executeXPathQuery(xpath, "//student/keresztnev | //student/vezeteknev", "12. Összes keresztnev és vezeteknev:");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void executeXPathQuery(XPath xpath, String expression, String description) throws Exception {
        XPathExpression expr = xpath.compile(expression);
        NodeList result = (NodeList) expr.evaluate(xpath, XPathConstants.NODESET);

        System.out.println("\n" + description);
        for (int i = 0; i < result.getLength(); i++) {
            System.out.println(result.item(i).getTextContent());
        }
    }
}
