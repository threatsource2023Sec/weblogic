package jnr.posix;

import jnr.constants.platform.Sysconf;
import jnr.ffi.Memory;
import jnr.ffi.Pointer;
import jnr.ffi.Struct;
import jnr.ffi.mapper.FromNativeContext;
import jnr.posix.util.MethodName;

final class OpenBSDPOSIX extends BaseNativePOSIX {
   public static final BaseNativePOSIX.PointerConverter PASSWD = new BaseNativePOSIX.PointerConverter() {
      public Object fromNative(Object arg, FromNativeContext ctx) {
         return arg != null ? new OpenBSDPasswd((Pointer)arg) : null;
      }
   };

   OpenBSDPOSIX(LibCProvider libc, POSIXHandler handler) {
      super(libc, handler);
   }

   public FileStat allocateStat() {
      return new OpenBSDFileStat(this);
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

   public int utimes(String path, long[] atimeval, long[] mtimeval) {
      Timeval[] times = null;
      if (atimeval != null && mtimeval != null) {
         times = (Timeval[])Struct.arrayOf(this.getRuntime(), OpenBSDTimeval.class, 2);
         times[0].setTime(atimeval);
         times[1].setTime(mtimeval);
      }

      return this.libc().utimes((CharSequence)path, (Timeval[])times);
   }

   public Pointer allocatePosixSpawnFileActions() {
      return Memory.allocateDirect(this.getRuntime(), 8);
   }

   public Pointer allocatePosixSpawnattr() {
      return Memory.allocateDirect(this.getRuntime(), 8);
   }
}
