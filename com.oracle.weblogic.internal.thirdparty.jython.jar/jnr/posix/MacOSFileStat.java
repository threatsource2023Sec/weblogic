package jnr.posix;

import jnr.ffi.Runtime;
import jnr.ffi.StructLayout;

public final class MacOSFileStat extends BaseFileStat implements NanosecondFileStat {
   private static final Layout layout = new Layout(Runtime.getSystemRuntime());

   public MacOSFileStat(MacOSPOSIX posix) {
      super(posix, layout);
   }

   public long atime() {
      return layout.st_atime.get(this.memory);
   }

   public long blocks() {
      return layout.st_blocks.get(this.memory);
   }

   public long blockSize() {
      return (long)layout.st_blksize.get(this.memory);
   }

   public long ctime() {
      return layout.st_ctime.get(this.memory);
   }

   public long dev() {
      return (long)layout.st_dev.get(this.memory);
   }

   public int gid() {
      return layout.st_gid.get(this.memory);
   }

   public long ino() {
      return (long)layout.st_ino.get(this.memory);
   }

   public int mode() {
      return layout.st_mode.get(this.memory) & '\uffff';
   }

   public long mtime() {
      return layout.st_mtime.get(this.memory);
   }

   public int nlink() {
      return layout.st_nlink.get(this.memory);
   }

   public long rdev() {
      return (long)layout.st_rdev.get(this.memory);
   }

   public long st_size() {
      return layout.st_size.get(this.memory);
   }

   public int uid() {
      return layout.st_uid.get(this.memory);
   }

   public long aTimeNanoSecs() {
      return layout.st_atimensec.get(this.memory);
   }

   public long cTimeNanoSecs() {
      return layout.st_ctimensec.get(this.memory);
   }

   public long mTimeNanoSecs() {
      return layout.st_mtimensec.get(this.memory);
   }

   public static class Layout extends StructLayout {
      public final StructLayout.Signed32 st_dev = new StructLayout.Signed32();
      public final StructLayout.Signed32 st_ino = new StructLayout.Signed32();
      public final StructLayout.Signed16 st_mode = new StructLayout.Signed16();
      public final StructLayout.Signed16 st_nlink = new StructLayout.Signed16();
      public final StructLayout.Signed32 st_uid = new StructLayout.Signed32();
      public final StructLayout.Signed32 st_gid = new StructLayout.Signed32();
      public final StructLayout.Signed32 st_rdev = new StructLayout.Signed32();
      public final time_t st_atime = new time_t();
      public final StructLayout.SignedLong st_atimensec = new StructLayout.SignedLong();
      public final time_t st_mtime = new time_t();
      public final StructLayout.SignedLong st_mtimensec = new StructLayout.SignedLong();
      public final time_t st_ctime = new time_t();
      public final StructLayout.SignedLong st_ctimensec = new StructLayout.SignedLong();
      public final StructLayout.Signed64 st_size = new StructLayout.Signed64();
      public final StructLayout.Signed64 st_blocks = new StructLayout.Signed64();
      public final StructLayout.Signed32 st_blksize = new StructLayout.Signed32();
      public final StructLayout.Signed32 st_flags = new StructLayout.Signed32();
      public final StructLayout.Signed32 st_gen = new StructLayout.Signed32();
      public final StructLayout.Signed32 st_lspare = new StructLayout.Signed32();
      public final StructLayout.Signed64 st_qspare0 = new StructLayout.Signed64();
      public final StructLayout.Signed64 st_qspare1 = new StructLayout.Signed64();

      public Layout(Runtime runtime) {
         super(runtime);
      }

      public final class time_t extends StructLayout.SignedLong {
         public time_t() {
            super();
         }
      }
   }
}
