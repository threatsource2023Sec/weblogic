package jnr.posix;

import java.io.FileDescriptor;
import jnr.constants.platform.Errno;
import jnr.constants.platform.Sysconf;
import jnr.ffi.Memory;
import jnr.ffi.Pointer;
import jnr.ffi.mapper.FromNativeContext;
import jnr.posix.util.Platform;

final class LinuxPOSIX extends BaseNativePOSIX implements Linux {
   private volatile boolean use_fxstat64 = true;
   private volatile boolean use_lxstat64 = true;
   private volatile boolean use_xstat64 = true;
   private final int statVersion;
   public static final BaseNativePOSIX.PointerConverter PASSWD = new BaseNativePOSIX.PointerConverter() {
      public Object fromNative(Object arg, FromNativeContext ctx) {
         return arg != null ? new LinuxPasswd((Pointer)arg) : null;
      }
   };

   LinuxPOSIX(LibCProvider libcProvider, POSIXHandler handler) {
      super(libcProvider, handler);
      if (Platform.IS_32_BIT) {
         this.statVersion = 3;
      } else {
         FileStat stat = this.allocateStat();
         if (((LinuxLibC)this.libc()).__xstat64(0, (CharSequence)"/dev/null", stat) < 0) {
            this.statVersion = 1;
         } else {
            this.statVersion = 0;
         }
      }

   }

   public FileStat allocateStat() {
      if (Platform.IS_32_BIT) {
         return new LinuxFileStat32(this);
      } else {
         return (FileStat)("aarch64".equals(Platform.ARCH) ? new LinuxFileStatAARCH64(this) : new LinuxFileStat64(this));
      }
   }

   public MsgHdr allocateMsgHdr() {
      return new LinuxMsgHdr(this);
   }

   public Pointer allocatePosixSpawnFileActions() {
      return Memory.allocateDirect(this.getRuntime(), 80);
   }

   public Pointer allocatePosixSpawnattr() {
      return Memory.allocateDirect(this.getRuntime(), 336);
   }

   public SocketMacros socketMacros() {
      return LinuxSocketMacros.INSTANCE;
   }

   private int old_fstat(int fd, FileStat stat) {
      try {
         return super.fstat(fd, stat);
      } catch (UnsatisfiedLinkError var4) {
         this.handler.unimplementedError("fstat");
         return -1;
      }
   }

   public int fstat(int fd, FileStat stat) {
      if (this.use_fxstat64) {
         try {
            int ret;
            if ((ret = ((LinuxLibC)this.libc()).__fxstat64(this.statVersion, fd, stat)) < 0) {
               this.handler.error(Errno.valueOf((long)this.errno()), "fstat", Integer.toString(fd));
            }

            return ret;
         } catch (UnsatisfiedLinkError var5) {
            this.use_fxstat64 = false;
            return this.old_fstat(fd, stat);
         }
      } else {
         return this.old_fstat(fd, stat);
      }
   }

   public FileStat fstat(int fd) {
      FileStat stat = this.allocateStat();
      int ret = this.fstat(fd, stat);
      if (ret < 0) {
         this.handler.error(Errno.valueOf((long)this.errno()), "fstat", Integer.toString(fd));
      }

      return stat;
   }

   public int fstat(FileDescriptor fileDescriptor, FileStat stat) {
      return this.fstat(this.helper.getfd(fileDescriptor), stat);
   }

   public FileStat fstat(FileDescriptor fileDescriptor) {
      FileStat stat = this.allocateStat();
      int fd = this.helper.getfd(fileDescriptor);
      int ret = this.fstat(fd, stat);
      if (ret < 0) {
         this.handler.error(Errno.valueOf((long)this.errno()), "fstat", Integer.toString(fd));
      }

      return stat;
   }

   private final int old_lstat(String path, FileStat stat) {
      try {
         return super.lstat(path, stat);
      } catch (UnsatisfiedLinkError var4) {
         this.handler.unimplementedError("lstat");
         return -1;
      }
   }

   public int lstat(String path, FileStat stat) {
      if (this.use_lxstat64) {
         try {
            return ((LinuxLibC)this.libc()).__lxstat64(this.statVersion, (CharSequence)path, stat);
         } catch (UnsatisfiedLinkError var4) {
            this.use_lxstat64 = false;
            return this.old_lstat(path, stat);
         }
      } else {
         return this.old_lstat(path, stat);
      }
   }

   public FileStat lstat(String path) {
      FileStat stat = this.allocateStat();
      int ret = this.lstat(path, stat);
      if (ret < 0) {
         this.handler.error(Errno.valueOf((long)this.errno()), "lstat", path);
      }

      return stat;
   }

   private final int old_stat(String path, FileStat stat) {
      try {
         return super.stat(path, stat);
      } catch (UnsatisfiedLinkError var4) {
         this.handler.unimplementedError("stat");
         return -1;
      }
   }

   public int stat(String path, FileStat stat) {
      if (this.use_xstat64) {
         try {
            return ((LinuxLibC)this.libc()).__xstat64(this.statVersion, (CharSequence)path, stat);
         } catch (UnsatisfiedLinkError var4) {
            this.use_xstat64 = false;
            return this.old_stat(path, stat);
         }
      } else {
         return this.old_stat(path, stat);
      }
   }

   public FileStat stat(String path) {
      FileStat stat = this.allocateStat();
      int ret = this.stat(path, stat);
      if (ret < 0) {
         this.handler.error(Errno.valueOf((long)this.errno()), "stat", path);
      }

      return stat;
   }

   public long sysconf(Sysconf name) {
      return this.libc().sysconf(name);
   }

   public Times times() {
      return NativeTimes.times(this);
   }

   public int ioprio_get(int which, int who) {
      Syscall.ABI abi = LinuxPOSIX.Syscall.abi();
      if (abi == null) {
         this.handler.unimplementedError("ioprio_get");
         return -1;
      } else {
         return this.libc().syscall(abi.__NR_ioprio_get(), which, who);
      }
   }

   public int ioprio_set(int which, int who, int ioprio) {
      Syscall.ABI abi = LinuxPOSIX.Syscall.abi();
      if (abi == null) {
         this.handler.unimplementedError("ioprio_set");
         return -1;
      } else {
         return this.libc().syscall(abi.__NR_ioprio_set(), which, who, ioprio);
      }
   }

   public static final class Syscall {
      static final ABI _ABI_X86_32 = new ABI_X86_32();
      static final ABI _ABI_X86_64 = new ABI_X86_64();
      static final ABI _ABI_AARCH64 = new ABI_AARCH64();

      public static ABI abi() {
         if ("x86_64".equals(Platform.ARCH)) {
            if (Platform.IS_64_BIT) {
               return _ABI_X86_64;
            }
         } else {
            if ("i386".equals(Platform.ARCH)) {
               return _ABI_X86_32;
            }

            if ("aarch64".equals(Platform.ARCH)) {
               return _ABI_AARCH64;
            }
         }

         return null;
      }

      static final class ABI_AARCH64 implements ABI {
         public int __NR_ioprio_set() {
            return 30;
         }

         public int __NR_ioprio_get() {
            return 31;
         }
      }

      static final class ABI_X86_64 implements ABI {
         public int __NR_ioprio_set() {
            return 251;
         }

         public int __NR_ioprio_get() {
            return 252;
         }
      }

      static final class ABI_X86_32 implements ABI {
         public int __NR_ioprio_set() {
            return 289;
         }

         public int __NR_ioprio_get() {
            return 290;
         }
      }

      interface ABI {
         int __NR_ioprio_set();

         int __NR_ioprio_get();
      }
   }
}
