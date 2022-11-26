package weblogic.cache.util;

import commonj.work.WorkManager;
import java.util.Map;
import weblogic.cache.EvictionListener;

class EvictionListenerSupport extends ListenerSupport {
   public EvictionListenerSupport(WorkManager workManager) {
      super(workManager);
   }

   protected void onEvent(EvictionListener listener, Object type, Map data, Object auxData) {
      listener.onEviction(data);
   }
}
