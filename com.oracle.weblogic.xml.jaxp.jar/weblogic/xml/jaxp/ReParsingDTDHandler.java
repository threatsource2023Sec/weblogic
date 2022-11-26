package weblogic.xml.jaxp;

import org.xml.sax.DTDHandler;
import org.xml.sax.SAXException;

public class ReParsingDTDHandler implements DTDHandler, ReParsingEventQueue.EventHandler {
   private ReParsingEventQueue queue = null;
   private DTDHandler dtdHandler = null;
   private static final int NOTATION_DECL = 1;
   private static final int UNPARSED_ENTITY_DECL = 2;

   public void setDTDHandler(DTDHandler dtdHandler) {
      this.dtdHandler = dtdHandler;
   }

   public DTDHandler getDTDHandler() {
      return this.dtdHandler;
   }

   public void notationDecl(String name, String publicId, String systemId) throws SAXException {
      if (this.dtdHandler != null) {
         EventInfo info = new EventInfo();
         info.name = name;
         info.publicId = publicId;
         info.systemId = systemId;
         this.queue.addEvent(info, this, 1);
      }

   }

   public void unparsedEntityDecl(String name, String publicId, String systemId, String notationName) throws SAXException {
      if (this.dtdHandler != null) {
         EventInfo info = new EventInfo();
         info.name = name;
         info.publicId = publicId;
         info.systemId = systemId;
         info.notationName = notationName;
         this.queue.addEvent(info, this, 2);
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
            this.dtdHandler.notationDecl(info.name, info.publicId, info.systemId);
            break;
         case 2:
            this.dtdHandler.unparsedEntityDecl(info.name, info.publicId, info.systemId, info.notationName);
      }

   }

   private class EventInfo extends ReParsingEventQueue.EventInfo {
      public String name;
      public String publicId;
      public String systemId;
      public String notationName;

      private EventInfo() {
      }

      // $FF: synthetic method
      EventInfo(Object x1) {
         this();
      }
   }
}
