package org.python.core;

public interface PyProxy {
   void _setPyInstance(PyObject var1);

   PyObject _getPyInstance();

   void _setPySystemState(PySystemState var1);

   PySystemState _getPySystemState();

   void __initProxy__(Object[] var1);
}
