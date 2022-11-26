package org.glassfish.grizzly.http.server.util;

import java.util.LinkedHashMap;
import java.util.Map;

public final class ParameterMap extends LinkedHashMap {
   private boolean locked = false;

   public ParameterMap() {
   }

   public ParameterMap(int initialCapacity) {
      super(initialCapacity);
   }

   public ParameterMap(int initialCapacity, float loadFactor) {
      super(initialCapacity, loadFactor);
   }

   public ParameterMap(Map map) {
      super(map);
   }

   public boolean isLocked() {
      return this.locked;
   }

   public void setLocked(boolean locked) {
      this.locked = locked;
   }

   public void clear() {
      if (this.locked) {
         throw new IllegalStateException("Illegal attempt to modify ParameterMap while locked.");
      } else {
         super.clear();
      }
   }

   public String[] put(String key, String[] value) {
      if (this.locked) {
         throw new IllegalStateException("Illegal attempt to modify ParameterMap while locked.");
      } else {
         return (String[])super.put(key, value);
      }
   }

   public void putAll(Map map) {
      if (this.locked) {
         throw new IllegalStateException("Illegal attempt to modify ParameterMap while locked.");
      } else {
         super.putAll(map);
      }
   }

   public Object remove(String key) {
      if (this.locked) {
         throw new IllegalStateException("Illegal attempt to modify ParameterMap while locked.");
      } else {
         return super.remove(key);
      }
   }
}
