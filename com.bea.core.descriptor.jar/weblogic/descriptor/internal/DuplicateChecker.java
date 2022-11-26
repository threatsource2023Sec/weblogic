package weblogic.descriptor.internal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import weblogic.descriptor.DescriptorBean;

class DuplicateChecker {
   private Map map = Collections.synchronizedMap(new HashMap());

   public DescriptorBean registerIfNoDuplicate(DescriptorBean bean) {
      return (DescriptorBean)this.register(bean);
   }

   public ResolvedReference registerIfNoDuplicate(ResolvedReference ref) {
      ResolvedReference origRef = (ResolvedReference)this.register(ref);
      if (origRef == null) {
         return null;
      } else if (ref == origRef) {
         return ref;
      } else if (origRef.isValid()) {
         return origRef;
      } else {
         this.unregister(origRef);
         this.register(ref);
         return null;
      }
   }

   public void unregister(Object toRemove) {
      this.map.remove(toRemove);
   }

   private Object register(Object entry) {
      Object origEntry = this.map.put(entry, entry);
      if (origEntry == null) {
         return null;
      } else if (entry == origEntry) {
         return entry;
      } else {
         this.map.put(origEntry, origEntry);
         return origEntry;
      }
   }
}
