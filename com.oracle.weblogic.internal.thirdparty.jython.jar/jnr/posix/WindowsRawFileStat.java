package jnr.posix;

import jnr.posix.util.WindowsHelpers;
import jnr.posix.windows.CommonFileInformation;

public class WindowsRawFileStat extends JavaFileStat {
   private int st_atime;
   private int st_rdev;
   private int st_dev;
   private int st_nlink;
   private int st_mode;

   public WindowsRawFileStat(POSIX posix, POSIXHandler handler) {
      super(posix, handler);
   }

   public void setup(String path, CommonFileInformation fileInfo) {
      this.st_mode = fileInfo.getMode(path);
      this.setup(fileInfo);
      if (WindowsHelpers.isDriveLetterPath(path)) {
         int letterAsNumber = Character.toUpperCase(path.charAt(0)) - 65;
         this.st_rdev = letterAsNumber;
         this.st_dev = letterAsNumber;
      }

   }

   public void setup(CommonFileInformation fileInfo) {
      this.st_atime = (int)fileInfo.getLastAccessTimeMicroseconds();
      this.st_mtime = (int)fileInfo.getLastWriteTimeMicroseconds();
      this.st_ctime = (int)fileInfo.getCreationTimeMicroseconds();
      this.st_size = this.isDirectory() ? 0L : fileInfo.getFileSize();
      this.st_nlink = 1;
      this.st_mode &= -19;
   }

   public int mode() {
      return this.st_mode;
   }

   public int gid() {
      return 0;
   }

   public int uid() {
      return 0;
   }

   public long atime() {
      return (long)this.st_atime;
   }

   public long dev() {
      return (long)this.st_dev;
   }

   public int nlink() {
      return this.st_nlink;
   }

   public long rdev() {
      return (long)this.st_rdev;
   }

   public long blocks() {
      return -1L;
   }

   public long blockSize() {
      return -1L;
   }

   public boolean isBlockDev() {
      return (this.mode() & '\uf000') == 24576;
   }

   public boolean isCharDev() {
      return (this.mode() & '\uf000') == 8192;
   }

   public boolean isDirectory() {
      return (this.mode() & '\uf000') == 16384;
   }

   public boolean isEmpty() {
      return this.st_size() == 0L;
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

   public boolean isFile() {
      return (this.mode() & '\uf000') == 32768;
   }

   public boolean isFifo() {
      return (this.mode() & '\uf000') == 4096;
   }

   public boolean isGroupOwned() {
      return this.groupMember(this.gid());
   }

   public boolean isIdentical(FileStat other) {
      return this.dev() == other.dev() && this.ino() == other.ino();
   }

   public boolean isNamedPipe() {
      return (this.mode() & 4096) != 0;
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

   public boolean isSetgid() {
      return (this.mode() & 1024) != 0;
   }

   public boolean isSetuid() {
      return (this.mode() & 2048) != 0;
   }

   public boolean isSocket() {
      return (this.mode() & '\uf000') == 49152;
   }

   public boolean isSticky() {
      return (this.mode() & 512) != 0;
   }

   public boolean isSymlink() {
      return (this.mode() & '\uf000') == 40960;
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
}
