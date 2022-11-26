package org.python.google.common.collect;

import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.RandomAccess;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Preconditions;

@GwtCompatible
abstract class AbstractMapBasedMultimap extends AbstractMultimap implements Serializable {
   private transient Map map;
   private transient int totalSize;
   private static final long serialVersionUID = 2447537837011683357L;

   protected AbstractMapBasedMultimap(Map map) {
      Preconditions.checkArgument(map.isEmpty());
      this.map = map;
   }

   final void setMap(Map map) {
      this.map = map;
      this.totalSize = 0;

      Collection values;
      for(Iterator var2 = map.values().iterator(); var2.hasNext(); this.totalSize += values.size()) {
         values = (Collection)var2.next();
         Preconditions.checkArgument(!values.isEmpty());
      }

   }

   Collection createUnmodifiableEmptyCollection() {
      return unmodifiableCollectionSubclass(this.createCollection());
   }

   abstract Collection createCollection();

   Collection createCollection(@Nullable Object key) {
      return this.createCollection();
   }

   Map backingMap() {
      return this.map;
   }

   public int size() {
      return this.totalSize;
   }

   public boolean containsKey(@Nullable Object key) {
      return this.map.containsKey(key);
   }

   public boolean put(@Nullable Object key, @Nullable Object value) {
      Collection collection = (Collection)this.map.get(key);
      if (collection == null) {
         collection = this.createCollection(key);
         if (collection.add(value)) {
            ++this.totalSize;
            this.map.put(key, collection);
            return true;
         } else {
            throw new AssertionError("New Collection violated the Collection spec");
         }
      } else if (collection.add(value)) {
         ++this.totalSize;
         return true;
      } else {
         return false;
      }
   }

   private Collection getOrCreateCollection(@Nullable Object key) {
      Collection collection = (Collection)this.map.get(key);
      if (collection == null) {
         collection = this.createCollection(key);
         this.map.put(key, collection);
      }

      return collection;
   }

   public Collection replaceValues(@Nullable Object key, Iterable values) {
      Iterator iterator = values.iterator();
      if (!iterator.hasNext()) {
         return this.removeAll(key);
      } else {
         Collection collection = this.getOrCreateCollection(key);
         Collection oldValues = this.createCollection();
         oldValues.addAll(collection);
         this.totalSize -= collection.size();
         collection.clear();

         while(iterator.hasNext()) {
            if (collection.add(iterator.next())) {
               ++this.totalSize;
            }
         }

         return unmodifiableCollectionSubclass(oldValues);
      }
   }

   public Collection removeAll(@Nullable Object key) {
      Collection collection = (Collection)this.map.remove(key);
      if (collection == null) {
         return this.createUnmodifiableEmptyCollection();
      } else {
         Collection output = this.createCollection();
         output.addAll(collection);
         this.totalSize -= collection.size();
         collection.clear();
         return unmodifiableCollectionSubclass(output);
      }
   }

   static Collection unmodifiableCollectionSubclass(Collection collection) {
      if (collection instanceof NavigableSet) {
         return Sets.unmodifiableNavigableSet((NavigableSet)collection);
      } else if (collection instanceof SortedSet) {
         return Collections.unmodifiableSortedSet((SortedSet)collection);
      } else if (collection instanceof Set) {
         return Collections.unmodifiableSet((Set)collection);
      } else {
         return (Collection)(collection instanceof List ? Collections.unmodifiableList((List)collection) : Collections.unmodifiableCollection(collection));
      }
   }

   public void clear() {
      Iterator var1 = this.map.values().iterator();

      while(var1.hasNext()) {
         Collection collection = (Collection)var1.next();
         collection.clear();
      }

      this.map.clear();
      this.totalSize = 0;
   }

   public Collection get(@Nullable Object key) {
      Collection collection = (Collection)this.map.get(key);
      if (collection == null) {
         collection = this.createCollection(key);
      }

      return this.wrapCollection(key, collection);
   }

   Collection wrapCollection(@Nullable Object key, Collection collection) {
      if (collection instanceof NavigableSet) {
         return new WrappedNavigableSet(key, (NavigableSet)collection, (WrappedCollection)null);
      } else if (collection instanceof SortedSet) {
         return new WrappedSortedSet(key, (SortedSet)collection, (WrappedCollection)null);
      } else if (collection instanceof Set) {
         return new WrappedSet(key, (Set)collection);
      } else {
         return (Collection)(collection instanceof List ? this.wrapList(key, (List)collection, (WrappedCollection)null) : new WrappedCollection(key, collection, (WrappedCollection)null));
      }
   }

