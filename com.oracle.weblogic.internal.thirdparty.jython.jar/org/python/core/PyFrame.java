package org.python.core;

import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposeAsSuperclass;
import org.python.expose.ExposedType;

@ExposedType(
   name = "frame",
   isBaseType = false
)
public class PyFrame extends PyObject implements Traverseproc {
   public static final PyType TYPE;
   public PyFrame f_back;
   public PyBaseCode f_code;
   public PyObject f_builtins;
   public PyObject f_globals;
   public PyObject f_locals;
   public int f_lineno;
   public PyObject[] f_fastlocals;
   public PyCell[] f_env;
   private int env_j;
   public int f_ncells;
   public int f_nfreevars;
   public int f_lasti;
   public Object[] f_savedlocals;
   private Object generatorInput;
   public PyObject[] f_exits;
   public TraceFunction tracefunc;
   private static final String NAME_ERROR_MSG = "name '%.200s' is not defined";
   private static final String GLOBAL_NAME_ERROR_MSG = "global name '%.200s' is not defined";
   private static final String UNBOUNDLOCAL_ERROR_MSG = "local variable '%.200s' referenced before assignment";

   public PyFrame(PyBaseCode code, PyObject locals, PyObject globals, PyObject builtins) {
      super(TYPE);
      this.env_j = 0;
      this.generatorInput = Py.None;
      this.f_code = code;
      this.f_locals = locals;
      this.f_globals = globals;
      this.f_builtins = builtins;
      if (locals == null && code != null) {
         if (!code.co_flags.isFlagSet(CodeFlag.CO_OPTIMIZED) && code.nargs <= 0) {
            this.f_locals = new PyStringMap();
         } else if (code.co_nlocals > 0) {
            this.f_fastlocals = new PyObject[code.co_nlocals - code.jy_npurecell];
         }
      }

      if (code != null) {
         int env_sz = 0;
         if (code.co_freevars != null) {
            env_sz += this.f_nfreevars = code.co_freevars.length;
         }

         if (code.co_cellvars != null) {
            env_sz += this.f_ncells = code.co_cellvars.length;
         }

         if (env_sz > 0) {
            this.f_env = new PyCell[env_sz];
         }
      }

   }

   public PyFrame(PyBaseCode code, PyObject globals) {
      this(code, (PyObject)null, globals, (PyObject)null);
   }

   void setupEnv(PyTuple freevars) {
      int ntotal;
      for(ntotal = this.f_ncells + this.f_nfreevars; this.env_j < this.f_ncells; ++this.env_j) {
         this.f_env[this.env_j] = new PyCell();
      }

      for(int i = 0; this.env_j < ntotal; ++this.env_j) {
         this.f_env[this.env_j] = (PyCell)freevars.pyget(i);
         ++i;
      }

   }

   void setGeneratorInput(Object value) {
      this.generatorInput = value;
   }

   public Object getGeneratorInput() {
      Object input = this.generatorInput;
      this.generatorInput = Py.None;
      return input;
   }

   public Object checkGeneratorInput() {
      return this.generatorInput;
   }

   public PyObject getLocals() {
      if (this.f_locals == null) {
         this.f_locals = new PyStringMap();
      }

      if (this.f_code != null && (this.f_code.co_nlocals > 0 || this.f_nfreevars > 0)) {
         int i;
         if (this.f_fastlocals != null) {
            for(i = 0; i < this.f_fastlocals.length; ++i) {
               PyObject o = this.f_fastlocals[i];
               if (o != null) {
                  this.f_locals.__setitem__(this.f_code.co_varnames[i], o);
               }
            }

            if (!this.f_code.co_flags.isFlagSet(CodeFlag.CO_OPTIMIZED)) {
               this.f_fastlocals = null;
            }
         }

         int j = 0;

         PyObject v;
         for(i = 0; i < this.f_ncells; ++j) {
            v = this.f_env[j].ob_ref;
            if (v != null) {
               this.f_locals.__setitem__(this.f_code.co_cellvars[i], v);
            }

            ++i;
         }

         for(i = 0; i < this.f_nfreevars; ++j) {
            v = this.f_env[j].ob_ref;
            if (v != null) {
               this.f_locals.__setitem__(this.f_code.co_freevars[i], v);
            }

            ++i;
         }
      }

      return this.f_locals;
   }

