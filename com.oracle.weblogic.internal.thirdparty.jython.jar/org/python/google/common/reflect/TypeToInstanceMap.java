package org.python.google.common.reflect;

import java.util.Map;
import javax.annotation.Nullable;
import org.python.google.common.annotations.Beta;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@Beta
public interface TypeToInstanceMap extends Map {
   @Nullable
   Object getInstance(Class var1);

   @Nullable
   @CanIgnoreReturnValue
   Object putInstance(Class var1, @Nullable Object var2);

   @Nullable
   Object getInstance(TypeToken var1);

   @Nullable
   @CanIgnoreReturnValue
   Object putInstance(TypeToken var1, @Nullable Object var2);
}
