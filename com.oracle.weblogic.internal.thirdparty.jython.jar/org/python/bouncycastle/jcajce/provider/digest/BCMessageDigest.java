package org.python.bouncycastle.jcajce.provider.digest;

import java.security.MessageDigest;
import org.python.bouncycastle.crypto.Digest;

public class BCMessageDigest extends MessageDigest {
   protected Digest digest;

   protected BCMessageDigest(Digest var1) {
      super(var1.getAlgorithmName());
      this.digest = var1;
   }

   public void engineReset() {
      this.digest.reset();
   }

   public void engineUpdate(byte var1) {
      this.digest.update(var1);
   }

   public void engineUpdate(byte[] var1, int var2, int var3) {
      this.digest.update(var1, var2, var3);
   }

   public byte[] engineDigest() {
      byte[] var1 = new byte[this.digest.getDigestSize()];
      this.digest.doFinal(var1, 0);
      return var1;
   }
}
