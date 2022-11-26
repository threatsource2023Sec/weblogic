package weblogic.cache.query.filter;

import java.util.Collection;
import java.util.Comparator;
import java.util.Set;

public interface FilterQuery {
   Set keySet(Object... var1);

   Set keySet(Comparator var1, Object... var2);

   Set entrySet(Object... var1);

   Set entrySet(Comparator var1, Object... var2);

   Collection values(Object... var1);

   Collection values(Comparator var1, Object... var2);
}
