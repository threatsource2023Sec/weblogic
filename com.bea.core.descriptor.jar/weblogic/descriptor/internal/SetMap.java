package weblogic.descriptor.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import weblogic.utils.Debug;

class SetMap extends LinkedHashMap {
   private Comparator comparator;

   public SetMap() {
   }

   public SetMap(Comparator comparator) {
      this.comparator = comparator;
   }

   public Iterator getSetIterator(Object key) {
      Map set = this.getSet(key);
      return set == null ? Collections.EMPTY_LIST.iterator() : Arrays.asList(set.values().toArray()).iterator();
   }

   public final Object put(Object key, Object object) {
      throw new UnsupportedOperationException("use putInSet()");
   }

   public boolean putInSet(Object key, Object object) {
      Map set = this.getSet(key);
      if (set == null) {
         set = new TreeMap(this.comparator);
         super.put(key, set);
      }

      return this.addToSet((Map)set, object);
   }

   public void removeFromSet(Object key, Object object) {
      Map set = this.getSet(key);
      if (set != null) {
         Object removed = set.remove(object);
         if (removed == null) {
            Debug.say("failed to remove " + key + ": " + object);
         }

         if (set.size() == 0) {
            this.remove(key);
         }
      }

   }

   public Collection values() {
      List list = new ArrayList(this.size());
      Iterator it = super.values().iterator();

      while(it.hasNext()) {
         list.addAll(((Map)it.next()).values());
      }

      return list;
   }

   protected boolean addToSet(Map set, Object object) {
      Object existing = set.get(object);
      if (existing == null) {
         set.put(object, object);
         return true;
      } else {
         return existing == object;
      }
   }

   protected final Map getSet(Object key) {
      return (Map)this.get(key);
   }
}
