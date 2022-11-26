package org.python.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public abstract class PyIterator extends PyObject implements Iterable, Traverseproc {
   protected PyException stopException;
   public static PyString __doc__next = new PyString("x.next() -> the next value, or raise StopIteration");

   public PyIterator() {
   }

   public PyIterator(PyType subType) {
      super(subType);
   }

   public abstract PyObject __iternext__();

   public PyObject __iter__() {
      return this;
   }

   public PyObject next() {
      return this.doNext(this.__iternext__());
   }

   protected final PyObject doNext(PyObject ret) {
      if (ret == null) {
         if (this.stopException != null) {
            PyException toThrow = this.stopException;
            this.stopException = null;
            throw toThrow;
         } else {
            throw Py.StopIteration("");
         }
      } else {
         return ret;
      }
   }

   public Iterator iterator() {
      return new WrappedIterIterator(this) {
         public Object next() {
            return this.getNext().__tojava__(Object.class);
         }
      };
   }

   public Object __tojava__(Class c) {
      if (c.isAssignableFrom(Iterable.class)) {
         return this;
      } else if (c.isAssignableFrom(Iterator.class)) {
         return this.iterator();
      } else if (!c.isAssignableFrom(Collection.class)) {
         if (c.isArray()) {
            PyArray array = new PyArray(c.getComponentType(), this);
            return array.__tojava__(c);
         } else {
            return super.__tojava__(c);
         }
      } else {
         List list = new ArrayList();
         Iterator var3 = this.iterator();

         while(var3.hasNext()) {
            Object obj = var3.next();
            list.add(obj);
         }

         return list;
      }
   }

   public int traverse(Visitproc visit, Object arg) {
      return this.stopException != null ? this.stopException.traverse(visit, arg) : 0;
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob != null && this.stopException != null && this.stopException.refersDirectlyTo(ob);
   }
}
