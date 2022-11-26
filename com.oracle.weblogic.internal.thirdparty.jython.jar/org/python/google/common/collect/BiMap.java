package org.python.google.common.collect;

import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtCompatible
public interface BiMap extends Map {
   @Nullable
   @CanIgnoreReturnValue
   Object put(@Nullable Object var1, @Nullable Object var2);

   @Nullable
   @CanIgnoreReturnValue
   Object forcePut(@Nullable Object var1, @Nullable Object var2);

   void putAll(Map var1);

   Set values();

   BiMap inverse();
}
