package jnr.posix;

import jnr.constants.platform.Errno;
import jnr.constants.platform.Fcntl;
import jnr.constants.platform.Sysconf;
import jnr.ffi.Memory;
import jnr.ffi.Pointer;
import jnr.ffi.Runtime;
import jnr.ffi.StructLayout;
import jnr.ffi.mapper.FromNativeContext;
import jnr.posix.util.MethodName;
import jnr.posix.util.Platform;

final class SolarisPOSIX extends BaseNativePOSIX {
   public static final int LOCK_SH = 1;
   public static final int LOCK_EX = 2;
   public static final int LOCK_NB = 4;
   public static final int LOCK_UN = 8;
   public static final int SEEK_SET = 0;
   private static final Layout FLOCK_LAYOUT = new Layout(Runtime.getSystemRuntime());
   public static final BaseNativePOSIX.PointerConverter PASSWD = new BaseNativePOSIX.PointerConverter() {
      public Object fromNative(Object arg, FromNativeContext ctx) {
         return arg != null ? new SolarisPasswd((Pointer)arg) : null;
      }
   };

   SolarisPOSIX(LibCProvider libc, POSIXHandler handler) {
      super(libc, handler);
   }

   public FileStat allocateStat() {
      return (FileStat)(Platform.IS_32_BIT ? new SolarisFileStat32(this) : new SolarisFileStat64(this));
   }

   public MsgHdr allocateMsgHdr() {
      this.handler.unimplementedError(MethodName.getCallerMethodName());
      return null;
   }

   public SocketMacros socketMacros() {
      this.handler.unimplementedError(MethodName.getCallerMethodName());
      return null;
   }

   public long sysconf(Sysconf name) {
      return this.libc().sysconf(name);
   }

   public Times times() {
      return NativeTimes.times(this);
   }

   public int flock(int fd, int operation) {
      Pointer lock = this.getRuntime().getMemoryManager().allocateTemporary(FLOCK_LAYOUT.size(), true);
      switch (operation & -5) {
         case 1:
            FLOCK_LAYOUT.l_type.set(lock, (long)((short)Fcntl.F_RDLCK.intValue()));
            break;
         case 2:
            FLOCK_LAYOUT.l_type.set(lock, (long)((short)Fcntl.F_WRLCK.intValue()));
            break;
         case 8:
            FLOCK_LAYOUT.l_type.set(lock, (long)((short)Fcntl.F_UNLCK.intValue()));
            break;
         default:
            this.errno(Errno.EINVAL.intValue());
            return -1;
      }

      FLOCK_LAYOUT.l_whence.set(lock, 0L);
      FLOCK_LAYOUT.l_start.set(lock, 0L);
      FLOCK_LAYOUT.l_len.set(lock, 0L);
      return this.libc().fcntl(fd, (operation & 4) != 0 ? Fcntl.F_SETLK.intValue() : Fcntl.F_SETLKW.intValue(), lock);
   }

   public Pointer allocatePosixSpawnFileActions() {
      return Memory.allocateDirect(this.getRuntime(), 8);
   }

   public Pointer allocatePosixSpawnattr() {
      return Memory.allocateDirect(this.getRuntime(), 8);
   }

   public static class Layout extends StructLayout {
      public final StructLayout.int16_t l_type = new StructLayout.int16_t();
      public final StructLayout.int16_t l_whence = new StructLayout.int16_t();
      public final StructLayout.off_t l_start = new StructLayout.off_t();
      public final StructLayout.off_t l_len = new StructLayout.off_t();
      public final StructLayout.int32_t l_sysid = new StructLayout.int32_t();
      public final StructLayout.pid_t l_pid = new StructLayout.pid_t();
      public final StructLayout.int32_t[] l_pad = new StructLayout.int32_t[4];

      protected Layout(Runtime runtime) {
         super(runtime);
      }
   }
}