   private List wrapList(@Nullable Object key, List list, @Nullable WrappedCollection ancestor) {
      return (List)(list instanceof RandomAccess ? new RandomAccessWrappedList(key, list, ancestor) : new WrappedList(key, list, ancestor));
   }

   private static Iterator iteratorOrListIterator(Collection collection) {
      return (Iterator)(collection instanceof List ? ((List)collection).listIterator() : collection.iterator());
   }

   Set createKeySet() {
      if (this.map instanceof NavigableMap) {
         return new NavigableKeySet((NavigableMap)this.map);
      } else {
         return (Set)(this.map instanceof SortedMap ? new SortedKeySet((SortedMap)this.map) : new KeySet(this.map));
      }
   }

   private void removeValuesForKey(Object key) {
      Collection collection = (Collection)Maps.safeRemove(this.map, key);
      if (collection != null) {
         int count = collection.size();
         collection.clear();
         this.totalSize -= count;
      }

   }

   public Collection values() {
      return super.values();
   }

   Iterator valueIterator() {
      return new Itr() {
         Object output(Object key, Object value) {
            return value;
         }
      };
   }

   public Collection entries() {
      return super.entries();
   }

   Iterator entryIterator() {
      return new Itr() {
         Map.Entry output(Object key, Object value) {
            return Maps.immutableEntry(key, value);
         }
      };
   }

   Map createAsMap() {
      if (this.map instanceof NavigableMap) {
         return new NavigableAsMap((NavigableMap)this.map);
      } else {
         return (Map)(this.map instanceof SortedMap ? new SortedAsMap((SortedMap)this.map) : new AsMap(this.map));
      }
   }

   class NavigableAsMap extends SortedAsMap implements NavigableMap {
      NavigableAsMap(NavigableMap submap) {
         super(submap);
      }

      NavigableMap sortedMap() {
         return (NavigableMap)super.sortedMap();
      }

      public Map.Entry lowerEntry(Object key) {
         Map.Entry entry = this.sortedMap().lowerEntry(key);
         return entry == null ? null : this.wrapEntry(entry);
      }

      public Object lowerKey(Object key) {
         return this.sortedMap().lowerKey(key);
      }

      public Map.Entry floorEntry(Object key) {
         Map.Entry entry = this.sortedMap().floorEntry(key);
         return entry == null ? null : this.wrapEntry(entry);
      }

      public Object floorKey(Object key) {
         return this.sortedMap().floorKey(key);
      }

      public Map.Entry ceilingEntry(Object key) {
         Map.Entry entry = this.sortedMap().ceilingEntry(key);
         return entry == null ? null : this.wrapEntry(entry);
      }

      public Object ceilingKey(Object key) {
         return this.sortedMap().ceilingKey(key);
      }

      public Map.Entry higherEntry(Object key) {
         Map.Entry entry = this.sortedMap().higherEntry(key);
         return entry == null ? null : this.wrapEntry(entry);
      }

      public Object higherKey(Object key) {
         return this.sortedMap().higherKey(key);
      }

      public Map.Entry firstEntry() {
         Map.Entry entry = this.sortedMap().firstEntry();
         return entry == null ? null : this.wrapEntry(entry);
      }

      public Map.Entry lastEntry() {
         Map.Entry entry = this.sortedMap().lastEntry();
         return entry == null ? null : this.wrapEntry(entry);
      }

      public Map.Entry pollFirstEntry() {
         return this.pollAsMapEntry(this.entrySet().iterator());
      }

      public Map.Entry pollLastEntry() {
         return this.pollAsMapEntry(this.descendingMap().entrySet().iterator());
      }

      Map.Entry pollAsMapEntry(Iterator entryIterator) {
         if (!entryIterator.hasNext()) {
            return null;
         } else {
            Map.Entry entry = (Map.Entry)entryIterator.next();
            Collection output = AbstractMapBasedMultimap.this.createCollection();
            output.addAll((Collection)entry.getValue());
            entryIterator.remove();
            return Maps.immutableEntry(entry.getKey(), AbstractMapBasedMultimap.unmodifiableCollectionSubclass(output));
         }
      }

