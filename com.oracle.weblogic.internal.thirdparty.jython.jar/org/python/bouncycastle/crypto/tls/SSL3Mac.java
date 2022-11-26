package org.python.bouncycastle.crypto.tls;

import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.Digest;
import org.python.bouncycastle.crypto.Mac;
import org.python.bouncycastle.crypto.params.KeyParameter;
import org.python.bouncycastle.util.Arrays;

public class SSL3Mac implements Mac {
   private static final byte IPAD_BYTE = 54;
   private static final byte OPAD_BYTE = 92;
   static final byte[] IPAD = genPad((byte)54, 48);
   static final byte[] OPAD = genPad((byte)92, 48);
   private Digest digest;
   private int padLength;
   private byte[] secret;

   public SSL3Mac(Digest var1) {
      this.digest = var1;
      if (var1.getDigestSize() == 20) {
         this.padLength = 40;
      } else {
         this.padLength = 48;
      }

   }

   public String getAlgorithmName() {
      return this.digest.getAlgorithmName() + "/SSL3MAC";
   }

   public Digest getUnderlyingDigest() {
      return this.digest;
   }

   public void init(CipherParameters var1) {
      this.secret = Arrays.clone(((KeyParameter)var1).getKey());
      this.reset();
   }

   public int getMacSize() {
      return this.digest.getDigestSize();
   }

   public void update(byte var1) {
      this.digest.update(var1);
   }

   public void update(byte[] var1, int var2, int var3) {
      this.digest.update(var1, var2, var3);
   }

   public int doFinal(byte[] var1, int var2) {
      byte[] var3 = new byte[this.digest.getDigestSize()];
      this.digest.doFinal(var3, 0);
      this.digest.update(this.secret, 0, this.secret.length);
      this.digest.update(OPAD, 0, this.padLength);
      this.digest.update(var3, 0, var3.length);
      int var4 = this.digest.doFinal(var1, var2);
      this.reset();
      return var4;
   }

   public void reset() {
      this.digest.reset();
      this.digest.update(this.secret, 0, this.secret.length);
      this.digest.update(IPAD, 0, this.padLength);
   }

   private static byte[] genPad(byte var0, int var1) {
      byte[] var2 = new byte[var1];
      Arrays.fill(var2, var0);
      return var2;
   }
}
