package javolution.realtime;

import javolution.JavolutionError;

public abstract class ObjectPool {
   public static final ObjectPool NULL = new ObjectPool() {
      public int size() {
         return 0;
      }

      public Object next() {
         return null;
      }

      public void recycle(Object var1) {
      }

      protected void recycleAll() {
      }

      protected void clearAll() {
      }
   };
   ObjectPool outer;
   Thread user;
   boolean inUse;

   protected ObjectPool() {
   }

   public final Thread getUser() {
      return this.user;
   }

   public final boolean isLocal() {
      if (this.inUse) {
         if (this.user == null) {
            return false;
         } else if (this.user == Thread.currentThread()) {
            return true;
         } else {
            throw new JavolutionError("Concurrent access to local pool detected");
         }
      } else {
         throw new JavolutionError("Access to inner pool or unused pool detected");
      }
   }

   public final boolean inUse() {
      return this.inUse;
   }

   public final ObjectPool getOuter() {
      return this.outer;
   }

   public abstract int size();

   public abstract Object next();

   public abstract void recycle(Object var1);

   protected abstract void recycleAll();

   protected abstract void clearAll();
}
