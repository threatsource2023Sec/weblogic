package org.python.google.common.collect;

import java.util.Map;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtCompatible
public interface ClassToInstanceMap extends Map {
   @CanIgnoreReturnValue
   Object getInstance(Class var1);

   @CanIgnoreReturnValue
   Object putInstance(Class var1, @Nullable Object var2);
}
