package org.python.icu.impl.coll;

import java.util.Arrays;

public final class CollationSettings extends SharedObject {
   public static final int CHECK_FCD = 1;
   public static final int NUMERIC = 2;
   static final int SHIFTED = 4;
   static final int ALTERNATE_MASK = 12;
   static final int MAX_VARIABLE_SHIFT = 4;
   static final int MAX_VARIABLE_MASK = 112;
   static final int UPPER_FIRST = 256;
   public static final int CASE_FIRST = 512;
   public static final int CASE_FIRST_AND_UPPER_MASK = 768;
   public static final int CASE_LEVEL = 1024;
   public static final int BACKWARD_SECONDARY = 2048;
   static final int STRENGTH_SHIFT = 12;
   static final int STRENGTH_MASK = 61440;
   static final int MAX_VAR_SPACE = 0;
   static final int MAX_VAR_PUNCT = 1;
   static final int MAX_VAR_SYMBOL = 2;
   static final int MAX_VAR_CURRENCY = 3;
   public int options = 8208;
   public long variableTop;
   public byte[] reorderTable;
   long minHighNoReorder;
   long[] reorderRanges;
   public int[] reorderCodes;
   private static final int[] EMPTY_INT_ARRAY = new int[0];
   public int fastLatinOptions;
   public char[] fastLatinPrimaries;

   CollationSettings() {
      this.reorderCodes = EMPTY_INT_ARRAY;
      this.fastLatinOptions = -1;
      this.fastLatinPrimaries = new char[384];
   }

   public CollationSettings clone() {
      CollationSettings newSettings = (CollationSettings)super.clone();
      newSettings.fastLatinPrimaries = (char[])this.fastLatinPrimaries.clone();
      return newSettings;
   }

   public boolean equals(Object other) {
      if (other == null) {
         return false;
      } else if (!this.getClass().equals(other.getClass())) {
         return false;
      } else {
         CollationSettings o = (CollationSettings)other;
         if (this.options != o.options) {
            return false;
         } else if ((this.options & 12) != 0 && this.variableTop != o.variableTop) {
            return false;
         } else {
            return Arrays.equals(this.reorderCodes, o.reorderCodes);
         }
      }
   }

   public int hashCode() {
      int h = this.options << 8;
      if ((this.options & 12) != 0) {
         h = (int)((long)h ^ this.variableTop);
      }

      h ^= this.reorderCodes.length;

      for(int i = 0; i < this.reorderCodes.length; ++i) {
         h ^= this.reorderCodes[i] << i;
      }

      return h;
   }

   public void resetReordering() {
      this.reorderTable = null;
      this.minHighNoReorder = 0L;
      this.reorderRanges = null;
      this.reorderCodes = EMPTY_INT_ARRAY;
   }

   void aliasReordering(CollationData data, int[] codesAndRanges, int codesLength, byte[] table) {
      int[] codes;
      if (codesLength == codesAndRanges.length) {
         codes = codesAndRanges;
      } else {
         codes = new int[codesLength];
         System.arraycopy(codesAndRanges, 0, codes, 0, codesLength);
      }

      int rangesLimit = codesAndRanges.length;
      int rangesLength = rangesLimit - codesLength;
      if (table != null) {
         label64: {
            if (rangesLength == 0) {
               if (reorderTableHasSplitBytes(table)) {
                  break label64;
               }
            } else if (rangesLength < 2 || (codesAndRanges[codesLength] & '\uffff') != 0 || (codesAndRanges[rangesLimit - 1] & '\uffff') == 0) {
               break label64;
            }

            this.reorderTable = table;
            this.reorderCodes = codes;

            int firstSplitByteRangeIndex;
            for(firstSplitByteRangeIndex = codesLength; firstSplitByteRangeIndex < rangesLimit && (codesAndRanges[firstSplitByteRangeIndex] & 16711680) == 0; ++firstSplitByteRangeIndex) {
            }

            if (firstSplitByteRangeIndex == rangesLimit) {
               assert !reorderTableHasSplitBytes(table);

               this.minHighNoReorder = 0L;
               this.reorderRanges = null;
            } else {
               assert table[codesAndRanges[firstSplitByteRangeIndex] >>> 24] == 0;

               this.minHighNoReorder = (long)codesAndRanges[rangesLimit - 1] & 4294901760L;
               this.setReorderRanges(codesAndRanges, firstSplitByteRangeIndex, rangesLimit - firstSplitByteRangeIndex);
            }

            return;
         }
      }

      this.setReordering(data, codes);
   }

   public void setReordering(CollationData data, int[] codes) {
      if (codes.length == 0 || codes.length == 1 && codes[0] == 103) {
         this.resetReordering();
      } else {
         UVector32 rangesList = new UVector32();
         data.makeReorderRanges(codes, rangesList);
         int rangesLength = rangesList.size();
         if (rangesLength == 0) {
            this.resetReordering();
         } else {
            int[] ranges = rangesList.getBuffer();

            assert rangesLength >= 2;

            assert (ranges[0] & '\uffff') == 0 && (ranges[rangesLength - 1] & '\uffff') != 0;

            this.minHighNoReorder = (long)ranges[rangesLength - 1] & 4294901760L;
            byte[] table = new byte[256];
            int b = 0;
            int firstSplitByteRangeIndex = -1;

            int i;
            for(i = 0; i < rangesLength; ++i) {
               int pair = ranges[i];

               int limit1;
               for(limit1 = pair >>> 24; b < limit1; ++b) {
                  table[b] = (byte)(b + pair);
               }

               if ((pair & 16711680) != 0) {
                  table[limit1] = 0;
                  b = limit1 + 1;
                  if (firstSplitByteRangeIndex < 0) {
                     firstSplitByteRangeIndex = i;
                  }
               }
            }

            while(b <= 255) {
               table[b] = (byte)b;
               ++b;
            }

            if (firstSplitByteRangeIndex < 0) {
               rangesLength = 0;
               i = 0;
            } else {
               i = firstSplitByteRangeIndex;
               rangesLength -= firstSplitByteRangeIndex;
            }

            this.setReorderArrays(codes, ranges, i, rangesLength, table);
         }
      }
   }

