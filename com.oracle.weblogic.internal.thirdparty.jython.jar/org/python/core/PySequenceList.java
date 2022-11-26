package org.python.core;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public abstract class PySequenceList extends PySequence implements List, Traverseproc {
   protected PySequenceList(PyType type) {
      super(type);
   }

   public abstract void add(int var1, Object var2);

   public abstract boolean add(Object var1);

   public abstract boolean addAll(int var1, Collection var2);

   public abstract boolean addAll(Collection var1);

   public abstract void clear();

   public abstract boolean contains(Object var1);

   public abstract boolean containsAll(Collection var1);

   public abstract boolean equals(Object var1);

   public abstract Object get(int var1);

   public abstract PyObject[] getArray();

   public abstract int hashCode();

   public abstract int indexOf(Object var1);

   public abstract boolean isEmpty();

   public abstract Iterator iterator();

   public abstract int lastIndexOf(Object var1);

   public abstract ListIterator listIterator();

   public abstract ListIterator listIterator(int var1);

   public abstract void pyadd(int var1, PyObject var2);

   public abstract boolean pyadd(PyObject var1);

   public abstract PyObject pyget(int var1);

   public abstract void pyset(int var1, PyObject var2);

   public abstract Object remove(int var1);

   public abstract void remove(int var1, int var2);

   public abstract boolean remove(Object var1);

   public abstract boolean removeAll(Collection var1);

   public abstract boolean retainAll(Collection var1);

   public abstract Object set(int var1, Object var2);

   public abstract int size();

   public abstract List subList(int var1, int var2);

   public abstract Object[] toArray();

   public abstract Object[] toArray(Object[] var1);

   public abstract String toString();

   public int traverse(Visitproc visit, Object arg) {
      for(int i = 0; i < this.size(); ++i) {
         int retVal = visit.visit(this.pyget(i), arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      return 0;
   }

   public boolean refersDirectlyTo(PyObject ob) throws UnsupportedOperationException {
      throw new UnsupportedOperationException();
   }
}
