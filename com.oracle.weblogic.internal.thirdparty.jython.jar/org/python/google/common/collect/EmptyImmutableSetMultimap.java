package org.python.google.common.collect;

import java.util.Comparator;
import org.python.google.common.annotations.GwtCompatible;

@GwtCompatible(
   serializable = true
)
class EmptyImmutableSetMultimap extends ImmutableSetMultimap {
   static final EmptyImmutableSetMultimap INSTANCE = new EmptyImmutableSetMultimap();
   private static final long serialVersionUID = 0L;

   private EmptyImmutableSetMultimap() {
      super(ImmutableMap.of(), 0, (Comparator)null);
   }

   private Object readResolve() {
      return INSTANCE;
   }
}
