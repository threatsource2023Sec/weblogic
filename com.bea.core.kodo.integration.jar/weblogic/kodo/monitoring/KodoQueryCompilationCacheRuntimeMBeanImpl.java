package weblogic.kodo.monitoring;

import java.util.Map;
import weblogic.management.ManagementException;
import weblogic.management.runtime.KodoQueryCompilationCacheRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;

public class KodoQueryCompilationCacheRuntimeMBeanImpl extends RuntimeMBeanDelegate implements KodoQueryCompilationCacheRuntimeMBean {
   Map cache = null;

   public KodoQueryCompilationCacheRuntimeMBeanImpl(String name, RuntimeMBean parent, Map cache) throws ManagementException {
      super(name, parent, true, "QueryCompilationCacheRuntime");
      this.cache = cache;
   }

   public void clear() {
      this.cache.clear();
   }

   public int getTotalCurrentEntries() {
      return this.cache.size();
   }
}
