package org.python.core;

import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposedType;

@ExposedType(
   name = "dictproxy",
   isBaseType = false
)
public class PyDictProxy extends PyObject implements Traverseproc {
   PyObject dict;

   public PyDictProxy(PyObject dict) {
      this.dict = dict;
   }

   public PyObject __iter__() {
      return this.dict.__iter__();
   }

   public PyObject __finditem__(PyObject key) {
      return this.dict.__finditem__(key);
   }

   public int __len__() {
      return this.dict.__len__();
   }

   public PyObject dictproxy___getitem__(PyObject key) {
      return this.dict.__getitem__(key);
   }

   public boolean dictproxy___contains__(PyObject value) {
      return this.dict.__contains__(value);
   }

   public boolean dictproxy_has_key(PyObject key) {
      return this.dict.__contains__(key);
   }

   public PyObject dictproxy_get(PyObject key, PyObject default_object) {
      return this.dict.invoke("get", key, default_object);
   }

   public PyObject dictproxy_keys() {
      return this.dict.invoke("keys");
   }

   public PyObject dictproxy_values() {
      return this.dict.invoke("values");
   }

   public PyObject dictproxy_items() {
      return this.dict.invoke("items");
   }

   public PyObject dictproxy_iterkeys() {
      return this.dict.invoke("iterkeys");
   }

   public PyObject dictproxy_itervalues() {
      return this.dict.invoke("itervalues");
   }

   public PyObject dictproxy_iteritems() {
      return this.dict.invoke("iteritems");
   }

   public PyObject dictproxy_copy() {
      return this.dict.invoke("copy");
   }

   public int __cmp__(PyObject other) {
      return this.dictproxy___cmp__(other);
   }

   public int dictproxy___cmp__(PyObject other) {
      return this.dict._cmp(other);
   }

   public PyObject dictproxy___lt__(PyObject other) {
      return this.dict.__lt__(other);
   }

   public PyObject dictproxy___le__(PyObject other) {
      return this.dict.__le__(other);
   }

   public PyObject dictproxy___eq__(PyObject other) {
      return this.dict.__eq__(other);
   }

   public PyObject dictproxy___ne__(PyObject other) {
      return this.dict.__ne__(other);
   }

   public PyObject dictproxy___gt__(PyObject other) {
      return this.dict.__gt__(other);
   }

   public PyObject dictproxy___ge__(PyObject other) {
      return this.dict.__ge__(other);
   }

   public PyString __str__() {
      return this.dict.__str__();
   }

   public boolean isMappingType() {
      return true;
   }

   public boolean isSequenceType() {
      return false;
   }

   public int traverse(Visitproc visit, Object arg) {
      return this.dict == null ? 0 : visit.visit(this.dict, arg);
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob != null && ob == this.dict;
   }

   static {
      PyType.addBuilder(PyDictProxy.class, new PyExposer());
   }

