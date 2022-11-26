package org.python.google.common.base;

import java.util.BitSet;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.annotations.VisibleForTesting;

@GwtIncompatible
final class SmallCharMatcher extends CharMatcher.NamedFastMatcher {
   static final int MAX_SIZE = 1023;
   private final char[] table;
   private final boolean containsZero;
   private final long filter;
   private static final int C1 = -862048943;
   private static final int C2 = 461845907;
   private static final double DESIRED_LOAD_FACTOR = 0.5;

   private SmallCharMatcher(char[] table, long filter, boolean containsZero, String description) {
      super(description);
      this.table = table;
      this.filter = filter;
      this.containsZero = containsZero;
   }

   static int smear(int hashCode) {
      return 461845907 * Integer.rotateLeft(hashCode * -862048943, 15);
   }

   private boolean checkFilter(int c) {
      return 1L == (1L & this.filter >> c);
   }

   @VisibleForTesting
   static int chooseTableSize(int setSize) {
      if (setSize == 1) {
         return 2;
      } else {
         int tableSize;
         for(tableSize = Integer.highestOneBit(setSize - 1) << 1; (double)tableSize * 0.5 < (double)setSize; tableSize <<= 1) {
         }

         return tableSize;
      }
   }

   static CharMatcher from(BitSet chars, String description) {
      long filter = 0L;
      int size = chars.cardinality();
      boolean containsZero = chars.get(0);
      char[] table = new char[chooseTableSize(size)];
      int mask = table.length - 1;

      for(int c = chars.nextSetBit(0); c != -1; c = chars.nextSetBit(c + 1)) {
         filter |= 1L << c;

         int index;
         for(index = smear(c) & mask; table[index] != 0; index = index + 1 & mask) {
         }

         table[index] = (char)c;
      }

      return new SmallCharMatcher(table, filter, containsZero, description);
   }

   public boolean matches(char c) {
      if (c == 0) {
         return this.containsZero;
      } else if (!this.checkFilter(c)) {
         return false;
      } else {
         int mask = this.table.length - 1;
         int startingIndex = smear(c) & mask;
         int index = startingIndex;

         while(this.table[index] != 0) {
            if (this.table[index] == c) {
               return true;
            }

            index = index + 1 & mask;
            if (index == startingIndex) {
               return false;
            }
         }

         return false;
      }
   }

   void setBits(BitSet table) {
      if (this.containsZero) {
         table.set(0);
      }

      char[] var2 = this.table;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         char c = var2[var4];
         if (c != 0) {
            table.set(c);
         }
      }

   }
}
