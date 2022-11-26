package org.python.google.common.collect;

import org.python.google.common.annotations.GwtCompatible;

@GwtCompatible(
   serializable = true
)
class EmptyImmutableListMultimap extends ImmutableListMultimap {
   static final EmptyImmutableListMultimap INSTANCE = new EmptyImmutableListMultimap();
   private static final long serialVersionUID = 0L;

   private EmptyImmutableListMultimap() {
      super(ImmutableMap.of(), 0);
   }

   private Object readResolve() {
      return INSTANCE;
   }
}
