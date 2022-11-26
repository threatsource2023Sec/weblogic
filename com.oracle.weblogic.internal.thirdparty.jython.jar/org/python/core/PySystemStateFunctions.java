package org.python.core;

@Untraversable
class PySystemStateFunctions extends PyBuiltinFunctionSet {
   PySystemStateFunctions(String name, int index, int minargs, int maxargs) {
      super(name, index, minargs, maxargs);
   }

   public PyObject __call__(PyObject arg) {
      switch (this.index) {
         case 10:
            PySystemState.displayhook(arg);
            return Py.None;
         default:
            throw this.info.unexpectedCall(1, false);
      }
   }

   public PyObject __call__(PyObject arg1, PyObject arg2, PyObject arg3) {
      switch (this.index) {
         case 30:
            PySystemState.excepthook(arg1, arg2, arg3);
            return Py.None;
         default:
            throw this.info.unexpectedCall(3, false);
      }
   }
}
