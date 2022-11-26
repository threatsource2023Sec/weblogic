package jnr.posix;

import java.io.File;
import java.io.IOException;

public class JavaFileStat implements FileStat {
   private final POSIXHandler handler;
   private final POSIX posix;
   short st_mode;
   int st_blksize;
   long st_size;
   int st_ctime;
   int st_mtime;

   public JavaFileStat(POSIX posix, POSIXHandler handler) {
      this.handler = handler;
      this.posix = posix;
   }

   public void setup(String path) {
      File file = new JavaSecuredFile(path);
      this.st_blksize = 4096;
      this.st_mode = this.calculateMode(file, this.st_mode);
      this.st_size = file.length();
      this.st_mtime = (int)(file.lastModified() / 1000L);
      if (file.getParentFile() != null) {
         this.st_ctime = (int)(file.getParentFile().lastModified() / 1000L);
      } else {
         this.st_ctime = this.st_mtime;
      }

   }

   private short calculateMode(File file, short st_mode) {
      if (file.canRead()) {
         st_mode = (short)(st_mode | 292);
      }

      if (file.canWrite()) {
         st_mode = (short)(st_mode | 146);
         st_mode = (short)(st_mode & -19);
      }

      if (file.isDirectory()) {
         st_mode = (short)(st_mode | 16384);
      } else if (file.isFile()) {
         st_mode = (short)(st_mode | '耀');
      }

      try {
         st_mode = this.calculateSymlink(file, st_mode);
      } catch (IOException var4) {
      }

      return st_mode;
   }

   private short calculateSymlink(File file, short st_mode) throws IOException {
      if (file.getAbsoluteFile().getParentFile() == null) {
         return st_mode;
      } else {
         File absoluteParent = file.getAbsoluteFile().getParentFile();
         File canonicalParent = absoluteParent.getCanonicalFile();
         if (canonicalParent.getAbsolutePath().equals(absoluteParent.getAbsolutePath()) && !file.getAbsolutePath().equalsIgnoreCase(file.getCanonicalPath())) {
            st_mode = (short)(st_mode | 'ꀀ');
            return st_mode;
         } else {
            File file = new JavaSecuredFile(canonicalParent.getAbsolutePath() + "/" + file.getName());
            if (!file.getAbsolutePath().equalsIgnoreCase(file.getCanonicalPath())) {
               st_mode = (short)(st_mode | 'ꀀ');
            }

            return st_mode;
         }
      }
   }

   public long atime() {
      return (long)this.st_mtime;
   }

   public long blocks() {
      this.handler.unimplementedError("stat.st_blocks");
      return -1L;
   }

   public long blockSize() {
      return (long)this.st_blksize;
   }

   public long ctime() {
      return (long)this.st_ctime;
   }

   public long dev() {
      this.handler.unimplementedError("stat.st_dev");
      return -1L;
   }

   public String ftype() {
      if (this.isFile()) {
         return "file";
      } else {
         return this.isDirectory() ? "directory" : "unknown";
      }
   }

   public int gid() {
      this.handler.unimplementedError("stat.st_gid");
      return -1;
   }

   public boolean groupMember(int gid) {
      return this.posix.getgid() == gid || this.posix.getegid() == gid;
   }

   public long ino() {
      return 0L;
   }

   public boolean isBlockDev() {
      this.handler.unimplementedError("block device detection");
      return false;
   }

   public boolean isCharDev() {
      return false;
   }

   public boolean isDirectory() {
      return (this.mode() & 16384) != 0;
   }

   public boolean isEmpty() {
      return this.st_size() == 0L;
   }

   public boolean isExecutable() {
      this.handler.warn(POSIXHandler.WARNING_ID.DUMMY_VALUE_USED, "executable? does not in this environment and will return a dummy value", "executable");
      return true;
   }

   public boolean isExecutableReal() {
      this.handler.warn(POSIXHandler.WARNING_ID.DUMMY_VALUE_USED, "executable_real? does not work in this environmnt and will return a dummy value", "executable_real");
      return true;
   }

   public boolean isFifo() {
      this.handler.unimplementedError("fifo file detection");
      return false;
   }

   public boolean isFile() {
      return (this.mode() & '耀') != 0;
   }

   public boolean isGroupOwned() {
      return this.groupMember(this.gid());
   }

   public boolean isIdentical(FileStat other) {
      this.handler.unimplementedError("identical file detection");
      return false;
   }

   public boolean isNamedPipe() {
      this.handler.unimplementedError("piped file detection");
      return false;
   }

   public boolean isOwned() {
      return this.posix.geteuid() == this.uid();
   }

   public boolean isROwned() {
      return this.posix.getuid() == this.uid();
   }

   public boolean isReadable() {
      int mode = this.mode();
      if ((mode & 256) != 0) {
         return true;
      } else if ((mode & 32) != 0) {
         return true;
      } else {
         return (mode & 4) != 0;
      }
   }

   public boolean isReadableReal() {
      return this.isReadable();
   }

   public boolean isSymlink() {
      return (this.mode() & 'ꀀ') == 40960;
   }

   public boolean isWritable() {
      int mode = this.mode();
      if ((mode & 128) != 0) {
         return true;
      } else if ((mode & 16) != 0) {
         return true;
      } else {
         return (mode & 2) != 0;
      }
   }

   public boolean isWritableReal() {
      return this.isWritable();
   }

   public boolean isSetgid() {
      this.handler.unimplementedError("setgid detection");
      return false;
   }

   public boolean isSetuid() {
      this.handler.unimplementedError("setuid detection");
      return false;
   }

   public boolean isSocket() {
      this.handler.unimplementedError("socket file type detection");
      return false;
   }

   public boolean isSticky() {
      this.handler.unimplementedError("sticky bit detection");
      return false;
   }

   public int major(long dev) {
      this.handler.unimplementedError("major device");
      return -1;
   }

   public int minor(long dev) {
      this.handler.unimplementedError("minor device");
      return -1;
   }

   public int mode() {
      return this.st_mode & '\uffff';
   }

   public long mtime() {
      return (long)this.st_mtime;
   }

   public int nlink() {
      this.handler.unimplementedError("stat.nlink");
      return -1;
   }

   public long rdev() {
      this.handler.unimplementedError("stat.rdev");
      return -1L;
   }

   public long st_size() {
      return this.st_size;
   }

   public int uid() {
      return -1;
   }
}
