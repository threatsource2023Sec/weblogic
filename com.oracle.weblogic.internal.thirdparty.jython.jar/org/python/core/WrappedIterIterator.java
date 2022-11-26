package org.python.core;

import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class WrappedIterIterator implements Iterator {
   private final PyObject iter;
   private PyObject next;
   private boolean checkedForNext;

   public WrappedIterIterator(PyObject iter) {
      this.iter = iter;
   }

   public boolean hasNext() {
      if (!this.checkedForNext) {
         this.next = this.iter.__iternext__();
         this.checkedForNext = true;
      }

      return this.next != null;
   }

   public abstract Object next();

   public PyObject getNext() {
      if (!this.hasNext()) {
         throw new NoSuchElementException("End of the line, bub");
      } else {
         PyObject toReturn = this.next;
         this.checkedForNext = false;
         this.next = null;
         return toReturn;
      }
   }

   public void remove() {
      throw new UnsupportedOperationException("Can't remove from a Python iterator");
   }
}
