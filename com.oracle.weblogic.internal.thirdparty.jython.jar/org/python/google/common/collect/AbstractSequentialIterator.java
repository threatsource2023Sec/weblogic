package org.python.google.common.collect;

import java.util.NoSuchElementException;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;

@GwtCompatible
public abstract class AbstractSequentialIterator extends UnmodifiableIterator {
   private Object nextOrNull;

   protected AbstractSequentialIterator(@Nullable Object firstOrNull) {
      this.nextOrNull = firstOrNull;
   }

   protected abstract Object computeNext(Object var1);

   public final boolean hasNext() {
      return this.nextOrNull != null;
   }

   public final Object next() {
      if (!this.hasNext()) {
         throw new NoSuchElementException();
      } else {
         Object var1;
         try {
            var1 = this.nextOrNull;
         } finally {
            this.nextOrNull = this.computeNext(this.nextOrNull);
         }

         return var1;
      }
   }
}
