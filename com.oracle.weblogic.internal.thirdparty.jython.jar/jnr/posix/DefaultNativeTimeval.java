package jnr.posix;

import jnr.ffi.Runtime;
import jnr.ffi.Struct;

public final class DefaultNativeTimeval extends Timeval {
   public final Struct.SignedLong tv_sec = new Struct.SignedLong();
   public final Struct.SignedLong tv_usec = new Struct.SignedLong();

   public DefaultNativeTimeval(Runtime runtime) {
      super(runtime);
   }

   public void setTime(long[] timeval) {
      assert timeval.length == 2;

      this.tv_sec.set(timeval[0]);
      this.tv_usec.set(timeval[1]);
   }

   public void sec(long sec) {
      this.tv_sec.set(sec);
   }

   public void usec(long usec) {
      this.tv_usec.set(usec);
   }

   public long sec() {
      return this.tv_sec.get();
   }

   public long usec() {
      return this.tv_usec.get();
   }
}
