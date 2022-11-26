package com.sun.faces.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TypedCollections {
   private static boolean checkCollectionMembers(Collection c, Class type) {
      Iterator i$ = c.iterator();

      Object element;
      do {
         if (!i$.hasNext()) {
            return true;
         }

         element = i$.next();
      } while(element == null || type.isInstance(element));

      return false;
   }

   public static Collection dynamicallyCastCollection(Collection c, Class type, Class collectionType) {
      if (c == null) {
         return null;
      } else if (!collectionType.isInstance(c)) {
         throw new ClassCastException(c.getClass().getName());
      } else {
         assert checkCollectionMembers(c, type) : "The collection contains members with a type other than " + type.getName();

         return (Collection)collectionType.cast(c);
      }
   }

   public static List dynamicallyCastList(List list, Class type) {
      return (List)dynamicallyCastCollection(list, type, List.class);
   }

   public static Set dynamicallyCastSet(Set set, Class type) {
      return (Set)dynamicallyCastCollection(set, type, Set.class);
   }

   public static Map dynamicallyCastMap(Map map, Class keyType, Class valueType) {
      if (map == null) {
         return null;
      } else {
         assert checkCollectionMembers(map.keySet(), keyType) : "The map contains keys with a type other than " + keyType.getName();

         assert checkCollectionMembers(map.values(), valueType) : "The map contains values with a type other than " + valueType.getName();

         return map;
      }
   }
}
