package org.python.google.common.collect;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;
import org.python.google.errorprone.annotations.CompatibleWith;

@GwtCompatible
public interface Multiset extends Collection {
   int size();

   int count(@Nullable @CompatibleWith("E") Object var1);

   @CanIgnoreReturnValue
   int add(@Nullable Object var1, int var2);

   @CanIgnoreReturnValue
   int remove(@Nullable @CompatibleWith("E") Object var1, int var2);

   @CanIgnoreReturnValue
   int setCount(Object var1, int var2);

   @CanIgnoreReturnValue
   boolean setCount(Object var1, int var2, int var3);

   Set elementSet();

   Set entrySet();

   boolean equals(@Nullable Object var1);

   int hashCode();

   String toString();

   Iterator iterator();

   boolean contains(@Nullable Object var1);

   boolean containsAll(Collection var1);

   @CanIgnoreReturnValue
   boolean add(Object var1);

   @CanIgnoreReturnValue
   boolean remove(@Nullable Object var1);

   @CanIgnoreReturnValue
   boolean removeAll(Collection var1);

   @CanIgnoreReturnValue
   boolean retainAll(Collection var1);

   public interface Entry {
      Object getElement();

      int getCount();

      boolean equals(Object var1);

      int hashCode();

      String toString();
   }
}
