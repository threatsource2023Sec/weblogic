package jnr.posix;

import jnr.ffi.Pointer;

public interface SocketMacros {
   int CMSG_SPACE(int var1);

   int CMSG_LEN(int var1);

   Pointer CMSG_DATA(Pointer var1);
}
