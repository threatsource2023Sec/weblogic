package org.python.google.common.collect;

import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@Beta
@GwtIncompatible
public interface Interner {
   @CanIgnoreReturnValue
   Object intern(Object var1);
}
