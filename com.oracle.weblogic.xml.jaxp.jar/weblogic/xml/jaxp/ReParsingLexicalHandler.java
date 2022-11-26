package weblogic.xml.jaxp;

import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;

public class ReParsingLexicalHandler implements LexicalHandler, ReParsingEventQueue.EventHandler {
   private static final int COMMENT = 1;
   private static final int END_CDATA = 2;
   private static final int END_DTD = 3;
   private static final int END_ENTITY = 4;
   private static final int START_CDATA = 5;
   private static final int START_DTD = 6;
   private static final int START_ENTITY = 7;
   private ReParsingEventQueue queue = null;
   private LexicalHandler lexicalHandler = null;

   public void setLexicalHandler(LexicalHandler declHandler) {
      this.lexicalHandler = declHandler;
   }

   public LexicalHandler getLexicalHandler() {
      return this.lexicalHandler;
   }

   public void comment(char[] ch, int start, int length) throws SAXException {
      if (this.lexicalHandler != null) {
         EventInfo info = new EventInfo();
         info.ch = ch;
         info.start = start;
         info.length = length;
         this.queue.addEvent(info, this, 1);
      }

   }

   public void endCDATA() throws SAXException {
      if (this.lexicalHandler != null) {
         EventInfo info = new EventInfo();
         this.queue.addEvent(info, this, 2);
      }

   }

   public void endDTD() throws SAXException {
      if (this.lexicalHandler != null) {
         EventInfo info = new EventInfo();
         this.queue.addEvent(info, this, 3);
      }

   }

   public void endEntity(String name) throws SAXException {
      if (this.lexicalHandler != null) {
         EventInfo info = new EventInfo();
         info.name = name;
         this.queue.addEvent(info, this, 4);
      }

   }

   public void startCDATA() throws SAXException {
      if (this.lexicalHandler != null) {
         EventInfo info = new EventInfo();
         this.queue.addEvent(info, this, 5);
      }

   }

   public void startDTD(String name, String publicId, String systemId) throws SAXException {
      if (this.lexicalHandler != null) {
         EventInfo info = new EventInfo();
         info.name = name;
         info.publicId = publicId;
         info.systemId = systemId;
         this.queue.addEvent(info, this, 6);
      }

   }

   public void startEntity(String name) throws SAXException {
      if (this.lexicalHandler != null) {
         EventInfo info = new EventInfo();
         info.name = name;
         this.queue.addEvent(info, this, 7);
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
            this.lexicalHandler.comment(info.ch, info.start, info.length);
            break;
         case 2:
            this.lexicalHandler.endCDATA();
            break;
         case 3:
            this.lexicalHandler.endDTD();
            break;
         case 4:
            this.lexicalHandler.endEntity(info.name);
            break;
         case 5:
            this.lexicalHandler.startCDATA();
            break;
         case 6:
            this.lexicalHandler.startDTD(info.name, info.publicId, info.systemId);
            break;
         case 7:
            this.lexicalHandler.startEntity(info.name);
      }

   }

   private class EventInfo extends ReParsingEventQueue.EventInfo {
      public String systemId;
      public String publicId;
      public String name;
      public int length;
      public int start;
      public char[] ch;

      private EventInfo() {
      }

      // $FF: synthetic method
      EventInfo(Object x1) {
         this();
      }
   }
}
