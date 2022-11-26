package org.apache.taglibs.standard.lang.jstl;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class EnumeratedMap implements Map {
   Map mMap;

   public void clear() {
      throw new UnsupportedOperationException();
   }

   public boolean containsKey(Object pKey) {
      return this.getValue(pKey) != null;
   }

   public boolean containsValue(Object pValue) {
      return this.getAsMap().containsValue(pValue);
   }

   public Set entrySet() {
      return this.getAsMap().entrySet();
   }

   public Object get(Object pKey) {
      return this.getValue(pKey);
   }

   public boolean isEmpty() {
      return !this.enumerateKeys().hasMoreElements();
   }

   public Set keySet() {
      return this.getAsMap().keySet();
   }

   public Object put(Object pKey, Object pValue) {
      throw new UnsupportedOperationException();
   }

   public void putAll(Map pMap) {
      throw new UnsupportedOperationException();
   }

   public Object remove(Object pKey) {
      throw new UnsupportedOperationException();
   }

   public int size() {
      return this.getAsMap().size();
   }

   public Collection values() {
      return this.getAsMap().values();
   }

   public abstract Enumeration enumerateKeys();

   public abstract boolean isMutable();

   public abstract Object getValue(Object var1);

   public Map getAsMap() {
      if (this.mMap != null) {
         return this.mMap;
      } else {
         Map m = this.convertToMap();
         if (!this.isMutable()) {
            this.mMap = m;
         }

         return m;
      }
   }

   Map convertToMap() {
      Map ret = new HashMap();
      Enumeration e = this.enumerateKeys();

      while(e.hasMoreElements()) {
         Object key = e.nextElement();
         Object value = this.getValue(key);
         ret.put(key, value);
      }

      return ret;
   }
}
