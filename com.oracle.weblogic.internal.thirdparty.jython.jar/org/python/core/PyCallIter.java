package org.python.core;

public class PyCallIter extends PyIterator {
   private PyObject callable;
   private PyObject sentinel;

   public PyCallIter(PyObject callable, PyObject sentinel) {
      if (!callable.isCallable()) {
         throw Py.TypeError("iter(v, w): v must be callable");
      } else {
         this.callable = callable;
         this.sentinel = sentinel;
      }
   }

   public PyObject __iternext__() {
      if (this.callable == null) {
         return null;
      } else {
         PyObject result;
         try {
            result = this.callable.__call__();
         } catch (PyException var3) {
            if (var3.match(Py.StopIteration)) {
               this.callable = null;
               this.stopException = var3;
               return null;
            }

            throw var3;
         }

         if (result._eq(this.sentinel).__nonzero__()) {
            this.callable = null;
            return null;
         } else {
            return result;
         }
      }
   }

   public int traverse(Visitproc visit, Object arg) {
      int retValue = super.traverse(visit, arg);
      if (retValue != 0) {
         return retValue;
      } else {
         if (this.callable != null) {
            retValue = visit.visit(this.callable, arg);
            if (retValue != 0) {
               return retValue;
            }
         }

         return this.sentinel != null ? visit.visit(this.sentinel, arg) : 0;
      }
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob != null && (ob == this.callable || ob == this.sentinel || super.refersDirectlyTo(ob));
   }
}
