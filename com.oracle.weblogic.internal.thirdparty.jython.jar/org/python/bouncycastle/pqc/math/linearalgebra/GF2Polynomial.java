package org.python.bouncycastle.pqc.math.linearalgebra;

import java.math.BigInteger;
import java.util.Random;

public class GF2Polynomial {
   private int len;
   private int blocks;
   private int[] value;
   private static Random rand = new Random();
   private static final boolean[] parity = new boolean[]{false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false};
   private static final short[] squaringTable = new short[]{0, 1, 4, 5, 16, 17, 20, 21, 64, 65, 68, 69, 80, 81, 84, 85, 256, 257, 260, 261, 272, 273, 276, 277, 320, 321, 324, 325, 336, 337, 340, 341, 1024, 1025, 1028, 1029, 1040, 1041, 1044, 1045, 1088, 1089, 1092, 1093, 1104, 1105, 1108, 1109, 1280, 1281, 1284, 1285, 1296, 1297, 1300, 1301, 1344, 1345, 1348, 1349, 1360, 1361, 1364, 1365, 4096, 4097, 4100, 4101, 4112, 4113, 4116, 4117, 4160, 4161, 4164, 4165, 4176, 4177, 4180, 4181, 4352, 4353, 4356, 4357, 4368, 4369, 4372, 4373, 4416, 4417, 4420, 4421, 4432, 4433, 4436, 4437, 5120, 5121, 5124, 5125, 5136, 5137, 5140, 5141, 5184, 5185, 5188, 5189, 5200, 5201, 5204, 5205, 5376, 5377, 5380, 5381, 5392, 5393, 5396, 5397, 5440, 5441, 5444, 5445, 5456, 5457, 5460, 5461, 16384, 16385, 16388, 16389, 16400, 16401, 16404, 16405, 16448, 16449, 16452, 16453, 16464, 16465, 16468, 16469, 16640, 16641, 16644, 16645, 16656, 16657, 16660, 16661, 16704, 16705, 16708, 16709, 16720, 16721, 16724, 16725, 17408, 17409, 17412, 17413, 17424, 17425, 17428, 17429, 17472, 17473, 17476, 17477, 17488, 17489, 17492, 17493, 17664, 17665, 17668, 17669, 17680, 17681, 17684, 17685, 17728, 17729, 17732, 17733, 17744, 17745, 17748, 17749, 20480, 20481, 20484, 20485, 20496, 20497, 20500, 20501, 20544, 20545, 20548, 20549, 20560, 20561, 20564, 20565, 20736, 20737, 20740, 20741, 20752, 20753, 20756, 20757, 20800, 20801, 20804, 20805, 20816, 20817, 20820, 20821, 21504, 21505, 21508, 21509, 21520, 21521, 21524, 21525, 21568, 21569, 21572, 21573, 21584, 21585, 21588, 21589, 21760, 21761, 21764, 21765, 21776, 21777, 21780, 21781, 21824, 21825, 21828, 21829, 21840, 21841, 21844, 21845};
   private static final int[] bitMask = new int[]{1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 262144, 524288, 1048576, 2097152, 4194304, 8388608, 16777216, 33554432, 67108864, 134217728, 268435456, 536870912, 1073741824, Integer.MIN_VALUE, 0};
   private static final int[] reverseRightMask = new int[]{0, 1, 3, 7, 15, 31, 63, 127, 255, 511, 1023, 2047, 4095, 8191, 16383, 32767, 65535, 131071, 262143, 524287, 1048575, 2097151, 4194303, 8388607, 16777215, 33554431, 67108863, 134217727, 268435455, 536870911, 1073741823, Integer.MAX_VALUE, -1};

   public GF2Polynomial(int var1) {
      int var2 = var1;
      if (var1 < 1) {
         var2 = 1;
      }

      this.blocks = (var2 - 1 >> 5) + 1;
      this.value = new int[this.blocks];
      this.len = var2;
   }

   public GF2Polynomial(int var1, Random var2) {
      int var3 = var1;
      if (var1 < 1) {
         var3 = 1;
      }

      this.blocks = (var3 - 1 >> 5) + 1;
      this.value = new int[this.blocks];
      this.len = var3;
      this.randomize(var2);
   }

   public GF2Polynomial(int var1, String var2) {
      int var3 = var1;
      if (var1 < 1) {
         var3 = 1;
      }

      this.blocks = (var3 - 1 >> 5) + 1;
      this.value = new int[this.blocks];
      this.len = var3;
      if (var2.equalsIgnoreCase("ZERO")) {
         this.assignZero();
      } else if (var2.equalsIgnoreCase("ONE")) {
         this.assignOne();
      } else if (var2.equalsIgnoreCase("RANDOM")) {
         this.randomize();
      } else if (var2.equalsIgnoreCase("X")) {
         this.assignX();
      } else {
         if (!var2.equalsIgnoreCase("ALL")) {
            throw new IllegalArgumentException("Error: GF2Polynomial was called using " + var2 + " as value!");
         }

         this.assignAll();
      }

   }

   public GF2Polynomial(int var1, int[] var2) {
      int var3 = var1;
      if (var1 < 1) {
         var3 = 1;
      }

      this.blocks = (var3 - 1 >> 5) + 1;
      this.value = new int[this.blocks];
      this.len = var3;
      int var4 = Math.min(this.blocks, var2.length);
      System.arraycopy(var2, 0, this.value, 0, var4);
      this.zeroUnusedBits();
   }

   public GF2Polynomial(int var1, byte[] var2) {
      int var3 = var1;
      if (var1 < 1) {
         var3 = 1;
      }

      this.blocks = (var3 - 1 >> 5) + 1;
      this.value = new int[this.blocks];
      this.len = var3;
      int var4 = Math.min((var2.length - 1 >> 2) + 1, this.blocks);

      int[] var10000;
      int var5;
      int var6;
      for(var5 = 0; var5 < var4 - 1; ++var5) {
         var6 = var2.length - (var5 << 2) - 1;
         this.value[var5] = var2[var6] & 255;
         var10000 = this.value;
         var10000[var5] |= var2[var6 - 1] << 8 & '\uff00';
         var10000 = this.value;
         var10000[var5] |= var2[var6 - 2] << 16 & 16711680;
         var10000 = this.value;
         var10000[var5] |= var2[var6 - 3] << 24 & -16777216;
      }

      var5 = var4 - 1;
      var6 = var2.length - (var5 << 2) - 1;
      this.value[var5] = var2[var6] & 255;
      if (var6 > 0) {
         var10000 = this.value;
         var10000[var5] |= var2[var6 - 1] << 8 & '\uff00';
      }

      if (var6 > 1) {
         var10000 = this.value;
         var10000[var5] |= var2[var6 - 2] << 16 & 16711680;
      }

      if (var6 > 2) {
         var10000 = this.value;
         var10000[var5] |= var2[var6 - 3] << 24 & -16777216;
      }

      this.zeroUnusedBits();
      this.reduceN();
   }

