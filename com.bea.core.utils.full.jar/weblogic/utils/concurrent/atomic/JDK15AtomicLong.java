package weblogic.utils.concurrent.atomic;

public final class JDK15AtomicLong implements AtomicLong {
   private final java.util.concurrent.atomic.AtomicLong value = new java.util.concurrent.atomic.AtomicLong();

   JDK15AtomicLong() {
   }

   public long addAndGet(long delta) {
      return this.value.addAndGet(delta);
   }

   public long decrementAndGet() {
      return this.value.decrementAndGet();
   }

   public long get() {
      return this.value.get();
   }

   public long getAndAdd(long delta) {
      return this.value.getAndAdd(delta);
   }

   public long getAndDecrement() {
      return this.value.getAndDecrement();
   }

   public long getAndIncrement() {
      return this.value.getAndIncrement();
   }

   public long getAndSet(long newValue) {
      return this.value.getAndSet(newValue);
   }

   public long incrementAndGet() {
      return this.value.incrementAndGet();
   }

   public void set(long newValue) {
      this.value.set(newValue);
   }

   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else {
         return !(obj instanceof JDK15AtomicLong) ? false : this.value.equals(((JDK15AtomicLong)obj).value);
      }
   }

   public int hashCode() {
      return this.value.hashCode();
   }

   public String toString() {
      return this.value.toString();
   }
}
