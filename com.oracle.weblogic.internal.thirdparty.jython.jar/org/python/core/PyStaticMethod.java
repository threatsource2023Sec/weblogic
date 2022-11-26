package org.python.core;

import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposeAsSuperclass;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@ExposedType(
   name = "staticmethod",
   doc = "staticmethod(function) -> method\n\nConvert a function to be a static method.\n\nA static method does not receive an implicit first argument.\nTo declare a static method, use this idiom:\n\n     class C:\n     def f(arg1, arg2, ...): ...\n     f = staticmethod(f)\n\nIt can be called either on the class (e.g. C.f()) or on an instance\n(e.g. C().f()).  The instance is ignored except for its class.\n\nStatic methods in Python are similar to those found in Java or C++.\nFor a more advanced concept, see the classmethod builtin."
)
public class PyStaticMethod extends PyObject implements Traverseproc {
   public static final PyType TYPE;
   protected PyObject callable;

   public PyStaticMethod(PyObject callable) {
      super(TYPE);
      this.callable = callable;
   }

   @ExposedNew
   static final PyObject staticmethod_new(PyNewWrapper new_, boolean init, PyType subtype, PyObject[] args, String[] keywords) {
      if (keywords.length != 0) {
         throw Py.TypeError("staticmethod does not accept keyword arguments");
      } else if (args.length != 1) {
         throw Py.TypeError("staticmethod expected 1 argument, got " + args.length);
      } else {
         return new PyStaticMethod(args[0]);
      }
   }

   public PyObject __get__(PyObject obj, PyObject type) {
      return this.staticmethod___get__(obj, type);
   }

   final PyObject staticmethod___get__(PyObject obj, PyObject type) {
      return this.callable;
   }

   public int traverse(Visitproc visit, Object arg) {
      return this.callable != null ? visit.visit(this.callable, arg) : 0;
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob != null && ob == this.callable;
   }

   static {
      PyType.addBuilder(PyStaticMethod.class, new PyExposer());
      TYPE = PyType.fromClass(PyStaticMethod.class);
   }

   private static class staticmethod___get___exposer extends PyBuiltinMethodNarrow {
      public staticmethod___get___exposer(String var1) {
         super(var1, 2, 3);
         super.doc = "descr.__get__(obj[, type]) -> value";
      }

      public staticmethod___get___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "descr.__get__(obj[, type]) -> value";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new staticmethod___get___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return ((PyStaticMethod)this.self).staticmethod___get__(var1, var2);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyStaticMethod)this.self).staticmethod___get__(var1, (PyObject)null);
      }
   }

   private static class __func___descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public __func___descriptor() {
         super("__func__", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyStaticMethod)var1).callable;
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

   private static class exposed___new__ extends PyNewWrapper {
      public exposed___new__() {
      }

      public PyObject new_impl(boolean var1, PyType var2, PyObject[] var3, String[] var4) {
         return PyStaticMethod.staticmethod_new(this, var1, var2, var3, var4);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new staticmethod___get___exposer("__get__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new __func___descriptor()};
         super("staticmethod", PyStaticMethod.class, Object.class, (boolean)1, "staticmethod(function) -> method\n\nConvert a function to be a static method.\n\nA static method does not receive an implicit first argument.\nTo declare a static method, use this idiom:\n\n     class C:\n     def f(arg1, arg2, ...): ...\n     f = staticmethod(f)\n\nIt can be called either on the class (e.g. C.f()) or on an instance\n(e.g. C().f()).  The instance is ignored except for its class.\n\nStatic methods in Python are similar to those found in Java or C++.\nFor a more advanced concept, see the classmethod builtin.", var1, var2, new exposed___new__());
      }
   }
}
