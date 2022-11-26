package weblogic.xml.stax;

import java.util.Iterator;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.Comment;
import javax.xml.stream.events.DTD;
import javax.xml.stream.events.EndDocument;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.EntityDeclaration;
import javax.xml.stream.events.EntityReference;
import javax.xml.stream.events.Namespace;
import javax.xml.stream.events.ProcessingInstruction;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import weblogic.xml.stax.events.CharactersEvent;
import weblogic.xml.stax.events.CommentEvent;
import weblogic.xml.stax.events.DTDEvent;
import weblogic.xml.stax.events.EndDocumentEvent;
import weblogic.xml.stax.events.EndElementEvent;
import weblogic.xml.stax.events.EntityReferenceEvent;
import weblogic.xml.stax.events.ProcessingInstructionEvent;
import weblogic.xml.stax.events.StartDocumentEvent;
import weblogic.xml.stax.events.StartElementEvent;

public class EventFactory extends XMLEventFactory {
   private Location location;

   public void setLocation(Location l) {
      this.location = l;
   }

   public Attribute createAttribute(QName name, String value) {
      return new AttributeBase(name, value);
   }

   public Attribute createAttribute(String localName, String value) {
      return new AttributeBase("", localName, value);
   }

   public Attribute createAttribute(String prefix, String namespaceURI, String localName, String value) {
      return new AttributeBase(prefix, namespaceURI, localName, value, "CDATA");
   }

   public Namespace createNamespace(String namespaceURI) {
      return new NamespaceBase(namespaceURI);
   }

   public Namespace createNamespace(String prefix, String namespaceUri) {
      if (prefix == null) {
         throw new NullPointerException("The prefix of a namespace may not be set to null");
      } else {
         return new NamespaceBase(prefix, namespaceUri);
      }
   }

   public StartElement createStartElement(QName name, Iterator attributes, Iterator namespaces) {
      StartElementEvent e = new StartElementEvent(name);

      while(attributes != null && attributes.hasNext()) {
         e.addAttribute((Attribute)attributes.next());
      }

      while(namespaces != null && namespaces.hasNext()) {
         e.addNamespace((Namespace)namespaces.next());
      }

      return e;
   }

   public StartElement createStartElement(String prefix, String namespaceUri, String localName) {
      return new StartElementEvent(new QName(namespaceUri, localName, prefix));
   }

   public static String checkPrefix(String prefix) {
      return prefix == null ? "" : prefix;
   }

   public StartElement createStartElement(String prefix, String namespaceUri, String localName, Iterator attributes, Iterator namespaces) {
      prefix = checkPrefix(prefix);
      StartElementEvent e = new StartElementEvent(new QName(namespaceUri, localName, prefix));

      while(attributes != null && attributes.hasNext()) {
         e.addAttribute((Attribute)attributes.next());
      }

      while(namespaces != null && namespaces.hasNext()) {
         e.addNamespace((Namespace)namespaces.next());
      }

      return e;
   }

   public StartElement createStartElement(String prefix, String namespaceUri, String localName, Iterator attributes, Iterator namespaces, NamespaceContext context) {
      prefix = checkPrefix(prefix);
      StartElementEvent e = new StartElementEvent(new QName(namespaceUri, localName, prefix));

      while(attributes != null && attributes.hasNext()) {
         e.addAttribute((Attribute)attributes.next());
      }

      while(namespaces != null && namespaces.hasNext()) {
         e.addNamespace((Namespace)namespaces.next());
      }

      e.setNamespaceContext(context);
      return e;
   }

   public EndElement createEndElement(QName name, Iterator namespaces) {
      EndElementEvent e = new EndElementEvent(name);

      while(namespaces != null && namespaces.hasNext()) {
         e.addNamespace((Namespace)namespaces.next());
      }

      return e;
   }

   public EndElement createEndElement(String prefix, String namespaceUri, String localName) {
      prefix = checkPrefix(prefix);
      return new EndElementEvent(new QName(namespaceUri, localName, prefix));
   }

   public EndElement createEndElement(String prefix, String namespaceUri, String localName, Iterator namespaces) {
      prefix = checkPrefix(prefix);
      EndElementEvent e = new EndElementEvent(new QName(namespaceUri, localName, prefix));

      while(namespaces.hasNext()) {
         e.addNamespace((Namespace)namespaces.next());
      }

      return e;
   }

   public Characters createCharacters(String content) {
      return new CharactersEvent(content);
   }

   public Characters createCData(String content) {
      return new CharactersEvent(content, true);
   }

   public StartDocument createStartDocument() {
      return new StartDocumentEvent();
   }

   public StartDocument createStartDocument(String encoding, String version, boolean standalone) {
      StartDocumentEvent e = new StartDocumentEvent();
      e.setEncoding(encoding);
      e.setVersion(version);
      e.setStandalone(standalone);
      return e;
   }

   public StartDocument createStartDocument(String encoding, String version) {
      StartDocumentEvent e = new StartDocumentEvent();
      e.setEncoding(encoding);
      e.setVersion(version);
      return e;
   }

   public StartDocument createStartDocument(String encoding) {
      StartDocumentEvent e = new StartDocumentEvent();
      e.setEncoding(encoding);
      return e;
   }

   public EndDocument createEndDocument() {
      return new EndDocumentEvent();
   }

   public EntityReference createEntityReference(String name, EntityDeclaration declaration) {
      return new EntityReferenceEvent(name, declaration);
   }

   public Characters createSpace(String content) {
      CharactersEvent c = new CharactersEvent(content);
      c.setSpace(true);
      return c;
   }

   public Characters createIgnorableSpace(String content) {
      CharactersEvent c = new CharactersEvent(content);
      c.setSpace(true);
      c.setIgnorable(true);
      return c;
   }

   public Comment createComment(String text) {
      return new CommentEvent(text);
   }

   public ProcessingInstruction createProcessingInstruction(String target, String data) {
      return new ProcessingInstructionEvent(target, data);
   }

   public DTD createDTD(String dtd) {
      return new DTDEvent(dtd);
   }

   public String toString() {
      return "weblogic.xml.stax.EventFactory";
   }
}
