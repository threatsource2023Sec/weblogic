package org.python.google.common.collect;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Function;
import org.python.google.common.base.Preconditions;
import org.python.google.common.base.Predicate;
import org.python.google.common.base.Predicates;
import org.python.google.common.base.Supplier;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtCompatible
class StandardTable extends AbstractTable implements Serializable {
   @GwtTransient
   final Map backingMap;
   @GwtTransient
   final Supplier factory;
   private transient Set columnKeySet;
   private transient Map rowMap;
   private transient ColumnMap columnMap;
   private static final long serialVersionUID = 0L;

   StandardTable(Map backingMap, Supplier factory) {
      this.backingMap = backingMap;
      this.factory = factory;
   }

   public boolean contains(@Nullable Object rowKey, @Nullable Object columnKey) {
      return rowKey != null && columnKey != null && super.contains(rowKey, columnKey);
   }

   public boolean containsColumn(@Nullable Object columnKey) {
      if (columnKey == null) {
         return false;
      } else {
         Iterator var2 = this.backingMap.values().iterator();

         Map map;
         do {
            if (!var2.hasNext()) {
               return false;
            }

            map = (Map)var2.next();
         } while(!Maps.safeContainsKey(map, columnKey));

         return true;
      }
   }

   public boolean containsRow(@Nullable Object rowKey) {
      return rowKey != null && Maps.safeContainsKey(this.backingMap, rowKey);
   }

   public boolean containsValue(@Nullable Object value) {
      return value != null && super.containsValue(value);
   }

   public Object get(@Nullable Object rowKey, @Nullable Object columnKey) {
      return rowKey != null && columnKey != null ? super.get(rowKey, columnKey) : null;
   }

   public boolean isEmpty() {
      return this.backingMap.isEmpty();
   }

   public int size() {
      int size = 0;

      Map map;
      for(Iterator var2 = this.backingMap.values().iterator(); var2.hasNext(); size += map.size()) {
         map = (Map)var2.next();
      }

      return size;
   }

   public void clear() {
      this.backingMap.clear();
   }

   private Map getOrCreate(Object rowKey) {
      Map map = (Map)this.backingMap.get(rowKey);
      if (map == null) {
         map = (Map)this.factory.get();
         this.backingMap.put(rowKey, map);
      }

      return map;
   }

   @CanIgnoreReturnValue
   public Object put(Object rowKey, Object columnKey, Object value) {
      Preconditions.checkNotNull(rowKey);
      Preconditions.checkNotNull(columnKey);
      Preconditions.checkNotNull(value);
      return this.getOrCreate(rowKey).put(columnKey, value);
   }

   @CanIgnoreReturnValue
   public Object remove(@Nullable Object rowKey, @Nullable Object columnKey) {
      if (rowKey != null && columnKey != null) {
         Map map = (Map)Maps.safeGet(this.backingMap, rowKey);
         if (map == null) {
            return null;
         } else {
            Object value = map.remove(columnKey);
            if (map.isEmpty()) {
               this.backingMap.remove(rowKey);
            }

            return value;
         }
      } else {
         return null;
      }
   }

   @CanIgnoreReturnValue
   private Map removeColumn(Object column) {
      Map output = new LinkedHashMap();
      Iterator iterator = this.backingMap.entrySet().iterator();

      while(iterator.hasNext()) {
         Map.Entry entry = (Map.Entry)iterator.next();
         Object value = ((Map)entry.getValue()).remove(column);
         if (value != null) {
            output.put(entry.getKey(), value);
            if (((Map)entry.getValue()).isEmpty()) {
               iterator.remove();
            }
         }
      }

      return output;
   }

   private boolean containsMapping(Object rowKey, Object columnKey, Object value) {
      return value != null && value.equals(this.get(rowKey, columnKey));
   }

   private boolean removeMapping(Object rowKey, Object columnKey, Object value) {
      if (this.containsMapping(rowKey, columnKey, value)) {
         this.remove(rowKey, columnKey);
         return true;
      } else {
         return false;
      }
   }

   public Set cellSet() {
      return super.cellSet();
   }

   Iterator cellIterator() {
      return new CellIterator();
   }

   public Map row(Object rowKey) {
      return new Row(rowKey);
   }

   public Map column(Object columnKey) {
      return new Column(columnKey);
   }

   public Set rowKeySet() {
      return this.rowMap().keySet();
   }

   public Set columnKeySet() {
      Set result = this.columnKeySet;
      return result == null ? (this.columnKeySet = new ColumnKeySet()) : result;
   }

   Iterator createColumnKeyIterator() {
      return new ColumnKeyIterator();
   }

