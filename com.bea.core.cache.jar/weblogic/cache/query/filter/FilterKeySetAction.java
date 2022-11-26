package weblogic.cache.query.filter;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import weblogic.cache.CacheMap;
import weblogic.cache.CacheRuntimeException;

public class FilterKeySetAction extends FilterAction {
   public FilterKeySetAction(Comparator comparator, String name, Object... parameters) {
      super(comparator, name, parameters);
   }

   public Object mergeResult(Map results) {
      Object result;
      if (this.comparator != null) {
         result = new TreeSet(this.comparator);
      } else {
         result = new HashSet(this.getTotalSize(results), 1.0F);
      }

      Iterator var3 = results.values().iterator();

      while(var3.hasNext()) {
         Object o = var3.next();
         if (o instanceof Set) {
            ((Set)result).addAll((Set)o);
         } else {
            this.throwUnexpectedResultException(o, Set.class);
         }
      }

      return result;
   }

   public Object run() {
      if (!(this.cache instanceof CacheMap)) {
         throw new AssertionError("Cache is not a cache map.");
      } else {
         Filter filter = this.getFilter();
         if (filter == null) {
            throw new CacheRuntimeException("Unable to find filter with name " + this.name);
         } else {
            return FilterQueryHelper.keySet(filter, this.cache, this.comparator, this.parameters);
         }
      }
   }
}
