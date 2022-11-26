package com.bea.core.repackaged.springframework.util.xml;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.EntityDeclaration;
import javax.xml.stream.events.Namespace;
import javax.xml.stream.events.StartElement;

class XMLEventStreamWriter implements XMLStreamWriter {
   private static final String DEFAULT_ENCODING = "UTF-8";
   private final XMLEventWriter eventWriter;
   private final XMLEventFactory eventFactory;
   private final List endElements = new ArrayList();
   private boolean emptyElement = false;

   public XMLEventStreamWriter(XMLEventWriter eventWriter, XMLEventFactory eventFactory) {
      this.eventWriter = eventWriter;
      this.eventFactory = eventFactory;
   }

   public void setNamespaceContext(NamespaceContext context) throws XMLStreamException {
      this.eventWriter.setNamespaceContext(context);
   }

   public NamespaceContext getNamespaceContext() {
      return this.eventWriter.getNamespaceContext();
   }

   public void setPrefix(String prefix, String uri) throws XMLStreamException {
      this.eventWriter.setPrefix(prefix, uri);
   }

   public String getPrefix(String uri) throws XMLStreamException {
      return this.eventWriter.getPrefix(uri);
   }

   public void setDefaultNamespace(String uri) throws XMLStreamException {
      this.eventWriter.setDefaultNamespace(uri);
   }

   public Object getProperty(String name) throws IllegalArgumentException {
      throw new IllegalArgumentException();
   }

   public void writeStartDocument() throws XMLStreamException {
      this.closeEmptyElementIfNecessary();
      this.eventWriter.add(this.eventFactory.createStartDocument());
   }

   public void writeStartDocument(String version) throws XMLStreamException {
      this.closeEmptyElementIfNecessary();
      this.eventWriter.add(this.eventFactory.createStartDocument("UTF-8", version));
   }

   public void writeStartDocument(String encoding, String version) throws XMLStreamException {
      this.closeEmptyElementIfNecessary();
      this.eventWriter.add(this.eventFactory.createStartDocument(encoding, version));
   }

   public void writeStartElement(String localName) throws XMLStreamException {
      this.closeEmptyElementIfNecessary();
      this.doWriteStartElement(this.eventFactory.createStartElement(new QName(localName), (Iterator)null, (Iterator)null));
   }

   public void writeStartElement(String namespaceURI, String localName) throws XMLStreamException {
      this.closeEmptyElementIfNecessary();
      this.doWriteStartElement(this.eventFactory.createStartElement(new QName(namespaceURI, localName), (Iterator)null, (Iterator)null));
   }

