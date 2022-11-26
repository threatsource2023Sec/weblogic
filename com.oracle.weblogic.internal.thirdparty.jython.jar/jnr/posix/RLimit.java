package jnr.posix;

import jnr.ffi.Runtime;
import jnr.ffi.Struct;

public abstract class RLimit extends Struct {
   protected RLimit(Runtime runtime) {
      super(runtime);
   }

   public abstract void init(long var1, long var3);

   public abstract long rlimCur();

   public abstract long rlimMax();
}
