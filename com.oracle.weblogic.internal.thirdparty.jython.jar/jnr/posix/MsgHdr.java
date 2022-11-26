package jnr.posix;

import java.nio.ByteBuffer;

public interface MsgHdr {
   void setName(String var1);

   String getName();

   void setIov(ByteBuffer[] var1);

   ByteBuffer[] getIov();

   void setFlags(int var1);

   int getFlags();

   CmsgHdr allocateControl(int var1);

   CmsgHdr[] allocateControls(int[] var1);

   CmsgHdr[] getControls();

   int getControlLen();
}
