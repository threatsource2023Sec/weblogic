package javax.faces.component;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

class TypedCollections {
   private static boolean checkCollectionMembers(Collection c, Class type) {
      Iterator var2 = c.iterator();

      Object element;
      do {
         if (!var2.hasNext()) {
            return true;
         }

         element = var2.next();
      } while(element == null || type.isInstance(element));

      return false;
   }

   static Collection dynamicallyCastCollection(Collection c, Class type, Class collectionType) {
      if (c == null) {
         return null;
      } else if (!collectionType.isInstance(c)) {
         throw new ClassCastException(c.getClass().getName());
      } else {
         assert checkCollectionMembers(c, type) : "The collection contains members with a type other than " + type.getName();

         return (Collection)collectionType.cast(c);
      }
   }

   static List dynamicallyCastList(List list, Class type) {
      return (List)dynamicallyCastCollection(list, type, List.class);
   }

   static Set dynamicallyCastSet(Set set, Class type) {
      return (Set)dynamicallyCastCollection(set, type, Set.class);
   }

   static Map dynamicallyCastMap(Map map, Class keyType, Class valueType) {
      if (map == null) {
         return null;
      } else {
         assert checkCollectionMembers(map.keySet(), keyType) : "The map contains keys with a type other than " + keyType.getName();

         assert checkCollectionMembers(map.values(), valueType) : "The map contains values with a type other than " + valueType.getName();

         return map;
      }
   }
}
