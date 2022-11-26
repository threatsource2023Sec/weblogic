package jnr.ffi.byref;

public abstract class AbstractNumberReference extends Number implements ByReference {
   Number value;

   protected AbstractNumberReference(Number value) {
      this.value = value;
   }

   protected static Number checkNull(Number value) {
      if (value == null) {
         throw new NullPointerException("reference value cannot be null");
      } else {
         return value;
      }
   }

   public Number getValue() {
      return this.value;
   }

   public final byte byteValue() {
      return this.value.byteValue();
   }

   public final short shortValue() {
      return (short)this.value.byteValue();
   }

   public final int intValue() {
      return this.value.intValue();
   }

   public final long longValue() {
      return this.value.longValue();
   }

   public final float floatValue() {
      return this.value.floatValue();
   }

   public final double doubleValue() {
      return this.value.doubleValue();
   }
}
