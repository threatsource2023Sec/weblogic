package jnr.posix;

import jnr.ffi.Pointer;

public class MacOSSocketMacros implements SocketMacros {
   public static final SocketMacros INSTANCE = new MacOSSocketMacros();

   public int __DARWIN_ALIGN32(int x) {
      return x + 3 & -4;
   }

   public int CMSG_SPACE(int l) {
      return this.__DARWIN_ALIGN32(MacOSCmsgHdr.layout.size()) + this.__DARWIN_ALIGN32(l);
   }

   public int CMSG_LEN(int l) {
      return this.__DARWIN_ALIGN32(MacOSCmsgHdr.layout.size()) + l;
   }

   public Pointer CMSG_DATA(Pointer cmsg) {
      return cmsg.slice((long)this.__DARWIN_ALIGN32(MacOSCmsgHdr.layout.size()));
   }
}
