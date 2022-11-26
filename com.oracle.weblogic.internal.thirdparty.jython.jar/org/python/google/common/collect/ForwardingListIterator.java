package org.python.google.common.collect;

import java.util.ListIterator;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtCompatible
public abstract class ForwardingListIterator extends ForwardingIterator implements ListIterator {
   protected ForwardingListIterator() {
   }

   protected abstract ListIterator delegate();

   public void add(Object element) {
      this.delegate().add(element);
   }

   public boolean hasPrevious() {
      return this.delegate().hasPrevious();
   }

   public int nextIndex() {
      return this.delegate().nextIndex();
   }

   @CanIgnoreReturnValue
   public Object previous() {
      return this.delegate().previous();
   }

   public int previousIndex() {
      return this.delegate().previousIndex();
   }

   public void set(Object element) {
      this.delegate().set(element);
   }
}
