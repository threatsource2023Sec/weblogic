package weblogic.cache.query.filter;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import weblogic.cache.CacheMap;
import weblogic.cache.query.filter.index.IndexFilter;
import weblogic.cache.query.filter.index.IndexFilterQueryHelper;
import weblogic.cache.util.BaseCacheEntry;

public class FilterQueryHelper {
   public static Set entrySet(Filter filter, CacheMap cache, Comparator comparator, Object... parameters) {
      if (filter instanceof IndexFilter) {
         Set result = IndexFilterQueryHelper.entrySet((IndexFilter)filter, cache, comparator, parameters);
         if (result != null) {
            return result;
         }
      }

      Object result;
      if (comparator != null) {
         result = new TreeSet(comparator);
      } else {
         result = new HashSet();
      }

      Set entries = cache.entrySet();
      Iterator var6 = entries.iterator();

      while(var6.hasNext()) {
         Map.Entry entry = (Map.Entry)var6.next();
         if (filter.evaluate(cache, entry, parameters)) {
            ((Set)result).add(new BaseCacheEntry(entry.getKey(), entry.getValue(), 0L, 0L));
         }
      }

      return Collections.unmodifiableSet((Set)result);
   }

   public static Set keySet(Filter filter, CacheMap cache, Comparator comparator, Object... parameters) {
      if (filter instanceof IndexFilter) {
         Set result = IndexFilterQueryHelper.keySet((IndexFilter)filter, cache, comparator, parameters);
         if (result != null) {
            return result;
         }
      }

      Object result;
      if (comparator != null) {
         result = new TreeSet(comparator);
      } else {
         result = new HashSet();
      }

      Set entries = cache.entrySet();
      Iterator var6 = entries.iterator();

      while(var6.hasNext()) {
         Map.Entry entry = (Map.Entry)var6.next();
         if (filter.evaluate(cache, entry, parameters)) {
            ((Set)result).add(entry.getKey());
         }
      }

      return Collections.unmodifiableSet((Set)result);
   }

   public static Collection values(Filter filter, CacheMap cache, Comparator comparator, Object... parameters) {
      if (filter instanceof IndexFilter) {
         Collection result = IndexFilterQueryHelper.values((IndexFilter)filter, cache, comparator, parameters);
         if (result != null) {
            return result;
         }
      }

      Object result;
      if (comparator != null) {
         result = new TreeSet(comparator);
      } else {
         result = new HashSet();
      }

      Set entries = cache.entrySet();
      Iterator var6 = entries.iterator();

      while(var6.hasNext()) {
         Map.Entry entry = (Map.Entry)var6.next();
         if (filter.evaluate(cache, entry, parameters)) {
            ((Collection)result).add(entry.getValue());
         }
      }

      return Collections.unmodifiableCollection((Collection)result);
   }
}
