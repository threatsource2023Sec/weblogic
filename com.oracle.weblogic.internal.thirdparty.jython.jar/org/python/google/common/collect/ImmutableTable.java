package org.python.google.common.collect;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.MoreObjects;
import org.python.google.common.base.Preconditions;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtCompatible
public abstract class ImmutableTable extends AbstractTable implements Serializable {
   public static ImmutableTable of() {
      return SparseImmutableTable.EMPTY;
   }

   public static ImmutableTable of(Object rowKey, Object columnKey, Object value) {
      return new SingletonImmutableTable(rowKey, columnKey, value);
   }

   public static ImmutableTable copyOf(Table table) {
      if (table instanceof ImmutableTable) {
         ImmutableTable parameterizedTable = (ImmutableTable)table;
         return parameterizedTable;
      } else {
         return copyOf((Iterable)table.cellSet());
      }
   }

   private static ImmutableTable copyOf(Iterable cells) {
      Builder builder = builder();
      Iterator var2 = cells.iterator();

      while(var2.hasNext()) {
         Table.Cell cell = (Table.Cell)var2.next();
         builder.put(cell);
      }

      return builder.build();
   }

   public static Builder builder() {
      return new Builder();
   }

   static Table.Cell cellOf(Object rowKey, Object columnKey, Object value) {
      return Tables.immutableCell(Preconditions.checkNotNull(rowKey), Preconditions.checkNotNull(columnKey), Preconditions.checkNotNull(value));
   }

   ImmutableTable() {
   }

   public ImmutableSet cellSet() {
      return (ImmutableSet)super.cellSet();
   }

   abstract ImmutableSet createCellSet();

   final UnmodifiableIterator cellIterator() {
      throw new AssertionError("should never be called");
   }

   public ImmutableCollection values() {
      return (ImmutableCollection)super.values();
   }

   abstract ImmutableCollection createValues();

   final Iterator valuesIterator() {
      throw new AssertionError("should never be called");
   }

   public ImmutableMap column(Object columnKey) {
      Preconditions.checkNotNull(columnKey);
      return (ImmutableMap)MoreObjects.firstNonNull((ImmutableMap)this.columnMap().get(columnKey), ImmutableMap.of());
   }

   public ImmutableSet columnKeySet() {
      return this.columnMap().keySet();
   }

   public abstract ImmutableMap columnMap();

   public ImmutableMap row(Object rowKey) {
      Preconditions.checkNotNull(rowKey);
      return (ImmutableMap)MoreObjects.firstNonNull((ImmutableMap)this.rowMap().get(rowKey), ImmutableMap.of());
   }

   public ImmutableSet rowKeySet() {
      return this.rowMap().keySet();
   }

   public abstract ImmutableMap rowMap();

   public boolean contains(@Nullable Object rowKey, @Nullable Object columnKey) {
      return this.get(rowKey, columnKey) != null;
   }

   public boolean containsValue(@Nullable Object value) {
      return this.values().contains(value);
   }

   /** @deprecated */
   @Deprecated
   public final void clear() {
      throw new UnsupportedOperationException();
   }

   /** @deprecated */
   @Deprecated
   @CanIgnoreReturnValue
   public final Object put(Object rowKey, Object columnKey, Object value) {
      throw new UnsupportedOperationException();
   }

   /** @deprecated */
   @Deprecated
   public final void putAll(Table table) {
      throw new UnsupportedOperationException();
   }

   /** @deprecated */
   @Deprecated
   @CanIgnoreReturnValue
   public final Object remove(Object rowKey, Object columnKey) {
      throw new UnsupportedOperationException();
   }

   abstract SerializedForm createSerializedForm();

   final Object writeReplace() {
      return this.createSerializedForm();
   }

   static final class SerializedForm implements Serializable {
      private final Object[] rowKeys;
      private final Object[] columnKeys;
      private final Object[] cellValues;
      private final int[] cellRowIndices;
      private final int[] cellColumnIndices;
      private static final long serialVersionUID = 0L;

      private SerializedForm(Object[] rowKeys, Object[] columnKeys, Object[] cellValues, int[] cellRowIndices, int[] cellColumnIndices) {
         this.rowKeys = rowKeys;
         this.columnKeys = columnKeys;
         this.cellValues = cellValues;
         this.cellRowIndices = cellRowIndices;
         this.cellColumnIndices = cellColumnIndices;
      }

      static SerializedForm create(ImmutableTable table, int[] cellRowIndices, int[] cellColumnIndices) {
         return new SerializedForm(table.rowKeySet().toArray(), table.columnKeySet().toArray(), table.values().toArray(), cellRowIndices, cellColumnIndices);
      }

      Object readResolve() {
         if (this.cellValues.length == 0) {
            return ImmutableTable.of();
         } else if (this.cellValues.length == 1) {
            return ImmutableTable.of(this.rowKeys[0], this.columnKeys[0], this.cellValues[0]);
         } else {
            ImmutableList.Builder cellListBuilder = new ImmutableList.Builder(this.cellValues.length);

            for(int i = 0; i < this.cellValues.length; ++i) {
               cellListBuilder.add((Object)ImmutableTable.cellOf(this.rowKeys[this.cellRowIndices[i]], this.columnKeys[this.cellColumnIndices[i]], this.cellValues[i]));
            }

            return RegularImmutableTable.forOrderedComponents(cellListBuilder.build(), ImmutableSet.copyOf(this.rowKeys), ImmutableSet.copyOf(this.columnKeys));
         }
      }
   }

   public static final class Builder {
      private final List cells = Lists.newArrayList();
      private Comparator rowComparator;
      private Comparator columnComparator;

      @CanIgnoreReturnValue
      public Builder orderRowsBy(Comparator rowComparator) {
         this.rowComparator = (Comparator)Preconditions.checkNotNull(rowComparator);
         return this;
      }

      @CanIgnoreReturnValue
      public Builder orderColumnsBy(Comparator columnComparator) {
         this.columnComparator = (Comparator)Preconditions.checkNotNull(columnComparator);
         return this;
      }

      @CanIgnoreReturnValue
      public Builder put(Object rowKey, Object columnKey, Object value) {
         this.cells.add(ImmutableTable.cellOf(rowKey, columnKey, value));
         return this;
      }

      @CanIgnoreReturnValue
      public Builder put(Table.Cell cell) {
         if (cell instanceof Tables.ImmutableCell) {
            Preconditions.checkNotNull(cell.getRowKey());
            Preconditions.checkNotNull(cell.getColumnKey());
            Preconditions.checkNotNull(cell.getValue());
            this.cells.add(cell);
         } else {
            this.put(cell.getRowKey(), cell.getColumnKey(), cell.getValue());
         }

         return this;
      }

      @CanIgnoreReturnValue
      public Builder putAll(Table table) {
         Iterator var2 = table.cellSet().iterator();

         while(var2.hasNext()) {
            Table.Cell cell = (Table.Cell)var2.next();
            this.put(cell);
         }

         return this;
      }

      public ImmutableTable build() {
         int size = this.cells.size();
         switch (size) {
            case 0:
               return ImmutableTable.of();
            case 1:
               return new SingletonImmutableTable((Table.Cell)Iterables.getOnlyElement(this.cells));
            default:
               return RegularImmutableTable.forCells(this.cells, this.rowComparator, this.columnComparator);
         }
      }
   }
}
