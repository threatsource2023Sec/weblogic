package org.python.google.common.collect;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.base.Objects;
import org.python.google.common.base.Preconditions;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;
import org.python.google.j2objc.annotations.RetainedWith;

@GwtCompatible(
   emulated = true
)
abstract class AbstractBiMap extends ForwardingMap implements BiMap, Serializable {
   private transient Map delegate;
   @RetainedWith
   transient AbstractBiMap inverse;
   private transient Set keySet;
   private transient Set valueSet;
   private transient Set entrySet;
   @GwtIncompatible
   private static final long serialVersionUID = 0L;

   AbstractBiMap(Map forward, Map backward) {
      this.setDelegates(forward, backward);
   }

   private AbstractBiMap(Map backward, AbstractBiMap forward) {
      this.delegate = backward;
      this.inverse = forward;
   }

   protected Map delegate() {
      return this.delegate;
   }

   @CanIgnoreReturnValue
   Object checkKey(@Nullable Object key) {
      return key;
   }

   @CanIgnoreReturnValue
   Object checkValue(@Nullable Object value) {
      return value;
   }

   void setDelegates(Map forward, Map backward) {
      Preconditions.checkState(this.delegate == null);
      Preconditions.checkState(this.inverse == null);
      Preconditions.checkArgument(forward.isEmpty());
      Preconditions.checkArgument(backward.isEmpty());
      Preconditions.checkArgument(forward != backward);
      this.delegate = forward;
      this.inverse = this.makeInverse(backward);
   }

   AbstractBiMap makeInverse(Map backward) {
      return new Inverse(backward, this);
   }

   void setInverse(AbstractBiMap inverse) {
      this.inverse = inverse;
   }

   public boolean containsValue(@Nullable Object value) {
      return this.inverse.containsKey(value);
   }

   @CanIgnoreReturnValue
   public Object put(@Nullable Object key, @Nullable Object value) {
      return this.putInBothMaps(key, value, false);
   }

   @CanIgnoreReturnValue
   public Object forcePut(@Nullable Object key, @Nullable Object value) {
      return this.putInBothMaps(key, value, true);
   }

   private Object putInBothMaps(@Nullable Object key, @Nullable Object value, boolean force) {
      this.checkKey(key);
      this.checkValue(value);
      boolean containedKey = this.containsKey(key);
      if (containedKey && Objects.equal(value, this.get(key))) {
         return value;
      } else {
         if (force) {
            this.inverse().remove(value);
         } else {
            Preconditions.checkArgument(!this.containsValue(value), "value already present: %s", value);
         }

         Object oldValue = this.delegate.put(key, value);
         this.updateInverseMap(key, containedKey, oldValue, value);
         return oldValue;
      }
   }

   private void updateInverseMap(Object key, boolean containedKey, Object oldValue, Object newValue) {
      if (containedKey) {
         this.removeFromInverseMap(oldValue);
      }

      this.inverse.delegate.put(newValue, key);
   }

   @CanIgnoreReturnValue
   public Object remove(@Nullable Object key) {
      return this.containsKey(key) ? this.removeFromBothMaps(key) : null;
   }

   @CanIgnoreReturnValue
   private Object removeFromBothMaps(Object key) {
      Object oldValue = this.delegate.remove(key);
      this.removeFromInverseMap(oldValue);
      return oldValue;
   }

   private void removeFromInverseMap(Object oldValue) {
      this.inverse.delegate.remove(oldValue);
   }

