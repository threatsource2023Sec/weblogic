package weblogic.apache.org.apache.log.output;

import java.util.LinkedList;
import weblogic.apache.org.apache.log.ErrorAware;
import weblogic.apache.org.apache.log.ErrorHandler;
import weblogic.apache.org.apache.log.LogEvent;
import weblogic.apache.org.apache.log.LogTarget;

public class AsyncLogTarget extends AbstractTarget implements Runnable {
   private final LinkedList m_list;
   private final int m_queueSize;
   private final LogTarget m_logTarget;

   public AsyncLogTarget(LogTarget logTarget) {
      this(logTarget, 15);
   }

   public AsyncLogTarget(LogTarget logTarget, int queueSize) {
      this.m_logTarget = logTarget;
      this.m_list = new LinkedList();
      this.m_queueSize = queueSize;
      this.open();
   }

   public synchronized void setErrorHandler(ErrorHandler errorHandler) {
      super.setErrorHandler(errorHandler);
      if (this.m_logTarget instanceof ErrorAware) {
         ((ErrorAware)this.m_logTarget).setErrorHandler(errorHandler);
      }

   }

   public void doProcessEvent(LogEvent event) {
      LinkedList var2 = this.m_list;
      synchronized(var2) {
         int size = this.m_list.size();

         while(this.m_queueSize <= size) {
            try {
               this.m_list.wait();
            } catch (InterruptedException var6) {
            }
         }

         this.m_list.addFirst(event);
         if (size == 0) {
            this.m_list.notify();
         }

      }
   }

   public void run() {
      boolean interupted = false;

      while(true) {
         LogEvent event = null;
         LinkedList var3 = this.m_list;
         synchronized(var3) {
            while(true) {
               if (null != event) {
                  break;
               }

               int size = this.m_list.size();
               if (size > 0) {
                  event = (LogEvent)this.m_list.removeLast();
                  if (size == this.m_queueSize) {
                     this.m_list.notify();
                  }
               } else {
                  if (interupted || Thread.interrupted()) {
                     return;
                  }

                  try {
                     this.m_list.wait();
                  } catch (InterruptedException var7) {
                     interupted = true;
                  }
               }
            }
         }

         try {
            this.m_logTarget.processEvent(event);
         } catch (Throwable var8) {
            this.getErrorHandler().error("Unknown error writing event.", var8, event);
         }
      }
   }
}
