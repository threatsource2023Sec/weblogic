package weblogic.xml.jaxp;

import org.xml.sax.SAXException;
import org.xml.sax.ext.DeclHandler;

public class ReParsingDeclHandler implements DeclHandler, ReParsingEventQueue.EventHandler {
   private static final int ATTRIBUTE_DECL = 1;
   private static final int ELEMENT_DECL = 2;
   private static final int EXTERNAL_ENTITY_DECL = 3;
   private static final int INTERNAL_ENTITY_DECL = 4;
   private ReParsingEventQueue queue = null;
   private DeclHandler declHandler = null;

   public void setDeclHandler(DeclHandler declHandler) {
      this.declHandler = declHandler;
   }

   public DeclHandler getDeclHandler() {
      return this.declHandler;
   }

   public void attributeDecl(String name, String name2, String type, String mode, String value) throws SAXException {
      if (this.declHandler != null) {
         EventInfo info = new EventInfo();
         info.name = name;
         info.name2 = name2;
         info._type = type;
         info.mode = mode;
         info.value = value;
         this.queue.addEvent(info, this, 1);
      }

   }

   public void elementDecl(String name, String model) throws SAXException {
      if (this.declHandler != null) {
         EventInfo info = new EventInfo();
         info.name = name;
         info.model = model;
         this.queue.addEvent(info, this, 2);
      }

   }

   public void externalEntityDecl(String name, String publicId, String systemId) throws SAXException {
      if (this.declHandler != null) {
         EventInfo info = new EventInfo();
         info.name = name;
         info.publicId = publicId;
         info.systemId = systemId;
         this.queue.addEvent(info, this, 3);
      }

   }

   public void internalEntityDecl(String name, String value) throws SAXException {
      if (this.declHandler != null) {
         EventInfo info = new EventInfo();
         info.name = name;
         info.value = value;
         this.queue.addEvent(info, this, 4);
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
            this.declHandler.attributeDecl(info.name, info.name2, info._type, info.mode, info.value);
            break;
         case 2:
            this.declHandler.elementDecl(info.name, info.model);
            break;
         case 3:
            this.declHandler.externalEntityDecl(info.name, info.publicId, info.systemId);
            break;
         case 4:
            this.declHandler.internalEntityDecl(info.name, info.value);
      }

   }

   private class EventInfo extends ReParsingEventQueue.EventInfo {
      public String _type;
      public String systemId;
      public String publicId;
      public String model;
      public String value;
      public String mode;
      public String name2;
      public String name;

      private EventInfo() {
      }

      // $FF: synthetic method
      EventInfo(Object x1) {
         this();
      }
   }
}
