package jnr.posix;

import jnr.ffi.Runtime;
import jnr.ffi.Struct;

public class DefaultNativeRLimit extends RLimit {
   public final Struct.UnsignedLong rlim_cur = new Struct.UnsignedLong();
   public final Struct.UnsignedLong rlim_max = new Struct.UnsignedLong();

   protected DefaultNativeRLimit(Runtime runtime) {
      super(runtime);
   }

   public void init(long rlimCur, long rlimMax) {
      this.rlim_cur.set(rlimCur);
      this.rlim_max.set(rlimMax);
   }

   public long rlimCur() {
      return this.rlim_cur.get();
   }

   public long rlimMax() {
      return this.rlim_max.get();
   }
}
