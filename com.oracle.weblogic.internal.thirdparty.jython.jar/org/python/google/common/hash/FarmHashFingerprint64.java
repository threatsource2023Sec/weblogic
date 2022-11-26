package org.python.google.common.hash;

import org.python.google.common.annotations.VisibleForTesting;
import org.python.google.common.base.Preconditions;

final class FarmHashFingerprint64 extends AbstractNonStreamingHashFunction {
   private static final long K0 = -4348849565147123417L;
   private static final long K1 = -5435081209227447693L;
   private static final long K2 = -7286425919675154353L;

   public HashCode hashBytes(byte[] input, int off, int len) {
      Preconditions.checkPositionIndexes(off, off + len, input.length);
      return HashCode.fromLong(fingerprint(input, off, len));
   }

   public int bits() {
      return 64;
   }

   public String toString() {
      return "Hashing.farmHashFingerprint64()";
   }

   @VisibleForTesting
   static long fingerprint(byte[] bytes, int offset, int length) {
      if (length <= 32) {
         return length <= 16 ? hashLength0to16(bytes, offset, length) : hashLength17to32(bytes, offset, length);
      } else {
         return length <= 64 ? hashLength33To64(bytes, offset, length) : hashLength65Plus(bytes, offset, length);
      }
   }

   private static long shiftMix(long val) {
      return val ^ val >>> 47;
   }

   private static long hashLength16(long u, long v, long mul) {
      long a = (u ^ v) * mul;
      a ^= a >>> 47;
      long b = (v ^ a) * mul;
      b ^= b >>> 47;
      b *= mul;
      return b;
   }

   private static void weakHashLength32WithSeeds(byte[] bytes, int offset, long seedA, long seedB, long[] output) {
      long part1 = LittleEndianByteArray.load64(bytes, offset);
      long part2 = LittleEndianByteArray.load64(bytes, offset + 8);
      long part3 = LittleEndianByteArray.load64(bytes, offset + 16);
      long part4 = LittleEndianByteArray.load64(bytes, offset + 24);
      seedA += part1;
      seedB = Long.rotateRight(seedB + seedA + part4, 21);
      long c = seedA;
      seedA += part2;
      seedA += part3;
      seedB += Long.rotateRight(seedA, 44);
      output[0] = seedA + part4;
      output[1] = seedB + c;
   }

   private static long hashLength0to16(byte[] bytes, int offset, int length) {
      long mul;
      long a;
      if (length >= 8) {
         mul = -7286425919675154353L + (long)(length * 2);
         a = LittleEndianByteArray.load64(bytes, offset) + -7286425919675154353L;
         long b = LittleEndianByteArray.load64(bytes, offset + length - 8);
         long c = Long.rotateRight(b, 37) * mul + a;
         long d = (Long.rotateRight(a, 25) + b) * mul;
         return hashLength16(c, d, mul);
      } else if (length >= 4) {
         mul = -7286425919675154353L + (long)(length * 2);
         a = (long)LittleEndianByteArray.load32(bytes, offset) & 4294967295L;
         return hashLength16((long)length + (a << 3), (long)LittleEndianByteArray.load32(bytes, offset + length - 4) & 4294967295L, mul);
      } else if (length > 0) {
         byte a = bytes[offset];
         byte b = bytes[offset + (length >> 1)];
         byte c = bytes[offset + (length - 1)];
         int y = (a & 255) + ((b & 255) << 8);
         int z = length + ((c & 255) << 2);
         return shiftMix((long)y * -7286425919675154353L ^ (long)z * -4348849565147123417L) * -7286425919675154353L;
      } else {
         return -7286425919675154353L;
      }
   }

   private static long hashLength17to32(byte[] bytes, int offset, int length) {
      long mul = -7286425919675154353L + (long)(length * 2);
      long a = LittleEndianByteArray.load64(bytes, offset) * -5435081209227447693L;
      long b = LittleEndianByteArray.load64(bytes, offset + 8);
      long c = LittleEndianByteArray.load64(bytes, offset + length - 8) * mul;
      long d = LittleEndianByteArray.load64(bytes, offset + length - 16) * -7286425919675154353L;
      return hashLength16(Long.rotateRight(a + b, 43) + Long.rotateRight(c, 30) + d, a + Long.rotateRight(b + -7286425919675154353L, 18) + c, mul);
   }

