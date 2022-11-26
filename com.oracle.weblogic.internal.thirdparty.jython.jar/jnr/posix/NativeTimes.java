package jnr.posix;

import jnr.ffi.Memory;
import jnr.ffi.Pointer;
import jnr.ffi.Runtime;
import jnr.ffi.StructLayout;

public final class NativeTimes implements Times {
   private static final Layout layout = new Layout(Runtime.getSystemRuntime());
   final Pointer memory;

   static NativeTimes times(BaseNativePOSIX posix) {
      NativeTimes tms = new NativeTimes(posix);
      return posix.libc().times(tms) == -1L ? null : tms;
   }

   NativeTimes(NativePOSIX posix) {
      this.memory = Memory.allocate(posix.getRuntime(), layout.size());
   }

   public long utime() {
      return layout.tms_utime.get(this.memory);
   }

   public long stime() {
      return layout.tms_stime.get(this.memory);
   }

   public long cutime() {
      return layout.tms_cutime.get(this.memory);
   }

   public long cstime() {
      return layout.tms_cstime.get(this.memory);
   }

   static final class Layout extends StructLayout {
      public final StructLayout.clock_t tms_utime = new StructLayout.clock_t();
      public final StructLayout.clock_t tms_stime = new StructLayout.clock_t();
      public final StructLayout.clock_t tms_cutime = new StructLayout.clock_t();
      public final StructLayout.clock_t tms_cstime = new StructLayout.clock_t();

      Layout(Runtime runtime) {
         super(runtime);
      }
   }
}
