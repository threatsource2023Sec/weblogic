package weblogic.cache.query.filter;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import weblogic.cache.Action;
import weblogic.cache.CacheRuntimeException;
import weblogic.cache.MergeableAction;
import weblogic.cache.ReadOnlyAction;
import weblogic.cache.util.AbstractAction;

public abstract class FilterAction extends AbstractAction implements ReadOnlyAction, MergeableAction {
   protected final Comparator comparator;
   protected final String name;
   protected final Object[] parameters;

   protected FilterAction(Comparator comparator, String name, Object... parameters) {
      this.name = name;
      this.parameters = parameters;
      this.comparator = comparator;
   }

   public Action divide(Set keys) {
      return this;
   }

   public Set getAffectedKeys() {
      return null;
   }

   public boolean includesClear() {
      return false;
   }

   protected Filter getFilter() {
      return QueryFilterRegistration.instance.getFilter(this.name);
   }

   protected int getTotalSize(Map results) {
      int size = 0;
      Iterator var3 = results.values().iterator();

      while(var3.hasNext()) {
         Object o = var3.next();
         if (o instanceof Collection) {
            size += ((Collection)o).size();
         } else {
            this.throwUnexpectedResultException(o, Collection.class);
         }
      }

      return size;
   }

   protected void throwUnexpectedResultException(Object o, Class expected) {
      if (o instanceof RuntimeException) {
         throw (RuntimeException)o;
      } else if (o instanceof Error) {
         throw (Error)o;
      } else if (o instanceof Exception) {
         throw new CacheRuntimeException((Exception)o);
      } else {
         throw new CacheRuntimeException("Unknown result, expected " + expected.getName() + " , received: " + o.getClass().getName());
      }
   }
}
