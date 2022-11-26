package jnr.ffi.byref;

import jnr.ffi.Pointer;
import jnr.ffi.Runtime;

public final class IntByReference extends AbstractNumberReference {
   public IntByReference() {
      super(0);
   }

   public IntByReference(Integer value) {
      super(checkNull(value));
   }

   public IntByReference(int value) {
      super(value);
   }

   public void toNative(Runtime runtime, Pointer buffer, long offset) {
      buffer.putInt(offset, (Integer)this.value);
   }

   public void fromNative(Runtime runtime, Pointer buffer, long offset) {
      this.value = buffer.getInt(offset);
   }

   public int nativeSize(Runtime runtime) {
      return 4;
   }
}
