package org.python.core;

@Untraversable
public class PyBuiltinFunctionNarrow extends PyBuiltinFunction {
   protected PyBuiltinFunctionNarrow(String name, int minargs, int maxargs, String doc) {
      super(name, minargs, maxargs, doc);
   }

   public PyObject fancyCall(PyObject[] args) {
      throw this.info.unexpectedCall(args.length, false);
   }

   public PyObject __call__(PyObject[] args) {
      int nargs = args.length;
      switch (nargs) {
         case 0:
            return this.__call__();
         case 1:
            return this.__call__(args[0]);
         case 2:
            return this.__call__(args[0], args[1]);
         case 3:
            return this.__call__(args[0], args[1], args[2]);
         case 4:
            return this.__call__(args[0], args[1], args[2], args[3]);
         default:
            return this.fancyCall(args);
      }
   }

   public PyObject __call__(PyObject[] args, String[] kws) {
      if (kws.length != 0) {
         throw Py.TypeError(this.fastGetName() + "() takes no keyword arguments");
      } else {
         return this.__call__(args);
      }
   }

   public PyObject __call__() {
      throw this.info.unexpectedCall(0, false);
   }

   public PyObject __call__(PyObject arg1) {
      throw this.info.unexpectedCall(1, false);
   }

   public PyObject __call__(PyObject arg1, PyObject arg2) {
      throw this.info.unexpectedCall(2, false);
   }

   public PyObject __call__(PyObject arg1, PyObject arg2, PyObject arg3) {
      throw this.info.unexpectedCall(3, false);
   }

   public PyObject __call__(PyObject arg1, PyObject arg2, PyObject arg3, PyObject arg4) {
      throw this.info.unexpectedCall(4, false);
   }
}
