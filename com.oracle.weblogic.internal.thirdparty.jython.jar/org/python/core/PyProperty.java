package org.python.core;

import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposeAsSuperclass;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@ExposedType(
   name = "property",
   doc = "property(fget=None, fset=None, fdel=None, doc=None) -> property attribute\n\nfget is a function to be used for getting an attribute value, and likewise\nfset is a function for setting, and fdel a function for del'ing, an\nattribute.  Typical use is to define a managed attribute x:\nclass C(object):\n    def getx(self): return self._x\n    def setx(self, value): self._x = value\n    def delx(self): del self._x\n    x = property(getx, setx, delx, \"I'm the 'x' property.\")\n\nDecorators make defining new properties or modifying existing ones easy:\nclass C(object):\n    @property\n    def x(self): return self._x\n    @x.setter\n    def x(self, value): self._x = value\n    @x.deleter\n    def x(self): del self._x\n"
)
public class PyProperty extends PyObject implements Traverseproc {
   public static final PyType TYPE;
   protected PyObject fget;
   protected PyObject fset;
   protected PyObject fdel;
   protected boolean docFromGetter;
   protected PyObject doc;

   public PyProperty() {
      this(TYPE);
   }

   public PyProperty(PyType subType) {
      super(subType);
   }

   @ExposedNew
   public void property___init__(PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("property", args, keywords, new String[]{"fget", "fset", "fdel", "doc"}, 0);
      this.fget = ap.getPyObject(0, (PyObject)null);
      this.fget = this.fget == Py.None ? null : this.fget;
      this.fset = ap.getPyObject(1, (PyObject)null);
      this.fset = this.fset == Py.None ? null : this.fset;
      this.fdel = ap.getPyObject(2, (PyObject)null);
      this.fdel = this.fdel == Py.None ? null : this.fdel;
      this.doc = ap.getPyObject(3, (PyObject)null);
      if ((this.doc == null || this.doc == Py.None) && this.fget != null) {
         PyObject getDoc = this.fget.__findattr__("__doc__");
         if (this.getType() == TYPE) {
            this.doc = getDoc;
         } else {
            this.__setattr__("__doc__", getDoc);
         }

         this.docFromGetter = true;
      }

   }

   public PyObject __call__(PyObject arg1, PyObject[] args, String[] keywords) {
      return this.fget.__call__(arg1);
   }

   public PyObject __get__(PyObject obj, PyObject type) {
      return this.property___get__(obj, type);
   }

   final PyObject property___get__(PyObject obj, PyObject type) {
      if (obj != null && obj != Py.None) {
         if (this.fget == null) {
            throw Py.AttributeError("unreadable attribute");
         } else {
            return this.fget.__call__(obj);
         }
      } else {
         return this;
      }
   }

   public void __set__(PyObject obj, PyObject value) {
      this.property___set__(obj, value);
   }

   final void property___set__(PyObject obj, PyObject value) {
      if (this.fset == null) {
         throw Py.AttributeError("can't set attribute");
      } else {
         this.fset.__call__(obj, value);
      }
   }

   public void __delete__(PyObject obj) {
      this.property___delete__(obj);
   }

   final void property___delete__(PyObject obj) {
      if (this.fdel == null) {
         throw Py.AttributeError("can't delete attribute");
      } else {
         this.fdel.__call__(obj);
      }
   }

   public PyObject getter(PyObject getter) {
      return this.property_getter(getter);
   }

   final PyObject property_getter(PyObject getter) {
      return this.propertyCopy(getter, (PyObject)null, (PyObject)null);
   }

   public PyObject setter(PyObject setter) {
      return this.property_setter(setter);
   }

   final PyObject property_setter(PyObject setter) {
      return this.propertyCopy((PyObject)null, setter, (PyObject)null);
   }

   public PyObject deleter(PyObject deleter) {
      return this.property_deleter(deleter);
   }

   final PyObject property_deleter(PyObject deleter) {
      return this.propertyCopy((PyObject)null, (PyObject)null, deleter);
   }

   private PyObject propertyCopy(PyObject get, PyObject set, PyObject del) {
      if (get == null) {
         get = this.fget != null ? this.fget : Py.None;
      }

      if (set == null) {
         set = this.fset != null ? this.fset : Py.None;
      }

      if (del == null) {
         del = this.fdel != null ? this.fdel : Py.None;
      }

      PyObject doc;
      if (this.docFromGetter) {
         doc = Py.None;
      } else {
         doc = this.doc != null ? this.doc : Py.None;
      }

      return this.getType().__call__(get, set, del, doc);
   }