      public NavigableMap descendingMap() {
         return AbstractMapBasedMultimap.this.new NavigableAsMap(this.sortedMap().descendingMap());
      }

      public NavigableSet keySet() {
         return (NavigableSet)super.keySet();
      }

      NavigableSet createKeySet() {
         return AbstractMapBasedMultimap.this.new NavigableKeySet(this.sortedMap());
      }

      public NavigableSet navigableKeySet() {
         return this.keySet();
      }

      public NavigableSet descendingKeySet() {
         return this.descendingMap().navigableKeySet();
      }

      public NavigableMap subMap(Object fromKey, Object toKey) {
         return this.subMap(fromKey, true, toKey, false);
      }

      public NavigableMap subMap(Object fromKey, boolean fromInclusive, Object toKey, boolean toInclusive) {
         return AbstractMapBasedMultimap.this.new NavigableAsMap(this.sortedMap().subMap(fromKey, fromInclusive, toKey, toInclusive));
      }

      public NavigableMap headMap(Object toKey) {
         return this.headMap(toKey, false);
      }

      public NavigableMap headMap(Object toKey, boolean inclusive) {
         return AbstractMapBasedMultimap.this.new NavigableAsMap(this.sortedMap().headMap(toKey, inclusive));
      }

      public NavigableMap tailMap(Object fromKey) {
         return this.tailMap(fromKey, true);
      }

      public NavigableMap tailMap(Object fromKey, boolean inclusive) {
         return AbstractMapBasedMultimap.this.new NavigableAsMap(this.sortedMap().tailMap(fromKey, inclusive));
      }
   }

   private class SortedAsMap extends AsMap implements SortedMap {
      SortedSet sortedKeySet;

      SortedAsMap(SortedMap submap) {
         super(submap);
      }

      SortedMap sortedMap() {
         return (SortedMap)this.submap;
      }

      public Comparator comparator() {
         return this.sortedMap().comparator();
      }

      public Object firstKey() {
         return this.sortedMap().firstKey();
      }

      public Object lastKey() {
         return this.sortedMap().lastKey();
      }

      public SortedMap headMap(Object toKey) {
         return AbstractMapBasedMultimap.this.new SortedAsMap(this.sortedMap().headMap(toKey));
      }

      public SortedMap subMap(Object fromKey, Object toKey) {
         return AbstractMapBasedMultimap.this.new SortedAsMap(this.sortedMap().subMap(fromKey, toKey));
      }

      public SortedMap tailMap(Object fromKey) {
         return AbstractMapBasedMultimap.this.new SortedAsMap(this.sortedMap().tailMap(fromKey));
      }

      public SortedSet keySet() {
         SortedSet result = this.sortedKeySet;
         return result == null ? (this.sortedKeySet = this.createKeySet()) : result;
      }

      SortedSet createKeySet() {
         return AbstractMapBasedMultimap.this.new SortedKeySet(this.sortedMap());
      }
   }

   private class AsMap extends Maps.ViewCachingAbstractMap {
      final transient Map submap;

      AsMap(Map submap) {
         this.submap = submap;
      }

      protected Set createEntrySet() {
         return new AsMapEntries();
      }

      public boolean containsKey(Object key) {
         return Maps.safeContainsKey(this.submap, key);
      }

      public Collection get(Object key) {
         Collection collection = (Collection)Maps.safeGet(this.submap, key);
         return collection == null ? null : AbstractMapBasedMultimap.this.wrapCollection(key, collection);
      }

      public Set keySet() {
         return AbstractMapBasedMultimap.this.keySet();
      }

      public int size() {
         return this.submap.size();
      }

      public Collection remove(Object key) {
         Collection collection = (Collection)this.submap.remove(key);
         if (collection == null) {
            return null;
         } else {
            Collection output = AbstractMapBasedMultimap.this.createCollection();
            output.addAll(collection);
            AbstractMapBasedMultimap.this.totalSize = AbstractMapBasedMultimap.this.totalSize - collection.size();
            collection.clear();
            return output;
         }
      }

      public boolean equals(@Nullable Object object) {
         return this == object || this.submap.equals(object);
      }

