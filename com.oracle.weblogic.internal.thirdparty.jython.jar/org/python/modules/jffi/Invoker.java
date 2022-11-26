package org.python.modules.jffi;

import org.python.core.PyObject;

public abstract class Invoker {
   public abstract PyObject invoke(PyObject[] var1);

   public abstract PyObject invoke();

   public abstract PyObject invoke(PyObject var1);

   public abstract PyObject invoke(PyObject var1, PyObject var2);

   public abstract PyObject invoke(PyObject var1, PyObject var2, PyObject var3);

   public abstract PyObject invoke(PyObject var1, PyObject var2, PyObject var3, PyObject var4);

   public abstract PyObject invoke(PyObject var1, PyObject var2, PyObject var3, PyObject var4, PyObject var5);

   public abstract PyObject invoke(PyObject var1, PyObject var2, PyObject var3, PyObject var4, PyObject var5, PyObject var6);
}
