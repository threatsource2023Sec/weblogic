package jnr.posix;

import jnr.ffi.Pointer;
import jnr.ffi.Runtime;
import jnr.ffi.TypeAlias;

public class LinuxSocketMacros implements SocketMacros {
   public static final LinuxSocketMacros INSTANCE = new LinuxSocketMacros();

   public int CMSG_ALIGN(int len) {
      int sizeof_size_t = Runtime.getSystemRuntime().findType(TypeAlias.size_t).size();
      return len + sizeof_size_t - 1 & ~(sizeof_size_t - 1);
   }

   public int CMSG_SPACE(int l) {
      return this.CMSG_ALIGN(l) + this.CMSG_ALIGN(LinuxCmsgHdr.layout.size());
   }

   public int CMSG_LEN(int l) {
      return this.CMSG_ALIGN(LinuxCmsgHdr.layout.size() + l);
   }

   public Pointer CMSG_DATA(Pointer cmsg) {
      return cmsg.slice((long)this.CMSG_ALIGN(LinuxCmsgHdr.layout.size()));
   }
}
