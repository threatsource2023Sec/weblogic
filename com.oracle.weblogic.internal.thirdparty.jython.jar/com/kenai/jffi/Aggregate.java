package com.kenai.jffi;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Aggregate extends Type {
   private final Type.TypeInfo typeInfo;
   private final long handle;
   private volatile int disposed;
   private static final AtomicIntegerFieldUpdater UPDATER = AtomicIntegerFieldUpdater.newUpdater(Aggregate.class, "disposed");
   private final Foreign foreign;

   Aggregate(Foreign foreign, long handle) {
      if (handle == 0L) {
         throw new NullPointerException("Invalid ffi_type handle");
      } else {
         this.foreign = foreign;
         this.handle = handle;
         this.typeInfo = new Type.TypeInfo(handle, foreign.getTypeType(handle), foreign.getTypeSize(handle), foreign.getTypeAlign(handle));
      }
   }

   final Type.TypeInfo getTypeInfo() {
      return this.typeInfo;
   }

   public final synchronized void dispose() {
   }

   protected void finalize() throws Throwable {
      try {
         int disposed = UPDATER.getAndSet(this, 1);
         if (disposed == 0) {
            this.foreign.freeAggregate(this.typeInfo.handle);
         }
      } catch (Throwable var5) {
         Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Exception when freeing FFI aggregate: %s", var5.getLocalizedMessage());
      } finally {
         super.finalize();
      }

   }
}
