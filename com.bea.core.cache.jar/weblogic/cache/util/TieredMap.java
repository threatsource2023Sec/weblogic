package weblogic.cache.util;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import weblogic.cache.CacheMap;
import weblogic.cache.EvictionListener;

public class TieredMap implements Map {
   private static final Object NULL = new Object();
   private final CoherencyStrategy strategy;
   private final Map L1;
   private final Map L1_MISSES;
   private final Map L2;
   public static final int PAGING = 1;
   public static final int WRITE_THROUGH = 2;
   public static final int READ_ONLY = 4;

   public TieredMap(Map l1, Map l2) {
      this(l1, l2, 2);
   }

   public TieredMap(Map l1, Map l2, int policy) {
      this(l1, l2, policy2strategy(l1, l2, policy));
   }

   public TieredMap(Map l1, Map l2, CoherencyStrategy strategy) {
      this.L1_MISSES = new HashMap(10);
      if (l1 != null && l2 != null && strategy != null) {
         this.L1 = l1;
         this.L2 = l2;
         this.strategy = strategy;
      } else {
         throw new NullPointerException("Invalid backing map");
      }
   }

   private static CoherencyStrategy policy2strategy(Map l1, Map l2, int policy) {
      if (l1 != null && l2 != null) {
         switch (policy) {
            case 1:
               CoherencyStrategy cstrategy = new PagingEvictionListenerCoherencyStrategy(l2);
               if (l1 instanceof CacheMap) {
                  ((CacheMap)l1).addEvictionListener((EvictionListener)cstrategy);
                  return cstrategy;
               }

               throw new IllegalArgumentException("Paging requires a CacheMap L1");
            case 2:
               return new WriteThroughCoherencyStrategy(l2);
            case 3:
            default:
               throw new IllegalArgumentException("Unknown policy: '" + policy + "'");
            case 4:
               return new ReadOnlyCoherencyStrategy();
         }
      } else {
         throw new NullPointerException("Arguments cannot be null");
      }
   }

   public Map getL1() {
      return this.L1;
   }

   public Map getL2() {
      return this.L2;
   }

   public boolean equals(Object o) {
      if (o != null && o instanceof TieredMap) {
         TieredMap other = (TieredMap)o;
         return other.L1.equals(this.L1) && other.L2.equals(this.L2);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.L1.hashCode() ^ this.L2.hashCode();
   }

   public int size() {
      if (this.L2.size() == 0) {
         return this.L1.size();
      } else if (this.L1.size() == 0) {
         return this.L2.size();
      } else {
         int n = this.L1.size() + this.L2.size();
         Iterator it = this.L1.keySet().iterator();

         while(it.hasNext()) {
            if (this.L2.containsKey(it.next())) {
               --n;
            }
         }

         return n;
      }
   }

   public boolean isEmpty() {
      return this.L1.isEmpty() && this.L2.isEmpty();
   }

   public Object get(Object key) {
      Object value = this.L1.get(key);
      if (value == null) {
         if (this.L1_MISSES.get(key) != null) {
            return null;
         }

         value = this.L2.get(key);
         if (value == null) {
            this.L1_MISSES.put(key, NULL);
            return null;
         }

         this.L1.put(key, value);
      }

      return value;
   }

   public boolean containsKey(Object key) {
      return this.L1.containsKey(key) || this.L2.containsKey(key);
   }

   public Object put(Object key, Object value) {
      Object old = this.strategy.onPut(key, value);
      Object old2 = this.L1.put(key, value);
      if (old == null) {
         old = old2;
      }

      if (old == null) {
         this.L1_MISSES.remove(key);
      }

      return old;
   }

   public Object putIfAbsent(Object key, Object value) {
      throw new UnsupportedOperationException();
   }

   public void putAll(Map t) {
      this.strategy.onPutAll(t);
      this.L1_MISSES.clear();
      this.L1.putAll(t);
   }

   public Object remove(Object key) {
      Object o = this.strategy.onRemove(key);
      Object old = this.L1.remove(key);
      if (old == null) {
         old = o;
      }

      return old;
   }

   public void clear() {
      this.strategy.onClear();
      this.L1.clear();
   }

   public boolean containsValue(Object value) {
      return this.L1.containsValue(value) || this.L2.containsValue(value);
   }

   public Set keySet() {
      return new CombinedUniqueSet(this.L1.keySet(), this.L2.keySet());
   }

   public Collection values() {
      return new CombinedUniqueSet(this.L1.values(), this.L2.values());
   }

   public Set entrySet() {
      return new CombinedUniqueSet(this.L1.entrySet(), this.L2.entrySet());
   }

   final class CombinedUniqueSet extends AbstractSet {
      private final Collection collection1;
      private final Collection collection2;
      private final Collection combined;

      public CombinedUniqueSet(Collection c1, Collection c2) {
         this.collection1 = c1;
         this.collection2 = c2;
         this.combined = new HashSet();
         this.combined.addAll(c1);
         this.combined.addAll(c2);
      }

      public Iterator iterator() {
         return new Iterator() {
            private Iterator it;
            private Object cursor;

            {
               this.it = CombinedUniqueSet.this.combined.iterator();
            }

            public boolean hasNext() {
               return this.it.hasNext();
            }

            public Object next() {
               this.cursor = this.it.next();
               return this.cursor;
            }

            public void remove() {
               if (this.cursor != null) {
                  CombinedUniqueSet.this.collection1.remove(this.cursor);
                  CombinedUniqueSet.this.collection2.remove(this.cursor);
                  this.it.remove();
               }
            }
         };
      }

      public int size() {
         return this.combined.size();
      }
   }

   public interface CoherencyStrategy {
      Object onPut(Object var1, Object var2);

      void onPutAll(Map var1);

      Object onRemove(Object var1);

      void onClear();
   }
}
