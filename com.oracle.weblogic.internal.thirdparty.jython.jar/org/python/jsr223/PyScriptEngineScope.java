package org.python.jsr223;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import org.python.core.Py;
import org.python.core.PyBuiltinCallable;
import org.python.core.PyBuiltinMethod;
import org.python.core.PyBuiltinMethodNarrow;
import org.python.core.PyDataDescr;
import org.python.core.PyDictionary;
import org.python.core.PyIterator;
import org.python.core.PyList;
import org.python.core.PyNewWrapper;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PyType;
import org.python.core.Untraversable;
import org.python.core.Visitproc;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposeAsSuperclass;
import org.python.expose.ExposedType;

@Untraversable
@ExposedType(
   name = "scope",
   isBaseType = false
)
public final class PyScriptEngineScope extends PyObject {
   public static final PyType TYPE;
   private final ScriptContext context;
   private final ScriptEngine engine;

   PyScriptEngineScope(ScriptEngine engine, ScriptContext context) {
      this.context = context;
      this.engine = engine;
   }

   public PyObject pyGetContext() {
      return Py.java2py(this.context);
   }

   public PyObject pyGetEngine() {
      return Py.java2py(this.engine);
   }

   public PyObject scope_keys() {
      PyList members = new PyList();
      List scopes = this.context.getScopes();
      Iterator var3 = scopes.iterator();

      while(true) {
         Bindings bindings;
         do {
            if (!var3.hasNext()) {
               members.sort();
               return members;
            }

            int scope = (Integer)var3.next();
            bindings = this.context.getBindings(scope);
         } while(bindings == null);

         Iterator var6 = bindings.keySet().iterator();

         while(var6.hasNext()) {
            String key = (String)var6.next();
            members.append(new PyString(key));
         }
      }
   }

   public PyObject __getitem__(PyObject key) {
      return this.__finditem__(key);
   }

   public PyObject __iter__() {
      return new ScopeIterator(this);
   }

   final PyObject scope_get(PyObject keyObj, PyObject defaultObj) {
      String key = keyObj.asString();
      int scope = this.context.getAttributesScope(key);
      return scope == -1 ? defaultObj : Py.java2py(this.context.getAttribute(key, scope));
   }

   final boolean scope_has_key(PyObject key) {
      return this.context.getAttributesScope(key.asString()) != -1;
   }

   public boolean __contains__(PyObject obj) {
      return this.scope___contains__(obj);
   }

   final boolean scope___contains__(PyObject obj) {
      return this.scope_has_key(obj);
   }

   final PyObject scope_setdefault(PyObject keyObj, PyObject failObj) {
      String key = keyObj.asString();
      int scope = this.context.getAttributesScope(key);
      PyObject result;
      if (scope == -1) {
         int scope = 100;
         this.context.setAttribute(key, failObj instanceof PyType ? failObj : failObj.__tojava__(Object.class), scope);
         result = failObj;
      } else {
         result = Py.java2py(this.context.getAttribute(key, scope));
      }

      return result;
   }

   public String toString() {
      return this.getDictionary().toString();
   }

   public PyObject __finditem__(PyObject key) {
      return this.__finditem__(key.asString());
   }

   public PyObject __finditem__(String key) {
      int scope = this.context.getAttributesScope(key);
      return scope == -1 ? null : Py.java2py(this.context.getAttribute(key, scope));
   }

   public void __setitem__(PyObject key, PyObject value) {
      this.__setitem__(key.asString(), value);
   }

   public void __setitem__(String key, PyObject value) {
      int scope = this.context.getAttributesScope(key);
      if (scope == -1) {
         scope = 100;
      }

      this.context.setAttribute(key, value instanceof PyType ? value : value.__tojava__(Object.class), scope);
   }

   public void __delitem__(PyObject key) {
      this.__delitem__(key.asString());
   }

   public void __delitem__(String key) {
      int scope = this.context.getAttributesScope(key);
      if (scope == -1) {
         throw Py.KeyError(key);
      } else {
         this.context.removeAttribute(key, scope);
      }
   }

   private Map getMap() {
      ScopeIterator iterator = new ScopeIterator(this);
      Map map = new HashMap(iterator.size());

      for(PyObject key = iterator.__iternext__(); key != null; key = iterator.__iternext__()) {
         map.put(key, this.__finditem__(key));
      }

      return map;
   }

   private PyDictionary getDictionary() {
      return new PyDictionary(this.getMap());
   }

