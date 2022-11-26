package weblogic.cache;

import java.util.Collection;
import java.util.Map;

public interface CacheLoader {
   Object load(Object var1);

   Map loadAll(Collection var1);
}