   public GF2Polynomial(int var1, BigInteger var2) {
      int var3 = var1;
      if (var1 < 1) {
         var3 = 1;
      }

      this.blocks = (var3 - 1 >> 5) + 1;
      this.value = new int[this.blocks];
      this.len = var3;
      byte[] var4 = var2.toByteArray();
      if (var4[0] == 0) {
         byte[] var5 = new byte[var4.length - 1];
         System.arraycopy(var4, 1, var5, 0, var5.length);
         var4 = var5;
      }

      int var9 = var4.length & 3;
      int var6 = (var4.length - 1 >> 2) + 1;

      int var7;
      int[] var10000;
      for(var7 = 0; var7 < var9; ++var7) {
         var10000 = this.value;
         var10000[var6 - 1] |= (var4[var7] & 255) << (var9 - 1 - var7 << 3);
      }

      boolean var8 = false;

      for(var7 = 0; var7 <= var4.length - 4 >> 2; ++var7) {
         int var10 = var4.length - 1 - (var7 << 2);
         this.value[var7] = var4[var10] & 255;
         var10000 = this.value;
         var10000[var7] |= var4[var10 - 1] << 8 & '\uff00';
         var10000 = this.value;
         var10000[var7] |= var4[var10 - 2] << 16 & 16711680;
         var10000 = this.value;
         var10000[var7] |= var4[var10 - 3] << 24 & -16777216;
      }

      if ((this.len & 31) != 0) {
         var10000 = this.value;
         int var10001 = this.blocks - 1;
         var10000[var10001] &= reverseRightMask[this.len & 31];
      }

      this.reduceN();
   }

   public GF2Polynomial(GF2Polynomial var1) {
      this.len = var1.len;
      this.blocks = var1.blocks;
      this.value = IntUtils.clone(var1.value);
   }

   public Object clone() {
      return new GF2Polynomial(this);
   }

   public int getLength() {
      return this.len;
   }

   public int[] toIntegerArray() {
      int[] var1 = new int[this.blocks];
      System.arraycopy(this.value, 0, var1, 0, this.blocks);
      return var1;
   }

   public String toString(int var1) {
      char[] var2 = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
      String[] var3 = new String[]{"0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111", "1000", "1001", "1010", "1011", "1100", "1101", "1110", "1111"};
      String var4 = new String();
      int var5;
      if (var1 == 16) {
         for(var5 = this.blocks - 1; var5 >= 0; --var5) {
            var4 = var4 + var2[this.value[var5] >>> 28 & 15];
            var4 = var4 + var2[this.value[var5] >>> 24 & 15];
            var4 = var4 + var2[this.value[var5] >>> 20 & 15];
            var4 = var4 + var2[this.value[var5] >>> 16 & 15];
            var4 = var4 + var2[this.value[var5] >>> 12 & 15];
            var4 = var4 + var2[this.value[var5] >>> 8 & 15];
            var4 = var4 + var2[this.value[var5] >>> 4 & 15];
            var4 = var4 + var2[this.value[var5] & 15];
            var4 = var4 + " ";
         }
      } else {
         for(var5 = this.blocks - 1; var5 >= 0; --var5) {
            var4 = var4 + var3[this.value[var5] >>> 28 & 15];
            var4 = var4 + var3[this.value[var5] >>> 24 & 15];
            var4 = var4 + var3[this.value[var5] >>> 20 & 15];
            var4 = var4 + var3[this.value[var5] >>> 16 & 15];
            var4 = var4 + var3[this.value[var5] >>> 12 & 15];
            var4 = var4 + var3[this.value[var5] >>> 8 & 15];
            var4 = var4 + var3[this.value[var5] >>> 4 & 15];
            var4 = var4 + var3[this.value[var5] & 15];
            var4 = var4 + " ";
         }
      }

      return var4;
   }

   public byte[] toByteArray() {
      int var1 = (this.len - 1 >> 3) + 1;
      int var2 = var1 & 3;
      byte[] var3 = new byte[var1];

      int var4;
      int var5;
      for(var4 = 0; var4 < var1 >> 2; ++var4) {
         var5 = var1 - (var4 << 2) - 1;
         var3[var5] = (byte)(this.value[var4] & 255);
         var3[var5 - 1] = (byte)((this.value[var4] & '\uff00') >>> 8);
         var3[var5 - 2] = (byte)((this.value[var4] & 16711680) >>> 16);
         var3[var5 - 3] = (byte)((this.value[var4] & -16777216) >>> 24);
      }

      for(var4 = 0; var4 < var2; ++var4) {
         var5 = var2 - var4 - 1 << 3;
         var3[var4] = (byte)((this.value[this.blocks - 1] & 255 << var5) >>> var5);
      }

      return var3;
   }

   public BigInteger toFlexiBigInt() {
      return this.len != 0 && !this.isZero() ? new BigInteger(1, this.toByteArray()) : new BigInteger(0, new byte[0]);
   }

   public void assignOne() {
      for(int var1 = 1; var1 < this.blocks; ++var1) {
         this.value[var1] = 0;
      }

      this.value[0] = 1;
   }

   public void assignX() {
      for(int var1 = 1; var1 < this.blocks; ++var1) {
         this.value[var1] = 0;
      }

      this.value[0] = 2;
   }

   public void assignAll() {
      for(int var1 = 0; var1 < this.blocks; ++var1) {
         this.value[var1] = -1;
      }

      this.zeroUnusedBits();
   }

