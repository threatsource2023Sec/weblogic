package weblogic.xml.babel.baseparser;

import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public class SAXAdapter {
   private SAXElementFactory saxElementFactory = new SAXElementFactory();

   public void startPrefixMapping(String prefix, String uri, ContentHandler contentHandler) throws SAXException {
      contentHandler.startPrefixMapping(prefix, uri);
   }

   public void endPrefixMapping(String prefix, ContentHandler contentHandler) throws SAXException {
      contentHandler.endPrefixMapping(prefix);
   }

   public void startElement(StartElement startElement, ContentHandler contentHandler) throws SAXException {
      String var10001 = SAXElementFactory.nullToEmptyString(startElement.uri);
      String var10002 = startElement.name;
      String var10003 = startElement.getRawName();
      SAXElementFactory var10004 = this.saxElementFactory;
      contentHandler.startElement(var10001, var10002, var10003, SAXElementFactory.createAttributes(startElement.attributes));
   }

   public void endElement(EndElement endElement, ContentHandler contentHandler) throws SAXException {
      contentHandler.endElement(SAXElementFactory.nullToEmptyString(endElement.uri), endElement.name, endElement.getRawName());
   }

   public void characters(CharDataElement charDataElement, ContentHandler contentHandler) throws SAXException {
      contentHandler.characters(charDataElement.getArray(), 0, charDataElement.length);
   }

   public void characters(Space spaceElement, ContentHandler contentHandler) throws SAXException {
      contentHandler.characters(spaceElement.getArray(), 0, spaceElement.length);
   }

   public void processingInstruction(ProcessingInstruction processingInstruction, ContentHandler contentHandler) throws SAXException {
      contentHandler.processingInstruction(processingInstruction.getName(), SAXElementFactory.nullToEmptyString(processingInstruction.getTarget()));
   }
}
