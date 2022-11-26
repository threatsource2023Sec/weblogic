package weblogic.application.archive.navigator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class IteratorIterable implements Iterable {
   private Iterator iterator;
   private Transformer transformer;

   public IteratorIterable(Iterator it, Transformer transformer) {
      this.iterator = it;
      this.transformer = transformer;
   }

   public Iterator iterator() {
      return new Iterator() {
         private Object next;

         public boolean hasNext() {
            this.next = null;

            do {
               if (!IteratorIterable.this.iterator.hasNext()) {
                  return false;
               }

               this.next = IteratorIterable.this.transformer == null ? IteratorIterable.this.iterator.next() : IteratorIterable.this.transformer.transform(IteratorIterable.this.iterator.next());
            } while(this.next == null);

            return true;
         }

         public Object next() {
            if (this.next == null) {
               throw new NoSuchElementException("no more elements");
            } else {
               return this.next;
            }
         }

         public void remove() {
            throw new UnsupportedOperationException();
         }
      };
   }

   public interface Predicate {
      boolean accept(Object var1);
   }

   public static class FilteredIterator extends AbstractIterator {
      private Predicate p;
      private Iterator it;

      public FilteredIterator(Iterator iterator, Predicate predicate) {
         this.p = predicate;
         this.it = iterator;
      }

      public boolean hasNext() {
         while(true) {
            if (this.it.hasNext()) {
               Object item = this.it.next();
               if (!this.p.accept(item)) {
                  continue;
               }

               this.next = item;
               return true;
            }

            return false;
         }
      }
   }

   public abstract static class AbstractIterator implements Iterator {
      protected Object next;

      public abstract boolean hasNext();

      public Object next() {
         if (this.next == null) {
            throw new NoSuchElementException();
         } else {
            return this.next;
         }
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }
   }

   public interface Transformer {
      Object transform(Object var1);
   }
}
