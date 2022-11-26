package org.python.google.common.collect;

import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtCompatible
public interface SetMultimap extends Multimap {
   Set get(@Nullable Object var1);

   @CanIgnoreReturnValue
   Set removeAll(@Nullable Object var1);

   @CanIgnoreReturnValue
   Set replaceValues(Object var1, Iterable var2);

   Set entries();

   Map asMap();

   boolean equals(@Nullable Object var1);
}
