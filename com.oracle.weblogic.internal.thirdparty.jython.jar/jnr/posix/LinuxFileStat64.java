package jnr.posix;

import jnr.ffi.Runtime;
import jnr.ffi.StructLayout;

public final class LinuxFileStat64 extends BaseFileStat implements NanosecondFileStat {
   private static final Layout layout = new Layout(Runtime.getSystemRuntime());

   public LinuxFileStat64(LinuxPOSIX posix) {
      super(posix, layout);
   }

   public long atime() {
      return layout.st_atime.get(this.memory);
   }

   public long aTimeNanoSecs() {
      return layout.st_atimensec.get(this.memory);
   }

   public long blockSize() {
      return layout.st_blksize.get(this.memory);
   }

   public long blocks() {
      return layout.st_blocks.get(this.memory);
   }

   public long ctime() {
      return layout.st_ctime.get(this.memory);
   }

   public long cTimeNanoSecs() {
      return layout.st_ctimensec.get(this.memory);
   }

   public long dev() {
      return layout.st_dev.get(this.memory);
   }

   public int gid() {
      return (int)layout.st_gid.get(this.memory);
   }

   public long ino() {
      return layout.st_ino.get(this.memory);
   }

   public int mode() {
      return (int)layout.st_mode.get(this.memory);
   }

   public long mtime() {
      return layout.st_mtime.get(this.memory);
   }

   public long mTimeNanoSecs() {
      return layout.st_mtimensec.get(this.memory);
   }

   public int nlink() {
      return (int)layout.st_nlink.get(this.memory);
   }

   public long rdev() {
      return layout.st_rdev.get(this.memory);
   }

   public long st_size() {
      return layout.st_size.get(this.memory);
   }

   public int uid() {
      return (int)layout.st_uid.get(this.memory);
   }

   public static final class Layout extends StructLayout {
      public final StructLayout.dev_t st_dev = new StructLayout.dev_t();
      public final StructLayout.ino_t st_ino = new StructLayout.ino_t();
      public final StructLayout.nlink_t st_nlink = new StructLayout.nlink_t();
      public final StructLayout.mode_t st_mode = new StructLayout.mode_t();
      public final StructLayout.uid_t st_uid = new StructLayout.uid_t();
      public final StructLayout.gid_t st_gid = new StructLayout.gid_t();
      public final StructLayout.dev_t st_rdev = new StructLayout.dev_t();
      public final StructLayout.size_t st_size = new StructLayout.size_t();
      public final StructLayout.blksize_t st_blksize = new StructLayout.blksize_t();
      public final StructLayout.blkcnt_t st_blocks = new StructLayout.blkcnt_t();
      public final StructLayout.time_t st_atime = new StructLayout.time_t();
      public final StructLayout.SignedLong st_atimensec = new StructLayout.SignedLong();
      public final StructLayout.time_t st_mtime = new StructLayout.time_t();
      public final StructLayout.SignedLong st_mtimensec = new StructLayout.SignedLong();
      public final StructLayout.time_t st_ctime = new StructLayout.time_t();
      public final StructLayout.SignedLong st_ctimensec = new StructLayout.SignedLong();
      public final StructLayout.Signed64 __unused4 = new StructLayout.Signed64();
      public final StructLayout.Signed64 __unused5 = new StructLayout.Signed64();
      public final StructLayout.Signed64 __unused6 = new StructLayout.Signed64();

      public Layout(Runtime runtime) {
         super(runtime);
      }
   }
}
