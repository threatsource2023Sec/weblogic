package org.python.modules.jffi;

import org.python.core.Py;
import org.python.core.PyObject;

public abstract class JITInvoker extends Invoker {
   protected static final com.kenai.jffi.Invoker jffiInvoker = com.kenai.jffi.Invoker.getInstance();
   protected final com.kenai.jffi.Function jffiFunction;
   protected final Invoker fallbackInvoker;
   private final int arity;

   protected JITInvoker(int arity, com.kenai.jffi.Function function, Invoker fallbackInvoker) {
      this.arity = arity;
      this.jffiFunction = function;
      this.fallbackInvoker = fallbackInvoker;
   }

   protected final PyObject invalidArity(int got) {
      checkArity(this.arity, got);
      return Py.None;
   }

   protected final void checkArity(PyObject[] args) {
      checkArity(this.arity, args.length);
   }

   public static void checkArity(int arity, int got) {
      if (got != arity) {
         throw Py.TypeError(String.format("__call__() takes exactly %d arguments (%d given)", arity, got));
      }
   }

   public PyObject invoke(PyObject[] args) {
      this.checkArity(args);
      switch (this.arity) {
         case 0:
            return this.invoke();
         case 1:
            return this.invoke(args[0]);
         case 2:
            return this.invoke(args[0], args[1]);
         case 3:
            return this.invoke(args[0], args[1], args[2]);
         case 4:
            return this.invoke(args[0], args[1], args[2], args[3]);
         case 5:
            return this.invoke(args[0], args[1], args[2], args[3], args[4]);
         case 6:
            return this.invoke(args[0], args[1], args[2], args[3], args[4], args[5]);
         default:
            throw Py.RuntimeError("invalid fast-int arity");
      }
   }
}
