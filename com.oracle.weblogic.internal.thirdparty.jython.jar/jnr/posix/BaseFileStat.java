package jnr.posix;

import jnr.ffi.Memory;
import jnr.ffi.Pointer;
import jnr.ffi.StructLayout;

public abstract class BaseFileStat implements FileStat {
   protected final POSIX posix;
   protected final Pointer memory;

   protected BaseFileStat(NativePOSIX posix, StructLayout layout) {
      this.posix = posix;
      this.memory = Memory.allocate(posix.getRuntime(), layout.size());
   }

   public String ftype() {
      if (this.isFile()) {
         return "file";
      } else if (this.isDirectory()) {
         return "directory";
      } else if (this.isCharDev()) {
         return "characterSpecial";
      } else if (this.isBlockDev()) {
         return "blockSpecial";
      } else if (this.isFifo()) {
         return "fifo";
      } else if (this.isSymlink()) {
         return "link";
      } else {
         return this.isSocket() ? "socket" : "unknown";
      }
   }

   public boolean groupMember(int gid) {
      return this.posix.getgid() == gid || this.posix.getegid() == gid;
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
      if (this.posix.geteuid() == 0) {
         return (this.mode() & 73) != 0;
      } else if (this.isOwned()) {
         return (this.mode() & 64) != 0;
      } else if (this.isGroupOwned()) {
         return (this.mode() & 8) != 0;
      } else {
         return (this.mode() & 1) != 0;
      }
   }

   public boolean isExecutableReal() {
      if (this.posix.getuid() == 0) {
         return (this.mode() & 73) != 0;
      } else if (this.isROwned()) {
         return (this.mode() & 64) != 0;
      } else if (this.groupMember(this.gid())) {
         return (this.mode() & 8) != 0;
      } else {
         return (this.mode() & 1) != 0;
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
      return this.posix.geteuid() == this.uid();
   }

   public boolean isROwned() {
      return this.posix.getuid() == this.uid();
   }

   public boolean isReadable() {
      if (this.posix.geteuid() == 0) {
         return true;
      } else if (this.isOwned()) {
         return (this.mode() & 256) != 0;
      } else if (this.isGroupOwned()) {
         return (this.mode() & 32) != 0;
      } else {
         return (this.mode() & 4) != 0;
      }
   }

   public boolean isReadableReal() {
      if (this.posix.getuid() == 0) {
         return true;
      } else if (this.isROwned()) {
         return (this.mode() & 256) != 0;
      } else if (this.groupMember(this.gid())) {
         return (this.mode() & 32) != 0;
      } else {
         return (this.mode() & 4) != 0;
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
      if (this.posix.geteuid() == 0) {
         return true;
      } else if (this.isOwned()) {
         return (this.mode() & 128) != 0;
      } else if (this.isGroupOwned()) {
         return (this.mode() & 16) != 0;
      } else {
         return (this.mode() & 2) != 0;
      }
   }

   public boolean isWritableReal() {
      if (this.posix.getuid() == 0) {
         return true;
      } else if (this.isROwned()) {
         return (this.mode() & 128) != 0;
      } else if (this.groupMember(this.gid())) {
         return (this.mode() & 16) != 0;
      } else {
         return (this.mode() & 2) != 0;
      }
   }

   public int major(long dev) {
      return (int)(dev >> 24) & 255;
   }

   public int minor(long dev) {
      return (int)(dev & 16777215L);
   }
}
