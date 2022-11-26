package org.python.google.common.collect;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;
import org.python.google.errorprone.annotations.CompatibleWith;

@GwtCompatible
public interface Multimap {
   int size();

   boolean isEmpty();

   boolean containsKey(@Nullable @CompatibleWith("K") Object var1);

   boolean containsValue(@Nullable @CompatibleWith("V") Object var1);

   boolean containsEntry(@Nullable @CompatibleWith("K") Object var1, @Nullable @CompatibleWith("V") Object var2);

   @CanIgnoreReturnValue
   boolean put(@Nullable Object var1, @Nullable Object var2);

   @CanIgnoreReturnValue
   boolean remove(@Nullable @CompatibleWith("K") Object var1, @Nullable @CompatibleWith("V") Object var2);

   @CanIgnoreReturnValue
   boolean putAll(@Nullable Object var1, Iterable var2);

   @CanIgnoreReturnValue
   boolean putAll(Multimap var1);

   @CanIgnoreReturnValue
   Collection replaceValues(@Nullable Object var1, Iterable var2);

   @CanIgnoreReturnValue
   Collection removeAll(@Nullable @CompatibleWith("K") Object var1);

   void clear();

   Collection get(@Nullable Object var1);

   Set keySet();

   Multiset keys();

   Collection values();

   Collection entries();

   Map asMap();

   boolean equals(@Nullable Object var1);

   int hashCode();
}
