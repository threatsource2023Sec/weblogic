package jnr.posix;

import jnr.constants.platform.Sysconf;
import jnr.ffi.Memory;
import jnr.ffi.Pointer;
import jnr.ffi.mapper.FromNativeContext;
import jnr.posix.util.MethodName;

final class FreeBSDPOSIX extends BaseNativePOSIX {
   public static final BaseNativePOSIX.PointerConverter PASSWD = new BaseNativePOSIX.PointerConverter() {
      public Object fromNative(Object arg, FromNativeContext ctx) {
         return arg != null ? new FreeBSDPasswd((Pointer)arg) : null;
      }
   };

   FreeBSDPOSIX(LibCProvider libc, POSIXHandler handler) {
      super(libc, handler);
   }

   public FileStat allocateStat() {
      return new FreeBSDFileStat(this);
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
      return Memory.allocateDirect(this.getRuntime(), 8);
   }

   public Pointer allocatePosixSpawnattr() {
      return Memory.allocateDirect(this.getRuntime(), 8);
   }
}
