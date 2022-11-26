package com.kenai.jffi;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ClosureMagazine {
   private final Foreign foreign;
   private final CallContext callContext;
   private final long magazineAddress;
   private volatile int disposed;
   private static final AtomicIntegerFieldUpdater UPDATER = AtomicIntegerFieldUpdater.newUpdater(ClosureMagazine.class, "disposed");

   ClosureMagazine(Foreign foreign, CallContext callContext, long magazineAddress) {
      this.foreign = foreign;
      this.callContext = callContext;
      this.magazineAddress = magazineAddress;
   }

   public Closure.Handle allocate(Object proxy) {
      long closureAddress = this.foreign.closureMagazineGet(this.magazineAddress, proxy);
      return closureAddress != 0L ? new Handle(this, closureAddress, MemoryIO.getInstance().getAddress(closureAddress)) : null;
   }

   public void dispose() {
      int disposed = UPDATER.getAndSet(this, 1);
      if (this.magazineAddress != 0L && disposed == 0) {
         this.foreign.freeClosureMagazine(this.magazineAddress);
      }

   }

   protected void finalize() throws Throwable {
      try {
         int disposed = UPDATER.getAndSet(this, 1);
         if (this.magazineAddress != 0L && disposed == 0) {
            this.foreign.freeClosureMagazine(this.magazineAddress);
         }
      } catch (Throwable var5) {
         Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "exception when freeing " + this.getClass() + ": %s", var5.getLocalizedMessage());
      } finally {
         super.finalize();
      }

   }

   private static final class Handle implements Closure.Handle {
      private final ClosureMagazine magazine;
      private final long closureAddress;
      private final long codeAddress;

      private Handle(ClosureMagazine magazine, long closureAddress, long codeAddress) {
         this.magazine = magazine;
         this.closureAddress = closureAddress;
         this.codeAddress = codeAddress;
      }

      public long getAddress() {
         return this.codeAddress;
      }

      public void setAutoRelease(boolean autorelease) {
      }

      public void dispose() {
      }

      public void free() {
      }

      // $FF: synthetic method
      Handle(ClosureMagazine x0, long x1, long x2, Object x3) {
         this(x0, x1, x2);
      }
   }
}
