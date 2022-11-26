package weblogic.xml.dom;

import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;

public final class Util {
   public static final NodeList EMPTY_NODELIST = new NodeList() {
      public int getLength() {
         return 0;
      }

      public Node item(int index) {
         return null;
      }
   };
   public static final NamedNodeMap NAMED_NODE_MAP = new NamedNodeMap() {
      public int getLength() {
         return 0;
      }

      public Node getNamedItem(String name) {
         return null;
      }

      public Node getNamedItemNS(String namespaceURI, String localName) {
         return null;
      }

      public Node item(int index) {
         return null;
      }

      public Node removeNamedItem(String name) {
         return null;
      }

      public Node removeNamedItemNS(String namespaceURI, String localName) {
         return null;
      }

      public Node setNamedItem(Node arg) {
         throw new UnsupportedOperationException("This NamedNodeMap is readOnly");
      }

      public Node setNamedItemNS(Node arg) {
         throw new UnsupportedOperationException("This NamedNodeMap is readOnly");
      }
   };

   private Util() {
   }

   public static final String getPrefix(String qualifiedName) {
      if (qualifiedName == null) {
         return null;
      } else {
         int index = qualifiedName.indexOf(58);
         return index < 0 ? null : qualifiedName.substring(0, index);
      }
   }

   public static final String getLocalName(String qualifiedName) {
      if (qualifiedName == null) {
         return null;
      } else {
         int index = qualifiedName.indexOf(58);
         return index < 0 ? qualifiedName : qualifiedName.substring(index + 1);
      }
   }

   public static String printNode(Node node) {
      StringBuffer b = new StringBuffer();
      switch (node.getNodeType()) {
         case 1:
            String name = node.getNodeName();
            b.append("<" + name);
            NamedNodeMap attributes = node.getAttributes();

            for(int i = 0; i < attributes.getLength(); ++i) {
               Node current = attributes.item(i);
               b.append(" " + current.getNodeName() + "=\"" + current.getNodeValue() + "\"");
            }

            b.append(">");
            NodeList children = node.getChildNodes();
            if (children != null) {
               for(int i = 0; i < children.getLength(); ++i) {
                  b.append(printNode(children.item(i)));
               }
            }

            b.append("</" + name + ">");
            break;
         case 2:
         case 5:
         case 6:
         default:
            throw new IllegalArgumentException("Unable to process " + node);
         case 3:
         case 4:
            b.append(node.getNodeValue());
            break;
         case 7:
            ProcessingInstruction pi = (ProcessingInstruction)node;
            b.append("<?" + pi.getTarget() + " " + pi.getData() + "?>");
            break;
         case 8:
            b.append("<!--" + ((Comment)node).getData() + "-->\n");
            break;
         case 9:
            Document doc = (Document)node;
            b.append(printNode(doc.getDocumentElement()));
            break;
         case 10:
            b.append("<xml version=\"1.0\">\n");
            break;
         case 11:
            NodeList l = node.getChildNodes();
            int j = l.getLength();

            for(int i = 0; i < j; ++i) {
               b.append(printNode(l.item(i)));
            }
      }

      return b.toString();
   }
}
