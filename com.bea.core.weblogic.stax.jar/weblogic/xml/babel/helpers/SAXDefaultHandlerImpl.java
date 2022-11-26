package weblogic.xml.babel.helpers;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXDefaultHandlerImpl extends DefaultHandler implements ContentHandler, EntityResolver, DTDHandler, ErrorHandler {
   private Locator locator;
   private boolean VERBOSE = true;

   public void setVerbose() {
      this.VERBOSE = true;
   }

   public void setQuiet() {
      this.VERBOSE = false;
   }

   public void setDocumentLocator(Locator locator) {
      this.locator = locator;
   }

   public void startDocument() throws SAXException {
      if (this.VERBOSE) {
         System.out.println("[startDocument]");
      }

   }

   public void endDocument() throws SAXException {
      if (this.VERBOSE) {
         System.out.println("[endDocument]");
      }

   }

   public void startPrefixMapping(String prefix, String uri) throws SAXException {
      if (this.VERBOSE) {
         System.out.println("[startPrefixMapping]\t[prefix:'" + prefix + "' mapped to URI: '" + uri + "']");
      }

   }

   public void endPrefixMapping(String prefix) throws SAXException {
      if (this.VERBOSE) {
         System.out.println("[endPrefixMapping]\t[prefix:'" + prefix + "']");
      }

   }

   public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
      if (this.VERBOSE) {
         System.out.println("[startElement]\t[namespaceURI:'" + namespaceURI + "']\n\t[localName:'" + localName + "' qName:'" + qName + "']");
      }

      if (this.VERBOSE) {
         for(int i = 0; i < atts.getLength(); ++i) {
            System.out.println("\t\t[attribute][URI:'" + atts.getURI(i) + "' localName:'" + atts.getLocalName(i) + "'\n\t\tqName:'" + atts.getQName(i) + "' type:" + atts.getType(i) + " = " + atts.getValue(i) + "]");
         }
      }

   }

   public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
      if (this.VERBOSE) {
         System.out.println("[endElement]\t[namespaceURI:'" + namespaceURI + "' localName:'" + localName + "' qName:'" + qName + "']");
      }

   }

   public void characters(char[] ch, int start, int length) throws SAXException {
      if (this.VERBOSE) {
         System.out.println("[characters]\t['" + new String(ch, start, length) + "']");
      }

   }

   public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
      if (this.VERBOSE) {
         System.out.println("[ignorableWhitespace]\t['" + new String(ch, start, length) + "']");
      }

   }

   public void processingInstruction(String target, String data) throws SAXException {
      if (this.VERBOSE) {
         System.out.println("[processingInstruction]\t[target:'" + target + "' data:'" + data + "']");
      }

   }

   public void skippedEntity(String name) throws SAXException {
      if (this.VERBOSE) {
         System.out.println("[skippedEntity]\t['" + name + "']");
      }

   }

   public InputSource resolveEntity(String publicId, String systemId) throws SAXException {
      return null;
   }

   public void notationDecl(String name, String publicId, String systemId) throws SAXException {
   }

   public void unparsedEntityDecl(String name, String publicId, String systemId, String notationName) throws SAXException {
   }

   public void warning(SAXParseException e) throws SAXException {
   }

   public void error(SAXParseException e) throws SAXException {
   }

   public void fatalError(SAXParseException e) throws SAXException {
      throw e;
   }
}
