package org.python.google.common.collect;

import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtCompatible
public interface ListMultimap extends Multimap {
   List get(@Nullable Object var1);

   @CanIgnoreReturnValue
   List removeAll(@Nullable Object var1);

   @CanIgnoreReturnValue
   List replaceValues(Object var1, Iterable var2);

   Map asMap();

   boolean equals(@Nullable Object var1);
}