   public PyObject getTrace() {
      return this.tracefunc instanceof PythonTraceFunction ? ((PythonTraceFunction)this.tracefunc).tracefunc : Py.None;
   }

   public void setTrace(PyObject trace) {
      this.tracefunc = new PythonTraceFunction(trace);
   }

   public void delTrace() {
      this.tracefunc = null;
   }

   public PyObject getf_locals() {
      return this.f_locals;
   }

   public void setline(int line) {
      this.f_lineno = line;
      if (this.tracefunc != null) {
         this.tracefunc = this.tracefunc.traceLine(this, line);
      }

   }

   public int getline() {
      return this.tracefunc != null ? this.f_lineno : this.f_code.getline(this);
   }

   public PyObject getlocal(int index) {
      if (this.f_fastlocals != null) {
         PyObject ret = this.f_fastlocals[index];
         if (ret != null) {
            return ret;
         }
      }

      String name = this.f_code.co_varnames[index];
      if (this.f_locals != null) {
         PyObject ret = this.f_locals.__finditem__(name);
         if (ret != null) {
            return ret;
         }
      }

      throw Py.UnboundLocalError(String.format("local variable '%.200s' referenced before assignment", name));
   }

   public PyObject getname(String index) {
      PyObject ret;
      if (this.f_locals != null && this.f_locals != this.f_globals) {
         ret = this.f_locals.__finditem__(index);
         if (ret != null) {
            return ret;
         }

         ret = this.doGetglobal(index);
      } else {
         ret = this.doGetglobal(index);
      }

      if (ret != null) {
         return ret;
      } else {
         throw Py.NameError(String.format("name '%.200s' is not defined", index));
      }
   }

   public PyObject getglobal(String index) {
      PyObject ret = this.doGetglobal(index);
      if (ret != null) {
         return ret;
      } else {
         throw Py.NameError(String.format("global name '%.200s' is not defined", index));
      }
   }

   private PyObject doGetglobal(String index) {
      PyObject ret = this.f_globals.__finditem__(index);
      if (ret != null) {
         return ret;
      } else {
         if (this.f_builtins == null) {
            this.f_builtins = Py.getThreadState().getSystemState().builtins;
         }

         return this.f_builtins.__finditem__(index);
      }
   }

   public void setlocal(int index, PyObject value) {
      if (this.f_fastlocals != null) {
         this.f_fastlocals[index] = value;
      } else {
         this.setlocal(this.f_code.co_varnames[index], value);
      }

   }

   public void setlocal(String index, PyObject value) {
      if (this.f_locals != null) {
         this.f_locals.__setitem__(index, value);
      } else {
         throw Py.SystemError(String.format("no locals found when storing '%s'", value));
      }
   }

   public void setglobal(String index, PyObject value) {
      this.f_globals.__setitem__(index, value);
   }

   public void dellocal(int index) {
      if (this.f_fastlocals != null) {
         if (this.f_fastlocals[index] == null) {
            throw Py.UnboundLocalError(String.format("local variable '%.200s' referenced before assignment", this.f_code.co_varnames[index]));
         }

         this.f_fastlocals[index] = null;
      } else {
         this.dellocal(this.f_code.co_varnames[index]);
      }

   }

   public void dellocal(String index) {
      if (this.f_locals != null) {
         try {
            this.f_locals.__delitem__(index);
         } catch (PyException var3) {
            if (var3.match(Py.KeyError)) {
               throw Py.NameError(String.format("name '%.200s' is not defined", index));
            } else {
               throw var3;
            }
         }
      } else {
         throw Py.SystemError(String.format("no locals when deleting '%s'", index));
      }
   }