   public void writeStartElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
      this.closeEmptyElementIfNecessary();
      this.doWriteStartElement(this.eventFactory.createStartElement(new QName(namespaceURI, localName, prefix), (Iterator)null, (Iterator)null));
   }

   private void doWriteStartElement(StartElement startElement) throws XMLStreamException {
      this.eventWriter.add(startElement);
      this.endElements.add(this.eventFactory.createEndElement(startElement.getName(), startElement.getNamespaces()));
   }

   public void writeEmptyElement(String localName) throws XMLStreamException {
      this.closeEmptyElementIfNecessary();
      this.writeStartElement(localName);
      this.emptyElement = true;
   }

   public void writeEmptyElement(String namespaceURI, String localName) throws XMLStreamException {
      this.closeEmptyElementIfNecessary();
      this.writeStartElement(namespaceURI, localName);
      this.emptyElement = true;
   }

   public void writeEmptyElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
      this.closeEmptyElementIfNecessary();
      this.writeStartElement(prefix, localName, namespaceURI);
      this.emptyElement = true;
   }

   private void closeEmptyElementIfNecessary() throws XMLStreamException {
      if (this.emptyElement) {
         this.emptyElement = false;
         this.writeEndElement();
      }

   }

   public void writeEndElement() throws XMLStreamException {
      this.closeEmptyElementIfNecessary();
      int last = this.endElements.size() - 1;
      EndElement lastEndElement = (EndElement)this.endElements.get(last);
      this.eventWriter.add(lastEndElement);
      this.endElements.remove(last);
   }

   public void writeAttribute(String localName, String value) throws XMLStreamException {
      this.eventWriter.add(this.eventFactory.createAttribute(localName, value));
   }

   public void writeAttribute(String namespaceURI, String localName, String value) throws XMLStreamException {
      this.eventWriter.add(this.eventFactory.createAttribute(new QName(namespaceURI, localName), value));
   }

   public void writeAttribute(String prefix, String namespaceURI, String localName, String value) throws XMLStreamException {
      this.eventWriter.add(this.eventFactory.createAttribute(prefix, namespaceURI, localName, value));
   }

   public void writeNamespace(String prefix, String namespaceURI) throws XMLStreamException {
      this.doWriteNamespace(this.eventFactory.createNamespace(prefix, namespaceURI));
   }

   public void writeDefaultNamespace(String namespaceURI) throws XMLStreamException {
      this.doWriteNamespace(this.eventFactory.createNamespace(namespaceURI));
   }

   private void doWriteNamespace(Namespace namespace) throws XMLStreamException {
      int last = this.endElements.size() - 1;
      EndElement oldEndElement = (EndElement)this.endElements.get(last);
      Iterator oldNamespaces = oldEndElement.getNamespaces();
      List newNamespaces = new ArrayList();

      while(oldNamespaces.hasNext()) {
         Namespace oldNamespace = (Namespace)oldNamespaces.next();
         newNamespaces.add(oldNamespace);
      }

      newNamespaces.add(namespace);
      EndElement newEndElement = this.eventFactory.createEndElement(oldEndElement.getName(), newNamespaces.iterator());
      this.eventWriter.add(namespace);
      this.endElements.set(last, newEndElement);
   }

   public void writeCharacters(String text) throws XMLStreamException {
      this.closeEmptyElementIfNecessary();
      this.eventWriter.add(this.eventFactory.createCharacters(text));
   }

   public void writeCharacters(char[] text, int start, int len) throws XMLStreamException {
      this.closeEmptyElementIfNecessary();
      this.eventWriter.add(this.eventFactory.createCharacters(new String(text, start, len)));
   }

   public void writeCData(String data) throws XMLStreamException {
      this.closeEmptyElementIfNecessary();
      this.eventWriter.add(this.eventFactory.createCData(data));
   }

   public void writeComment(String data) throws XMLStreamException {
      this.closeEmptyElementIfNecessary();
      this.eventWriter.add(this.eventFactory.createComment(data));
   }

   public void writeProcessingInstruction(String target) throws XMLStreamException {
      this.closeEmptyElementIfNecessary();
      this.eventWriter.add(this.eventFactory.createProcessingInstruction(target, ""));
   }

   public void writeProcessingInstruction(String target, String data) throws XMLStreamException {
      this.closeEmptyElementIfNecessary();
      this.eventWriter.add(this.eventFactory.createProcessingInstruction(target, data));
   }

   public void writeDTD(String dtd) throws XMLStreamException {
      this.closeEmptyElementIfNecessary();
      this.eventWriter.add(this.eventFactory.createDTD(dtd));
   }

   public void writeEntityRef(String name) throws XMLStreamException {
      this.closeEmptyElementIfNecessary();
      this.eventWriter.add(this.eventFactory.createEntityReference(name, (EntityDeclaration)null));
   }

   public void writeEndDocument() throws XMLStreamException {
      this.closeEmptyElementIfNecessary();
      this.eventWriter.add(this.eventFactory.createEndDocument());
   }

   public void flush() throws XMLStreamException {
      this.eventWriter.flush();
   }

   public void close() throws XMLStreamException {
      this.closeEmptyElementIfNecessary();
      this.eventWriter.close();
   }
}
