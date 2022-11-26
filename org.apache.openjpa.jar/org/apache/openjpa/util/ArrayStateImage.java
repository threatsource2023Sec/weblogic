package org.apache.openjpa.util;

import java.util.BitSet;
import org.apache.commons.lang.ObjectUtils;

public class ArrayStateImage {
   public static Object[] newImage(int numFields) {
      Object[] state = new Object[numFields + 1];
      state[numFields] = new BitSet(numFields);
      return state;
   }

   public static boolean isImage(Object obj) {
      if (!(obj instanceof Object[])) {
         return false;
      } else {
         Object[] arr = (Object[])((Object[])obj);
         return arr.length > 0 && arr[arr.length - 1] instanceof BitSet;
      }
   }

   public static BitSet getLoaded(Object[] state) {
      return (BitSet)state[state.length - 1];
   }

   public static void setLoaded(Object[] state, BitSet loaded) {
      state[state.length - 1] = loaded;
   }

   public static Object[] clone(Object[] state) {
      Object[] copy = new Object[state.length];
      System.arraycopy(state, 0, copy, 0, state.length - 1);
      BitSet loaded = (BitSet)state[state.length - 1];
      copy[copy.length - 1] = loaded.clone();
      return copy;
   }

   public static boolean sameVersion(Object[] state1, Object[] state2) {
      if (state1 == state2) {
         return true;
      } else if (state1 != null && state2 != null) {
         BitSet loaded1 = getLoaded(state1);
         BitSet loaded2 = getLoaded(state2);
         int i = 0;

         for(int max = loaded1.length(); i < max; ++i) {
            if (loaded1.get(i) && loaded2.get(i) && !ObjectUtils.equals(state1[i], state2[i])) {
               return false;
            }
         }

         return true;
      } else {
         return true;
      }
   }
}
