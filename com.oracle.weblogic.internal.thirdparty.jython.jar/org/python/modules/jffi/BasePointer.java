package org.python.modules.jffi;

import org.python.core.Py;
import org.python.core.PyObject;
import org.python.core.PyType;
import org.python.expose.ExposedGet;

public abstract class BasePointer extends PyObject implements Pointer {
   BasePointer(PyType subtype) {
      super(subtype);
   }

   @ExposedGet(
      name = "address"
   )
   public PyObject address() {
      return Py.newInteger(this.getMemory().getAddress());
   }

   public boolean __nonzero__() {
      return !this.getMemory().isNull();
   }

   public PyObject __int__() {
      return this.address();
   }

   public PyObject __long__() {
      return this.address();
   }
}
