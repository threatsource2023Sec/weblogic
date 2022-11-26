package jnr.ffi.byref;

import jnr.ffi.Pointer;
import jnr.ffi.Runtime;

public final class ByteByReference extends AbstractNumberReference {
   public ByteByReference() {
      super((byte)0);
   }

   public ByteByReference(Byte value) {
      super(checkNull(value));
   }

   public ByteByReference(byte value) {
      super(value);
   }

   public void toNative(Runtime runtime, Pointer buffer, long offset) {
      buffer.putByte(offset, (Byte)this.value);
   }

   public void fromNative(Runtime runtime, Pointer buffer, long offset) {
      this.value = buffer.getByte(offset);
   }

   public final int nativeSize(Runtime runtime) {
      return 1;
   }
}