   public Collection values() {
      return super.values();
   }

   public Map rowMap() {
      Map result = this.rowMap;
      return result == null ? (this.rowMap = this.createRowMap()) : result;
   }

   Map createRowMap() {
      return new RowMap();
   }

   public Map columnMap() {
      ColumnMap result = this.columnMap;
      return result == null ? (this.columnMap = new ColumnMap()) : result;
   }

   private class ColumnMap extends Maps.ViewCachingAbstractMap {
      private ColumnMap() {
      }

      public Map get(Object key) {
         return StandardTable.this.containsColumn(key) ? StandardTable.this.column(key) : null;
      }

      public boolean containsKey(Object key) {
         return StandardTable.this.containsColumn(key);
      }

      public Map remove(Object key) {
         return StandardTable.this.containsColumn(key) ? StandardTable.this.removeColumn(key) : null;
      }

      public Set createEntrySet() {
         return new ColumnMapEntrySet();
      }

      public Set keySet() {
         return StandardTable.this.columnKeySet();
      }

      Collection createValues() {
         return new ColumnMapValues();
      }

      // $FF: synthetic method
      ColumnMap(Object x1) {
         this();
      }

      private class ColumnMapValues extends Maps.Values {
         ColumnMapValues() {
            super(ColumnMap.this);
         }

         public boolean remove(Object obj) {
            Iterator var2 = ColumnMap.this.entrySet().iterator();

            Map.Entry entry;
            do {
               if (!var2.hasNext()) {
                  return false;
               }

               entry = (Map.Entry)var2.next();
            } while(!((Map)entry.getValue()).equals(obj));

            StandardTable.this.removeColumn(entry.getKey());
            return true;
         }

         public boolean removeAll(Collection c) {
            Preconditions.checkNotNull(c);
            boolean changed = false;
            Iterator var3 = Lists.newArrayList(StandardTable.this.columnKeySet().iterator()).iterator();

            while(var3.hasNext()) {
               Object columnKey = var3.next();
               if (c.contains(StandardTable.this.column(columnKey))) {
                  StandardTable.this.removeColumn(columnKey);
                  changed = true;
               }
            }

            return changed;
         }

         public boolean retainAll(Collection c) {
            Preconditions.checkNotNull(c);
            boolean changed = false;
            Iterator var3 = Lists.newArrayList(StandardTable.this.columnKeySet().iterator()).iterator();

            while(var3.hasNext()) {
               Object columnKey = var3.next();
               if (!c.contains(StandardTable.this.column(columnKey))) {
                  StandardTable.this.removeColumn(columnKey);
                  changed = true;
               }
            }

            return changed;
         }
      }

      class ColumnMapEntrySet extends TableSet {
         ColumnMapEntrySet() {
            super(null);
         }

         public Iterator iterator() {
            return Maps.asMapEntryIterator(StandardTable.this.columnKeySet(), new Function() {
               public Map apply(Object columnKey) {
                  return StandardTable.this.column(columnKey);
               }
            });
         }

         public int size() {
            return StandardTable.this.columnKeySet().size();
         }

         public boolean contains(Object obj) {
            if (obj instanceof Map.Entry) {
               Map.Entry entry = (Map.Entry)obj;
               if (StandardTable.this.containsColumn(entry.getKey())) {
                  Object columnKey = entry.getKey();
                  return ColumnMap.this.get(columnKey).equals(entry.getValue());
               }
            }

            return false;
         }

         public boolean remove(Object obj) {
            if (this.contains(obj)) {
               Map.Entry entry = (Map.Entry)obj;
               StandardTable.this.removeColumn(entry.getKey());
               return true;
            } else {
               return false;
            }
         }

         public boolean removeAll(Collection c) {
            Preconditions.checkNotNull(c);
            return Sets.removeAllImpl(this, (Iterator)c.iterator());
         }

         public boolean retainAll(Collection c) {
            Preconditions.checkNotNull(c);
            boolean changed = false;
            Iterator var3 = Lists.newArrayList(StandardTable.this.columnKeySet().iterator()).iterator();

            while(var3.hasNext()) {
               Object columnKey = var3.next();
               if (!c.contains(Maps.immutableEntry(columnKey, StandardTable.this.column(columnKey)))) {
                  StandardTable.this.removeColumn(columnKey);
                  changed = true;
               }
            }

            return changed;
         }
      }
   }

   class RowMap extends Maps.ViewCachingAbstractMap {
      public boolean containsKey(Object key) {
         return StandardTable.this.containsRow(key);
      }

