package org.python.bouncycastle.crypto.engines;

import org.python.bouncycastle.crypto.BlockCipher;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.DataLengthException;
import org.python.bouncycastle.crypto.OutputLengthException;
import org.python.bouncycastle.crypto.params.KeyParameter;

public abstract class SerpentEngineBase implements BlockCipher {
   protected static final int BLOCK_SIZE = 16;
   static final int ROUNDS = 32;
   static final int PHI = -1640531527;
   protected boolean encrypting;
   protected int[] wKey;
   protected int X0;
   protected int X1;
   protected int X2;
   protected int X3;

   SerpentEngineBase() {
   }

   public void init(boolean var1, CipherParameters var2) {
      if (var2 instanceof KeyParameter) {
         this.encrypting = var1;
         this.wKey = this.makeWorkingKey(((KeyParameter)var2).getKey());
      } else {
         throw new IllegalArgumentException("invalid parameter passed to " + this.getAlgorithmName() + " init - " + var2.getClass().getName());
      }
   }

   public String getAlgorithmName() {
      return "Serpent";
   }

   public int getBlockSize() {
      return 16;
   }

   public final int processBlock(byte[] var1, int var2, byte[] var3, int var4) {
      if (this.wKey == null) {
         throw new IllegalStateException(this.getAlgorithmName() + " not initialised");
      } else if (var2 + 16 > var1.length) {
         throw new DataLengthException("input buffer too short");
      } else if (var4 + 16 > var3.length) {
         throw new OutputLengthException("output buffer too short");
      } else {
         if (this.encrypting) {
            this.encryptBlock(var1, var2, var3, var4);
         } else {
            this.decryptBlock(var1, var2, var3, var4);
         }

         return 16;
      }
   }

   public void reset() {
   }

   protected static int rotateLeft(int var0, int var1) {
      return var0 << var1 | var0 >>> -var1;
   }

   protected static int rotateRight(int var0, int var1) {
      return var0 >>> var1 | var0 << -var1;
   }

   protected final void sb0(int var1, int var2, int var3, int var4) {
      int var5 = var1 ^ var4;
      int var6 = var3 ^ var5;
      int var7 = var2 ^ var6;
      this.X3 = var1 & var4 ^ var7;
      int var8 = var1 ^ var2 & var5;
      this.X2 = var7 ^ (var3 | var8);
      int var9 = this.X3 & (var6 ^ var8);
      this.X1 = ~var6 ^ var9;
      this.X0 = var9 ^ ~var8;
   }

   protected final void ib0(int var1, int var2, int var3, int var4) {
      int var5 = ~var1;
      int var6 = var1 ^ var2;
      int var7 = var4 ^ (var5 | var6);
      int var8 = var3 ^ var7;
      this.X2 = var6 ^ var8;
      int var9 = var5 ^ var4 & var6;
      this.X1 = var7 ^ this.X2 & var9;
      this.X3 = var1 & var7 ^ (var8 | this.X1);
      this.X0 = this.X3 ^ var8 ^ var9;
   }

   protected final void sb1(int var1, int var2, int var3, int var4) {
      int var5 = var2 ^ ~var1;
      int var6 = var3 ^ (var1 | var5);
      this.X2 = var4 ^ var6;
      int var7 = var2 ^ (var4 | var5);
      int var8 = var5 ^ this.X2;
      this.X3 = var8 ^ var6 & var7;
      int var9 = var6 ^ var7;
      this.X1 = this.X3 ^ var9;
      this.X0 = var6 ^ var8 & var9;
   }

   protected final void ib1(int var1, int var2, int var3, int var4) {
      int var5 = var2 ^ var4;
      int var6 = var1 ^ var2 & var5;
      int var7 = var5 ^ var6;
      this.X3 = var3 ^ var7;
      int var8 = var2 ^ var5 & var6;
      int var9 = this.X3 | var8;
      this.X1 = var6 ^ var9;
      int var10 = ~this.X1;
      int var11 = this.X3 ^ var8;
      this.X0 = var10 ^ var11;
      this.X2 = var7 ^ (var10 | var11);
   }

