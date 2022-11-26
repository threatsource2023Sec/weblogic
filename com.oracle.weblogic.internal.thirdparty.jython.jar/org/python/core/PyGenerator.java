package org.python.core;

import org.python.core.finalization.FinalizableBuiltin;
import org.python.core.finalization.FinalizeTrigger;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposeAsSuperclass;
import org.python.expose.ExposedType;

@ExposedType(
   name = "generator",
   base = PyObject.class,
   isBaseType = false
)
public class PyGenerator extends PyIterator implements FinalizableBuiltin {
   public static final PyType TYPE;
   protected PyFrame gi_frame;
   protected PyCode gi_code = null;
   protected boolean gi_running;
   private PyObject closure;

   public PyGenerator(PyFrame frame, PyObject closure) {
      super(TYPE);
      this.gi_frame = frame;
      if (this.gi_frame != null) {
         this.gi_code = this.gi_frame.f_code;
      }

      this.closure = closure;
      FinalizeTrigger.ensureFinalizer(this);
   }

   public String getName() {
      return this.gi_code.co_name;
   }

   public PyObject send(PyObject value) {
      return this.generator_send(value);
   }

   final PyObject generator_send(PyObject value) {
      if (this.gi_frame == null) {
         throw Py.StopIteration("");
      } else if (this.gi_frame.f_lasti == 0 && value != Py.None) {
         throw Py.TypeError("can't send non-None value to a just-started generator");
      } else {
         this.gi_frame.setGeneratorInput(value);
         return this.next();
      }
   }

   public PyObject throw$(PyObject type, PyObject value, PyObject tb) {
      return this.generator_throw$(type, value, tb);
   }

   final PyObject generator_throw$(PyObject type, PyObject value, PyObject tb) {
      if (tb == Py.None) {
         tb = null;
      } else if (tb != null && !(tb instanceof PyTraceback)) {
         throw Py.TypeError("throw() third argument must be a traceback object");
      }

      return this.raiseException(Py.makeException(type, value, tb));
   }

   public PyObject close() {
      return this.generator_close();
   }

   final PyObject generator_close() {
      try {
         this.raiseException(Py.makeException(Py.GeneratorExit));
         throw Py.RuntimeError("generator ignored GeneratorExit");
      } catch (PyException var2) {
         if (var2.type != Py.StopIteration && var2.type != Py.GeneratorExit) {
            throw var2;
         } else {
            return Py.None;
         }
      }
   }

   public PyObject next() {
      return this.generator_next();
   }

   final PyObject generator_next() {
      return super.next();
   }

   public PyObject __iter__() {
      return this.generator___iter__();
   }

   final PyObject generator___iter__() {
      return this;
   }

   private PyObject raiseException(PyException ex) {
      if (this.gi_frame != null && this.gi_frame.f_lasti != 0) {
         this.gi_frame.setGeneratorInput(ex);
         return this.next();
      } else {
         this.gi_frame = null;
         throw ex;
      }
   }

   public void __del_builtin__() {
      if (this.gi_frame != null && this.gi_frame.f_lasti != -1) {
         try {
            this.close();
         } catch (PyException var5) {
            String className = PyException.exceptionClassName(var5.type);
            int lastDot = className.lastIndexOf(46);
            if (lastDot != -1) {
               className = className.substring(lastDot + 1);
            }

            String msg = String.format("Exception %s: %s in %s", className, var5.value.__repr__(), this.__repr__());
            Py.println(Py.getSystemState().stderr, Py.newString(msg));
         } catch (Throwable var6) {
         }

      }
   }

   public PyObject __iternext__() {
      return this.__iternext__(Py.getThreadState());
   }

