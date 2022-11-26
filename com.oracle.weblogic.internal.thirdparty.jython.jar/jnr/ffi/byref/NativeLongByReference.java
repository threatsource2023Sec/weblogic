package jnr.ffi.byref;

import jnr.ffi.NativeLong;
import jnr.ffi.Pointer;
import jnr.ffi.Runtime;

public final class NativeLongByReference extends AbstractNumberReference {
   public NativeLongByReference() {
      super(NativeLong.valueOf(0));
   }

   public NativeLongByReference(NativeLong value) {
      super(checkNull(value));
   }

   public NativeLongByReference(long value) {
      super(NativeLong.valueOf(value));
   }

   public void toNative(Runtime runtime, Pointer memory, long offset) {
      memory.putNativeLong(offset, ((NativeLong)this.value).longValue());
   }

   public void fromNative(Runtime runtime, Pointer memory, long offset) {
      this.value = NativeLong.valueOf(memory.getNativeLong(offset));
   }

   public final int nativeSize(Runtime runtime) {
      return runtime.longSize();
   }
}
