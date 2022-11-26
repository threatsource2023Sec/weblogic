package weblogic.xml.dom;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public final class NamespaceUtils {
   public static String getPrefixOnElement(Node element_node, String nsURI, boolean check_default_ns) {
      assert 1 == element_node.getNodeType();

      if (element_node.hasAttributes()) {
         assert nsURI != null;

         NamedNodeMap atts = element_node.getAttributes();
         int i = 0;

         for(int len = atts.getLength(); i < len; ++i) {
            Attr att = (Attr)atts.item(i);
            String lname = att.getLocalName();
            if (lname != null && "http://www.w3.org/2000/xmlns/".equals(att.getNamespaceURI())) {
               if ("xmlns".equals(att.getPrefix()) && nsURI.equals(att.getValue())) {
                  return att.getLocalName();
               }

               if (check_default_ns && "xmlns".equals(lname) && nsURI.equals(att.getValue())) {
                  return "";
               }
            }
         }
      }

      return null;
   }

   public static String getNamespaceOnElement(Element element_node, String prefix) {
      assert 1 == element_node.getNodeType();

      if (element_node.hasAttributes()) {
         assert prefix != null;

         NamedNodeMap atts = element_node.getAttributes();
         int i = 0;

         for(int len = atts.getLength(); i < len; ++i) {
            Attr att = (Attr)atts.item(i);
            String lname = att.getLocalName();
            if (lname != null && "http://www.w3.org/2000/xmlns/".equals(att.getNamespaceURI())) {
               if ("xmlns".equals(att.getPrefix()) && prefix.equals(att.getLocalName())) {
                  return att.getValue();
               }

               if ("xmlns".equals(lname) && "".equals(prefix)) {
                  return att.getValue();
               }
            }
         }
      }

      return null;
   }

   public static void defineNamespace(Element element, String prefix, String namespaceUri) {
      element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:" + prefix, namespaceUri);
   }

   public static void defineDefaultNamespace(Element element, String namespaceUri) {
      element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", namespaceUri);
   }
}
