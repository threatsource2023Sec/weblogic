package jnr.ffi.byref;

import jnr.ffi.Pointer;
import jnr.ffi.Runtime;

public final class LongLongByReference extends AbstractNumberReference {
   public LongLongByReference() {
      super(0L);
   }

   public LongLongByReference(Long value) {
      super(checkNull(value));
   }

   public LongLongByReference(long value) {
      super(value);
   }

   public void toNative(Runtime runtime, Pointer memory, long offset) {
      memory.putLongLong(offset, (Long)this.value);
   }

   public void fromNative(Runtime runtime, Pointer memory, long offset) {
      this.value = memory.getLongLong(offset);
   }

   public final int nativeSize(Runtime runtime) {
      return 8;
   }
}