   public void putAll(Map map) {
      Iterator var2 = map.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry entry = (Map.Entry)var2.next();
         this.put(entry.getKey(), entry.getValue());
      }

   }

   public void clear() {
      this.delegate.clear();
      this.inverse.delegate.clear();
   }

   public BiMap inverse() {
      return this.inverse;
   }

   public Set keySet() {
      Set result = this.keySet;
      return result == null ? (this.keySet = new KeySet()) : result;
   }

   public Set values() {
      Set result = this.valueSet;
      return result == null ? (this.valueSet = new ValueSet()) : result;
   }

   public Set entrySet() {
      Set result = this.entrySet;
      return result == null ? (this.entrySet = new EntrySet()) : result;
   }

   Iterator entrySetIterator() {
      final Iterator iterator = this.delegate.entrySet().iterator();
      return new Iterator() {
         Map.Entry entry;

         public boolean hasNext() {
            return iterator.hasNext();
         }

         public Map.Entry next() {
            this.entry = (Map.Entry)iterator.next();
            return AbstractBiMap.this.new BiMapEntry(this.entry);
         }

         public void remove() {
            CollectPreconditions.checkRemove(this.entry != null);
            Object value = this.entry.getValue();
            iterator.remove();
            AbstractBiMap.this.removeFromInverseMap(value);
         }
      };
   }

   // $FF: synthetic method
   AbstractBiMap(Map x0, AbstractBiMap x1, Object x2) {
      this(x0, x1);
   }

   static class Inverse extends AbstractBiMap {
      @GwtIncompatible
      private static final long serialVersionUID = 0L;

      Inverse(Map backward, AbstractBiMap forward) {
         super(backward, forward, null);
      }

      Object checkKey(Object key) {
         return this.inverse.checkValue(key);
      }

      Object checkValue(Object value) {
         return this.inverse.checkKey(value);
      }

      @GwtIncompatible
      private void writeObject(ObjectOutputStream stream) throws IOException {
         stream.defaultWriteObject();
         stream.writeObject(this.inverse());
      }

      @GwtIncompatible
      private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
         stream.defaultReadObject();
         this.setInverse((AbstractBiMap)stream.readObject());
      }

      @GwtIncompatible
      Object readResolve() {
         return this.inverse().inverse();
      }
   }

   private class EntrySet extends ForwardingSet {
      final Set esDelegate;

      private EntrySet() {
         this.esDelegate = AbstractBiMap.this.delegate.entrySet();
      }

      protected Set delegate() {
         return this.esDelegate;
      }

      public void clear() {
         AbstractBiMap.this.clear();
      }

      public boolean remove(Object object) {
         if (!this.esDelegate.contains(object)) {
            return false;
         } else {
            Map.Entry entry = (Map.Entry)object;
            AbstractBiMap.this.inverse.delegate.remove(entry.getValue());
            this.esDelegate.remove(entry);
            return true;
         }
      }

      public Iterator iterator() {
         return AbstractBiMap.this.entrySetIterator();
      }

      public Object[] toArray() {
         return this.standardToArray();
      }

      public Object[] toArray(Object[] array) {
         return this.standardToArray(array);
      }

      public boolean contains(Object o) {
         return Maps.containsEntryImpl(this.delegate(), o);
      }

      public boolean containsAll(Collection c) {
         return this.standardContainsAll(c);
      }

      public boolean removeAll(Collection c) {
         return this.standardRemoveAll(c);
      }

      public boolean retainAll(Collection c) {
         return this.standardRetainAll(c);
      }

      // $FF: synthetic method
      EntrySet(Object x1) {
         this();
      }
   }

   class BiMapEntry extends ForwardingMapEntry {
      private final Map.Entry delegate;

      BiMapEntry(Map.Entry delegate) {
         this.delegate = delegate;
      }

      protected Map.Entry delegate() {
         return this.delegate;
      }

      public Object setValue(Object value) {
         Preconditions.checkState(AbstractBiMap.this.entrySet().contains(this), "entry no longer in map");
         if (Objects.equal(value, this.getValue())) {
            return value;
         } else {
            Preconditions.checkArgument(!AbstractBiMap.this.containsValue(value), "value already present: %s", value);
            Object oldValue = this.delegate.setValue(value);
            Preconditions.checkState(Objects.equal(value, AbstractBiMap.this.get(this.getKey())), "entry no longer in map");
            AbstractBiMap.this.updateInverseMap(this.getKey(), true, oldValue, value);
            return oldValue;
         }
      }
   }

   private class ValueSet extends ForwardingSet {
      final Set valuesDelegate;

      private ValueSet() {
         this.valuesDelegate = AbstractBiMap.this.inverse.keySet();
      }

      protected Set delegate() {
         return this.valuesDelegate;
      }

      public Iterator iterator() {
         return Maps.valueIterator(AbstractBiMap.this.entrySet().iterator());
      }

      public Object[] toArray() {
         return this.standardToArray();
      }

      public Object[] toArray(Object[] array) {
         return this.standardToArray(array);
      }

      public String toString() {
         return this.standardToString();
      }

      // $FF: synthetic method
      ValueSet(Object x1) {
         this();
      }
   }

   private class KeySet extends ForwardingSet {
      private KeySet() {
      }

      protected Set delegate() {
         return AbstractBiMap.this.delegate.keySet();
      }

      public void clear() {
         AbstractBiMap.this.clear();
      }

      public boolean remove(Object key) {
         if (!this.contains(key)) {
            return false;
         } else {
            AbstractBiMap.this.removeFromBothMaps(key);
            return true;
         }
      }

      public boolean removeAll(Collection keysToRemove) {
         return this.standardRemoveAll(keysToRemove);
      }

      public boolean retainAll(Collection keysToRetain) {
         return this.standardRetainAll(keysToRetain);
      }

      public Iterator iterator() {
         return Maps.keyIterator(AbstractBiMap.this.entrySet().iterator());
      }

      // $FF: synthetic method
      KeySet(Object x1) {
         this();
      }
   }
}
