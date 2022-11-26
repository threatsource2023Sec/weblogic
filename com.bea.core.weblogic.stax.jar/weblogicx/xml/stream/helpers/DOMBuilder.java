package weblogicx.xml.stream.helpers;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import weblogicx.xml.stream.EndElementEvent;
import weblogicx.xml.stream.StartElementEvent;
import weblogicx.xml.stream.TextEvent;
import weblogicx.xml.stream.XMLEvent;
import weblogicx.xml.stream.XMLEventStream;

public class DOMBuilder {
   public static DocumentFragment process(XMLEventStream xes, Document doc) throws SAXException {
      if (!xes.hasStartElement()) {
         throw new SAXException("Could not create document, no more elements");
      } else {
         int startTags = 0;
         int endTags = 0;
         DocumentFragment df = doc.createDocumentFragment();
         Node currentNode = df;

         while(xes.hasNext()) {
            XMLEvent e = xes.next();
            if (e instanceof StartElementEvent) {
               ++startTags;
               StartElementEvent see = (StartElementEvent)e;
               String name = see.getName();
               String qualifiedName = see.getQualifiedName();
               String namespaceURI = see.getNamespaceURI();
               Element element;
               if (namespaceURI == null && name.equals(qualifiedName)) {
                  element = doc.createElement(name);
               } else {
                  element = doc.createElementNS(namespaceURI, qualifiedName);
               }

               Attributes attributes = see.getAttributes();
               int length = attributes.getLength();

               for(int i = 0; i < length; ++i) {
                  String localName = attributes.getLocalName(i);
                  String qName = attributes.getQName(i);
                  String value = attributes.getValue(i);
                  Attr attr;
                  if (localName.equals(qName)) {
                     attr = doc.createAttribute(localName);
                  } else if (qName.startsWith("xmlns:")) {
                     attr = doc.createAttributeNS("http://www.w3.org/2000/xmlns/", qName);
                  } else {
                     attr = doc.createAttributeNS(attributes.getURI(i), qName);
                  }

                  attr.setValue(value);
                  element.setAttributeNode(attr);
               }

               ((Node)currentNode).appendChild(element);
               currentNode = element;
            }

            if (e instanceof EndElementEvent) {
               ++endTags;
               if (endTags == startTags) {
                  return df;
               }

               currentNode = ((Node)currentNode).getParentNode();
            }

            if (e instanceof TextEvent) {
               TextEvent te = (TextEvent)e;
               Node text = doc.createTextNode(te.getText());
               ((Node)currentNode).appendChild(text);
            }
         }

         return df;
      }
   }
}
