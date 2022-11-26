package com.trilead.ssh2.crypto.digest;

public interface Digest {
   int getDigestLength();

   void update(byte var1);

   void update(byte[] var1);

   void update(byte[] var1, int var2, int var3);

   void reset();

   void digest(byte[] var1);

   void digest(byte[] var1, int var2);
}
