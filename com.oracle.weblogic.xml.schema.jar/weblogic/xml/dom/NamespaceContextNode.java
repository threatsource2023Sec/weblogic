package weblogic.xml.dom;

import java.util.ArrayList;
import java.util.Iterator;
import javax.xml.namespace.NamespaceContext;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class NamespaceContextNode implements NamespaceContext {
   private final Element current;

   public NamespaceContextNode(Element elem) {
      this.current = elem;
   }

   public String getNamespaceURI(String prefix) {
      if (prefix == null) {
         throw new IllegalArgumentException("null prefix");
      } else if ("xml".equals(prefix)) {
         return "http://www.w3.org/XML/1998/namespace";
      } else if ("xmlns".equals(prefix)) {
         return "http://www.w3.org/2000/xmlns/";
      } else {
         return this.current == null ? null : this.getNamespaceURI(this.current, prefix);
      }
   }

   public String getPrefix(String namespaceURI) {
      if (namespaceURI == null) {
         throw new IllegalArgumentException("null namespaceURI");
      } else if ("http://www.w3.org/XML/1998/namespace".equals(namespaceURI)) {
         return "xml";
      } else if ("http://www.w3.org/2000/xmlns/".equals(namespaceURI)) {
         return "xmlns";
      } else {
         return this.current == null ? null : this.getPrefix(this.current, namespaceURI);
      }
   }

   private String getNamespaceURI(Element n, String prefix) {
      String result = NamespaceUtils.getNamespaceOnElement(n, prefix);
      if (result != null) {
         return result;
      } else {
         Node next = n.getParentNode();
         if (next == null) {
            return null;
         } else {
            return next.getNodeType() != 1 ? null : this.getNamespaceURI((Element)next, prefix);
         }
      }
   }

   public String getPrefix(Element n, String namespaceURI) {
      String prefix = NamespaceUtils.getPrefixOnElement(n, namespaceURI, true);
      if (prefix != null) {
         return prefix;
      } else {
         Node next = n.getParentNode();
         if (next == null) {
            return null;
         } else {
            return next.getNodeType() != 1 ? null : this.getPrefix((Element)next, namespaceURI);
         }
      }
   }

   private void collectPrefixes(Element n, String namespaceURI, ArrayList l) {
      NamedNodeMap m = n.getAttributes();

      for(int i = 0; i < m.getLength(); ++i) {
         Attr a = (Attr)m.item(i);
         if (a.getValue().equals(namespaceURI) && a.getNamespaceURI().equals("http://www.w3.org/2000/xmlns/")) {
            l.add(a.getLocalName());
         }
      }

      Node next = n.getParentNode();
      if (next != null) {
         if (next.getNodeType() == 1) {
            this.collectPrefixes((Element)next, namespaceURI, l);
         }
      }
   }

   public Iterator getPrefixes(String namespaceURI) {
      ArrayList l = new ArrayList();
      this.collectPrefixes(this.current, namespaceURI, l);
      return l.iterator();
   }
}