   static {
      PyType.addBuilder(PyScriptEngineScope.class, new PyExposer());
      TYPE = PyType.fromClass(PyScriptEngineScope.class);
   }

   public class ScopeIterator extends PyIterator {
      private int _index;
      private int _size;
      private PyObject _keys;

      ScopeIterator(PyScriptEngineScope scope) {
         this._keys = scope.scope_keys();
         this._size = this._keys.__len__();
         this._index = -1;
      }

      public int size() {
         return this._size;
      }

      public PyObject __iternext__() {
         PyObject result = null;
         ++this._index;
         if (this._index < this.size()) {
            result = this._keys.__getitem__(this._index);
         }

         return result;
      }

      public int traverse(Visitproc visit, Object arg) {
         int retVal = super.traverse(visit, arg);
         if (retVal != 0) {
            return retVal;
         } else {
            return this._keys != null ? visit.visit(this._keys, arg) : 0;
         }
      }

      public boolean refersDirectlyTo(PyObject ob) {
         return ob != null && (ob == this._keys || super.refersDirectlyTo(ob));
      }
   }

   private static class scope_keys_exposer extends PyBuiltinMethodNarrow {
      public scope_keys_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public scope_keys_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new scope_keys_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyScriptEngineScope)this.self).scope_keys();
      }
   }

   private static class __getitem___exposer extends PyBuiltinMethodNarrow {
      public __getitem___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public __getitem___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new __getitem___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyScriptEngineScope)this.self).__getitem__(var1);
      }
   }

   private static class __iter___exposer extends PyBuiltinMethodNarrow {
      public __iter___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public __iter___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new __iter___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyScriptEngineScope)this.self).__iter__();
      }
   }

   private static class scope_get_exposer extends PyBuiltinMethodNarrow {
      public scope_get_exposer(String var1) {
         super(var1, 2, 3);
         super.doc = "";
      }

      public scope_get_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new scope_get_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return ((PyScriptEngineScope)this.self).scope_get(var1, var2);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyScriptEngineScope)this.self).scope_get(var1, Py.None);
      }
   }

   private static class scope_has_key_exposer extends PyBuiltinMethodNarrow {
      public scope_has_key_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public scope_has_key_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new scope_has_key_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return Py.newBoolean(((PyScriptEngineScope)this.self).scope_has_key(var1));
      }
   }

   private static class scope___contains___exposer extends PyBuiltinMethodNarrow {
      public scope___contains___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public scope___contains___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new scope___contains___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return Py.newBoolean(((PyScriptEngineScope)this.self).scope___contains__(var1));
      }
   }

   private static class scope_setdefault_exposer extends PyBuiltinMethodNarrow {
      public scope_setdefault_exposer(String var1) {
         super(var1, 2, 3);
         super.doc = "";
      }

      public scope_setdefault_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new scope_setdefault_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return ((PyScriptEngineScope)this.self).scope_setdefault(var1, var2);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyScriptEngineScope)this.self).scope_setdefault(var1, Py.None);
      }
   }

   private static class __setitem___exposer extends PyBuiltinMethodNarrow {
      public __setitem___exposer(String var1) {
         super(var1, 3, 3);
         super.doc = "";
      }

      public __setitem___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new __setitem___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         ((PyScriptEngineScope)this.self).__setitem__(var1, var2);
         return Py.None;
      }
   }

   private static class __delitem___exposer extends PyBuiltinMethodNarrow {
      public __delitem___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public __delitem___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new __delitem___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         ((PyScriptEngineScope)this.self).__delitem__(var1);
         return Py.None;
      }
   }

   private static class engine_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public engine_descriptor() {
         super("engine", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyScriptEngineScope)var1).pyGetEngine();
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

   private static class context_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public context_descriptor() {
         super("context", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyScriptEngineScope)var1).pyGetContext();
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
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new scope_keys_exposer("keys"), new __getitem___exposer("__getitem__"), new __iter___exposer("__iter__"), new scope_get_exposer("get"), new scope_has_key_exposer("has_key"), new scope___contains___exposer("__contains__"), new scope_setdefault_exposer("setdefault"), new __setitem___exposer("__setitem__"), new __delitem___exposer("__delitem__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new engine_descriptor(), new context_descriptor()};
         super("scope", PyScriptEngineScope.class, Object.class, (boolean)0, (String)null, var1, var2, (PyNewWrapper)null);
      }
   }
}
