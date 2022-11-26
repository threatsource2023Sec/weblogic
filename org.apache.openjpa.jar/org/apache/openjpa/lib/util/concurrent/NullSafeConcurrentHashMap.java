package org.apache.openjpa.lib.util.concurrent;

import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.collections.set.MapBackedSet;

public class NullSafeConcurrentHashMap extends ConcurrentHashMap {
   private Set randomKeys;
   private Random random;

   public NullSafeConcurrentHashMap(int size, float load, int concurrencyLevel) {
      super(size, load, concurrencyLevel);
      this.randomKeys = MapBackedSet.decorate(new ConcurrentHashMap(), NullSafeConcurrentHashMap.Markers.MAP_BACKED_SET_DUMMY_VAL);
      this.random = new Random();
   }

   public NullSafeConcurrentHashMap() {
      this.randomKeys = MapBackedSet.decorate(new ConcurrentHashMap(), NullSafeConcurrentHashMap.Markers.MAP_BACKED_SET_DUMMY_VAL);
      this.random = new Random();
   }

   private static Object maskNull(Object o) {
      return o == null ? NullSafeConcurrentHashMap.Markers.NULL : o;
   }

   private static Object unmaskNull(Object o) {
      return o == NullSafeConcurrentHashMap.Markers.NULL ? null : o;
   }

   public Map.Entry removeRandom() {
      Iterator iter = this.randomKeys.iterator();

      Object key;
      Object val;
      while(iter.hasNext()) {
         key = iter.next();
         if (key != null && this.randomKeys.remove(key)) {
            val = super.remove(key);
            if (val != null) {
               return new EntryImpl(unmaskNull(key), unmaskNull(val));
            }
         }
      }

      iter = super.keySet().iterator();

      while(iter.hasNext()) {
         key = iter.next();
         if (key != null) {
            val = super.remove(key);
            if (val != null) {
               return new EntryImpl(unmaskNull(key), unmaskNull(val));
            }
         }
      }

      return null;
   }

   public Iterator randomEntryIterator() {
      return new Iterator() {
         Iterator randomIter;
         Iterator nonRandomIter;
         Set returned;
         Map.Entry next;
         boolean nextSet;

         {
            this.randomIter = NullSafeConcurrentHashMap.this.randomKeys.iterator();
            this.nonRandomIter = NullSafeConcurrentHashMap.super.keySet().iterator();
            this.returned = new HashSet();
            this.nextSet = false;
         }

         public boolean hasNext() {
            if (this.nextSet) {
               return true;
            } else {
               Object nextKey;
               Object nextValue;
               if (this.randomIter.hasNext()) {
                  nextKey = this.randomIter.next();
                  nextValue = NullSafeConcurrentHashMap.super.get(nextKey);
                  if (nextValue != null) {
                     this.returned.add(nextKey);
                     this.next = NullSafeConcurrentHashMap.this.new EntryImpl(NullSafeConcurrentHashMap.unmaskNull(nextKey), NullSafeConcurrentHashMap.unmaskNull(nextValue));
                     this.nextSet = true;
                     return true;
                  }
               }

               while(this.nonRandomIter.hasNext()) {
                  nextKey = this.nonRandomIter.next();
                  if (!this.returned.contains(nextKey)) {
                     nextValue = NullSafeConcurrentHashMap.super.get(nextKey);
                     if (nextValue != null) {
                        this.returned.add(nextKey);
                        this.next = NullSafeConcurrentHashMap.this.new EntryImpl(NullSafeConcurrentHashMap.unmaskNull(nextKey), NullSafeConcurrentHashMap.unmaskNull(nextValue));
                        this.nextSet = true;
                        return true;
                     }
                  }
               }

               return false;
            }
         }

         public Map.Entry next() {
            if (!this.nextSet && !this.hasNext()) {
               return null;
            } else {
               this.nextSet = false;
               return NullSafeConcurrentHashMap.this.containsKey(this.next.getKey()) ? this.next : this.next();
            }
         }

         public void remove() {
            throw new UnsupportedOperationException();
         }
      };
   }

   public Object remove(Object key) {
      Object maskedKey = maskNull(key);
      Object val = unmaskNull(super.remove(maskedKey));
      this.randomKeys.remove(maskedKey);
      return val;
   }

   public boolean remove(Object key, Object value) {
      Object maskedKey = maskNull(key);
      boolean val = super.remove(maskedKey, maskNull(value));
      this.randomKeys.remove(maskedKey);
      return val;
   }

   public boolean replace(Object key, Object oldValue, Object newValue) {
      return super.replace(maskNull(key), maskNull(oldValue), maskNull(newValue));
   }

