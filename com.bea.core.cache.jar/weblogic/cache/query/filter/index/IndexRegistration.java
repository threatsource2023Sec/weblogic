package weblogic.cache.query.filter.index;

import java.util.Map;
import weblogic.cache.CacheMap;

public abstract class IndexRegistration {
   public abstract void addIndex(CacheMap var1, IndexExtractor var2, Map var3);

   public static IndexRegistration getIndexRegistration() {
      return QueryIndexRegistration.instance;
   }

   public abstract Map getIndexes(CacheMap var1);

   public abstract void removeIndex(CacheMap var1, IndexExtractor var2);
}
