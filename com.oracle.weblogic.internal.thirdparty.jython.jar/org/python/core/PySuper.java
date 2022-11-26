package org.python.core;

import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposeAsSuperclass;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@ExposedType(
   name = "super",
   doc = "super(type) -> unbound super object\nsuper(type, obj) -> bound super object; requires isinstance(obj, type)\nsuper(type, type2) -> bound super object; requires issubclass(type2, type)\nTypical use to call a cooperative superclass method:\nclass C(B):\n    def meth(self, arg):\n        super(C, self).meth(arg)"
)
public class PySuper extends PyObject implements Traverseproc {
   public static final PyType TYPE;
   protected PyType superType;
   protected PyObject obj;
   protected PyType objType;

   public PySuper() {
      this(TYPE);
   }

   public PySuper(PyType subType) {
      super(subType);
   }

   @ExposedNew
   public void super___init__(PyObject[] args, String[] keywords) {
      if (keywords.length == 0 && PyBuiltinCallable.DefaultInfo.check(args.length, 1, 2)) {
         if (!(args[0] instanceof PyType)) {
            throw Py.TypeError("super: argument 1 must be type");
         } else {
            PyType type = (PyType)args[0];
            PyObject obj = null;
            PyType objType = null;
            if (args.length == 2 && args[1] != Py.None) {
               obj = args[1];
            }

            if (obj != null) {
               objType = this.supercheck(type, obj);
            }

            this.superType = type;
            this.obj = obj;
            this.objType = objType;
         }
      } else {
         throw PyBuiltinCallable.DefaultInfo.unexpectedCall(args.length, keywords.length != 0, "super", 1, 2);
      }
   }

   private PyType supercheck(PyType type, PyObject obj) {
      if (obj instanceof PyType && ((PyType)obj).isSubType(type)) {
         return (PyType)obj;
      } else {
         PyType objType = obj.getType();
         if (objType.isSubType(type)) {
            return objType;
         } else {
            PyObject classAttr = obj.__findattr__("__class__");
            if (classAttr != null && classAttr instanceof PyType && ((PyType)classAttr).isSubType(type)) {
               return (PyType)classAttr;
            } else {
               throw Py.TypeError("super(type, obj): obj must be an instance or subtype of type");
            }
         }
      }
   }

   public PyObject __findattr_ex__(String name) {
      return this.super___findattr_ex__(name);
   }

   final PyObject super___findattr_ex__(String name) {
      if (this.objType != null && name != "__class__") {
         PyObject descr = this.objType.super_lookup(this.superType, name);
         if (descr != null) {
            return descr.__get__(this.objType == this.obj ? null : this.obj, this.objType);
         }
      }

      return super.__findattr_ex__(name);
   }

   final PyObject super___getattribute__(PyObject name) {
      PyObject ret = this.super___findattr_ex__(asName(name));
      if (ret == null) {
         this.noAttributeError(asName(name));
      }

      return ret;
   }

   public PyObject __get__(PyObject obj, PyObject type) {
      return this.super___get__(obj, type);
   }

   final PyObject super___get__(PyObject obj, PyObject type) {
      if (obj != null && obj != Py.None && this.obj == null) {
         if (this.getType() != TYPE) {
            return this.getType().__call__(type, obj);
         } else {
            PyType objType = this.supercheck(this.superType, obj);
            PySuper newsuper = new PySuper();
            newsuper.superType = this.superType;
            newsuper.obj = obj;
            newsuper.objType = objType;
            return newsuper;
         }
      } else {
         return this;
      }
   }

   public String toString() {
      String superTypeName = this.superType != null ? this.superType.fastGetName() : "NULL";
      return this.objType != null ? String.format("<super: <class '%s'>, <%s object>>", superTypeName, this.objType.fastGetName()) : String.format("<super: <class '%s'>, NULL>", superTypeName);
   }

   public PyType getSuperType() {
      return this.superType;
   }

   public PyObject getObj() {
      return this.obj;
   }

   public PyType getObjType() {
      return this.objType;
   }

   public int traverse(Visitproc visit, Object arg) {
      int retVal;
      if (this.superType != null) {
         retVal = visit.visit(this.superType, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.obj != null) {
         retVal = visit.visit(this.obj, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      return this.objType == null ? 0 : visit.visit(this.objType, arg);
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob != null && (ob == this.superType || ob == this.obj || ob == this.objType);
   }

   static {
      PyType.addBuilder(PySuper.class, new PyExposer());
      TYPE = PyType.fromClass(PySuper.class);
   }

   private static class super___init___exposer extends PyBuiltinMethod {
      public super___init___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public super___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new super___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((PySuper)this.self).super___init__(var1, var2);
         return Py.None;
      }
   }

   private static class super___getattribute___exposer extends PyBuiltinMethodNarrow {
      public super___getattribute___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__getattribute__('name') <==> x.name";
      }

      public super___getattribute___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__getattribute__('name') <==> x.name";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new super___getattribute___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PySuper)this.self).super___getattribute__(var1);
      }
   }

   private static class super___get___exposer extends PyBuiltinMethodNarrow {
      public super___get___exposer(String var1) {
         super(var1, 2, 3);
         super.doc = "descr.__get__(obj[, type]) -> value";
      }

      public super___get___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "descr.__get__(obj[, type]) -> value";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new super___get___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return ((PySuper)this.self).super___get__(var1, var2);
      }

      public PyObject __call__(PyObject var1) {
         return ((PySuper)this.self).super___get__(var1, (PyObject)null);
      }
   }

   private static class __self_class___descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public __self_class___descriptor() {
         super("__self_class__", PyType.class, "the type of the instance invoking super(); may be None");
      }

      public Object invokeGet(PyObject var1) {
         return ((PySuper)var1).objType;
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

   private static class __self___descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public __self___descriptor() {
         super("__self__", PyObject.class, "the instance invoking super(); may be None");
      }

      public Object invokeGet(PyObject var1) {
         return ((PySuper)var1).obj;
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

   private static class __thisclass___descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public __thisclass___descriptor() {
         super("__thisclass__", PyType.class, "the class invoking super()");
      }

      public Object invokeGet(PyObject var1) {
         return ((PySuper)var1).superType;
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

   private static class exposed___new__ extends PyOverridableNew {
      public exposed___new__() {
      }

      public PyObject createOfType(boolean var1, PyObject[] var2, String[] var3) {
         PySuper var4 = new PySuper(this.for_type);
         if (var1) {
            var4.super___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new PySuperDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new super___init___exposer("__init__"), new super___getattribute___exposer("__getattribute__"), new super___get___exposer("__get__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new __self_class___descriptor(), new __self___descriptor(), new __thisclass___descriptor()};
         super("super", PySuper.class, Object.class, (boolean)1, "super(type) -> unbound super object\nsuper(type, obj) -> bound super object; requires isinstance(obj, type)\nsuper(type, type2) -> bound super object; requires issubclass(type2, type)\nTypical use to call a cooperative superclass method:\nclass C(B):\n    def meth(self, arg):\n        super(C, self).meth(arg)", var1, var2, new exposed___new__());
      }
   }
}
