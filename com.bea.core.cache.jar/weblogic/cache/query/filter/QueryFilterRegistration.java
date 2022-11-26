package weblogic.cache.query.filter;

import java.util.HashMap;
import java.util.Map;
import weblogic.cache.CacheMap;

public class QueryFilterRegistration extends FilterRegistration {
   static final QueryFilterRegistration instance = new QueryFilterRegistration();
   private final Map filters = new HashMap();

   public FilterQuery getQuery(String name, CacheMap cache) {
      return new CacheFilterQuery(name, cache);
   }

   public Filter getFilter(String name) {
      return (Filter)this.filters.get(name);
   }

   public void registerFilter(String name, Filter filter) {
      this.filters.put(name, filter);
   }

   public void unregisterFilter(String name) {
      this.filters.remove(name);
   }
}
