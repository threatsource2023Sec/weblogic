package weblogic.xml.sax;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class ContentEventListener implements ContentHandler {
   private String namespaceURI;
   private String localName;
   private String qualifiedName;
   private boolean startElementPassed = false;
   private boolean endDocumentPassed = false;

   public void startElement(String namespaceURI, String localName, String qName, Attributes atts) {
      this.setNamespaceURI(namespaceURI);
      this.setLocalName(localName);
      this.setQualifiedName(qName);
      this.setStartElementPassed(true);
   }

   public String getNamespaceURI() {
      return this.namespaceURI;
   }

   private void setNamespaceURI(String namespaceURI) {
      this.namespaceURI = namespaceURI;
   }

   public String getLocalName() {
      return this.localName;
   }

   private void setLocalName(String localName) {
      this.localName = localName;
   }

   public String getQualifiedName() {
      return this.qualifiedName;
   }

   private void setQualifiedName(String qName) {
      this.qualifiedName = qName;
   }

   protected boolean getStartElementPassed() {
      return this.startElementPassed;
   }

   private void setStartElementPassed(boolean value) {
      this.startElementPassed = value;
   }

   protected boolean getEndDocumentPassed() {
      return this.endDocumentPassed;
   }

   private void setEndDocumentPassed(boolean value) {
      this.endDocumentPassed = value;
   }

   public void characters(char[] ch, int start, int length) throws SAXException {
   }

   public void endDocument() throws SAXException {
      this.setEndDocumentPassed(true);
   }

   public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
   }

   public void endPrefixMapping(String prefix) throws SAXException {
   }

   public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
   }

   public void processingInstruction(String target, String data) throws SAXException {
   }

   public void setDocumentLocator(Locator locator) {
   }

   public void skippedEntity(String name) throws SAXException {
   }

   public void startDocument() throws SAXException {
   }

   public void startPrefixMapping(String prefix, String uri) throws SAXException {
   }
}
