package org.jboss.weld.util.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WeldCollections {
   private WeldCollections() {
   }

   public static Set immutableSetView(Set set) {
      return set instanceof ImmutableSet ? set : Collections.unmodifiableSet(set);
   }

   public static List immutableListView(List list) {
      if (list instanceof ImmutableList) {
         return list;
      } else {
         if (list instanceof ArrayList) {
            ((ArrayList)ArrayList.class.cast(list)).trimToSize();
         }

         return Collections.unmodifiableList(list);
      }
   }

   public static Map immutableMapView(Map map) {
      return map instanceof ImmutableMap ? map : Collections.unmodifiableMap(map);
   }

   public static List sort(List list, Comparator comparator) {
      Collections.sort(list, comparator);
      return list;
   }

   public static String toMultiRowString(Collection collection) {
      if (collection == null) {
         return null;
      } else if (collection.isEmpty()) {
         return "(empty collection)";
      } else {
         StringBuilder builder = new StringBuilder("\n  - ");
         Iterator iterator = collection.iterator();

         while(iterator.hasNext()) {
            builder.append(iterator.next());
            if (iterator.hasNext()) {
               builder.append(",\n  - ");
            }
         }

         return builder.toString();
      }
   }

   public static boolean addIfNotNull(Collection collection, Object element) {
      return element == null ? false : collection.add(element);
   }

   public static Object putIfAbsent(Map map, Object key, Object value) {
      Object old = map.putIfAbsent(key, value);
      return old != null ? old : value;
   }
}
