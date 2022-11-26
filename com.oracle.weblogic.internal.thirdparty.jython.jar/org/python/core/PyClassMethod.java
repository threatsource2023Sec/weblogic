package org.python.core;

import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposeAsSuperclass;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@ExposedType(
   name = "classmethod",
   doc = "classmethod(function) -> method\n\nConvert a function to be a class method.\n\nA class method receives the class as implicit first argument,\njust like an instance method receives the instance.\nTo declare a class method, use this idiom:\n\n  class C:\n      def f(cls, arg1, arg2, ...): ...\n      f = classmethod(f)\n\nIt can be called either on the class (e.g. C.f()) or on an instance\n(e.g. C().f()).  The instance is ignored except for its class.\nIf a class method is called for a derived class, the derived class\nobject is passed as the implied first argument.\n\nClass methods are different than C++ or Java static methods.\nIf you want those, see the staticmethod builtin."
)
public class PyClassMethod extends PyObject implements Traverseproc {
   public static final PyType TYPE;
   protected PyObject callable;

   public PyClassMethod(PyObject callable) {
      if (!callable.isCallable()) {
         throw Py.TypeError("'" + callable.getType().fastGetName() + "' object is not callable");
      } else {
         this.callable = callable;
      }
   }

   @ExposedNew
   static final PyObject classmethod_new(PyNewWrapper new_, boolean init, PyType subtype, PyObject[] args, String[] keywords) {
      if (keywords.length != 0) {
         throw Py.TypeError("classmethod does not accept keyword arguments");
      } else if (args.length != 1) {
         throw Py.TypeError("classmethod expected 1 argument, got " + args.length);
      } else {
         return new PyClassMethod(args[0]);
      }
   }

   public PyObject __get__(PyObject obj) {
      return this.classmethod___get__(obj, (PyObject)null);
   }

   public PyObject __get__(PyObject obj, PyObject type) {
      return this.classmethod___get__(obj, type);
   }

   final PyObject classmethod___get__(PyObject obj, PyObject type) {
      if (type == null) {
         type = obj.getType();
      }

      return new PyMethod(this.callable, (PyObject)type, ((PyObject)type).getType());
   }

   public int traverse(Visitproc visit, Object arg) {
      return this.callable != null ? visit.visit(this.callable, arg) : 0;
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob != null && ob == this.callable;
   }

   static {
      PyType.addBuilder(PyClassMethod.class, new PyExposer());
      TYPE = PyType.fromClass(PyClassMethod.class);
   }

   private static class classmethod___get___exposer extends PyBuiltinMethodNarrow {
      public classmethod___get___exposer(String var1) {
         super(var1, 2, 3);
         super.doc = "descr.__get__(obj[, type]) -> value";
      }

      public classmethod___get___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "descr.__get__(obj[, type]) -> value";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new classmethod___get___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return ((PyClassMethod)this.self).classmethod___get__(var1, var2);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyClassMethod)this.self).classmethod___get__(var1, (PyObject)null);
      }
   }

   private static class __func___descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public __func___descriptor() {
         super("__func__", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyClassMethod)var1).callable;
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
         return PyClassMethod.classmethod_new(this, var1, var2, var3, var4);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new classmethod___get___exposer("__get__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new __func___descriptor()};
         super("classmethod", PyClassMethod.class, Object.class, (boolean)1, "classmethod(function) -> method\n\nConvert a function to be a class method.\n\nA class method receives the class as implicit first argument,\njust like an instance method receives the instance.\nTo declare a class method, use this idiom:\n\n  class C:\n      def f(cls, arg1, arg2, ...): ...\n      f = classmethod(f)\n\nIt can be called either on the class (e.g. C.f()) or on an instance\n(e.g. C().f()).  The instance is ignored except for its class.\nIf a class method is called for a derived class, the derived class\nobject is passed as the implied first argument.\n\nClass methods are different than C++ or Java static methods.\nIf you want those, see the staticmethod builtin.", var1, var2, new exposed___new__());
      }
   }
}
