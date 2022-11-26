package org.python.core;

import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposeAsSuperclass;
import org.python.expose.ExposedType;

@Untraversable
@ExposedType(
   name = "classmethod_descriptor",
   isBaseType = false
)
public class PyClassMethodDescr extends PyMethodDescr {
   public static final PyType TYPE;

   PyClassMethodDescr(PyType type, PyBuiltinCallable meth) {
      super(type, meth);
   }

   public PyObject __get__(PyObject obj, PyObject type) {
      return this.classmethod_descriptor___get__(obj, type);
   }

   public PyObject classmethod_descriptor___get__(PyObject obj, PyObject type) {
      if (type != null && type != Py.None) {
         if (!(type instanceof PyType)) {
            throw Py.TypeError(String.format("descriptor '%s' for type '%s' needs a type, not a '%s' as arg 2", this.name, this.dtype.fastGetName(), ((PyObject)type).getType().fastGetName()));
         }
      } else {
         if (obj == null) {
            throw Py.TypeError(String.format("descriptor '%s' for type '%s' needs either an  object or a type", this.name, this.dtype.fastGetName()));
         }

         type = obj.getType();
      }

      this.checkGetterType((PyType)type);
      return this.meth.bind((PyObject)type);
   }

   public String getDoc() {
      return super.getDoc();
   }

   static {
      PyType.addBuilder(PyClassMethodDescr.class, new PyExposer());
      TYPE = PyType.fromClass(PyClassMethodDescr.class);
   }

   private static class classmethod_descriptor___get___exposer extends PyBuiltinMethodNarrow {
      public classmethod_descriptor___get___exposer(String var1) {
         super(var1, 2, 3);
         super.doc = "";
      }

      public classmethod_descriptor___get___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new classmethod_descriptor___get___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return ((PyClassMethodDescr)this.self).classmethod_descriptor___get__(var1, var2);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyClassMethodDescr)this.self).classmethod_descriptor___get__(var1, (PyObject)null);
      }
   }

   private static class __doc___descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public __doc___descriptor() {
         super("__doc__", String.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         String var10000 = ((PyClassMethodDescr)var1).getDoc();
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
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new classmethod_descriptor___get___exposer("__get__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new __doc___descriptor()};
         super("classmethod_descriptor", PyClassMethodDescr.class, Object.class, (boolean)0, (String)null, var1, var2, (PyNewWrapper)null);
      }
   }
}
