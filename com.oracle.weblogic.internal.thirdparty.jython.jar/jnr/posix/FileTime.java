package jnr.posix;

import jnr.ffi.Runtime;
import jnr.ffi.Struct;

public class FileTime extends Struct {
   public final Struct.Unsigned32 dwLowDateTime = new Struct.Unsigned32();
   public final Struct.Unsigned32 dwHighDateTime = new Struct.Unsigned32();

   FileTime(Runtime runtime) {
      super(runtime);
   }
}
