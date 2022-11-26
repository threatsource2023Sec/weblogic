package org.python.icu.impl.coll;

import org.python.icu.util.ByteArrayWrapper;

public class BOCSU {
   private static final int SLOPE_MIN_ = 3;
   private static final int SLOPE_MAX_ = 255;
   private static final int SLOPE_MIDDLE_ = 129;
   private static final int SLOPE_TAIL_COUNT_ = 253;
   private static final int SLOPE_MAX_BYTES_ = 4;
   private static final int SLOPE_SINGLE_ = 80;
   private static final int SLOPE_LEAD_2_ = 42;
   private static final int SLOPE_LEAD_3_ = 3;
   private static final int SLOPE_REACH_POS_1_ = 80;
   private static final int SLOPE_REACH_NEG_1_ = -80;
   private static final int SLOPE_REACH_POS_2_ = 10667;
   private static final int SLOPE_REACH_NEG_2_ = -10668;
   private static final int SLOPE_REACH_POS_3_ = 192785;
   private static final int SLOPE_REACH_NEG_3_ = -192786;
   private static final int SLOPE_START_POS_2_ = 210;
   private static final int SLOPE_START_POS_3_ = 252;
   private static final int SLOPE_START_NEG_2_ = 49;
   private static final int SLOPE_START_NEG_3_ = 7;

   public static int writeIdenticalLevelRun(int prev, CharSequence s, int i, int length, ByteArrayWrapper sink) {
      while(i < length) {
         ensureAppendCapacity(sink, 16, s.length() * 2);
         byte[] buffer = sink.bytes;
         int capacity = buffer.length;
         int p = sink.size;
         int lastSafe = capacity - 4;

         while(i < length && p <= lastSafe) {
            if (prev >= 19968 && prev < 40960) {
               prev = 30292;
            } else {
               prev = (prev & -128) - -80;
            }

            int c = Character.codePointAt(s, i);
            i += Character.charCount(c);
            if (c == 65534) {
               buffer[p++] = 2;
               prev = 0;
            } else {
               p = writeDiff(c - prev, buffer, p);
               prev = c;
            }
         }

         sink.size = p;
      }

      return prev;
   }

   private static void ensureAppendCapacity(ByteArrayWrapper sink, int minCapacity, int desiredCapacity) {
      int remainingCapacity = sink.bytes.length - sink.size;
      if (remainingCapacity < minCapacity) {
         if (desiredCapacity < minCapacity) {
            desiredCapacity = minCapacity;
         }

         sink.ensureCapacity(sink.size + desiredCapacity);
      }
   }

   private BOCSU() {
   }

   private static final long getNegDivMod(int number, int factor) {
      int modulo = number % factor;
      long result = (long)(number / factor);
      if (modulo < 0) {
         --result;
         modulo += factor;
      }

      return result << 32 | (long)modulo;
   }

   private static final int writeDiff(int diff, byte[] buffer, int offset) {
      if (diff >= -80) {
         if (diff <= 80) {
            buffer[offset++] = (byte)(129 + diff);
         } else if (diff <= 10667) {
            buffer[offset++] = (byte)(210 + diff / 253);
            buffer[offset++] = (byte)(3 + diff % 253);
         } else if (diff <= 192785) {
            buffer[offset + 2] = (byte)(3 + diff % 253);
            diff /= 253;
            buffer[offset + 1] = (byte)(3 + diff % 253);
            buffer[offset] = (byte)(252 + diff / 253);
            offset += 3;
         } else {
            buffer[offset + 3] = (byte)(3 + diff % 253);
            diff /= 253;
            buffer[offset + 2] = (byte)(3 + diff % 253);
            diff /= 253;
            buffer[offset + 1] = (byte)(3 + diff % 253);
            buffer[offset] = -1;
            offset += 4;
         }
      } else {
         long division = getNegDivMod(diff, 253);
         int modulo = (int)division;
         if (diff >= -10668) {
            diff = (int)(division >> 32);
            buffer[offset++] = (byte)(49 + diff);
            buffer[offset++] = (byte)(3 + modulo);
         } else if (diff >= -192786) {
            buffer[offset + 2] = (byte)(3 + modulo);
            diff = (int)(division >> 32);
            division = getNegDivMod(diff, 253);
            modulo = (int)division;
            diff = (int)(division >> 32);
            buffer[offset + 1] = (byte)(3 + modulo);
            buffer[offset] = (byte)(7 + diff);
            offset += 3;
         } else {
            buffer[offset + 3] = (byte)(3 + modulo);
            diff = (int)(division >> 32);
            division = getNegDivMod(diff, 253);
            modulo = (int)division;
            diff = (int)(division >> 32);
            buffer[offset + 2] = (byte)(3 + modulo);
            division = getNegDivMod(diff, 253);
            modulo = (int)division;
            buffer[offset + 1] = (byte)(3 + modulo);
            buffer[offset] = 3;
            offset += 4;
         }
      }

      return offset;
   }
}
