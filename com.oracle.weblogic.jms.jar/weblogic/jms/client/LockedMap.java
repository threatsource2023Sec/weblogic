package weblogic.jms.client;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

final class LockedMap implements Cloneable, Map {
   private final String name;
   private Object lock;
   private HashMap map = new HashMap();

   LockedMap(String name, Object lock) {
      this.name = name;
      this.lock = lock;
   }

   String getName() {
      return this.name;
   }

   void setLock(Object lock) {
      this.lock = lock;
   }

   public Object getLock() {
      return this.lock;
   }

   Iterator cloneValuesIterator() {
      synchronized(this.lock) {
         Iterator var10000;
         try {
            var10000 = ((LockedMap)this.clone()).valuesIterator();
         } catch (CloneNotSupportedException var4) {
            throw new AssertionError(var4);
         }

         return var10000;
      }
   }

   Iterator valuesIterator() {
      synchronized(this.lock) {
         return this.values().iterator();
      }
   }

   protected Object clone() throws CloneNotSupportedException {
      synchronized(this.lock) {
         LockedMap clone = (LockedMap)super.clone();
         clone.map = (HashMap)this.map.clone();
         return clone;
      }
   }

   public int size() {
      synchronized(this.lock) {
         return this.map.size();
      }
   }

   public boolean isEmpty() {
      synchronized(this.lock) {
         return this.map.isEmpty();
      }
   }

   public boolean containsKey(Object key) {
      synchronized(this.lock) {
         return this.map.containsKey(key);
      }
   }

   public boolean containsValue(Object value) {
      synchronized(this.lock) {
         return this.map.containsValue(value);
      }
   }

   public Object get(Object key) {
      synchronized(this.lock) {
         return this.map.get(key);
      }
   }

   public Object put(Object key, Object value) {
      synchronized(this.lock) {
         return this.map.put(key, value);
      }
   }

   public Object remove(Object key) {
      synchronized(this.lock) {
         return this.map.remove(key);
      }
   }

   public void putAll(Map map) {
      synchronized(this.lock) {
         this.map.putAll(map);
      }
   }

   public void clear() {
      synchronized(this.lock) {
         this.map.clear();
      }
   }

   public Set keySet() {
      synchronized(this.lock) {
         return this.map.keySet();
      }
   }

   public Collection values() {
      synchronized(this.lock) {
         return this.map.values();
      }
   }

   public Set entrySet() {
      synchronized(this.lock) {
         return this.map.entrySet();
      }
   }

   public boolean equals(Object o) {
      synchronized(this.lock) {
         return this.map.equals(o);
      }
   }

   public int hashCode() {
      synchronized(this.lock) {
         return this.map.hashCode();
      }
   }

   public String toString() {
      Iterator iterator = this.valuesIterator();
      int i = 1;
      String acc = "{ [" + this.getName() + "] (";
      Object next;
      if (iterator.hasNext() && (next = iterator.next()) != null) {
         for(acc = acc + next.getClass().getName() + " " + next.toString() + ")"; iterator.hasNext(); acc = acc + "  (" + next.getClass().getName() + "#" + i + " " + next.toString() + ")") {
            next = iterator.next();
         }

         return acc + " }";
      } else {
         return acc + null + "#" + i + " null) }";
      }
   }
}
