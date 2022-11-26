package jnr.posix;

import jnr.ffi.Runtime;
import jnr.ffi.StructLayout;

public class WindowsFileStat extends BaseFileStat {
   private static final Layout layout = new Layout(Runtime.getSystemRuntime());

   public WindowsFileStat(NativePOSIX posix) {
      super(posix, layout);
   }

   public long atime() {
      return layout.st_atime.get(this.memory);
   }

   public long blockSize() {
      return 512L;
   }

   public long blocks() {
      return (layout.st_size.get(this.memory) + 512L - 1L) / 512L;
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
      return layout.st_mode.get(this.memory) & -19 & '\uffff';
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

   public boolean groupMember(int gid) {
      return true;
   }

   public boolean isExecutable() {
      if (this.isOwned()) {
         return (this.mode() & 64) != 0;
      } else if (this.isGroupOwned()) {
         return (this.mode() & 8) != 0;
      } else {
         return (this.mode() & 1) == 0;
      }
   }

   public boolean isExecutableReal() {
      if (this.isROwned()) {
         return (this.mode() & 64) != 0;
      } else if (this.groupMember(this.gid())) {
         return (this.mode() & 8) != 0;
      } else {
         return (this.mode() & 1) == 0;
      }
   }

   public boolean isOwned() {
      return true;
   }

   public boolean isROwned() {
      return true;
   }

   public boolean isReadable() {
      if (this.isOwned()) {
         return (this.mode() & 256) != 0;
      } else if (this.isGroupOwned()) {
         return (this.mode() & 32) != 0;
      } else {
         return (this.mode() & 4) == 0;
      }
   }

   public boolean isReadableReal() {
      if (this.isROwned()) {
         return (this.mode() & 256) != 0;
      } else if (this.groupMember(this.gid())) {
         return (this.mode() & 32) != 0;
      } else {
         return (this.mode() & 4) == 0;
      }
   }

   public boolean isWritable() {
      if (this.isOwned()) {
         return (this.mode() & 128) != 0;
      } else if (this.isGroupOwned()) {
         return (this.mode() & 16) != 0;
      } else {
         return (this.mode() & 2) == 0;
      }
   }

   public boolean isWritableReal() {
      if (this.isROwned()) {
         return (this.mode() & 128) != 0;
      } else if (this.groupMember(this.gid())) {
         return (this.mode() & 16) != 0;
      } else {
         return (this.mode() & 2) == 0;
      }
   }

   public String toString() {
      return "st_dev: " + layout.st_dev.get(this.memory) + ", st_mode: " + Integer.toOctalString(this.mode()) + ", layout.st_nlink: " + layout.st_nlink.get(this.memory) + ", layout.st_rdev: " + layout.st_rdev.get(this.memory) + ", layout.st_size: " + layout.st_size.get(this.memory) + ", layout.st_uid: " + layout.st_uid.get(this.memory) + ", layout.st_gid: " + layout.st_gid.get(this.memory) + ", layout.st_atime: " + layout.st_atime.get(this.memory) + ", layout.st_ctime: " + layout.st_ctime.get(this.memory) + ", layout.st_mtime: " + layout.st_mtime.get(this.memory) + ", layout.st_ino: " + layout.st_ino.get(this.memory);
   }

   private static final class Layout extends StructLayout {
      public final StructLayout.Signed32 st_dev;
      public final StructLayout.Signed16 st_ino;
      public final StructLayout.Signed16 st_mode;
      public final StructLayout.Signed16 st_nlink;
      public final StructLayout.Signed16 st_uid;
      public final StructLayout.Signed16 st_gid;
      public final StructLayout.Signed32 st_rdev;
      public final StructLayout.Signed64 st_size;
      public final StructLayout.Signed64 st_atime;
      public final StructLayout.Signed64 st_mtime;
      public final StructLayout.Signed64 st_ctime;

      private Layout(Runtime runtime) {
         super(runtime);
         this.st_dev = new StructLayout.Signed32();
         this.st_ino = new StructLayout.Signed16();
         this.st_mode = new StructLayout.Signed16();
         this.st_nlink = new StructLayout.Signed16();
         this.st_uid = new StructLayout.Signed16();
         this.st_gid = new StructLayout.Signed16();
         this.st_rdev = new StructLayout.Signed32();
         this.st_size = new StructLayout.Signed64();
         this.st_atime = new StructLayout.Signed64();
         this.st_mtime = new StructLayout.Signed64();
         this.st_ctime = new StructLayout.Signed64();
      }

      // $FF: synthetic method
      Layout(Runtime x0, Object x1) {
         this(x0);
      }
   }
}
