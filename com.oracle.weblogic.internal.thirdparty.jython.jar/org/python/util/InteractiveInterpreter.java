package org.python.util;

import org.python.core.CompileMode;
import org.python.core.Py;
import org.python.core.PyException;
import org.python.core.PyObject;
import org.python.core.PySystemState;
import org.python.core.ThreadState;
import org.python.core.TraceFunction;

public class InteractiveInterpreter extends PythonInterpreter {
   public StringBuilder buffer;
   public String filename;

   public InteractiveInterpreter() {
      this((PyObject)null);
   }

   public InteractiveInterpreter(PyObject locals) {
      this(locals, (PySystemState)null);
   }

   public InteractiveInterpreter(PyObject locals, PySystemState systemState) {
      super(locals, systemState);
      this.buffer = new StringBuilder();
      this.filename = "<stdin>";
   }

   public boolean runsource(String source) {
      return this.runsource(source, "<input>", CompileMode.single);
   }

   public boolean runsource(String source, String filename) {
      return this.runsource(source, filename, CompileMode.single);
   }

   public boolean runsource(String source, String filename, CompileMode kind) {
      PyObject code;
      try {
         code = Py.compile_command_flags(source, filename, kind, this.cflags, true);
      } catch (PyException var6) {
         if (var6.match(Py.SyntaxError)) {
            this.showexception(var6);
            return false;
         }

         if (!var6.match(Py.ValueError) && !var6.match(Py.OverflowError)) {
            throw var6;
         }

         this.showexception(var6);
         return false;
      }

      if (code == Py.None) {
         return true;
      } else {
         this.runcode(code);
         return false;
      }
   }

   public void runcode(PyObject code) {
      try {
         this.exec(code);
      } catch (PyException var3) {
         if (var3.match(Py.SystemExit)) {
            throw var3;
         }

         this.showexception(var3);
      }

   }

   public void showexception(PyException exc) {
      Py.printException(exc);
   }

   public void write(String data) {
      Py.stderr.write(data);
   }

   public void resetbuffer() {
      this.buffer.setLength(0);
   }

   public void interrupt(ThreadState ts) {
      TraceFunction breaker = new BreakTraceFunction();
      TraceFunction oldTrace = ts.tracefunc;
      ts.tracefunc = breaker;
      if (ts.frame != null) {
         ts.frame.tracefunc = breaker;
      }

      ts.tracefunc = oldTrace;
   }
}
