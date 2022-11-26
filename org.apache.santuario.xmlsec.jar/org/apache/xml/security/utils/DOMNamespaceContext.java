package org.apache.xml.security.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.xml.namespace.NamespaceContext;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class DOMNamespaceContext implements NamespaceContext {
   private Map namespaceMap = new HashMap();

   public DOMNamespaceContext(Node contextNode) {
      this.addNamespaces(contextNode);
   }

   public String getNamespaceURI(String arg0) {
      return (String)this.namespaceMap.get(arg0);
   }

   public String getPrefix(String arg0) {
      Iterator var2 = this.namespaceMap.entrySet().iterator();

      Map.Entry entry;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         entry = (Map.Entry)var2.next();
      } while(!((String)entry.getValue()).equals(arg0));

      return (String)entry.getKey();
   }

   public Iterator getPrefixes(String arg0) {
      return this.namespaceMap.keySet().iterator();
   }

   private void addNamespaces(Node element) {
      if (element.getParentNode() != null) {
         this.addNamespaces(element.getParentNode());
      }

      if (element instanceof Element) {
         Element el = (Element)element;
         NamedNodeMap map = el.getAttributes();

         for(int x = 0; x < map.getLength(); ++x) {
            Attr attr = (Attr)map.item(x);
            if ("xmlns".equals(attr.getPrefix())) {
               this.namespaceMap.put(attr.getLocalName(), attr.getValue());
            }
         }
      }

   }
}
