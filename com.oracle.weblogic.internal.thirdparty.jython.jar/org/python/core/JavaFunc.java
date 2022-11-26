package org.python.core;

import java.lang.reflect.Method;

@Untraversable
class JavaFunc extends PyObject {
   Method method;

   public JavaFunc(Method method) {
      this.method = method;
   }

   public PyObject __call__(PyObject[] args, String[] kws) {
      Object[] margs = new Object[]{args, kws};

      try {
         return Py.java2py(this.method.invoke((Object)null, margs));
      } catch (Throwable var5) {
         throw Py.JavaError(var5);
      }
   }

   public PyObject _doget(PyObject container) {
      return this._doget(container, (PyObject)null);
   }

   public PyObject _doget(PyObject container, PyObject wherefound) {
      return (PyObject)(container == null ? this : new PyMethod(this, container, wherefound));
   }

   public boolean _doset(PyObject container) {
      throw Py.TypeError("java function not settable: " + this.method.getName());
   }
}