   public void delglobal(String index) {
      try {
         this.f_globals.__delitem__(index);
      } catch (PyException var3) {
         if (var3.match(Py.KeyError)) {
            throw Py.NameError(String.format("global name '%.200s' is not defined", index));
         } else {
            throw var3;
         }
      }
   }

   public PyObject getclosure(int index) {
      return this.f_env[index];
   }

   public PyObject getderef(int index) {
      PyObject obj = this.f_env[index].ob_ref;
      if (obj != null) {
         return obj;
      } else {
         String name;
         if (index >= this.f_ncells) {
            name = this.f_code.co_freevars[index - this.f_ncells];
         } else {
            name = this.f_code.co_cellvars[index];
         }

         throw Py.UnboundLocalError(String.format("local variable '%.200s' referenced before assignment", name));
      }
   }

   public void setderef(int index, PyObject value) {
      this.f_env[index].ob_ref = value;
   }

   public void to_cell(int parm_index, int env_index) {
      this.f_env[env_index].ob_ref = this.f_fastlocals[parm_index];
   }

   public int traverse(Visitproc visit, Object arg) {
      int retVal;
      if (this.f_back != null) {
         retVal = visit.visit(this.f_back, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.f_code != null) {
         retVal = visit.visit(this.f_code, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.f_builtins != null) {
         retVal = visit.visit(this.f_builtins, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.f_globals != null) {
         retVal = visit.visit(this.f_globals, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.f_locals != null) {
         retVal = visit.visit(this.f_locals, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.tracefunc != null && this.tracefunc instanceof Traverseproc) {
         retVal = ((Traverseproc)this.tracefunc).traverse(visit, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      PyObject[] var4;
      int var5;
      int var6;
      PyObject ob;
      if (this.f_fastlocals != null) {
         var4 = this.f_fastlocals;
         var5 = var4.length;

         for(var6 = 0; var6 < var5; ++var6) {
            ob = var4[var6];
            if (ob != null) {
               retVal = visit.visit(ob, arg);
               if (retVal != 0) {
                  return retVal;
               }
            }
         }
      }

      if (this.f_savedlocals != null) {
         Object[] var8 = this.f_savedlocals;
         var5 = var8.length;

         for(var6 = 0; var6 < var5; ++var6) {
            Object ob = var8[var6];
            if (ob != null) {
               if (ob instanceof PyObject) {
                  retVal = visit.visit((PyObject)ob, arg);
                  if (retVal != 0) {
                     return retVal;
                  }
               } else if (ob instanceof Traverseproc) {
                  retVal = ((Traverseproc)ob).traverse(visit, arg);
                  if (retVal != 0) {
                     return retVal;
                  }
               }
            }
         }
      }

      if (this.f_env != null) {
         PyCell[] var9 = this.f_env;
         var5 = var9.length;

         for(var6 = 0; var6 < var5; ++var6) {
            PyCell ob = var9[var6];
            if (ob != null) {
               retVal = visit.visit(ob, arg);
               if (retVal != 0) {
                  return retVal;
               }
            }
         }
      }

      if (this.generatorInput != null) {
         if (this.generatorInput instanceof PyObject) {
            retVal = visit.visit((PyObject)this.generatorInput, arg);
            if (retVal != 0) {
               return retVal;
            }
         } else if (this.generatorInput instanceof Traverseproc) {
            retVal = ((Traverseproc)this.generatorInput).traverse(visit, arg);
            if (retVal != 0) {
               return retVal;
            }
         }
      }

      if (this.f_exits != null) {
         var4 = this.f_exits;
         var5 = var4.length;

         for(var6 = 0; var6 < var5; ++var6) {
            ob = var4[var6];
            if (ob != null) {
               retVal = visit.visit(ob, arg);
               if (retVal != 0) {
                  return retVal;
               }
            }
         }
      }

      return 0;
   }

   public boolean refersDirectlyTo(PyObject ob) throws UnsupportedOperationException {
      if (ob == null) {
         return false;
      } else if (ob != this.f_back && ob != this.f_code && ob != this.f_builtins && ob != this.f_globals && ob != this.f_locals && ob != this.generatorInput) {
         PyObject[] var2;
         int var3;
         int var4;
         PyObject obj;
         if (this.f_fastlocals != null) {
            var2 = this.f_fastlocals;
            var3 = var2.length;

            for(var4 = 0; var4 < var3; ++var4) {
               obj = var2[var4];
               if (obj == ob) {
                  return true;
               }
            }
         }

         if (this.f_env != null) {
            PyCell[] var6 = this.f_env;
            var3 = var6.length;

            for(var4 = 0; var4 < var3; ++var4) {
               PyObject obj = var6[var4];
               if (obj == ob) {
                  return true;
               }
            }
         }

         if (this.f_exits != null) {
            var2 = this.f_exits;
            var3 = var2.length;

            for(var4 = 0; var4 < var3; ++var4) {
               obj = var2[var4];
               if (obj == ob) {
                  return true;
               }
            }
         }

         if (this.f_savedlocals != null) {
            Object[] var7 = this.f_savedlocals;
            var3 = var7.length;

            for(var4 = 0; var4 < var3; ++var4) {
               Object obj = var7[var4];
               if (obj == ob) {
                  return true;
               }

               if (obj != null && obj instanceof Traverseproc && ((Traverseproc)obj).refersDirectlyTo(ob)) {
                  return true;
               }
            }
         }

         if (this.tracefunc != null && this.tracefunc instanceof Traverseproc && ((Traverseproc)this.tracefunc).refersDirectlyTo(ob)) {
            return true;
         } else {
            return this.generatorInput instanceof Traverseproc ? ((Traverseproc)this.generatorInput).refersDirectlyTo(ob) : false;
         }
      } else {
         return true;
      }
   }

   static {
      PyType.addBuilder(PyFrame.class, new PyExposer());
      TYPE = PyType.fromClass(PyFrame.class);
   }

   private static class f_builtins_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public f_builtins_descriptor() {
         super("f_builtins", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyFrame)var1).f_builtins;
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class f_lineno_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public f_lineno_descriptor() {
         super("f_lineno", Integer.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return Py.newInteger(((PyFrame)var1).getline());
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class f_code_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public f_code_descriptor() {
         super("f_code", PyBaseCode.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyFrame)var1).f_code;
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class f_trace_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public f_trace_descriptor() {
         super("f_trace", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyFrame)var1).getTrace();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((PyFrame)var1).setTrace((PyObject)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public void invokeDelete(PyObject var1) {
         ((PyFrame)var1).delTrace();
      }

      public boolean implementsDescrDelete() {
         return true;
      }
   }

   private static class f_back_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public f_back_descriptor() {
         super("f_back", PyFrame.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyFrame)var1).f_back;
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class f_globals_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public f_globals_descriptor() {
         super("f_globals", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyFrame)var1).f_globals;
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class f_locals_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public f_locals_descriptor() {
         super("f_locals", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyFrame)var1).getLocals();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class f_lasti_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public f_lasti_descriptor() {
         super("f_lasti", Integer.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return Py.newInteger(((PyFrame)var1).f_lasti);
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[0];
         PyDataDescr[] var2 = new PyDataDescr[]{new f_builtins_descriptor(), new f_lineno_descriptor(), new f_code_descriptor(), new f_trace_descriptor(), new f_back_descriptor(), new f_globals_descriptor(), new f_locals_descriptor(), new f_lasti_descriptor()};
         super("frame", PyFrame.class, Object.class, (boolean)0, (String)null, var1, var2, (PyNewWrapper)null);
      }
   }
}
