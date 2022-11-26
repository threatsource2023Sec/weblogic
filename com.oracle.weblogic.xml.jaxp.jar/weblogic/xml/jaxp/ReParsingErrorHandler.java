package weblogic.xml.jaxp;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class ReParsingErrorHandler implements ErrorHandler, ReParsingEventQueue.EventHandler {
   private ReParsingEventQueue queue = null;
   private ErrorHandler errorHandler = null;
   private ReParsingStatus status = null;
   private static final int ERROR = 1;
   private static final int FATAL_ERROR = 2;
   private static final int WARNING = 3;

   public void setErrorHandler(ErrorHandler errorHandler) {
      this.errorHandler = errorHandler;
   }

   public ErrorHandler getErrorHandler() {
      return this.errorHandler;
   }

   public void hookStatus(ReParsingStatus status) {
      this.status = status;
   }

   public void error(SAXParseException exception) throws SAXException {
      if (this.status != null) {
         this.status.error = exception;
      }

      if (this.errorHandler != null) {
         EventInfo info = new EventInfo();
         info.exception = exception;
         this.queue.addEvent(info, this, 1);
      }

   }

   public void fatalError(SAXParseException exception) throws SAXException {
      if (this.status != null) {
         this.status.error = exception;
      }

      if (this.errorHandler != null) {
         EventInfo info = new EventInfo();
         info.exception = exception;
         this.queue.addEvent(info, this, 2);
      } else {
         throw exception;
      }
   }

   public void warning(SAXParseException exception) throws SAXException {
      if (this.errorHandler != null) {
         EventInfo info = new EventInfo();
         info.exception = exception;
         this.queue.addEvent(info, this, 3);
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
            this.errorHandler.error(info.exception);
            break;
         case 2:
            this.errorHandler.fatalError(info.exception);
            break;
         case 3:
            this.errorHandler.warning(info.exception);
      }

   }

   private class EventInfo extends ReParsingEventQueue.EventInfo {
      public SAXParseException exception;

      private EventInfo() {
      }

      // $FF: synthetic method
      EventInfo(Object x1) {
         this();
      }
   }
}
