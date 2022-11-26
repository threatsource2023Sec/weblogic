package weblogic.xml.dom;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import weblogic.utils.XXEUtils;

public class DOMDeserializer implements XMLStreamConstants {
   public static Node deserialize(XMLStreamReader reader) throws XMLStreamException, ParserConfigurationException {
      DocumentBuilderFactory factory = XXEUtils.createDocumentBuilderFactoryInstance();
      DocumentBuilder docBuilder = factory.newDocumentBuilder();
      return deserialize(reader, docBuilder);
   }

   public static Node deserialize(XMLStreamReader reader, DocumentBuilder docBuilder) throws XMLStreamException {
      Document document = null;
      Node parentNode = null;
      String localname = null;
      String uri = null;

      while(true) {
         while(reader.hasNext()) {
            int event = reader.next();
            switch (event) {
               case 1:
                  localname = reader.getLocalName();
                  uri = reader.getNamespaceURI();
                  Element e;
                  if (uri == null) {
                     e = document.createElement(localname);
                  } else {
                     String prefix = reader.getPrefix();
                     String prefixedName;
                     if (prefix != null && prefix.length() > 0) {
                        prefixedName = prefix + ":" + localname;
                     } else {
                        prefixedName = localname;
                     }

                     e = document.createElementNS(uri, prefixedName);
                  }

                  ((Node)parentNode).appendChild(e);
                  parentNode = e;
                  int numAttrs = reader.getAttributeCount();

                  String prefix;
                  String qName;
                  int numNamespaces;
                  for(numNamespaces = 0; numNamespaces < numAttrs; ++numNamespaces) {
                     String localName = reader.getAttributeLocalName(numNamespaces);
                     uri = reader.getAttributeNamespace(numNamespaces);
                     prefix = reader.getAttributeValue(numNamespaces);
                     if (uri == null) {
                        e.setAttribute(localName, prefix);
                     } else {
                        qName = reader.getAttributePrefix(numNamespaces);
                        String prefixedName;
                        if (qName != null && qName.length() > 0) {
                           prefixedName = qName + ":" + localname;
                        } else {
                           prefixedName = localname;
                        }

                        e.setAttributeNS(uri, prefixedName, prefix);
                     }
                  }

                  numNamespaces = reader.getNamespaceCount();

                  for(int i = 0; i < numNamespaces; ++i) {
                     prefix = reader.getNamespacePrefix(i);
                     uri = reader.getNamespaceURI(i);
                     qName = null;
                     if (prefix.equals("")) {
                        qName = "xmlns";
                     } else {
                        qName = "xmlns:" + prefix;
                     }

                     e.setAttributeNS("", qName, uri);
                  }
                  break;
               case 2:
                  parentNode = ((Node)parentNode).getParentNode();
                  break;
               case 3:
                  ((Node)parentNode).appendChild(document.createProcessingInstruction(reader.getPITarget(), reader.getPIData()));
                  break;
               case 4:
                  ((Node)parentNode).appendChild(document.createTextNode(reader.getText()));
                  break;
               case 5:
                  ((Node)parentNode).appendChild(document.createComment(reader.getText()));
               case 6:
               case 8:
               default:
                  break;
               case 7:
                  document = docBuilder.newDocument();
                  parentNode = document;
            }
         }

         return document;
      }
   }
}
