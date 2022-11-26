package weblogic.cache.store;

import java.util.Map;
import weblogic.cache.util.BaseEvictionStrategy;
import weblogic.cache.util.DelegatingEvictionStrategy;

public class WriteBackEvictionStrategy extends DelegatingEvictionStrategy {
   private WriteBehind writePolicy;

   public WriteBackEvictionStrategy(BaseEvictionStrategy delegate, WriteBehind writePolicy) {
      super(delegate);
      this.setWritePolicy(writePolicy);
   }

   public void setWritePolicy(WriteBehind writePolicy) {
      assert writePolicy != null;

      this.writePolicy = writePolicy;
   }

   public Map evict() {
      Map evictedMap = super.evict();
      this.writePolicy.storeAll(evictedMap);
      return evictedMap;
   }
}
