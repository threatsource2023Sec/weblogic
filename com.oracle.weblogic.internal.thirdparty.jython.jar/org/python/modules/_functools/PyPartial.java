package org.python.modules._functools;

import java.util.Iterator;
import java.util.Map;
import org.python.core.AbstractDict;
import org.python.core.Py;
import org.python.core.PyBuiltinCallable;
import org.python.core.PyBuiltinMethod;
import org.python.core.PyBuiltinMethodNarrow;
import org.python.core.PyDataDescr;
import org.python.core.PyDictionary;
import org.python.core.PyNewWrapper;
import org.python.core.PyObject;
import org.python.core.PyStringMap;
import org.python.core.PyTuple;
import org.python.core.PyType;
import org.python.core.Traverseproc;
import org.python.core.Visitproc;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposeAsSuperclass;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;
import org.python.util.Generic;

@ExposedType(
   name = "_functools.partial"
)
public class PyPartial extends PyObject implements Traverseproc {
   public static final PyType TYPE;
   public PyObject func;
   public PyObject[] args;
   private String[] keywords;
   private PyObject __dict__;

   public PyPartial() {
      super(TYPE);
   }

   public PyPartial(PyType subType) {
      super(subType);
   }

   @ExposedNew
   public static PyObject partial___new__(PyNewWrapper new_, boolean init, PyType subtype, PyObject[] args, String[] keywords) {
      if (args.length - keywords.length < 1) {
         throw Py.TypeError("type 'partial' takes at least one argument");
      } else {
         PyObject func = args[0];
         if (!func.isCallable()) {
            throw Py.TypeError("the first argument must be callable");
         } else {
            PyObject[] noFunc = new PyObject[args.length - 1];
            System.arraycopy(args, 1, noFunc, 0, args.length - 1);
            Object partial;
            if (new_.for_type == subtype) {
               partial = new PyPartial();
            } else {
               partial = new PyPartialDerived(subtype);
            }

            ((PyPartial)partial).func = func;
            ((PyPartial)partial).args = noFunc;
            ((PyPartial)partial).keywords = keywords;
            return (PyObject)partial;
         }
      }
   }

   public PyObject __call__(PyObject[] args, String[] keywords) {
      return this.partial___call__(args, keywords);
   }

   public PyObject partial___call__(PyObject[] args, String[] keywords) {
      int partialArgc = this.args.length - this.keywords.length;
      int argc = args.length - keywords.length;
      PyObject[] argAppl;
      String[] kwAppl;
      if (partialArgc == 0 && this.keywords.length == 0) {
         argAppl = args;
         kwAppl = keywords;
      } else if (argc == 0 && keywords.length == 0) {
         argAppl = this.args;
         kwAppl = this.keywords;
      } else {
         Map merged = Generic.map();

         int i;
         String keyword;
         PyObject value;
         for(i = 0; i < this.keywords.length; ++i) {
            keyword = this.keywords[i];
            value = this.args[partialArgc + i];
            merged.put(keyword, value);
         }

         for(i = 0; i < keywords.length; ++i) {
            keyword = keywords[i];
            value = args[argc + i];
            merged.put(keyword, value);
         }

         int keywordc = merged.size();
         argAppl = new PyObject[partialArgc + argc + keywordc];
         System.arraycopy(this.args, 0, argAppl, 0, partialArgc);
         System.arraycopy(args, 0, argAppl, partialArgc, argc);
         kwAppl = new String[keywordc];
         i = 0;
         int j = partialArgc + argc;

         Map.Entry entry;
         for(Iterator var11 = merged.entrySet().iterator(); var11.hasNext(); argAppl[j++] = (PyObject)entry.getValue()) {
            entry = (Map.Entry)var11.next();
            kwAppl[i++] = (String)entry.getKey();
         }
      }

      return this.func.__call__(argAppl, kwAppl);
   }

   public PyObject getArgs() {
      PyObject[] justArgs;
      if (this.keywords.length == 0) {
         justArgs = this.args;
      } else {
         int argc = this.args.length - this.keywords.length;
         justArgs = new PyObject[argc];
         System.arraycopy(this.args, 0, justArgs, 0, argc);
      }

      return new PyTuple(justArgs);
   }

   public PyObject getKeywords() {
      if (this.keywords.length == 0) {
         return Py.None;
      } else {
         int argc = this.args.length - this.keywords.length;
         PyObject kwDict = new PyDictionary();

         for(int i = 0; i < this.keywords.length; ++i) {
            kwDict.__setitem__((PyObject)Py.newString(this.keywords[i]), this.args[argc + i]);
         }

         return kwDict;
      }
   }

   public void __setattr__(String name, PyObject value) {
      this.partial___setattr__(name, value);
   }

   final void partial___setattr__(String name, PyObject value) {
      this.ensureDict();
      super.__setattr__(name, value);
   }

   public PyObject fastGetDict() {
      return this.__dict__;
   }

   public PyObject getDict() {
      this.ensureDict();
      return this.__dict__;
   }

   public void setDict(PyObject val) {
      if (!(val instanceof AbstractDict)) {
         throw Py.TypeError("setting partial object's dictionary to a non-dict");
      } else {
         this.__dict__ = val;
      }
   }

   private void ensureDict() {
      if (this.__dict__ == null) {
         this.__dict__ = new PyStringMap();
      }

   }

   public int traverse(Visitproc visit, Object arg) {
      int retVal;
      if (this.func != null) {
         retVal = visit.visit(this.func, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.args != null) {
         PyObject[] var4 = this.args;
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

      return this.__dict__ != null ? visit.visit(this.__dict__, arg) : 0;
   }

   public boolean refersDirectlyTo(PyObject ob) {
      if (ob == null) {
         return false;
      } else {
         if (this.args != null) {
            PyObject[] var2 = this.args;
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               PyObject obj = var2[var4];
               if (obj == ob) {
                  return true;
               }
            }
         }

         return ob == this.func || ob == this.__dict__;
      }
   }

   static {
      PyType.addBuilder(PyPartial.class, new PyExposer());
      TYPE = PyType.fromClass(PyPartial.class);
   }

   private static class partial___call___exposer extends PyBuiltinMethod {
      public partial___call___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public partial___call___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new partial___call___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         return ((PyPartial)this.self).partial___call__(var1, var2);
      }
   }

   private static class partial___setattr___exposer extends PyBuiltinMethodNarrow {
      public partial___setattr___exposer(String var1) {
         super(var1, 3, 3);
         super.doc = "";
      }

      public partial___setattr___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new partial___setattr___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         ((PyPartial)this.self).partial___setattr__(var1.asString(), var2);
         return Py.None;
      }
   }

   private static class args_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public args_descriptor() {
         super("args", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyPartial)var1).getArgs();
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

   private static class func_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public func_descriptor() {
         super("func", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyPartial)var1).func;
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

   private static class keywords_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public keywords_descriptor() {
         super("keywords", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyPartial)var1).getKeywords();
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

   private static class __dict___descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public __dict___descriptor() {
         super("__dict__", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyPartial)var1).getDict();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((PyPartial)var1).setDict((PyObject)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class exposed___new__ extends PyNewWrapper {
      public exposed___new__() {
      }

      public PyObject new_impl(boolean var1, PyType var2, PyObject[] var3, String[] var4) {
         return PyPartial.partial___new__(this, var1, var2, var3, var4);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new partial___call___exposer("__call__"), new partial___setattr___exposer("__setattr__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new args_descriptor(), new func_descriptor(), new keywords_descriptor(), new __dict___descriptor()};
         super("_functools.partial", PyPartial.class, Object.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
      }
   }
}
