package org.python.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Iterator;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposeAsSuperclass;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@ExposedType(
   name = "function",
   isBaseType = false,
   doc = "function(code, globals[, name[, argdefs[, closure]]])\n\nCreate a function object from a code object and a dictionary.\nThe optional name string overrides the name from the code object.\nThe optional argdefs tuple specifies the default argument values.\nThe optional closure tuple supplies the bindings for free variables."
)
public class PyFunction extends PyObject implements InvocationHandler, Traverseproc {
   public static final PyType TYPE;
   public String __name__;
   public PyObject __doc__;
   public PyObject __globals__;
   public PyObject[] __defaults__;
   public PyCode __code__;
   public PyObject __dict__;
   public PyObject __closure__;
   public PyObject __module__;

   public PyFunction(PyObject globals, PyObject[] defaults, PyCode code, PyObject doc, PyObject[] closure_cells) {
      super(TYPE);
      this.__globals__ = globals;
      this.__name__ = code.co_name;
      this.__doc__ = doc != null ? doc : Py.None;
      this.__defaults__ = defaults != null && defaults.length == 0 ? null : defaults;
      this.__code__ = code;
      this.__closure__ = closure_cells != null ? new PyTuple(closure_cells) : null;
      PyObject moduleName = globals.__finditem__("__name__");
      this.__module__ = moduleName != null ? moduleName : Py.None;
   }

   public PyFunction(PyObject globals, PyObject[] defaults, PyCode code, PyObject doc) {
      this(globals, defaults, code, doc, (PyObject[])null);
   }

   public PyFunction(PyObject globals, PyObject[] defaults, PyCode code) {
      this(globals, defaults, code, (PyObject)null, (PyObject[])null);
   }

   public PyFunction(PyObject globals, PyObject[] defaults, PyCode code, PyObject[] closure_cells) {
      this(globals, defaults, code, (PyObject)null, closure_cells);
   }

   @ExposedNew
   static final PyObject function___new__(PyNewWrapper new_, boolean init, PyType subtype, PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("function", args, keywords, new String[]{"code", "globals", "name", "argdefs", "closure"}, 0);
      PyObject code = ap.getPyObject(0);
      PyObject globals = ap.getPyObject(1);
      PyObject name = ap.getPyObject(2, Py.None);
      PyObject defaults = ap.getPyObject(3, Py.None);
      PyObject closure = ap.getPyObject(4, Py.None);
      if (!(code instanceof PyBaseCode)) {
         throw Py.TypeError("function() argument 1 must be code, not " + code.getType().fastGetName());
      } else if (name != Py.None && !Py.isInstance(name, PyString.TYPE)) {
         throw Py.TypeError("arg 3 (name) must be None or string");
      } else if (defaults != Py.None && !(defaults instanceof PyTuple)) {
         throw Py.TypeError("arg 4 (defaults) must be None or tuple");
      } else {
         PyBaseCode tcode = (PyBaseCode)code;
         int nfree = tcode.co_freevars == null ? 0 : tcode.co_freevars.length;
         if (!(closure instanceof PyTuple)) {
            if (nfree > 0 && closure == Py.None) {
               throw Py.TypeError("arg 5 (closure) must be tuple");
            }

            if (closure != Py.None) {
               throw Py.TypeError("arg 5 (closure) must be None or tuple");
            }
         }

         int nclosure = closure == Py.None ? 0 : closure.__len__();
         if (nfree != nclosure) {
            throw Py.ValueError(String.format("%s requires closure of length %d, not %d", tcode.co_name, nfree, nclosure));
         } else {
            if (nclosure > 0) {
               Iterator var14 = ((PyTuple)closure).asIterable().iterator();

               while(var14.hasNext()) {
                  PyObject o = (PyObject)var14.next();
                  if (!(o instanceof PyCell)) {
                     throw Py.TypeError(String.format("arg 5 (closure) expected cell, found %s", o.getType().fastGetName()));
                  }
               }
            }

            PyFunction function = new PyFunction(globals, defaults == Py.None ? null : ((PyTuple)defaults).getArray(), tcode, (PyObject)null, closure == Py.None ? null : ((PyTuple)closure).getArray());
            if (name != Py.None) {
               function.__name__ = name.toString();
            }

            return function;
         }
      }
   }

