package weblogic.cache.util;

import java.util.Map;
import weblogic.cache.CacheMap;
import weblogic.cache.ChangeListener;

public class DelegatingMonitoredMap extends MonitoredMapAdapter implements Map, CacheMap {
   private final CacheMap delegate;

   public DelegatingMonitoredMap(CacheMap m, CacheMap mm) {
      super(m);
      this.delegate = mm;
   }

   public void addUpdateListenerLocal(ChangeListener l) {
      super.addChangeListener(l);
   }

   public void addChangeListener(ChangeListener l) {
      this.delegate.addChangeListener(l);
   }

   public void removeChangeListener(ChangeListener l) {
      this.delegate.removeChangeListener(l);
   }
}
