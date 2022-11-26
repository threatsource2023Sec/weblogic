package weblogic.cache.query.filter;

import java.util.Map;
import weblogic.cache.CacheMap;

public interface Filter {
   boolean evaluate(CacheMap var1, Map.Entry var2, Object... var3);
}
