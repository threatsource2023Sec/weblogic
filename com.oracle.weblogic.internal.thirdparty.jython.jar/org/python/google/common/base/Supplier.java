package org.python.google.common.base;

import org.python.google.common.annotations.GwtCompatible;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtCompatible
public interface Supplier {
   @CanIgnoreReturnValue
   Object get();
}