   public void setName(String func_name) {
      this.__name__ = func_name;
   }

   public void delName() {
      throw Py.TypeError("__name__ must be set to a string object");
   }

   /** @deprecated */
   @Deprecated
   public String getFuncName() {
      return this.__name__;
   }

   /** @deprecated */
   @Deprecated
   public void setFuncName(String func_name) {
      this.setName(func_name);
   }

   /** @deprecated */
   @Deprecated
   public void delFuncName() {
      this.delName();
   }

   /** @deprecated */
   @Deprecated
   public PyObject getFuncDoc() {
      return this.__doc__;
   }

   /** @deprecated */
   @Deprecated
   public void setFuncDoc(PyObject func_doc) {
      this.__doc__ = func_doc;
   }

   /** @deprecated */
   @Deprecated
   public void delFuncDoc() {
      this.delDoc();
   }

   public void delDoc() {
      this.__doc__ = Py.None;
   }

   public PyObject getDefaults() {
      return (PyObject)(this.__defaults__ == null ? Py.None : new PyTuple(this.__defaults__));
   }

   public void setDefaults(PyObject func_defaults) {
      if (func_defaults != Py.None && !(func_defaults instanceof PyTuple)) {
         throw Py.TypeError("func_defaults must be set to a tuple object");
      } else {
         this.__defaults__ = func_defaults == Py.None ? null : ((PyTuple)func_defaults).getArray();
      }
   }

   public void delDefaults() {
      this.__defaults__ = null;
   }

   /** @deprecated */
   @Deprecated
   public PyObject getFuncDefaults() {
      return this.getDefaults();
   }

   /** @deprecated */
   @Deprecated
   public void setFuncDefaults(PyObject func_defaults) {
      this.setDefaults(func_defaults);
   }

   /** @deprecated */
   @Deprecated
   public void delFuncDefaults() {
      this.delDefaults();
   }

   /** @deprecated */
   @Deprecated
   public PyCode getFuncCode() {
      return this.__code__;
   }

   /** @deprecated */
   @Deprecated
   public void setFuncCode(PyCode code) {
      this.setCode(code);
   }

   public void setCode(PyCode code) {
      if (this.__code__ != null && code instanceof PyBaseCode) {
         PyBaseCode tcode = (PyBaseCode)code;
         int nfree = tcode.co_freevars == null ? 0 : tcode.co_freevars.length;
         int nclosure = this.__closure__ != null ? this.__closure__.__len__() : 0;
         if (nclosure != nfree) {
            throw Py.ValueError(String.format("%s() requires a code object with %d free vars, not %d", this.__name__, nclosure, nfree));
         } else {
            this.__code__ = code;
         }
      } else {
         throw Py.TypeError("__code__ must be set to a code object");
      }
   }

   public void delModule() {
      this.__module__ = Py.None;
   }

   public PyObject fastGetDict() {
      return this.__dict__;
   }

   public PyObject getDict() {
      this.ensureDict();
      return this.__dict__;
   }

   public void setDict(PyObject value) {
      if (!(value instanceof AbstractDict)) {
         throw Py.TypeError("setting function's dictionary to a non-dict");
      } else {
         this.__dict__ = value;
      }
   }

   public void delDict() {
      throw Py.TypeError("function's dictionary may not be deleted");
   }

   /** @deprecated */
   @Deprecated
   public PyObject getFuncDict() {
      return this.getDict();
   }

   /** @deprecated */
   @Deprecated
   public void setFuncDict(PyObject value) {
      this.setDict(value);
   }

