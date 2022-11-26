package org.python.core;

import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposeAsSuperclass;
import org.python.expose.ExposedType;

@Untraversable
@ExposedType(
   name = "getset_descriptor",
   base = PyObject.class,
   isBaseType = false
)
public abstract class PyDataDescr extends PyDescriptor {
   protected Class ofType;
   private String doc;

   public PyDataDescr(PyType onType, String name, Class ofType, String doc) {
      this(name, ofType, doc);
      this.setType(onType);
   }

   public PyDataDescr(String name, Class ofType, String doc) {
      this.name = name;
      this.ofType = ofType;
      this.doc = doc;
   }

   public void setType(PyType onType) {
      this.dtype = onType;
   }

   public PyObject __get__(PyObject obj, PyObject type) {
      return this.getset_descriptor___get__(obj, type);
   }

   public PyObject getset_descriptor___get__(PyObject obj, PyObject type) {
      if (obj != null) {
         this.checkGetterType(obj.getType());
         return Py.java2py(this.invokeGet(obj));
      } else {
         return this;
      }
   }

   public abstract Object invokeGet(PyObject var1);

   public void __set__(PyObject obj, PyObject value) {
      this.getset_descriptor___set__(obj, value);
   }

   public void getset_descriptor___set__(PyObject obj, PyObject value) {
      this.checkGetterType(obj.getType());
      Object converted = value.__tojava__(this.ofType);
      if (converted == Py.NoConversion) {
         throw Py.TypeError(String.format("unsupported type for assignment to %s: '%.200s'", this.name, value.getType().fastGetName()));
      } else {
         this.invokeSet(obj, converted);
      }
   }

   public void invokeSet(PyObject obj, Object converted) {
      throw new UnsupportedOperationException("Must be overriden by a subclass");
   }

   public void __delete__(PyObject obj) {
      this.getset_descriptor___delete__(obj);
   }

   public void getset_descriptor___delete__(PyObject obj) {
      if (obj != null) {
         this.checkGetterType(obj.getType());
         this.invokeDelete(obj);
      }

   }

   public void invokeDelete(PyObject obj) {
      throw new UnsupportedOperationException("Must be overriden by a subclass");
   }

   public boolean isDataDescr() {
      return true;
   }

   public String toString() {
      return String.format("<attribute '%s' of '%s' objects>", this.name, this.dtype.fastGetName());
   }

   public String getDoc() {
      return this.doc;
   }

   public String getName() {
      return this.name;
   }

   public PyObject getObjClass() {
      return this.dtype;
   }

   static {
      PyType.addBuilder(PyDataDescr.class, new PyExposer());
   }

   private static class getset_descriptor___get___exposer extends PyBuiltinMethodNarrow {
      public getset_descriptor___get___exposer(String var1) {
         super(var1, 2, 3);
         super.doc = "";
      }

      public getset_descriptor___get___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new getset_descriptor___get___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return ((PyDataDescr)this.self).getset_descriptor___get__(var1, var2);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyDataDescr)this.self).getset_descriptor___get__(var1, (PyObject)null);
      }
   }

   private static class getset_descriptor___set___exposer extends PyBuiltinMethodNarrow {
      public getset_descriptor___set___exposer(String var1) {
         super(var1, 3, 3);
         super.doc = "";
      }

      public getset_descriptor___set___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new getset_descriptor___set___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         ((PyDataDescr)this.self).getset_descriptor___set__(var1, var2);
         return Py.None;
      }
   }

   private static class getset_descriptor___delete___exposer extends PyBuiltinMethodNarrow {
      public getset_descriptor___delete___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public getset_descriptor___delete___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new getset_descriptor___delete___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         ((PyDataDescr)this.self).getset_descriptor___delete__(var1);
         return Py.None;
      }
   }

   private static class __objclass___descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public __objclass___descriptor() {
         super("__objclass__", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyDataDescr)var1).getObjClass();
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
         String var10000 = ((PyDataDescr)var1).getName();
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
         String var10000 = ((PyDataDescr)var1).getDoc();
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
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new getset_descriptor___get___exposer("__get__"), new getset_descriptor___set___exposer("__set__"), new getset_descriptor___delete___exposer("__delete__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new __objclass___descriptor(), new __name___descriptor(), new __doc___descriptor()};
         super("getset_descriptor", PyDataDescr.class, PyObject.class, (boolean)0, (String)null, var1, var2, (PyNewWrapper)null);
      }
   }
}
