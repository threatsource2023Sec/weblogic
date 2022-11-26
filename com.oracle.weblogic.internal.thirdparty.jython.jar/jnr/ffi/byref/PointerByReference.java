package jnr.ffi.byref;

import jnr.ffi.Pointer;
import jnr.ffi.Runtime;

public final class PointerByReference extends AbstractReference {
   public PointerByReference() {
      super((Object)null);
   }

   public PointerByReference(Pointer value) {
      super(value);
   }

   public final void toNative(Runtime runtime, Pointer memory, long offset) {
      memory.putPointer(offset, (Pointer)this.value);
   }

   public final void fromNative(Runtime runtime, Pointer memory, long offset) {
      this.value = memory.getPointer(offset);
   }

   public final int nativeSize(Runtime runtime) {
      return runtime.addressSize();
   }
}
