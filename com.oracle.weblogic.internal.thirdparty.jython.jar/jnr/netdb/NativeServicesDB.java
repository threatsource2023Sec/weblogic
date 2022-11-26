package jnr.netdb;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jnr.ffi.CallingConvention;
import jnr.ffi.Library;
import jnr.ffi.LibraryOption;
import jnr.ffi.Memory;
import jnr.ffi.NativeLong;
import jnr.ffi.Platform;
import jnr.ffi.Pointer;
import jnr.ffi.Runtime;
import jnr.ffi.Struct;
import jnr.ffi.annotations.Direct;
import jnr.ffi.annotations.Out;

abstract class NativeServicesDB implements ServicesDB {
   protected final LibServices lib;

   public NativeServicesDB(LibServices lib) {
      this.lib = lib;
   }

   public static final NativeServicesDB getInstance() {
      return NativeServicesDB.SingletonHolder.INSTANCE;
   }

   static final NativeServicesDB load() {
      try {
         Platform.OS os = Platform.getNativePlatform().getOS();
         if (!os.equals(Platform.OS.DARWIN) && (!os.equals(Platform.OS.WINDOWS) || Platform.getNativePlatform().getCPU() != Platform.CPU.I386) && !os.equals(Platform.OS.LINUX) && !os.equals(Platform.OS.SOLARIS) && !os.equals(Platform.OS.FREEBSD) && !os.equals(Platform.OS.NETBSD)) {
            return null;
         } else {
            LibServices lib;
            if (os.equals(Platform.OS.WINDOWS)) {
               Map options = new HashMap();
               options.put(LibraryOption.CallingConvention, CallingConvention.STDCALL);
               lib = (LibServices)Library.loadLibrary((Class)LibServices.class, (Map)options, (String[])("Ws2_32"));
            } else {
               String[] libnames = os.equals(Platform.OS.SOLARIS) ? new String[]{"socket", "nsl", "c"} : new String[]{"c"};
               if (os.equals(Platform.OS.LINUX)) {
                  lib = (LibServices)Library.loadLibrary(LinuxLibServices.class, libnames);
               } else {
                  lib = (LibServices)Library.loadLibrary(LibServices.class, libnames);
               }
            }

            NativeServicesDB services = os.equals(Platform.OS.LINUX) ? new LinuxServicesDB(lib) : new DefaultNativeServicesDB(lib);
            if (((NativeServicesDB)services).getServiceByName("comsat", "udp") == null) {
               return null;
            } else {
               ((NativeServicesDB)services).getServiceByName("bootps", "udp");
               ((NativeServicesDB)services).getServiceByPort(67, "udp");
               return (NativeServicesDB)services;
            }
         }
      } catch (Throwable var3) {
         Logger.getLogger(NativeServicesDB.class.getName()).log(Level.WARNING, "Failed to load native services db", var3);
         return null;
      }
   }

   static int ntohs(int value) {
      int hostValue = ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN) ? Short.reverseBytes((short)value) : value;
      if (hostValue < 0) {
         hostValue = (hostValue & 32767) + '耀';
      }

