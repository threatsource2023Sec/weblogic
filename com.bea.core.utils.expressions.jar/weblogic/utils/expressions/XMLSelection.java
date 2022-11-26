package weblogic.utils.expressions;

import java.io.StringWriter;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLSelection {
   public Object filter(Document doc, String exp) throws XPathExpressionException, TransformerException {
      if (doc == null) {
         return null;
      } else {
         exp = exp.substring(1, exp.length() - 1);
         XPath xpath = XPathFactory.newInstance().newXPath();
         NodeList nl = (NodeList)xpath.evaluate(exp, doc, XPathConstants.NODESET);
         if (nl != null && nl.getLength() != 0) {
            Node node = nl.item(0);
            StringWriter result = new StringWriter();
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();
            t.setOutputProperty("omit-xml-declaration", "yes");
            t.transform(new DOMSource(node), new StreamResult(result));
            return result.toString();
         } else {
            return null;
         }
      }
   }
}
