package org.python.google.common.collect;

import java.util.Iterator;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtCompatible
public abstract class ForwardingIterator extends ForwardingObject implements Iterator {
   protected ForwardingIterator() {
   }

   protected abstract Iterator delegate();

   public boolean hasNext() {
      return this.delegate().hasNext();
   }

   @CanIgnoreReturnValue
   public Object next() {
      return this.delegate().next();
   }

   public void remove() {
      this.delegate().remove();
   }
}
