package net.shibboleth.utilities.java.support.primitive;

import javax.annotation.Nullable;

public final class ObjectSupport {
   private ObjectSupport() {
   }

   public static int hashCode(@Nullable Object o) {
      return o == null ? 0 : o.hashCode();
   }

   @Nullable
   public static Object firstNonNull(@Nullable Object... objects) {
      if (objects == null) {
         return null;
      } else {
         Object[] arr$ = objects;
         int len$ = objects.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            Object obj = arr$[i$];
            if (obj != null) {
               return obj;
            }
         }

         return null;
      }
   }
}
