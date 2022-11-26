package org.python.netty.util.internal;

import org.python.netty.util.Recycler;
import org.python.netty.util.ReferenceCountUtil;
import org.python.netty.util.concurrent.Promise;

public final class PendingWrite {
   private static final Recycler RECYCLER = new Recycler() {
      protected PendingWrite newObject(Recycler.Handle handle) {
         return new PendingWrite(handle);
      }
   };
   private final Recycler.Handle handle;
   private Object msg;
   private Promise promise;

   public static PendingWrite newInstance(Object msg, Promise promise) {
      PendingWrite pending = (PendingWrite)RECYCLER.get();
      pending.msg = msg;
      pending.promise = promise;
      return pending;
   }

   private PendingWrite(Recycler.Handle handle) {
      this.handle = handle;
   }

   public boolean recycle() {
      this.msg = null;
      this.promise = null;
      this.handle.recycle(this);
      return true;
   }

   public boolean failAndRecycle(Throwable cause) {
      ReferenceCountUtil.release(this.msg);
      if (this.promise != null) {
         this.promise.setFailure(cause);
      }

      return this.recycle();
   }

   public boolean successAndRecycle() {
      if (this.promise != null) {
         this.promise.setSuccess((Object)null);
      }

      return this.recycle();
   }

   public Object msg() {
      return this.msg;
   }

   public Promise promise() {
      return this.promise;
   }

   public Promise recycleAndGet() {
      Promise promise = this.promise;
      this.recycle();
      return promise;
   }

   // $FF: synthetic method
   PendingWrite(Recycler.Handle x0, Object x1) {
      this(x0);
   }
}
