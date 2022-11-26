package weblogic.xml.babel.adapters;

import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import weblogic.xml.babel.baseparser.PrefixMapping;
import weblogic.xml.babel.baseparser.SymbolTable;
import weblogic.xml.stream.Location;
import weblogic.xml.stream.events.AttributeImpl;
import weblogic.xml.stream.events.ChangePrefixMappingEvent;
import weblogic.xml.stream.events.CharacterDataEvent;
import weblogic.xml.stream.events.EndDocumentEvent;
import weblogic.xml.stream.events.EndElementEvent;
import weblogic.xml.stream.events.EndPrefixMappingEvent;
import weblogic.xml.stream.events.LocationImpl;
import weblogic.xml.stream.events.Name;
import weblogic.xml.stream.events.ProcessingInstructionEvent;
import weblogic.xml.stream.events.SpaceEvent;
import weblogic.xml.stream.events.StartDocumentEvent;
import weblogic.xml.stream.events.StartElementEvent;
import weblogic.xml.stream.events.StartPrefixMappingEvent;

public class SAXAdapter implements ContentHandler, DTDHandler, ErrorHandler {
   private boolean debug = false;
   protected ElementConsumer consumer;
   protected Locator locator;
   protected SymbolTable nameSpaceTable;

   public SAXAdapter() {
   }

   public SAXAdapter(ElementConsumer consumer) {
      this.consumer = consumer;
   }

   public void setElementConsumer(ElementConsumer consumer) {
      this.consumer = consumer;
   }

   public ElementConsumer getElementConsumer() {
      return this.consumer;
   }

   public Location getLocation(Locator locator) {
      return new LocationImpl(locator.getColumnNumber(), locator.getLineNumber(), locator.getPublicId(), locator.getSystemId());
   }

   public void setDocumentLocator(Locator locator) {
      this.locator = locator;
   }

   public void startDocument() throws SAXException {
      if (this.debug) {
         System.out.println("[StartDocument]");
      }

      this.consumer.add(new StartDocumentEvent());
      this.nameSpaceTable = new SymbolTable();
      this.nameSpaceTable.put("", (String)null);
      this.nameSpaceTable.put("xml", "http://www.w3.org/XML/1998/namespace");
   }

   public void endDocument() throws SAXException {
      if (this.debug) {
         System.out.println("[EndDocument]");
      }

      this.consumer.add(new EndDocumentEvent());
   }

   public void startPrefixMapping(String prefix, String uri) throws SAXException {
      this.nameSpaceTable.openScope();
      if (this.debug) {
         System.out.println("[startPrefixMapping[" + prefix + "][" + uri + "]]");
      }

      this.consumer.add(new StartPrefixMappingEvent(prefix, uri));
      this.nameSpaceTable.put(prefix, uri);
   }

   public void endPrefixMapping(String prefix) throws SAXException {
      List outOfScopeNameSpace = this.nameSpaceTable.closeScope();
      if (this.debug) {
         System.out.println("[endPrefixMapping[" + prefix + "]]");
      }

      int i = 0;

      for(int len = outOfScopeNameSpace.size(); i < len; ++i) {
         PrefixMapping prefixMapping = (PrefixMapping)outOfScopeNameSpace.get(i);
         if (prefixMapping.getUri() == null) {
            this.consumer.add(new EndPrefixMappingEvent(prefixMapping.getPrefix()));
         } else {
            this.consumer.add(new ChangePrefixMappingEvent(prefixMapping.getOldUri(), prefixMapping.getUri(), prefixMapping.getPrefix()));
         }
      }

   }

   protected String getPrefix(String qName) {
      int index = qName.indexOf(58);
      return index == -1 ? null : qName.substring(0, index);
   }

   public String getUri(String uri) {
      if (uri.equals("")) {
         uri = null;
      }

      return uri;
   }

   public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
      StartElementEvent event = new StartElementEvent(new Name(this.getUri(namespaceURI), localName, this.getPrefix(qName)));

      for(int i = 0; i < atts.getLength(); ++i) {
         String attLocalName = atts.getLocalName(i);
         String attPrefix = this.getPrefix(atts.getQName(i));
         if (attLocalName.equals("xmlns")) {
            event.addNamespace(new AttributeImpl(new Name(attLocalName), atts.getValue(i), atts.getType(i)));
         } else if (attPrefix != null && attPrefix.equals("xmlns")) {
            event.addNamespace(new AttributeImpl(new Name((String)null, attLocalName, attPrefix), atts.getValue(i), atts.getType(i)));
         } else {
            event.addAttribute(new AttributeImpl(new Name(this.getUri(atts.getURI(i)), atts.getLocalName(i), this.getPrefix(atts.getQName(i))), atts.getValue(i), atts.getType(i)));
         }
      }

      if (this.debug) {
         System.out.print("[startElement[" + event + "]");
      }

      this.consumer.add(event);
   }

   public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
      if (this.debug) {
         System.out.println("[endElement[" + qName + "]");
      }

      this.consumer.add(new EndElementEvent(new Name(this.getUri(namespaceURI), localName, this.getPrefix(qName))));
   }

   protected boolean isSpace(char c) {
      return c == ' ' || c == '\t' || c == '\r' || c == '\n';
   }

   public void characters(char[] ch, int start, int length) throws SAXException {
      if (this.debug) {
         System.out.print("[Characters[]");
      }

      boolean space = true;

      for(int i = 0; i < length; ++i) {
         if (!this.isSpace(ch[start + i])) {
            space = false;
            break;
         }
      }

      if (space) {
         this.consumer.add(new SpaceEvent(new String(ch, start, length)));
      } else {
         this.consumer.add(new CharacterDataEvent(new String(ch, start, length)));
      }

   }

   public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
      this.consumer.add(new SpaceEvent(new String(ch, start, length)));
   }

   public void processingInstruction(String target, String data) throws SAXException {
      if (this.debug) {
         System.out.println("[ProcessingInstruction]");
      }

      this.consumer.add(new ProcessingInstructionEvent(new Name(target), data));
   }

   public void skippedEntity(String name) throws SAXException {
   }

   public void warning(SAXParseException exception) throws SAXException {
      throw new SAXException(exception);
   }

   public void error(SAXParseException exception) throws SAXException {
      throw new SAXException(exception);
   }

   public void fatalError(SAXParseException exception) throws SAXException {
      throw new SAXException(exception);
   }

   public void notationDecl(String name, String publicId, String systemId) throws SAXException {
   }

   public void unparsedEntityDecl(String name, String publicId, String systemId, String notationName) throws SAXException {
   }
}
