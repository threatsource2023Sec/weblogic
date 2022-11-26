package jnr.ffi.byref;

public abstract class AbstractReference implements ByReference {
   Object value;

   protected AbstractReference(Object value) {
      this.value = value;
   }

   protected static Object checkNull(Object value) {
      if (value == null) {
         throw new NullPointerException("reference value cannot be null");
      } else {
         return value;
      }
   }

   public Object getValue() {
      return this.value;
   }
}
