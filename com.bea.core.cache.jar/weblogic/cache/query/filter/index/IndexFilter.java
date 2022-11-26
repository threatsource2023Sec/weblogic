package weblogic.cache.query.filter.index;

import java.util.Map;
import java.util.Set;
import weblogic.cache.CacheMap;
import weblogic.cache.query.filter.Filter;

public interface IndexFilter extends Filter {
   Set keySet(CacheMap var1, Map var2, Object... var3);
}
