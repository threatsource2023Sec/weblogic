package weblogic.utils.collections;

import java.io.Serializable;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public final class SoftHashMap extends AbstractMap implements Map, Cloneable, Serializable {
   private static final long serialVersionUID = -6634836669858888039L;
   private final transient Map hash;
   private final transient ReferenceQueue queue;
   private Set entrySet;

   public SoftHashMap(int initialCapacity, float loadFactor) {
      this.queue = new ReferenceQueue();
      this.entrySet = null;
      this.hash = new HashMap(initialCapacity, loadFactor);
   }

   public SoftHashMap(int initialCapacity) {
      this.queue = new ReferenceQueue();
      this.entrySet = null;
      this.hash = new HashMap(initialCapacity);
   }

   public SoftHashMap() {
      this.queue = new ReferenceQueue();
      this.entrySet = null;
      this.hash = new HashMap();
   }

   public SoftHashMap(Map map) {
      this();
      this.putAll(map);
   }

   public Object clone() {
      try {
         Map m = new SoftHashMap();
         m.putAll(this);
         return m;
      } catch (Exception var2) {
         return new SoftHashMap(this);
      }
   }

   public int size() {
      int j = 0;
      Iterator i = this.hash.entrySet().iterator();

      while(true) {
         SoftValue ref;
         do {
            if (!i.hasNext()) {
               return j;
            }

            Map.Entry entry = (Map.Entry)i.next();
            ref = (SoftValue)entry.getValue();
         } while(ref != null && ref.get() == null);

         ++j;
      }
   }

   public boolean isEmpty() {
      Iterator i = this.hash.entrySet().iterator();

      SoftValue ref;
      do {
         if (!i.hasNext()) {
            return true;
         }

         Map.Entry entry = (Map.Entry)i.next();
         ref = (SoftValue)entry.getValue();
      } while(ref != null && ref.get() == null);

      return false;
   }

   public boolean containsKey(Object key) {
      if (!this.hash.containsKey(key)) {
         return false;
      } else {
         SoftValue ref = (SoftValue)this.hash.get(key);
         return ref == null || ref.get() != null;
      }
   }

   public boolean containsValue(Object value) {
      Object val = null;
      Iterator i = this.hash.entrySet().iterator();

      SoftValue ref;
      do {
         do {
            if (!i.hasNext()) {
               return false;
            }

            Map.Entry entry = (Map.Entry)i.next();
            ref = (SoftValue)entry.getValue();
            if (value == null && ref == null) {
               return true;
            }
         } while(ref != null && (val = ref.get()) == null);
      } while(value == null || !val.equals(value));

      return true;
   }

   public Object get(Object key) {
      SoftValue ref = (SoftValue)this.hash.get(key);
      return ref == null ? null : ref.get();
   }

   public Object put(Object key, Object value) {
      this.processQueue();
      SoftValue o = (SoftValue)this.hash.put(key, SoftHashMap.SoftValue.create(key, value, this.queue));
      if (o == null) {
         return null;
      } else {
         Object val = o.get();
         if (val != null) {
            o.clear();
         }

         return val;
      }
   }

   public Object remove(Object key) {
      this.processQueue();
      SoftValue ref = (SoftValue)this.hash.remove(key);
      if (ref != null) {
         Object o = ref.get();
         if (o != null) {
            ref.clear();
         }

         return o;
      } else {
         return null;
      }
   }

   public void clear() {
      this.processQueue();
      this.hash.clear();
   }

   private void processQueue() {
      SoftValue sv;
      while((sv = (SoftValue)this.queue.poll()) != null) {
         Object key = sv.getKey();
         this.hash.remove(key);
         sv.resetKey();
         sv = null;
      }

   }

   public Set entrySet() {
      if (this.entrySet == null) {
         this.entrySet = new EntrySet();
      }

      return this.entrySet;
   }

   private static final class SoftValue extends SoftReference {
      private final int hash;
      private Object key;

      private SoftValue(Object key, Object value, ReferenceQueue q) {
         super(value, q);
         this.key = key;
         this.hash = value.hashCode();
      }

      private static SoftValue create(Object k, Object v, ReferenceQueue q) {
         return v == null ? null : new SoftValue(k, v, q);
      }

      Object getKey() {
         return this.key;
      }

      void resetKey() {
         this.key = null;
      }

      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (!(o instanceof SoftValue)) {
            return false;
         } else {
            Object t = this.get();
            Object u = ((SoftValue)o).get();
            if (t != null && u != null) {
               return t == u ? true : t.equals(u);
            } else {
               return false;
            }
         }
      }

      public int hashCode() {
         return this.hash;
      }
   }

   private final class EntrySet extends AbstractSet implements Serializable {
      private static final long serialVersionUID = 151647111930348035L;
      final Set hashEntrySet;

      private EntrySet() {
         this.hashEntrySet = SoftHashMap.this.hash.entrySet();
      }

      public Iterator iterator() {
         return new Iterator() {
            final Iterator hashIterator;
            Entry next;

            {
               this.hashIterator = EntrySet.this.hashEntrySet.iterator();
               this.next = null;
            }

            public boolean hasNext() {
               while(true) {
                  if (this.hashIterator.hasNext()) {
                     Map.Entry ent = (Map.Entry)this.hashIterator.next();
                     SoftValue ref = (SoftValue)ent.getValue();
                     Object v = null;
                     if (ref != null && (v = ref.get()) == null) {
                        continue;
                     }

                     this.next = SoftHashMap.this.new Entry(ent, v);
                     return true;
                  }

                  return false;
               }
            }

            public Map.Entry next() {
               if (this.next == null && !this.hasNext()) {
                  throw new NoSuchElementException();
               } else {
                  Entry e = this.next;
                  this.next = null;
                  return e;
               }
            }

            public void remove() {
               SoftHashMap.this.processQueue();
               this.hashIterator.remove();
            }
         };
      }

      public boolean isEmpty() {
         return !this.iterator().hasNext();
      }

      public int size() {
         int j = 0;
         Iterator i = this.iterator();

         while(i.hasNext()) {
            ++j;
            i.next();
         }

         return j;
      }

      public boolean remove(Object o) {
         SoftHashMap.this.processQueue();
         if (o != null && o instanceof Map.Entry) {
            Map.Entry e = (Map.Entry)o;
            return SoftHashMap.this.remove(e.getKey()) != null;
         } else {
            return false;
         }
      }

      public int hashCode() {
         int h = 0;

         Object key;
         Object v;
         for(Iterator i = this.hashEntrySet.iterator(); i.hasNext(); h += key.hashCode() ^ (v == null ? 0 : v.hashCode())) {
            Map.Entry ent = (Map.Entry)i.next();
            key = ent.getKey();
            SoftValue ref = (SoftValue)ent.getValue();
            v = ref.get();
         }

         return h;
      }

      // $FF: synthetic method
      EntrySet(Object x1) {
         this();
      }
   }

   private final class Entry implements Map.Entry {
      private final Map.Entry ent;
      private Object val;

      Entry(Map.Entry ent, Object val) {
         this.ent = ent;
         this.val = val;
      }

      public Object getKey() {
         return this.ent.getKey();
      }

      public Object getValue() {
         return this.val;
      }

      public Object setValue(Object value) {
         this.val = value;
         return ((SoftValue)this.ent.setValue(SoftHashMap.SoftValue.create(this.getKey(), value, SoftHashMap.this.queue))).get();
      }

      private boolean valEquals(Object o1, Object o2) {
         return o1 == null ? o2 == null : o1.equals(o2);
      }

      public boolean equals(Object o) {
         if (o != null && o instanceof Map.Entry) {
            Map.Entry e = (Map.Entry)o;
            return this.valEquals(this.getKey(), e.getKey()) && this.valEquals(this.getValue(), e.getValue());
         } else {
            return false;
         }
      }

      public int hashCode() {
         Object v;
         return (this.getKey() == null ? 0 : this.getKey().hashCode()) ^ ((v = this.getValue()) == null ? 0 : v.hashCode());
      }
   }
}
