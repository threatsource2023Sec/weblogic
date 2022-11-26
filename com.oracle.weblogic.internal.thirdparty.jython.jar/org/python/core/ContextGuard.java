package org.python.core;

public class ContextGuard implements ContextManager {
   private final PyObject __enter__method;
   private final PyObject __exit__method;

   private ContextGuard(PyObject manager) {
      this.__exit__method = manager.__getattr__("__exit__");
      this.__enter__method = manager.__getattr__("__enter__");
   }

   public PyObject __enter__(ThreadState ts) {
      return this.__enter__method.__call__(ts);
   }

   public boolean __exit__(ThreadState ts, PyException exception) {
      Object type;
      Object value;
      Object traceback;
      if (exception != null) {
         type = exception.type;
         value = exception.value;
         traceback = exception.traceback;
      } else {
         type = value = traceback = Py.None;
      }

      return this.__exit__method.__call__((ThreadState)ts, (PyObject)type, (PyObject)(value == null ? Py.None : value), (PyObject)(traceback == null ? Py.None : traceback)).__nonzero__();
   }

   public static ContextManager getManager(PyObject manager) {
      return (ContextManager)(manager instanceof ContextManager ? (ContextManager)manager : new ContextGuard(manager));
   }

   public static PyObject makeManager(PyObject object) {
      if (object instanceof PyFunction) {
         PyFunction function = (PyFunction)object;
         PyCode code = function.__code__;
         if (code instanceof PyBaseCode) {
            PyBaseCode pyCode = (PyBaseCode)code;
            if (pyCode.co_flags.isFlagSet(CodeFlag.CO_GENERATOR)) {
               return new PyFunction(function.__globals__, function.__defaults__, new ContextCode(pyCode), function.__doc__, function.__closure__ == null ? null : ((PyTuple)function.__closure__).getArray());
            }
         }
      }

      throw Py.TypeError("Argument must be a generator function.");
   }

   private abstract static class GeneratorContextManager extends PyObject implements ContextManager, Traverseproc {
      final PyFrame frame;

      public GeneratorContextManager(PyFrame frame) {
         this.frame = frame;
      }

      public PyObject __enter__(ThreadState ts) {
         PyObject res = this.body(ts);
         if (this.frame.f_lasti == -1) {
            throw Py.RuntimeError("generator didn't yield");
         } else {
            return res;
         }
      }

      public boolean __exit__(ThreadState ts, PyException exception) {
         if (exception != null) {
            this.frame.setGeneratorInput(exception);
         }

         PyObject res;
         try {
            res = this.body(ts);
         } catch (PyException var5) {
            if (var5.equals(exception)) {
               return false;
            }

            throw var5;
         }

         if (this.frame.f_lasti != -1) {
            throw Py.RuntimeError("generator didn't stop");
         } else {
            return res.__nonzero__();
         }
      }

      abstract PyObject body(ThreadState var1);

      public int traverse(Visitproc visit, Object arg) {
         return this.frame != null ? visit.visit(this.frame, arg) : 0;
      }

      public boolean refersDirectlyTo(PyObject ob) {
         return ob != null && ob == this.frame;
      }
   }

   private static class ContextCode extends PyBaseCode implements Traverseproc {
      private final PyBaseCode code;

      ContextCode(PyBaseCode code) {
         this.co_name = code.co_name;
         this.code = code;
         this.co_argcount = code.co_argcount;
         this.nargs = code.nargs;
         this.co_firstlineno = code.co_firstlineno;
         this.co_varnames = code.co_varnames;
         this.co_cellvars = code.co_cellvars;
         this.jy_npurecell = code.jy_npurecell;
         this.co_freevars = code.co_freevars;
         this.co_filename = code.co_filename;
         this.co_nlocals = code.co_nlocals;
         this.varargs = code.varargs;
         this.varkwargs = code.varkwargs;
         CodeFlag[] var2 = CodeFlag.values();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            CodeFlag flag = var2[var4];
            if (code.co_flags.isFlagSet(flag) && flag != CodeFlag.CO_GENERATOR) {
               this.co_flags.setFlag(flag);
            }
         }

      }

      protected PyObject interpret(PyFrame frame, ThreadState ts) {
         frame.f_back = null;
         return new GeneratorContextManager(frame) {
            PyObject body(ThreadState ts) {
               return ContextCode.this.code.interpret(this.frame, ts);
            }
         };
      }

      public PyObject call(ThreadState ts, PyFrame frame, final PyObject closure) {
         frame.f_back = null;
         return new GeneratorContextManager(frame) {
            PyObject body(ThreadState ts) {
               return ContextCode.this.code.call(ts, this.frame, closure);
            }
         };
      }

      public int traverse(Visitproc visit, Object arg) {
         return this.code != null ? visit.visit(this.code, arg) : 0;
      }

      public boolean refersDirectlyTo(PyObject ob) {
         return ob != null && ob == this.code;
      }
   }
}
