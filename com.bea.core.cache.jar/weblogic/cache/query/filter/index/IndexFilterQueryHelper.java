package weblogic.cache.query.filter.index;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import weblogic.cache.CacheMap;

public class IndexFilterQueryHelper {
   public static Set entrySet(IndexFilter filter, CacheMap cache, Comparator comparator, Object... parameters) {
      Set keySet = filter.keySet(cache, IndexRegistration.getIndexRegistration().getIndexes(cache), parameters);
      if (keySet == null) {
         return null;
      } else {
         Object result;
         if (comparator != null) {
            result = new TreeSet(comparator);
         } else {
            result = new HashSet();
         }

         Iterator var6 = keySet.iterator();

         while(var6.hasNext()) {
            Object key = var6.next();
            ((Set)result).add(cache.getEntry(key));
         }

         return (Set)result;
      }
   }

   public static Set keySet(IndexFilter filter, CacheMap cache, Comparator comparator, Object... parameters) {
      Set keySet = filter.keySet(cache, IndexRegistration.getIndexRegistration().getIndexes(cache), parameters);
      if (keySet == null) {
         return null;
      } else {
         Object result;
         if (comparator != null) {
            result = new TreeSet(comparator);
            ((Set)result).addAll(keySet);
         } else {
            result = keySet;
         }

         return (Set)result;
      }
   }

   public static Collection values(IndexFilter filter, CacheMap cache, Comparator comparator, Object... parameters) {
      Set keySet = filter.keySet(cache, IndexRegistration.getIndexRegistration().getIndexes(cache), parameters);
      if (keySet == null) {
         return null;
      } else {
         Object result;
         if (comparator != null) {
            result = new TreeSet(comparator);
         } else {
            result = new HashSet();
         }

         Iterator var6 = keySet.iterator();

         while(var6.hasNext()) {
            Object key = var6.next();
            ((Set)result).add(cache.get(key));
         }

         return (Collection)result;
      }
   }
}
