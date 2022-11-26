package org.python.google.common.collect;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.concurrent.Immutable;
import org.python.google.common.annotations.GwtCompatible;

@GwtCompatible
@Immutable
final class SparseImmutableTable extends RegularImmutableTable {
   static final ImmutableTable EMPTY = new SparseImmutableTable(ImmutableList.of(), ImmutableSet.of(), ImmutableSet.of());
   private final ImmutableMap rowMap;
   private final ImmutableMap columnMap;
   private final int[] cellRowIndices;
   private final int[] cellColumnInRowIndices;

   SparseImmutableTable(ImmutableList cellList, ImmutableSet rowSpace, ImmutableSet columnSpace) {
      Map rowIndex = Maps.indexMap(rowSpace);
      Map rows = Maps.newLinkedHashMap();
      UnmodifiableIterator var6 = rowSpace.iterator();

      while(var6.hasNext()) {
         Object row = var6.next();
         rows.put(row, new LinkedHashMap());
      }

      Map columns = Maps.newLinkedHashMap();
      UnmodifiableIterator var17 = columnSpace.iterator();

      while(var17.hasNext()) {
         Object col = var17.next();
         columns.put(col, new LinkedHashMap());
      }

      int[] cellRowIndices = new int[cellList.size()];
      int[] cellColumnInRowIndices = new int[cellList.size()];

      for(int i = 0; i < cellList.size(); ++i) {
         Table.Cell cell = (Table.Cell)cellList.get(i);
         Object rowKey = cell.getRowKey();
         Object columnKey = cell.getColumnKey();
         Object value = cell.getValue();
         cellRowIndices[i] = (Integer)rowIndex.get(rowKey);
         Map thisRow = (Map)rows.get(rowKey);
         cellColumnInRowIndices[i] = thisRow.size();
         Object oldValue = thisRow.put(columnKey, value);
         if (oldValue != null) {
            throw new IllegalArgumentException("Duplicate value for row=" + rowKey + ", column=" + columnKey + ": " + value + ", " + oldValue);
         }

         ((Map)columns.get(columnKey)).put(rowKey, value);
      }

      this.cellRowIndices = cellRowIndices;
      this.cellColumnInRowIndices = cellColumnInRowIndices;
      ImmutableMap.Builder rowBuilder = new ImmutableMap.Builder(rows.size());
      Iterator var21 = rows.entrySet().iterator();

      while(var21.hasNext()) {
         Map.Entry row = (Map.Entry)var21.next();
         rowBuilder.put(row.getKey(), ImmutableMap.copyOf((Map)row.getValue()));
      }

      this.rowMap = rowBuilder.build();
      ImmutableMap.Builder columnBuilder = new ImmutableMap.Builder(columns.size());
      Iterator var24 = columns.entrySet().iterator();

      while(var24.hasNext()) {
         Map.Entry col = (Map.Entry)var24.next();
         columnBuilder.put(col.getKey(), ImmutableMap.copyOf((Map)col.getValue()));
      }

      this.columnMap = columnBuilder.build();
   }

   public ImmutableMap columnMap() {
      return this.columnMap;
   }

   public ImmutableMap rowMap() {
      return this.rowMap;
   }

   public int size() {
      return this.cellRowIndices.length;
   }

   Table.Cell getCell(int index) {
      int rowIndex = this.cellRowIndices[index];
      Map.Entry rowEntry = (Map.Entry)this.rowMap.entrySet().asList().get(rowIndex);
      ImmutableMap row = (ImmutableMap)rowEntry.getValue();
      int columnIndex = this.cellColumnInRowIndices[index];
      Map.Entry colEntry = (Map.Entry)row.entrySet().asList().get(columnIndex);
      return cellOf(rowEntry.getKey(), colEntry.getKey(), colEntry.getValue());
   }

   Object getValue(int index) {
      int rowIndex = this.cellRowIndices[index];
      ImmutableMap row = (ImmutableMap)this.rowMap.values().asList().get(rowIndex);
      int columnIndex = this.cellColumnInRowIndices[index];
      return row.values().asList().get(columnIndex);
   }

   ImmutableTable.SerializedForm createSerializedForm() {
      Map columnKeyToIndex = Maps.indexMap(this.columnKeySet());
      int[] cellColumnIndices = new int[this.cellSet().size()];
      int i = 0;

      Table.Cell cell;
      for(UnmodifiableIterator var4 = this.cellSet().iterator(); var4.hasNext(); cellColumnIndices[i++] = (Integer)columnKeyToIndex.get(cell.getColumnKey())) {
         cell = (Table.Cell)var4.next();
      }

      return ImmutableTable.SerializedForm.create(this, this.cellRowIndices, cellColumnIndices);
   }
}
