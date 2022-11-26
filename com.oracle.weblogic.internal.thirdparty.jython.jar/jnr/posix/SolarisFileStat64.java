package jnr.posix;

import jnr.ffi.Runtime;
import jnr.ffi.StructLayout;

public class SolarisFileStat64 extends BaseFileStat implements NanosecondFileStat {
   private static final Layout layout = new Layout(Runtime.getSystemRuntime());

   public SolarisFileStat64() {
      this((NativePOSIX)null);
   }

   public SolarisFileStat64(NativePOSIX posix) {
      super(posix, layout);
   }

   public long atime() {
      return layout.st_atim_sec.get(this.memory);
   }

   public long blocks() {
      return layout.st_blocks.get(this.memory);
   }

   public long blockSize() {
      return (long)layout.st_blksize.get(this.memory);
   }

   public long ctime() {
      return layout.st_ctim_sec.get(this.memory);
   }

   public long dev() {
      return layout.st_dev.get(this.memory);
   }

   public int gid() {
      return layout.st_gid.get(this.memory);
   }

   public long ino() {
      return layout.st_ino.get(this.memory);
   }

   public int mode() {
      return layout.st_mode.get(this.memory) & '\uffff';
   }

   public long mtime() {
      return layout.st_mtim_sec.get(this.memory);
   }

   public int nlink() {
      return layout.st_nlink.get(this.memory);
   }

   public long rdev() {
      return layout.st_rdev.get(this.memory);
   }

   public long st_size() {
      return layout.st_size.get(this.memory);
   }

   public int uid() {
      return layout.st_uid.get(this.memory);
   }

   public long aTimeNanoSecs() {
      return layout.st_atim_nsec.get(this.memory);
   }

   public long cTimeNanoSecs() {
      return layout.st_ctim_nsec.get(this.memory);
   }

   public long mTimeNanoSecs() {
      return layout.st_mtim_nsec.get(this.memory);
   }

   static final class Layout extends StructLayout {
      public static final int _ST_FSTYPSZ = 16;
      public final StructLayout.UnsignedLong st_dev = new StructLayout.UnsignedLong();
      public final StructLayout.Signed64 st_ino = new StructLayout.Signed64();
      public final StructLayout.Signed32 st_mode = new StructLayout.Signed32();
      public final StructLayout.Signed32 st_nlink = new StructLayout.Signed32();
      public final StructLayout.Signed32 st_uid = new StructLayout.Signed32();
      public final StructLayout.Signed32 st_gid = new StructLayout.Signed32();
      public final StructLayout.UnsignedLong st_rdev = new StructLayout.UnsignedLong();
      public final StructLayout.Signed64 st_size = new StructLayout.Signed64();
      public final StructLayout.SignedLong st_atim_sec = new StructLayout.SignedLong();
      public final StructLayout.SignedLong st_atim_nsec = new StructLayout.SignedLong();
      public final StructLayout.SignedLong st_mtim_sec = new StructLayout.SignedLong();
      public final StructLayout.SignedLong st_mtim_nsec = new StructLayout.SignedLong();
      public final StructLayout.SignedLong st_ctim_sec = new StructLayout.SignedLong();
      public final StructLayout.SignedLong st_ctim_nsec = new StructLayout.SignedLong();
      public final StructLayout.Signed32 st_blksize = new StructLayout.Signed32();
      public final StructLayout.Signed64 st_blocks = new StructLayout.Signed64();
      public final StructLayout.Signed8[] st_fstype = (StructLayout.Signed8[])this.array(new StructLayout.Signed8[16]);

      Layout(Runtime runtime) {
         super(runtime);
      }
   }
}
