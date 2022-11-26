package weblogic.xml.jaxp;

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
import weblogic.utils.Debug;

public class WebLogicXMLEventFactory extends XMLEventFactory {
   private static final boolean debug = Boolean.getBoolean("weblogic.xml.debug");
   private XMLEventFactory delegate;

   public WebLogicXMLEventFactory() {
      String[] eventFactories = new String[]{"com.ctc.wstx.stax.WstxEventFactory", "org.codehaus.stax2.ri.Stax2EventFactoryImpl", "weblogic.xml.stax.EventFactory", "javax.xml.stream.XMLEventFactory"};
      this.delegate = (XMLEventFactory)Utils.getDelegate(eventFactories);
      if (debug) {
         Debug.say("WebLogicXMLEventFactory is delegating to " + this.delegate.getClass());
      }

   }

   public void setLocation(Location location) {
      this.delegate.setLocation(location);
   }

   public Attribute createAttribute(String prefix, String namespaceURI, String localName, String value) {
      return this.delegate.createAttribute(prefix, namespaceURI, localName, value);
   }

   public Attribute createAttribute(String localName, String value) {
      return this.delegate.createAttribute(localName, value);
   }

   public Attribute createAttribute(QName name, String value) {
      return this.delegate.createAttribute(name, value);
   }

   public Namespace createNamespace(String namespaceURI) {
      return this.delegate.createNamespace(namespaceURI);
   }

   public Namespace createNamespace(String prefix, String namespaceUri) {
      return this.delegate.createNamespace(prefix, namespaceUri);
   }

   public StartElement createStartElement(QName name, Iterator attributes, Iterator namespaces) {
      return this.delegate.createStartElement(name, attributes, namespaces);
   }

   public StartElement createStartElement(String prefix, String namespaceUri, String localName) {
      return this.delegate.createStartElement(prefix, namespaceUri, localName);
   }

   public StartElement createStartElement(String prefix, String namespaceUri, String localName, Iterator attributes, Iterator namespaces) {
      return this.delegate.createStartElement(prefix, namespaceUri, localName, attributes, namespaces);
   }

   public StartElement createStartElement(String prefix, String namespaceUri, String localName, Iterator attributes, Iterator namespaces, NamespaceContext context) {
      return this.delegate.createStartElement(prefix, namespaceUri, localName, attributes, namespaces, context);
   }

   public EndElement createEndElement(QName name, Iterator namespaces) {
      return this.delegate.createEndElement(name, namespaces);
   }

   public EndElement createEndElement(String prefix, String namespaceUri, String localName) {
      return this.delegate.createEndElement(prefix, namespaceUri, localName);
   }

   public EndElement createEndElement(String prefix, String namespaceUri, String localName, Iterator namespaces) {
      return this.delegate.createEndElement(prefix, namespaceUri, localName, namespaces);
   }

   public Characters createCharacters(String content) {
      return this.delegate.createCharacters(content);
   }

   public Characters createCData(String content) {
      return this.delegate.createCData(content);
   }

   public Characters createSpace(String content) {
      return this.delegate.createSpace(content);
   }

   public Characters createIgnorableSpace(String content) {
      return this.delegate.createIgnorableSpace(content);
   }

   public StartDocument createStartDocument() {
      return this.delegate.createStartDocument();
   }

   public StartDocument createStartDocument(String encoding, String version, boolean standalone) {
      return this.delegate.createStartDocument(encoding, version, standalone);
   }

   public StartDocument createStartDocument(String encoding, String version) {
      return this.delegate.createStartDocument(encoding, version);
   }

   public StartDocument createStartDocument(String encoding) {
      return this.delegate.createStartDocument(encoding);
   }

   public EndDocument createEndDocument() {
      return this.delegate.createEndDocument();
   }

   public EntityReference createEntityReference(String name, EntityDeclaration declaration) {
      return this.delegate.createEntityReference(name, declaration);
   }

   public Comment createComment(String text) {
      return this.delegate.createComment(text);
   }

   public ProcessingInstruction createProcessingInstruction(String target, String data) {
      return this.delegate.createProcessingInstruction(target, data);
   }

   public DTD createDTD(String dtd) {
      return this.delegate.createDTD(dtd);
   }
}
