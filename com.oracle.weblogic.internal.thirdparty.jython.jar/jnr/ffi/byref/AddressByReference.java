package jnr.ffi.byref;

import jnr.ffi.Address;
import jnr.ffi.Pointer;
import jnr.ffi.Runtime;

public final class AddressByReference extends AbstractReference {
   public AddressByReference() {
      super(Address.valueOf(0));
   }

   public AddressByReference(Address value) {
      super(checkNull(value));
   }

   public void toNative(Runtime runtime, Pointer memory, long offset) {
      memory.putAddress(offset, ((Address)this.value).nativeAddress());
   }

   public void fromNative(Runtime runtime, Pointer memory, long offset) {
      this.value = Address.valueOf(memory.getAddress(offset));
   }

   public int nativeSize(Runtime runtime) {
      return runtime.addressSize();
   }
}