   public Object replace(Object key, Object value) {
      return unmaskNull(super.replace(maskNull(key), maskNull(value)));
   }

   public Object putIfAbsent(Object key, Object value) {
      Object maskedKey = maskNull(key);
      Object superVal = super.putIfAbsent(maskedKey, maskNull(value));
      this.addRandomKey(maskedKey);
      return unmaskNull(superVal);
   }

   public Object put(Object key, Object value) {
      Object maskedKey = maskNull(key);
      Object superVal = super.put(maskedKey, maskNull(value));
      this.addRandomKey(maskedKey);
      return unmaskNull(superVal);
   }

   private void addRandomKey(Object maskedKey) {
      if (this.random != null && this.randomKeys.size() < 16 && this.random.nextInt(10) < 3) {
         this.randomKeys.add(maskedKey);
      }

   }

   public Object get(Object key) {
      return unmaskNull(super.get(maskNull(key)));
   }

   public boolean containsKey(Object key) {
      return super.containsKey(maskNull(key));
   }

   public boolean containsValue(Object value) {
      return super.containsValue(maskNull(value));
   }

   public boolean contains(Object value) {
      throw new UnsupportedOperationException();
   }

   public Enumeration elements() {
      throw new UnsupportedOperationException();
   }

   public Set entrySet() {
      return new TranslatingSet(super.entrySet()) {
         protected Object unmask(Object internal) {
            final Map.Entry e = (Map.Entry)internal;
            return new Map.Entry() {
               public Object getKey() {
                  return NullSafeConcurrentHashMap.unmaskNull(e.getKey());
               }

               public Object getValue() {
                  return NullSafeConcurrentHashMap.unmaskNull(e.getValue());
               }

               public Object setValue(Object value) {
                  return NullSafeConcurrentHashMap.unmaskNull(e.setValue(NullSafeConcurrentHashMap.maskNull(value)));
               }

               public int hashCode() {
                  return e.hashCode();
               }
            };
         }
      };
   }

   public Enumeration keys() {
      throw new UnsupportedOperationException();
   }

   public Set keySet() {
      return new TranslatingSet(super.keySet()) {
         protected Object unmask(Object internal) {
            return NullSafeConcurrentHashMap.unmaskNull(internal);
         }
      };
   }

   public Collection values() {
      return new TranslatingCollection(super.values()) {
         protected Object unmask(Object internal) {
            return NullSafeConcurrentHashMap.unmaskNull(internal);
         }
      };
   }

   public interface KeyFilter {
      boolean exclude(Object var1);
   }

   private class EntryImpl implements Map.Entry {
      final Object key;
      final Object val;

      private EntryImpl(Object key, Object val) {
         this.key = key;
         this.val = val;
      }

      public Object getKey() {
         return this.key;
      }

      public Object getValue() {
         return this.val;
      }

      public Object setValue(Object value) {
         throw new UnsupportedOperationException();
      }

      // $FF: synthetic method
      EntryImpl(Object x1, Object x2, Object x3) {
         this(x1, x2);
      }
   }

   private abstract class TranslatingCollection extends AbstractCollection {
      private Collection backingCollection;

      private TranslatingCollection(Collection backing) {
         this.backingCollection = backing;
      }

      protected abstract Object unmask(Object var1);

      public Iterator iterator() {
         final Iterator iterator = this.backingCollection.iterator();
         return new Iterator() {
            public boolean hasNext() {
               return iterator.hasNext();
            }

            public Object next() {
               return TranslatingCollection.this.unmask(iterator.next());
            }

            public void remove() {
               iterator.remove();
            }
         };
      }

      public int size() {
         return this.backingCollection.size();
      }

      // $FF: synthetic method
      TranslatingCollection(Collection x1, Object x2) {
         this(x1);
      }
   }

   private abstract class TranslatingSet extends AbstractSet {
      private Set backingSet;

      private TranslatingSet(Set backing) {
         this.backingSet = backing;
      }

      protected abstract Object unmask(Object var1);

      public Iterator iterator() {
         final Iterator iterator = this.backingSet.iterator();
         return new Iterator() {
            public boolean hasNext() {
               return iterator.hasNext();
            }

            public Object next() {
               return TranslatingSet.this.unmask(iterator.next());
            }

            public void remove() {
               iterator.remove();
            }
         };
      }

      public int size() {
         return this.backingSet.size();
      }

      // $FF: synthetic method
      TranslatingSet(Set x1, Object x2) {
         this(x1);
      }
   }

   private static enum Markers {
      NULL,
      MAP_BACKED_SET_DUMMY_VAL;
   }
}
