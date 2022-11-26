package org.python.core;

import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposeAsSuperclass;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@ExposedType(
   name = "module"
)
public class PyModule extends PyObject implements Traverseproc {
   private final PyObject moduleDoc;
   public PyObject __dict__;

   public PyModule() {
      this.moduleDoc = new PyString("module(name[, doc])\n\nCreate a module object.\nThe name must be a string; the optional doc argument can have any type.");
   }

   public PyModule(PyType subType) {
      super(subType);
      this.moduleDoc = new PyString("module(name[, doc])\n\nCreate a module object.\nThe name must be a string; the optional doc argument can have any type.");
   }

   public PyModule(PyType subType, String name) {
      super(subType);
      this.moduleDoc = new PyString("module(name[, doc])\n\nCreate a module object.\nThe name must be a string; the optional doc argument can have any type.");
      this.module___init__((PyObject)(new PyString(name)), (PyObject)Py.None);
   }

   public PyModule(String name) {
      this((String)name, (PyObject)null);
   }

   public PyModule(String name, PyObject dict) {
      this.moduleDoc = new PyString("module(name[, doc])\n\nCreate a module object.\nThe name must be a string; the optional doc argument can have any type.");
      this.__dict__ = dict;
      this.module___init__((PyObject)(new PyString(name)), (PyObject)Py.None);
   }

   @ExposedNew
   final void module___init__(PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("__init__", args, keywords, new String[]{"name", "doc"});
      PyObject name = ap.getPyObject(0);
      PyObject docs = ap.getPyObject(1, Py.None);
      this.module___init__(name, docs);
   }

   private void module___init__(PyObject name, PyObject doc) {
      this.ensureDict();
      this.__dict__.__setitem__("__name__", name);
      this.__dict__.__setitem__("__doc__", doc);
      if (name.equals(new PyString("__main__"))) {
         this.__dict__.__setitem__("__builtins__", Py.getSystemState().modules.__finditem__("__builtin__"));
         this.__dict__.__setitem__("__package__", Py.None);
      }

   }

   public PyObject fastGetDict() {
      return this.__dict__;
   }

   public PyObject getDict() {
      return this.__dict__;
   }

   public void setDict(PyObject newDict) {
      throw Py.TypeError("readonly attribute");
   }

   public void delDict() {
      throw Py.TypeError("readonly attribute");
   }

   protected PyObject impAttr(String name) {
      if (this.__dict__ != null && name.length() != 0) {
         PyObject path = this.__dict__.__finditem__("__path__");
         if (path == null) {
            path = new PyList();
         }

         PyObject pyName = this.__dict__.__finditem__("__name__");
         if (pyName == null) {
            return null;
         } else {
            String fullName = (pyName.__str__().toString() + '.' + name).intern();
            PyObject modules = Py.getSystemState().modules;
            PyObject attr = modules.__finditem__(fullName);
            if (path instanceof PyList) {
               if (attr == null) {
                  attr = imp.find_module(name, fullName, (PyList)path);
               }
            } else if (path != Py.None) {
               throw Py.TypeError("__path__ must be list or None");
            }

            if (attr == null) {
               attr = PySystemState.packageManager.lookupName(fullName);
            }

            if (attr != null) {
               PyObject found = modules.__finditem__(fullName);
               if (found != null) {
                  attr = found;
               }

               this.__dict__.__setitem__(name, attr);
               return attr;
            } else {
               return null;
            }
         }
      } else {
         return null;
      }
   }

   public PyObject __findattr_ex__(String name) {
      PyObject attr = super.__findattr_ex__(name);
      return attr != null ? attr : this.impAttr(name);
   }

   public void __setattr__(String name, PyObject value) {
      this.module___setattr__(name, value);
   }

   final void module___setattr__(String name, PyObject value) {
      if (name != "__dict__") {
         this.ensureDict();
      }

      super.__setattr__(name, value);
   }

   public void __delattr__(String name) {
      this.module___delattr__(name);
   }

   final void module___delattr__(String name) {
      super.__delattr__(name);
   }

   public String toString() {
      return this.module_toString();
   }

   final String module_toString() {
      PyObject name = null;
      PyObject filename = null;
      if (this.__dict__ != null) {
         name = this.__dict__.__finditem__("__name__");
         filename = this.__dict__.__finditem__("__file__");
      }

      if (name == null) {
         name = new PyString("?");
      }

      return filename == null ? String.format("<module '%s' (built-in)>", name) : String.format("<module '%s' from '%s'>", name, filename);
   }

   public PyObject __dir__() {
      PyObject d;
      if (this instanceof PyModuleDerived) {
         d = this.__findattr_ex__("__dict__");
      } else {
         d = this.__dict__;
      }

      if (d != null && (d instanceof AbstractDict || d instanceof PyDictProxy)) {
         return d.invoke("keys");
      } else {
         throw Py.TypeError(String.format("%.200s.__dict__ is not a dictionary", this.getType().fastGetName().toLowerCase()));
      }
   }

   private void ensureDict() {
      if (this.__dict__ == null) {
         this.__dict__ = new PyStringMap();
      }

   }

   public Object newJ(Class jcls, Object... args) {
      return Py.newJ(this, jcls, args);
   }

   public Object newJ(Class jcls, String[] keywords, Object... args) {
      return Py.newJ(this, jcls, keywords, args);
   }

   public int traverse(Visitproc visit, Object arg) {
      return this.__dict__ == null ? 0 : visit.visit(this.__dict__, arg);
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob != null && ob == this.__dict__;
   }

   static {
      PyType.addBuilder(PyModule.class, new PyExposer());
   }

   private static class module___init___exposer extends PyBuiltinMethod {
      public module___init___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public module___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new module___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((PyModule)this.self).module___init__(var1, var2);
         return Py.None;
      }
   }

   private static class module___setattr___exposer extends PyBuiltinMethodNarrow {
      public module___setattr___exposer(String var1) {
         super(var1, 3, 3);
         super.doc = "";
      }

      public module___setattr___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new module___setattr___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         ((PyModule)this.self).module___setattr__(var1.asString(), var2);
         return Py.None;
      }
   }

   private static class module___delattr___exposer extends PyBuiltinMethodNarrow {
      public module___delattr___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public module___delattr___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new module___delattr___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         ((PyModule)this.self).module___delattr__(var1.asString());
         return Py.None;
      }
   }

   private static class module_toString_exposer extends PyBuiltinMethodNarrow {
      public module_toString_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public module_toString_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new module_toString_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         String var10000 = ((PyModule)this.self).module_toString();
         return (PyObject)(var10000 == null ? Py.None : Py.newString(var10000));
      }
   }

   private static class __dict___descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public __dict___descriptor() {
         super("__dict__", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyModule)var1).__dict__;
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((PyModule)var1).setDict((PyObject)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public void invokeDelete(PyObject var1) {
         ((PyModule)var1).delDict();
      }

      public boolean implementsDescrDelete() {
         return true;
      }
   }

   private static class exposed___new__ extends PyOverridableNew {
      public exposed___new__() {
      }

      public PyObject createOfType(boolean var1, PyObject[] var2, String[] var3) {
         PyModule var4 = new PyModule(this.for_type);
         if (var1) {
            var4.module___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new PyModuleDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new module___init___exposer("__init__"), new module___setattr___exposer("__setattr__"), new module___delattr___exposer("__delattr__"), new module_toString_exposer("__repr__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new __dict___descriptor()};
         super("module", PyModule.class, Object.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
      }
   }
}
