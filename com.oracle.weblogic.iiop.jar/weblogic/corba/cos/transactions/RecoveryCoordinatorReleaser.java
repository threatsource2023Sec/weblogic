package weblogic.corba.cos.transactions;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.transaction.RollbackException;
import javax.transaction.Synchronization;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import weblogic.iiop.IIOPLogger;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManagerFactory;

public final class RecoveryCoordinatorReleaser {
   private static RecoveryCoordinatorReleaser singleton;
   private RecoveryCoordinatorReleaseTrigger trigger;
   private Hashtable listeners = new Hashtable();

   public static RecoveryCoordinatorReleaser getReleaser() {
      return singleton == null ? createSingleton() : singleton;
   }

   private RecoveryCoordinatorReleaser() {
   }

   private static synchronized RecoveryCoordinatorReleaser createSingleton() {
      if (singleton == null) {
         singleton = new RecoveryCoordinatorReleaser();
      }

      return singleton;
   }

   public void register(Transaction tx, Releasable releasable) {
      if (tx != null) {
         if (this.trigger == null) {
            this.trigger = new RecoveryCoordinatorReleaseTrigger();
         }

         TxListener listener = (TxListener)this.listeners.get(tx);
         if (listener == null) {
            listener = new TxListener(tx);

            try {
               tx.registerSynchronization(listener);
               this.listeners.put(tx, listener);
            } catch (SystemException | RollbackException var5) {
               IIOPLogger.logOTSError("Could not register synchronization", var5);
            }
         }

         listener.add(releasable);
      }
   }

   private static class RecoveryCoordinatorReleaseTrigger implements TimerListener {
      private static final long RC_DEFUALT_RELEASE_SEC = 90000L;
      private static final long DEFAULT_TRIGGER_INTERVAL = 30000L;
      private long releaseSec = 90000L;
      private long triggerInterval = 30000L;
      private List[] array;
      private int currentIndex = 0;

      public RecoveryCoordinatorReleaseTrigger() {
         int roundup = this.releaseSec % this.triggerInterval == 0L ? 0 : 1;
         int bucketSize = (int)(this.releaseSec / this.triggerInterval) + roundup + 1;
         this.array = new LinkedList[bucketSize];
         TimerManagerFactory.getTimerManagerFactory().getDefaultTimerManager().schedule(this, this.triggerInterval, this.triggerInterval);
      }

      public synchronized void add(List coordinators) {
         List list = this.array[this.currentIndex];
         if (list == null) {
            list = this.array[this.currentIndex] = new LinkedList();
         }

         list.addAll(coordinators);
      }

      public void timerExpired(Timer timer) {
         this.currentIndex = (this.currentIndex + 1) % this.array.length;
         List list = this.array[this.currentIndex];
         if (list != null) {
            Iterator var3 = list.iterator();

            while(var3.hasNext()) {
               Releasable recovery = (Releasable)var3.next();
               recovery.release();
            }

            this.array[this.currentIndex] = null;
         }

      }
   }

   private class TxListener implements Synchronization {
      private List releasables = new LinkedList();
      private Transaction tx;

      TxListener(Transaction tx) {
         this.tx = tx;
      }

      public void add(Releasable releasable) {
         this.releasables.add(releasable);
      }

      public void beforeCompletion() {
      }

      public void afterCompletion(int status) {
         RecoveryCoordinatorReleaser.this.trigger.add(this.releasables);
         RecoveryCoordinatorReleaser.this.listeners.remove(this.tx);
      }
   }
}
