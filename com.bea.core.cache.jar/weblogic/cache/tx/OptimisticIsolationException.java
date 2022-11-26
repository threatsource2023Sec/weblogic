package weblogic.cache.tx;

import java.util.Collection;
import weblogic.cache.CacheRuntimeException;

public class OptimisticIsolationException extends CacheRuntimeException {
   private final Collection _failed;

   public OptimisticIsolationException(Collection failedKeys) {
      super(failedKeys.toString());
      this._failed = failedKeys;
   }

   public Collection getFailedKeys() {
      return this._failed;
   }
}
