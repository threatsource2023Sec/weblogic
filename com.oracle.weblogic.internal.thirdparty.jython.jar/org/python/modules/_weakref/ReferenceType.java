package org.python.modules._weakref;

import org.python.core.ArgParser;
import org.python.core.Py;
import org.python.core.PyBuiltinCallable;
import org.python.core.PyBuiltinMethod;
import org.python.core.PyDataDescr;
import org.python.core.PyNewWrapper;
import org.python.core.PyObject;
import org.python.core.PyType;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@ExposedType(
   name = "weakref"
)
public class ReferenceType extends AbstractReference {
   public static final PyType TYPE;

   public ReferenceType(PyType subType, ReferenceBackend gref, PyObject callback) {
      super(subType, gref, callback);
   }

   public ReferenceType(ReferenceBackend gref, PyObject callback) {
      this(TYPE, gref, callback);
   }

   @ExposedNew
   static final PyObject weakref___new__(PyNewWrapper new_, boolean init, PyType subtype, PyObject[] args, String[] keywords) {
      ArgParser ap = parseInitArgs("__new__", args, keywords);
      PyObject ob = ap.getPyObject(0);
      PyObject callback = ap.getPyObject(1, (PyObject)null);
      if (callback == Py.None) {
         callback = null;
      }

      ReferenceBackend gref = GlobalRef.newInstance(ob);
      if (new_.for_type == subtype) {
         if (callback == null) {
            ReferenceType ret = (ReferenceType)gref.find(ReferenceType.class);
            if (ret != null) {
               return ret;
            }
         }

         return new ReferenceType(gref, callback);
      } else {
         return new ReferenceTypeDerived(subtype, gref, callback);
      }
   }

   final void weakref___init__(PyObject[] args, String[] keywords) {
      ArgParser ap = parseInitArgs("__init__", args, keywords);
      ap.getPyObject(0);
      int arglen = ap.getList(2).__len__();
      if (arglen > 2) {
         throw Py.TypeError(String.format("__init__ expected at most 2 arguments, got %d", arglen));
      }
   }

   private static ArgParser parseInitArgs(String funcName, PyObject[] args, String[] keywords) {
      if (keywords.length > 0) {
         int argc = args.length - keywords.length;
         PyObject[] justArgs = new PyObject[argc];
         System.arraycopy(args, 0, justArgs, 0, argc);
         args = justArgs;
      }

      return new ArgParser(funcName, args, Py.NoKeywords, Py.NoKeywords);
   }

   public PyObject __call__(PyObject[] args, String[] keywords) {
      return this.weakref___call__(args, keywords);
   }

   final PyObject weakref___call__(PyObject[] args, String[] keywords) {
      new ArgParser("__call__", args, keywords, Py.NoKeywords, 0);
      return Py.java2py(this.get());
   }

   public String toString() {
      PyObject obj = this.get();
      if (obj == null) {
         return String.format("<weakref at %s; dead>", Py.idstr(this));
      } else {
         PyObject nameObj = obj.__findattr__("__name__");
         return nameObj != null ? String.format("<weakref at %s; to '%.50s' at %s (%s)>", Py.idstr(this), obj.getType().fastGetName(), Py.idstr(obj), nameObj) : String.format("<weakref at %s; to '%.50s' at %s>", Py.idstr(this), obj.getType().fastGetName(), Py.idstr(obj));
      }
   }

   static {
      PyType.addBuilder(ReferenceType.class, new PyExposer());
      TYPE = PyType.fromClass(ReferenceType.class);
   }

   private static class weakref___init___exposer extends PyBuiltinMethod {
      public weakref___init___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public weakref___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new weakref___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((ReferenceType)this.self).weakref___init__(var1, var2);
         return Py.None;
      }
   }

   private static class weakref___call___exposer extends PyBuiltinMethod {
      public weakref___call___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public weakref___call___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new weakref___call___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         return ((ReferenceType)this.self).weakref___call__(var1, var2);
      }
   }

   private static class exposed___new__ extends PyNewWrapper {
      public exposed___new__() {
      }

      public PyObject new_impl(boolean var1, PyType var2, PyObject[] var3, String[] var4) {
         return ReferenceType.weakref___new__(this, var1, var2, var3, var4);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new weakref___init___exposer("__init__"), new weakref___call___exposer("__call__")};
         PyDataDescr[] var2 = new PyDataDescr[0];
         super("weakref", ReferenceType.class, Object.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
      }
   }
}
