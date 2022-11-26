package org.python.core;

public abstract class PyOverridableNew extends PyNewWrapper {
   public PyObject new_impl(boolean init, PyType subtype, PyObject[] args, String[] keywords) {
      return this.for_type == subtype ? this.createOfType(init, args, keywords) : this.createOfSubtype(subtype);
   }

   public abstract PyObject createOfType(boolean var1, PyObject[] var2, String[] var3);

   public abstract PyObject createOfSubtype(PyType var1);
}
