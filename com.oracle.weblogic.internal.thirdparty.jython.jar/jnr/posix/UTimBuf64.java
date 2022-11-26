package jnr.posix;

import jnr.ffi.Runtime;
import jnr.ffi.Struct;

public final class UTimBuf64 extends Struct {
   public final Struct.Signed64 actime = new Struct.Signed64();
   public final Struct.Signed64 modtime = new Struct.Signed64();

   public UTimBuf64(Runtime runtime, long actime, long modtime) {
      super(runtime);
      this.actime.set(actime);
      this.modtime.set(modtime);
   }
}