      public int hashCode() {
         return this.submap.hashCode();
      }

      public String toString() {
         return this.submap.toString();
      }

      public void clear() {
         if (this.submap == AbstractMapBasedMultimap.this.map) {
            AbstractMapBasedMultimap.this.clear();
         } else {
            Iterators.clear(new AsMapIterator());
         }

      }

      Map.Entry wrapEntry(Map.Entry entry) {
         Object key = entry.getKey();
         return Maps.immutableEntry(key, AbstractMapBasedMultimap.this.wrapCollection(key, (Collection)entry.getValue()));
      }

      class AsMapIterator implements Iterator {
         final Iterator delegateIterator;
         Collection collection;

         AsMapIterator() {
            this.delegateIterator = AsMap.this.submap.entrySet().iterator();
         }

         public boolean hasNext() {
            return this.delegateIterator.hasNext();
         }

         public Map.Entry next() {
            Map.Entry entry = (Map.Entry)this.delegateIterator.next();
            this.collection = (Collection)entry.getValue();
            return AsMap.this.wrapEntry(entry);
         }

         public void remove() {
            this.delegateIterator.remove();
            AbstractMapBasedMultimap.this.totalSize = AbstractMapBasedMultimap.this.totalSize - this.collection.size();
            this.collection.clear();
         }
      }

      class AsMapEntries extends Maps.EntrySet {
         Map map() {
            return AsMap.this;
         }

         public Iterator iterator() {
            return AsMap.this.new AsMapIterator();
         }

         public boolean contains(Object o) {
            return Collections2.safeContains(AsMap.this.submap.entrySet(), o);
         }

         public boolean remove(Object o) {
            if (!this.contains(o)) {
               return false;
            } else {
               Map.Entry entry = (Map.Entry)o;
               AbstractMapBasedMultimap.this.removeValuesForKey(entry.getKey());
               return true;
            }
         }
      }
   }

   private abstract class Itr implements Iterator {
      final Iterator keyIterator;
      Object key;
      Collection collection;
      Iterator valueIterator;

      Itr() {
         this.keyIterator = AbstractMapBasedMultimap.this.map.entrySet().iterator();
         this.key = null;
         this.collection = null;
         this.valueIterator = Iterators.emptyModifiableIterator();
      }

      abstract Object output(Object var1, Object var2);

      public boolean hasNext() {
         return this.keyIterator.hasNext() || this.valueIterator.hasNext();
      }

      public Object next() {
         if (!this.valueIterator.hasNext()) {
            Map.Entry mapEntry = (Map.Entry)this.keyIterator.next();
            this.key = mapEntry.getKey();
            this.collection = (Collection)mapEntry.getValue();
            this.valueIterator = this.collection.iterator();
         }

         return this.output(this.key, this.valueIterator.next());
      }

      public void remove() {
         this.valueIterator.remove();
         if (this.collection.isEmpty()) {
            this.keyIterator.remove();
         }

         AbstractMapBasedMultimap.this.totalSize--;
      }
   }

   class NavigableKeySet extends SortedKeySet implements NavigableSet {
      NavigableKeySet(NavigableMap subMap) {
         super(subMap);
      }

      NavigableMap sortedMap() {
         return (NavigableMap)super.sortedMap();
      }

      public Object lower(Object k) {
         return this.sortedMap().lowerKey(k);
      }

      public Object floor(Object k) {
         return this.sortedMap().floorKey(k);
      }

      public Object ceiling(Object k) {
         return this.sortedMap().ceilingKey(k);
      }

      public Object higher(Object k) {
         return this.sortedMap().higherKey(k);
      }

      public Object pollFirst() {
         return Iterators.pollNext(this.iterator());
      }

      public Object pollLast() {
         return Iterators.pollNext(this.descendingIterator());
      }

      public NavigableSet descendingSet() {
         return AbstractMapBasedMultimap.this.new NavigableKeySet(this.sortedMap().descendingMap());
      }

      public Iterator descendingIterator() {
         return this.descendingSet().iterator();
      }

      public NavigableSet headSet(Object toElement) {
         return this.headSet(toElement, false);
      }

      public NavigableSet headSet(Object toElement, boolean inclusive) {
         return AbstractMapBasedMultimap.this.new NavigableKeySet(this.sortedMap().headMap(toElement, inclusive));
      }

