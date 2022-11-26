package org.python.bouncycastle.crypto.params;

import org.python.bouncycastle.crypto.CipherParameters;

public class AEADParameters implements CipherParameters {
   private byte[] associatedText;
   private byte[] nonce;
   private KeyParameter key;
   private int macSize;

   public AEADParameters(KeyParameter var1, int var2, byte[] var3) {
      this(var1, var2, var3, (byte[])null);
   }

   public AEADParameters(KeyParameter var1, int var2, byte[] var3, byte[] var4) {
      this.key = var1;
      this.nonce = var3;
      this.macSize = var2;
      this.associatedText = var4;
   }

   public KeyParameter getKey() {
      return this.key;
   }

   public int getMacSize() {
      return this.macSize;
   }

   public byte[] getAssociatedText() {
      return this.associatedText;
   }

   public byte[] getNonce() {
      return this.nonce;
   }
}
