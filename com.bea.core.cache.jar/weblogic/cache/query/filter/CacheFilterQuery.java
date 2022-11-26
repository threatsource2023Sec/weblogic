package weblogic.cache.query.filter;

import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import weblogic.cache.CacheMap;

public class CacheFilterQuery implements FilterQuery {
   protected final String filterName;
   protected final CacheMap cache;

   public CacheFilterQuery(String fn, CacheMap cache) {
      this.filterName = fn;
      this.cache = cache;
   }

   public Set entrySet(Object... parameters) {
      return this.entrySet((Comparator)null, parameters);
   }

   public Set entrySet(Comparator comparator, Object... parameters) {
      return (Set)this.cache.prepare(new FilterEntrySetAction(comparator, this.filterName, parameters)).run();
   }

   public Set keySet(Object... parameters) {
      return this.keySet((Comparator)null, parameters);
   }

   public Set keySet(Comparator comparator, Object... parameters) {
      return (Set)this.cache.prepare(new FilterKeySetAction(comparator, this.filterName, parameters)).run();
   }

   public Collection values(Object... parameters) {
      return this.values((Comparator)null, parameters);
   }

   public Collection values(Comparator comparator, Object... parameters) {
      return (Collection)this.cache.prepare(new FilterValuesAction(comparator, this.filterName, parameters)).run();
   }
}
