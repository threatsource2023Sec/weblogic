package org.python.core;

public abstract class PyDescriptor extends PyObject implements Traverseproc {
   protected PyType dtype;
   protected String name;

   protected void checkCallerType(PyType type) {
      if (type != this.dtype && !type.isSubType(this.dtype)) {
         String msg = String.format("descriptor '%s' requires a '%s' object but received a '%s'", this.name, this.dtype.fastGetName(), type.fastGetName());
         throw Py.TypeError(msg);
      }
   }

   protected void checkGetterType(PyType type) {
      if (type != this.dtype && !type.isSubType(this.dtype)) {
         String msg = String.format("descriptor '%s' for '%s' objects doesn't apply to '%s' object", this.name, this.dtype.fastGetName(), type.fastGetName());
         throw Py.TypeError(msg);
      }
   }

   public int traverse(Visitproc visit, Object arg) {
      return this.dtype != null ? visit.visit(this.dtype, arg) : 0;
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob == this.dtype;
   }
}
