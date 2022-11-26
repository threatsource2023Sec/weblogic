package org.python.core;

public class PySequenceIter extends PyIterator {
   private PyObject seq;
   private int index = 0;

   public PySequenceIter(PyObject seq) {
      this.seq = seq;
   }

   public PyObject __iternext__() {
      if (this.seq == null) {
         return null;
      } else {
         PyObject result;
         try {
            result = this.seq.__finditem__(this.index++);
         } catch (PyException var3) {
            if (var3.match(Py.StopIteration)) {
               this.seq = null;
               return null;
            }

            throw var3;
         }

         if (result == null) {
            this.seq = null;
         }

         return result;
      }
   }

   public int traverse(Visitproc visit, Object arg) {
      int retVal = super.traverse(visit, arg);
      if (retVal != 0) {
         return retVal;
      } else {
         return this.seq == null ? 0 : visit.visit(this.seq, arg);
      }
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob != null && (ob == this.seq || super.refersDirectlyTo(ob));
   }
}
