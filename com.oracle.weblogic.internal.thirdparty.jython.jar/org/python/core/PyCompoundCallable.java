package org.python.core;

import java.util.Iterator;
import java.util.List;
import org.python.util.Generic;

public class PyCompoundCallable extends PyObject implements Traverseproc {
   private List callables = Generic.list();
   private PySystemState systemState = Py.getSystemState();

   public void append(PyObject callable) {
      this.callables.add(callable);
   }

   public void clear() {
      this.callables.clear();
   }

   public PyObject __call__(PyObject[] args, String[] keywords) {
      Py.setSystemState(this.systemState);
      Iterator var3 = this.callables.iterator();

      while(var3.hasNext()) {
         PyObject callable = (PyObject)var3.next();
         callable.__call__(args, keywords);
      }

      return Py.None;
   }

   public String toString() {
      return "<CompoundCallable with " + this.callables.size() + " callables>";
   }

   public int traverse(Visitproc visit, Object arg) {
      int retValue;
      if (this.systemState != null) {
         retValue = visit.visit(this.systemState, arg);
         if (retValue != 0) {
            return retValue;
         }
      }

      if (this.callables != null) {
         Iterator var4 = this.callables.iterator();

         while(var4.hasNext()) {
            PyObject ob = (PyObject)var4.next();
            retValue = visit.visit(ob, arg);
            if (retValue != 0) {
               return retValue;
            }
         }
      }

      return 0;
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob != null && (ob == this.systemState || this.callables.contains(ob));
   }
}
