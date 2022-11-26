package jnr.posix;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import jnr.ffi.Library;
import jnr.ffi.LibraryOption;
import jnr.ffi.Platform;
import jnr.ffi.Runtime;
import jnr.ffi.Struct;
import jnr.ffi.mapper.FunctionMapper;
import jnr.posix.util.DefaultPOSIXHandler;

public class POSIXFactory {
   private static final Class BOGUS_HACK = Struct.class;

   public static POSIX getPOSIX(POSIXHandler handler, boolean useNativePOSIX) {
      return new LazyPOSIX(handler, useNativePOSIX);
   }

   public static POSIX getPOSIX() {
      return getPOSIX(new DefaultPOSIXHandler(), true);
   }

   public static POSIX getJavaPOSIX(POSIXHandler handler) {
      return new JavaPOSIX(handler);
   }

   public static POSIX getJavaPOSIX() {
      return getJavaPOSIX(new DefaultPOSIXHandler());
   }

   public static POSIX getNativePOSIX(POSIXHandler handler) {
      return loadNativePOSIX(handler);
   }

   public static POSIX getNativePOSIX() {
      return getNativePOSIX(new DefaultPOSIXHandler());
   }

   static POSIX loadPOSIX(POSIXHandler handler, boolean useNativePOSIX) {
      POSIX posix = null;
      if (useNativePOSIX) {
         try {
            POSIX posix = loadNativePOSIX(handler);
            posix = posix != null ? new CheckedPOSIX(posix, handler) : null;
            if (handler.isVerbose()) {
               if (posix != null) {
                  System.err.println("Successfully loaded native POSIX impl.");
               } else {
                  System.err.println("Failed to load native POSIX impl; falling back on Java impl. Unsupported OS.");
               }
            }
         } catch (Throwable var4) {
            if (handler.isVerbose()) {
               System.err.println("Failed to load native POSIX impl; falling back on Java impl. Stacktrace follows.");
               var4.printStackTrace();
            }
         }
      }

      if (posix == null) {
         posix = getJavaPOSIX(handler);
      }

      return (POSIX)posix;
   }

   private static POSIX loadNativePOSIX(POSIXHandler handler) {
      switch (Platform.getNativePlatform().getOS()) {
         case DARWIN:
            return loadMacOSPOSIX(handler);
         case LINUX:
            return loadLinuxPOSIX(handler);
         case FREEBSD:
            return loadFreeBSDPOSIX(handler);
         case OPENBSD:
            return loadOpenBSDPOSIX(handler);
         case SOLARIS:
            return loadSolarisPOSIX(handler);
         case AIX:
            return loadAixPOSIX(handler);
         case WINDOWS:
            return loadWindowsPOSIX(handler);
         default:
            return null;
      }
   }

   public static POSIX loadLinuxPOSIX(POSIXHandler handler) {
      return new LinuxPOSIX(POSIXFactory.DefaultLibCProvider.INSTANCE, handler);
   }

   public static POSIX loadMacOSPOSIX(POSIXHandler handler) {
      return new MacOSPOSIX(POSIXFactory.DefaultLibCProvider.INSTANCE, handler);
   }

   public static POSIX loadSolarisPOSIX(POSIXHandler handler) {
      return new SolarisPOSIX(POSIXFactory.DefaultLibCProvider.INSTANCE, handler);
   }

   public static POSIX loadFreeBSDPOSIX(POSIXHandler handler) {
      return new FreeBSDPOSIX(POSIXFactory.DefaultLibCProvider.INSTANCE, handler);
   }

   public static POSIX loadOpenBSDPOSIX(POSIXHandler handler) {
      return new OpenBSDPOSIX(POSIXFactory.DefaultLibCProvider.INSTANCE, handler);
   }

   public static POSIX loadWindowsPOSIX(POSIXHandler handler) {
      return new WindowsPOSIX(POSIXFactory.DefaultLibCProvider.INSTANCE, handler);
   }

   public static POSIX loadAixPOSIX(POSIXHandler handler) {
      return new AixPOSIX(POSIXFactory.DefaultLibCProvider.INSTANCE, handler);
   }

   private static String[] libraries() {
      switch (Platform.getNativePlatform().getOS()) {
         case LINUX:
            return new String[]{Platform.getNativePlatform().getStandardCLibraryName(), "libcrypt.so.1"};
         case FREEBSD:
         case NETBSD:
            return new String[]{Platform.getNativePlatform().getStandardCLibraryName(), "crypt"};
         case OPENBSD:
         default:
            return new String[]{Platform.getNativePlatform().getStandardCLibraryName()};
         case SOLARIS:
            return new String[]{"socket", "nsl", Platform.getNativePlatform().getStandardCLibraryName()};
         case AIX:
            return Runtime.getSystemRuntime().addressSize() == 4 ? new String[]{"libc.a(shr.o)"} : new String[]{"libc.a(shr_64.o)"};
         case WINDOWS:
            return new String[]{"msvcrt", "kernel32"};
      }
   }

   private static Class libraryInterface() {
      switch (Platform.getNativePlatform().getOS()) {
         case LINUX:
            return LinuxLibC.class;
         case FREEBSD:
         case OPENBSD:
         default:
            return UnixLibC.class;
         case SOLARIS:
            return SolarisLibC.class;
         case AIX:
            return AixLibC.class;
         case WINDOWS:
            return WindowsLibC.class;
      }
   }

   private static FunctionMapper functionMapper() {
      switch (Platform.getNativePlatform().getOS()) {
         case SOLARIS:
            return jnr.posix.util.Platform.IS_32_BIT ? (new SimpleFunctionMapper.Builder()).map("stat", "stat64").map("fstat", "fstat64").map("lstat", "lstat64").build() : null;
         case AIX:
            return (new SimpleFunctionMapper.Builder()).map("stat", "stat64x").map("fstat", "fstat64x").map("lstat", "lstat64x").map("stat64", "stat64x").map("fstat64", "fstat64x").map("lstat64", "lstat64x").build();
         case WINDOWS:
            return (new SimpleFunctionMapper.Builder()).map("getpid", "_getpid").map("chmod", "_chmod").map("fstat", "_fstat64").map("stat", "_stat64").map("umask", "_umask").map("isatty", "_isatty").map("read", "_read").map("write", "_write").map("close", "_close").map("getcwd", "_getcwd").map("unlink", "_unlink").map("access", "_access").map("open", "_open").map("dup", "_dup").map("dup2", "_dup2").map("lseek", "_lseek").map("ftruncate", "_chsize").build();
         default:
            return null;
      }
   }

   private static Map options() {
      Map options = new HashMap();
      FunctionMapper functionMapper = functionMapper();
      if (functionMapper != null) {
         options.put(LibraryOption.FunctionMapper, functionMapper);
      }

      options.put(LibraryOption.TypeMapper, POSIXTypeMapper.INSTANCE);
      options.put(LibraryOption.LoadNow, Boolean.TRUE);
      return Collections.unmodifiableMap(options);
   }

   private static final class DefaultLibCProvider implements LibCProvider {
      public static final LibCProvider INSTANCE = new DefaultLibCProvider();

      public final LibC getLibC() {
         return POSIXFactory.DefaultLibCProvider.SingletonHolder.libc;
      }

      private static final class SingletonHolder {
         public static LibC libc = (LibC)Library.loadLibrary(POSIXFactory.libraryInterface(), POSIXFactory.options(), POSIXFactory.libraries());
      }
   }
}
