package org.python.bouncycastle.crypto.engines;

import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.DataLengthException;
import org.python.bouncycastle.crypto.OutputLengthException;
import org.python.bouncycastle.crypto.StreamCipher;
import org.python.bouncycastle.crypto.params.KeyParameter;
import org.python.bouncycastle.util.Pack;

public class ISAACEngine implements StreamCipher {
   private final int sizeL = 8;
   private final int stateArraySize = 256;
   private int[] engineState = null;
   private int[] results = null;
   private int a = 0;
   private int b = 0;
   private int c = 0;
   private int index = 0;
   private byte[] keyStream = new byte[1024];
   private byte[] workingKey = null;
   private boolean initialised = false;

   public void init(boolean var1, CipherParameters var2) {
      if (!(var2 instanceof KeyParameter)) {
         throw new IllegalArgumentException("invalid parameter passed to ISAAC init - " + var2.getClass().getName());
      } else {
         KeyParameter var3 = (KeyParameter)var2;
         this.setKey(var3.getKey());
      }
   }

   public byte returnByte(byte var1) {
      if (this.index == 0) {
         this.isaac();
         this.keyStream = Pack.intToBigEndian(this.results);
      }

      byte var2 = (byte)(this.keyStream[this.index] ^ var1);
      this.index = this.index + 1 & 1023;
      return var2;
   }

   public int processBytes(byte[] var1, int var2, int var3, byte[] var4, int var5) {
      if (!this.initialised) {
         throw new IllegalStateException(this.getAlgorithmName() + " not initialised");
      } else if (var2 + var3 > var1.length) {
         throw new DataLengthException("input buffer too short");
      } else if (var5 + var3 > var4.length) {
         throw new OutputLengthException("output buffer too short");
      } else {
         for(int var6 = 0; var6 < var3; ++var6) {
            if (this.index == 0) {
               this.isaac();
               this.keyStream = Pack.intToBigEndian(this.results);
            }

            var4[var6 + var5] = (byte)(this.keyStream[this.index] ^ var1[var6 + var2]);
            this.index = this.index + 1 & 1023;
         }

         return var3;
      }
   }

   public String getAlgorithmName() {
      return "ISAAC";
   }

   public void reset() {
      this.setKey(this.workingKey);
   }

   private void setKey(byte[] var1) {
      this.workingKey = var1;
      if (this.engineState == null) {
         this.engineState = new int[256];
      }

      if (this.results == null) {
         this.results = new int[256];
      }

      int var2;
      for(var2 = 0; var2 < 256; ++var2) {
         this.engineState[var2] = this.results[var2] = 0;
      }

      this.a = this.b = this.c = 0;
      this.index = 0;
      byte[] var3 = new byte[var1.length + (var1.length & 3)];
      System.arraycopy(var1, 0, var3, 0, var1.length);

      for(var2 = 0; var2 < var3.length; var2 += 4) {
         this.results[var2 >>> 2] = Pack.littleEndianToInt(var3, var2);
      }

      int[] var4 = new int[8];

      for(var2 = 0; var2 < 8; ++var2) {
         var4[var2] = -1640531527;
      }

      for(var2 = 0; var2 < 4; ++var2) {
         this.mix(var4);
      }

      for(var2 = 0; var2 < 2; ++var2) {
         for(int var5 = 0; var5 < 256; var5 += 8) {
            int var6;
            for(var6 = 0; var6 < 8; ++var6) {
               var4[var6] += var2 < 1 ? this.results[var5 + var6] : this.engineState[var5 + var6];
            }

            this.mix(var4);

            for(var6 = 0; var6 < 8; ++var6) {
               this.engineState[var5 + var6] = var4[var6];
            }
         }
      }

      this.isaac();
      this.initialised = true;
   }

   private void isaac() {
      this.b += ++this.c;

      for(int var1 = 0; var1 < 256; ++var1) {
         int var2 = this.engineState[var1];
         switch (var1 & 3) {
            case 0:
               this.a ^= this.a << 13;
               break;
            case 1:
               this.a ^= this.a >>> 6;
               break;
            case 2:
               this.a ^= this.a << 2;
               break;
            case 3:
               this.a ^= this.a >>> 16;
         }

         this.a += this.engineState[var1 + 128 & 255];
         int var3;
         this.engineState[var1] = var3 = this.engineState[var2 >>> 2 & 255] + this.a + this.b;
         this.results[var1] = this.b = this.engineState[var3 >>> 10 & 255] + var2;
      }

   }

   private void mix(int[] var1) {
      var1[0] ^= var1[1] << 11;
      var1[3] += var1[0];
      var1[1] += var1[2];
      var1[1] ^= var1[2] >>> 2;
      var1[4] += var1[1];
      var1[2] += var1[3];
      var1[2] ^= var1[3] << 8;
      var1[5] += var1[2];
      var1[3] += var1[4];
      var1[3] ^= var1[4] >>> 16;
      var1[6] += var1[3];
      var1[4] += var1[5];
      var1[4] ^= var1[5] << 10;
      var1[7] += var1[4];
      var1[5] += var1[6];
      var1[5] ^= var1[6] >>> 4;
      var1[0] += var1[5];
      var1[6] += var1[7];
      var1[6] ^= var1[7] << 8;
      var1[1] += var1[6];
      var1[7] += var1[0];
      var1[7] ^= var1[0] >>> 9;
      var1[2] += var1[7];
      var1[0] += var1[1];
   }
}
