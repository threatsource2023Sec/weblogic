package weblogic.cache.query.filter;

import weblogic.cache.CacheMap;

public abstract class FilterRegistration {
   public abstract FilterQuery getQuery(String var1, CacheMap var2);

   public static FilterRegistration getRegistration() {
      return QueryFilterRegistration.instance;
   }

   public abstract void registerFilter(String var1, Filter var2);

   public abstract void unregisterFilter(String var1);
}
