package com.trilead.ssh2.crypto.cipher;

public class DESede extends DES {
   private int[] key1 = null;
   private int[] key2 = null;
   private int[] key3 = null;
   private boolean encrypt;

   public void init(boolean encrypting, byte[] key) {
      this.key1 = this.generateWorkingKey(encrypting, key, 0);
      this.key2 = this.generateWorkingKey(!encrypting, key, 8);
      this.key3 = this.generateWorkingKey(encrypting, key, 16);
      this.encrypt = encrypting;
   }

   public String getAlgorithmName() {
      return "DESede";
   }

   public int getBlockSize() {
      return 8;
   }

   public void transformBlock(byte[] in, int inOff, byte[] out, int outOff) {
      if (this.key1 == null) {
         throw new IllegalStateException("DESede engine not initialised!");
      } else {
         if (this.encrypt) {
            this.desFunc(this.key1, in, inOff, out, outOff);
            this.desFunc(this.key2, out, outOff, out, outOff);
            this.desFunc(this.key3, out, outOff, out, outOff);
         } else {
            this.desFunc(this.key3, in, inOff, out, outOff);
            this.desFunc(this.key2, out, outOff, out, outOff);
            this.desFunc(this.key1, out, outOff, out, outOff);
         }

      }
   }

   public void reset() {
   }
}
