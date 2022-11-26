package weblogic.messaging.kernel.internal;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import weblogic.diagnostics.debug.DebugLogger;

final class OwnableLock {
   private int count;
   private Object owner;
   private static final DebugLogger logger = DebugLogger.getDebugLogger("DebugMessagingOwnableLock");

   synchronized void lock(Object locker) {
      while(this.count != 0 && this.owner != locker) {
         if (logger.isDebugEnabled()) {
            this.debugLog("OwnableLock.lock() WAITING count " + this.count + " owner " + this.owner + " locker " + locker);
         }

         try {
            this.wait();
         } catch (InterruptedException var3) {
         }
      }

      ++this.count;
      this.owner = locker;
      if (logger.isDebugEnabled()) {
         this.debugLog("OwnableLock.lock() OWNIT count " + this.count + " owner " + this.owner + " locker " + locker);
      }

   }

   synchronized void unlock(Object unlocker) {
      if (this.owner != unlocker && this.owner != null) {
         throw new AssertionError("Lock must be unlocked by owner only");
      } else {
         assert this.count > 0;

         --this.count;
         if (this.count < 0) {
            this.count = 0;
         }

         if (this.count == 0) {
            if (logger.isDebugEnabled()) {
               this.debugLog("OwnableLock.unlock() UNLOCK-NOTIFY count " + this.count + " owner " + this.owner + " unlocker " + unlocker);
            }

            this.owner = null;
            this.notify();
         } else if (logger.isDebugEnabled()) {
            this.debugLog("OwnableLock.unlock() UNLOCK count " + this.count + " owner " + this.owner + " unlocker " + unlocker);
         }

      }
   }

   synchronized void unlockAll(Object unlocker) {
      if (this.owner != unlocker && this.owner != null) {
         throw new AssertionError("Lock must be unlocked by owner only");
      } else {
         assert this.count > 0;

         if (logger.isDebugEnabled()) {
            this.debugLog("OwnableLock.unlock() NOTIFY ALL count " + this.count + " owner " + this.owner + " unlocker " + unlocker);
         }

         this.count = 0;
         this.owner = null;
         this.notify();
      }
   }

   synchronized boolean isLocked(Object checker) {
      return this.count > 0 && this.owner == checker;
   }

   private void debugLog(String logMsg) {
      ByteArrayOutputStream ostr = new ByteArrayOutputStream();
      Exception e = new Exception();
      e.printStackTrace(new PrintStream(ostr));
      logger.debug("<" + Thread.currentThread().getName() + "> " + logMsg + "\n " + ostr);
   }
}
