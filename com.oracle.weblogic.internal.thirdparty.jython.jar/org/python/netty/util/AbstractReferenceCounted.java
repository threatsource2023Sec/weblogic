package org.python.netty.util;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import org.python.netty.util.internal.ObjectUtil;

public abstract class AbstractReferenceCounted implements ReferenceCounted {
   private static final AtomicIntegerFieldUpdater refCntUpdater = AtomicIntegerFieldUpdater.newUpdater(AbstractReferenceCounted.class, "refCnt");
   private volatile int refCnt = 1;

   public final int refCnt() {
      return this.refCnt;
   }

   protected final void setRefCnt(int refCnt) {
      this.refCnt = refCnt;
   }

   public ReferenceCounted retain() {
      return this.retain0(1);
   }

   public ReferenceCounted retain(int increment) {
      return this.retain0(ObjectUtil.checkPositive(increment, "increment"));
   }

   private ReferenceCounted retain0(int increment) {
      int refCnt;
      int nextCnt;
      do {
         refCnt = this.refCnt;
         nextCnt = refCnt + increment;
         if (nextCnt <= increment) {
            throw new IllegalReferenceCountException(refCnt, increment);
         }
      } while(!refCntUpdater.compareAndSet(this, refCnt, nextCnt));

      return this;
   }

   public ReferenceCounted touch() {
      return this.touch((Object)null);
   }

   public boolean release() {
      return this.release0(1);
   }

   public boolean release(int decrement) {
      return this.release0(ObjectUtil.checkPositive(decrement, "decrement"));
   }

   private boolean release0(int decrement) {
      int refCnt;
      do {
         refCnt = this.refCnt;
         if (refCnt < decrement) {
            throw new IllegalReferenceCountException(refCnt, -decrement);
         }
      } while(!refCntUpdater.compareAndSet(this, refCnt, refCnt - decrement));

      if (refCnt == decrement) {
         this.deallocate();
         return true;
      } else {
         return false;
      }
   }

   protected abstract void deallocate();
}
