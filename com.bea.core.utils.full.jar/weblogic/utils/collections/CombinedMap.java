package weblogic.utils.collections;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class CombinedMap implements Map {
   private Map[] maps;

   public CombinedMap(Map[] maps) {
      this.maps = maps;
   }

   public int size() {
      int n = 0;

      for(int i = 0; i < this.maps.length; ++i) {
         n += this.maps[i].size();
      }

      return n;
   }

   public boolean isEmpty() {
      for(int i = 0; i < this.maps.length; ++i) {
         if (!this.maps[i].isEmpty()) {
            return false;
         }
      }

      return true;
   }

   public boolean containsKey(Object key) {
      for(int i = 0; i < this.maps.length; ++i) {
         if (this.maps[i].containsKey(key)) {
            return true;
         }
      }

      return false;
   }

   public boolean containsValue(Object value) {
      for(int i = 0; i < this.maps.length; ++i) {
         if (this.maps[i].containsValue(value)) {
            return true;
         }
      }

      return false;
   }

   public Object get(Object key) {
      for(int i = 0; i < this.maps.length; ++i) {
         Object v = this.maps[i].get(key);
         if (v != null) {
            return v;
         }
      }

      return null;
   }

   public Object put(Object key, Object value) {
      throw new UnsupportedOperationException();
   }

   public Object remove(Object key) {
      throw new UnsupportedOperationException();
   }

   public void putAll(Map t) {
      throw new UnsupportedOperationException();
   }

   public void clear() {
      throw new UnsupportedOperationException();
   }

   public Set keySet() {
      Set[] sets = new Set[this.maps.length];

      for(int i = 0; i < this.maps.length; ++i) {
         sets[i] = this.maps[i].keySet();
      }

      return new CombinedSet(sets);
   }

   public Collection values() {
      Collection[] v = new Collection[this.maps.length];

      for(int i = 0; i < this.maps.length; ++i) {
         v[i] = this.maps[i].values();
      }

      return new CombinedSet(v);
   }

   public Set entrySet() {
      Set[] sets = new Set[this.maps.length];

      for(int i = 0; i < this.maps.length; ++i) {
         sets[i] = this.maps[i].entrySet();
      }

      return new CombinedSet(sets);
   }
}
