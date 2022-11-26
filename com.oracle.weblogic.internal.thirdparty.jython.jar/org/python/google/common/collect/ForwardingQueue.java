package org.python.google.common.collect;

import java.util.NoSuchElementException;
import java.util.Queue;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtCompatible
public abstract class ForwardingQueue extends ForwardingCollection implements Queue {
   protected ForwardingQueue() {
   }

   protected abstract Queue delegate();

   @CanIgnoreReturnValue
   public boolean offer(Object o) {
      return this.delegate().offer(o);
   }

   @CanIgnoreReturnValue
   public Object poll() {
      return this.delegate().poll();
   }

   @CanIgnoreReturnValue
   public Object remove() {
      return this.delegate().remove();
   }

   public Object peek() {
      return this.delegate().peek();
   }

   public Object element() {
      return this.delegate().element();
   }

   protected boolean standardOffer(Object e) {
      try {
         return this.add(e);
      } catch (IllegalStateException var3) {
         return false;
      }
   }

   protected Object standardPeek() {
      try {
         return this.element();
      } catch (NoSuchElementException var2) {
         return null;
      }
   }

   protected Object standardPoll() {
      try {
         return this.remove();
      } catch (NoSuchElementException var2) {
         return null;
      }
   }
}
