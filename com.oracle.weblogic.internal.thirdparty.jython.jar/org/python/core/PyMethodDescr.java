package org.python.core;

import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposeAsSuperclass;
import org.python.expose.ExposedType;

@ExposedType(
   name = "method_descriptor",
   base = PyObject.class,
   isBaseType = false
)
public class PyMethodDescr extends PyDescriptor implements PyBuiltinCallable.Info, Traverseproc {
   protected int minargs;
   protected int maxargs;
   protected PyBuiltinCallable meth;

   public PyMethodDescr(PyType t, PyBuiltinCallable func) {
      this.name = func.info.getName();
      this.dtype = t;
      this.minargs = func.info.getMinargs();
      this.maxargs = func.info.getMaxargs();
      this.meth = func;
      this.meth.setInfo(this);
   }

   public String getDoc() {
      return this.meth.getDoc();
   }

   public int getMaxargs() {
      return this.maxargs;
   }

   public int getMinargs() {
      return this.minargs;
   }

   public String toString() {
      return String.format("<method '%s' of '%s' objects>", this.name, this.dtype.fastGetName());
   }

   public PyObject __call__(PyObject[] args, String[] kwargs) {
      return this.method_descriptor___call__(args, kwargs);
   }

   final PyObject method_descriptor___call__(PyObject[] args, String[] kwargs) {
      if (args.length == kwargs.length) {
         throw Py.TypeError(this.name + " requires at least one argument");
      } else {
         this.checkCallerType(args[0].getType());
         PyObject[] actualArgs = new PyObject[args.length - 1];
         System.arraycopy(args, 1, actualArgs, 0, actualArgs.length);
         return this.meth.bind(args[0]).__call__(actualArgs, kwargs);
      }
   }

   public PyException unexpectedCall(int nargs, boolean keywords) {
      return PyBuiltinCallable.DefaultInfo.unexpectedCall(nargs, keywords, this.name, this.minargs, this.maxargs);
   }

   public PyObject __get__(PyObject obj, PyObject type) {
      return this.method_descriptor___get__(obj, type);
   }

   final PyObject method_descriptor___get__(PyObject obj, PyObject type) {
      if (obj != null) {
         this.checkGetterType(obj.getType());
         return this.meth.bind(obj);
      } else {
         return this;
      }
   }

   public String getName() {
      return this.name;
   }

   public PyObject getObjClass() {
      return this.dtype;
   }

   public int traverse(Visitproc visit, Object arg) {
      return this.meth == null ? 0 : visit.visit(this.meth, arg);
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob != null && ob == this.meth;
   }

   static {
      PyType.addBuilder(PyMethodDescr.class, new PyExposer());
   }

   private static class method_descriptor___call___exposer extends PyBuiltinMethod {
      public method_descriptor___call___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public method_descriptor___call___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new method_descriptor___call___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         return ((PyMethodDescr)this.self).method_descriptor___call__(var1, var2);
      }
   }

   private static class method_descriptor___get___exposer extends PyBuiltinMethodNarrow {
      public method_descriptor___get___exposer(String var1) {
         super(var1, 2, 3);
         super.doc = "";
      }

      public method_descriptor___get___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new method_descriptor___get___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return ((PyMethodDescr)this.self).method_descriptor___get__(var1, var2);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyMethodDescr)this.self).method_descriptor___get__(var1, (PyObject)null);
      }
   }

   private static class __objclass___descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public __objclass___descriptor() {
         super("__objclass__", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyMethodDescr)var1).getObjClass();
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
         String var10000 = ((PyMethodDescr)var1).getName();
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

   private static class __doc___descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public __doc___descriptor() {
         super("__doc__", String.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         String var10000 = ((PyMethodDescr)var1).getDoc();
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

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new method_descriptor___call___exposer("__call__"), new method_descriptor___get___exposer("__get__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new __objclass___descriptor(), new __name___descriptor(), new __doc___descriptor()};
         super("method_descriptor", PyMethodDescr.class, PyObject.class, (boolean)0, (String)null, var1, var2, (PyNewWrapper)null);
      }
   }
}
