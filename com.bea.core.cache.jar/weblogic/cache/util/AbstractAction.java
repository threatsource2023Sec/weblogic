package weblogic.cache.util;

import weblogic.cache.Action;
import weblogic.cache.CacheMap;

public abstract class AbstractAction implements Action {
   protected CacheMap cache;

   public void setTarget(CacheMap cache) {
      this.cache = cache;
   }

   public Object run() {
      return null;
   }

   public void close() {
   }
}
