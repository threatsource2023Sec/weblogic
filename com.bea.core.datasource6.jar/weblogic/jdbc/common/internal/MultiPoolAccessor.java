package weblogic.jdbc.common.internal;

public class MultiPoolAccessor {
   private MultiPool delegate;

   public MultiPoolAccessor(MultiPool pool) {
      this.delegate = pool;
   }

   public int getMaxCapacity() {
      return this.delegate.getMaxCapacity();
   }

   public int getMinCapacity() {
      return this.delegate.getMinCapacity();
   }
}
