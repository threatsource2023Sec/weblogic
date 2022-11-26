package org.python.google.common.base;

import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtCompatible
public interface Predicate {
   @CanIgnoreReturnValue
   boolean apply(@Nullable Object var1);

   boolean equals(@Nullable Object var1);
}
