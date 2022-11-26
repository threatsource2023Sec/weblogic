package weblogic.xml.jaxp;

import org.xml.sax.AttributeList;
import org.xml.sax.DocumentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class ReParsingDocumentHandler implements DocumentHandler, ReParsingEventQueue.EventHandler {
   private ReParsingEventQueue queue = null;
   private DocumentHandler documentHandler = null;
   private static final int CHARACTERS = 1;
   private static final int END_DOCUMENT = 2;
   private static final int END_ELEMENT = 3;
   private static final int IGNORABLE_WHITESPACE = 5;
   private static final int PROCESSING_INSTRUCTION = 6;
   private static final int SET_DOCUMENT_LOCATOR = 7;
   private static final int START_DOCUMENT = 9;
   private static final int START_ELEMENT = 10;

   public void setDocumentHandler(DocumentHandler documentHandler) {
      this.documentHandler = documentHandler;
   }

   public DocumentHandler getDocumentHandler() {
      return this.documentHandler;
   }

   public void characters(char[] ch, int start, int length) throws SAXException {
      if (this.documentHandler != null) {
         EventInfo info = new EventInfo();
         info.ch = ch;
         info.start = start;
         info.length = length;
         this.queue.addEvent(info, this, 1);
      }

   }

   public void endDocument() throws SAXException {
      if (this.documentHandler != null) {
         EventInfo info = new EventInfo();
         this.queue.addEvent(info, this, 2);
      }

   }

   public void endElement(String name) throws SAXException {
      if (this.documentHandler != null) {
         EventInfo info = new EventInfo();
         info.name = name;
         this.queue.addEvent(info, this, 3);
      }

   }

   public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
      if (this.documentHandler != null) {
         EventInfo info = new EventInfo();
         info.ch = ch;
         info.start = start;
         info.length = length;
         this.queue.addEvent(info, this, 5);
      }

   }

   public void processingInstruction(String target, String data) throws SAXException {
      if (this.documentHandler != null) {
         EventInfo info = new EventInfo();
         info.target = target;
         info.data = data;
         this.queue.addEvent(info, this, 6);
      }

   }

   public void setDocumentLocator(Locator locator) {
      if (this.documentHandler != null) {
         EventInfo info = new EventInfo();
         info.locator = locator;
         this.queue.addEvent(info, this, 7);
      }

   }

   public void startDocument() throws SAXException {
      if (this.documentHandler != null) {
         EventInfo info = new EventInfo();
         this.queue.addEvent(info, this, 9);
      }

   }

   public void startElement(String name, AttributeList atts) throws SAXException {
      if (this.documentHandler != null) {
         EventInfo info = new EventInfo();
         info.name = name;
         info.atts = atts;
         this.queue.addEvent(info, this, 10);
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
            this.documentHandler.characters(info.ch, info.start, info.length);
            break;
         case 2:
            this.documentHandler.endDocument();
            break;
         case 3:
            this.documentHandler.endElement(info.name);
         case 4:
         case 8:
         default:
            break;
         case 5:
            this.documentHandler.ignorableWhitespace(info.ch, info.start, info.length);
            break;
         case 6:
            this.documentHandler.processingInstruction(info.target, info.data);
            break;
         case 7:
            this.documentHandler.setDocumentLocator(info.locator);
            break;
         case 9:
            this.documentHandler.startDocument();
            break;
         case 10:
            this.documentHandler.startElement(info.name, info.atts);
      }

   }

   private class EventInfo extends ReParsingEventQueue.EventInfo {
      public char[] ch;
      public int start;
      public int length;
      public String target;
      public String data;
      public Locator locator;
      public String name;
      public AttributeList atts;

      private EventInfo() {
      }

      // $FF: synthetic method
      EventInfo(Object x1) {
         this();
      }
   }
}
