package com.bea.core.repackaged.springframework.core.env;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

abstract class ReadOnlySystemAttributesMap implements Map {
   public boolean containsKey(Object key) {
      return this.get(key) != null;
   }

   @Nullable
   public String get(Object key) {
      if (!(key instanceof String)) {
         throw new IllegalArgumentException("Type of key [" + key.getClass().getName() + "] must be java.lang.String");
      } else {
         return this.getSystemAttribute((String)key);
      }
   }

   public boolean isEmpty() {
      return false;
   }

   @Nullable
   protected abstract String getSystemAttribute(String var1);

   public int size() {
      throw new UnsupportedOperationException();
   }

   public String put(String key, String value) {
      throw new UnsupportedOperationException();
   }

   public boolean containsValue(Object value) {
      throw new UnsupportedOperationException();
   }

   public String remove(Object key) {
      throw new UnsupportedOperationException();
   }

   public void clear() {
      throw new UnsupportedOperationException();
   }

   public Set keySet() {
      return Collections.emptySet();
   }

   public void putAll(Map map) {
      throw new UnsupportedOperationException();
   }

   public Collection values() {
      return Collections.emptySet();
   }

   public Set entrySet() {
      return Collections.emptySet();
   }
}
