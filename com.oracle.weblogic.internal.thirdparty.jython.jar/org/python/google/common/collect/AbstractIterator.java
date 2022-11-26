package org.python.google.common.collect;

import java.util.NoSuchElementException;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Preconditions;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtCompatible
public abstract class AbstractIterator extends UnmodifiableIterator {
   private State state;
   private Object next;

   protected AbstractIterator() {
      this.state = AbstractIterator.State.NOT_READY;
   }

   protected abstract Object computeNext();

   @CanIgnoreReturnValue
   protected final Object endOfData() {
      this.state = AbstractIterator.State.DONE;
      return null;
   }

   @CanIgnoreReturnValue
   public final boolean hasNext() {
      Preconditions.checkState(this.state != AbstractIterator.State.FAILED);
      switch (this.state) {
         case DONE:
            return false;
         case READY:
            return true;
         default:
            return this.tryToComputeNext();
      }
   }

   private boolean tryToComputeNext() {
      this.state = AbstractIterator.State.FAILED;
      this.next = this.computeNext();
      if (this.state != AbstractIterator.State.DONE) {
         this.state = AbstractIterator.State.READY;
         return true;
      } else {
         return false;
      }
   }

   @CanIgnoreReturnValue
   public final Object next() {
      if (!this.hasNext()) {
         throw new NoSuchElementException();
      } else {
         this.state = AbstractIterator.State.NOT_READY;
         Object result = this.next;
         this.next = null;
         return result;
      }
   }

   public final Object peek() {
      if (!this.hasNext()) {
         throw new NoSuchElementException();
      } else {
         return this.next;
      }
   }

   private static enum State {
      READY,
      NOT_READY,
      DONE,
      FAILED;
   }
}
