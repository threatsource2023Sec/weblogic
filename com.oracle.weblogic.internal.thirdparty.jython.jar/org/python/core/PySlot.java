package org.python.core;

import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposeAsSuperclass;
import org.python.expose.ExposedType;

@Untraversable
@ExposedType(
   name = "member_descriptor",
   base = PyObject.class,
   isBaseType = false
)
public class PySlot extends PyDescriptor {
   private int index;

   public PySlot(PyType dtype, String name, int index) {
      this.name = name;
      this.dtype = dtype;
      this.index = index;
   }

   public boolean implementsDescrSet() {
      return true;
   }

   public boolean isDataDescr() {
      return true;
   }

   public PyObject __get__(PyObject obj, PyObject type) {
      return this.member_descriptor___get__(obj, type);
   }

   public PyObject member_descriptor___get__(PyObject obj, PyObject type) {
      if (obj != null && obj != Py.None) {
         this.checkGetterType(obj.getType());
         return ((Slotted)obj).getSlot(this.index);
      } else {
         return this;
      }
   }

   public void __set__(PyObject obj, PyObject value) {
      this.member_descriptor___set__(obj, value);
   }

   public void member_descriptor___set__(PyObject obj, PyObject value) {
      this.checkGetterType(obj.getType());
      ((Slotted)obj).setSlot(this.index, value);
   }

   public void __delete__(PyObject obj) {
      this.member_descriptor___delete__(obj);
   }

   public void member_descriptor___delete__(PyObject obj) {
      this.checkGetterType(obj.getType());
      ((Slotted)obj).setSlot(this.index, (PyObject)null);
   }

   public String toString() {
      return String.format("<member '%s' of '%s' objects>", this.name, this.dtype.fastGetName());
   }

   public String getName() {
      return this.name;
   }

   public PyObject getObjClass() {
      return this.dtype;
   }

   static {
      PyType.addBuilder(PySlot.class, new PyExposer());
   }

   private static class member_descriptor___get___exposer extends PyBuiltinMethodNarrow {
      public member_descriptor___get___exposer(String var1) {
         super(var1, 2, 3);
         super.doc = "";
      }

      public member_descriptor___get___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new member_descriptor___get___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return ((PySlot)this.self).member_descriptor___get__(var1, var2);
      }

      public PyObject __call__(PyObject var1) {
         return ((PySlot)this.self).member_descriptor___get__(var1, (PyObject)null);
      }
   }

   private static class member_descriptor___set___exposer extends PyBuiltinMethodNarrow {
      public member_descriptor___set___exposer(String var1) {
         super(var1, 3, 3);
         super.doc = "";
      }

      public member_descriptor___set___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new member_descriptor___set___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         ((PySlot)this.self).member_descriptor___set__(var1, var2);
         return Py.None;
      }
   }

   private static class member_descriptor___delete___exposer extends PyBuiltinMethodNarrow {
      public member_descriptor___delete___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public member_descriptor___delete___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new member_descriptor___delete___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         ((PySlot)this.self).member_descriptor___delete__(var1);
         return Py.None;
      }
   }

   private static class __objclass___descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public __objclass___descriptor() {
         super("__objclass__", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PySlot)var1).getObjClass();
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
         String var10000 = ((PySlot)var1).getName();
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
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new member_descriptor___get___exposer("__get__"), new member_descriptor___set___exposer("__set__"), new member_descriptor___delete___exposer("__delete__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new __objclass___descriptor(), new __name___descriptor()};
         super("member_descriptor", PySlot.class, PyObject.class, (boolean)0, (String)null, var1, var2, (PyNewWrapper)null);
      }
   }
}
