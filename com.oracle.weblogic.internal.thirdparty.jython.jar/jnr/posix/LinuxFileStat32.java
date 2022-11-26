package jnr.posix;

import jnr.ffi.Runtime;
import jnr.ffi.StructLayout;

public final class LinuxFileStat32 extends BaseFileStat implements NanosecondFileStat {
   private static final Layout layout = new Layout(Runtime.getSystemRuntime());

   public LinuxFileStat32() {
      this((BaseNativePOSIX)null);
   }

   public LinuxFileStat32(BaseNativePOSIX posix) {
      super(posix, layout);
   }

   public long atime() {
      return (long)layout.st_atim_sec.get(this.memory);
   }

   public long aTimeNanoSecs() {
      return (long)layout.st_atim_nsec.get(this.memory);
   }

   public long blocks() {
      return (long)layout.st_blocks.get(this.memory);
   }

   public long blockSize() {
      return (long)layout.st_blksize.get(this.memory);
   }

   public long ctime() {
      return (long)layout.st_ctim_sec.get(this.memory);
   }

   public long cTimeNanoSecs() {
      return (long)layout.st_ctim_nsec.get(this.memory);
   }

   public long dev() {
      return layout.st_dev.get(this.memory);
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
      return (long)layout.st_mtim_sec.get(this.memory);
   }

   public long mTimeNanoSecs() {
      return (long)layout.st_mtim_nsec.get(this.memory);
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

   private static final class Layout extends StructLayout {
      public final StructLayout.Signed64 st_dev;
      public final StructLayout.Signed16 __pad1;
      public final StructLayout.Signed32 st_ino;
      public final StructLayout.Signed32 st_mode;
      public final StructLayout.Signed32 st_nlink;
      public final StructLayout.Signed32 st_uid;
      public final StructLayout.Signed32 st_gid;
      public final StructLayout.Signed64 st_rdev;
      public final StructLayout.Signed16 __pad2;
      public final StructLayout.Signed64 st_size;
      public final StructLayout.Signed32 st_blksize;
      public final StructLayout.Signed32 st_blocks;
      public final StructLayout.Signed32 __unused4;
      public final StructLayout.Signed32 st_atim_sec;
      public final StructLayout.Signed32 st_atim_nsec;
      public final StructLayout.Signed32 st_mtim_sec;
      public final StructLayout.Signed32 st_mtim_nsec;
      public final StructLayout.Signed32 st_ctim_sec;
      public final StructLayout.Signed32 st_ctim_nsec;
      public final StructLayout.Signed64 __unused5;

      private Layout(Runtime runtime) {
         super(runtime);
         this.st_dev = new StructLayout.Signed64();
         this.__pad1 = new StructLayout.Signed16();
         this.st_ino = new StructLayout.Signed32();
         this.st_mode = new StructLayout.Signed32();
         this.st_nlink = new StructLayout.Signed32();
         this.st_uid = new StructLayout.Signed32();
         this.st_gid = new StructLayout.Signed32();
         this.st_rdev = new StructLayout.Signed64();
         this.__pad2 = new StructLayout.Signed16();
         this.st_size = new StructLayout.Signed64();
         this.st_blksize = new StructLayout.Signed32();
         this.st_blocks = new StructLayout.Signed32();
         this.__unused4 = new StructLayout.Signed32();
         this.st_atim_sec = new StructLayout.Signed32();
         this.st_atim_nsec = new StructLayout.Signed32();
         this.st_mtim_sec = new StructLayout.Signed32();
         this.st_mtim_nsec = new StructLayout.Signed32();
         this.st_ctim_sec = new StructLayout.Signed32();
         this.st_ctim_nsec = new StructLayout.Signed32();
         this.__unused5 = new StructLayout.Signed64();
      }

      // $FF: synthetic method
      Layout(Runtime x0, Object x1) {
         this(x0);
      }
   }
}