   protected final void sb2(int var1, int var2, int var3, int var4) {
      int var5 = ~var1;
      int var6 = var2 ^ var4;
      int var7 = var3 & var5;
      this.X0 = var6 ^ var7;
      int var8 = var3 ^ var5;
      int var9 = var3 ^ this.X0;
      int var10 = var2 & var9;
      this.X3 = var8 ^ var10;
      this.X2 = var1 ^ (var4 | var10) & (this.X0 | var8);
      this.X1 = var6 ^ this.X3 ^ this.X2 ^ (var4 | var5);
   }

   protected final void ib2(int var1, int var2, int var3, int var4) {
      int var5 = var2 ^ var4;
      int var6 = ~var5;
      int var7 = var1 ^ var3;
      int var8 = var3 ^ var5;
      int var9 = var2 & var8;
      this.X0 = var7 ^ var9;
      int var10 = var1 | var6;
      int var11 = var4 ^ var10;
      int var12 = var7 | var11;
      this.X3 = var5 ^ var12;
      int var13 = ~var8;
      int var14 = this.X0 | this.X3;
      this.X1 = var13 ^ var14;
      this.X2 = var4 & var13 ^ var7 ^ var14;
   }

   protected final void sb3(int var1, int var2, int var3, int var4) {
      int var5 = var1 ^ var2;
      int var6 = var1 & var3;
      int var7 = var1 | var4;
      int var8 = var3 ^ var4;
      int var9 = var5 & var7;
      int var10 = var6 | var9;
      this.X2 = var8 ^ var10;
      int var11 = var2 ^ var7;
      int var12 = var10 ^ var11;
      int var13 = var8 & var12;
      this.X0 = var5 ^ var13;
      int var14 = this.X2 & this.X0;
      this.X1 = var12 ^ var14;
      this.X3 = (var2 | var4) ^ var8 ^ var14;
   }

   protected final void ib3(int var1, int var2, int var3, int var4) {
      int var5 = var1 | var2;
      int var6 = var2 ^ var3;
      int var7 = var2 & var6;
      int var8 = var1 ^ var7;
      int var9 = var3 ^ var8;
      int var10 = var4 | var8;
      this.X0 = var6 ^ var10;
      int var11 = var6 | var10;
      int var12 = var4 ^ var11;
      this.X2 = var9 ^ var12;
      int var13 = var5 ^ var12;
      int var14 = this.X0 & var13;
      this.X3 = var8 ^ var14;
      this.X1 = this.X3 ^ this.X0 ^ var13;
   }

   protected final void sb4(int var1, int var2, int var3, int var4) {
      int var5 = var1 ^ var4;
      int var6 = var4 & var5;
      int var7 = var3 ^ var6;
      int var8 = var2 | var7;
      this.X3 = var5 ^ var8;
      int var9 = ~var2;
      int var10 = var5 | var9;
      this.X0 = var7 ^ var10;
      int var11 = var1 & this.X0;
      int var12 = var5 ^ var9;
      int var13 = var8 & var12;
      this.X2 = var11 ^ var13;
      this.X1 = var1 ^ var7 ^ var12 & this.X2;
   }

   protected final void ib4(int var1, int var2, int var3, int var4) {
      int var5 = var3 | var4;
      int var6 = var1 & var5;
      int var7 = var2 ^ var6;
      int var8 = var1 & var7;
      int var9 = var3 ^ var8;
      this.X1 = var4 ^ var9;
      int var10 = ~var1;
      int var11 = var9 & this.X1;
      this.X3 = var7 ^ var11;
      int var12 = this.X1 | var10;
      int var13 = var4 ^ var12;
      this.X0 = this.X3 ^ var13;
      this.X2 = var7 & var13 ^ this.X1 ^ var10;
   }

   protected final void sb5(int var1, int var2, int var3, int var4) {
      int var5 = ~var1;
      int var6 = var1 ^ var2;
      int var7 = var1 ^ var4;
      int var8 = var3 ^ var5;
      int var9 = var6 | var7;
      this.X0 = var8 ^ var9;
      int var10 = var4 & this.X0;
      int var11 = var6 ^ this.X0;
      this.X1 = var10 ^ var11;
      int var12 = var5 | this.X0;
      int var13 = var6 | var10;
      int var14 = var7 ^ var12;
      this.X2 = var13 ^ var14;
      this.X3 = var2 ^ var10 ^ this.X1 & var14;
   }

