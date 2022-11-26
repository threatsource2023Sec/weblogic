package weblogic.xml.util;

import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

abstract class DelegatingXMLStreamWriter implements XMLStreamWriter {
   private final XMLStreamWriter writer;

   public DelegatingXMLStreamWriter(XMLStreamWriter writer) {
      this.writer = writer;
   }

   public void writeStartElement(String localName) throws XMLStreamException {
      this.writer.writeStartElement(localName);
   }

   public void writeStartElement(String namespaceURI, String localName) throws XMLStreamException {
      this.writer.writeStartElement(namespaceURI, localName);
   }

   public void writeStartElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
      this.writer.writeStartElement(prefix, localName, namespaceURI);
   }

   public void writeEmptyElement(String namespaceURI, String localName) throws XMLStreamException {
      this.writer.writeEmptyElement(namespaceURI, localName);
   }

   public void writeEmptyElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
      this.writer.writeEmptyElement(prefix, localName, namespaceURI);
   }

   public void writeEmptyElement(String localName) throws XMLStreamException {
      this.writer.writeEmptyElement(localName);
   }

   public void writeEndElement() throws XMLStreamException {
      this.writer.writeEndElement();
   }

   public void writeEndDocument() throws XMLStreamException {
      this.writer.writeEndDocument();
   }

   public void close() throws XMLStreamException {
      this.writer.close();
   }

   public void flush() throws XMLStreamException {
      this.writer.flush();
   }

   public void writeAttribute(String localName, String value) throws XMLStreamException {
      this.writer.writeAttribute(localName, value);
   }

   public void writeAttribute(String prefix, String namespaceURI, String localName, String value) throws XMLStreamException {
      this.writer.writeAttribute(prefix, namespaceURI, localName, value);
   }

   public void writeAttribute(String namespaceURI, String localName, String value) throws XMLStreamException {
      this.writer.writeAttribute(namespaceURI, localName, value);
   }

   public void writeNamespace(String prefix, String namespaceURI) throws XMLStreamException {
      this.writer.writeNamespace(prefix, namespaceURI);
   }

   public void writeDefaultNamespace(String namespaceURI) throws XMLStreamException {
      this.writer.writeDefaultNamespace(namespaceURI);
   }

   public void writeComment(String data) throws XMLStreamException {
      this.writer.writeComment(data);
   }

   public void writeProcessingInstruction(String target) throws XMLStreamException {
      this.writer.writeProcessingInstruction(target);
   }

   public void writeProcessingInstruction(String target, String data) throws XMLStreamException {
      this.writer.writeProcessingInstruction(target, data);
   }

   public void writeCData(String data) throws XMLStreamException {
      this.writer.writeCData(data);
   }

   public void writeDTD(String dtd) throws XMLStreamException {
      this.writer.writeDTD(dtd);
   }

   public void writeEntityRef(String name) throws XMLStreamException {
      this.writer.writeEntityRef(name);
   }

   public void writeStartDocument() throws XMLStreamException {
      this.writer.writeStartDocument();
   }

   public void writeStartDocument(String version) throws XMLStreamException {
      this.writer.writeStartDocument(version);
   }

   public void writeStartDocument(String encoding, String version) throws XMLStreamException {
      this.writer.writeStartDocument(encoding, version);
   }

   public void writeCharacters(String text) throws XMLStreamException {
      this.writer.writeCharacters(text);
   }

   public void writeCharacters(char[] text, int start, int len) throws XMLStreamException {
      this.writer.writeCharacters(text, start, len);
   }

   public String getPrefix(String uri) throws XMLStreamException {
      return this.writer.getPrefix(uri);
   }

   public void setPrefix(String prefix, String uri) throws XMLStreamException {
      this.writer.setPrefix(prefix, uri);
   }

   public void setDefaultNamespace(String uri) throws XMLStreamException {
      this.writer.setDefaultNamespace(uri);
   }

   public void setNamespaceContext(NamespaceContext context) throws XMLStreamException {
      this.writer.setNamespaceContext(context);
   }

   public NamespaceContext getNamespaceContext() {
      return this.writer.getNamespaceContext();
   }

   public Object getProperty(String name) throws IllegalArgumentException {
      return this.writer.getProperty(name);
   }
}
