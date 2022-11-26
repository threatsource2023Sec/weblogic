package jnr.ffi.provider.jffi.platform.x86_64.windows;

import java.util.EnumMap;
import java.util.Map;
import jnr.ffi.NativeType;
import jnr.ffi.TypeAlias;

public final class TypeAliases {
   public static final Map ALIASES = buildTypeMap();

   private static Map buildTypeMap() {
      Map m = new EnumMap(TypeAlias.class);
      m.put(TypeAlias.int8_t, NativeType.SCHAR);
      m.put(TypeAlias.u_int8_t, NativeType.UCHAR);
      m.put(TypeAlias.int16_t, NativeType.SSHORT);
      m.put(TypeAlias.u_int16_t, NativeType.USHORT);
      m.put(TypeAlias.int32_t, NativeType.SINT);
      m.put(TypeAlias.u_int32_t, NativeType.UINT);
      m.put(TypeAlias.int64_t, NativeType.SLONGLONG);
      m.put(TypeAlias.u_int64_t, NativeType.ULONGLONG);
      m.put(TypeAlias.intptr_t, NativeType.SLONGLONG);
      m.put(TypeAlias.uintptr_t, NativeType.ULONGLONG);
      m.put(TypeAlias.caddr_t, NativeType.ADDRESS);
      m.put(TypeAlias.dev_t, NativeType.UINT);
      m.put(TypeAlias.blkcnt_t, NativeType.SLONG);
      m.put(TypeAlias.blksize_t, NativeType.SLONG);
      m.put(TypeAlias.gid_t, NativeType.SINT);
      m.put(TypeAlias.in_addr_t, NativeType.UINT);
      m.put(TypeAlias.in_port_t, NativeType.USHORT);
      m.put(TypeAlias.ino_t, NativeType.USHORT);
      m.put(TypeAlias.ino64_t, NativeType.ULONGLONG);
      m.put(TypeAlias.key_t, NativeType.SINT);
      m.put(TypeAlias.mode_t, NativeType.USHORT);
      m.put(TypeAlias.nlink_t, NativeType.SINT);
      m.put(TypeAlias.id_t, NativeType.SINT);
      m.put(TypeAlias.pid_t, NativeType.SLONGLONG);
      m.put(TypeAlias.off_t, NativeType.SLONGLONG);
      m.put(TypeAlias.swblk_t, NativeType.SLONG);
      m.put(TypeAlias.uid_t, NativeType.SINT);
      m.put(TypeAlias.clock_t, NativeType.SINT);
      m.put(TypeAlias.size_t, NativeType.ULONGLONG);
      m.put(TypeAlias.ssize_t, NativeType.SLONGLONG);
      m.put(TypeAlias.time_t, NativeType.SLONGLONG);
      m.put(TypeAlias.fsblkcnt_t, NativeType.ULONG);
      m.put(TypeAlias.fsfilcnt_t, NativeType.ULONG);
      m.put(TypeAlias.sa_family_t, NativeType.USHORT);
      m.put(TypeAlias.socklen_t, NativeType.SINT);
      m.put(TypeAlias.rlim_t, NativeType.SINT);
      return m;
   }
}