      public NavigableSet subSet(Object fromElement, Object toElement) {
         return this.subSet(fromElement, true, toElement, false);
      }

      public NavigableSet subSet(Object fromElement, boolean fromInclusive, Object toElement, boolean toInclusive) {
         return AbstractMapBasedMultimap.this.new NavigableKeySet(this.sortedMap().subMap(fromElement, fromInclusive, toElement, toInclusive));
      }

      public NavigableSet tailSet(Object fromElement) {
         return this.tailSet(fromElement, true);
      }

      public NavigableSet tailSet(Object fromElement, boolean inclusive) {
         return AbstractMapBasedMultimap.this.new NavigableKeySet(this.sortedMap().tailMap(fromElement, inclusive));
      }
   }

   private class SortedKeySet extends KeySet implements SortedSet {
      SortedKeySet(SortedMap subMap) {
         super(subMap);
      }

      SortedMap sortedMap() {
         return (SortedMap)super.map();
      }

      public Comparator comparator() {
         return this.sortedMap().comparator();
      }

      public Object first() {
         return this.sortedMap().firstKey();
      }

      public SortedSet headSet(Object toElement) {
         return AbstractMapBasedMultimap.this.new SortedKeySet(this.sortedMap().headMap(toElement));
      }

      public Object last() {
         return this.sortedMap().lastKey();
      }

      public SortedSet subSet(Object fromElement, Object toElement) {
         return AbstractMapBasedMultimap.this.new SortedKeySet(this.sortedMap().subMap(fromElement, toElement));
      }

      public SortedSet tailSet(Object fromElement) {
         return AbstractMapBasedMultimap.this.new SortedKeySet(this.sortedMap().tailMap(fromElement));
      }
   }

   private class KeySet extends Maps.KeySet {
      KeySet(Map subMap) {
         super(subMap);
      }

      public Iterator iterator() {
         final Iterator entryIterator = this.map().entrySet().iterator();
         return new Iterator() {
            Map.Entry entry;

            public boolean hasNext() {
               return entryIterator.hasNext();
            }

            public Object next() {
               this.entry = (Map.Entry)entryIterator.next();
               return this.entry.getKey();
            }

            public void remove() {
               CollectPreconditions.checkRemove(this.entry != null);
               Collection collection = (Collection)this.entry.getValue();
               entryIterator.remove();
               AbstractMapBasedMultimap.this.totalSize = AbstractMapBasedMultimap.this.totalSize - collection.size();
               collection.clear();
            }
         };
      }

      public boolean remove(Object key) {
         int count = 0;
         Collection collection = (Collection)this.map().remove(key);
         if (collection != null) {
            count = collection.size();
            collection.clear();
            AbstractMapBasedMultimap.this.totalSize = AbstractMapBasedMultimap.this.totalSize - count;
         }

         return count > 0;
      }

      public void clear() {
         Iterators.clear(this.iterator());
      }

      public boolean containsAll(Collection c) {
         return this.map().keySet().containsAll(c);
      }

      public boolean equals(@Nullable Object object) {
         return this == object || this.map().keySet().equals(object);
      }

      public int hashCode() {
         return this.map().keySet().hashCode();
      }
   }

   private class RandomAccessWrappedList extends WrappedList implements RandomAccess {
      RandomAccessWrappedList(@Nullable Object key, List delegate, @Nullable WrappedCollection ancestor) {
         super(key, delegate, ancestor);
      }
   }

   private class WrappedList extends WrappedCollection implements List {
      WrappedList(@Nullable Object key, List delegate, @Nullable WrappedCollection ancestor) {
         super(key, delegate, ancestor);
      }

      List getListDelegate() {
         return (List)this.getDelegate();
      }

      public boolean addAll(int index, Collection c) {
         if (c.isEmpty()) {
            return false;
         } else {
            int oldSize = this.size();
            boolean changed = this.getListDelegate().addAll(index, c);
            if (changed) {
               int newSize = this.getDelegate().size();
               AbstractMapBasedMultimap.this.totalSize = AbstractMapBasedMultimap.this.totalSize + (newSize - oldSize);
               if (oldSize == 0) {
                  this.addToMap();
               }
            }

            return changed;
         }
      }

