package org.python.google.common.collect;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.base.Objects;
import org.python.google.common.base.Preconditions;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@Beta
@GwtCompatible(
   emulated = true
)
public final class ArrayTable extends AbstractTable implements Serializable {
   private final ImmutableList rowList;
   private final ImmutableList columnList;
   private final ImmutableMap rowKeyToIndex;
   private final ImmutableMap columnKeyToIndex;
   private final Object[][] array;
   private transient ColumnMap columnMap;
   private transient RowMap rowMap;
   private static final long serialVersionUID = 0L;

   public static ArrayTable create(Iterable rowKeys, Iterable columnKeys) {
      return new ArrayTable(rowKeys, columnKeys);
   }

   public static ArrayTable create(Table table) {
      return table instanceof ArrayTable ? new ArrayTable((ArrayTable)table) : new ArrayTable(table);
   }

   private ArrayTable(Iterable rowKeys, Iterable columnKeys) {
      this.rowList = ImmutableList.copyOf(rowKeys);
      this.columnList = ImmutableList.copyOf(columnKeys);
      Preconditions.checkArgument(!this.rowList.isEmpty());
      Preconditions.checkArgument(!this.columnList.isEmpty());
      this.rowKeyToIndex = Maps.indexMap(this.rowList);
      this.columnKeyToIndex = Maps.indexMap(this.columnList);
      Object[][] tmpArray = (Object[][])(new Object[this.rowList.size()][this.columnList.size()]);
      this.array = tmpArray;
      this.eraseAll();
   }

   private ArrayTable(Table table) {
      this(table.rowKeySet(), table.columnKeySet());
      this.putAll(table);
   }

   private ArrayTable(ArrayTable table) {
      this.rowList = table.rowList;
      this.columnList = table.columnList;
      this.rowKeyToIndex = table.rowKeyToIndex;
      this.columnKeyToIndex = table.columnKeyToIndex;
      Object[][] copy = (Object[][])(new Object[this.rowList.size()][this.columnList.size()]);
      this.array = copy;
      this.eraseAll();

      for(int i = 0; i < this.rowList.size(); ++i) {
         System.arraycopy(table.array[i], 0, copy[i], 0, table.array[i].length);
      }

   }

   public ImmutableList rowKeyList() {
      return this.rowList;
   }

   public ImmutableList columnKeyList() {
      return this.columnList;
   }

   public Object at(int rowIndex, int columnIndex) {
      Preconditions.checkElementIndex(rowIndex, this.rowList.size());
      Preconditions.checkElementIndex(columnIndex, this.columnList.size());
      return this.array[rowIndex][columnIndex];
   }

   @CanIgnoreReturnValue
   public Object set(int rowIndex, int columnIndex, @Nullable Object value) {
      Preconditions.checkElementIndex(rowIndex, this.rowList.size());
      Preconditions.checkElementIndex(columnIndex, this.columnList.size());
      Object oldValue = this.array[rowIndex][columnIndex];
      this.array[rowIndex][columnIndex] = value;
      return oldValue;
   }

   @GwtIncompatible
   public Object[][] toArray(Class valueClass) {
      Object[][] copy = (Object[][])((Object[][])Array.newInstance(valueClass, new int[]{this.rowList.size(), this.columnList.size()}));

      for(int i = 0; i < this.rowList.size(); ++i) {
         System.arraycopy(this.array[i], 0, copy[i], 0, this.array[i].length);
      }

      return copy;
   }

   /** @deprecated */
   @Deprecated
   public void clear() {
      throw new UnsupportedOperationException();
   }

