package org.python.core;

import org.python.google.common.base.CharMatcher;
import org.python.modules._systemrestart;

public abstract class PyBaseCode extends PyCode {
   public int co_argcount;
   int nargs;
   public int co_firstlineno = -1;
   public String[] co_varnames;
   public String[] co_cellvars;
   public int jy_npurecell;
   public String[] co_freevars;
   public String co_filename;
   public CompilerFlags co_flags = new CompilerFlags();
   public int co_nlocals;
   public boolean varargs;
   public boolean varkwargs;

   public boolean hasFreevars() {
      return this.co_freevars != null && this.co_freevars.length > 0;
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
         ret = this.interpret(frame, ts);
      } catch (Throwable var11) {
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

   public PyObject call(ThreadState state, PyObject globals, PyObject[] defaults, PyObject closure) {
      if (this.co_argcount == 0 && !this.varargs && !this.varkwargs) {
         PyFrame frame = new PyFrame(this, globals);
         return (PyObject)(this.co_flags.isFlagSet(CodeFlag.CO_GENERATOR) ? new PyGenerator(frame, closure) : this.call(state, frame, closure));
      } else {
         return this.call(state, Py.EmptyObjects, Py.NoKeywords, globals, defaults, closure);
      }
   }

   public PyObject call(ThreadState state, PyObject arg1, PyObject globals, PyObject[] defaults, PyObject closure) {
      if (this.co_argcount == 1 && !this.varargs && !this.varkwargs) {
         PyFrame frame = new PyFrame(this, globals);
         frame.f_fastlocals[0] = arg1;
         return (PyObject)(this.co_flags.isFlagSet(CodeFlag.CO_GENERATOR) ? new PyGenerator(frame, closure) : this.call(state, frame, closure));
      } else {
         return this.call(state, new PyObject[]{arg1}, Py.NoKeywords, globals, defaults, closure);
      }
   }

   public PyObject call(ThreadState state, PyObject arg1, PyObject arg2, PyObject globals, PyObject[] defaults, PyObject closure) {
      if (this.co_argcount == 2 && !this.varargs && !this.varkwargs) {
         PyFrame frame = new PyFrame(this, globals);
         frame.f_fastlocals[0] = arg1;
         frame.f_fastlocals[1] = arg2;
         return (PyObject)(this.co_flags.isFlagSet(CodeFlag.CO_GENERATOR) ? new PyGenerator(frame, closure) : this.call(state, frame, closure));
      } else {
         return this.call(state, new PyObject[]{arg1, arg2}, Py.NoKeywords, globals, defaults, closure);
      }
   }

   public PyObject call(ThreadState state, PyObject arg1, PyObject arg2, PyObject arg3, PyObject globals, PyObject[] defaults, PyObject closure) {
      if (this.co_argcount == 3 && !this.varargs && !this.varkwargs) {
         PyFrame frame = new PyFrame(this, globals);
         frame.f_fastlocals[0] = arg1;
         frame.f_fastlocals[1] = arg2;
         frame.f_fastlocals[2] = arg3;
         return (PyObject)(this.co_flags.isFlagSet(CodeFlag.CO_GENERATOR) ? new PyGenerator(frame, closure) : this.call(state, frame, closure));
      } else {
         return this.call(state, new PyObject[]{arg1, arg2, arg3}, Py.NoKeywords, globals, defaults, closure);
      }
   }

   public PyObject call(ThreadState state, PyObject arg1, PyObject arg2, PyObject arg3, PyObject arg4, PyObject globals, PyObject[] defaults, PyObject closure) {
      if (this.co_argcount == 4 && !this.varargs && !this.varkwargs) {
         PyFrame frame = new PyFrame(this, globals);
         frame.f_fastlocals[0] = arg1;
         frame.f_fastlocals[1] = arg2;
         frame.f_fastlocals[2] = arg3;
         frame.f_fastlocals[3] = arg4;
         return (PyObject)(this.co_flags.isFlagSet(CodeFlag.CO_GENERATOR) ? new PyGenerator(frame, closure) : this.call(state, frame, closure));
      } else {
         return this.call(state, new PyObject[]{arg1, arg2, arg3, arg4}, Py.NoKeywords, globals, defaults, closure);
      }
   }

   public PyObject call(ThreadState state, PyObject self, PyObject[] args, String[] keywords, PyObject globals, PyObject[] defaults, PyObject closure) {
      PyObject[] os = new PyObject[args.length + 1];
      os[0] = self;
      System.arraycopy(args, 0, os, 1, args.length);
      return this.call(state, os, keywords, globals, defaults, closure);
   }

   public PyObject call(ThreadState state, PyObject[] args, String[] kws, PyObject globals, PyObject[] defs, PyObject closure) {
      PyFrame frame = new PyFrame(this, globals);
      int argcount = args.length - kws.length;
      if (this.co_argcount <= 0 && !this.varargs && !this.varkwargs) {
         if (argcount > 0 || args.length > 0 && this.co_argcount == 0 && !this.varargs && !this.varkwargs) {
            throw Py.TypeError(String.format("%.200s() takes no arguments (%d given)", this.co_name, args.length));
         }
      } else {
         int n = argcount;
         PyObject kwdict = null;
         PyObject[] fastlocals = frame.f_fastlocals;
         int i;
         if (this.varkwargs) {
            kwdict = new PyDictionary();
            i = this.co_argcount;
            if (this.varargs) {
               ++i;
            }

            fastlocals[i] = kwdict;
         }

         int defcount;
         if (argcount > this.co_argcount) {
            if (!this.varargs) {
               defcount = defs != null ? defs.length : 0;
               String msg = String.format("%.200s() takes %s %d %sargument%s (%d given)", this.co_name, defcount > 0 ? "at most" : "exactly", this.co_argcount, kws.length > 0 ? "" : "", this.co_argcount == 1 ? "" : "s", args.length);
               throw Py.TypeError(msg);
            }

            n = this.co_argcount;
         }

         System.arraycopy(args, 0, fastlocals, 0, n);
         if (this.varargs) {
            PyObject[] u = new PyObject[argcount - n];
            System.arraycopy(args, n, u, 0, argcount - n);
            PyObject uTuple = new PyTuple(u);
            fastlocals[this.co_argcount] = uTuple;
         }

         for(i = 0; i < kws.length; ++i) {
            String keyword = kws[i];
            PyObject value = args[i + argcount];

            int j;
            for(j = 0; j < this.co_argcount && !this.co_varnames[j].equals(keyword); ++j) {
            }

            if (j >= this.co_argcount) {
               if (kwdict == null) {
                  throw Py.TypeError(String.format("%.200s() got an unexpected keyword argument '%.400s'", this.co_name, Py.newUnicode(keyword).encode("ascii", "replace")));
               }

               if (CharMatcher.ascii().matchesAllOf(keyword)) {
                  kwdict.__setitem__(keyword, value);
               } else {
                  kwdict.__setitem__((PyObject)Py.newUnicode(keyword), value);
               }
            } else {
               if (fastlocals[j] != null) {
                  throw Py.TypeError(String.format("%.200s() got multiple values for keyword argument '%.400s'", this.co_name, keyword));
               }

               fastlocals[j] = value;
            }
         }

         if (argcount < this.co_argcount) {
            defcount = defs != null ? defs.length : 0;
            int m = this.co_argcount - defcount;

            for(i = argcount; i < m; ++i) {
               if (fastlocals[i] == null) {
                  String msg = String.format("%.200s() takes %s %d %sargument%s (%d given)", this.co_name, !this.varargs && defcount <= 0 ? "exactly" : "at least", m, kws.length > 0 ? "" : "", m == 1 ? "" : "s", args.length);
                  throw Py.TypeError(msg);
               }
            }

            if (n > m) {
               i = n - m;
            } else {
               i = 0;
            }

            for(; i < defcount; ++i) {
               if (fastlocals[m + i] == null) {
                  fastlocals[m + i] = defs[i];
               }
            }
         }
      }

      if (this.co_flags.isFlagSet(CodeFlag.CO_GENERATOR)) {
         return new PyGenerator(frame, closure);
      } else {
         return this.call(state, frame, closure);
      }
   }

   public String toString() {
      String filename = PyString.encode_UnicodeEscape(this.co_filename, '"');
      return String.format("<code object %.100s at %s, file %.300s, line %d>", this.co_name, Py.idstr(this), filename, this.co_firstlineno);
   }

   protected abstract PyObject interpret(PyFrame var1, ThreadState var2);

   protected int getline(PyFrame f) {
      return f.f_lineno;
   }

   public CompilerFlags getCompilerFlags() {
      return this.co_flags;
   }
}
