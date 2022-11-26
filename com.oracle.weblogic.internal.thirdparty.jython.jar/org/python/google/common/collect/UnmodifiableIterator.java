package org.python.google.common.collect;

import java.util.Iterator;
import org.python.google.common.annotations.GwtCompatible;

@GwtCompatible
public abstract class UnmodifiableIterator implements Iterator {
   protected UnmodifiableIterator() {
   }

   /** @deprecated */
   @Deprecated
   public final void remove() {
      throw new UnsupportedOperationException();
   }
}
