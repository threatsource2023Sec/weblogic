package org.python.google.common.collect;

import java.util.Map;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Preconditions;

@GwtCompatible
@Immutable
final class DenseImmutableTable extends RegularImmutableTable {
   private final ImmutableMap rowKeyToIndex;
   private final ImmutableMap columnKeyToIndex;
   private final ImmutableMap rowMap;
   private final ImmutableMap columnMap;
   private final int[] rowCounts;
   private final int[] columnCounts;
   private final Object[][] values;
   private final int[] cellRowIndices;
   private final int[] cellColumnIndices;

   DenseImmutableTable(ImmutableList cellList, ImmutableSet rowSpace, ImmutableSet columnSpace) {
      Object[][] array = (Object[][])(new Object[rowSpace.size()][columnSpace.size()]);
      this.values = array;
      this.rowKeyToIndex = Maps.indexMap(rowSpace);
      this.columnKeyToIndex = Maps.indexMap(columnSpace);
      this.rowCounts = new int[this.rowKeyToIndex.size()];
      this.columnCounts = new int[this.columnKeyToIndex.size()];
      int[] cellRowIndices = new int[cellList.size()];
      int[] cellColumnIndices = new int[cellList.size()];

      for(int i = 0; i < cellList.size(); ++i) {
         Table.Cell cell = (Table.Cell)cellList.get(i);
         Object rowKey = cell.getRowKey();
         Object columnKey = cell.getColumnKey();
         int rowIndex = (Integer)this.rowKeyToIndex.get(rowKey);
         int columnIndex = (Integer)this.columnKeyToIndex.get(columnKey);
         Object existingValue = this.values[rowIndex][columnIndex];
         Preconditions.checkArgument(existingValue == null, "duplicate key: (%s, %s)", rowKey, columnKey);
         this.values[rowIndex][columnIndex] = cell.getValue();
         int var10002 = this.rowCounts[rowIndex]++;
         var10002 = this.columnCounts[columnIndex]++;
         cellRowIndices[i] = rowIndex;
         cellColumnIndices[i] = columnIndex;
      }

      this.cellRowIndices = cellRowIndices;
      this.cellColumnIndices = cellColumnIndices;
      this.rowMap = new RowMap();
      this.columnMap = new ColumnMap();
   }

   public ImmutableMap columnMap() {
      return this.columnMap;
   }

   public ImmutableMap rowMap() {
      return this.rowMap;
   }

   public Object get(@Nullable Object rowKey, @Nullable Object columnKey) {
      Integer rowIndex = (Integer)this.rowKeyToIndex.get(rowKey);
      Integer columnIndex = (Integer)this.columnKeyToIndex.get(columnKey);
      return rowIndex != null && columnIndex != null ? this.values[rowIndex][columnIndex] : null;
   }

   public int size() {
      return this.cellRowIndices.length;
   }

   Table.Cell getCell(int index) {
      int rowIndex = this.cellRowIndices[index];
      int columnIndex = this.cellColumnIndices[index];
      Object rowKey = this.rowKeySet().asList().get(rowIndex);
      Object columnKey = this.columnKeySet().asList().get(columnIndex);
      Object value = this.values[rowIndex][columnIndex];
      return cellOf(rowKey, columnKey, value);
   }

   Object getValue(int index) {
      return this.values[this.cellRowIndices[index]][this.cellColumnIndices[index]];
   }

   ImmutableTable.SerializedForm createSerializedForm() {
      return ImmutableTable.SerializedForm.create(this, this.cellRowIndices, this.cellColumnIndices);
   }

   private final class ColumnMap extends ImmutableArrayMap {
      private ColumnMap() {
         super(DenseImmutableTable.this.columnCounts.length);
      }

      ImmutableMap keyToIndex() {
         return DenseImmutableTable.this.columnKeyToIndex;
      }

      Map getValue(int keyIndex) {
         return DenseImmutableTable.this.new Column(keyIndex);
      }

      boolean isPartialView() {
         return false;
      }

      // $FF: synthetic method
      ColumnMap(Object x1) {
         this();
      }
   }

   private final class RowMap extends ImmutableArrayMap {
      private RowMap() {
         super(DenseImmutableTable.this.rowCounts.length);
      }

      ImmutableMap keyToIndex() {
         return DenseImmutableTable.this.rowKeyToIndex;
      }

      Map getValue(int keyIndex) {
         return DenseImmutableTable.this.new Row(keyIndex);
      }

      boolean isPartialView() {
         return false;
      }

      // $FF: synthetic method
      RowMap(Object x1) {
         this();
      }
   }

   private final class Column extends ImmutableArrayMap {
      private final int columnIndex;

      Column(int columnIndex) {
         super(DenseImmutableTable.this.columnCounts[columnIndex]);
         this.columnIndex = columnIndex;
      }

      ImmutableMap keyToIndex() {
         return DenseImmutableTable.this.rowKeyToIndex;
      }

      Object getValue(int keyIndex) {
         return DenseImmutableTable.this.values[keyIndex][this.columnIndex];
      }

      boolean isPartialView() {
         return true;
      }
   }

   private final class Row extends ImmutableArrayMap {
      private final int rowIndex;

      Row(int rowIndex) {
         super(DenseImmutableTable.this.rowCounts[rowIndex]);
         this.rowIndex = rowIndex;
      }

      ImmutableMap keyToIndex() {
         return DenseImmutableTable.this.columnKeyToIndex;
      }

      Object getValue(int keyIndex) {
         return DenseImmutableTable.this.values[this.rowIndex][keyIndex];
      }

      boolean isPartialView() {
         return true;
      }
   }

   private abstract static class ImmutableArrayMap extends ImmutableMap.IteratorBasedImmutableMap {
      private final int size;

      ImmutableArrayMap(int size) {
         this.size = size;
      }

      abstract ImmutableMap keyToIndex();

      private boolean isFull() {
         return this.size == this.keyToIndex().size();
      }

      Object getKey(int index) {
         return this.keyToIndex().keySet().asList().get(index);
      }

      @Nullable
      abstract Object getValue(int var1);

      ImmutableSet createKeySet() {
         return this.isFull() ? this.keyToIndex().keySet() : super.createKeySet();
      }

      public int size() {
         return this.size;
      }

      public Object get(@Nullable Object key) {
         Integer keyIndex = (Integer)this.keyToIndex().get(key);
         return keyIndex == null ? null : this.getValue(keyIndex);
      }

      UnmodifiableIterator entryIterator() {
         return new AbstractIterator() {
            private int index = -1;
            private final int maxIndex = ImmutableArrayMap.this.keyToIndex().size();

            protected Map.Entry computeNext() {
               ++this.index;

               while(this.index < this.maxIndex) {
                  Object value = ImmutableArrayMap.this.getValue(this.index);
                  if (value != null) {
                     return Maps.immutableEntry(ImmutableArrayMap.this.getKey(this.index), value);
                  }

                  ++this.index;
               }

               return (Map.Entry)this.endOfData();
            }
         };
      }
   }
}
