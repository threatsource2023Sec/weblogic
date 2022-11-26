package com.bea.xbeanmarshal.runtime.internal.util;

import com.bea.staxb.runtime.internal.util.collections.ArrayIterator;
import com.bea.xbeanmarshal.runtime.internal.util.collections.EmptyIterator;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;

public final class ArrayUtils {
   public static String arrayToString(Object array) {
      if (array == null) {
         return "null";
      } else if (!array.getClass().isArray()) {
         return array.toString();
      } else {
         StringBuffer buf = new StringBuffer();
         buf.append("[");
         int lim = -1 + Array.getLength(array);

         for(int i = 0; i <= lim; ++i) {
            Object o = Array.get(array, i);
            buf.append(o == array ? "(this Array)" : arrayToString(o));
            if (i < lim) {
               buf.append(", ");
            }
         }

         buf.append("]");
         return buf.toString();
      }
   }

   public static boolean arrayEquals(Object aa, Object bb) {
      if (aa == bb) {
         return true;
      } else if (aa == null) {
         return bb == null;
      } else if (!aa.getClass().isArray()) {
         return aa.equals(bb);
      } else if (bb == null) {
         return aa == null;
      } else if (!bb.getClass().isArray()) {
         return bb.equals(aa);
      } else {
         int lena = Array.getLength(aa);
         int lenb = Array.getLength(bb);
         if (lena != lenb) {
            return false;
         } else {
            for(int i = 0; i < lena; ++i) {
               Object oa = Array.get(aa, i);
               Object ob = Array.get(bb, i);
               if (!arrayEquals(oa, ob)) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   public static void main(String[] args) {
      Object a = new int[]{3, 4, 5, 34423, 45};
      Object b = new int[]{3, 4, 5, 34423, 45};
      if (a == b) {
         throw new AssertionError("bad objs");
      } else if (!arrayEquals(a, b)) {
         throw new AssertionError("bad equals");
      } else {
         Object a = new int[][]{{1, 2, 3}, {3, 4, 5}};
         Object b = new int[][]{{1, 2, 3}, {3, 4, 5}};
         if (a == b) {
            throw new AssertionError("bad objs");
         } else if (!arrayEquals(a, b)) {
            throw new AssertionError("bad equals");
         } else {
            System.out.println("ok");
         }
      }
   }

   public static Iterator getCollectionIterator(Object value) {
      if (value == null) {
         return EmptyIterator.getInstance();
      } else if (value instanceof Object[]) {
         return new ArrayIterator((Object[])((Object[])value));
      } else if (value instanceof Collection) {
         return ((Collection)value).iterator();
      } else {
         System.err.println("\nArrayUtils.getCollectionIterator for object '" + value.getClass().getName() + "', cannot determine correct Iterator class..  returning NULL\n");
         return null;
      }
   }
}