      public Map get(Object key) {
         return StandardTable.this.containsRow(key) ? StandardTable.this.row(key) : null;
      }

      public Map remove(Object key) {
         return key == null ? null : (Map)StandardTable.this.backingMap.remove(key);
      }

      protected Set createEntrySet() {
         return new EntrySet();
      }

      class EntrySet extends TableSet {
         EntrySet() {
            super(null);
         }

         public Iterator iterator() {
            return Maps.asMapEntryIterator(StandardTable.this.backingMap.keySet(), new Function() {
               public Map apply(Object rowKey) {
                  return StandardTable.this.row(rowKey);
               }
            });
         }

         public int size() {
            return StandardTable.this.backingMap.size();
         }

         public boolean contains(Object obj) {
            if (!(obj instanceof Map.Entry)) {
               return false;
            } else {
               Map.Entry entry = (Map.Entry)obj;
               return entry.getKey() != null && entry.getValue() instanceof Map && Collections2.safeContains(StandardTable.this.backingMap.entrySet(), entry);
            }
         }

         public boolean remove(Object obj) {
            if (!(obj instanceof Map.Entry)) {
               return false;
            } else {
               Map.Entry entry = (Map.Entry)obj;
               return entry.getKey() != null && entry.getValue() instanceof Map && StandardTable.this.backingMap.entrySet().remove(entry);
            }
         }
      }
   }

   private class ColumnKeyIterator extends AbstractIterator {
      final Map seen;
      final Iterator mapIterator;
      Iterator entryIterator;

      private ColumnKeyIterator() {
         this.seen = (Map)StandardTable.this.factory.get();
         this.mapIterator = StandardTable.this.backingMap.values().iterator();
         this.entryIterator = Iterators.emptyIterator();
      }

      protected Object computeNext() {
         while(true) {
            if (this.entryIterator.hasNext()) {
               Map.Entry entry = (Map.Entry)this.entryIterator.next();
               if (!this.seen.containsKey(entry.getKey())) {
                  this.seen.put(entry.getKey(), entry.getValue());
                  return entry.getKey();
               }
            } else {
               if (!this.mapIterator.hasNext()) {
                  return this.endOfData();
               }

               this.entryIterator = ((Map)this.mapIterator.next()).entrySet().iterator();
            }
         }
      }

      // $FF: synthetic method
      ColumnKeyIterator(Object x1) {
         this();
      }
   }

   private class ColumnKeySet extends TableSet {
      private ColumnKeySet() {
         super(null);
      }

      public Iterator iterator() {
         return StandardTable.this.createColumnKeyIterator();
      }

      public int size() {
         return Iterators.size(this.iterator());
      }

      public boolean remove(Object obj) {
         if (obj == null) {
            return false;
         } else {
            boolean changed = false;
            Iterator iterator = StandardTable.this.backingMap.values().iterator();

            while(iterator.hasNext()) {
               Map map = (Map)iterator.next();
               if (map.keySet().remove(obj)) {
                  changed = true;
                  if (map.isEmpty()) {
                     iterator.remove();
                  }
               }
            }

            return changed;
         }
      }

      public boolean removeAll(Collection c) {
         Preconditions.checkNotNull(c);
         boolean changed = false;
         Iterator iterator = StandardTable.this.backingMap.values().iterator();

         while(iterator.hasNext()) {
            Map map = (Map)iterator.next();
            if (Iterators.removeAll(map.keySet().iterator(), c)) {
               changed = true;
               if (map.isEmpty()) {
                  iterator.remove();
               }
            }
         }

         return changed;
      }

      public boolean retainAll(Collection c) {
         Preconditions.checkNotNull(c);
         boolean changed = false;
         Iterator iterator = StandardTable.this.backingMap.values().iterator();

         while(iterator.hasNext()) {
            Map map = (Map)iterator.next();
            if (map.keySet().retainAll(c)) {
               changed = true;
               if (map.isEmpty()) {
                  iterator.remove();
               }
            }
         }

         return changed;
      }

      public boolean contains(Object obj) {
         return StandardTable.this.containsColumn(obj);
      }

      // $FF: synthetic method
      ColumnKeySet(Object x1) {
         this();
      }
   }

   private class Column extends Maps.ViewCachingAbstractMap {
      final Object columnKey;

      Column(Object columnKey) {
         this.columnKey = Preconditions.checkNotNull(columnKey);
      }

      public Object put(Object key, Object value) {
         return StandardTable.this.put(key, this.columnKey, value);
      }

      public Object get(Object key) {
         return StandardTable.this.get(key, this.columnKey);
      }

      public boolean containsKey(Object key) {
         return StandardTable.this.contains(key, this.columnKey);
      }

