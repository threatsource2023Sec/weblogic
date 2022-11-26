package org.python.google.common.collect;

import java.util.ListIterator;
import org.python.google.common.annotations.GwtCompatible;

@GwtCompatible
public abstract class UnmodifiableListIterator extends UnmodifiableIterator implements ListIterator {
   protected UnmodifiableListIterator() {
   }

   /** @deprecated */
   @Deprecated
   public final void add(Object e) {
      throw new UnsupportedOperationException();
   }

   /** @deprecated */
   @Deprecated
   public final void set(Object e) {
      throw new UnsupportedOperationException();
   }
}
