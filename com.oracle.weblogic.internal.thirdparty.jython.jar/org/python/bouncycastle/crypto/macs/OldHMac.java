package org.python.bouncycastle.crypto.macs;

import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.Digest;
import org.python.bouncycastle.crypto.Mac;
import org.python.bouncycastle.crypto.params.KeyParameter;

public class OldHMac implements Mac {
   private static final int BLOCK_LENGTH = 64;
   private static final byte IPAD = 54;
   private static final byte OPAD = 92;
   private Digest digest;
   private int digestSize;
   private byte[] inputPad = new byte[64];
   private byte[] outputPad = new byte[64];

   /** @deprecated */
   public OldHMac(Digest var1) {
      this.digest = var1;
      this.digestSize = var1.getDigestSize();
   }

   public String getAlgorithmName() {
      return this.digest.getAlgorithmName() + "/HMAC";
   }

   public Digest getUnderlyingDigest() {
      return this.digest;
   }

   public void init(CipherParameters var1) {
      this.digest.reset();
      byte[] var2 = ((KeyParameter)var1).getKey();
      int var3;
      if (var2.length > 64) {
         this.digest.update(var2, 0, var2.length);
         this.digest.doFinal(this.inputPad, 0);

         for(var3 = this.digestSize; var3 < this.inputPad.length; ++var3) {
            this.inputPad[var3] = 0;
         }
      } else {
         System.arraycopy(var2, 0, this.inputPad, 0, var2.length);

         for(var3 = var2.length; var3 < this.inputPad.length; ++var3) {
            this.inputPad[var3] = 0;
         }
      }

      this.outputPad = new byte[this.inputPad.length];
      System.arraycopy(this.inputPad, 0, this.outputPad, 0, this.inputPad.length);

      byte[] var10000;
      for(var3 = 0; var3 < this.inputPad.length; ++var3) {
         var10000 = this.inputPad;
         var10000[var3] = (byte)(var10000[var3] ^ 54);
      }

      for(var3 = 0; var3 < this.outputPad.length; ++var3) {
         var10000 = this.outputPad;
         var10000[var3] = (byte)(var10000[var3] ^ 92);
      }

      this.digest.update(this.inputPad, 0, this.inputPad.length);
   }

   public int getMacSize() {
      return this.digestSize;
   }

   public void update(byte var1) {
      this.digest.update(var1);
   }

   public void update(byte[] var1, int var2, int var3) {
      this.digest.update(var1, var2, var3);
   }

   public int doFinal(byte[] var1, int var2) {
      byte[] var3 = new byte[this.digestSize];
      this.digest.doFinal(var3, 0);
      this.digest.update(this.outputPad, 0, this.outputPad.length);
      this.digest.update(var3, 0, var3.length);
      int var4 = this.digest.doFinal(var1, var2);
      this.reset();
      return var4;
   }

   public void reset() {
      this.digest.reset();
      this.digest.update(this.inputPad, 0, this.inputPad.length);
   }
}