      public Object get(int index) {
         this.refreshIfEmpty();
         return this.getListDelegate().get(index);
      }

      public Object set(int index, Object element) {
         this.refreshIfEmpty();
         return this.getListDelegate().set(index, element);
      }

      public void add(int index, Object element) {
         this.refreshIfEmpty();
         boolean wasEmpty = this.getDelegate().isEmpty();
         this.getListDelegate().add(index, element);
         AbstractMapBasedMultimap.this.totalSize++;
         if (wasEmpty) {
            this.addToMap();
         }

      }

      public Object remove(int index) {
         this.refreshIfEmpty();
         Object value = this.getListDelegate().remove(index);
         AbstractMapBasedMultimap.this.totalSize--;
         this.removeIfEmpty();
         return value;
      }

      public int indexOf(Object o) {
         this.refreshIfEmpty();
         return this.getListDelegate().indexOf(o);
      }

      public int lastIndexOf(Object o) {
         this.refreshIfEmpty();
         return this.getListDelegate().lastIndexOf(o);
      }

      public ListIterator listIterator() {
         this.refreshIfEmpty();
         return new WrappedListIterator();
      }

      public ListIterator listIterator(int index) {
         this.refreshIfEmpty();
         return new WrappedListIterator(index);
      }

      public List subList(int fromIndex, int toIndex) {
         this.refreshIfEmpty();
         return AbstractMapBasedMultimap.this.wrapList(this.getKey(), this.getListDelegate().subList(fromIndex, toIndex), (WrappedCollection)(this.getAncestor() == null ? this : this.getAncestor()));
      }

      private class WrappedListIterator extends WrappedCollection.WrappedIterator implements ListIterator {
         WrappedListIterator() {
            super();
         }

         public WrappedListIterator(int index) {
            super(WrappedList.this.getListDelegate().listIterator(index));
         }

         private ListIterator getDelegateListIterator() {
            return (ListIterator)this.getDelegateIterator();
         }

         public boolean hasPrevious() {
            return this.getDelegateListIterator().hasPrevious();
         }

         public Object previous() {
            return this.getDelegateListIterator().previous();
         }

         public int nextIndex() {
            return this.getDelegateListIterator().nextIndex();
         }

         public int previousIndex() {
            return this.getDelegateListIterator().previousIndex();
         }

         public void set(Object value) {
            this.getDelegateListIterator().set(value);
         }

         public void add(Object value) {
            boolean wasEmpty = WrappedList.this.isEmpty();
            this.getDelegateListIterator().add(value);
            AbstractMapBasedMultimap.this.totalSize++;
            if (wasEmpty) {
               WrappedList.this.addToMap();
            }

         }
      }
   }

   class WrappedNavigableSet extends WrappedSortedSet implements NavigableSet {
      WrappedNavigableSet(@Nullable Object key, NavigableSet delegate, @Nullable WrappedCollection ancestor) {
         super(key, delegate, ancestor);
      }

      NavigableSet getSortedSetDelegate() {
         return (NavigableSet)super.getSortedSetDelegate();
      }

      public Object lower(Object v) {
         return this.getSortedSetDelegate().lower(v);
      }

      public Object floor(Object v) {
         return this.getSortedSetDelegate().floor(v);
      }

      public Object ceiling(Object v) {
         return this.getSortedSetDelegate().ceiling(v);
      }

      public Object higher(Object v) {
         return this.getSortedSetDelegate().higher(v);
      }

      public Object pollFirst() {
         return Iterators.pollNext(this.iterator());
      }

      public Object pollLast() {
         return Iterators.pollNext(this.descendingIterator());
      }

      private NavigableSet wrap(NavigableSet wrapped) {
         return AbstractMapBasedMultimap.this.new WrappedNavigableSet(this.key, wrapped, (WrappedCollection)(this.getAncestor() == null ? this : this.getAncestor()));
      }

      public NavigableSet descendingSet() {
         return this.wrap(this.getSortedSetDelegate().descendingSet());
      }

      public Iterator descendingIterator() {
         return new WrappedCollection.WrappedIterator(this.getSortedSetDelegate().descendingIterator());
      }

      public NavigableSet subSet(Object fromElement, boolean fromInclusive, Object toElement, boolean toInclusive) {
         return this.wrap(this.getSortedSetDelegate().subSet(fromElement, fromInclusive, toElement, toInclusive));
      }

