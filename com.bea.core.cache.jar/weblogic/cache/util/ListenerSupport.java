package weblogic.cache.util;

import commonj.work.Work;
import commonj.work.WorkException;
import commonj.work.WorkManager;
import java.util.Iterator;
import java.util.Vector;
import weblogic.cache.CacheRuntimeException;

public abstract class ListenerSupport extends Vector {
   private WorkManager workManager;
   private boolean started = true;

   public ListenerSupport(WorkManager workManager) {
      this.setWorkManager(workManager);
   }

   public void setWorkManager(WorkManager workManager) {
      this.workManager = workManager;
   }

   public void fireEvent(Object type, Object data, Object auxData) throws ListenerSupportException {
      if (this.started) {
         Iterator var4 = this.iterator();

         while(var4.hasNext()) {
            Object listener = var4.next();
            if (this.workManager == null) {
               this.onEvent(listener, type, data, auxData);
            } else {
               try {
                  this.workManager.schedule(new ListenerDispatcher(this, listener, type, data, auxData));
               } catch (IllegalArgumentException var7) {
                  throw new ListenerSupportException("Unable to schedule listener dispacther", var7);
               } catch (WorkException var8) {
                  throw new ListenerSupportException("Unable to schedule listener dispacther", var8);
               }
            }
         }

      }
   }

   protected abstract void onEvent(Object var1, Object var2, Object var3, Object var4);

   public void start() {
      this.started = true;
   }

   public void stop(boolean graceful) {
      this.started = false;
   }

   public static class ListenerSupportException extends CacheRuntimeException {
      public ListenerSupportException(String messsage, Throwable t) {
         super(messsage, t);
      }
   }

   public static class ListenerDispatcher implements Work {
      private final ListenerSupport listenerSupport;
      private final Object listener;
      private final Object eventType;
      private final Object eventData;
      private final Object auxData;
      private boolean release = false;

      public ListenerDispatcher(ListenerSupport listenerSupport, Object listener, Object eventType, Object eventData, Object auxData) {
         this.listenerSupport = listenerSupport;
         this.listener = listener;
         this.eventType = eventType;
         this.eventData = eventData;
         this.auxData = auxData;
      }

      public void release() {
         this.release = true;
      }

      public boolean isDaemon() {
         return false;
      }

      public void run() {
         if (!this.release) {
            this.listenerSupport.onEvent(this.listener, this.eventType, this.eventData, this.auxData);
         }

      }
   }
}
