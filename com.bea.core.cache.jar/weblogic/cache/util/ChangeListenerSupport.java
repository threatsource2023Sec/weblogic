package weblogic.cache.util;

import commonj.work.WorkManager;
import weblogic.cache.CacheEntry;
import weblogic.cache.ChangeListener;

class ChangeListenerSupport extends ListenerSupport {
   public ChangeListenerSupport(WorkManager workManager) {
      super(workManager);
   }

   protected void onEvent(ChangeListener listener, ChangeEvent type, CacheEntry data, Object auxData) {
      if (type == ChangeEvent.CLEAR) {
         listener.onClear();
      } else if (type == ChangeEvent.CREATE) {
         listener.onCreate(data);
      } else if (type == ChangeEvent.DELETE) {
         listener.onDelete(data);
      } else if (type == ChangeEvent.UPDATE) {
         listener.onUpdate(data, auxData);
      }

   }
}
