package org.python.core;

public class PyBuiltinMethodSet extends PyBuiltinFunctionSet implements Cloneable, Traverseproc {
   private Class type;
   protected PyObject __self__;

   public PyBuiltinMethodSet(String name, int index, int minargs, int maxargs, String doc, Class type) {
      super(name, index, minargs, maxargs, doc);
      this.__self__ = Py.None;
      this.type = type;
   }

   public PyObject __get__(PyObject obj, PyObject type) {
      if (obj != null) {
         if (this.type.isAssignableFrom(obj.getClass())) {
            return this.bind(obj);
         } else {
            throw Py.TypeError(String.format("descriptor '%s' for '%s' objects doesn't apply to '%s' object", this.info.getName(), PyType.fromClass(this.type), obj.getType()));
         }
      } else {
         return this;
      }
   }

   public PyBuiltinCallable bind(PyObject bindTo) {
      if (this.__self__ != Py.None) {
         return this;
      } else {
         PyBuiltinMethodSet bindable;
         try {
            bindable = (PyBuiltinMethodSet)this.clone();
         } catch (CloneNotSupportedException var4) {
            throw new RuntimeException("Didn't expect PyBuiltinMethodSet to throw CloneNotSupported since it implements Cloneable", var4);
         }

         bindable.__self__ = bindTo;
         return bindable;
      }
   }

   public PyObject getSelf() {
      return this.__self__;
   }

   public boolean implementsDescrGet() {
      return true;
   }

   public String toString() {
      return String.format("<built-in method %s>", this.info.getName());
   }

   public int traverse(Visitproc visit, Object arg) {
      return visit.visit(this.__self__, arg);
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob == this.__self__;
   }
}
