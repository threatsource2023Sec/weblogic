package weblogic.utils.concurrent.atomic;

public final class JDK15AtomicInteger implements AtomicInteger {
   private final java.util.concurrent.atomic.AtomicInteger value = new java.util.concurrent.atomic.AtomicInteger();

   JDK15AtomicInteger() {
   }

   public int addAndGet(int delta) {
      return this.value.addAndGet(delta);
   }

   public int decrementAndGet() {
      return this.value.decrementAndGet();
   }

   public int get() {
      return this.value.get();
   }

   public int getAndAdd(int delta) {
      return this.value.getAndAdd(delta);
   }

   public int getAndDecrement() {
      return this.value.getAndDecrement();
   }

   public int getAndIncrement() {
      return this.value.getAndIncrement();
   }

   public int getAndSet(int newValue) {
      return this.value.getAndSet(newValue);
   }

   public int incrementAndGet() {
      return this.value.incrementAndGet();
   }

   public void set(int newValue) {
      this.value.set(newValue);
   }

   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else {
         return !(obj instanceof JDK15AtomicInteger) ? false : this.value.equals(((JDK15AtomicInteger)obj).value);
      }
   }

   public int hashCode() {
      return this.value.hashCode();
   }

   public String toString() {
      return this.value.toString();
   }
}