   public PyObject __iternext__(ThreadState state) {
      if (this.gi_running) {
         throw Py.ValueError("generator already executing");
      } else if (this.gi_frame == null) {
         return null;
      } else if (this.gi_frame.f_lasti == -1) {
         this.gi_frame = null;
         return null;
      } else {
         this.gi_running = true;
         PyObject result = null;

         Object var4;
         try {
            result = this.gi_frame.f_code.call(state, this.gi_frame, this.closure);
            return result == Py.None && this.gi_frame.f_lasti == -1 ? null : result;
         } catch (PyException var8) {
            if (var8.type != Py.StopIteration && var8.type != Py.GeneratorExit) {
               this.gi_frame = null;
               throw var8;
            }

            this.stopException = var8;
            this.gi_frame = null;
            var4 = null;
         } finally {
            this.gi_running = false;
         }

         return (PyObject)var4;
      }
   }

   public String toString() {
      return this.generator_toString();
   }

   final String generator_toString() {
      return String.format("<generator object %s at %s>", this.getName(), Py.idstr(this));
   }

   public int traverse(Visitproc visit, Object arg) {
      int retValue = super.traverse(visit, arg);
      if (retValue != 0) {
         return retValue;
      } else {
         if (this.gi_frame != null) {
            retValue = visit.visit(this.gi_frame, arg);
            if (retValue != 0) {
               return retValue;
            }
         }

         if (this.gi_code != null) {
            retValue = visit.visit(this.gi_code, arg);
            if (retValue != 0) {
               return retValue;
            }
         }

         return this.closure == null ? 0 : visit.visit(this.closure, arg);
      }
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob != null && (ob == this.gi_frame || ob == this.gi_code || ob == this.closure || super.refersDirectlyTo(ob));
   }

   static {
      PyType.addBuilder(PyGenerator.class, new PyExposer());
      TYPE = PyType.fromClass(PyGenerator.class);
   }

   private static class generator_send_exposer extends PyBuiltinMethodNarrow {
      public generator_send_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public generator_send_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new generator_send_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyGenerator)this.self).generator_send(var1);
      }
   }

   private static class generator_close_exposer extends PyBuiltinMethodNarrow {
      public generator_close_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public generator_close_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new generator_close_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyGenerator)this.self).generator_close();
      }
   }

   private static class generator_next_exposer extends PyBuiltinMethodNarrow {
      public generator_next_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.next() -> the next value, or raise StopIteration";
      }

      public generator_next_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.next() -> the next value, or raise StopIteration";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new generator_next_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyGenerator)this.self).generator_next();
      }
   }

   private static class generator___iter___exposer extends PyBuiltinMethodNarrow {
      public generator___iter___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public generator___iter___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new generator___iter___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyGenerator)this.self).generator___iter__();
      }
   }

   private static class generator_toString_exposer extends PyBuiltinMethodNarrow {
      public generator_toString_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public generator_toString_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new generator_toString_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         String var10000 = ((PyGenerator)this.self).generator_toString();
         return (PyObject)(var10000 == null ? Py.None : Py.newString(var10000));
      }
   }

   private static class gi_code_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public gi_code_descriptor() {
         super("gi_code", PyCode.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyGenerator)var1).gi_code;
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

   private static class __name___descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public __name___descriptor() {
         super("__name__", String.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         String var10000 = ((PyGenerator)var1).getName();
         return var10000 == null ? Py.None : Py.newString(var10000);
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

   private static class gi_frame_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public gi_frame_descriptor() {
         super("gi_frame", PyFrame.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyGenerator)var1).gi_frame;
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

   private static class gi_running_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public gi_running_descriptor() {
         super("gi_running", Boolean.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return Py.newBoolean(((PyGenerator)var1).gi_running);
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
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new generator_send_exposer("send"), new PyGenerator$generator_throw$_exposer("throw"), new generator_close_exposer("close"), new generator_next_exposer("next"), new generator___iter___exposer("__iter__"), new generator_toString_exposer("__repr__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new gi_code_descriptor(), new __name___descriptor(), new gi_frame_descriptor(), new gi_running_descriptor()};
         super("generator", PyGenerator.class, PyObject.class, (boolean)0, (String)null, var1, var2, (PyNewWrapper)null);
      }
   }
}