      public NavigableSet headSet(Object toElement, boolean inclusive) {
         return this.wrap(this.getSortedSetDelegate().headSet(toElement, inclusive));
      }

      public NavigableSet tailSet(Object fromElement, boolean inclusive) {
         return this.wrap(this.getSortedSetDelegate().tailSet(fromElement, inclusive));
      }
   }

   private class WrappedSortedSet extends WrappedCollection implements SortedSet {
      WrappedSortedSet(@Nullable Object key, SortedSet delegate, @Nullable WrappedCollection ancestor) {
         super(key, delegate, ancestor);
      }

      SortedSet getSortedSetDelegate() {
         return (SortedSet)this.getDelegate();
      }

      public Comparator comparator() {
         return this.getSortedSetDelegate().comparator();
      }

      public Object first() {
         this.refreshIfEmpty();
         return this.getSortedSetDelegate().first();
      }

      public Object last() {
         this.refreshIfEmpty();
         return this.getSortedSetDelegate().last();
      }

      public SortedSet headSet(Object toElement) {
         this.refreshIfEmpty();
         return AbstractMapBasedMultimap.this.new WrappedSortedSet(this.getKey(), this.getSortedSetDelegate().headSet(toElement), (WrappedCollection)(this.getAncestor() == null ? this : this.getAncestor()));
      }

      public SortedSet subSet(Object fromElement, Object toElement) {
         this.refreshIfEmpty();
         return AbstractMapBasedMultimap.this.new WrappedSortedSet(this.getKey(), this.getSortedSetDelegate().subSet(fromElement, toElement), (WrappedCollection)(this.getAncestor() == null ? this : this.getAncestor()));
      }

      public SortedSet tailSet(Object fromElement) {
         this.refreshIfEmpty();
         return AbstractMapBasedMultimap.this.new WrappedSortedSet(this.getKey(), this.getSortedSetDelegate().tailSet(fromElement), (WrappedCollection)(this.getAncestor() == null ? this : this.getAncestor()));
      }
   }

   private class WrappedSet extends WrappedCollection implements Set {
      WrappedSet(@Nullable Object key, Set delegate) {
         super(key, delegate, (WrappedCollection)null);
      }

      public boolean removeAll(Collection c) {
         if (c.isEmpty()) {
            return false;
         } else {
            int oldSize = this.size();
            boolean changed = Sets.removeAllImpl((Set)this.delegate, c);
            if (changed) {
               int newSize = this.delegate.size();
               AbstractMapBasedMultimap.this.totalSize = AbstractMapBasedMultimap.this.totalSize + (newSize - oldSize);
               this.removeIfEmpty();
            }

            return changed;
         }
      }
   }

   private class WrappedCollection extends AbstractCollection {
      final Object key;
      Collection delegate;
      final WrappedCollection ancestor;
      final Collection ancestorDelegate;

      WrappedCollection(@Nullable Object key, Collection delegate, @Nullable WrappedCollection ancestor) {
         this.key = key;
         this.delegate = delegate;
         this.ancestor = ancestor;
         this.ancestorDelegate = ancestor == null ? null : ancestor.getDelegate();
      }

      void refreshIfEmpty() {
         if (this.ancestor != null) {
            this.ancestor.refreshIfEmpty();
            if (this.ancestor.getDelegate() != this.ancestorDelegate) {
               throw new ConcurrentModificationException();
            }
         } else if (this.delegate.isEmpty()) {
            Collection newDelegate = (Collection)AbstractMapBasedMultimap.this.map.get(this.key);
            if (newDelegate != null) {
               this.delegate = newDelegate;
            }
         }

      }

      void removeIfEmpty() {
         if (this.ancestor != null) {
            this.ancestor.removeIfEmpty();
         } else if (this.delegate.isEmpty()) {
            AbstractMapBasedMultimap.this.map.remove(this.key);
         }

      }

      Object getKey() {
         return this.key;
      }

      void addToMap() {
         if (this.ancestor != null) {
            this.ancestor.addToMap();
         } else {
            AbstractMapBasedMultimap.this.map.put(this.key, this.delegate);
         }

      }

      public int size() {
         this.refreshIfEmpty();
         return this.delegate.size();
      }

