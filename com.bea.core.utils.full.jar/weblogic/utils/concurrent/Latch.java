package weblogic.utils.concurrent;

import java.util.concurrent.atomic.AtomicBoolean;

/** @deprecated */
@Deprecated
public final class Latch {
   private AtomicBoolean locked = new AtomicBoolean(false);

   public boolean tryLock() {
      return this.locked.compareAndSet(false, true);
   }

   public boolean isLocked() {
      return this.locked.get();
   }
}
