package jnr.ffi.byref;

import jnr.ffi.Pointer;
import jnr.ffi.Runtime;

public final class DoubleByReference extends AbstractNumberReference {
   private static final Double DEFAULT = 0.0;

   public DoubleByReference() {
      super(DEFAULT);
   }

   public DoubleByReference(Double value) {
      super(checkNull(value));
   }

   public DoubleByReference(double value) {
      super(value);
   }

   public void toNative(Runtime runtime, Pointer buffer, long offset) {
      buffer.putDouble(offset, (Double)this.value);
   }

   public void fromNative(Runtime runtime, Pointer buffer, long offset) {
      this.value = buffer.getDouble(offset);
   }

   public final int nativeSize(Runtime runtime) {
      return 8;
   }
}
