package jnr.posix;

import java.nio.ByteBuffer;

public interface CmsgHdr {
   void setLevel(int var1);

   int getLevel();

   void setType(int var1);

   int getType();

   void setData(ByteBuffer var1);

   ByteBuffer getData();

   int getLen();
}
