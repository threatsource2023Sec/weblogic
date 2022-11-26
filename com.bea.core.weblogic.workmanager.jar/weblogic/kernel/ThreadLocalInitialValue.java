package weblogic.kernel;

public class ThreadLocalInitialValue {
   protected final boolean inherit;

   public ThreadLocalInitialValue() {
      this.inherit = false;
   }

   public ThreadLocalInitialValue(boolean inherit) {
      this.inherit = inherit;
   }

   protected Object childValue(Object parentValue) {
      return this.inherit ? parentValue : this.initialValue();
   }

   protected Object initialValue() {
      return null;
   }

   protected Object resetValue(Object currentValue) {
      return this.initialValue();
   }
}
