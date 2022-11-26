package weblogic.xml.stream;

import java.util.Iterator;
import weblogic.xml.stream.events.AttributeImpl;
import weblogic.xml.stream.events.AttributeIteratorImpl;
import weblogic.xml.stream.events.CharacterDataEvent;
import weblogic.xml.stream.events.CommentEvent;
import weblogic.xml.stream.events.EndDocumentEvent;
import weblogic.xml.stream.events.EndElementEvent;
import weblogic.xml.stream.events.Name;
import weblogic.xml.stream.events.ProcessingInstructionEvent;
import weblogic.xml.stream.events.SpaceEvent;
import weblogic.xml.stream.events.StartDocumentEvent;
import weblogic.xml.stream.events.StartElementEvent;

/** @deprecated */
@Deprecated
public class ElementFactory {
   public static XMLName createXMLName(String localName) {
      if (localName == null) {
         throw new IllegalArgumentException("The local name of an XMLName may not be null.");
      } else {
         return new Name(localName);
      }
   }

   public static XMLName createXMLName(String namespaceUri, String localName) {
      if (localName == null) {
         throw new IllegalArgumentException("The local name of an XMLName may not be null.");
      } else {
         return new Name(namespaceUri, localName);
      }
   }

   public static XMLName createXMLName(String namespaceUri, String localName, String prefix) {
      if (localName == null) {
         throw new IllegalArgumentException("The local name of an XMLName may not be null.");
      } else {
         return new Name(namespaceUri, localName, prefix);
      }
   }

   public static Attribute createAttribute(String localName, String value) {
      return new AttributeImpl(localName, value);
   }

   public static Attribute createAttribute(String namespaceUri, String localName, String value) {
      return new AttributeImpl(namespaceUri, localName, value);
   }

   public static Attribute createAttribute(XMLName name, String value, String type) {
      return new AttributeImpl(name, value, type);
   }

   public static Attribute createAttribute(XMLName name, String value) {
      return new AttributeImpl(name, value, "CDATA");
   }

   public static Attribute createNamespaceAttribute(String prefix, String namespaceUri) {
      if (namespaceUri == null) {
         throw new IllegalArgumentException("namespaceUri cannot be null");
      } else {
         return prefix == null ? createAttribute("xmlns", namespaceUri) : createAttribute(createXMLName((String)null, prefix, "xmlns"), namespaceUri, "CDATA");
      }
   }

   public static StartElement createStartElement(String namespaceURI, String localName, String prefix) {
      return new StartElementEvent(new Name(namespaceURI, localName, prefix));
   }

   public static StartElement createStartElement(String namespaceUri, String localName) {
      return new StartElementEvent(new Name(namespaceUri, localName));
   }

   public static StartElement createStartElement(String localName) {
      return new StartElementEvent(new Name(localName));
   }

   public static StartElement createStartElement(XMLName name) {
      return new StartElementEvent(name);
   }

   public static StartElement createStartElement(XMLName name, AttributeIterator attributes) {
      StartElementEvent e = new StartElementEvent(name);
      if (attributes != null) {
         while(attributes.hasNext()) {
            e.addAttribute(attributes.next());
         }
      }

      return e;
   }

   public static StartElement createStartElement(XMLName name, AttributeIterator attributes, AttributeIterator namespaces) {
      StartElementEvent e = new StartElementEvent(name);
      if (attributes != null) {
         while(attributes.hasNext()) {
            e.addAttribute(attributes.next());
         }
      }

      if (namespaces != null) {
         while(namespaces.hasNext()) {
            e.addNamespace(namespaces.next());
         }
      }

      return e;
   }

   public static EndElement createEndElement(XMLName name) {
      return new EndElementEvent(name);
   }

   public static EndElement createEndElement(String namespaceUri, String localName, String prefix) {
      return new EndElementEvent(new Name(namespaceUri, localName, prefix));
   }

   public static EndElement createEndElement(String namespaceUri, String localName) {
      return new EndElementEvent(new Name(namespaceUri, localName));
   }

   public static EndElement createEndElement(String localName) {
      return new EndElementEvent(new Name(localName));
   }

   public static CharacterData createCharacterData(String content) {
      return new CharacterDataEvent(content);
   }

   public static ProcessingInstruction createProcessingInstruction(XMLName name, String content) {
      return new ProcessingInstructionEvent(name, content);
   }

   public static Space createSpace(String content) {
      return new SpaceEvent(content);
   }

   public static Comment createComment(String content) {
      return new CommentEvent(content);
   }

   public static StartDocument createStartDocument() {
      return new StartDocumentEvent();
   }

   public static StartDocument createStartDocument(String encoding, String version, String standalone) {
      StartDocumentEvent e = new StartDocumentEvent();
      e.setEncoding(encoding);
      e.setStandalone(standalone);
      e.setVersion(version);
      return e;
   }

   public static StartDocument createStartDocument(String encoding, String version) {
      StartDocumentEvent e = new StartDocumentEvent();
      e.setEncoding(encoding);
      e.setVersion(version);
      return e;
   }

   public static StartDocument createStartDocument(String encoding) {
      StartDocumentEvent e = new StartDocumentEvent();
      e.setEncoding(encoding);
      return e;
   }

   public static EndDocument createEndDocument() {
      return new EndDocumentEvent();
   }

   public static AttributeIterator createAttributeIterator(Iterator iterator) {
      return new AttributeIteratorImpl(iterator);
   }
}
