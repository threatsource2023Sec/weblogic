package javolution.xml.sax;

import org.xml.sax.ErrorHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class DefaultHandler implements ContentHandler, ErrorHandler {
   public void warning(SAXParseException var1) throws SAXException {
   }

   public void error(SAXParseException var1) throws SAXException {
   }

   public void fatalError(SAXParseException var1) throws SAXException {
      throw var1;
   }

   public void setDocumentLocator(Locator var1) {
   }

   public void startDocument() throws SAXException {
   }

   public void endDocument() throws SAXException {
   }

   public void startPrefixMapping(CharSequence var1, CharSequence var2) throws SAXException {
   }

   public void endPrefixMapping(CharSequence var1) throws SAXException {
   }

   public void startElement(CharSequence var1, CharSequence var2, CharSequence var3, Attributes var4) throws SAXException {
   }

   public void endElement(CharSequence var1, CharSequence var2, CharSequence var3) throws SAXException {
   }

   public void characters(char[] var1, int var2, int var3) throws SAXException {
   }

   public void ignorableWhitespace(char[] var1, int var2, int var3) throws SAXException {
   }

   public void processingInstruction(CharSequence var1, CharSequence var2) throws SAXException {
   }

   public void skippedEntity(CharSequence var1) throws SAXException {
   }

   protected final void startElement(CharSequence var1, CharSequence var2, CharSequence var3, org.xml.sax.Attributes var4) throws SAXException {
      throw new UnsupportedOperationException();
   }
}