   private static class dictproxy___getitem___exposer extends PyBuiltinMethodNarrow {
      public dictproxy___getitem___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public dictproxy___getitem___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new dictproxy___getitem___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyDictProxy)this.self).dictproxy___getitem__(var1);
      }
   }

   private static class dictproxy___contains___exposer extends PyBuiltinMethodNarrow {
      public dictproxy___contains___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public dictproxy___contains___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new dictproxy___contains___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return Py.newBoolean(((PyDictProxy)this.self).dictproxy___contains__(var1));
      }
   }

   private static class dictproxy_has_key_exposer extends PyBuiltinMethodNarrow {
      public dictproxy_has_key_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public dictproxy_has_key_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new dictproxy_has_key_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return Py.newBoolean(((PyDictProxy)this.self).dictproxy_has_key(var1));
      }
   }

   private static class dictproxy_get_exposer extends PyBuiltinMethodNarrow {
      public dictproxy_get_exposer(String var1) {
         super(var1, 2, 3);
         super.doc = "";
      }

      public dictproxy_get_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new dictproxy_get_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return ((PyDictProxy)this.self).dictproxy_get(var1, var2);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyDictProxy)this.self).dictproxy_get(var1, Py.None);
      }
   }

   private static class dictproxy_keys_exposer extends PyBuiltinMethodNarrow {
      public dictproxy_keys_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public dictproxy_keys_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new dictproxy_keys_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyDictProxy)this.self).dictproxy_keys();
      }
   }

   private static class dictproxy_values_exposer extends PyBuiltinMethodNarrow {
      public dictproxy_values_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public dictproxy_values_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new dictproxy_values_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyDictProxy)this.self).dictproxy_values();
      }
   }

   private static class dictproxy_items_exposer extends PyBuiltinMethodNarrow {
      public dictproxy_items_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public dictproxy_items_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new dictproxy_items_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyDictProxy)this.self).dictproxy_items();
      }
   }

   private static class dictproxy_iterkeys_exposer extends PyBuiltinMethodNarrow {
      public dictproxy_iterkeys_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public dictproxy_iterkeys_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new dictproxy_iterkeys_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyDictProxy)this.self).dictproxy_iterkeys();
      }
   }

   private static class dictproxy_itervalues_exposer extends PyBuiltinMethodNarrow {
      public dictproxy_itervalues_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public dictproxy_itervalues_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new dictproxy_itervalues_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyDictProxy)this.self).dictproxy_itervalues();
      }
   }

   private static class dictproxy_iteritems_exposer extends PyBuiltinMethodNarrow {
      public dictproxy_iteritems_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public dictproxy_iteritems_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new dictproxy_iteritems_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyDictProxy)this.self).dictproxy_iteritems();
      }
   }

   private static class dictproxy_copy_exposer extends PyBuiltinMethodNarrow {
      public dictproxy_copy_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public dictproxy_copy_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new dictproxy_copy_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyDictProxy)this.self).dictproxy_copy();
      }
   }

   private static class dictproxy___cmp___exposer extends PyBuiltinMethodNarrow {
      public dictproxy___cmp___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public dictproxy___cmp___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new dictproxy___cmp___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         int var10000 = ((PyDictProxy)this.self).dictproxy___cmp__(var1);
         if (var10000 == -2) {
            throw Py.TypeError("dictproxy.__cmp__(x,y) requires y to be 'dictproxy', not a '" + var1.getType().fastGetName() + "'");
         } else {
            return Py.newInteger(var10000);
         }
      }
   }

   private static class dictproxy___lt___exposer extends PyBuiltinMethodNarrow {
      public dictproxy___lt___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public dictproxy___lt___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new dictproxy___lt___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyDictProxy)this.self).dictproxy___lt__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class dictproxy___le___exposer extends PyBuiltinMethodNarrow {
      public dictproxy___le___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public dictproxy___le___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new dictproxy___le___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyDictProxy)this.self).dictproxy___le__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class dictproxy___eq___exposer extends PyBuiltinMethodNarrow {
      public dictproxy___eq___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public dictproxy___eq___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new dictproxy___eq___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyDictProxy)this.self).dictproxy___eq__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class dictproxy___ne___exposer extends PyBuiltinMethodNarrow {
      public dictproxy___ne___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public dictproxy___ne___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new dictproxy___ne___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyDictProxy)this.self).dictproxy___ne__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class dictproxy___gt___exposer extends PyBuiltinMethodNarrow {
      public dictproxy___gt___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public dictproxy___gt___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new dictproxy___gt___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyDictProxy)this.self).dictproxy___gt__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class dictproxy___ge___exposer extends PyBuiltinMethodNarrow {
      public dictproxy___ge___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public dictproxy___ge___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new dictproxy___ge___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyDictProxy)this.self).dictproxy___ge__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class __str___exposer extends PyBuiltinMethodNarrow {
      public __str___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public __str___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new __str___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyDictProxy)this.self).__str__();
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new dictproxy___getitem___exposer("__getitem__"), new dictproxy___contains___exposer("__contains__"), new dictproxy_has_key_exposer("has_key"), new dictproxy_get_exposer("get"), new dictproxy_keys_exposer("keys"), new dictproxy_values_exposer("values"), new dictproxy_items_exposer("items"), new dictproxy_iterkeys_exposer("iterkeys"), new dictproxy_itervalues_exposer("itervalues"), new dictproxy_iteritems_exposer("iteritems"), new dictproxy_copy_exposer("copy"), new dictproxy___cmp___exposer("__cmp__"), new dictproxy___lt___exposer("__lt__"), new dictproxy___le___exposer("__le__"), new dictproxy___eq___exposer("__eq__"), new dictproxy___ne___exposer("__ne__"), new dictproxy___gt___exposer("__gt__"), new dictproxy___ge___exposer("__ge__"), new __str___exposer("__str__")};
         PyDataDescr[] var2 = new PyDataDescr[0];
         super("dictproxy", PyDictProxy.class, Object.class, (boolean)0, (String)null, var1, var2, (PyNewWrapper)null);
      }
   }
}
