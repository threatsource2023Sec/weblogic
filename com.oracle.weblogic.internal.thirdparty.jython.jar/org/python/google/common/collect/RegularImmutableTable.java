package org.python.google.common.collect;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Preconditions;

@GwtCompatible
abstract class RegularImmutableTable extends ImmutableTable {
   abstract Table.Cell getCell(int var1);

   final ImmutableSet createCellSet() {
      return (ImmutableSet)(this.isEmpty() ? ImmutableSet.of() : new CellSet());
   }

   abstract Object getValue(int var1);

   final ImmutableCollection createValues() {
      return (ImmutableCollection)(this.isEmpty() ? ImmutableList.of() : new Values());
   }

   static RegularImmutableTable forCells(List cells, @Nullable final Comparator rowComparator, @Nullable final Comparator columnComparator) {
      Preconditions.checkNotNull(cells);
      if (rowComparator != null || columnComparator != null) {
         Comparator comparator = new Comparator() {
            public int compare(Table.Cell cell1, Table.Cell cell2) {
               int rowCompare = rowComparator == null ? 0 : rowComparator.compare(cell1.getRowKey(), cell2.getRowKey());
               if (rowCompare != 0) {
                  return rowCompare;
               } else {
                  return columnComparator == null ? 0 : columnComparator.compare(cell1.getColumnKey(), cell2.getColumnKey());
               }
            }
         };
         Collections.sort(cells, comparator);
      }

      return forCellsInternal(cells, rowComparator, columnComparator);
   }

   static RegularImmutableTable forCells(Iterable cells) {
      return forCellsInternal(cells, (Comparator)null, (Comparator)null);
   }

   private static final RegularImmutableTable forCellsInternal(Iterable cells, @Nullable Comparator rowComparator, @Nullable Comparator columnComparator) {
      Set rowSpaceBuilder = new LinkedHashSet();
      Set columnSpaceBuilder = new LinkedHashSet();
      ImmutableList cellList = ImmutableList.copyOf(cells);
      Iterator var6 = cells.iterator();

      while(var6.hasNext()) {
         Table.Cell cell = (Table.Cell)var6.next();
         rowSpaceBuilder.add(cell.getRowKey());
         columnSpaceBuilder.add(cell.getColumnKey());
      }

      ImmutableSet rowSpace = rowComparator == null ? ImmutableSet.copyOf((Collection)rowSpaceBuilder) : ImmutableSet.copyOf((Collection)ImmutableList.sortedCopyOf(rowComparator, rowSpaceBuilder));
      ImmutableSet columnSpace = columnComparator == null ? ImmutableSet.copyOf((Collection)columnSpaceBuilder) : ImmutableSet.copyOf((Collection)ImmutableList.sortedCopyOf(columnComparator, columnSpaceBuilder));
      return forOrderedComponents(cellList, rowSpace, columnSpace);
   }

   static RegularImmutableTable forOrderedComponents(ImmutableList cellList, ImmutableSet rowSpace, ImmutableSet columnSpace) {
      return (RegularImmutableTable)((long)cellList.size() > (long)rowSpace.size() * (long)columnSpace.size() / 2L ? new DenseImmutableTable(cellList, rowSpace, columnSpace) : new SparseImmutableTable(cellList, rowSpace, columnSpace));
   }

   private final class Values extends ImmutableList {
      private Values() {
      }

      public int size() {
         return RegularImmutableTable.this.size();
      }

      public Object get(int index) {
         return RegularImmutableTable.this.getValue(index);
      }

      boolean isPartialView() {
         return true;
      }

      // $FF: synthetic method
      Values(Object x1) {
         this();
      }
   }

   private final class CellSet extends ImmutableSet.Indexed {
      private CellSet() {
      }

      public int size() {
         return RegularImmutableTable.this.size();
      }

      Table.Cell get(int index) {
         return RegularImmutableTable.this.getCell(index);
      }

      public boolean contains(@Nullable Object object) {
         if (!(object instanceof Table.Cell)) {
            return false;
         } else {
            Table.Cell cell = (Table.Cell)object;
            Object value = RegularImmutableTable.this.get(cell.getRowKey(), cell.getColumnKey());
            return value != null && value.equals(cell.getValue());
         }
      }

      boolean isPartialView() {
         return false;
      }

      // $FF: synthetic method
      CellSet(Object x1) {
         this();
      }
   }
}
