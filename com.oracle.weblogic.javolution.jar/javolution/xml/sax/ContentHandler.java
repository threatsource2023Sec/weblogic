package javolution.xml.sax;

import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public interface ContentHandler {
   void setDocumentLocator(Locator var1);

   void startDocument() throws SAXException;

   void endDocument() throws SAXException;

   void startPrefixMapping(CharSequence var1, CharSequence var2) throws SAXException;

   void endPrefixMapping(CharSequence var1) throws SAXException;

   void startElement(CharSequence var1, CharSequence var2, CharSequence var3, Attributes var4) throws SAXException;

   void endElement(CharSequence var1, CharSequence var2, CharSequence var3) throws SAXException;

   void characters(char[] var1, int var2, int var3) throws SAXException;

   void ignorableWhitespace(char[] var1, int var2, int var3) throws SAXException;

   void processingInstruction(CharSequence var1, CharSequence var2) throws SAXException;

   void skippedEntity(CharSequence var1) throws SAXException;
}
