package jnr.posix;

import jnr.ffi.Runtime;
import jnr.ffi.StructLayout;

public final class OpenBSDFileStat extends BaseFileStat implements NanosecondFileStat {
   private static final Layout layout = new Layout(Runtime.getSystemRuntime());

   public OpenBSDFileStat(NativePOSIX posix) {
      super(posix, layout);
   }

   public long atime() {
      return layout.st_atime.get(this.memory);
   }

   public long blocks() {
      return layout.st_blocks.get(this.memory);
   }

   public long blockSize() {
      return layout.st_blksize.get(this.memory);
   }

   public long ctime() {
      return layout.st_ctime.get(this.memory);
   }

   public long dev() {
      return (long)layout.st_dev.get(this.memory);
   }

   public int gid() {
      return (int)layout.st_gid.get(this.memory);
   }

   public long ino() {
      return layout.st_ino.get(this.memory);
   }

   public int mode() {
      return (int)(layout.st_mode.get(this.memory) & 65535L);
   }

   public long mtime() {
      return layout.st_mtime.get(this.memory);
   }

   public int nlink() {
      return (int)layout.st_nlink.get(this.memory);
   }

   public long rdev() {
      return (long)layout.st_rdev.get(this.memory);
   }

   public long st_size() {
      return layout.st_size.get(this.memory);
   }

   public int uid() {
      return (int)layout.st_uid.get(this.memory);
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

   private static final class Layout extends StructLayout {
      public final StructLayout.Unsigned32 st_mode;
      public final dev_t st_dev;
      public final StructLayout.Unsigned64 st_ino;
      public final StructLayout.Unsigned32 st_nlink;
      public final StructLayout.Unsigned32 st_uid;
      public final StructLayout.Unsigned32 st_gid;
      public final dev_t st_rdev;
      public final time_t st_atime;
      public final StructLayout.SignedLong st_atimensec;
      public final time_t st_mtime;
      public final StructLayout.SignedLong st_mtimensec;
      public final time_t st_ctime;
      public final StructLayout.SignedLong st_ctimensec;
      public final StructLayout.Signed64 st_size;
      public final StructLayout.Signed64 st_blocks;
      public final StructLayout.Unsigned32 st_blksize;
      public final StructLayout.Unsigned32 st_flags;
      public final StructLayout.Unsigned32 st_gen;
      public final time_t st_birthtime;
      public final StructLayout.SignedLong st_birthtimensec;

      private Layout(Runtime runtime) {
         super(runtime);
         this.st_mode = new StructLayout.Unsigned32();
         this.st_dev = new dev_t();
         this.st_ino = new StructLayout.Unsigned64();
         this.st_nlink = new StructLayout.Unsigned32();
         this.st_uid = new StructLayout.Unsigned32();
         this.st_gid = new StructLayout.Unsigned32();
         this.st_rdev = new dev_t();
         this.st_atime = new time_t();
         this.st_atimensec = new StructLayout.SignedLong();
         this.st_mtime = new time_t();
         this.st_mtimensec = new StructLayout.SignedLong();
         this.st_ctime = new time_t();
         this.st_ctimensec = new StructLayout.SignedLong();
         this.st_size = new StructLayout.Signed64();
         this.st_blocks = new StructLayout.Signed64();
         this.st_blksize = new StructLayout.Unsigned32();
         this.st_flags = new StructLayout.Unsigned32();
         this.st_gen = new StructLayout.Unsigned32();
         this.st_birthtime = new time_t();
         this.st_birthtimensec = new StructLayout.SignedLong();
      }

      // $FF: synthetic method
      Layout(Runtime x0, Object x1) {
         this(x0);
      }

      public final class dev_t extends StructLayout.Signed32 {
         public dev_t() {
            super();
         }
      }

      public final class time_t extends StructLayout.Signed64 {
         public time_t() {
            super();
         }
      }
   }
}
