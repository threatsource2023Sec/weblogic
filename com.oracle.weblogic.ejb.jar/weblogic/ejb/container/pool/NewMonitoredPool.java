package weblogic.ejb.container.pool;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public final class NewMonitoredPool {
   private final NewShrinkablePool delegate;
   private final AtomicLong accesses = new AtomicLong();
   private final AtomicLong misses = new AtomicLong();

   public NewMonitoredPool(NewShrinkablePool pool) {
      this.delegate = pool;
   }

   public int getFreeCount() {
      return this.size();
   }

   public long getAccessCount() {
      return this.accesses.get();
   }

   public long getMissCount() {
      return this.misses.get();
   }

   public void setCapacity(int maxiSize) {
      this.delegate.setCapacity(maxiSize);
   }

   public int getCapacity() {
      return this.delegate.getCapacity();
   }

   public Object remove() {
      this.accesses.incrementAndGet();
      Object obj = this.delegate.remove();
      if (obj == null) {
         this.misses.incrementAndGet();
      }

      return obj;
   }

   public boolean isEmpty() {
      return this.delegate.isEmpty();
   }

   public boolean add(Object o) {
      return this.delegate.add(o);
   }

   public int size() {
      return this.delegate.size();
   }

   public List trim(boolean idleTimeout) {
      return this.delegate.trim(idleTimeout);
   }

   public List trim(int removeNumber) {
      return this.delegate.trim(removeNumber);
   }
}
