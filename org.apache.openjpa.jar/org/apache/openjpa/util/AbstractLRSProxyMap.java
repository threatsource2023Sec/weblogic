package org.apache.openjpa.util;

import java.io.ObjectStreamException;
import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.iterators.FilterIterator;
import org.apache.commons.collections.iterators.IteratorChain;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.lib.util.Closeable;
import org.apache.openjpa.lib.util.Localizer;

public abstract class AbstractLRSProxyMap implements Map, LRSProxy, MapChangeTracker, Predicate {
   private static final int MODE_KEY = 0;
   private static final int MODE_VALUE = 1;
   private static final int MODE_ENTRY = 2;
   private static final Localizer _loc = Localizer.forPackage(AbstractLRSProxyMap.class);
   private Class _keyType = null;
   private Class _valueType = null;
   private MapChangeTrackerImpl _ct = null;
   private OpenJPAStateManager _sm = null;
   private int _field = -1;
   private OpenJPAStateManager _origOwner = null;
   private int _origField = -1;
   private Map _map = null;
   private int _count = -1;
   private boolean _iterated = false;

   public AbstractLRSProxyMap(Class keyType, Class valueType) {
      this._keyType = keyType;
      this._valueType = valueType;
      this._ct = new MapChangeTrackerImpl(this, false);
      this._ct.setAutoOff(false);
   }

   public void setOwner(OpenJPAStateManager sm, int field) {
      if (sm == null || this._origOwner == null || this._origOwner == sm && this._origField == field) {
         this._sm = sm;
         this._field = field;
         if (sm != null) {
            this._origOwner = sm;
            this._origField = field;
         }

      } else {
         throw new InvalidStateException(_loc.get("transfer-lrs", (Object)this._origOwner.getMetaData().getField(this._origField)));
      }
   }

   public OpenJPAStateManager getOwner() {
      return this._sm;
   }

   public int getOwnerField() {
      return this._field;
   }

   public ChangeTracker getChangeTracker() {
      return this;
   }

   public Object copy(Object orig) {
      return null;
   }

   boolean isIterated() {
      return this._iterated;
   }

   void setIterated(boolean it) {
      this._iterated = it;
   }

   public int size() {
      if (this._count == -1) {
         this._count = this.count();
      }

      return this._count == Integer.MAX_VALUE ? this._count : this._count + this._ct.getAdded().size() - this._ct.getRemoved().size();
   }

   public boolean isEmpty() {
      return this.size() == 0;
   }

   public boolean containsKey(Object key) {
      if (this._keyType != null && !this._keyType.isInstance(key)) {
         return false;
      } else if (this._map != null && this._map.containsKey(key)) {
         return true;
      } else if (this._ct.getTrackKeys()) {
         return this._ct.getRemoved().contains(key) ? false : this.hasKey(key);
      } else if (this._ct.getRemoved().isEmpty()) {
         return this.hasKey(key);
      } else {
         return this.get(key) != null;
      }
   }

   public boolean containsValue(Object val) {
      if (this._valueType != null && !this._valueType.isInstance(val)) {
         return false;
      } else if (this._map != null && this._map.containsValue(val)) {
         return true;
      } else if (!this._ct.getTrackKeys()) {
         return this._ct.getRemoved().contains(val) ? false : this.hasValue(val);
      } else {
         Collection keys = this.keys(val);
         if (keys != null && !keys.isEmpty()) {
            keys.removeAll(this._ct.getRemoved());
            keys.removeAll(this._ct.getChanged());
            return keys.size() > 0;
         } else {
            return false;
         }
      }
   }

   public Object get(Object key) {
      if (this._keyType != null && !this._keyType.isInstance(key)) {
         return null;
      } else {
         Object ret = this._map == null ? null : this._map.get(key);
         if (ret != null) {
            return ret;
         } else if (this._ct.getTrackKeys() && this._ct.getRemoved().contains(key)) {
            return null;
         } else {
            Object val = this.value(key);
            return !this._ct.getTrackKeys() && this._ct.getRemoved().contains(val) ? null : val;
         }
      }
   }

   public Object put(Object key, Object value) {
      Proxies.assertAllowedType(key, this._keyType);
      Proxies.assertAllowedType(value, this._valueType);
      Proxies.dirty(this, false);
      if (this._map == null) {
         this._map = new HashMap();
      }

      Object old = this._map.put(key, value);
      if (old == null && (!this._ct.getTrackKeys() || !this._ct.getRemoved().contains(key))) {
         old = this.value(key);
      }

      if (old != null) {
         this._ct.changed(key, old, value);
         Proxies.removed(this, old, false);
      } else {
         this._ct.added(key, value);
      }

      return old;
   }

   public void putAll(Map m) {
      Iterator itr = m.entrySet().iterator();

      while(itr.hasNext()) {
         Map.Entry entry = (Map.Entry)itr.next();
         this.put(entry.getKey(), entry.getValue());
      }

   }

   public Object remove(Object key) {
      Proxies.dirty(this, false);
      Object old = this._map == null ? null : this._map.remove(key);
      if (old == null && (!this._ct.getTrackKeys() || !this._ct.getRemoved().contains(key))) {
         old = this.value(key);
      }

      if (old != null) {
         this._ct.removed(key, old);
         Proxies.removed(this, key, true);
         Proxies.removed(this, old, false);
      }

      return old;
   }

   public void clear() {
      Proxies.dirty(this, false);
      Itr itr = this.iterator(2);

      try {
         while(itr.hasNext()) {
            Map.Entry entry = (Map.Entry)itr.next();
            Proxies.removed(this, entry.getKey(), true);
            Proxies.removed(this, entry.getValue(), false);
            this._ct.removed(entry.getKey(), entry.getValue());
         }
      } finally {
         itr.close();
      }

   }

