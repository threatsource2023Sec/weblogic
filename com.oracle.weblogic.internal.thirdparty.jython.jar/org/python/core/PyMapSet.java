package org.python.core;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;

abstract class PyMapSet extends AbstractSet {
   private final Collection coll;

   PyMapSet(Collection coll) {
      this.coll = coll;
   }

   abstract Object toJava(Object var1);

   abstract Object toPython(Object var1);

   public int size() {
      return this.coll.size();
   }

   public boolean contains(Object o) {
      return this.coll.contains(this.toPython(o));
   }

   public boolean remove(Object o) {
      return this.coll.remove(this.toPython(o));
   }

   public void clear() {
      this.coll.clear();
   }

   public Iterator iterator() {
      return new PySetIter(this.coll.iterator());
   }

   class PySetIter implements Iterator {
      Iterator itr;

      PySetIter(Iterator itr) {
         this.itr = itr;
      }

      public boolean hasNext() {
         return this.itr.hasNext();
      }

      public Object next() {
         return PyMapSet.this.toJava(this.itr.next());
      }

      public void remove() {
         this.itr.remove();
      }
   }
}
