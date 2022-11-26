package org.python.core;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

public abstract class AbstractDict extends PyObject {
   public AbstractDict(PyType type) {
      super(type);
   }

   public abstract void clear();

   public abstract AbstractDict copy();

   public abstract PyObject get(PyObject var1);

   public abstract PyObject get(PyObject var1, PyObject var2);

   public abstract ConcurrentMap getMap();

   public abstract boolean has_key(PyObject var1);

   public abstract PyList items();

   public abstract PyObject iteritems();

   public abstract PyObject iterkeys();

   public abstract PyObject itervalues();

   public abstract PyList keys();

   public abstract void merge(PyObject var1, boolean var2);

   public abstract void mergeFromKeys(PyObject var1, PyObject var2, boolean var3);

   public abstract void mergeFromSeq(PyObject var1, boolean var2);

   public abstract PyObject pop(PyObject var1);

   public abstract PyObject pop(PyObject var1, PyObject var2);

   public abstract PyObject popitem();

   public abstract PyObject setdefault(PyObject var1);

   public abstract PyObject setdefault(PyObject var1, PyObject var2);

   public abstract void update(PyObject var1);

   public abstract Collection values();

   public abstract Set pyKeySet();

   public abstract Set entrySet();

   public PyObject viewkeys() {
      return new PyDictionary.PyDictionaryViewKeys(this);
   }

   public PyObject viewitems() {
      return new PyDictionary.PyDictionaryViewItems(this);
   }

   public PyObject viewvalues() {
      return new PyDictionary.PyDictionaryViewValues(this);
   }

   static Object tojava(Object val) {
      return val == null ? null : ((PyObject)val).__tojava__(Object.class);
   }

   static class ValuesIter extends PyIterator {
      private final Iterator iterator;
      private final int size;

      public ValuesIter(Collection values) {
         this.iterator = values.iterator();
         this.size = values.size();
      }

      public PyObject __iternext__() {
         return !this.iterator.hasNext() ? null : (PyObject)this.iterator.next();
      }
   }
}
