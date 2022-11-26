package org.python.bouncycastle.crypto.digests;

import java.io.ByteArrayOutputStream;
import org.python.bouncycastle.crypto.Digest;

public class NullDigest implements Digest {
   private ByteArrayOutputStream bOut = new ByteArrayOutputStream();

   public String getAlgorithmName() {
      return "NULL";
   }

   public int getDigestSize() {
      return this.bOut.size();
   }

   public void update(byte var1) {
      this.bOut.write(var1);
   }

   public void update(byte[] var1, int var2, int var3) {
      this.bOut.write(var1, var2, var3);
   }

   public int doFinal(byte[] var1, int var2) {
      byte[] var3 = this.bOut.toByteArray();
      System.arraycopy(var3, 0, var1, var2, var3.length);
      this.reset();
      return var3.length;
   }

   public void reset() {
      this.bOut.reset();
   }
}
