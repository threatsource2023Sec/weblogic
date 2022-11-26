package org.python.core;

public abstract class TraceFunction {
   public abstract TraceFunction traceCall(PyFrame var1);

   public abstract TraceFunction traceReturn(PyFrame var1, PyObject var2);

   public abstract TraceFunction traceLine(PyFrame var1, int var2);

   public abstract TraceFunction traceException(PyFrame var1, PyException var2);
}
