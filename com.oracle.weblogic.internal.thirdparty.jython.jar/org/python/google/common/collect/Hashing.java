package org.python.google.common.collect;

import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;

@GwtCompatible
final class Hashing {
   private static final long C1 = -862048943L;
   private static final long C2 = 461845907L;
   private static final int MAX_TABLE_SIZE = 1073741824;

   private Hashing() {
   }

   static int smear(int hashCode) {
      return (int)(461845907L * (long)Integer.rotateLeft((int)((long)hashCode * -862048943L), 15));
   }

   static int smearedHash(@Nullable Object o) {
      return smear(o == null ? 0 : o.hashCode());
   }

   static int closedTableSize(int expectedEntries, double loadFactor) {
      expectedEntries = Math.max(expectedEntries, 2);
      int tableSize = Integer.highestOneBit(expectedEntries);
      if (expectedEntries > (int)(loadFactor * (double)tableSize)) {
         tableSize <<= 1;
         return tableSize > 0 ? tableSize : 1073741824;
      } else {
         return tableSize;
      }
   }

   static boolean needsResizing(int size, int tableSize, double loadFactor) {
      return (double)size > loadFactor * (double)tableSize && tableSize < 1073741824;
   }
}
