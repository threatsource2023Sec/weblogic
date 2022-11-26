package org.python.google.common.base;

import java.util.Arrays;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;

@GwtCompatible
public final class Objects extends ExtraObjectsMethodsForWeb {
   private Objects() {
   }

   public static boolean equal(@Nullable Object a, @Nullable Object b) {
      return a == b || a != null && a.equals(b);
   }

   public static int hashCode(@Nullable Object... objects) {
      return Arrays.hashCode(objects);
   }
}
