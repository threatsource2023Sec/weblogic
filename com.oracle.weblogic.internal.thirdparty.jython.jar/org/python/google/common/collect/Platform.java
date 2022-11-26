package org.python.google.common.collect;

import java.lang.reflect.Array;
import org.python.google.common.annotations.GwtCompatible;

@GwtCompatible(
   emulated = true
)
final class Platform {
   static Object[] newArray(Object[] reference, int length) {
      Class type = reference.getClass().getComponentType();
      Object[] result = (Object[])((Object[])Array.newInstance(type, length));
      return result;
   }

   static MapMaker tryWeakKeys(MapMaker mapMaker) {
      return mapMaker.weakKeys();
   }

   private Platform() {
   }
}
