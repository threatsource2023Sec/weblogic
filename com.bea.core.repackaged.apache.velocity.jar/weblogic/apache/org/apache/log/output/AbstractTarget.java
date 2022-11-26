package weblogic.apache.org.apache.log.output;

import weblogic.apache.org.apache.log.ErrorAware;
import weblogic.apache.org.apache.log.ErrorHandler;
import weblogic.apache.org.apache.log.LogEvent;
import weblogic.apache.org.apache.log.LogTarget;

public abstract class AbstractTarget implements LogTarget, ErrorAware {
   private ErrorHandler m_errorHandler;
   private boolean m_isOpen;

   public synchronized void setErrorHandler(ErrorHandler errorHandler) {
      this.m_errorHandler = errorHandler;
   }

   protected synchronized boolean isOpen() {
      return this.m_isOpen;
   }

   protected synchronized void open() {
      if (!this.isOpen()) {
         this.m_isOpen = true;
      }

   }

   public synchronized void processEvent(LogEvent event) {
      if (!this.isOpen()) {
         this.getErrorHandler().error("Writing event to closed stream.", (Throwable)null, event);
      } else {
         try {
            this.doProcessEvent(event);
         } catch (Throwable var3) {
            this.getErrorHandler().error("Unknown error writing event.", var3, event);
         }

      }
   }

   protected abstract void doProcessEvent(LogEvent var1) throws Exception;

   public synchronized void close() {
      if (this.isOpen()) {
         this.m_isOpen = false;
      }

   }

   protected final ErrorHandler getErrorHandler() {
      return this.m_errorHandler;
   }

   /** @deprecated */
   protected final void error(String message, Throwable throwable) {
      this.getErrorHandler().error(message, throwable, (LogEvent)null);
   }
}
