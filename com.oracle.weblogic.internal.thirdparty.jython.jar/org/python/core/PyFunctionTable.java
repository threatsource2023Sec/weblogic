package org.python.core;

import java.io.Serializable;

public abstract class PyFunctionTable implements Serializable {
   public abstract PyObject call_function(int var1, PyFrame var2, ThreadState var3);
}
