package jnr.posix.windows;

import jnr.ffi.Runtime;
import jnr.ffi.Struct;

public class SystemTime extends Struct {
   Struct.Unsigned16 wYear = new Struct.Unsigned16();
   Struct.Unsigned16 wMonth = new Struct.Unsigned16();
   Struct.Unsigned16 wDayOfWeek = new Struct.Unsigned16();
   Struct.Unsigned16 wDay = new Struct.Unsigned16();
   Struct.Unsigned16 wHour = new Struct.Unsigned16();
   Struct.Unsigned16 wMinute = new Struct.Unsigned16();
   Struct.Unsigned16 wSecond = new Struct.Unsigned16();
   Struct.Unsigned16 wMilliseconds = new Struct.Unsigned16();

   public SystemTime(Runtime runtime) {
      super(runtime);
   }

   public java.lang.String toString() {
      return "" + this.wYear + "/" + this.wMonth + "/" + this.wDay + " " + this.wHour + ":" + this.wMinute + ":" + this.wSecond;
   }
}
