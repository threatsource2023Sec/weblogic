package weblogic.xml.xpath.dom.axes;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import weblogic.xml.xpath.common.Axis;
import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.common.iterators.EmptyIterator;
import weblogic.xml.xpath.dom.NamespaceNode;

public final class NamespaceAxis implements Axis {
   public static final Axis INSTANCE = new NamespaceAxis();

   private NamespaceAxis() {
   }

   public Iterator createIterator(Context ctx) {
      Node node = (Node)ctx.node;
      if (node.getNodeType() != 1) {
         return EmptyIterator.getInstance();
      } else {
         Map map = new HashMap();
         map.put("xml", new NamespaceNode(node, "xml", "http://www.w3.org/XML/1998/namespace"));

         do {
            NamedNodeMap atts = node.getAttributes();
            if (atts != null) {
               int i = 0;

               for(int iL = atts.getLength(); i < iL; ++i) {
                  Node att = atts.item(i);
                  if (att.getNodeName().startsWith("xmlns")) {
                     String name = att.getNodeName();
                     if (name.length() == 5) {
                        name = "";
                     } else {
                        name = name.substring(6);
                     }

                     if (!map.containsKey(name)) {
                        map.put(name, new NamespaceNode(node, name, att.getNodeValue()));
                     }
                  }
               }
            }

            node = node.getParentNode();
         } while(node != null);

         return map.values().iterator();
      }
   }
}
