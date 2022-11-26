package weblogic.descriptor.internal;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

class WeakSetMap extends SetMap {
   private static boolean ENFORCE_UNIQUENESS = true;
   private static final Comparator REF_COMPARATOR = new Comparator() {
      public int compare(Object o1, Object o2) {
         Comparable actual1 = (Comparable)((WeakReference)o1).get();
         Object actual2 = ((Reference)o2).get();
         if (actual1 == null && actual2 == null) {
            return System.identityHashCode(actual1) - System.identityHashCode(actual2);
         } else if (actual1 == null) {
            return -1;
         } else if (actual2 == null) {
            return 1;
         } else {
            return WeakSetMap.ENFORCE_UNIQUENESS ? actual1.compareTo(actual2) : System.identityHashCode(actual1) - System.identityHashCode(actual2);
         }
      }
   };

   public WeakSetMap() {
      super(REF_COMPARATOR);
   }

   public final Iterator getSetIterator(Object key) {
      Map set = this.getSet(key);
      if (set != null) {
         List list = new ArrayList();
         Iterator it = set.values().iterator();

         while(it.hasNext()) {
            Reference ref = (Reference)it.next();
            if (ref.get() == null) {
               it.remove();
            } else {
               list.add(ref.get());
            }
         }

         if (list.size() != 0) {
            return list.iterator();
         }

         this.remove(key);
      }

      return Collections.EMPTY_LIST.iterator();
   }

   public final boolean putInSet(Object key, Object object) {
      boolean result = super.putInSet(key, new WeakReference(object));
      return result;
   }

   public final void removeFromSet(Object key, Object object) {
      super.removeFromSet(key, new WeakReference(object));
   }

   protected boolean addToSet(Map set, Object object) {
      Reference existing = (Reference)set.get(object);
      if (existing == null) {
         set.put(object, object);
         return true;
      } else {
         return existing.get() == ((Reference)object).get();
      }
   }
}
