package org.python.core;

public interface ContextManager {
   PyObject __enter__(ThreadState var1);

   boolean __exit__(ThreadState var1, PyException var2);
}