   protected final void ib5(int var1, int var2, int var3, int var4) {
      int var5 = ~var3;
      int var6 = var2 & var5;
      int var7 = var4 ^ var6;
      int var8 = var1 & var7;
      int var9 = var2 ^ var5;
      this.X3 = var8 ^ var9;
      int var10 = var2 | this.X3;
      int var11 = var1 & var10;
      this.X1 = var7 ^ var11;
      int var12 = var1 | var4;
      int var13 = var5 ^ var10;
      this.X0 = var12 ^ var13;
      this.X2 = var2 & var12 ^ (var8 | var1 ^ var3);
   }

   protected final void sb6(int var1, int var2, int var3, int var4) {
      int var5 = ~var1;
      int var6 = var1 ^ var4;
      int var7 = var2 ^ var6;
      int var8 = var5 | var6;
      int var9 = var3 ^ var8;
      this.X1 = var2 ^ var9;
      int var10 = var6 | this.X1;
      int var11 = var4 ^ var10;
      int var12 = var9 & var11;
      this.X2 = var7 ^ var12;
      int var13 = var9 ^ var11;
      this.X0 = this.X2 ^ var13;
      this.X3 = ~var9 ^ var7 & var13;
   }

   protected final void ib6(int var1, int var2, int var3, int var4) {
      int var5 = ~var1;
      int var6 = var1 ^ var2;
      int var7 = var3 ^ var6;
      int var8 = var3 | var5;
      int var9 = var4 ^ var8;
      this.X1 = var7 ^ var9;
      int var10 = var7 & var9;
      int var11 = var6 ^ var10;
      int var12 = var2 | var11;
      this.X3 = var9 ^ var12;
      int var13 = var2 | this.X3;
      this.X0 = var11 ^ var13;
      this.X2 = var4 & var5 ^ var7 ^ var13;
   }

   protected final void sb7(int var1, int var2, int var3, int var4) {
      int var5 = var2 ^ var3;
      int var6 = var3 & var5;
      int var7 = var4 ^ var6;
      int var8 = var1 ^ var7;
      int var9 = var4 | var5;
      int var10 = var8 & var9;
      this.X1 = var2 ^ var10;
      int var11 = var7 | this.X1;
      int var12 = var1 & var8;
      this.X3 = var5 ^ var12;
      int var13 = var8 ^ var11;
      int var14 = this.X3 & var13;
      this.X2 = var7 ^ var14;
      this.X0 = ~var13 ^ this.X3 & this.X2;
   }

   protected final void ib7(int var1, int var2, int var3, int var4) {
      int var5 = var3 | var1 & var2;
      int var6 = var4 & (var1 | var2);
      this.X3 = var5 ^ var6;
      int var7 = ~var4;
      int var8 = var2 ^ var6;
      int var9 = var8 | this.X3 ^ var7;
      this.X1 = var1 ^ var9;
      this.X0 = var3 ^ var8 ^ (var4 | this.X1);
      this.X2 = var5 ^ this.X1 ^ this.X0 ^ var1 & this.X3;
   }

   protected final void LT() {
      int var1 = rotateLeft(this.X0, 13);
      int var2 = rotateLeft(this.X2, 3);
      int var3 = this.X1 ^ var1 ^ var2;
      int var4 = this.X3 ^ var2 ^ var1 << 3;
      this.X1 = rotateLeft(var3, 1);
      this.X3 = rotateLeft(var4, 7);
      this.X0 = rotateLeft(var1 ^ this.X1 ^ this.X3, 5);
      this.X2 = rotateLeft(var2 ^ this.X3 ^ this.X1 << 7, 22);
   }

   protected final void inverseLT() {
      int var1 = rotateRight(this.X2, 22) ^ this.X3 ^ this.X1 << 7;
      int var2 = rotateRight(this.X0, 5) ^ this.X1 ^ this.X3;
      int var3 = rotateRight(this.X3, 7);
      int var4 = rotateRight(this.X1, 1);
      this.X3 = var3 ^ var1 ^ var2 << 3;
      this.X1 = var4 ^ var2 ^ var1;
      this.X2 = rotateRight(var1, 3);
      this.X0 = rotateRight(var2, 13);
   }

   protected abstract int[] makeWorkingKey(byte[] var1);

   protected abstract void encryptBlock(byte[] var1, int var2, byte[] var3, int var4);

   protected abstract void decryptBlock(byte[] var1, int var2, byte[] var3, int var4);
}
