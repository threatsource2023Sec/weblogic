package weblogic.apache.org.apache.velocity.util;

public final class SimplePool {
   private Object[] pool;
   private int max;
   private int current = -1;

   public SimplePool(int max) {
      this.max = max;
      this.pool = new Object[max];
   }

   public void put(Object o) {
      int idx = -1;
      synchronized(this) {
         if (this.current < this.max - 1) {
            idx = ++this.current;
         }

         if (idx >= 0) {
            this.pool[idx] = o;
         }

      }
   }

   public Object get() {
      int idx = true;
      synchronized(this) {
         if (this.current >= 0) {
            int idx = this.current--;
            Object var3 = this.pool[idx];
            return var3;
         } else {
            return null;
         }
      }
   }

   public int getMax() {
      return this.max;
   }
}