   private static long hashLength33To64(byte[] bytes, int offset, int length) {
      long mul = -7286425919675154353L + (long)(length * 2);
      long a = LittleEndianByteArray.load64(bytes, offset) * -7286425919675154353L;
      long b = LittleEndianByteArray.load64(bytes, offset + 8);
      long c = LittleEndianByteArray.load64(bytes, offset + length - 8) * mul;
      long d = LittleEndianByteArray.load64(bytes, offset + length - 16) * -7286425919675154353L;
      long y = Long.rotateRight(a + b, 43) + Long.rotateRight(c, 30) + d;
      long z = hashLength16(y, a + Long.rotateRight(b + -7286425919675154353L, 18) + c, mul);
      long e = LittleEndianByteArray.load64(bytes, offset + 16) * mul;
      long f = LittleEndianByteArray.load64(bytes, offset + 24);
      long g = (y + LittleEndianByteArray.load64(bytes, offset + length - 32)) * mul;
      long h = (z + LittleEndianByteArray.load64(bytes, offset + length - 24)) * mul;
      return hashLength16(Long.rotateRight(e + f, 43) + Long.rotateRight(g, 30) + h, e + Long.rotateRight(f + a, 18) + g, mul);
   }

   private static long hashLength65Plus(byte[] bytes, int offset, int length) {
      int seed = true;
      long x = 81L;
      long y = 2480279821605975764L;
      long z = shiftMix(y * -7286425919675154353L + 113L) * -7286425919675154353L;
      long[] v = new long[2];
      long[] w = new long[2];
      x = x * -7286425919675154353L + LittleEndianByteArray.load64(bytes, offset);
      int end = offset + (length - 1) / 64 * 64;
      int last64offset = end + (length - 1 & 63) - 63;

      long mul;
      do {
         x = Long.rotateRight(x + y + v[0] + LittleEndianByteArray.load64(bytes, offset + 8), 37) * -5435081209227447693L;
         y = Long.rotateRight(y + v[1] + LittleEndianByteArray.load64(bytes, offset + 48), 42) * -5435081209227447693L;
         x ^= w[1];
         y += v[0] + LittleEndianByteArray.load64(bytes, offset + 40);
         z = Long.rotateRight(z + w[0], 33) * -5435081209227447693L;
         weakHashLength32WithSeeds(bytes, offset, v[1] * -5435081209227447693L, x + w[0], v);
         weakHashLength32WithSeeds(bytes, offset + 32, z + w[1], y + LittleEndianByteArray.load64(bytes, offset + 16), w);
         mul = x;
         x = z;
         z = mul;
         offset += 64;
      } while(offset != end);

      mul = -5435081209227447693L + ((mul & 255L) << 1);
      w[0] += (long)(length - 1 & 63);
      v[0] += w[0];
      w[0] += v[0];
      x = Long.rotateRight(x + y + v[0] + LittleEndianByteArray.load64(bytes, last64offset + 8), 37) * mul;
      y = Long.rotateRight(y + v[1] + LittleEndianByteArray.load64(bytes, last64offset + 48), 42) * mul;
      x ^= w[1] * 9L;
      y += v[0] * 9L + LittleEndianByteArray.load64(bytes, last64offset + 40);
      z = Long.rotateRight(z + w[0], 33) * mul;
      weakHashLength32WithSeeds(bytes, last64offset, v[1] * mul, x + w[0], v);
      weakHashLength32WithSeeds(bytes, last64offset + 32, z + w[1], y + LittleEndianByteArray.load64(bytes, last64offset + 16), w);
      return hashLength16(hashLength16(v[0], w[0], mul) + shiftMix(y) * -4348849565147123417L + x, hashLength16(v[1], w[1], mul) + z, mul);
   }
}
