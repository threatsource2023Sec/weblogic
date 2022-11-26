package weblogic.ldap;

import com.octetstring.vde.WorkQueueItem;
import com.octetstring.vde.WorkThread;

final class LDAPExecuteRequest implements Runnable {
   private static int requestCount = 0;
   private final WorkQueueItem workItem;

   static synchronized void waitForRequestsToComplete() {
      while(requestCount > 0) {
         try {
            LDAPExecuteRequest.class.wait();
         } catch (InterruptedException var1) {
         }
      }

   }

   private static synchronized void requestStarted() {
      ++requestCount;
   }

   private static synchronized void requestComplete() {
      --requestCount;
      LDAPExecuteRequest.class.notify();
   }

   public LDAPExecuteRequest(WorkQueueItem workQueueItem) {
      this.workItem = workQueueItem;
   }

   public void run() {
      EmbeddedLDAP ldap = EmbeddedLDAP.getEmbeddedLDAP();
      if (ldap.isRunning()) {
         requestStarted();

         try {
            if (ldap.isRunning()) {
               WorkThread.executeWorkQueueItem(this.workItem);
            }
         } finally {
            requestComplete();
         }
      }

   }
}
