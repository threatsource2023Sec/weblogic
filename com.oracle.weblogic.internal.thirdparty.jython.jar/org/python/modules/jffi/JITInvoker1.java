package org.python.modules.jffi;

import org.python.core.PyObject;

public abstract class JITInvoker1 extends JITInvoker {
   public JITInvoker1(com.kenai.jffi.Function function, Invoker fallbackInvoker) {
      super(1, function, fallbackInvoker);
   }

   public final PyObject invoke() {
      return this.invalidArity(0);
   }

   public final PyObject invoke(PyObject arg1, PyObject arg2) {
      return this.invalidArity(2);
   }

   public final PyObject invoke(PyObject arg1, PyObject arg2, PyObject arg3) {
      return this.invalidArity(3);
   }

   public final PyObject invoke(PyObject arg1, PyObject arg2, PyObject arg3, PyObject arg4) {
      return this.invalidArity(4);
   }

   public final PyObject invoke(PyObject arg1, PyObject arg2, PyObject arg3, PyObject arg4, PyObject arg5) {
      return this.invalidArity(5);
   }

   public final PyObject invoke(PyObject arg1, PyObject arg2, PyObject arg3, PyObject arg4, PyObject arg5, PyObject arg6) {
      return this.invalidArity(6);
   }
}
