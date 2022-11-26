package com.bea.xml.stream;

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class ReaderDelegate implements XMLStreamReader {
   private XMLStreamReader reader;

   public ReaderDelegate(XMLStreamReader reader) {
      this.reader = reader;
   }

   public void setDelegate(XMLStreamReader reader) {
      this.reader = reader;
   }

   public XMLStreamReader getDelegate() {
      return this.reader;
   }

   public int next() throws XMLStreamException {
      return this.reader.next();
   }

   public int nextTag() throws XMLStreamException {
      return this.reader.nextTag();
   }

   public String getElementText() throws XMLStreamException {
      return this.reader.getElementText();
   }

   public void require(int type, String namespaceURI, String localName) throws XMLStreamException {
      this.reader.require(type, namespaceURI, localName);
   }

   public boolean hasNext() throws XMLStreamException {
      return this.reader.hasNext();
   }

   public void close() throws XMLStreamException {
      this.reader.close();
   }

   public String getNamespaceURI(String prefix) {
      return this.reader.getNamespaceURI(prefix);
   }

   public NamespaceContext getNamespaceContext() {
      return this.reader.getNamespaceContext();
   }

   public boolean isStartElement() {
      return this.reader.isStartElement();
   }

   public boolean isEndElement() {
      return this.reader.isEndElement();
   }

   public boolean isCharacters() {
      return this.reader.isCharacters();
   }

   public boolean isWhiteSpace() {
      return this.reader.isWhiteSpace();
   }

   public QName getAttributeName(int index) {
      return this.reader.getAttributeName(index);
   }

   public int getTextCharacters(int sourceStart, char[] target, int targetStart, int length) throws XMLStreamException {
      return this.reader.getTextCharacters(sourceStart, target, targetStart, length);
   }

   public String getAttributeValue(String namespaceUri, String localName) {
      return this.reader.getAttributeValue(namespaceUri, localName);
   }

   public int getAttributeCount() {
      return this.reader.getAttributeCount();
   }

   public String getAttributePrefix(int index) {
      return this.reader.getAttributePrefix(index);
   }

   public String getAttributeNamespace(int index) {
      return this.reader.getAttributeNamespace(index);
   }

   public String getAttributeLocalName(int index) {
      return this.reader.getAttributeLocalName(index);
   }

   public String getAttributeType(int index) {
      return this.reader.getAttributeType(index);
   }

   public String getAttributeValue(int index) {
      return this.reader.getAttributeValue(index);
   }

   public boolean isAttributeSpecified(int index) {
      return this.reader.isAttributeSpecified(index);
   }

   public int getNamespaceCount() {
      return this.reader.getNamespaceCount();
   }

   public String getNamespacePrefix(int index) {
      return this.reader.getNamespacePrefix(index);
   }

   public String getNamespaceURI(int index) {
      return this.reader.getNamespaceURI(index);
   }

   public int getEventType() {
      return this.reader.getEventType();
   }

   public String getText() {
      return this.reader.getText();
   }

   public char[] getTextCharacters() {
      return this.reader.getTextCharacters();
   }

   public int getTextStart() {
      return this.reader.getTextStart();
   }

   public int getTextLength() {
      return this.reader.getTextLength();
   }

   public String getEncoding() {
      return this.reader.getEncoding();
   }

   public boolean hasText() {
      return this.reader.hasText();
   }

   public Location getLocation() {
      return this.reader.getLocation();
   }

   public QName getName() {
      return this.reader.getName();
   }

   public String getLocalName() {
      return this.reader.getLocalName();
   }

   public boolean hasName() {
      return this.reader.hasName();
   }

   public String getNamespaceURI() {
      return this.reader.getNamespaceURI();
   }

   public String getPrefix() {
      return this.reader.getPrefix();
   }

   public String getVersion() {
      return this.reader.getVersion();
   }

   public boolean isStandalone() {
      return this.reader.isStandalone();
   }

   public boolean standaloneSet() {
      return this.reader.standaloneSet();
   }

   public String getCharacterEncodingScheme() {
      return this.reader.getCharacterEncodingScheme();
   }

   public String getPITarget() {
      return this.reader.getPITarget();
   }

   public String getPIData() {
      return this.reader.getPIData();
   }

   public Object getProperty(String name) {
      return this.reader.getProperty(name);
   }
}
