package org.python.google.common.collect;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Supplier;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtCompatible(
   serializable = true
)
public class HashBasedTable extends StandardTable {
   private static final long serialVersionUID = 0L;

   public static HashBasedTable create() {
      return new HashBasedTable(new LinkedHashMap(), new Factory(0));
   }

   public static HashBasedTable create(int expectedRows, int expectedCellsPerRow) {
      CollectPreconditions.checkNonnegative(expectedCellsPerRow, "expectedCellsPerRow");
      Map backingMap = Maps.newLinkedHashMapWithExpectedSize(expectedRows);
      return new HashBasedTable(backingMap, new Factory(expectedCellsPerRow));
   }

   public static HashBasedTable create(Table table) {
      HashBasedTable result = create();
      result.putAll(table);
      return result;
   }

   HashBasedTable(Map backingMap, Factory factory) {
      super(backingMap, factory);
   }

   public boolean contains(@Nullable Object rowKey, @Nullable Object columnKey) {
      return super.contains(rowKey, columnKey);
   }

   public boolean containsColumn(@Nullable Object columnKey) {
      return super.containsColumn(columnKey);
   }

   public boolean containsRow(@Nullable Object rowKey) {
      return super.containsRow(rowKey);
   }

   public boolean containsValue(@Nullable Object value) {
      return super.containsValue(value);
   }

   public Object get(@Nullable Object rowKey, @Nullable Object columnKey) {
      return super.get(rowKey, columnKey);
   }

   public boolean equals(@Nullable Object obj) {
      return super.equals(obj);
   }

   @CanIgnoreReturnValue
   public Object remove(@Nullable Object rowKey, @Nullable Object columnKey) {
      return super.remove(rowKey, columnKey);
   }

   private static class Factory implements Supplier, Serializable {
      final int expectedSize;
      private static final long serialVersionUID = 0L;

      Factory(int expectedSize) {
         this.expectedSize = expectedSize;
      }

      public Map get() {
         return Maps.newLinkedHashMapWithExpectedSize(this.expectedSize);
      }
   }
}
