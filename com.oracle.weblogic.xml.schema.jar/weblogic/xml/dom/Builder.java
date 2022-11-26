package weblogic.xml.dom;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import weblogic.utils.XXEUtils;

public class Builder {
   public static void nextTag(XMLStreamReader reader) throws XMLStreamException {
      while(true) {
         if (reader.hasNext()) {
            if (!reader.isStartElement() && !reader.isEndElement()) {
               reader.next();
               continue;
            }

            return;
         }

         return;
      }
   }

   public NodeImpl create(File file) throws XMLStreamException, DOMException, IOException {
      XMLInputFactory f = XXEUtils.createXMLInputFactoryInstance();
      XMLStreamReader r = f.createXMLStreamReader(new FileInputStream(file));
      nextTag(r);
      return read(new ElementNode(), r);
   }

   public void readText(ElementNode node, XMLStreamReader reader) throws XMLStreamException, DOMException {
      for(; reader.getEventType() == 4 || reader.getEventType() == 5; reader.next()) {
         switch (reader.getEventType()) {
            case 4:
               node.appendChild(new TextNode(reader.getText()));
               break;
            case 5:
               node.appendChild((new TextNode(reader.getText())).asComment());
         }
      }

   }

   public static ElementNode read(ElementNode node, XMLStreamReader reader) throws XMLStreamException, DOMException {
      if (reader.getEventType() != 1) {
         throw new XMLStreamException("Expected a Start Element!");
      } else {
         nextTag(reader);
         node.setPrefix(reader.getPrefix());
         node.setNamespaceURI(reader.getNamespaceURI());
         node.setLocalName(reader.getLocalName());
         int attCount = reader.getAttributeCount();
         int nsCount = reader.getNamespaceCount();
         int total = attCount + nsCount;
         if (total > 0) {
            AttributeMap atts = new AttributeMap(total);
            atts.setOwner(node);
            node.setAttributes(atts);
            int i;
            if (nsCount > 0) {
               for(i = 0; i < nsCount; ++i) {
                  String prefix = reader.getNamespacePrefix(i);
                  if (prefix == null) {
                     atts.setAttribute(i, "http://www.w3.org/2000/xmlns/", "xmlns", (String)null, reader.getNamespaceURI(i));
                  } else {
                     atts.setAttribute(i, "http://www.w3.org/2000/xmlns/", prefix, "xmlns", reader.getNamespaceURI(i));
                  }
               }
            }

            if (attCount > 0) {
               for(i = 0; i < attCount; ++i) {
                  atts.setAttribute(i + nsCount, reader.getAttributeNamespace(i), reader.getAttributeLocalName(i), reader.getAttributePrefix(i), reader.getAttributeValue(i));
               }
            }
         }

         reader.next();

         while(reader.getEventType() == 1 || reader.getEventType() == 4 || reader.getEventType() == 5 || reader.getEventType() == 3) {
            switch (reader.getEventType()) {
               case 1:
                  node.appendChild(read(new ElementNode(), reader));
               case 2:
               default:
                  break;
               case 3:
                  node.appendChild(new PINode(reader.getPITarget(), reader.getPIData()));
                  reader.next();
                  break;
               case 4:
                  node.appendChild(new TextNode(reader.getText()));
                  reader.next();
                  break;
               case 5:
                  node.appendChild((new TextNode(reader.getText())).asComment());
                  reader.next();
            }
         }

         if (reader.getEventType() != 2) {
            throw new XMLStreamException("Expected an End Element!");
         } else {
            reader.next();
            return node;
         }
      }
   }

   public static void main(String[] args) throws Exception {
      Builder b = new Builder();
      NodeImpl n = b.create(new File(args[0]));
      System.out.println(n);
      NodeIterator i = n.iterator();

      while(true) {
         while(i.hasNext()) {
            Node no = i.nextNode();
            if (no.getNodeType() != 3 && no.getNodeType() != 8 && no.getNodeType() != 4) {
               System.out.println("[" + no.getLocalName() + "]");
            } else {
               System.out.println("[" + no + "]");
            }
         }

         XMLStreamIterator j = new XMLStreamIterator(n);

         while(true) {
            while(j.hasNext()) {
               Node no = j.nextNode();
               if (no.getNodeType() != 3 && no.getNodeType() != 8 && no.getNodeType() != 4) {
                  if (j.isOpen()) {
                     System.out.println("<" + no.getLocalName() + ">");
                  } else {
                     System.out.println("</" + no.getLocalName() + ">");
                  }
               } else {
                  System.out.println(no);
               }
            }

            return;
         }
      }
   }
}
