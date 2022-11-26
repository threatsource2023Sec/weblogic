package weblogic.cache.lld;

import java.util.Map;

class LDDGroupMessageInvalidator extends LLDGroupMessageInvalidator implements ChangeListener {
   public LDDGroupMessageInvalidator(String name, Map m) {
      super(name, m);
   }

   public void onUpdate(CacheEntry entry, Object oldValue) {
      this.onDelete(entry);
   }
}
