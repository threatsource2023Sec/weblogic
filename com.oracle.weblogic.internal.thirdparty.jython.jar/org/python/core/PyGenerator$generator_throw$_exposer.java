package org.python.core;

public class PyGenerator$generator_throw$_exposer extends PyBuiltinMethodNarrow {
   public PyGenerator$generator_throw$_exposer(String var1) {
      super(var1, 2, 4);
      super.doc = "";
   }

   public PyGenerator$generator_throw$_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
      super(var1, var2, var3);
      super.doc = "";
   }

   public PyBuiltinCallable bind(PyObject var1) {
      return new PyGenerator$generator_throw$_exposer(this.getType(), var1, this.info);
   }

   public PyObject __call__(PyObject var1, PyObject var2, PyObject var3) {
      return ((PyGenerator)this.self).generator_throw$(var1, var2, var3);
   }

   public PyObject __call__(PyObject var1, PyObject var2) {
      return ((PyGenerator)this.self).generator_throw$(var1, var2, (PyObject)null);
   }

   public PyObject __call__(PyObject var1) {
      return ((PyGenerator)this.self).generator_throw$(var1, (PyObject)null, (PyObject)null);
   }
}
