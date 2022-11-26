package weblogic.deployment.jms;

import java.util.ArrayList;
import java.util.Iterator;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;

public class JMSSessionPoolExceptionListener implements ExceptionListener {
   private ArrayList exceptionListeners = new ArrayList(2);

   protected JMSSessionPoolExceptionListener() {
   }

   public void addExceptionListener(ExceptionListener listener) {
      synchronized(this.exceptionListeners) {
         this.exceptionListeners.add(listener);
      }
   }

   public void removeExceptionListener(ExceptionListener listener) {
      synchronized(this.exceptionListeners) {
         int index = this.exceptionListeners.indexOf(listener);
         if (index >= 0) {
            this.exceptionListeners.remove(index);
         }

      }
   }

   public void onException(JMSException jmse) {
      if (JMSPoolDebug.logger.isDebugEnabled()) {
         JMSPoolDebug.logger.debug("JMSSessionPool.onException() " + jmse);
      }

      synchronized(this.exceptionListeners) {
         Iterator listeners = this.exceptionListeners.iterator();

         while(listeners.hasNext()) {
            ExceptionListener listener = (ExceptionListener)listeners.next();

            try {
               listener.onException(jmse);
            } catch (Throwable var7) {
               if (JMSPoolDebug.logger.isDebugEnabled()) {
                  JMSPoolDebug.logger.debug("Error in an onException method", var7);
               }
            }
         }

      }
   }
}
