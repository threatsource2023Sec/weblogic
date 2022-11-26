package org.python.core;

public abstract class PyBuiltinMethodNarrow extends PyBuiltinMethod {
   protected PyBuiltinMethodNarrow(String name) {
      this(name, 0);
   }

   protected PyBuiltinMethodNarrow(String name, int numArgs) {
      this(name, numArgs, numArgs);
   }

   protected PyBuiltinMethodNarrow(String name, int minArgs, int maxArgs) {
      super((PyObject)null, new PyBuiltinCallable.DefaultInfo(name, minArgs, maxArgs));
   }

   protected PyBuiltinMethodNarrow(PyObject self, PyBuiltinCallable.Info info) {
      super(self, info);
   }

   protected PyBuiltinMethodNarrow(PyType type, PyObject self, PyBuiltinCallable.Info info) {
      super(type, self, info);
   }

   public PyObject __call__(PyObject[] args, String[] keywords) {
      if (keywords.length != 0) {
         throw this.info.unexpectedCall(args.length, true);
      } else {
         return this.__call__(args);
      }
   }

   public PyObject __call__(PyObject[] args) {
      switch (args.length) {
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
            throw this.info.unexpectedCall(args.length, false);
      }
   }

   public PyObject __call__() {
      throw this.info.unexpectedCall(0, false);
   }

   public PyObject __call__(PyObject arg0) {
      throw this.info.unexpectedCall(1, false);
   }

   public PyObject __call__(PyObject arg0, PyObject arg1) {
      throw this.info.unexpectedCall(2, false);
   }

   public PyObject __call__(PyObject arg0, PyObject arg1, PyObject arg2) {
      throw this.info.unexpectedCall(3, false);
   }

   public PyObject __call__(PyObject arg0, PyObject arg1, PyObject arg2, PyObject arg3) {
      throw this.info.unexpectedCall(4, false);
   }
}
