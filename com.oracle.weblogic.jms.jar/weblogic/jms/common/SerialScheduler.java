package weblogic.jms.common;

import java.util.LinkedList;
import weblogic.work.WorkManagerFactory;

public class SerialScheduler implements Runnable {
   private boolean running = false;
   private boolean drain = false;
   private LinkedList schedList = new LinkedList();
   private Throwable firstThrowable;

   public void run() {
      int i = 0;

      while(true) {
         Runnable toRun = null;
         synchronized(this.schedList) {
            if (!this.drain && i == 100) {
               if (WorkManagerFactory.getInstance().getDefault().scheduleIfBusy(this)) {
                  return;
               }

               i = 0;
            }

            if (this.schedList.size() != 0) {
               toRun = (Runnable)this.schedList.removeFirst();
            }

            if (toRun == null) {
               this.running = false;
               this.drain = false;
               this.schedList.notifyAll();
               return;
            }
         }

         try {
            if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
               JMSDebug.JMSBackEnd.debug("Calling out to " + toRun);
            }

            toRun.run();
            if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
               JMSDebug.JMSBackEnd.debug("Back from " + toRun);
            }
         } catch (Throwable var5) {
            if (this.firstThrowable == null) {
               this.firstThrowable = var5;
            }

            var5.printStackTrace();
         }

         ++i;
      }
   }

   public void schedule(Runnable toRun) {
      synchronized(this.schedList) {
         this.schedList.add(toRun);
         if (!this.running) {
            this.running = true;
            WorkManagerFactory.getInstance().getDefault().schedule(this);
         }

      }
   }

   public void drain() {
      synchronized(this.schedList) {
         if (this.schedList.size() != 0) {
            this.drain = true;
            if (!this.running) {
               this.running = true;
               WorkManagerFactory.getInstance().getDefault().schedule(this);
            }

         }
      }
   }

   public Throwable waitForComplete() {
      synchronized(this.schedList) {
         while(this.schedList.size() > 0 || this.running) {
            try {
               this.schedList.wait();
            } catch (InterruptedException var4) {
               throw new RuntimeException(var4);
            }
         }

         Throwable returnValue = this.firstThrowable;
         this.firstThrowable = null;
         return returnValue;
      }
   }
}
