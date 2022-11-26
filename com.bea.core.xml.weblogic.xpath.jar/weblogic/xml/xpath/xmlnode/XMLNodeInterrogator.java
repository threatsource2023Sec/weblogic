package weblogic.xml.xpath.xmlnode;

import java.io.StringWriter;
import java.util.Iterator;
import weblogic.xml.stream.XMLName;
import weblogic.xml.xmlnode.XMLNode;
import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.common.Interrogator;
import weblogic.xml.xpath.common.iterators.DescendantOrSelfIterator;

public final class XMLNodeInterrogator implements Interrogator {
   public static final Interrogator INSTANCE = new XMLNodeInterrogator();

   private XMLNodeInterrogator() {
   }

   public String getNodeStringValue(Object n) {
      XMLNode node = (XMLNode)n;
      if (node.isTextNode()) {
         return node.getText();
      } else {
         StringWriter out = new StringWriter();
         DescendantOrSelfIterator iter = new DescendantOrSelfIterator(this, node);
         iter.next();

         while(iter.hasNext()) {
            XMLNode next = (XMLNode)iter.next();
            if (next.isTextNode()) {
               out.write(next.getText());
            }
         }

         return out.toString();
      }
   }

   public String getLocalName(Object node) {
      XMLName name = ((XMLNode)node).getName();
      return name == null ? null : name.getLocalName();
   }

   public String getPrefix(Object node) {
      XMLName name = ((XMLNode)node).getName();
      return name == null ? null : name.getPrefix();
   }

   public String getExpandedName(Object node) {
      XMLName name = ((XMLNode)node).getName();
      return name == null ? null : name.getQualifiedName();
   }

   public String getNamespaceURI(Object node) {
      XMLName name = ((XMLNode)node).getName();
      if (name == null) {
         return "";
      } else {
         String uri = name.getNamespaceUri();
         return uri == null ? "" : uri;
      }
   }

   public String getAttributeValue(Object node, String prefix, String attName) {
      return ((XMLNode)node).getAttribute(attName, ((XMLNode)node).getNamespaceURI(prefix));
   }

   public Object getParent(Object node) {
      return ((XMLNode)node).getParent();
   }

   public Object getNodeById(Context ctx, String id) {
      throw new IllegalStateException();
   }

   public Iterator getChildren(Object node) {
      return ((XMLNode)node).getChildren();
   }

   public boolean isComment(Object node) {
      throw new IllegalStateException();
   }

   public boolean isProcessingInstruction(Object node) {
      throw new IllegalStateException();
   }

   public boolean isText(Object node) {
      return ((XMLNode)node).isTextNode();
   }

   public boolean isNode(Object node) {
      return !((XMLNode)node).isEndNode();
   }

   public boolean isElement(Object node) {
      return !((XMLNode)node).isEndNode() && !((XMLNode)node).isTextNode();
   }
}
