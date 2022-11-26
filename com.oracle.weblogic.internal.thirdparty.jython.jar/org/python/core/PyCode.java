package org.python.core;

public abstract class PyCode extends PyObject {
   public String co_name;

   public abstract PyObject call(ThreadState var1, PyFrame var2, PyObject var3);

   public PyObject call(PyFrame frame) {
      return this.call(Py.getThreadState(), frame);
   }

   public PyObject call(ThreadState state, PyFrame frame) {
      return this.call(state, frame, (PyObject)null);
   }

   public abstract PyObject call(ThreadState var1, PyObject[] var2, String[] var3, PyObject var4, PyObject[] var5, PyObject var6);

   public abstract PyObject call(ThreadState var1, PyObject var2, PyObject[] var3, String[] var4, PyObject var5, PyObject[] var6, PyObject var7);

   public abstract PyObject call(ThreadState var1, PyObject var2, PyObject[] var3, PyObject var4);

   public abstract PyObject call(ThreadState var1, PyObject var2, PyObject var3, PyObject[] var4, PyObject var5);

   public abstract PyObject call(ThreadState var1, PyObject var2, PyObject var3, PyObject var4, PyObject[] var5, PyObject var6);

   public abstract PyObject call(ThreadState var1, PyObject var2, PyObject var3, PyObject var4, PyObject var5, PyObject[] var6, PyObject var7);

   public abstract PyObject call(ThreadState var1, PyObject var2, PyObject var3, PyObject var4, PyObject var5, PyObject var6, PyObject[] var7, PyObject var8);
}
