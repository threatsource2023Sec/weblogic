package jnr.posix;

import jnr.ffi.Runtime;
import jnr.ffi.StructLayout;

public class SolarisFileStat32 extends BaseFileStat implements NanosecondFileStat {
   private static final Layout layout = new Layout(Runtime.getSystemRuntime());

   public SolarisFileStat32(NativePOSIX posix) {
      super(posix, layout);
   }

   public long atime() {
      return (long)layout.st_atim_sec.get(this.memory);
   }

   public long blocks() {
      return layout.st_blocks.get(this.memory);
   }

   public long blockSize() {
      return (long)layout.st_blksize.get(this.memory);
   }

   public long ctime() {
      return (long)layout.st_ctim_sec.get(this.memory);
   }

   public long dev() {
      return (long)layout.st_dev.get(this.memory);
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
      return (long)layout.st_mtim_sec.get(this.memory);
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
      return (long)layout.st_atim_nsec.get(this.memory);
   }

   public long cTimeNanoSecs() {
      return (long)layout.st_ctim_nsec.get(this.memory);
   }

   public long mTimeNanoSecs() {
      return (long)layout.st_mtim_nsec.get(this.memory);
   }

   static final class Layout extends StructLayout {
      public static final int _ST_FSTYPSZ = 16;
      public final StructLayout.Signed32 st_dev = new StructLayout.Signed32();
      public final StructLayout.SignedLong[] st_pad1 = (StructLayout.SignedLong[])this.array(new StructLayout.SignedLong[3]);
      public final StructLayout.Signed64 st_ino = new StructLayout.Signed64();
      public final StructLayout.Signed32 st_mode = new StructLayout.Signed32();
      public final StructLayout.Signed32 st_nlink = new StructLayout.Signed32();
      public final StructLayout.Signed32 st_uid = new StructLayout.Signed32();
      public final StructLayout.Signed32 st_gid = new StructLayout.Signed32();
      public final StructLayout.Signed32 st_rdev = new StructLayout.Signed32();
      public final StructLayout.SignedLong[] st_pad2 = (StructLayout.SignedLong[])this.array(new StructLayout.SignedLong[2]);
      public final StructLayout.Signed64 st_size = new StructLayout.Signed64();
      public final StructLayout.Signed32 st_atim_sec = new StructLayout.Signed32();
      public final StructLayout.Signed32 st_atim_nsec = new StructLayout.Signed32();
      public final StructLayout.Signed32 st_mtim_sec = new StructLayout.Signed32();
      public final StructLayout.Signed32 st_mtim_nsec = new StructLayout.Signed32();
      public final StructLayout.Signed32 st_ctim_sec = new StructLayout.Signed32();
      public final StructLayout.Signed32 st_ctim_nsec = new StructLayout.Signed32();
      public final StructLayout.Signed32 st_blksize = new StructLayout.Signed32();
      public final StructLayout.Signed64 st_blocks = new StructLayout.Signed64();
      public final StructLayout.Signed8[] st_fstype = (StructLayout.Signed8[])this.array(new StructLayout.Signed8[16]);
      public final StructLayout.SignedLong[] st_pad4 = (StructLayout.SignedLong[])this.array(new StructLayout.SignedLong[8]);

      Layout(Runtime runtime) {
         super(runtime);
      }
   }
}
