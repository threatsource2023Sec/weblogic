package org.python.google.common.util.concurrent;

import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;

@GwtCompatible(
   emulated = true
)
final class Platform {
   static boolean isInstanceOfThrowableClass(@Nullable Throwable t, Class expectedClass) {
      return expectedClass.isInstance(t);
   }

   private Platform() {
   }
}
