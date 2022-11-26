package weblogic.xml.jaxp;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class ReParsingContentHandler implements ContentHandler, ReParsingEventQueue.EventHandler {
   private ReParsingEventQueue queue = null;
   private ContentHandler contentHandler = null;
   private static final int CHARACTERS = 1;
   private static final int END_DOCUMENT = 2;
   private static final int END_ELEMENT = 3;
   private static final int END_PREFIX_MAPPING = 4;
   private static final int IGNORABLE_WHITESPACE = 5;
   private static final int PROCESSING_INSTRUCTION = 6;
   private static final int SET_DOCUMENT_LOCATOR = 7;
   private static final int SKIPPED_ENTITY = 8;
   private static final int START_DOCUMENT = 9;
   private static final int START_ELEMENT = 10;
   private static final int START_PREFIX_MAPPING = 11;

   public void setContentHandler(ContentHandler contentHandler) {
      this.contentHandler = contentHandler;
   }

   public ContentHandler getContentHandler() {
      return this.contentHandler;
   }

   public void characters(char[] ch, int start, int length) throws SAXException {
      if (this.contentHandler != null) {
         EventInfo info = new EventInfo();
         info.ch = ch;
         info.start = start;
         info.length = length;
         this.queue.addEvent(info, this, 1);
      }

   }

   public void endDocument() throws SAXException {
      if (this.contentHandler != null) {
         EventInfo info = new EventInfo();
         this.queue.addEvent(info, this, 2);
      }

   }

   public void endElement(String uri, String localName, String qName) throws SAXException {
      if (this.contentHandler != null) {
         EventInfo info = new EventInfo();
         info.uri = uri;
         info.localName = localName;
         info.qName = qName;
         this.queue.addEvent(info, this, 3);
      }

   }

   public void endPrefixMapping(String prefix) throws SAXException {
      if (this.contentHandler != null) {
         EventInfo info = new EventInfo();
         info.prefix = prefix;
         this.queue.addEvent(info, this, 4);
      }

   }

   public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
      if (this.contentHandler != null) {
         EventInfo info = new EventInfo();
         info.ch = ch;
         info.start = start;
         info.length = length;
         this.queue.addEvent(info, this, 5);
      }

   }

   public void processingInstruction(String target, String data) throws SAXException {
      if (this.contentHandler != null) {
         EventInfo info = new EventInfo();
         info.target = target;
         info.data = data;
         this.queue.addEvent(info, this, 6);
      }

   }

   public void setDocumentLocator(Locator locator) {
      if (this.contentHandler != null) {
         EventInfo info = new EventInfo();
         info.locator = locator;
         this.queue.addEvent(info, this, 7);
      }

   }

   public void skippedEntity(String name) throws SAXException {
      if (this.contentHandler != null) {
         EventInfo info = new EventInfo();
         info.name = name;
         this.queue.addEvent(info, this, 8);
      }

   }

   public void startDocument() throws SAXException {
      if (this.contentHandler != null) {
         EventInfo info = new EventInfo();
         this.queue.addEvent(info, this, 9);
      }

   }

   public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
      if (this.contentHandler != null) {
         EventInfo info = new EventInfo();
         info.uri = uri;
         info.localName = localName;
         info.qName = qName;
         Attributes duplecatedAtts = new AttributesImpl(atts);
         info.atts = duplecatedAtts;
         this.queue.addEvent(info, this, 10);
      }

   }

   public void startPrefixMapping(String prefix, String uri) throws SAXException {
      if (this.contentHandler != null) {
         EventInfo info = new EventInfo();
         info.prefix = prefix;
         info.uri = uri;
         this.queue.addEvent(info, this, 11);
      }

   }

   public void registerQueue(ReParsingEventQueue queue) {
      this.queue = queue;
   }

   public void sendEvent(ReParsingEventQueue.EventInfo eventInfo) throws SAXException {
      EventInfo info = (EventInfo)eventInfo;
      int type = info.type;
      switch (type) {
         case 1:
            this.contentHandler.characters(info.ch, info.start, info.length);
            break;
         case 2:
            this.contentHandler.endDocument();
            break;
         case 3:
            this.contentHandler.endElement(info.uri, info.localName, info.qName);
            break;
         case 4:
            this.contentHandler.endPrefixMapping(info.prefix);
            break;
         case 5:
            this.contentHandler.ignorableWhitespace(info.ch, info.start, info.length);
            break;
         case 6:
            this.contentHandler.processingInstruction(info.target, info.data);
            break;
         case 7:
            this.contentHandler.setDocumentLocator(info.locator);
            break;
         case 8:
            this.contentHandler.skippedEntity(info.name);
            break;
         case 9:
            this.contentHandler.startDocument();
            break;
         case 10:
            this.contentHandler.startElement(info.uri, info.localName, info.qName, info.atts);
            break;
         case 11:
            this.contentHandler.startPrefixMapping(info.prefix, info.uri);
      }

   }

   private class EventInfo extends ReParsingEventQueue.EventInfo {
      public char[] ch;
      public int start;
      public int length;
      public String uri;
      public String localName;
      public String qName;
      public String prefix;
      public String target;
      public String data;
      public Locator locator;
      public String name;
      public Attributes atts;

      private EventInfo() {
      }

      // $FF: synthetic method
      EventInfo(Object x1) {
         this();
      }
   }
}
