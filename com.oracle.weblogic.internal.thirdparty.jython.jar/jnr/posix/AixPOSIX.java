package jnr.posix;

import jnr.constants.platform.Sysconf;
import jnr.ffi.Memory;
import jnr.ffi.Pointer;
import jnr.ffi.mapper.FromNativeContext;
import jnr.posix.util.MethodName;

final class AixPOSIX extends BaseNativePOSIX {
   public static final BaseNativePOSIX.PointerConverter PASSWD = new BaseNativePOSIX.PointerConverter() {
      public Object fromNative(Object arg, FromNativeContext ctx) {
         return arg != null ? new AixPasswd((Pointer)arg) : null;
      }
   };

   AixPOSIX(LibCProvider libc, POSIXHandler handler) {
      super(libc, handler);
   }

   public FileStat allocateStat() {
      return new AixFileStat(this);
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

   public Pointer allocatePosixSpawnFileActions() {
      return Memory.allocateDirect(this.getRuntime(), 4);
   }

   public Pointer allocatePosixSpawnattr() {
      return Memory.allocateDirect(this.getRuntime(), 60);
   }
}
