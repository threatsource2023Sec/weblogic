package com.bea.adaptive.harvester.utils.collections;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CollectionUtils {
   public static String iteratorToString(Iterator it) {
      StringBuffer str = new StringBuffer("[");

      for(boolean first = true; it.hasNext(); first = false) {
         if (!first) {
            str.append(", ");
         }

         str.append(it.next());
      }

      str.append("]");
      return str.toString();
   }

   public static String aggregateToString(Object o) {
      StringBuffer str = new StringBuffer(128);
      boolean first = true;
      Iterator it;
      if (o instanceof Collection) {
         str.append("[");
         Collection c = (Collection)o;

         for(it = c.iterator(); it.hasNext(); first = false) {
            if (!first) {
               str.append(", ");
            }

            str.append(aggregateToString(it.next()));
         }

         str.append("]");
      } else if (o instanceof Map) {
         str.append("{");
         Map m = (Map)o;
         it = m.entrySet().iterator();

         while(it.hasNext()) {
            if (!first) {
               str.append(", ");
            }

            Object key = it.next();
            str.append("($").append(key).append(aggregateToString(m.get(key))).append(")");
         }

         str.append("}");
      } else if (o != null && o.getClass().isArray()) {
         str.append("(");
         int size = Array.getLength(o);

         for(int i = 0; i < size; ++i) {
            if (!first) {
               str.append(", ");
            }

            str.append(aggregateToString(Array.get(o, i)));
            first = false;
         }

         str.append(")");
      } else {
         str.append(o);
      }

      return str.toString();
   }

   public static boolean extendTo(List al, int length) {
      int diff = length - al.size();
      if (diff <= 0) {
         return false;
      } else {
         for(int i = 0; i < diff; ++i) {
            al.add((Object)null);
         }

         return true;
      }
   }
}
