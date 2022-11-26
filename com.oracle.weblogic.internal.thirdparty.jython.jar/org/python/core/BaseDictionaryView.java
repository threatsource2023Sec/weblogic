package org.python.core;

import java.util.Iterator;

public abstract class BaseDictionaryView extends PyObject implements Traverseproc {
   protected final AbstractDict dvDict;

   public BaseDictionaryView(AbstractDict dvDict) {
      this.dvDict = dvDict;
   }

   final boolean allContainedIn(PyObject self, PyObject other) {
      Iterator var3 = self.asIterable().iterator();

      PyObject ob_value;
      do {
         if (!var3.hasNext()) {
            return true;
         }

         ob_value = (PyObject)var3.next();
      } while(other.__contains__(ob_value));

      return false;
   }

   static final boolean isSetDictViewInstance(PyObject otherObj) {
      return otherObj instanceof BaseSet || otherObj instanceof BaseDictionaryView;
   }

   public int __len__() {
      return this.dict_view___len__();
   }

   final int dict_view___len__() {
      return this.dvDict.getMap().size();
   }

   public PyObject __eq__(PyObject otherObj) {
      return this.dict_view___eq__(otherObj);
   }

   final PyObject dict_view___eq__(PyObject otherObj) {
      if (!isSetDictViewInstance(otherObj)) {
         return Py.False;
      } else if (this.__len__() != otherObj.__len__()) {
         return Py.False;
      } else {
         return !this.allContainedIn(this, otherObj) ? Py.False : Py.True;
      }
   }

   public PyObject __ne__(PyObject otherObj) {
      return this.dict_view___ne__(otherObj);
   }

   final PyObject dict_view___ne__(PyObject otherObj) {
      return this.dict_view___eq__(otherObj) == Py.True ? Py.False : Py.True;
   }

   public PyObject __lt__(PyObject otherObj) {
      return this.dict_view___lt__(otherObj);
   }

   final PyObject dict_view___lt__(PyObject otherObj) {
      if (!isSetDictViewInstance(otherObj)) {
         return Py.False;
      } else {
         return this.__len__() < otherObj.__len__() && this.allContainedIn(this, otherObj) ? Py.False : Py.True;
      }
   }

   public PyObject __le__(PyObject otherObj) {
      return this.dict_view___le__(otherObj);
   }

   final PyObject dict_view___le__(PyObject otherObj) {
      if (!isSetDictViewInstance(otherObj)) {
         return Py.False;
      } else {
         return this.__len__() <= otherObj.__len__() && this.allContainedIn(this, otherObj) ? Py.False : Py.True;
      }
   }

   public PyObject __gt__(PyObject otherObj) {
      return this.dict_view___gt__(otherObj);
   }

   final PyObject dict_view___gt__(PyObject otherObj) {
      if (!isSetDictViewInstance(otherObj)) {
         return Py.False;
      } else {
         return this.__len__() > otherObj.__len__() && this.allContainedIn(otherObj, this) ? Py.False : Py.True;
      }
   }

   public PyObject __ge__(PyObject otherObj) {
      return this.dict_view___ge__(otherObj);
   }

   final PyObject dict_view___ge__(PyObject otherObj) {
      if (!isSetDictViewInstance(otherObj)) {
         return Py.False;
      } else {
         return this.__len__() >= otherObj.__len__() && this.allContainedIn(otherObj, this) ? Py.False : Py.True;
      }
   }

   public String toString() {
      return this.dict_view_toString();
   }

   final String dict_view_toString() {
      String name = this.getType().fastGetName();
      ThreadState ts = Py.getThreadState();
      if (!ts.enterRepr(this)) {
         return name + "([])";
      } else {
         StringBuilder buf = (new StringBuilder(name)).append("([");
         Iterator i = this.asIterable().iterator();

         while(i.hasNext()) {
            buf.append(((PyObject)i.next()).__repr__().toString());
            if (i.hasNext()) {
               buf.append(", ");
            }
         }

         buf.append("])");
         ts.exitRepr(this);
         return buf.toString();
      }
   }

   public int traverse(Visitproc visit, Object arg) {
      return this.dvDict != null ? visit.visit(this.dvDict, arg) : 0;
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob != null && this.dvDict == ob;
   }
}
