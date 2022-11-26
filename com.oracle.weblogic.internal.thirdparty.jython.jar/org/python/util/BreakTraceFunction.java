package org.python.util;

import org.python.core.PyException;
import org.python.core.PyFrame;
import org.python.core.PyObject;
import org.python.core.TraceFunction;

class BreakTraceFunction extends TraceFunction {
   private void doBreak() {
      throw new Error("Python interrupt");
   }

   public TraceFunction traceCall(PyFrame frame) {
      this.doBreak();
      return null;
   }

   public TraceFunction traceReturn(PyFrame frame, PyObject ret) {
      this.doBreak();
      return null;
   }

   public TraceFunction traceLine(PyFrame frame, int line) {
      this.doBreak();
      return null;
   }

   public TraceFunction traceException(PyFrame frame, PyException exc) {
      this.doBreak();
      return null;
   }
}