      return hostValue;
   }

   static int htons(int value) {
      return ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN) ? Short.reverseBytes((short)value) : value;
   }

   static Service serviceFromNative(UnixServent s) {
      if (s == null) {
         return null;
      } else {
         List emptyAliases = Collections.emptyList();
         Pointer ptr;
         Collection aliases = (ptr = s.aliases.get()) != null ? StringUtil.getNullTerminatedStringArray(ptr) : emptyAliases;
         return new Service(s.name.get(), ntohs(s.port.get()), s.proto.get(), aliases);
      }
   }

   static final class LinuxServicesDB extends NativeServicesDB {
      private static final int BUFLEN = 4096;
      private final LinuxLibServices lib;
      private final Runtime runtime;
      private final Pointer buf;

      LinuxServicesDB(LibServices lib) {
         super(lib);
         this.lib = (LinuxLibServices)lib;
         this.runtime = Library.getRuntime(lib);
         this.buf = Memory.allocateDirect(this.runtime, 4096);
      }

      public synchronized Service getServiceByName(String name, String proto) {
         UnixServent servent = new UnixServent(this.runtime);
         Pointer result = Memory.allocateDirect(this.runtime, this.runtime.addressSize());
         if (this.lib.getservbyname_r(name, proto, servent, this.buf, new NativeLong(4096), result) == 0) {
            return result.getPointer(0L) != null ? serviceFromNative(servent) : null;
         } else {
            throw new RuntimeException("getservbyname_r failed");
         }
      }

      public synchronized Service getServiceByPort(Integer port, String proto) {
         UnixServent servent = new UnixServent(this.runtime);
         Pointer result = Memory.allocateDirect(this.runtime, this.runtime.addressSize());
         if (this.lib.getservbyport_r(htons(port), proto, servent, this.buf, new NativeLong(4096), result) == 0) {
            return result.getPointer(0L) != null ? serviceFromNative(servent) : null;
         } else {
            throw new RuntimeException("getservbyport_r failed");
         }
      }

      public synchronized Collection getAllServices() {
         UnixServent s = new UnixServent(this.runtime);
         List allServices = new ArrayList();
         Pointer result = Memory.allocateDirect(this.runtime, this.runtime.addressSize());
         NativeLong buflen = new NativeLong(4096);

         try {
            while(this.lib.getservent_r(s, this.buf, buflen, result) == 0 && result.getPointer(0L) != null) {
               allServices.add(serviceFromNative(s));
            }
         } finally {
            this.lib.endservent();
         }

         return allServices;
      }
   }

   static final class DefaultNativeServicesDB extends NativeServicesDB {
      DefaultNativeServicesDB(LibServices lib) {
         super(lib);
      }

      public Collection getAllServices() {
         List allServices = new ArrayList();

         UnixServent s;
         try {
            while((s = this.lib.getservent()) != null) {
               allServices.add(serviceFromNative(s));
            }
         } finally {
            this.lib.endservent();
         }

         return allServices;
      }

      public Service getServiceByName(String name, String proto) {
         return serviceFromNative(this.lib.getservbyname(name, proto));
      }

      public Service getServiceByPort(Integer port, String proto) {
         return serviceFromNative(this.lib.getservbyport(htons(port), proto));
      }
   }

   public interface LinuxLibServices extends LibServices {
      int getservbyname_r(String var1, String var2, @Direct UnixServent var3, Pointer var4, NativeLong var5, @Out Pointer var6);

      int getservbyport_r(Integer var1, String var2, @Direct UnixServent var3, Pointer var4, NativeLong var5, @Out Pointer var6);

      int getservent_r(@Direct UnixServent var1, Pointer var2, NativeLong var3, Pointer var4);
   }

   public interface LibServices {
      UnixServent getservbyname(String var1, String var2);

      UnixServent getservbyport(Integer var1, String var2);

      UnixServent getservent();

      void endservent();
   }

   public static class LinuxServent extends UnixServent {
      public static final int BUFLEN = 4096;
      public final Pointer buf;

      public LinuxServent(Runtime runtime) {
         super(runtime);
         this.buf = Memory.allocateDirect(runtime, 4096, true);
      }
   }

   public static class UnixServent extends Struct {
      public final Struct.String name = new Struct.UTF8StringRef();
      public final Struct.Pointer aliases = new Struct.Pointer();
      public final Struct.Signed32 port = new Struct.Signed32();
      public final Struct.String proto = new Struct.UTF8StringRef();

      public UnixServent(Runtime runtime) {
         super(runtime);
      }
   }

   private static final class SingletonHolder {
      public static final NativeServicesDB INSTANCE = NativeServicesDB.load();
   }
}