   /** @deprecated */
   @Deprecated
   public void delFuncDict() {
      this.delDict();
   }

   public void setGlobals(PyObject value) {
      throw Py.TypeError("readonly attribute");
   }

   public void delGlobals() {
      throw Py.TypeError("readonly attribute");
   }

   /** @deprecated */
   @Deprecated
   public PyObject getFuncGlobals() {
      return this.__globals__;
   }

   /** @deprecated */
   @Deprecated
   public void setFuncGlobals(PyObject value) {
      this.setGlobals(value);
   }

   /** @deprecated */
   @Deprecated
   public void delFuncGlobals() {
      this.delGlobals();
   }

   public void setClosure(PyObject value) {
      throw Py.TypeError("readonly attribute");
   }

   public void delClosure() {
      throw Py.TypeError("readonly attribute");
   }

   /** @deprecated */
   @Deprecated
   public PyObject getFuncClosure() {
      return this.__closure__;
   }

   /** @deprecated */
   @Deprecated
   public void setFuncClosure(PyObject value) {
      this.setClosure(value);
   }

   /** @deprecated */
   @Deprecated
   public void delFuncClosure() {
      this.delClosure();
   }

   private void ensureDict() {
      if (this.__dict__ == null) {
         this.__dict__ = new PyStringMap();
      }

   }

   public void __setattr__(String name, PyObject value) {
      this.function___setattr__(name, value);
   }

   final void function___setattr__(String name, PyObject value) {
      this.ensureDict();
      super.__setattr__(name, value);
   }

   public PyObject __get__(PyObject obj, PyObject type) {
      return this.function___get__(obj, type);
   }

   final PyObject function___get__(PyObject obj, PyObject type) {
      return new PyMethod(this, obj, type);
   }

   public PyObject __call__() {
      return this.__call__(Py.getThreadState());
   }

   public PyObject __call__(ThreadState state) {
      return this.__code__.call(state, this.__globals__, this.__defaults__, this.__closure__);
   }

   public PyObject __call__(PyObject arg) {
      return this.__call__(Py.getThreadState(), arg);
   }

   public PyObject __call__(ThreadState state, PyObject arg0) {
      return this.__code__.call(state, arg0, this.__globals__, this.__defaults__, this.__closure__);
   }

   public PyObject __call__(PyObject arg1, PyObject arg2) {
      return this.__call__(Py.getThreadState(), arg1, arg2);
   }

   public PyObject __call__(ThreadState state, PyObject arg0, PyObject arg1) {
      return this.__code__.call(state, arg0, arg1, this.__globals__, this.__defaults__, this.__closure__);
   }

   public PyObject __call__(PyObject arg1, PyObject arg2, PyObject arg3) {
      return this.__call__(Py.getThreadState(), arg1, arg2, arg3);
   }

   public PyObject __call__(ThreadState state, PyObject arg0, PyObject arg1, PyObject arg2) {
      return this.__code__.call(state, arg0, arg1, arg2, this.__globals__, this.__defaults__, this.__closure__);
   }

   public PyObject __call__(PyObject arg0, PyObject arg1, PyObject arg2, PyObject arg3) {
      return this.__call__(Py.getThreadState(), arg0, arg1, arg2, arg3);
   }

   public PyObject __call__(ThreadState state, PyObject arg0, PyObject arg1, PyObject arg2, PyObject arg3) {
      return this.__code__.call(state, arg0, arg1, arg2, arg3, this.__globals__, this.__defaults__, this.__closure__);
   }

   public PyObject __call__(PyObject[] args) {
      return this.__call__(Py.getThreadState(), args);
   }

   public PyObject __call__(ThreadState state, PyObject[] args) {
      return this.__call__(state, args, Py.NoKeywords);
   }

   public PyObject __call__(PyObject[] args, String[] keywords) {
      return this.__call__(Py.getThreadState(), args, keywords);
   }

