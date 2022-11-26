package jnr.ffi.byref;

import jnr.ffi.Pointer;
import jnr.ffi.Runtime;

public final class ShortByReference extends AbstractNumberReference {
   public ShortByReference() {
      super(Short.valueOf((short)0));
   }

   public ShortByReference(Short value) {
      super(checkNull(value));
   }

   public ShortByReference(short value) {
      super(value);
   }

   public void toNative(Runtime runtime, Pointer buffer, long offset) {
      buffer.putShort(offset, (Short)this.value);
   }

   public void fromNative(Runtime runtime, Pointer buffer, long offset) {
      this.value = buffer.getShort(offset);
   }

   public final int nativeSize(Runtime runtime) {
      return 2;
   }
}