   public void eraseAll() {
      Object[][] var1 = this.array;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Object[] row = var1[var3];
         Arrays.fill(row, (Object)null);
      }

   }

   public boolean contains(@Nullable Object rowKey, @Nullable Object columnKey) {
      return this.containsRow(rowKey) && this.containsColumn(columnKey);
   }

   public boolean containsColumn(@Nullable Object columnKey) {
      return this.columnKeyToIndex.containsKey(columnKey);
   }

   public boolean containsRow(@Nullable Object rowKey) {
      return this.rowKeyToIndex.containsKey(rowKey);
   }

   public boolean containsValue(@Nullable Object value) {
      Object[][] var2 = this.array;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Object[] row = var2[var4];
         Object[] var6 = row;
         int var7 = row.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            Object element = var6[var8];
            if (Objects.equal(value, element)) {
               return true;
            }
         }
      }

      return false;
   }

   public Object get(@Nullable Object rowKey, @Nullable Object columnKey) {
      Integer rowIndex = (Integer)this.rowKeyToIndex.get(rowKey);
      Integer columnIndex = (Integer)this.columnKeyToIndex.get(columnKey);
      return rowIndex != null && columnIndex != null ? this.at(rowIndex, columnIndex) : null;
   }

   public boolean isEmpty() {
      return false;
   }

   @CanIgnoreReturnValue
   public Object put(Object rowKey, Object columnKey, @Nullable Object value) {
      Preconditions.checkNotNull(rowKey);
      Preconditions.checkNotNull(columnKey);
      Integer rowIndex = (Integer)this.rowKeyToIndex.get(rowKey);
      Preconditions.checkArgument(rowIndex != null, "Row %s not in %s", rowKey, this.rowList);
      Integer columnIndex = (Integer)this.columnKeyToIndex.get(columnKey);
      Preconditions.checkArgument(columnIndex != null, "Column %s not in %s", columnKey, this.columnList);
      return this.set(rowIndex, columnIndex, value);
   }

   public void putAll(Table table) {
      super.putAll(table);
   }

   /** @deprecated */
   @Deprecated
   @CanIgnoreReturnValue
   public Object remove(Object rowKey, Object columnKey) {
      throw new UnsupportedOperationException();
   }

   @CanIgnoreReturnValue
   public Object erase(@Nullable Object rowKey, @Nullable Object columnKey) {
      Integer rowIndex = (Integer)this.rowKeyToIndex.get(rowKey);
      Integer columnIndex = (Integer)this.columnKeyToIndex.get(columnKey);
      return rowIndex != null && columnIndex != null ? this.set(rowIndex, columnIndex, (Object)null) : null;
   }

   public int size() {
      return this.rowList.size() * this.columnList.size();
   }

   public Set cellSet() {
      return super.cellSet();
   }

   Iterator cellIterator() {
      return new AbstractIndexedListIterator(this.size()) {
         protected Table.Cell get(int index) {
            return ArrayTable.this.getCell(index);
         }
      };
   }

   private Table.Cell getCell(final int index) {
      return new Tables.AbstractCell() {
         final int rowIndex;
         final int columnIndex;

         {
            this.rowIndex = index / ArrayTable.this.columnList.size();
            this.columnIndex = index % ArrayTable.this.columnList.size();
         }

         public Object getRowKey() {
            return ArrayTable.this.rowList.get(this.rowIndex);
         }

         public Object getColumnKey() {
            return ArrayTable.this.columnList.get(this.columnIndex);
         }

         public Object getValue() {
            return ArrayTable.this.at(this.rowIndex, this.columnIndex);
         }
      };
   }

   private Object getValue(int index) {
      int rowIndex = index / this.columnList.size();
      int columnIndex = index % this.columnList.size();
      return this.at(rowIndex, columnIndex);
   }

   public Map column(Object columnKey) {
      Preconditions.checkNotNull(columnKey);
      Integer columnIndex = (Integer)this.columnKeyToIndex.get(columnKey);
      return (Map)(columnIndex == null ? ImmutableMap.of() : new Column(columnIndex));
   }

   public ImmutableSet columnKeySet() {
      return this.columnKeyToIndex.keySet();
   }

   public Map columnMap() {
      ColumnMap map = this.columnMap;
      return map == null ? (this.columnMap = new ColumnMap()) : map;
   }

   public Map row(Object rowKey) {
      Preconditions.checkNotNull(rowKey);
      Integer rowIndex = (Integer)this.rowKeyToIndex.get(rowKey);
      return (Map)(rowIndex == null ? ImmutableMap.of() : new Row(rowIndex));
   }

   public ImmutableSet rowKeySet() {
      return this.rowKeyToIndex.keySet();
   }

   public Map rowMap() {
      RowMap map = this.rowMap;
      return map == null ? (this.rowMap = new RowMap()) : map;
   }

   public Collection values() {
      return super.values();
   }

   Iterator valuesIterator() {
      return new AbstractIndexedListIterator(this.size()) {
         protected Object get(int index) {
            return ArrayTable.this.getValue(index);
         }
      };
   }

   private class RowMap extends ArrayMap {
      private RowMap() {
         super(ArrayTable.this.rowKeyToIndex, null);
      }

      String getKeyRole() {
         return "Row";
      }

      Map getValue(int index) {
         return ArrayTable.this.new Row(index);
      }

      Map setValue(int index, Map newValue) {
         throw new UnsupportedOperationException();
      }

      public Map put(Object key, Map value) {
         throw new UnsupportedOperationException();
      }

      // $FF: synthetic method
      RowMap(Object x1) {
         this();
      }
   }

   private class Row extends ArrayMap {
      final int rowIndex;

      Row(int rowIndex) {
         super(ArrayTable.this.columnKeyToIndex, null);
         this.rowIndex = rowIndex;
      }

      String getKeyRole() {
         return "Column";
      }

      Object getValue(int index) {
         return ArrayTable.this.at(this.rowIndex, index);
      }

      Object setValue(int index, Object newValue) {
         return ArrayTable.this.set(this.rowIndex, index, newValue);
      }
   }

   private class ColumnMap extends ArrayMap {
      private ColumnMap() {
         super(ArrayTable.this.columnKeyToIndex, null);
      }

      String getKeyRole() {
         return "Column";
      }

      Map getValue(int index) {
         return ArrayTable.this.new Column(index);
      }

      Map setValue(int index, Map newValue) {
         throw new UnsupportedOperationException();
      }

      public Map put(Object key, Map value) {
         throw new UnsupportedOperationException();
      }

      // $FF: synthetic method
      ColumnMap(Object x1) {
         this();
      }
   }

   private class Column extends ArrayMap {
      final int columnIndex;

      Column(int columnIndex) {
         super(ArrayTable.this.rowKeyToIndex, null);
         this.columnIndex = columnIndex;
      }

      String getKeyRole() {
         return "Row";
      }

      Object getValue(int index) {
         return ArrayTable.this.at(index, this.columnIndex);
      }

      Object setValue(int index, Object newValue) {
         return ArrayTable.this.set(index, this.columnIndex, newValue);
      }
   }

   private abstract static class ArrayMap extends Maps.IteratorBasedAbstractMap {
      private final ImmutableMap keyIndex;

      private ArrayMap(ImmutableMap keyIndex) {
         this.keyIndex = keyIndex;
      }

      public Set keySet() {
         return this.keyIndex.keySet();
      }

      Object getKey(int index) {
         return this.keyIndex.keySet().asList().get(index);
      }

      abstract String getKeyRole();

      @Nullable
      abstract Object getValue(int var1);

      @Nullable
      abstract Object setValue(int var1, Object var2);

      public int size() {
         return this.keyIndex.size();
      }

      public boolean isEmpty() {
         return this.keyIndex.isEmpty();
      }

      Map.Entry getEntry(final int index) {
         Preconditions.checkElementIndex(index, this.size());
         return new AbstractMapEntry() {
            public Object getKey() {
               return ArrayMap.this.getKey(index);
            }

            public Object getValue() {
               return ArrayMap.this.getValue(index);
            }

            public Object setValue(Object value) {
               return ArrayMap.this.setValue(index, value);
            }
         };
      }

      Iterator entryIterator() {
         return new AbstractIndexedListIterator(this.size()) {
            protected Map.Entry get(int index) {
               return ArrayMap.this.getEntry(index);
            }
         };
      }

      public boolean containsKey(@Nullable Object key) {
         return this.keyIndex.containsKey(key);
      }

      public Object get(@Nullable Object key) {
         Integer index = (Integer)this.keyIndex.get(key);
         return index == null ? null : this.getValue(index);
      }

      public Object put(Object key, Object value) {
         Integer index = (Integer)this.keyIndex.get(key);
         if (index == null) {
            throw new IllegalArgumentException(this.getKeyRole() + " " + key + " not in " + this.keyIndex.keySet());
         } else {
            return this.setValue(index, value);
         }
      }

      public Object remove(Object key) {
         throw new UnsupportedOperationException();
      }

      public void clear() {
         throw new UnsupportedOperationException();
      }

      // $FF: synthetic method
      ArrayMap(ImmutableMap x0, Object x1) {
         this(x0);
      }
   }
}
