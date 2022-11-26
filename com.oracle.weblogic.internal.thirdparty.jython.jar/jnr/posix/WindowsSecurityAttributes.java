package jnr.posix;

import jnr.ffi.Runtime;
import jnr.ffi.Struct;

public class WindowsSecurityAttributes extends Struct {
   public final Struct.Unsigned32 length = new Struct.Unsigned32();
   public final Struct.Pointer securityDescriptor = new Struct.Pointer();
   public final Struct.WBOOL inheritHandle = new Struct.WBOOL();

   public WindowsSecurityAttributes(Runtime runtime) {
      super(runtime);
      this.length.set((long)Struct.size(this));
      this.inheritHandle.set(true);
   }

   public long getLength() {
      return this.length.get();
   }

   public boolean getInheritHandle() {
      return this.inheritHandle.get();
   }
}
