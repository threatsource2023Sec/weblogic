package jnr.posix;

import jnr.ffi.NativeType;
import jnr.ffi.Runtime;
import jnr.ffi.StructLayout;

public final class AixFileStat extends BaseFileStat implements NanosecondFileStat {
   private static final Layout layout = new Layout(Runtime.getSystemRuntime());

   public AixFileStat(NativePOSIX posix) {
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
      return layout.st_dev.get(this.memory);
   }

   public int gid() {
      return (int)layout.st_gid.get(this.memory);
   }

   public long ino() {
      return layout.st_ino.get(this.memory);
   }

   public int mode() {
      return (int)layout.st_mode.get(this.memory) & '\uffff';
   }

   public long mtime() {
      return layout.st_mtime.get(this.memory);
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
      return (int)layout.st_uid.get(this.memory);
   }

   public long aTimeNanoSecs() {
      return (long)layout.st_atime_n.get(this.memory);
   }

   public long cTimeNanoSecs() {
      return (long)layout.st_ctime_n.get(this.memory);
   }

   public long mTimeNanoSecs() {
      return (long)layout.st_mtime_n.get(this.memory);
   }

   private static final class Layout extends StructLayout {
      public final StructLayout.Unsigned64 st_dev;
      public final StructLayout.Signed64 st_ino;
      public final StructLayout.Unsigned32 st_mode;
      public final StructLayout.Signed16 st_nlink;
      public final StructLayout.Unsigned16 st_flag;
      public final StructLayout.Unsigned32 st_uid;
      public final StructLayout.Unsigned32 st_gid;
      public final StructLayout.Unsigned64 st_rdev;
      public final StructLayout.Signed64 st_size;
      public final StructLayout.Signed64 st_atime;
      public final StructLayout.Signed32 st_atime_n;
      public final StructLayout.Signed32 st_pad1;
      public final StructLayout.Signed64 st_mtime;
      public final StructLayout.Signed32 st_mtime_n;
      public final StructLayout.Signed32 st_pad2;
      public final StructLayout.Signed64 st_ctime;
      public final StructLayout.Signed32 st_ctime_n;
      public final StructLayout.Signed32 st_pad3;
      public final StructLayout.Unsigned64 st_blksize;
      public final StructLayout.Unsigned64 st_blocks;
      public final StructLayout.Signed32 st_vfstype;
      public final StructLayout.Unsigned32 st_vfs;
      public final StructLayout.Unsigned32 st_type;
      public final StructLayout.Unsigned32 st_gen;
      public final StructLayout.Padding st_reserved;

      private Layout(Runtime runtime) {
         super(runtime);
         this.st_dev = new StructLayout.Unsigned64();
         this.st_ino = new StructLayout.Signed64();
         this.st_mode = new StructLayout.Unsigned32();
         this.st_nlink = new StructLayout.Signed16();
         this.st_flag = new StructLayout.Unsigned16();
         this.st_uid = new StructLayout.Unsigned32();
         this.st_gid = new StructLayout.Unsigned32();
         this.st_rdev = new StructLayout.Unsigned64();
         this.st_size = new StructLayout.Signed64();
         this.st_atime = new StructLayout.Signed64();
         this.st_atime_n = new StructLayout.Signed32();
         this.st_pad1 = new StructLayout.Signed32();
         this.st_mtime = new StructLayout.Signed64();
         this.st_mtime_n = new StructLayout.Signed32();
         this.st_pad2 = new StructLayout.Signed32();
         this.st_ctime = new StructLayout.Signed64();
         this.st_ctime_n = new StructLayout.Signed32();
         this.st_pad3 = new StructLayout.Signed32();
         this.st_blksize = new StructLayout.Unsigned64();
         this.st_blocks = new StructLayout.Unsigned64();
         this.st_vfstype = new StructLayout.Signed32();
         this.st_vfs = new StructLayout.Unsigned32();
         this.st_type = new StructLayout.Unsigned32();
         this.st_gen = new StructLayout.Unsigned32();
         this.st_reserved = new StructLayout.Padding(NativeType.UINT, 11);
      }

      // $FF: synthetic method
      Layout(Runtime x0, Object x1) {
         this(x0);
      }
   }
}
