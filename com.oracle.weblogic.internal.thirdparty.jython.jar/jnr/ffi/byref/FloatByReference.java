package jnr.ffi.byref;

import jnr.ffi.Pointer;
import jnr.ffi.Runtime;

public final class FloatByReference extends AbstractNumberReference {
   private static final Float DEFAULT = 0.0F;

   public FloatByReference() {
      super(DEFAULT);
   }

   public FloatByReference(Float value) {
      super(checkNull(value));
   }

   public FloatByReference(float value) {
      super(value);
   }

   public void toNative(Runtime runtime, Pointer buffer, long offset) {
      buffer.putFloat(offset, (Float)this.value);
   }

   public void fromNative(Runtime runtime, Pointer buffer, long offset) {
      this.value = buffer.getFloat(offset);
   }

   public final int nativeSize(Runtime runtime) {
      return 4;
   }
}