   public PyObject __call__(ThreadState state, PyObject[] args, String[] keywords) {
      return this.function___call__(state, args, keywords);
   }

   final PyObject function___call__(ThreadState state, PyObject[] args, String[] keywords) {
      return this.__code__.call(state, args, keywords, this.__globals__, this.__defaults__, this.__closure__);
   }

   public PyObject __call__(PyObject arg1, PyObject[] args, String[] keywords) {
      return this.__call__(Py.getThreadState(), arg1, args, keywords);
   }

   public PyObject __call__(ThreadState state, PyObject arg1, PyObject[] args, String[] keywords) {
      return this.__code__.call(state, arg1, args, keywords, this.__globals__, this.__defaults__, this.__closure__);
   }

   public String toString() {
      return String.format("<function %s at %s>", this.__name__, Py.idstr(this));
   }

   public Object __tojava__(Class c) {
      if (c.isInstance(this) && c != InvocationHandler.class) {
         return c.cast(this);
      } else {
         if (c.isInterface()) {
            if (c.getDeclaredMethods().length == 1 && c.getInterfaces().length == 0) {
               return this.proxy(c);
            }

            String name = null;
            Method[] var3 = c.getMethods();
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               Method method = var3[var5];
               if (method.getDeclaringClass() != Object.class) {
                  if (name != null && !name.equals(method.getName())) {
                     name = null;
                     break;
                  }

                  name = method.getName();
               }
            }

            if (name != null) {
               return this.proxy(c);
            }
         }

         return super.__tojava__(c);
      }
   }

   private Object proxy(Class c) {
      return Proxy.newProxyInstance(c.getClassLoader(), new Class[]{c}, this);
   }

   public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      if (method.getDeclaringClass() == Object.class) {
         return method.invoke(this, args);
      } else {
         return args != null && args.length != 0 ? this.__call__(Py.javas2pys(args)).__tojava__(method.getReturnType()) : this.__call__().__tojava__(method.getReturnType());
      }
   }

   public boolean isMappingType() {
      return false;
   }

   public boolean isNumberType() {
      return false;
   }

   public boolean isSequenceType() {
      return false;
   }

   private Object readResolve() {
      throw new UnsupportedOperationException();
   }

   public int traverse(Visitproc visit, Object arg) {
      int retVal = visit.visit(this.__globals__, arg);
      if (retVal != 0) {
         return retVal;
      } else {
         if (this.__code__ != null) {
            retVal = visit.visit(this.__code__, arg);
            if (retVal != 0) {
               return retVal;
            }
         }

         retVal = visit.visit(this.__module__, arg);
         if (retVal != 0) {
            return retVal;
         } else {
            if (this.__defaults__ != null) {
               PyObject[] var4 = this.__defaults__;
               int var5 = var4.length;

               for(int var6 = 0; var6 < var5; ++var6) {
                  PyObject ob = var4[var6];
                  if (ob != null) {
                     retVal = visit.visit(ob, arg);
                     if (retVal != 0) {
                        return retVal;
                     }
                  }
               }
            }

            retVal = visit.visit(this.__doc__, arg);
            if (retVal != 0) {
               return retVal;
            } else {
               if (this.__dict__ != null) {
                  retVal = visit.visit(this.__dict__, arg);
                  if (retVal != 0) {
                     return retVal;
                  }
               }

               return this.__closure__ != null ? visit.visit(this.__closure__, arg) : 0;
            }
         }
      }
   }

   public boolean refersDirectlyTo(PyObject ob) {
      if (ob == null) {
         return false;
      } else {
         if (this.__defaults__ != null) {
            PyObject[] var2 = this.__defaults__;
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               PyObject obj = var2[var4];
               if (obj == ob) {
                  return true;
               }
            }
         }

         return ob == this.__doc__ || ob == this.__globals__ || ob == this.__code__ || ob == this.__dict__ || ob == this.__closure__ || ob == this.__module__;
      }
   }

   static {
      PyType.addBuilder(PyFunction.class, new PyExposer());
      TYPE = PyType.fromClass(PyFunction.class);
   }

   private static class function___setattr___exposer extends PyBuiltinMethodNarrow {
      public function___setattr___exposer(String var1) {
         super(var1, 3, 3);
         super.doc = "x.__setattr__('name', value) <==> x.name = value";
      }

      public function___setattr___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__setattr__('name', value) <==> x.name = value";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new function___setattr___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         ((PyFunction)this.self).function___setattr__(var1.asString(), var2);
         return Py.None;
      }
   }

   private static class function___get___exposer extends PyBuiltinMethodNarrow {
      public function___get___exposer(String var1) {
         super(var1, 2, 3);
         super.doc = "descr.__get__(obj[, type]) -> value";
      }

      public function___get___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "descr.__get__(obj[, type]) -> value";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new function___get___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return ((PyFunction)this.self).function___get__(var1, var2);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyFunction)this.self).function___get__(var1, (PyObject)null);
      }
   }

   private static class function___call___exposer extends PyBuiltinMethod {
      public function___call___exposer(String var1) {
         super(var1);
         super.doc = "x.__call__(...) <==> x(...)";
      }

      public function___call___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__call__(...) <==> x(...)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new function___call___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(ThreadState var1, PyObject[] var2, String[] var3) {
         return ((PyFunction)this.self).function___call__(var1, var2, var3);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         return this.__call__(Py.getThreadState(), var1, var2);
      }
   }

   private static class func_dict_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public func_dict_descriptor() {
         super("func_dict", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyFunction)var1).getFuncDict();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((PyFunction)var1).setFuncDict((PyObject)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public void invokeDelete(PyObject var1) {
         ((PyFunction)var1).delFuncDict();
      }

      public boolean implementsDescrDelete() {
         return true;
      }
   }

   private static class func_closure_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public func_closure_descriptor() {
         super("func_closure", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyFunction)var1).getFuncClosure();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((PyFunction)var1).setFuncClosure((PyObject)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public void invokeDelete(PyObject var1) {
         ((PyFunction)var1).delFuncClosure();
      }

      public boolean implementsDescrDelete() {
         return true;
      }
   }

   private static class __defaults___descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public __defaults___descriptor() {
         super("__defaults__", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyFunction)var1).getDefaults();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((PyFunction)var1).setDefaults((PyObject)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public void invokeDelete(PyObject var1) {
         ((PyFunction)var1).delDefaults();
      }

      public boolean implementsDescrDelete() {
         return true;
      }
   }

   private static class __dict___descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public __dict___descriptor() {
         super("__dict__", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyFunction)var1).getDict();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((PyFunction)var1).setDict((PyObject)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public void invokeDelete(PyObject var1) {
         ((PyFunction)var1).delDict();
      }

      public boolean implementsDescrDelete() {
         return true;
      }
   }

   private static class __module___descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public __module___descriptor() {
         super("__module__", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyFunction)var1).__module__;
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((PyFunction)var1).__module__ = (PyObject)var2;
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public void invokeDelete(PyObject var1) {
         ((PyFunction)var1).delModule();
      }

      public boolean implementsDescrDelete() {
         return true;
      }
   }

   private static class __globals___descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public __globals___descriptor() {
         super("__globals__", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyFunction)var1).__globals__;
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((PyFunction)var1).setGlobals((PyObject)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public void invokeDelete(PyObject var1) {
         ((PyFunction)var1).delGlobals();
      }

      public boolean implementsDescrDelete() {
         return true;
      }
   }

   private static class func_globals_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public func_globals_descriptor() {
         super("func_globals", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyFunction)var1).getFuncGlobals();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((PyFunction)var1).setFuncGlobals((PyObject)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public void invokeDelete(PyObject var1) {
         ((PyFunction)var1).delFuncGlobals();
      }

      public boolean implementsDescrDelete() {
         return true;
      }
   }

   private static class func_defaults_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public func_defaults_descriptor() {
         super("func_defaults", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyFunction)var1).getFuncDefaults();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((PyFunction)var1).setFuncDefaults((PyObject)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public void invokeDelete(PyObject var1) {
         ((PyFunction)var1).delFuncDefaults();
      }

      public boolean implementsDescrDelete() {
         return true;
      }
   }

   private static class __name___descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public __name___descriptor() {
         super("__name__", String.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         String var10000 = ((PyFunction)var1).__name__;
         return var10000 == null ? Py.None : Py.newString(var10000);
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((PyFunction)var1).setName((String)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public void invokeDelete(PyObject var1) {
         ((PyFunction)var1).delName();
      }

      public boolean implementsDescrDelete() {
         return true;
      }
   }

   private static class __code___descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public __code___descriptor() {
         super("__code__", PyCode.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyFunction)var1).__code__;
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((PyFunction)var1).setCode((PyCode)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class __closure___descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public __closure___descriptor() {
         super("__closure__", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyFunction)var1).__closure__;
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((PyFunction)var1).setClosure((PyObject)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public void invokeDelete(PyObject var1) {
         ((PyFunction)var1).delClosure();
      }

      public boolean implementsDescrDelete() {
         return true;
      }
   }

   private static class func_doc_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public func_doc_descriptor() {
         super("func_doc", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyFunction)var1).getFuncDoc();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((PyFunction)var1).setFuncDoc((PyObject)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public void invokeDelete(PyObject var1) {
         ((PyFunction)var1).delFuncDoc();
      }

      public boolean implementsDescrDelete() {
         return true;
      }
   }

   private static class __doc___descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public __doc___descriptor() {
         super("__doc__", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyFunction)var1).__doc__;
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((PyFunction)var1).__doc__ = (PyObject)var2;
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public void invokeDelete(PyObject var1) {
         ((PyFunction)var1).delDoc();
      }

      public boolean implementsDescrDelete() {
         return true;
      }
   }

   private static class func_code_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public func_code_descriptor() {
         super("func_code", PyCode.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyFunction)var1).getFuncCode();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((PyFunction)var1).setFuncCode((PyCode)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class func_name_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public func_name_descriptor() {
         super("func_name", String.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         String var10000 = ((PyFunction)var1).getFuncName();
         return var10000 == null ? Py.None : Py.newString(var10000);
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((PyFunction)var1).setFuncName((String)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public void invokeDelete(PyObject var1) {
         ((PyFunction)var1).delFuncName();
      }

      public boolean implementsDescrDelete() {
         return true;
      }
   }

   private static class exposed___new__ extends PyNewWrapper {
      public exposed___new__() {
      }

      public PyObject new_impl(boolean var1, PyType var2, PyObject[] var3, String[] var4) {
         return PyFunction.function___new__(this, var1, var2, var3, var4);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new function___setattr___exposer("__setattr__"), new function___get___exposer("__get__"), new function___call___exposer("__call__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new func_dict_descriptor(), new func_closure_descriptor(), new __defaults___descriptor(), new __dict___descriptor(), new __module___descriptor(), new __globals___descriptor(), new func_globals_descriptor(), new func_defaults_descriptor(), new __name___descriptor(), new __code___descriptor(), new __closure___descriptor(), new func_doc_descriptor(), new __doc___descriptor(), new func_code_descriptor(), new func_name_descriptor()};
         super("function", PyFunction.class, Object.class, (boolean)0, "function(code, globals[, name[, argdefs[, closure]]])\n\nCreate a function object from a code object and a dictionary.\nThe optional name string overrides the name from the code object.\nThe optional argdefs tuple specifies the default argument values.\nThe optional closure tuple supplies the bindings for free variables.", var1, var2, new exposed___new__());
      }
   }
}
