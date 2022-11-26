package weblogic.jms.utils.xml.dom;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.w3c.dom.Attr;
import org.w3c.dom.DocumentType;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public final class DOMSerializer {
   public static void serialize(Node node, XMLStreamWriter writer) throws XMLStreamException {
      serialize(node, writer, false, false);
   }

   public static void serialize(Node node, XMLStreamWriter writer, boolean stripPI, boolean stripComments) throws XMLStreamException {
      label26:
      while(true) {
         if (node != null) {
            start(node, writer, stripPI, stripComments);
            Node tmp = node.getFirstChild();
            if (tmp != null) {
               node = tmp;
               continue;
            }

            tmp = node.getNextSibling();
            if (tmp != null) {
               end(node, writer);
               node = tmp;
               continue;
            }

            while(true) {
               end(node, writer);
               node = node.getParentNode();
               if (node != null && node.getNextSibling() != null) {
                  end(node, writer);
                  node = node.getNextSibling();
                  continue label26;
               }

               if (node == null) {
                  continue label26;
               }
            }
         }

         return;
      }
   }

   private static void start(Node node, XMLStreamWriter writer, boolean stripPI, boolean stripComments) throws XMLStreamException {
      switch (node.getNodeType()) {
         case 1:
            String localName = node.getLocalName();
            if (localName == null) {
               localName = node.getNodeName();
            }

            writer.writeStartElement(node.getNamespaceURI(), localName);
            NamedNodeMap attrs = node.getAttributes();

            for(int i = 0; i < attrs.getLength(); ++i) {
               Attr attr = (Attr)attrs.item(i);
               String nodeName = attr.getNodeName();
               String prefix;
               if (nodeName.startsWith("xmlns")) {
                  prefix = "";
                  if (nodeName.length() >= 5 && nodeName.charAt(5) == ':') {
                     prefix = nodeName.substring(6);
                  }

                  writer.writeNamespace(prefix, attr.getValue());
               } else {
                  prefix = attr.getNamespaceURI();
                  String attrLocalName = attr.getLocalName();
                  if (prefix == null && attrLocalName == null) {
                     attrLocalName = nodeName;
                  }

                  writer.writeAttribute(prefix, attrLocalName, attr.getValue());
               }
            }
         case 2:
         case 5:
         case 6:
         default:
            break;
         case 3:
            writer.writeCharacters(node.getNodeValue());
            break;
         case 4:
            writer.writeCData(node.getNodeValue());
            break;
         case 7:
            if (!stripPI) {
               writer.writeProcessingInstruction(node.getNodeName(), node.getNodeValue());
            }
            break;
         case 8:
            if (!stripComments) {
               writer.writeComment(node.getNodeValue());
            }
            break;
         case 9:
            writer.writeStartDocument();
            break;
         case 10:
            DocumentType docNode = (DocumentType)node;
            writer.writeDTD("<!DOCTYPE " + docNode.getNodeName() + ">");
      }

   }

   private static void end(Node node, XMLStreamWriter writer) throws XMLStreamException {
      if (node.getNodeType() == 1) {
         writer.writeEndElement();
      } else if (node.getNodeType() == 9) {
         writer.writeEndDocument();
         writer.close();
      }

   }
}
