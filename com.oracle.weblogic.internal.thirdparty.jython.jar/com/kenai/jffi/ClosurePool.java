package com.kenai.jffi;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class ClosurePool {
   private final Set magazines = Collections.synchronizedSet(new HashSet());
   private final ConcurrentLinkedQueue freeQueue = new ConcurrentLinkedQueue();
   private final ConcurrentLinkedQueue partialQueue = new ConcurrentLinkedQueue();
   private final CallContext callContext;
   private static final Closure NULL_CLOSURE = new Closure() {
      public void invoke(Closure.Buffer buffer) {
      }
   };

   ClosurePool(CallContext callContext) {
      this.callContext = callContext;
   }

   synchronized void recycle(Magazine magazine) {
      magazine.recycle();
      if (!magazine.isEmpty()) {
         this.useMagazine(magazine);
      } else {
         this.magazines.remove(magazine);
      }

   }

   void recycle(Magazine.Slot slot, MagazineHolder holder) {
      this.partialQueue.add(new Handle(slot, holder));
   }

   private void useMagazine(Magazine m) {
      MagazineHolder h = new MagazineHolder(this, m);
      ArrayList handles = new ArrayList();
      ConcurrentLinkedQueue q = m.isFull() ? this.freeQueue : this.partialQueue;

      Magazine.Slot s;
      while((s = m.get()) != null) {
         handles.add(new Handle(s, h));
      }

      q.addAll(handles);
   }

   public Closure.Handle newClosureHandle(Closure closure) {
      Handle h = (Handle)this.partialQueue.poll();
      if (h == null) {
         h = (Handle)this.freeQueue.poll();
      }

      if (h == null) {
         h = this.allocateNewHandle();
      }

      h.slot.proxy.closure = closure;
      return h;
   }

   private Handle allocateNewHandle() {
      Handle h;
      while((h = (Handle)this.partialQueue.poll()) == null && (h = (Handle)this.freeQueue.poll()) == null) {
         Magazine m = new Magazine(this.callContext);
         this.useMagazine(m);
         this.magazines.add(m);
      }

      return h;
   }

   static final class Proxy {
      static final Method METHOD = getMethod();
      final CallContext callContext;
      volatile Closure closure;

      private static Method getMethod() {
         try {
            return Proxy.class.getDeclaredMethod("invoke", Long.TYPE, Long.TYPE);
         } catch (Throwable var1) {
            throw new RuntimeException(var1);
         }
      }

      Proxy(CallContext callContext) {
         this.closure = ClosurePool.NULL_CLOSURE;
         this.callContext = callContext;
      }

      public void invoke(long retvalAddress, long paramAddress) {
         this.closure.invoke(new DirectClosureBuffer(this.callContext, retvalAddress, paramAddress));
      }
   }

   private static final class MagazineHolder {
      final ClosurePool pool;
      final Magazine magazine;

      public MagazineHolder(ClosurePool pool, Magazine magazine) {
         this.pool = pool;
         this.magazine = magazine;
      }

      protected void finalize() throws Throwable {
         try {
            this.pool.recycle(this.magazine);
         } finally {
            super.finalize();
         }

      }
   }

   private static final class Magazine {
      private static final MemoryIO IO = MemoryIO.getInstance();
      private final Foreign foreign = Foreign.getInstance();
      private final CallContext ctx;
      private final long magazine;
      private final Slot[] slots;
      private int next;
      private int freeCount;

      Magazine(CallContext ctx) {
         this.ctx = ctx;
         this.magazine = this.foreign.newClosureMagazine(ctx.getAddress(), ClosurePool.Proxy.METHOD, false);
         ArrayList slots = new ArrayList();

         while(true) {
            Proxy proxy = new Proxy(ctx);
            long h;
            if ((h = this.foreign.closureMagazineGet(this.magazine, proxy)) == 0L) {
               this.slots = new Slot[slots.size()];
               slots.toArray(this.slots);
               this.next = 0;
               this.freeCount = this.slots.length;
               return;
            }

            Slot s = new Slot(h, proxy);
            slots.add(s);
         }
      }

      Slot get() {
         while(true) {
            if (this.freeCount > 0 && this.next < this.slots.length) {
               Slot s = this.slots[this.next++];
               if (!s.autorelease) {
                  continue;
               }

               --this.freeCount;
               return s;
            }

            return null;
         }
      }

      boolean isFull() {
         return this.slots.length == this.freeCount;
      }

      boolean isEmpty() {
         return this.freeCount < 1;
      }

      void recycle() {
         for(int i = 0; i < this.slots.length; ++i) {
            Slot s = this.slots[i];
            if (s.autorelease) {
               ++this.freeCount;
               s.proxy.closure = ClosurePool.NULL_CLOSURE;
            }
         }

         this.next = 0;
      }

      protected void finalize() throws Throwable {
         try {
            boolean release = true;
            int i = 0;

            while(true) {
               if (i < this.slots.length) {
                  if (this.slots[i].autorelease) {
                     ++i;
                     continue;
                  }

                  release = false;
               }

               if (this.magazine != 0L && release) {
                  this.foreign.freeClosureMagazine(this.magazine);
               }
               break;
            }
         } finally {
            super.finalize();
         }

      }

      static final class Slot {
         final long handle;
         final long codeAddress;
         final Proxy proxy;
         volatile boolean autorelease;

         public Slot(long handle, Proxy proxy) {
            this.handle = handle;
            this.proxy = proxy;
            this.autorelease = true;
            this.codeAddress = ClosurePool.Magazine.IO.getAddress(handle);
         }
      }
   }

   private static final class Handle implements Closure.Handle {
      final MagazineHolder holder;
      final Magazine.Slot slot;
      private volatile boolean disposed;

      Handle(Magazine.Slot slot, MagazineHolder holder) {
         this.slot = slot;
         this.holder = holder;
      }

      public long getAddress() {
         if (this.disposed) {
            throw new RuntimeException("trying to access disposed closure handle");
         } else {
            return this.slot.codeAddress;
         }
      }

      public void setAutoRelease(boolean autorelease) {
         if (!this.disposed) {
            this.slot.autorelease = autorelease;
         }

      }

      /** @deprecated */
      @Deprecated
      public void free() {
         this.dispose();
      }

      public synchronized void dispose() {
         if (!this.disposed) {
            this.disposed = true;
            this.slot.autorelease = true;
            this.slot.proxy.closure = ClosurePool.NULL_CLOSURE;
            this.holder.pool.recycle(this.slot, this.holder);
         }

      }
   }
}
