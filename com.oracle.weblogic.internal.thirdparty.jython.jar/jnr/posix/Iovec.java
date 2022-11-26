package jnr.posix;

import java.nio.ByteBuffer;

public interface Iovec {
   ByteBuffer get();

   void set(ByteBuffer var1);
}
