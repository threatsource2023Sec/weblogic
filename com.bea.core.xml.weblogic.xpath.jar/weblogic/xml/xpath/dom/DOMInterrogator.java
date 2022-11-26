package weblogic.xml.xpath.dom;

import java.io.StringWriter;
import java.util.Iterator;
import org.w3c.dom.Attr;
import org.w3c.dom.CharacterData;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.ProcessingInstruction;
import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.common.Interrogator;
import weblogic.xml.xpath.dom.iterators.DescendantOrSelfIterator;

final class DOMInterrogator implements Interrogator {
   public static final Interrogator INSTANCE = new DOMInterrogator();

   private DOMInterrogator() {
   }

   public String getNodeStringValue(Object n) {
      Node node = (Node)n;
      switch (node.getNodeType()) {
         case -4343:
            return node.getNodeValue();
         case 1:
         case 9:
            StringWriter out = new StringWriter();
            DescendantOrSelfIterator iter = new DescendantOrSelfIterator(node);
            iter.next();

            while(iter.hasNext()) {
               Node next = (Node)iter.next();
               switch (next.getNodeType()) {
                  case 3:
                  case 4:
                     out.write(((CharacterData)next).getData());
               }
            }

            return out.toString();
         case 2:
            return ((Attr)node).getValue();
         case 3:
         case 4:
            return node.getNodeValue();
         case 7:
            return node.getNodeValue();
         case 8:
            return node.getNodeValue();
         default:
            throw new IllegalArgumentException("Unknown node: " + node.getClass() + " " + node);
      }
   }

   public String getLocalName(Object node) {
      return ((Node)node).getPrefix() == null ? ((Node)node).getNodeName() : ((Node)node).getLocalName();
   }

   public String getPrefix(Object node) {
      return ((Node)node).getPrefix();
   }

   public String getExpandedName(Object n) {
      Node node = (Node)n;
      switch (node.getNodeType()) {
         case -4343:
            return node.getLocalName();
         case 1:
         case 2:
            String name = node.getNodeName();
            return name != null ? name : node.getLocalName();
         case 7:
            return ((ProcessingInstruction)node).getTarget();
         default:
            return null;
      }
   }

   public String getNamespaceURI(Object node) {
      String uri = ((Node)node).getNamespaceURI();
      return uri == null ? "" : uri;
   }

   public String getAttributeValue(Object node, String nsPrefix, String attName) {
      NamedNodeMap atts = ((Node)node).getAttributes();
      if (atts == null) {
         return null;
      } else {
         Node att = atts.getNamedItem(nsPrefix == null ? attName : nsPrefix + ":" + attName);
         if (att == null) {
            return null;
         } else {
            try {
               return att.getNodeValue();
            } catch (DOMException var7) {
               return null;
            }
         }
      }
   }

   public Object getParent(Object node) {
      return ((Node)node).getParentNode();
   }

   public Object getNodeById(Context ctx, String id) {
      Document doc;
      if (ctx.node instanceof Document) {
         doc = (Document)ctx.node;
      } else {
         doc = ((Node)ctx.node).getOwnerDocument();
      }

      return doc == null ? null : doc.getElementById(id);
   }

   public Iterator getChildren(Object node) {
      throw new IllegalStateException();
   }

   public boolean isComment(Object node) {
      return ((Node)node).getNodeType() == 8;
   }

   public boolean isProcessingInstruction(Object node) {
      return ((Node)node).getNodeType() == 7;
   }

   public boolean isText(Object node) {
      switch (((Node)node).getNodeType()) {
         case 3:
         case 4:
            return true;
         default:
            return false;
      }
   }

   public boolean isElement(Object node) {
      return ((Node)node).getNodeType() == 1;
   }

   public boolean isNode(Object node) {
      return Utils.isUsable((Node)node);
   }
}
