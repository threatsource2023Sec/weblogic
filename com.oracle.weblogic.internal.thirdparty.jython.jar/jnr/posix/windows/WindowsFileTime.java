package jnr.posix.windows;

import jnr.ffi.Runtime;
import jnr.ffi.Struct;

public class WindowsFileTime extends Struct {
   final Struct.Unsigned32 lowDateTime = new Struct.Unsigned32();
   final Struct.Unsigned32 highDateTime = new Struct.Unsigned32();

   public WindowsFileTime(Runtime runtime) {
      super(runtime);
   }

   public int getLowDateTime() {
      return this.lowDateTime.intValue();
   }

   public int getHighDateTime() {
      return this.highDateTime.intValue();
   }

   public long getLongValue() {
      return (long)(this.getHighDateTime() << 32 + this.getLowDateTime());
   }
}
