package jnr.netdb;

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

abstract class NativeProtocolsDB implements ProtocolsDB {
   public static final NativeProtocolsDB getInstance() {
      return NativeProtocolsDB.SingletonHolder.INSTANCE;
   }

   private static final NativeProtocolsDB load() {
      try {
         Platform.OS os = Platform.getNativePlatform().getOS();
         if (!os.equals(Platform.OS.DARWIN) && (!os.equals(Platform.OS.WINDOWS) || Platform.getNativePlatform().getCPU() != Platform.CPU.I386) && !os.equals(Platform.OS.LINUX) && !os.equals(Platform.OS.SOLARIS) && !os.equals(Platform.OS.FREEBSD) && !os.equals(Platform.OS.NETBSD)) {
            return null;
         } else {
            LibProto lib;
            if (os.equals(Platform.OS.WINDOWS)) {
               Map options = new HashMap();
               options.put(LibraryOption.CallingConvention, CallingConvention.STDCALL);
               lib = (LibProto)Library.loadLibrary((Class)LibProto.class, (Map)options, (String[])("Ws2_32"));
            } else {
               String[] libnames = os.equals(Platform.OS.SOLARIS) ? new String[]{"socket", "nsl", "c"} : new String[]{"c"};
               lib = os.equals(Platform.OS.LINUX) ? (LibProto)Library.loadLibrary(LinuxLibProto.class, libnames) : (LibProto)Library.loadLibrary(LibProto.class, libnames);
            }

            NativeProtocolsDB protocolsDB = os.equals(Platform.OS.LINUX) ? new LinuxNativeProtocolsDB((LinuxLibProto)lib) : new DefaultNativeProtocolsDB(lib);
            ((NativeProtocolsDB)protocolsDB).getProtocolByName("ip");
            ((NativeProtocolsDB)protocolsDB).getProtocolByNumber(0);
            return (NativeProtocolsDB)protocolsDB;
         }
      } catch (Throwable var3) {
         Logger.getLogger(NativeProtocolsDB.class.getName()).log(Level.WARNING, "Failed to load native protocols db", var3);
         return null;
      }
   }

   static Protocol protocolFromNative(UnixProtoent p) {
      if (p == null) {
         return null;
      } else {
         List emptyAliases = Collections.emptyList();
         Pointer ptr;
         Collection aliases = (ptr = p.aliases.get()) != null ? StringUtil.getNullTerminatedStringArray(ptr) : emptyAliases;
         return new Protocol(p.name.get(), (short)p.proto.get(), aliases);
      }
   }

   static final class LinuxNativeProtocolsDB extends NativeProtocolsDB {
      private static final int BUFLEN = 4096;
      private final Runtime runtime;
      private final Pointer buf;
      private final LinuxLibProto lib;

      LinuxNativeProtocolsDB(LinuxLibProto lib) {
         this.lib = lib;
         this.runtime = Library.getRuntime(lib);
         this.buf = Memory.allocateDirect(this.runtime, 4096);
      }

      public synchronized Protocol getProtocolByName(String name) {
         UnixProtoent protoent = new UnixProtoent(this.runtime);
         Pointer result = Memory.allocateDirect(this.runtime, this.runtime.addressSize());
         if (this.lib.getprotobyname_r(name, protoent, this.buf, new NativeLong(4096), result) == 0) {
            return result.getPointer(0L) != null ? protocolFromNative(protoent) : null;
         } else {
            throw new RuntimeException("getprotobyname_r failed");
         }
      }

      public synchronized Protocol getProtocolByNumber(Integer number) {
         UnixProtoent protoent = new UnixProtoent(this.runtime);
         Pointer result = Memory.allocateDirect(this.runtime, this.runtime.addressSize());
         if (this.lib.getprotobynumber_r(number, protoent, this.buf, new NativeLong(4096), result) == 0) {
            return result.getPointer(0L) != null ? protocolFromNative(protoent) : null;
         } else {
            throw new RuntimeException("getprotobynumber_r failed");
         }
      }

      public synchronized Collection getAllProtocols() {
         UnixProtoent p = new UnixProtoent(this.runtime);
         List allProtocols = new ArrayList();
         Pointer result = Memory.allocateDirect(this.runtime, this.runtime.addressSize());
         NativeLong buflen = new NativeLong(4096);
         this.lib.setprotoent(0);

         try {
            while(this.lib.getprotoent_r(p, this.buf, buflen, result) == 0 && result.getPointer(0L) != null) {
               allProtocols.add(protocolFromNative(p));
            }
         } finally {
            this.lib.endprotoent();
         }

         return allProtocols;
      }
   }

   static final class DefaultNativeProtocolsDB extends NativeProtocolsDB {
      private final LibProto lib;

      DefaultNativeProtocolsDB(LibProto lib) {
         this.lib = lib;
      }

      public synchronized Protocol getProtocolByName(String name) {
         return protocolFromNative(this.lib.getprotobyname(name));
      }

      public synchronized Protocol getProtocolByNumber(Integer proto) {
         return protocolFromNative(this.lib.getprotobynumber(proto));
      }

      public synchronized Collection getAllProtocols() {
         List allProtocols = new ArrayList();
         this.lib.setprotoent(0);

         UnixProtoent p;
         try {
            while((p = this.lib.getprotoent()) != null) {
               allProtocols.add(protocolFromNative(p));
            }
         } finally {
            this.lib.endprotoent();
         }

         return allProtocols;
      }
   }

   public interface LinuxLibProto extends LibProto {
      int getprotobyname_r(String var1, @Direct UnixProtoent var2, Pointer var3, NativeLong var4, Pointer var5);

      int getprotobynumber_r(int var1, @Direct UnixProtoent var2, Pointer var3, NativeLong var4, Pointer var5);

      int getprotoent_r(@Direct UnixProtoent var1, Pointer var2, NativeLong var3, Pointer var4);
   }

   public interface LibProto {
      UnixProtoent getprotobyname(String var1);

      UnixProtoent getprotobynumber(int var1);

      UnixProtoent getprotoent();

      void setprotoent(int var1);

      void endprotoent();
   }

   public static class UnixProtoent extends Struct {
      public final Struct.String name = new Struct.UTF8StringRef();
      public final Struct.Pointer aliases = new Struct.Pointer();
      public final Struct.Signed32 proto = new Struct.Signed32();

      public UnixProtoent(Runtime runtime) {
         super(runtime);
      }
   }

   private static final class SingletonHolder {
      public static final NativeProtocolsDB INSTANCE = NativeProtocolsDB.load();
   }
}