   public void assignZero() {
      for(int var1 = 0; var1 < this.blocks; ++var1) {
         this.value[var1] = 0;
      }

   }

   public void randomize() {
      for(int var1 = 0; var1 < this.blocks; ++var1) {
         this.value[var1] = rand.nextInt();
      }

      this.zeroUnusedBits();
   }

   public void randomize(Random var1) {
      for(int var2 = 0; var2 < this.blocks; ++var2) {
         this.value[var2] = var1.nextInt();
      }

      this.zeroUnusedBits();
   }

   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof GF2Polynomial) {
         GF2Polynomial var2 = (GF2Polynomial)var1;
         if (this.len != var2.len) {
            return false;
         } else {
            for(int var3 = 0; var3 < this.blocks; ++var3) {
               if (this.value[var3] != var2.value[var3]) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.len + this.value.hashCode();
   }

   public boolean isZero() {
      if (this.len == 0) {
         return true;
      } else {
         for(int var1 = 0; var1 < this.blocks; ++var1) {
            if (this.value[var1] != 0) {
               return false;
            }
         }

         return true;
      }
   }

   public boolean isOne() {
      for(int var1 = 1; var1 < this.blocks; ++var1) {
         if (this.value[var1] != 0) {
            return false;
         }
      }

      if (this.value[0] != 1) {
         return false;
      } else {
         return true;
      }
   }

   public void addToThis(GF2Polynomial var1) {
      this.expandN(var1.len);
      this.xorThisBy(var1);
   }

   public GF2Polynomial add(GF2Polynomial var1) {
      return this.xor(var1);
   }

   public void subtractFromThis(GF2Polynomial var1) {
      this.expandN(var1.len);
      this.xorThisBy(var1);
   }

   public GF2Polynomial subtract(GF2Polynomial var1) {
      return this.xor(var1);
   }

   public void increaseThis() {
      this.xorBit(0);
   }

   public GF2Polynomial increase() {
      GF2Polynomial var1 = new GF2Polynomial(this);
      var1.increaseThis();
      return var1;
   }

   public GF2Polynomial multiplyClassic(GF2Polynomial var1) {
      GF2Polynomial var2 = new GF2Polynomial(Math.max(this.len, var1.len) << 1);
      GF2Polynomial[] var3 = new GF2Polynomial[32];
      var3[0] = new GF2Polynomial(this);

      int var4;
      for(var4 = 1; var4 <= 31; ++var4) {
         var3[var4] = var3[var4 - 1].shiftLeft();
      }

      for(var4 = 0; var4 < var1.blocks; ++var4) {
         int var5;
         for(var5 = 0; var5 <= 31; ++var5) {
            if ((var1.value[var4] & bitMask[var5]) != 0) {
               var2.xorThisBy(var3[var5]);
            }
         }

         for(var5 = 0; var5 <= 31; ++var5) {
            var3[var5].shiftBlocksLeft();
         }
      }

      return var2;
   }

   public GF2Polynomial multiply(GF2Polynomial var1) {
      int var2 = Math.max(this.len, var1.len);
      this.expandN(var2);
      var1.expandN(var2);
      return this.karaMult(var1);
   }

   private GF2Polynomial karaMult(GF2Polynomial var1) {
      GF2Polynomial var2 = new GF2Polynomial(this.len << 1);
      if (this.len <= 32) {
         var2.value = mult32(this.value[0], var1.value[0]);
         return var2;
      } else if (this.len <= 64) {
         var2.value = mult64(this.value, var1.value);
         return var2;
      } else if (this.len <= 128) {
         var2.value = mult128(this.value, var1.value);
         return var2;
      } else if (this.len <= 256) {
         var2.value = mult256(this.value, var1.value);
         return var2;
      } else if (this.len <= 512) {
         var2.value = mult512(this.value, var1.value);
         return var2;
      } else {
         int var3 = IntegerFunctions.floorLog(this.len - 1);
         var3 = bitMask[var3];
         GF2Polynomial var4 = this.lower((var3 - 1 >> 5) + 1);
         GF2Polynomial var5 = this.upper((var3 - 1 >> 5) + 1);
         GF2Polynomial var6 = var1.lower((var3 - 1 >> 5) + 1);
         GF2Polynomial var7 = var1.upper((var3 - 1 >> 5) + 1);
         GF2Polynomial var8 = var5.karaMult(var7);
         GF2Polynomial var9 = var4.karaMult(var6);
         var4.addToThis(var5);
         var6.addToThis(var7);
         GF2Polynomial var10 = var4.karaMult(var6);
         var2.shiftLeftAddThis(var8, var3 << 1);
         var2.shiftLeftAddThis(var8, var3);
         var2.shiftLeftAddThis(var10, var3);
         var2.shiftLeftAddThis(var9, var3);
         var2.addToThis(var9);
         return var2;
      }
   }

   private static int[] mult512(int[] var0, int[] var1) {
      int[] var2 = new int[32];
      int[] var3 = new int[8];
      System.arraycopy(var0, 0, var3, 0, Math.min(8, var0.length));
      int[] var4 = new int[8];
      if (var0.length > 8) {
         System.arraycopy(var0, 8, var4, 0, Math.min(8, var0.length - 8));
      }

      int[] var5 = new int[8];
      System.arraycopy(var1, 0, var5, 0, Math.min(8, var1.length));
      int[] var6 = new int[8];
      if (var1.length > 8) {
         System.arraycopy(var1, 8, var6, 0, Math.min(8, var1.length - 8));
      }

      int[] var7 = mult256(var4, var6);
      var2[31] ^= var7[15];
      var2[30] ^= var7[14];
      var2[29] ^= var7[13];
      var2[28] ^= var7[12];
      var2[27] ^= var7[11];
      var2[26] ^= var7[10];
      var2[25] ^= var7[9];
      var2[24] ^= var7[8];
      var2[23] ^= var7[7] ^ var7[15];
      var2[22] ^= var7[6] ^ var7[14];
      var2[21] ^= var7[5] ^ var7[13];
      var2[20] ^= var7[4] ^ var7[12];
      var2[19] ^= var7[3] ^ var7[11];
      var2[18] ^= var7[2] ^ var7[10];
      var2[17] ^= var7[1] ^ var7[9];
      var2[16] ^= var7[0] ^ var7[8];
      var2[15] ^= var7[7];
      var2[14] ^= var7[6];
      var2[13] ^= var7[5];
      var2[12] ^= var7[4];
      var2[11] ^= var7[3];
      var2[10] ^= var7[2];
      var2[9] ^= var7[1];
      var2[8] ^= var7[0];
      var4[0] ^= var3[0];
      var4[1] ^= var3[1];
      var4[2] ^= var3[2];
      var4[3] ^= var3[3];
      var4[4] ^= var3[4];
      var4[5] ^= var3[5];
      var4[6] ^= var3[6];
      var4[7] ^= var3[7];
      var6[0] ^= var5[0];
      var6[1] ^= var5[1];
      var6[2] ^= var5[2];
      var6[3] ^= var5[3];
      var6[4] ^= var5[4];
      var6[5] ^= var5[5];
      var6[6] ^= var5[6];
      var6[7] ^= var5[7];
      int[] var8 = mult256(var4, var6);
      var2[23] ^= var8[15];
      var2[22] ^= var8[14];
      var2[21] ^= var8[13];
      var2[20] ^= var8[12];
      var2[19] ^= var8[11];
      var2[18] ^= var8[10];
      var2[17] ^= var8[9];
      var2[16] ^= var8[8];
      var2[15] ^= var8[7];
      var2[14] ^= var8[6];
      var2[13] ^= var8[5];
      var2[12] ^= var8[4];
      var2[11] ^= var8[3];
      var2[10] ^= var8[2];
      var2[9] ^= var8[1];
      var2[8] ^= var8[0];
      int[] var9 = mult256(var3, var5);
      var2[23] ^= var9[15];
      var2[22] ^= var9[14];
      var2[21] ^= var9[13];
      var2[20] ^= var9[12];
      var2[19] ^= var9[11];
      var2[18] ^= var9[10];
      var2[17] ^= var9[9];
      var2[16] ^= var9[8];
      var2[15] ^= var9[7] ^ var9[15];
      var2[14] ^= var9[6] ^ var9[14];
      var2[13] ^= var9[5] ^ var9[13];
      var2[12] ^= var9[4] ^ var9[12];
      var2[11] ^= var9[3] ^ var9[11];
      var2[10] ^= var9[2] ^ var9[10];
      var2[9] ^= var9[1] ^ var9[9];
      var2[8] ^= var9[0] ^ var9[8];
      var2[7] ^= var9[7];
      var2[6] ^= var9[6];
      var2[5] ^= var9[5];
      var2[4] ^= var9[4];
      var2[3] ^= var9[3];
      var2[2] ^= var9[2];
      var2[1] ^= var9[1];
      var2[0] ^= var9[0];
      return var2;
   }

   private static int[] mult256(int[] var0, int[] var1) {
      int[] var2 = new int[16];
      int[] var3 = new int[4];
      System.arraycopy(var0, 0, var3, 0, Math.min(4, var0.length));
      int[] var4 = new int[4];
      if (var0.length > 4) {
         System.arraycopy(var0, 4, var4, 0, Math.min(4, var0.length - 4));
      }

      int[] var5 = new int[4];
      System.arraycopy(var1, 0, var5, 0, Math.min(4, var1.length));
      int[] var6 = new int[4];
      if (var1.length > 4) {
         System.arraycopy(var1, 4, var6, 0, Math.min(4, var1.length - 4));
      }

      int[] var7;
      if (var4[3] == 0 && var4[2] == 0 && var6[3] == 0 && var6[2] == 0) {
         if (var4[1] == 0 && var6[1] == 0) {
            if (var4[0] != 0 || var6[0] != 0) {
               var7 = mult32(var4[0], var6[0]);
               var2[9] ^= var7[1];
               var2[8] ^= var7[0];
               var2[5] ^= var7[1];
               var2[4] ^= var7[0];
            }
         } else {
            var7 = mult64(var4, var6);
            var2[11] ^= var7[3];
            var2[10] ^= var7[2];
            var2[9] ^= var7[1];
            var2[8] ^= var7[0];
            var2[7] ^= var7[3];
            var2[6] ^= var7[2];
            var2[5] ^= var7[1];
            var2[4] ^= var7[0];
         }
      } else {
         var7 = mult128(var4, var6);
         var2[15] ^= var7[7];
         var2[14] ^= var7[6];
         var2[13] ^= var7[5];
         var2[12] ^= var7[4];
         var2[11] ^= var7[3] ^ var7[7];
         var2[10] ^= var7[2] ^ var7[6];
         var2[9] ^= var7[1] ^ var7[5];
         var2[8] ^= var7[0] ^ var7[4];
         var2[7] ^= var7[3];
         var2[6] ^= var7[2];
         var2[5] ^= var7[1];
         var2[4] ^= var7[0];
      }

      var4[0] ^= var3[0];
      var4[1] ^= var3[1];
      var4[2] ^= var3[2];
      var4[3] ^= var3[3];
      var6[0] ^= var5[0];
      var6[1] ^= var5[1];
      var6[2] ^= var5[2];
      var6[3] ^= var5[3];
      var7 = mult128(var4, var6);
      var2[11] ^= var7[7];
      var2[10] ^= var7[6];
      var2[9] ^= var7[5];
      var2[8] ^= var7[4];
      var2[7] ^= var7[3];
      var2[6] ^= var7[2];
      var2[5] ^= var7[1];
      var2[4] ^= var7[0];
      int[] var8 = mult128(var3, var5);
      var2[11] ^= var8[7];
      var2[10] ^= var8[6];
      var2[9] ^= var8[5];
      var2[8] ^= var8[4];
      var2[7] ^= var8[3] ^ var8[7];
      var2[6] ^= var8[2] ^ var8[6];
      var2[5] ^= var8[1] ^ var8[5];
      var2[4] ^= var8[0] ^ var8[4];
      var2[3] ^= var8[3];
      var2[2] ^= var8[2];
      var2[1] ^= var8[1];
      var2[0] ^= var8[0];
      return var2;
   }

   private static int[] mult128(int[] var0, int[] var1) {
      int[] var2 = new int[8];
      int[] var3 = new int[2];
      System.arraycopy(var0, 0, var3, 0, Math.min(2, var0.length));
      int[] var4 = new int[2];
      if (var0.length > 2) {
         System.arraycopy(var0, 2, var4, 0, Math.min(2, var0.length - 2));
      }

      int[] var5 = new int[2];
      System.arraycopy(var1, 0, var5, 0, Math.min(2, var1.length));
      int[] var6 = new int[2];
      if (var1.length > 2) {
         System.arraycopy(var1, 2, var6, 0, Math.min(2, var1.length - 2));
      }

      int[] var7;
      if (var4[1] == 0 && var6[1] == 0) {
         if (var4[0] != 0 || var6[0] != 0) {
            var7 = mult32(var4[0], var6[0]);
            var2[5] ^= var7[1];
            var2[4] ^= var7[0];
            var2[3] ^= var7[1];
            var2[2] ^= var7[0];
         }
      } else {
         var7 = mult64(var4, var6);
         var2[7] ^= var7[3];
         var2[6] ^= var7[2];
         var2[5] ^= var7[1] ^ var7[3];
         var2[4] ^= var7[0] ^ var7[2];
         var2[3] ^= var7[1];
         var2[2] ^= var7[0];
      }

      var4[0] ^= var3[0];
      var4[1] ^= var3[1];
      var6[0] ^= var5[0];
      var6[1] ^= var5[1];
      if (var4[1] == 0 && var6[1] == 0) {
         var7 = mult32(var4[0], var6[0]);
         var2[3] ^= var7[1];
         var2[2] ^= var7[0];
      } else {
         var7 = mult64(var4, var6);
         var2[5] ^= var7[3];
         var2[4] ^= var7[2];
         var2[3] ^= var7[1];
         var2[2] ^= var7[0];
      }

      if (var3[1] == 0 && var5[1] == 0) {
         var7 = mult32(var3[0], var5[0]);
         var2[3] ^= var7[1];
         var2[2] ^= var7[0];
         var2[1] ^= var7[1];
         var2[0] ^= var7[0];
      } else {
         var7 = mult64(var3, var5);
         var2[5] ^= var7[3];
         var2[4] ^= var7[2];
         var2[3] ^= var7[1] ^ var7[3];
         var2[2] ^= var7[0] ^ var7[2];
         var2[1] ^= var7[1];
         var2[0] ^= var7[0];
      }

      return var2;
   }

   private static int[] mult64(int[] var0, int[] var1) {
      int[] var2 = new int[4];
      int var3 = var0[0];
      int var4 = 0;
      if (var0.length > 1) {
         var4 = var0[1];
      }

      int var5 = var1[0];
      int var6 = 0;
      if (var1.length > 1) {
         var6 = var1[1];
      }

      int[] var7;
      if (var4 != 0 || var6 != 0) {
         var7 = mult32(var4, var6);
         var2[3] ^= var7[1];
         var2[2] ^= var7[0] ^ var7[1];
         var2[1] ^= var7[0];
      }

      var7 = mult32(var3 ^ var4, var5 ^ var6);
      var2[2] ^= var7[1];
      var2[1] ^= var7[0];
      int[] var8 = mult32(var3, var5);
      var2[2] ^= var8[1];
      var2[1] ^= var8[0] ^ var8[1];
      var2[0] ^= var8[0];
      return var2;
   }

   private static int[] mult32(int var0, int var1) {
      int[] var2 = new int[2];
      if (var0 != 0 && var1 != 0) {
         long var3 = (long)var1;
         var3 &= 4294967295L;
         long var5 = 0L;

         for(int var7 = 1; var7 <= 32; ++var7) {
            if ((var0 & bitMask[var7 - 1]) != 0) {
               var5 ^= var3;
            }

            var3 <<= 1;
         }

         var2[1] = (int)(var5 >>> 32);
         var2[0] = (int)(var5 & 4294967295L);
         return var2;
      } else {
         return var2;
      }
   }

   private GF2Polynomial upper(int var1) {
      int var2 = Math.min(var1, this.blocks - var1);
      GF2Polynomial var3 = new GF2Polynomial(var2 << 5);
      if (this.blocks >= var1) {
         System.arraycopy(this.value, var1, var3.value, 0, var2);
      }

      return var3;
   }

   private GF2Polynomial lower(int var1) {
      GF2Polynomial var2 = new GF2Polynomial(var1 << 5);
      System.arraycopy(this.value, 0, var2.value, 0, Math.min(var1, this.blocks));
      return var2;
   }

   public GF2Polynomial remainder(GF2Polynomial var1) throws RuntimeException {
      GF2Polynomial var2 = new GF2Polynomial(this);
      GF2Polynomial var3 = new GF2Polynomial(var1);
      if (var3.isZero()) {
         throw new RuntimeException();
      } else {
         var2.reduceN();
         var3.reduceN();
         if (var2.len < var3.len) {
            return var2;
         } else {
            for(int var4 = var2.len - var3.len; var4 >= 0; var4 = var2.len - var3.len) {
               GF2Polynomial var5 = var3.shiftLeft(var4);
               var2.subtractFromThis(var5);
               var2.reduceN();
            }

            return var2;
         }
      }
   }

   public GF2Polynomial quotient(GF2Polynomial var1) throws RuntimeException {
      GF2Polynomial var2 = new GF2Polynomial(this.len);
      GF2Polynomial var3 = new GF2Polynomial(this);
      GF2Polynomial var4 = new GF2Polynomial(var1);
      if (var4.isZero()) {
         throw new RuntimeException();
      } else {
         var3.reduceN();
         var4.reduceN();
         if (var3.len < var4.len) {
            return new GF2Polynomial(0);
         } else {
            int var5 = var3.len - var4.len;
            var2.expandN(var5 + 1);

            while(var5 >= 0) {
               GF2Polynomial var6 = var4.shiftLeft(var5);
               var3.subtractFromThis(var6);
               var3.reduceN();
               var2.xorBit(var5);
               var5 = var3.len - var4.len;
            }

            return var2;
         }
      }
   }

   public GF2Polynomial[] divide(GF2Polynomial var1) throws RuntimeException {
      GF2Polynomial[] var2 = new GF2Polynomial[2];
      GF2Polynomial var3 = new GF2Polynomial(this.len);
      GF2Polynomial var4 = new GF2Polynomial(this);
      GF2Polynomial var5 = new GF2Polynomial(var1);
      if (var5.isZero()) {
         throw new RuntimeException();
      } else {
         var4.reduceN();
         var5.reduceN();
         if (var4.len < var5.len) {
            var2[0] = new GF2Polynomial(0);
            var2[1] = var4;
            return var2;
         } else {
            int var6 = var4.len - var5.len;
            var3.expandN(var6 + 1);

            while(var6 >= 0) {
               GF2Polynomial var7 = var5.shiftLeft(var6);
               var4.subtractFromThis(var7);
               var4.reduceN();
               var3.xorBit(var6);
               var6 = var4.len - var5.len;
            }

            var2[0] = var3;
            var2[1] = var4;
            return var2;
         }
      }
   }

   public GF2Polynomial gcd(GF2Polynomial var1) throws RuntimeException {
      if (this.isZero() && var1.isZero()) {
         throw new ArithmeticException("Both operands of gcd equal zero.");
      } else if (this.isZero()) {
         return new GF2Polynomial(var1);
      } else if (var1.isZero()) {
         return new GF2Polynomial(this);
      } else {
         GF2Polynomial var2 = new GF2Polynomial(this);

         GF2Polynomial var4;
         for(GF2Polynomial var3 = new GF2Polynomial(var1); !var3.isZero(); var3 = var4) {
            var4 = var2.remainder(var3);
            var2 = var3;
         }

         return var2;
      }
   }

   public boolean isIrreducible() {
      if (this.isZero()) {
         return false;
      } else {
         GF2Polynomial var1 = new GF2Polynomial(this);
         var1.reduceN();
         int var2 = var1.len - 1;
         GF2Polynomial var3 = new GF2Polynomial(var1.len, "X");

         for(int var4 = 1; var4 <= var2 >> 1; ++var4) {
            var3.squareThisPreCalc();
            var3 = var3.remainder(var1);
            GF2Polynomial var5 = var3.add(new GF2Polynomial(32, "X"));
            if (var5.isZero()) {
               return false;
            }

            GF2Polynomial var6 = var1.gcd(var5);
            if (!var6.isOne()) {
               return false;
            }
         }

         return true;
      }
   }

   void reduceTrinomial(int var1, int var2) {
      int var3 = var1 >>> 5;
      int var4 = 32 - (var1 & 31);
      int var5 = var1 - var2 >>> 5;
      int var6 = 32 - (var1 - var2 & 31);
      int var7 = (var1 << 1) - 2 >>> 5;
      int var8 = var3;

      int[] var10000;
      long var10;
      for(int var9 = var7; var9 > var8; --var9) {
         var10 = (long)this.value[var9] & 4294967295L;
         var10000 = this.value;
         var10000[var9 - var3 - 1] ^= (int)(var10 << var4);
         var10000 = this.value;
         var10000[var9 - var3] = (int)((long)var10000[var9 - var3] ^ var10 >>> 32 - var4);
         var10000 = this.value;
         var10000[var9 - var5 - 1] ^= (int)(var10 << var6);
         var10000 = this.value;
         var10000[var9 - var5] = (int)((long)var10000[var9 - var5] ^ var10 >>> 32 - var6);
         this.value[var9] = 0;
      }

      var10 = (long)this.value[var8] & 4294967295L & 4294967295L << (var1 & 31);
      var10000 = this.value;
      var10000[0] = (int)((long)var10000[0] ^ var10 >>> 32 - var4);
      if (var8 - var5 - 1 >= 0) {
         var10000 = this.value;
         var10000[var8 - var5 - 1] ^= (int)(var10 << var6);
      }

      var10000 = this.value;
      var10000[var8 - var5] = (int)((long)var10000[var8 - var5] ^ var10 >>> 32 - var6);
      var10000 = this.value;
      var10000[var8] &= reverseRightMask[var1 & 31];
      this.blocks = (var1 - 1 >>> 5) + 1;
      this.len = var1;
   }

   void reducePentanomial(int var1, int[] var2) {
      int var3 = var1 >>> 5;
      int var4 = 32 - (var1 & 31);
      int var5 = var1 - var2[0] >>> 5;
      int var6 = 32 - (var1 - var2[0] & 31);
      int var7 = var1 - var2[1] >>> 5;
      int var8 = 32 - (var1 - var2[1] & 31);
      int var9 = var1 - var2[2] >>> 5;
      int var10 = 32 - (var1 - var2[2] & 31);
      int var11 = (var1 << 1) - 2 >>> 5;
      int var12 = var3;

      long var14;
      int[] var10000;
      for(int var13 = var11; var13 > var12; --var13) {
         var14 = (long)this.value[var13] & 4294967295L;
         var10000 = this.value;
         var10000[var13 - var3 - 1] ^= (int)(var14 << var4);
         var10000 = this.value;
         var10000[var13 - var3] = (int)((long)var10000[var13 - var3] ^ var14 >>> 32 - var4);
         var10000 = this.value;
         var10000[var13 - var5 - 1] ^= (int)(var14 << var6);
         var10000 = this.value;
         var10000[var13 - var5] = (int)((long)var10000[var13 - var5] ^ var14 >>> 32 - var6);
         var10000 = this.value;
         var10000[var13 - var7 - 1] ^= (int)(var14 << var8);
         var10000 = this.value;
         var10000[var13 - var7] = (int)((long)var10000[var13 - var7] ^ var14 >>> 32 - var8);
         var10000 = this.value;
         var10000[var13 - var9 - 1] ^= (int)(var14 << var10);
         var10000 = this.value;
         var10000[var13 - var9] = (int)((long)var10000[var13 - var9] ^ var14 >>> 32 - var10);
         this.value[var13] = 0;
      }

      var14 = (long)this.value[var12] & 4294967295L & 4294967295L << (var1 & 31);
      var10000 = this.value;
      var10000[0] = (int)((long)var10000[0] ^ var14 >>> 32 - var4);
      if (var12 - var5 - 1 >= 0) {
         var10000 = this.value;
         var10000[var12 - var5 - 1] ^= (int)(var14 << var6);
      }

      var10000 = this.value;
      var10000[var12 - var5] = (int)((long)var10000[var12 - var5] ^ var14 >>> 32 - var6);
      if (var12 - var7 - 1 >= 0) {
         var10000 = this.value;
         var10000[var12 - var7 - 1] ^= (int)(var14 << var8);
      }

      var10000 = this.value;
      var10000[var12 - var7] = (int)((long)var10000[var12 - var7] ^ var14 >>> 32 - var8);
      if (var12 - var9 - 1 >= 0) {
         var10000 = this.value;
         var10000[var12 - var9 - 1] ^= (int)(var14 << var10);
      }

      var10000 = this.value;
      var10000[var12 - var9] = (int)((long)var10000[var12 - var9] ^ var14 >>> 32 - var10);
      var10000 = this.value;
      var10000[var12] &= reverseRightMask[var1 & 31];
      this.blocks = (var1 - 1 >>> 5) + 1;
      this.len = var1;
   }

   public void reduceN() {
      int var1;
      for(var1 = this.blocks - 1; this.value[var1] == 0 && var1 > 0; --var1) {
      }

      int var2 = this.value[var1];

      int var3;
      for(var3 = 0; var2 != 0; ++var3) {
         var2 >>>= 1;
      }

      this.len = (var1 << 5) + var3;
      this.blocks = var1 + 1;
   }

   public void expandN(int var1) {
      if (this.len < var1) {
         this.len = var1;
         int var2 = (var1 - 1 >>> 5) + 1;
         if (this.blocks < var2) {
            if (this.value.length < var2) {
               int[] var4 = new int[var2];
               System.arraycopy(this.value, 0, var4, 0, this.blocks);
               this.blocks = var2;
               this.value = null;
               this.value = var4;
            } else {
               for(int var3 = this.blocks; var3 < var2; ++var3) {
                  this.value[var3] = 0;
               }

               this.blocks = var2;
            }
         }
      }
   }

   public void squareThisBitwise() {
      if (!this.isZero()) {
         int[] var1 = new int[this.blocks << 1];

         for(int var2 = this.blocks - 1; var2 >= 0; --var2) {
            int var3 = this.value[var2];
            int var4 = 1;

            for(int var5 = 0; var5 < 16; ++var5) {
               if ((var3 & 1) != 0) {
                  var1[var2 << 1] |= var4;
               }

               if ((var3 & 65536) != 0) {
                  var1[(var2 << 1) + 1] |= var4;
               }

               var4 <<= 2;
               var3 >>>= 1;
            }
         }

         this.value = null;
         this.value = var1;
         this.blocks = var1.length;
         this.len = (this.len << 1) - 1;
      }
   }

   public void squareThisPreCalc() {
      if (!this.isZero()) {
         int var1;
         if (this.value.length >= this.blocks << 1) {
            for(var1 = this.blocks - 1; var1 >= 0; --var1) {
               this.value[(var1 << 1) + 1] = squaringTable[(this.value[var1] & 16711680) >>> 16] | squaringTable[(this.value[var1] & -16777216) >>> 24] << 16;
               this.value[var1 << 1] = squaringTable[this.value[var1] & 255] | squaringTable[(this.value[var1] & '\uff00') >>> 8] << 16;
            }

            this.blocks <<= 1;
            this.len = (this.len << 1) - 1;
         } else {
            int[] var2 = new int[this.blocks << 1];

            for(var1 = 0; var1 < this.blocks; ++var1) {
               var2[var1 << 1] = squaringTable[this.value[var1] & 255] | squaringTable[(this.value[var1] & '\uff00') >>> 8] << 16;
               var2[(var1 << 1) + 1] = squaringTable[(this.value[var1] & 16711680) >>> 16] | squaringTable[(this.value[var1] & -16777216) >>> 24] << 16;
            }

            this.value = null;
            this.value = var2;
            this.blocks <<= 1;
            this.len = (this.len << 1) - 1;
         }

      }
   }

   public boolean vectorMult(GF2Polynomial var1) throws RuntimeException {
      boolean var2 = false;
      if (this.len != var1.len) {
         throw new RuntimeException();
      } else {
         for(int var3 = 0; var3 < this.blocks; ++var3) {
            int var4 = this.value[var3] & var1.value[var3];
            var2 ^= parity[var4 & 255];
            var2 ^= parity[var4 >>> 8 & 255];
            var2 ^= parity[var4 >>> 16 & 255];
            var2 ^= parity[var4 >>> 24 & 255];
         }

         return var2;
      }
   }

   public GF2Polynomial xor(GF2Polynomial var1) {
      int var2 = Math.min(this.blocks, var1.blocks);
      int[] var10000;
      GF2Polynomial var3;
      int var4;
      if (this.len >= var1.len) {
         var3 = new GF2Polynomial(this);

         for(var4 = 0; var4 < var2; ++var4) {
            var10000 = var3.value;
            var10000[var4] ^= var1.value[var4];
         }
      } else {
         var3 = new GF2Polynomial(var1);

         for(var4 = 0; var4 < var2; ++var4) {
            var10000 = var3.value;
            var10000[var4] ^= this.value[var4];
         }
      }

      var3.zeroUnusedBits();
      return var3;
   }

   public void xorThisBy(GF2Polynomial var1) {
      for(int var2 = 0; var2 < Math.min(this.blocks, var1.blocks); ++var2) {
         int[] var10000 = this.value;
         var10000[var2] ^= var1.value[var2];
      }

      this.zeroUnusedBits();
   }

   private void zeroUnusedBits() {
      if ((this.len & 31) != 0) {
         int[] var10000 = this.value;
         int var10001 = this.blocks - 1;
         var10000[var10001] &= reverseRightMask[this.len & 31];
      }

   }

   public void setBit(int var1) throws RuntimeException {
      if (var1 >= 0 && var1 <= this.len - 1) {
         int[] var10000 = this.value;
         var10000[var1 >>> 5] |= bitMask[var1 & 31];
      } else {
         throw new RuntimeException();
      }
   }

   public int getBit(int var1) {
      if (var1 < 0) {
         throw new RuntimeException();
      } else if (var1 > this.len - 1) {
         return 0;
      } else {
         return (this.value[var1 >>> 5] & bitMask[var1 & 31]) != 0 ? 1 : 0;
      }
   }

   public void resetBit(int var1) throws RuntimeException {
      if (var1 < 0) {
         throw new RuntimeException();
      } else if (var1 <= this.len - 1) {
         int[] var10000 = this.value;
         var10000[var1 >>> 5] &= ~bitMask[var1 & 31];
      }
   }

   public void xorBit(int var1) throws RuntimeException {
      if (var1 >= 0 && var1 <= this.len - 1) {
         int[] var10000 = this.value;
         var10000[var1 >>> 5] ^= bitMask[var1 & 31];
      } else {
         throw new RuntimeException();
      }
   }

   public boolean testBit(int var1) {
      if (var1 < 0) {
         throw new RuntimeException();
      } else if (var1 > this.len - 1) {
         return false;
      } else {
         return (this.value[var1 >>> 5] & bitMask[var1 & 31]) != 0;
      }
   }

   public GF2Polynomial shiftLeft() {
      GF2Polynomial var1 = new GF2Polynomial(this.len + 1, this.value);

      int[] var10000;
      for(int var2 = var1.blocks - 1; var2 >= 1; --var2) {
         var10000 = var1.value;
         var10000[var2] <<= 1;
         var10000 = var1.value;
         var10000[var2] |= var1.value[var2 - 1] >>> 31;
      }

      var10000 = var1.value;
      var10000[0] <<= 1;
      return var1;
   }

   public void shiftLeftThis() {
      int[] var10000;
      int var2;
      if ((this.len & 31) == 0) {
         ++this.len;
         ++this.blocks;
         if (this.blocks > this.value.length) {
            int[] var1 = new int[this.blocks];
            System.arraycopy(this.value, 0, var1, 0, this.value.length);
            this.value = null;
            this.value = var1;
         }

         for(var2 = this.blocks - 1; var2 >= 1; --var2) {
            var10000 = this.value;
            var10000[var2] |= this.value[var2 - 1] >>> 31;
            var10000 = this.value;
            var10000[var2 - 1] <<= 1;
         }
      } else {
         ++this.len;

         for(var2 = this.blocks - 1; var2 >= 1; --var2) {
            var10000 = this.value;
            var10000[var2] <<= 1;
            var10000 = this.value;
            var10000[var2] |= this.value[var2 - 1] >>> 31;
         }

         var10000 = this.value;
         var10000[0] <<= 1;
      }

   }

   public GF2Polynomial shiftLeft(int var1) {
      GF2Polynomial var2 = new GF2Polynomial(this.len + var1, this.value);
      if (var1 >= 32) {
         var2.doShiftBlocksLeft(var1 >>> 5);
      }

      int var3 = var1 & 31;
      if (var3 != 0) {
         int[] var10000;
         for(int var4 = var2.blocks - 1; var4 >= 1; --var4) {
            var10000 = var2.value;
            var10000[var4] <<= var3;
            var10000 = var2.value;
            var10000[var4] |= var2.value[var4 - 1] >>> 32 - var3;
         }

         var10000 = var2.value;
         var10000[0] <<= var3;
      }

      return var2;
   }

   public void shiftLeftAddThis(GF2Polynomial var1, int var2) {
      if (var2 == 0) {
         this.addToThis(var1);
      } else {
         this.expandN(var1.len + var2);
         int var3 = var2 >>> 5;

         for(int var4 = var1.blocks - 1; var4 >= 0; --var4) {
            int[] var10000;
            if (var4 + var3 + 1 < this.blocks && (var2 & 31) != 0) {
               var10000 = this.value;
               var10000[var4 + var3 + 1] ^= var1.value[var4] >>> 32 - (var2 & 31);
            }

            var10000 = this.value;
            var10000[var4 + var3] ^= var1.value[var4] << (var2 & 31);
         }

      }
   }

   void shiftBlocksLeft() {
      ++this.blocks;
      this.len += 32;
      if (this.blocks <= this.value.length) {
         for(int var1 = this.blocks - 1; var1 >= 1; --var1) {
            this.value[var1] = this.value[var1 - 1];
         }

         this.value[0] = 0;
      } else {
         int[] var2 = new int[this.blocks];
         System.arraycopy(this.value, 0, var2, 1, this.blocks - 1);
         this.value = null;
         this.value = var2;
      }

   }

   private void doShiftBlocksLeft(int var1) {
      if (this.blocks <= this.value.length) {
         int var2;
         for(var2 = this.blocks - 1; var2 >= var1; --var2) {
            this.value[var2] = this.value[var2 - var1];
         }

         for(var2 = 0; var2 < var1; ++var2) {
            this.value[var2] = 0;
         }
      } else {
         int[] var3 = new int[this.blocks];
         System.arraycopy(this.value, 0, var3, var1, this.blocks - var1);
         this.value = null;
         this.value = var3;
      }

   }

   public GF2Polynomial shiftRight() {
      GF2Polynomial var1 = new GF2Polynomial(this.len - 1);
      System.arraycopy(this.value, 0, var1.value, 0, var1.blocks);

      int[] var10000;
      for(int var2 = 0; var2 <= var1.blocks - 2; ++var2) {
         var10000 = var1.value;
         var10000[var2] >>>= 1;
         var10000 = var1.value;
         var10000[var2] |= var1.value[var2 + 1] << 31;
      }

      var10000 = var1.value;
      int var10001 = var1.blocks - 1;
      var10000[var10001] >>>= 1;
      if (var1.blocks < this.blocks) {
         var10000 = var1.value;
         var10001 = var1.blocks - 1;
         var10000[var10001] |= this.value[var1.blocks] << 31;
      }

      return var1;
   }

   public void shiftRightThis() {
      --this.len;
      this.blocks = (this.len - 1 >>> 5) + 1;

      int[] var10000;
      for(int var1 = 0; var1 <= this.blocks - 2; ++var1) {
         var10000 = this.value;
         var10000[var1] >>>= 1;
         var10000 = this.value;
         var10000[var1] |= this.value[var1 + 1] << 31;
      }

      var10000 = this.value;
      int var10001 = this.blocks - 1;
      var10000[var10001] >>>= 1;
      if ((this.len & 31) == 0) {
         var10000 = this.value;
         var10001 = this.blocks - 1;
         var10000[var10001] |= this.value[this.blocks] << 31;
      }

   }
}