   public Set keySet() {
      return new AbstractSet() {
         public int size() {
            return AbstractLRSProxyMap.this.size();
         }

         public boolean remove(Object o) {
            return AbstractLRSProxyMap.this.remove(o) != null;
         }

         public Iterator iterator() {
            return AbstractLRSProxyMap.this.iterator(0);
         }
      };
   }

   public Collection values() {
      return new AbstractCollection() {
         public int size() {
            return AbstractLRSProxyMap.this.size();
         }

         public Iterator iterator() {
            return AbstractLRSProxyMap.this.iterator(1);
         }
      };
   }

   public Set entrySet() {
      return new AbstractSet() {
         public int size() {
            return AbstractLRSProxyMap.this.size();
         }

         public Iterator iterator() {
            return AbstractLRSProxyMap.this.iterator(2);
         }
      };
   }

   protected Object writeReplace() throws ObjectStreamException {
      Itr itr = this.iterator(2);

      try {
         Map map = new HashMap();

         while(itr.hasNext()) {
            Map.Entry entry = (Map.Entry)itr.next();
            map.put(entry.getKey(), entry.getValue());
         }

         HashMap var4 = map;
         return var4;
      } finally {
         itr.close();
      }
   }

   protected abstract boolean hasKey(Object var1);

   protected abstract boolean hasValue(Object var1);

   protected abstract Collection keys(Object var1);

   protected abstract Object value(Object var1);

   protected abstract Iterator itr();

   protected abstract int count();

   private Itr iterator(int mode) {
      this._iterated = true;
      IteratorChain chain = new IteratorChain();
      if (this._map != null) {
         chain.addIterator((new ArrayList(this._map.entrySet())).iterator());
      }

      chain.addIterator(new FilterIterator(this.itr(), this));
      return new Itr(mode, chain);
   }

   public boolean evaluate(Object obj) {
      Map.Entry entry = (Map.Entry)obj;
      return (this._ct.getTrackKeys() && !this._ct.getRemoved().contains(entry.getKey()) || !this._ct.getTrackKeys() && !this._ct.getRemoved().contains(entry.getValue())) && (this._map == null || !this._map.containsKey(entry.getKey()));
   }

   public boolean isTracking() {
      return this._ct.isTracking();
   }

   public void startTracking() {
      this._ct.startTracking();
      this.reset();
   }

   public void stopTracking() {
      this._ct.stopTracking();
      this.reset();
   }

   private void reset() {
      if (this._map != null) {
         this._map.clear();
      }

      if (this._count != Integer.MAX_VALUE) {
         this._count = -1;
      }

   }

   public boolean getTrackKeys() {
      return this._ct.getTrackKeys();
   }

   public void setTrackKeys(boolean keys) {
      this._ct.setTrackKeys(keys);
   }

   public Collection getAdded() {
      return this._ct.getAdded();
   }

   public Collection getRemoved() {
      return this._ct.getRemoved();
   }

   public Collection getChanged() {
      return this._ct.getChanged();
   }

   public void added(Object key, Object val) {
      this._ct.added(key, val);
   }

   public void removed(Object key, Object val) {
      this._ct.removed(key, val);
   }

   public void changed(Object key, Object orig, Object val) {
      this._ct.changed(key, orig, val);
   }

   public int getNextSequence() {
      return this._ct.getNextSequence();
   }

   public void setNextSequence(int seq) {
      this._ct.setNextSequence(seq);
   }

   private class Itr implements Iterator, Closeable {
      private static final int OPEN = 0;
      private static final int LAST_ELEM = 1;
      private static final int CLOSED = 2;
      private final int _mode;
      private final IteratorChain _itr;
      private Map.Entry _last = null;
      private int _state = 0;

      public Itr(int mode, IteratorChain itr) {
         this._mode = mode;
         this._itr = itr;
      }

      public boolean hasNext() {
         if (this._state != 0) {
            return false;
         } else if (!this._itr.hasNext()) {
            this.free();
            this._state = 1;
            return false;
         } else {
            return true;
         }
      }

      public Object next() {
         if (this._state != 0) {
            throw new NoSuchElementException();
         } else {
            this._last = (Map.Entry)this._itr.next();
            switch (this._mode) {
               case 0:
                  return this._last.getKey();
               case 1:
                  return this._last.getValue();
               default:
                  return this._last;
            }
         }
      }

      public void remove() {
         if (this._state != 2 && this._last != null) {
            Proxies.dirty(AbstractLRSProxyMap.this, false);
            Proxies.removed(AbstractLRSProxyMap.this, this._last.getKey(), true);
            Proxies.removed(AbstractLRSProxyMap.this, this._last.getValue(), false);
            Object key = this._last.getKey();
            Object value = this._last.getValue();
            if (AbstractLRSProxyMap.this._map != null) {
               AbstractLRSProxyMap.this._map.remove(key);
            }

            AbstractLRSProxyMap.this._ct.removed(key, value);
            this._last = null;
         } else {
            throw new NoSuchElementException();
         }
      }

      public void close() {
         this.free();
         this._state = 2;
      }

      private void free() {
         if (this._state == 0) {
            List itrs = this._itr.getIterators();

            for(int i = 0; i < itrs.size(); ++i) {
               Iterator itr = (Iterator)itrs.get(i);
               if (itr instanceof FilterIterator) {
                  itr = ((FilterIterator)itr).getIterator();
               }

               ImplHelper.close(itr);
            }

         }
      }

      protected void finalize() {
         this.close();
      }
   }
}
