package org.python.bouncycastle.crypto.engines;

import org.python.bouncycastle.crypto.BlockCipher;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.DataLengthException;
import org.python.bouncycastle.crypto.OutputLengthException;
import org.python.bouncycastle.crypto.params.KeyParameter;

public class NoekeonEngine implements BlockCipher {
   private static final int genericSize = 16;
   private static final int[] nullVector = new int[]{0, 0, 0, 0};
   private static final int[] roundConstants = new int[]{128, 27, 54, 108, 216, 171, 77, 154, 47, 94, 188, 99, 198, 151, 53, 106, 212};
   private int[] state = new int[4];
   private int[] subKeys = new int[4];
   private int[] decryptKeys = new int[4];
   private boolean _initialised = false;
   private boolean _forEncryption;

   public String getAlgorithmName() {
      return "Noekeon";
   }

   public int getBlockSize() {
      return 16;
   }

   public void init(boolean var1, CipherParameters var2) {
      if (!(var2 instanceof KeyParameter)) {
         throw new IllegalArgumentException("invalid parameter passed to Noekeon init - " + var2.getClass().getName());
      } else {
         this._forEncryption = var1;
         this._initialised = true;
         KeyParameter var3 = (KeyParameter)var2;
         this.setKey(var3.getKey());
      }
   }

   public int processBlock(byte[] var1, int var2, byte[] var3, int var4) {
      if (!this._initialised) {
         throw new IllegalStateException(this.getAlgorithmName() + " not initialised");
      } else if (var2 + 16 > var1.length) {
         throw new DataLengthException("input buffer too short");
      } else if (var4 + 16 > var3.length) {
         throw new OutputLengthException("output buffer too short");
      } else {
         return this._forEncryption ? this.encryptBlock(var1, var2, var3, var4) : this.decryptBlock(var1, var2, var3, var4);
      }
   }

   public void reset() {
   }

   private void setKey(byte[] var1) {
      this.subKeys[0] = this.bytesToIntBig(var1, 0);
      this.subKeys[1] = this.bytesToIntBig(var1, 4);
      this.subKeys[2] = this.bytesToIntBig(var1, 8);
      this.subKeys[3] = this.bytesToIntBig(var1, 12);
   }

   private int encryptBlock(byte[] var1, int var2, byte[] var3, int var4) {
      this.state[0] = this.bytesToIntBig(var1, var2);
      this.state[1] = this.bytesToIntBig(var1, var2 + 4);
      this.state[2] = this.bytesToIntBig(var1, var2 + 8);
      this.state[3] = this.bytesToIntBig(var1, var2 + 12);

      int[] var10000;
      int var5;
      for(var5 = 0; var5 < 16; ++var5) {
         var10000 = this.state;
         var10000[0] ^= roundConstants[var5];
         this.theta(this.state, this.subKeys);
         this.pi1(this.state);
         this.gamma(this.state);
         this.pi2(this.state);
      }

      var10000 = this.state;
      var10000[0] ^= roundConstants[var5];
      this.theta(this.state, this.subKeys);
      this.intToBytesBig(this.state[0], var3, var4);
      this.intToBytesBig(this.state[1], var3, var4 + 4);
      this.intToBytesBig(this.state[2], var3, var4 + 8);
      this.intToBytesBig(this.state[3], var3, var4 + 12);
      return 16;
   }

   private int decryptBlock(byte[] var1, int var2, byte[] var3, int var4) {
      this.state[0] = this.bytesToIntBig(var1, var2);
      this.state[1] = this.bytesToIntBig(var1, var2 + 4);
      this.state[2] = this.bytesToIntBig(var1, var2 + 8);
      this.state[3] = this.bytesToIntBig(var1, var2 + 12);
      System.arraycopy(this.subKeys, 0, this.decryptKeys, 0, this.subKeys.length);
      this.theta(this.decryptKeys, nullVector);

      int[] var10000;
      int var5;
      for(var5 = 16; var5 > 0; --var5) {
         this.theta(this.state, this.decryptKeys);
         var10000 = this.state;
         var10000[0] ^= roundConstants[var5];
         this.pi1(this.state);
         this.gamma(this.state);
         this.pi2(this.state);
      }

      this.theta(this.state, this.decryptKeys);
      var10000 = this.state;
      var10000[0] ^= roundConstants[var5];
      this.intToBytesBig(this.state[0], var3, var4);
      this.intToBytesBig(this.state[1], var3, var4 + 4);
      this.intToBytesBig(this.state[2], var3, var4 + 8);
      this.intToBytesBig(this.state[3], var3, var4 + 12);
      return 16;
   }

   private void gamma(int[] var1) {
      var1[1] ^= ~var1[3] & ~var1[2];
      var1[0] ^= var1[2] & var1[1];
      int var2 = var1[3];
      var1[3] = var1[0];
      var1[0] = var2;
      var1[2] ^= var1[0] ^ var1[1] ^ var1[3];
      var1[1] ^= ~var1[3] & ~var1[2];
      var1[0] ^= var1[2] & var1[1];
   }

   private void theta(int[] var1, int[] var2) {
      int var3 = var1[0] ^ var1[2];
      var3 ^= this.rotl(var3, 8) ^ this.rotl(var3, 24);
      var1[1] ^= var3;
      var1[3] ^= var3;

      for(int var4 = 0; var4 < 4; ++var4) {
         var1[var4] ^= var2[var4];
      }

      var3 = var1[1] ^ var1[3];
      var3 ^= this.rotl(var3, 8) ^ this.rotl(var3, 24);
      var1[0] ^= var3;
      var1[2] ^= var3;
   }

   private void pi1(int[] var1) {
      var1[1] = this.rotl(var1[1], 1);
      var1[2] = this.rotl(var1[2], 5);
      var1[3] = this.rotl(var1[3], 2);
   }

   private void pi2(int[] var1) {
      var1[1] = this.rotl(var1[1], 31);
      var1[2] = this.rotl(var1[2], 27);
      var1[3] = this.rotl(var1[3], 30);
   }

   private int bytesToIntBig(byte[] var1, int var2) {
      return var1[var2++] << 24 | (var1[var2++] & 255) << 16 | (var1[var2++] & 255) << 8 | var1[var2] & 255;
   }

   private void intToBytesBig(int var1, byte[] var2, int var3) {
      var2[var3++] = (byte)(var1 >>> 24);
      var2[var3++] = (byte)(var1 >>> 16);
      var2[var3++] = (byte)(var1 >>> 8);
      var2[var3] = (byte)var1;
   }

   private int rotl(int var1, int var2) {
      return var1 << var2 | var1 >>> 32 - var2;
   }
}