      public Object remove(Object key) {
         return StandardTable.this.remove(key, this.columnKey);
      }

      @CanIgnoreReturnValue
      boolean removeFromColumnIf(Predicate predicate) {
         boolean changed = false;
         Iterator iterator = StandardTable.this.backingMap.entrySet().iterator();

         while(iterator.hasNext()) {
            Map.Entry entry = (Map.Entry)iterator.next();
            Map map = (Map)entry.getValue();
            Object value = map.get(this.columnKey);
            if (value != null && predicate.apply(Maps.immutableEntry(entry.getKey(), value))) {
               map.remove(this.columnKey);
               changed = true;
               if (map.isEmpty()) {
                  iterator.remove();
               }
            }
         }

         return changed;
      }

      Set createEntrySet() {
         return new EntrySet();
      }

      Set createKeySet() {
         return new KeySet();
      }

      Collection createValues() {
         return new Values();
      }

      private class Values extends Maps.Values {
         Values() {
            super(Column.this);
         }

         public boolean remove(Object obj) {
            return obj != null && Column.this.removeFromColumnIf(Maps.valuePredicateOnEntries(Predicates.equalTo(obj)));
         }

         public boolean removeAll(Collection c) {
            return Column.this.removeFromColumnIf(Maps.valuePredicateOnEntries(Predicates.in(c)));
         }

         public boolean retainAll(Collection c) {
            return Column.this.removeFromColumnIf(Maps.valuePredicateOnEntries(Predicates.not(Predicates.in(c))));
         }
      }

      private class KeySet extends Maps.KeySet {
         KeySet() {
            super(Column.this);
         }

         public boolean contains(Object obj) {
            return StandardTable.this.contains(obj, Column.this.columnKey);
         }

         public boolean remove(Object obj) {
            return StandardTable.this.remove(obj, Column.this.columnKey) != null;
         }

         public boolean retainAll(Collection c) {
            return Column.this.removeFromColumnIf(Maps.keyPredicateOnEntries(Predicates.not(Predicates.in(c))));
         }
      }

      private class EntrySetIterator extends AbstractIterator {
         final Iterator iterator;

         private EntrySetIterator() {
            this.iterator = StandardTable.this.backingMap.entrySet().iterator();
         }

         protected Map.Entry computeNext() {
            while(true) {
               if (this.iterator.hasNext()) {
                  final Map.Entry entry = (Map.Entry)this.iterator.next();
                  if (!((Map)entry.getValue()).containsKey(Column.this.columnKey)) {
                     continue;
                  }

                  class EntryImpl extends AbstractMapEntry {
                     public Object getKey() {
                        return entry.getKey();
                     }

                     public Object getValue() {
                        return ((Map)entry.getValue()).get(Column.this.columnKey);
                     }

                     public Object setValue(Object value) {
                        return ((Map)entry.getValue()).put(Column.this.columnKey, Preconditions.checkNotNull(value));
                     }
                  }

                  return new EntryImpl();
               }

               return (Map.Entry)this.endOfData();
            }
         }

         // $FF: synthetic method
         EntrySetIterator(Object x1) {
            this();
         }
      }

      private class EntrySet extends Sets.ImprovedAbstractSet {
         private EntrySet() {
         }

         public Iterator iterator() {
            return Column.this.new EntrySetIterator();
         }

         public int size() {
            int size = 0;
            Iterator var2 = StandardTable.this.backingMap.values().iterator();

            while(var2.hasNext()) {
               Map map = (Map)var2.next();
               if (map.containsKey(Column.this.columnKey)) {
                  ++size;
               }
            }

            return size;
         }

         public boolean isEmpty() {
            return !StandardTable.this.containsColumn(Column.this.columnKey);
         }

         public void clear() {
            Column.this.removeFromColumnIf(Predicates.alwaysTrue());
         }

         public boolean contains(Object o) {
            if (o instanceof Map.Entry) {
               Map.Entry entry = (Map.Entry)o;
               return StandardTable.this.containsMapping(entry.getKey(), Column.this.columnKey, entry.getValue());
            } else {
               return false;
            }
         }

         public boolean remove(Object obj) {
            if (obj instanceof Map.Entry) {
               Map.Entry entry = (Map.Entry)obj;
               return StandardTable.this.removeMapping(entry.getKey(), Column.this.columnKey, entry.getValue());
            } else {
               return false;
            }
         }

         public boolean retainAll(Collection c) {
            return Column.this.removeFromColumnIf(Predicates.not(Predicates.in(c)));
         }

