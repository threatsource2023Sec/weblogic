package jnr.posix;

import jnr.constants.platform.Sysconf;
import jnr.ffi.LibraryLoader;
import jnr.ffi.Memory;
import jnr.ffi.Pointer;
import jnr.ffi.mapper.FromNativeContext;

final class MacOSPOSIX extends BaseNativePOSIX {
   private final NSGetEnviron environ;
   public static final BaseNativePOSIX.PointerConverter PASSWD = new BaseNativePOSIX.PointerConverter() {
      public Object fromNative(Object arg, FromNativeContext ctx) {
         return arg != null ? new MacOSPasswd((Pointer)arg) : null;
      }
   };

   MacOSPOSIX(LibCProvider libcProvider, POSIXHandler handler) {
      super(libcProvider, handler);
      LibraryLoader loader = LibraryLoader.create(NSGetEnviron.class);
      loader.library("libSystem.B.dylib");
      this.environ = (NSGetEnviron)loader.load();
   }

   public FileStat allocateStat() {
      return new MacOSFileStat(this);
   }

   public MsgHdr allocateMsgHdr() {
      return new MacOSMsgHdr(this);
   }

   public Pointer allocatePosixSpawnFileActions() {
      return Memory.allocateDirect(this.getRuntime(), 8);
   }

   public Pointer allocatePosixSpawnattr() {
      return Memory.allocateDirect(this.getRuntime(), 8);
   }

   public SocketMacros socketMacros() {
      return MacOSSocketMacros.INSTANCE;
   }

   public long sysconf(Sysconf name) {
      return this.libc().sysconf(name);
   }

   public Times times() {
      return NativeTimes.times(this);
   }

   public Pointer environ() {
      return this.environ._NSGetEnviron().getPointer(0L);
   }
}
