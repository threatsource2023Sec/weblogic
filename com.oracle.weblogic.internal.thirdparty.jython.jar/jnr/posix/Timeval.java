package jnr.posix;

import jnr.ffi.Runtime;
import jnr.ffi.Struct;

public abstract class Timeval extends Struct {
   public Timeval(Runtime runtime) {
      super(runtime);
   }

   public abstract void setTime(long[] var1);

   public abstract void sec(long var1);

   public abstract void usec(long var1);

   public abstract long sec();

   public abstract long usec();
}
