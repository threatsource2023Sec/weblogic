package org.python.google.common.collect;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;
import org.python.google.errorprone.annotations.CompatibleWith;

@GwtCompatible
public interface Table {
   boolean contains(@Nullable @CompatibleWith("R") Object var1, @Nullable @CompatibleWith("C") Object var2);

   boolean containsRow(@Nullable @CompatibleWith("R") Object var1);

   boolean containsColumn(@Nullable @CompatibleWith("C") Object var1);

   boolean containsValue(@Nullable @CompatibleWith("V") Object var1);

   Object get(@Nullable @CompatibleWith("R") Object var1, @Nullable @CompatibleWith("C") Object var2);

   boolean isEmpty();

   int size();

   boolean equals(@Nullable Object var1);

   int hashCode();

   void clear();

   @Nullable
   @CanIgnoreReturnValue
   Object put(Object var1, Object var2, Object var3);

   void putAll(Table var1);

   @Nullable
   @CanIgnoreReturnValue
   Object remove(@Nullable @CompatibleWith("R") Object var1, @Nullable @CompatibleWith("C") Object var2);

   Map row(Object var1);

   Map column(Object var1);

   Set cellSet();

   Set rowKeySet();

   Set columnKeySet();

   Collection values();

   Map rowMap();

   Map columnMap();

   public interface Cell {
      @Nullable
      Object getRowKey();

      @Nullable
      Object getColumnKey();

      @Nullable
      Object getValue();

      boolean equals(@Nullable Object var1);

      int hashCode();
   }
}
