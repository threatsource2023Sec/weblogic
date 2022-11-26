package org.python.core;

class PythonTraceFunction extends TraceFunction implements Traverseproc {
   PyObject tracefunc;

   PythonTraceFunction(PyObject tracefunc) {
      this.tracefunc = tracefunc;
   }

   private TraceFunction safeCall(PyFrame frame, String label, PyObject arg) {
      Class var4 = imp.class;
      synchronized(imp.class) {
         PythonTraceFunction var10000;
         synchronized(this) {
            ThreadState ts = Py.getThreadState();
            if (ts.tracing) {
               var10000 = null;
               return var10000;
            }

            if (this.tracefunc == null) {
               var10000 = null;
               return var10000;
            }

            PyObject ret = null;

            try {
               ts.tracing = true;
               ret = this.tracefunc.__call__((PyObject)frame, (PyObject)(new PyString(label)), (PyObject)arg);
            } catch (PyException var16) {
               frame.tracefunc = null;
               ts.tracefunc = null;
               ts.profilefunc = null;
               throw var16;
            } finally {
               ts.tracing = false;
            }

            if (ret == this.tracefunc) {
               var10000 = this;
               return var10000;
            }

            if (ret == Py.None) {
               var10000 = null;
               return var10000;
            }

            var10000 = new PythonTraceFunction(ret);
         }

         return var10000;
      }
   }

   public TraceFunction traceCall(PyFrame frame) {
      return this.safeCall(frame, "call", Py.None);
   }

   public TraceFunction traceReturn(PyFrame frame, PyObject ret) {
      return this.safeCall(frame, "return", ret);
   }

   public TraceFunction traceLine(PyFrame frame, int line) {
      return this.safeCall(frame, "line", Py.None);
   }

   public TraceFunction traceException(PyFrame frame, PyException exc) {
      PyObject safeTraceback = exc.traceback == null ? Py.None : exc.traceback;
      return this.safeCall(frame, "exception", new PyTuple(new PyObject[]{exc.type, exc.value, (PyObject)safeTraceback}));
   }

   public int traverse(Visitproc visit, Object arg) {
      return this.tracefunc == null ? 0 : visit.visit(this.tracefunc, arg);
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob != null && ob == this.tracefunc;
   }
}