      public boolean equals(@Nullable Object object) {
         if (object == this) {
            return true;
         } else {
            this.refreshIfEmpty();
            return this.delegate.equals(object);
         }
      }

      public int hashCode() {
         this.refreshIfEmpty();
         return this.delegate.hashCode();
      }

      public String toString() {
         this.refreshIfEmpty();
         return this.delegate.toString();
      }

      Collection getDelegate() {
         return this.delegate;
      }

      public Iterator iterator() {
         this.refreshIfEmpty();
         return new WrappedIterator();
      }

      public boolean add(Object value) {
         this.refreshIfEmpty();
         boolean wasEmpty = this.delegate.isEmpty();
         boolean changed = this.delegate.add(value);
         if (changed) {
            AbstractMapBasedMultimap.this.totalSize++;
            if (wasEmpty) {
               this.addToMap();
            }
         }

         return changed;
      }

      WrappedCollection getAncestor() {
         return this.ancestor;
      }

      public boolean addAll(Collection collection) {
         if (collection.isEmpty()) {
            return false;
         } else {
            int oldSize = this.size();
            boolean changed = this.delegate.addAll(collection);
            if (changed) {
               int newSize = this.delegate.size();
               AbstractMapBasedMultimap.this.totalSize = AbstractMapBasedMultimap.this.totalSize + (newSize - oldSize);
               if (oldSize == 0) {
                  this.addToMap();
               }
            }

            return changed;
         }
      }

      public boolean contains(Object o) {
         this.refreshIfEmpty();
         return this.delegate.contains(o);
      }

      public boolean containsAll(Collection c) {
         this.refreshIfEmpty();
         return this.delegate.containsAll(c);
      }

      public void clear() {
         int oldSize = this.size();
         if (oldSize != 0) {
            this.delegate.clear();
            AbstractMapBasedMultimap.this.totalSize = AbstractMapBasedMultimap.this.totalSize - oldSize;
            this.removeIfEmpty();
         }
      }

      public boolean remove(Object o) {
         this.refreshIfEmpty();
         boolean changed = this.delegate.remove(o);
         if (changed) {
            AbstractMapBasedMultimap.this.totalSize--;
            this.removeIfEmpty();
         }

         return changed;
      }

      public boolean removeAll(Collection c) {
         if (c.isEmpty()) {
            return false;
         } else {
            int oldSize = this.size();
            boolean changed = this.delegate.removeAll(c);
            if (changed) {
               int newSize = this.delegate.size();
               AbstractMapBasedMultimap.this.totalSize = AbstractMapBasedMultimap.this.totalSize + (newSize - oldSize);
               this.removeIfEmpty();
            }

            return changed;
         }
      }

      public boolean retainAll(Collection c) {
         Preconditions.checkNotNull(c);
         int oldSize = this.size();
         boolean changed = this.delegate.retainAll(c);
         if (changed) {
            int newSize = this.delegate.size();
            AbstractMapBasedMultimap.this.totalSize = AbstractMapBasedMultimap.this.totalSize + (newSize - oldSize);
            this.removeIfEmpty();
         }

         return changed;
      }

      class WrappedIterator implements Iterator {
         final Iterator delegateIterator;
         final Collection originalDelegate;

         WrappedIterator() {
            this.originalDelegate = WrappedCollection.this.delegate;
            this.delegateIterator = AbstractMapBasedMultimap.iteratorOrListIterator(WrappedCollection.this.delegate);
         }

         WrappedIterator(Iterator delegateIterator) {
            this.originalDelegate = WrappedCollection.this.delegate;
            this.delegateIterator = delegateIterator;
         }

         void validateIterator() {
            WrappedCollection.this.refreshIfEmpty();
            if (WrappedCollection.this.delegate != this.originalDelegate) {
               throw new ConcurrentModificationException();
            }
         }

         public boolean hasNext() {
            this.validateIterator();
            return this.delegateIterator.hasNext();
         }

         public Object next() {
            this.validateIterator();
            return this.delegateIterator.next();
         }

         public void remove() {
            this.delegateIterator.remove();
            AbstractMapBasedMultimap.this.totalSize--;
            WrappedCollection.this.removeIfEmpty();
         }

         Iterator getDelegateIterator() {
            this.validateIterator();
            return this.delegateIterator;
         }
      }
   }
}