   public int traverse(Visitproc visit, Object arg) {
      int retVal;
      if (this.fget != null) {
         retVal = visit.visit(this.fget, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.fset != null) {
         retVal = visit.visit(this.fset, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.fdel != null) {
         retVal = visit.visit(this.fdel, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      return this.doc == null ? 0 : visit.visit(this.doc, arg);
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob != null && (ob == this.fget || ob == this.fset || ob == this.fdel || ob == this.doc);
   }

   static {
      PyType.addBuilder(PyProperty.class, new PyExposer());
      TYPE = PyType.fromClass(PyProperty.class);
   }

   private static class property___init___exposer extends PyBuiltinMethod {
      public property___init___exposer(String var1) {
         super(var1);
         super.doc = "x.__init__(...) initializes x; see help(type(x)) for signature";
      }

      public property___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__init__(...) initializes x; see help(type(x)) for signature";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new property___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((PyProperty)this.self).property___init__(var1, var2);
         return Py.None;
      }
   }

   private static class property___get___exposer extends PyBuiltinMethodNarrow {
      public property___get___exposer(String var1) {
         super(var1, 2, 3);
         super.doc = "descr.__get__(obj[, type]) -> value";
      }

      public property___get___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "descr.__get__(obj[, type]) -> value";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new property___get___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return ((PyProperty)this.self).property___get__(var1, var2);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyProperty)this.self).property___get__(var1, (PyObject)null);
      }
   }

   private static class property___set___exposer extends PyBuiltinMethodNarrow {
      public property___set___exposer(String var1) {
         super(var1, 3, 3);
         super.doc = "descr.__set__(obj, value)";
      }

      public property___set___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "descr.__set__(obj, value)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new property___set___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         ((PyProperty)this.self).property___set__(var1, var2);
         return Py.None;
      }
   }

   private static class property___delete___exposer extends PyBuiltinMethodNarrow {
      public property___delete___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "descr.__delete__(obj)";
      }

      public property___delete___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "descr.__delete__(obj)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new property___delete___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         ((PyProperty)this.self).property___delete__(var1);
         return Py.None;
      }
   }

   private static class property_getter_exposer extends PyBuiltinMethodNarrow {
      public property_getter_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "Descriptor to change the getter on a property.";
      }

      public property_getter_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Descriptor to change the getter on a property.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new property_getter_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyProperty)this.self).property_getter(var1);
      }
   }

   private static class property_setter_exposer extends PyBuiltinMethodNarrow {
      public property_setter_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "Descriptor to change the setter on a property.";
      }

      public property_setter_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Descriptor to change the setter on a property.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new property_setter_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyProperty)this.self).property_setter(var1);
      }
   }

   private static class property_deleter_exposer extends PyBuiltinMethodNarrow {
      public property_deleter_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "Descriptor to change the deleter on a property.";
      }

      public property_deleter_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Descriptor to change the deleter on a property.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new property_deleter_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyProperty)this.self).property_deleter(var1);
      }
   }

   private static class __doc___descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public __doc___descriptor() {
         super("__doc__", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyProperty)var1).doc;
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

   private static class fdel_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public fdel_descriptor() {
         super("fdel", PyObject.class, "");
      }

      public Object invokeGet(PyObject var1) {
         return ((PyProperty)var1).fdel;
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

   private static class fset_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public fset_descriptor() {
         super("fset", PyObject.class, "");
      }

      public Object invokeGet(PyObject var1) {
         return ((PyProperty)var1).fset;
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

   private static class fget_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public fget_descriptor() {
         super("fget", PyObject.class, "");
      }

      public Object invokeGet(PyObject var1) {
         return ((PyProperty)var1).fget;
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
         PyProperty var4 = new PyProperty(this.for_type);
         if (var1) {
            var4.property___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new PyPropertyDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new property___init___exposer("__init__"), new property___get___exposer("__get__"), new property___set___exposer("__set__"), new property___delete___exposer("__delete__"), new property_getter_exposer("getter"), new property_setter_exposer("setter"), new property_deleter_exposer("deleter")};
         PyDataDescr[] var2 = new PyDataDescr[]{new __doc___descriptor(), new fdel_descriptor(), new fset_descriptor(), new fget_descriptor()};
         super("property", PyProperty.class, Object.class, (boolean)1, "property(fget=None, fset=None, fdel=None, doc=None) -> property attribute\n\nfget is a function to be used for getting an attribute value, and likewise\nfset is a function for setting, and fdel a function for del'ing, an\nattribute.  Typical use is to define a managed attribute x:\nclass C(object):\n    def getx(self): return self._x\n    def setx(self, value): self._x = value\n    def delx(self): del self._x\n    x = property(getx, setx, delx, \"I'm the 'x' property.\")\n\nDecorators make defining new properties or modifying existing ones easy:\nclass C(object):\n    @property\n    def x(self): return self._x\n    @x.setter\n    def x(self, value): self._x = value\n    @x.deleter\n    def x(self): del self._x\n", var1, var2, new exposed___new__());
      }
   }
}
