package org.python.google.common.escape;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.annotations.VisibleForTesting;
import org.python.google.common.base.Preconditions;

@Beta
@GwtCompatible
public final class ArrayBasedEscaperMap {
   private final char[][] replacementArray;
   private static final char[][] EMPTY_REPLACEMENT_ARRAY = new char[0][0];

   public static ArrayBasedEscaperMap create(Map replacements) {
      return new ArrayBasedEscaperMap(createReplacementArray(replacements));
   }

   private ArrayBasedEscaperMap(char[][] replacementArray) {
      this.replacementArray = replacementArray;
   }

   char[][] getReplacementArray() {
      return this.replacementArray;
   }

   @VisibleForTesting
   static char[][] createReplacementArray(Map map) {
      Preconditions.checkNotNull(map);
      if (map.isEmpty()) {
         return EMPTY_REPLACEMENT_ARRAY;
      } else {
         char max = (Character)Collections.max(map.keySet());
         char[][] replacements = new char[max + 1][];

         char c;
         for(Iterator var3 = map.keySet().iterator(); var3.hasNext(); replacements[c] = ((String)map.get(c)).toCharArray()) {
            c = (Character)var3.next();
         }

         return replacements;
      }
   }
}
