package org.python.bouncycastle.crypto.engines;

import org.python.bouncycastle.util.Pack;

public class ChaCha7539Engine extends Salsa20Engine {
   public String getAlgorithmName() {
      return "ChaCha7539-" + this.rounds;
   }

   protected int getNonceSize() {
      return 12;
   }

   protected void advanceCounter(long var1) {
      int var3 = (int)(var1 >>> 32);
      int var4 = (int)var1;
      if (var3 > 0) {
         throw new IllegalStateException("attempt to increase counter past 2^32.");
      } else {
         int var5 = this.engineState[12];
         int[] var10000 = this.engineState;
         var10000[12] += var4;
         if (var5 != 0 && this.engineState[12] < var5) {
            throw new IllegalStateException("attempt to increase counter past 2^32.");
         }
      }
   }

   protected void advanceCounter() {
      if (++this.engineState[12] == 0) {
         throw new IllegalStateException("attempt to increase counter past 2^32.");
      }
   }

   protected void retreatCounter(long var1) {
      int var3 = (int)(var1 >>> 32);
      int var4 = (int)var1;
      if (var3 != 0) {
         throw new IllegalStateException("attempt to reduce counter past zero.");
      } else if (((long)this.engineState[12] & 4294967295L) >= ((long)var4 & 4294967295L)) {
         int[] var10000 = this.engineState;
         var10000[12] -= var4;
      } else {
         throw new IllegalStateException("attempt to reduce counter past zero.");
      }
   }

   protected void retreatCounter() {
      if (this.engineState[12] == 0) {
         throw new IllegalStateException("attempt to reduce counter past zero.");
      } else {
         int var10002 = this.engineState[12]--;
      }
   }

   protected long getCounter() {
      return (long)this.engineState[12] & 4294967295L;
   }

   protected void resetCounter() {
      this.engineState[12] = 0;
   }

   protected void setKey(byte[] var1, byte[] var2) {
      if (var1 != null) {
         if (var1.length != 32) {
            throw new IllegalArgumentException(this.getAlgorithmName() + " requires 256 bit key");
         }

         this.packTauOrSigma(var1.length, this.engineState, 0);
         Pack.littleEndianToInt(var1, 0, this.engineState, 4, 8);
      }

      Pack.littleEndianToInt(var2, 0, this.engineState, 13, 3);
   }

   protected void generateKeyStream(byte[] var1) {
      ChaChaEngine.chachaCore(this.rounds, this.engineState, this.x);
      Pack.intToLittleEndian(this.x, var1, 0);
   }
}