         // $FF: synthetic method
         EntrySet(Object x1) {
            this();
         }
      }
   }

   class Row extends Maps.IteratorBasedAbstractMap {
      final Object rowKey;
      Map backingRowMap;

      Row(Object rowKey) {
         this.rowKey = Preconditions.checkNotNull(rowKey);
      }

      Map backingRowMap() {
         return this.backingRowMap != null && (!this.backingRowMap.isEmpty() || !StandardTable.this.backingMap.containsKey(this.rowKey)) ? this.backingRowMap : (this.backingRowMap = this.computeBackingRowMap());
      }

      Map computeBackingRowMap() {
         return (Map)StandardTable.this.backingMap.get(this.rowKey);
      }

      void maintainEmptyInvariant() {
         if (this.backingRowMap() != null && this.backingRowMap.isEmpty()) {
            StandardTable.this.backingMap.remove(this.rowKey);
            this.backingRowMap = null;
         }

      }

      public boolean containsKey(Object key) {
         Map backingRowMap = this.backingRowMap();
         return key != null && backingRowMap != null && Maps.safeContainsKey(backingRowMap, key);
      }

      public Object get(Object key) {
         Map backingRowMap = this.backingRowMap();
         return key != null && backingRowMap != null ? Maps.safeGet(backingRowMap, key) : null;
      }

      public Object put(Object key, Object value) {
         Preconditions.checkNotNull(key);
         Preconditions.checkNotNull(value);
         return this.backingRowMap != null && !this.backingRowMap.isEmpty() ? this.backingRowMap.put(key, value) : StandardTable.this.put(this.rowKey, key, value);
      }

      public Object remove(Object key) {
         Map backingRowMap = this.backingRowMap();
         if (backingRowMap == null) {
            return null;
         } else {
            Object result = Maps.safeRemove(backingRowMap, key);
            this.maintainEmptyInvariant();
            return result;
         }
      }

      public void clear() {
         Map backingRowMap = this.backingRowMap();
         if (backingRowMap != null) {
            backingRowMap.clear();
         }

         this.maintainEmptyInvariant();
      }

      public int size() {
         Map map = this.backingRowMap();
         return map == null ? 0 : map.size();
      }

      Iterator entryIterator() {
         Map map = this.backingRowMap();
         if (map == null) {
            return Iterators.emptyModifiableIterator();
         } else {
            final Iterator iterator = map.entrySet().iterator();
            return new Iterator() {
               public boolean hasNext() {
                  return iterator.hasNext();
               }

               public Map.Entry next() {
                  return Row.this.wrapEntry((Map.Entry)iterator.next());
               }

               public void remove() {
                  iterator.remove();
                  Row.this.maintainEmptyInvariant();
               }
            };
         }
      }

      Map.Entry wrapEntry(final Map.Entry entry) {
         return new ForwardingMapEntry() {
            protected Map.Entry delegate() {
               return entry;
            }

            public Object setValue(Object value) {
               return super.setValue(Preconditions.checkNotNull(value));
            }

            public boolean equals(Object object) {
               return this.standardEquals(object);
            }
         };
      }
   }

   private class CellIterator implements Iterator {
      final Iterator rowIterator;
      Map.Entry rowEntry;
      Iterator columnIterator;

      private CellIterator() {
         this.rowIterator = StandardTable.this.backingMap.entrySet().iterator();
         this.columnIterator = Iterators.emptyModifiableIterator();
      }

      public boolean hasNext() {
         return this.rowIterator.hasNext() || this.columnIterator.hasNext();
      }

      public Table.Cell next() {
         if (!this.columnIterator.hasNext()) {
            this.rowEntry = (Map.Entry)this.rowIterator.next();
            this.columnIterator = ((Map)this.rowEntry.getValue()).entrySet().iterator();
         }

         Map.Entry columnEntry = (Map.Entry)this.columnIterator.next();
         return Tables.immutableCell(this.rowEntry.getKey(), columnEntry.getKey(), columnEntry.getValue());
      }

      public void remove() {
         this.columnIterator.remove();
         if (((Map)this.rowEntry.getValue()).isEmpty()) {
            this.rowIterator.remove();
         }

      }

      // $FF: synthetic method
      CellIterator(Object x1) {
         this();
      }
   }

   private abstract class TableSet extends Sets.ImprovedAbstractSet {
      private TableSet() {
      }

      public boolean isEmpty() {
         return StandardTable.this.backingMap.isEmpty();
      }

      public void clear() {
         StandardTable.this.backingMap.clear();
      }

      // $FF: synthetic method
      TableSet(Object x1) {
         this();
      }
   }
}
