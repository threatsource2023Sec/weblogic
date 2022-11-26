package weblogic.xml.util;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class DebugHandler implements EntityResolver, DTDHandler, ContentHandler, ErrorHandler {
   public InputSource resolveEntity(String publicId, String systemId) throws SAXException {
      return null;
   }

   public void notationDecl(String name, String publicId, String systemId) throws SAXException {
      System.out.println("NOTATION[[" + name + "][" + publicId + "][" + systemId + "]]");
   }

   public void unparsedEntityDecl(String name, String publicId, String systemId, String notationName) throws SAXException {
      System.out.println("UNPARSEDENTITYDECL[[" + name + "][" + publicId + "][" + systemId + "][" + notationName + "]]");
   }

   public void setDocumentLocator(Locator locator) {
   }

   public void startDocument() throws SAXException {
      System.out.println("STARTDOCUMENT[]");
   }

   public void endDocument() throws SAXException {
      System.out.println("ENDDOCUMENT[]");
   }

   public void startPrefixMapping(String prefix, String uri) throws SAXException {
      System.out.println("STARTPREFIXMAPPING[" + prefix + "][" + uri + "]");
   }

   public void endPrefixMapping(String prefix) throws SAXException {
      System.out.println("ENDPREFIXMAPPING[" + prefix + "]");
   }

   public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      System.out.print("STARTELEMENT[" + uri + "][" + localName + "][" + qName + "][");

      for(int i = 0; i < attributes.getLength(); ++i) {
         System.out.print("['" + attributes.getURI(i) + "':" + attributes.getLocalName(i) + "='" + attributes.getValue(i) + "']");
      }

      System.out.println("]");
   }

   public void endElement(String uri, String localName, String qName) throws SAXException {
      System.out.println("ENDELEMENT[" + uri + "][" + localName + "][" + qName + "][");
   }

   public void characters(char[] ch, int start, int length) throws SAXException {
      System.out.println("CHARACTERS[" + new String(ch, start, length) + "]");
   }

   public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
      System.out.println("CHARACTERS[" + new String(ch, start, length) + "]");
   }

   public void processingInstruction(String target, String data) throws SAXException {
      System.out.println("PI[" + target + "][" + data + "]");
   }

   public void skippedEntity(String name) throws SAXException {
      System.out.println("SKIPPEDENTRY[" + name + "]");
   }

   public void warning(SAXParseException e) throws SAXException {
      throw e;
   }

   public void error(SAXParseException e) throws SAXException {
      throw e;
   }

   public void fatalError(SAXParseException e) throws SAXException {
      throw e;
   }
}
