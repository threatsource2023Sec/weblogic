package org.python.core;

public class PyReversedIterator extends PyIterator {
   private PyObject seq;
   private int idx;

   public PyReversedIterator(PyObject seq) {
      this.seq = seq;
      this.idx = seq.__len__();
      if (this.idx > 0) {
         --this.idx;
      }

   }

   public PyObject __iternext__() {
      return this.idx >= 0 ? this.seq.__finditem__(this.idx--) : null;
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