   private void setReorderArrays(int[] codes, int[] ranges, int rangesStart, int rangesLength, byte[] table) {
      if (codes == null) {
         codes = EMPTY_INT_ARRAY;
      }

      assert codes.length == 0 == (table == null);

      this.reorderTable = table;
      this.reorderCodes = codes;
      this.setReorderRanges(ranges, rangesStart, rangesLength);
   }

   private void setReorderRanges(int[] ranges, int rangesStart, int rangesLength) {
      if (rangesLength == 0) {
         this.reorderRanges = null;
      } else {
         this.reorderRanges = new long[rangesLength];
         int i = 0;

         do {
            this.reorderRanges[i++] = (long)ranges[rangesStart++] & 4294967295L;
         } while(i < rangesLength);
      }

   }

   public void copyReorderingFrom(CollationSettings other) {
      if (!other.hasReordering()) {
         this.resetReordering();
      } else {
         this.minHighNoReorder = other.minHighNoReorder;
         this.reorderTable = other.reorderTable;
         this.reorderRanges = other.reorderRanges;
         this.reorderCodes = other.reorderCodes;
      }
   }

   public boolean hasReordering() {
      return this.reorderTable != null;
   }

   private static boolean reorderTableHasSplitBytes(byte[] table) {
      assert table[0] == 0;

      for(int i = 1; i < 256; ++i) {
         if (table[i] == 0) {
            return true;
         }
      }

      return false;
   }

   public long reorder(long p) {
      byte b = this.reorderTable[(int)p >>> 24];
      return b == 0 && p > 1L ? this.reorderEx(p) : ((long)b & 255L) << 24 | p & 16777215L;
   }

   private long reorderEx(long p) {
      assert this.minHighNoReorder > 0L;

      if (p >= this.minHighNoReorder) {
         return p;
      } else {
         long q = p | 65535L;

         long r;
         for(int i = 0; q >= (r = this.reorderRanges[i]); ++i) {
         }

         return p + ((long)((short)((int)r)) << 24);
      }
   }

   public void setStrength(int value) {
      int noStrength = this.options & -61441;
      switch (value) {
         case 0:
         case 1:
         case 2:
         case 3:
         case 15:
            this.options = noStrength | value << 12;
            return;
         default:
            throw new IllegalArgumentException("illegal strength value " + value);
      }
   }

   public void setStrengthDefault(int defaultOptions) {
      int noStrength = this.options & -61441;
      this.options = noStrength | defaultOptions & '\uf000';
   }

   static int getStrength(int options) {
      return options >> 12;
   }

   public int getStrength() {
      return getStrength(this.options);
   }

   public void setFlag(int bit, boolean value) {
      if (value) {
         this.options |= bit;
      } else {
         this.options &= ~bit;
      }

   }

   public void setFlagDefault(int bit, int defaultOptions) {
      this.options = this.options & ~bit | defaultOptions & bit;
   }

   public boolean getFlag(int bit) {
      return (this.options & bit) != 0;
   }

   public void setCaseFirst(int value) {
      assert value == 0 || value == 512 || value == 768;

      int noCaseFirst = this.options & -769;
      this.options = noCaseFirst | value;
   }

   public void setCaseFirstDefault(int defaultOptions) {
      int noCaseFirst = this.options & -769;
      this.options = noCaseFirst | defaultOptions & 768;
   }

   public int getCaseFirst() {
      return this.options & 768;
   }

   public void setAlternateHandlingShifted(boolean value) {
      int noAlternate = this.options & -13;
      if (value) {
         this.options = noAlternate | 4;
      } else {
         this.options = noAlternate;
      }

   }

   public void setAlternateHandlingDefault(int defaultOptions) {
      int noAlternate = this.options & -13;
      this.options = noAlternate | defaultOptions & 12;
   }

   public boolean getAlternateHandling() {
      return (this.options & 12) != 0;
   }

   public void setMaxVariable(int value, int defaultOptions) {
      int noMax = this.options & -113;
      switch (value) {
         case -1:
            this.options = noMax | defaultOptions & 112;
            break;
         case 0:
         case 1:
         case 2:
         case 3:
            this.options = noMax | value << 4;
            break;
         default:
            throw new IllegalArgumentException("illegal maxVariable value " + value);
      }

   }

   public int getMaxVariable() {
      return (this.options & 112) >> 4;
   }

   static boolean isTertiaryWithCaseBits(int options) {
      return (options & 1536) == 512;
   }

   static int getTertiaryMask(int options) {
      return isTertiaryWithCaseBits(options) ? '＿' : 16191;
   }

   static boolean sortsTertiaryUpperCaseFirst(int options) {
      return (options & 1792) == 768;
   }

   public boolean dontCheckFCD() {
      return (this.options & 1) == 0;
   }

   boolean hasBackwardSecondary() {
      return (this.options & 2048) != 0;
   }

   public boolean isNumeric() {
      return (this.options & 2) != 0;
   }
}
