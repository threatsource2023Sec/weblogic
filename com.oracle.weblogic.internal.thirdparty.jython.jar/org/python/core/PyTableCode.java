package org.python.core;

import org.python.modules._systemrestart;

@Untraversable
public class PyTableCode extends PyBaseCode {
   PyFunctionTable funcs;
   int func_id;
   public String co_code;
   private static final String[] __members__ = new String[]{"co_name", "co_argcount", "co_varnames", "co_filename", "co_firstlineno", "co_flags", "co_cellvars", "co_freevars", "co_nlocals"};

   public PyTableCode(int argcount, String[] varnames, String filename, String name, int firstlineno, boolean varargs, boolean varkwargs, PyFunctionTable funcs, int func_id) {
      this(argcount, varnames, filename, name, firstlineno, varargs, varkwargs, funcs, func_id, (String[])null, (String[])null, 0, 0);
   }

   public PyTableCode(int argcount, String[] varnames, String filename, String name, int firstlineno, boolean varargs, boolean varkwargs, PyFunctionTable funcs, int func_id, String[] cellvars, String[] freevars, int npurecell, int moreflags) {
      this.co_code = "";
      this.co_argcount = this.nargs = argcount;
      this.co_varnames = varnames;
      this.co_nlocals = varnames.length;
      this.co_filename = filename;
      this.co_firstlineno = firstlineno;
      this.co_cellvars = cellvars;
      this.co_freevars = freevars;
      this.jy_npurecell = npurecell;
      this.varargs = varargs;
      this.co_name = name;
      if (varargs) {
         --this.co_argcount;
         this.co_flags.setFlag(CodeFlag.CO_VARARGS);
      }

      this.varkwargs = varkwargs;
      if (varkwargs) {
         --this.co_argcount;
         this.co_flags.setFlag(CodeFlag.CO_VARKEYWORDS);
      }

      this.co_flags = new CompilerFlags(this.co_flags.toBits() | moreflags);
      this.funcs = funcs;
      this.func_id = func_id;
   }

   public PyObject __dir__() {
      PyString[] members = new PyString[__members__.length];

      for(int i = 0; i < __members__.length; ++i) {
         members[i] = new PyString(__members__[i]);
      }

      return new PyList(members);
   }

   private void throwReadonly(String name) {
      for(int i = 0; i < __members__.length; ++i) {
         if (__members__[i] == name) {
            throw Py.TypeError("readonly attribute");
         }
      }

      throw Py.AttributeError(name);
   }

   public void __setattr__(String name, PyObject value) {
      this.throwReadonly(name);
   }

   public void __delattr__(String name) {
      this.throwReadonly(name);
   }

   private static PyTuple toPyStringTuple(String[] ar) {
      if (ar == null) {
         return Py.EmptyTuple;
      } else {
         int sz = ar.length;
         PyString[] pystr = new PyString[sz];

         for(int i = 0; i < sz; ++i) {
            pystr[i] = new PyString(ar[i]);
         }

         return new PyTuple(pystr);
      }
   }

   public PyObject __findattr_ex__(String name) {
      if (name == "co_varnames") {
         return toPyStringTuple(this.co_varnames);
      } else if (name == "co_cellvars") {
         return toPyStringTuple(this.co_cellvars);
      } else if (name == "co_freevars") {
         return toPyStringTuple(this.co_freevars);
      } else if (name == "co_filename") {
         return Py.fileSystemEncode(this.co_filename);
      } else if (name == "co_name") {
         return new PyString(this.co_name);
      } else {
         return (PyObject)(name == "co_flags" ? Py.newInteger(this.co_flags.toBits()) : super.__findattr_ex__(name));
      }
   }

   public PyObject call(ThreadState ts, PyFrame frame, PyObject closure) {
      if (ts.getSystemState() == null) {
         ts.setSystemState(Py.defaultSystemState);
      }

      PyException previous_exception = ts.exception;
      frame.f_back = ts.frame;
      if (frame.f_builtins == null) {
         if (frame.f_back != null) {
            frame.f_builtins = frame.f_back.f_builtins;
         } else {
            frame.f_builtins = ts.getSystemState().builtins;
         }
      }

      frame.setupEnv((PyTuple)closure);
      ts.frame = frame;
      if (ts.tracefunc != null) {
         frame.f_lineno = this.co_firstlineno;
         frame.tracefunc = ts.tracefunc.traceCall(frame);
      }

      if (ts.profilefunc != null) {
         ts.profilefunc.traceCall(frame);
      }

      ThreadStateMapping.enterCall(ts);

      PyObject ret;
      try {
         ret = this.funcs.call_function(this.func_id, frame, ts);
      } catch (Throwable var11) {
         if (!(var11 instanceof Exception)) {
            Py.warning(Py.RuntimeWarning, "PyTableCode.call caught a Throwable that is not an Exception:\n" + var11 + "\nJython internals might be in a bad state now " + "that can cause deadlocks later on." + "\nSee http://bugs.jython.org/issue2536 for details.");
         }

         PyException pye = Py.JavaError(var11);
         pye.tracebackHere(frame);
         frame.f_lasti = -1;
         if (frame.tracefunc != null) {
            frame.tracefunc.traceException(frame, pye);
         }

         if (ts.profilefunc != null) {
            ts.profilefunc.traceException(frame, pye);
         }

         ts.exception = previous_exception;
         ts.frame = ts.frame.f_back;
         throw pye;
      } finally {
         ThreadStateMapping.exitCall(ts);
      }

      if (frame.tracefunc != null) {
         frame.tracefunc.traceReturn(frame, ret);
      }

      if (ts.profilefunc != null) {
         ts.profilefunc.traceReturn(frame, ret);
      }

      ts.exception = previous_exception;
      ts.frame = ts.frame.f_back;
      if (ts.getSystemState()._systemRestart && Thread.currentThread().isInterrupted()) {
         throw new PyException(_systemrestart.SystemRestart);
      } else {
         return ret;
      }
   }

   protected PyObject interpret(PyFrame f, ThreadState ts) {
      throw new UnsupportedOperationException("Inlined interpret to improve call performance (may want to reconsider in the future).");
   }
}
