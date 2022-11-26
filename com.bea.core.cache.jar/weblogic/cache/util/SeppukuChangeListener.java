package weblogic.cache.util;

import java.util.Map;
import weblogic.cache.CacheEntry;
import weblogic.cache.ChangeListener;

public class SeppukuChangeListener implements ChangeListener {
   private final Map target;

   public SeppukuChangeListener(Map m) {
      this.target = m;
   }

   public void onCreate(CacheEntry entry) {
      this.onDelete(entry);
   }

   public void onUpdate(CacheEntry entry, Object oldValue) {
      this.onDelete(entry);
   }

   public void onDelete(CacheEntry entry) {
      this.target.remove(entry.getKey());
   }

   public void onClear() {
      this.target.clear();
   }
}
