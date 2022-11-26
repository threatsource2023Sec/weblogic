package org.apache.xmlbeans.impl.common;

import java.lang.ref.SoftReference;
import java.util.HashMap;

public class SoftCache {
   private HashMap map = new HashMap();

   public Object get(Object key) {
      SoftReference softRef = (SoftReference)this.map.get(key);
      return softRef == null ? null : softRef.get();
   }

   public Object put(Object key, Object value) {
      SoftReference softRef = (SoftReference)this.map.put(key, new SoftReference(value));
      if (softRef == null) {
         return null;
      } else {
         Object oldValue = softRef.get();
         softRef.clear();
         return oldValue;
      }
   }

   public Object remove(Object key) {
      SoftReference softRef = (SoftReference)this.map.remove(key);
      if (softRef == null) {
         return null;
      } else {
         Object oldValue = softRef.get();
         softRef.clear();
         return oldValue;
      }
   }
}
