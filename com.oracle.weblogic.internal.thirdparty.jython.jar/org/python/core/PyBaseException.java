package org.python.core;

import java.util.Iterator;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposeAsSuperclass;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@ExposedType(
   name = "exceptions.BaseException",
   doc = "Common base class for all exceptions"
)
public class PyBaseException extends PyObject implements Traverseproc {
   public static final PyType TYPE;
   private PyObject message;
   public PyObject args;
   public PyObject __dict__;

   public PyBaseException() {
      this.message = Py.EmptyString;
      this.args = Py.EmptyTuple;
   }

   public PyBaseException(PyType subType) {
      super(subType);
      this.message = Py.EmptyString;
      this.args = Py.EmptyTuple;
   }

   public void __init__(PyObject[] args, String[] keywords) {
      this.BaseException___init__(args, keywords);
   }

   @ExposedNew
   final void BaseException___init__(PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser(this.getType().getName(), args, keywords, "args");
      ap.noKeywords();
      this.args = ap.getList(0);
      if (args.length == 1) {
         this.message = args[0];
      }

   }

   public PyObject __getitem__(PyObject index) {
      return this.BaseException___getitem__(index);
   }

   final PyObject BaseException___getitem__(PyObject index) {
      Py.warnPy3k("__getitem__ not supported for exception classes in 3.x; use args attribute");
      return this.args.__getitem__(index);
   }

   public PyObject __getslice__(PyObject start, PyObject stop) {
      return this.BaseException___getslice__(start, stop);
   }

   final PyObject BaseException___getslice__(PyObject start, PyObject stop) {
      Py.warnPy3k("__getslice__ not supported for exception classes in 3.x; use args attribute");
      return this.args.__getslice__(start, stop);
   }

   public PyObject __reduce__() {
      return this.BaseException___reduce__();
   }

   final PyObject BaseException___reduce__() {
      return this.__dict__ != null ? new PyTuple(new PyObject[]{this.getType(), this.args, this.__dict__}) : new PyTuple(new PyObject[]{this.getType(), this.args});
   }

   public PyObject __setstate__(PyObject state) {
      return this.BaseException___setstate__(state);
   }

   final PyObject BaseException___setstate__(PyObject state) {
      if (state != Py.None) {
         if (!(state instanceof AbstractDict)) {
            throw Py.TypeError("state is not a dictionary");
         }

         Iterator var2 = state.asIterable().iterator();

         while(var2.hasNext()) {
            PyObject key = (PyObject)var2.next();
            this.__setattr__((PyString)key, state.__finditem__(key));
         }
      }

      return Py.None;
   }

   public PyObject __findattr_ex__(String name) {
      return this.BaseException___findattr__(name);
   }

   final PyObject BaseException___findattr__(String name) {
      if (this.__dict__ != null) {
         PyObject attr = this.__dict__.__finditem__(name);
         if (attr != null) {
            return attr;
         }
      }

      return super.__findattr_ex__(name);
   }

   public void __setattr__(String name, PyObject value) {
      this.BaseException___setattr__(name, value);
   }

   final void BaseException___setattr__(String name, PyObject value) {
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
      if (!(val instanceof PyStringMap) && !(val instanceof PyDictionary)) {
         throw Py.TypeError("__dict__ must be a dictionary");
      } else {
         this.__dict__ = val;
      }
   }

   private void ensureDict() {
      if (this.__dict__ == null) {
         this.__dict__ = new PyStringMap();
      }

   }

   public PyString __str__() {
      return this.BaseException___str__();
   }

   final PyString BaseException___str__() {
      switch (this.args.__len__()) {
         case 0:
            return Py.EmptyString;
         case 1:
            PyObject arg = this.args.__getitem__(0);
            if (arg instanceof PyString) {
               return (PyString)arg;
            }

            return arg.__str__();
         default:
            return this.args.__str__();
      }
   }

   public PyUnicode __unicode__() {
      return this.BaseException___unicode__();
   }

   final PyUnicode BaseException___unicode__() {
      PyType type = this.getType();
      PyObject[] where = new PyObject[1];
      PyObject str = type.lookup_where("__str__", where);
      if (str != null && where[0] != TYPE) {
         return str.__get__(this, type).__call__().__unicode__();
      } else {
         switch (this.args.__len__()) {
            case 0:
               return new PyUnicode("");
            case 1:
               return this.args.__getitem__(0).__unicode__();
            default:
               return this.args.__unicode__();
         }
      }
   }

   public String toString() {
      return this.BaseException_toString();
   }

   final String BaseException_toString() {
      PyObject reprSuffix = this.args.__repr__();
      String name = this.getType().fastGetName();
      int lastDot = name.lastIndexOf(46);
      if (lastDot != -1) {
         name = name.substring(lastDot + 1);
      }

      return name + reprSuffix.toString();
   }

   public void setArgs(PyObject val) {
      this.args = PyTuple.fromIterable(val);
   }

   public PyObject getMessage() {
      PyObject message;
      if (this.__dict__ != null && (message = this.__dict__.__finditem__("message")) != null) {
         return message;
      } else if (this.message == null) {
         throw Py.AttributeError("message attribute was deleted");
      } else {
         Py.DeprecationWarning("BaseException.message has been deprecated as of Python 2.6");
         return this.message;
      }
   }

   public void setMessage(PyObject value) {
      this.getDict().__setitem__("message", value);
   }

   public void delMessage() {
      if (this.__dict__ != null && (this.message = this.__dict__.__finditem__("message")) != null) {
         this.__dict__.__delitem__("message");
      }

      this.message = null;
   }

   public int traverse(Visitproc visit, Object arg) {
      int retValue;
      if (this.message != null) {
         retValue = visit.visit(this.message, arg);
         if (retValue != 0) {
            return retValue;
         }
      }

      if (this.args != null) {
         retValue = visit.visit(this.args, arg);
         if (retValue != 0) {
            return retValue;
         }
      }

      return this.__dict__ != null ? visit.visit(this.__dict__, arg) : 0;
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob != null && (ob == this.message || ob == this.args || ob == this.__dict__);
   }

   static {
      PyType.addBuilder(PyBaseException.class, new PyExposer());
      TYPE = PyType.fromClass(PyBaseException.class);
   }

   private static class BaseException___init___exposer extends PyBuiltinMethod {
      public BaseException___init___exposer(String var1) {
         super(var1);
         super.doc = "x.__init__(...) initializes x; see help(type(x)) for signature";
      }

      public BaseException___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__init__(...) initializes x; see help(type(x)) for signature";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new BaseException___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((PyBaseException)this.self).BaseException___init__(var1, var2);
         return Py.None;
      }
   }

   private static class BaseException___getitem___exposer extends PyBuiltinMethodNarrow {
      public BaseException___getitem___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__getitem__(y) <==> x[y]";
      }

      public BaseException___getitem___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__getitem__(y) <==> x[y]";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new BaseException___getitem___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyBaseException)this.self).BaseException___getitem__(var1);
      }
   }

   private static class BaseException___getslice___exposer extends PyBuiltinMethodNarrow {
      public BaseException___getslice___exposer(String var1) {
         super(var1, 3, 3);
         super.doc = "x.__getslice__(i, j) <==> x[i:j]\n           \n           Use of negative indices is not supported.";
      }

      public BaseException___getslice___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__getslice__(i, j) <==> x[i:j]\n           \n           Use of negative indices is not supported.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new BaseException___getslice___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return ((PyBaseException)this.self).BaseException___getslice__(var1, var2);
      }
   }

   private static class BaseException___reduce___exposer extends PyBuiltinMethodNarrow {
      public BaseException___reduce___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public BaseException___reduce___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new BaseException___reduce___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyBaseException)this.self).BaseException___reduce__();
      }
   }

   private static class BaseException___setstate___exposer extends PyBuiltinMethodNarrow {
      public BaseException___setstate___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public BaseException___setstate___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new BaseException___setstate___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyBaseException)this.self).BaseException___setstate__(var1);
      }
   }

   private static class BaseException___setattr___exposer extends PyBuiltinMethodNarrow {
      public BaseException___setattr___exposer(String var1) {
         super(var1, 3, 3);
         super.doc = "x.__setattr__('name', value) <==> x.name = value";
      }

      public BaseException___setattr___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__setattr__('name', value) <==> x.name = value";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new BaseException___setattr___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         ((PyBaseException)this.self).BaseException___setattr__(var1.asString(), var2);
         return Py.None;
      }
   }

   private static class BaseException___str___exposer extends PyBuiltinMethodNarrow {
      public BaseException___str___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__str__() <==> str(x)";
      }

      public BaseException___str___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__str__() <==> str(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new BaseException___str___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyBaseException)this.self).BaseException___str__();
      }
   }

   private static class BaseException___unicode___exposer extends PyBuiltinMethodNarrow {
      public BaseException___unicode___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public BaseException___unicode___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new BaseException___unicode___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyBaseException)this.self).BaseException___unicode__();
      }
   }

   private static class BaseException_toString_exposer extends PyBuiltinMethodNarrow {
      public BaseException_toString_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__repr__() <==> repr(x)";
      }

      public BaseException_toString_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__repr__() <==> repr(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new BaseException_toString_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         String var10000 = ((PyBaseException)this.self).BaseException_toString();
         return (PyObject)(var10000 == null ? Py.None : Py.newString(var10000));
      }
   }

   private static class args_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public args_descriptor() {
         super("args", PyObject.class, "");
      }

      public Object invokeGet(PyObject var1) {
         return ((PyBaseException)var1).args;
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((PyBaseException)var1).setArgs((PyObject)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class __dict___descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public __dict___descriptor() {
         super("__dict__", PyObject.class, "");
      }

      public Object invokeGet(PyObject var1) {
         return ((PyBaseException)var1).getDict();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((PyBaseException)var1).setDict((PyObject)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class message_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public message_descriptor() {
         super("message", PyObject.class, "");
      }

      public Object invokeGet(PyObject var1) {
         return ((PyBaseException)var1).getMessage();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((PyBaseException)var1).setMessage((PyObject)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public void invokeDelete(PyObject var1) {
         ((PyBaseException)var1).delMessage();
      }

      public boolean implementsDescrDelete() {
         return true;
      }
   }

   private static class exposed___new__ extends PyOverridableNew {
      public exposed___new__() {
      }

      public PyObject createOfType(boolean var1, PyObject[] var2, String[] var3) {
         PyBaseException var4 = new PyBaseException(this.for_type);
         if (var1) {
            var4.BaseException___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new PyBaseExceptionDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new BaseException___init___exposer("__init__"), new BaseException___getitem___exposer("__getitem__"), new BaseException___getslice___exposer("__getslice__"), new BaseException___reduce___exposer("__reduce__"), new BaseException___setstate___exposer("__setstate__"), new BaseException___setattr___exposer("__setattr__"), new BaseException___str___exposer("__str__"), new BaseException___unicode___exposer("__unicode__"), new BaseException_toString_exposer("__repr__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new args_descriptor(), new __dict___descriptor(), new message_descriptor()};
         super("exceptions.BaseException", PyBaseException.class, Object.class, (boolean)1, "Common base class for all exceptions", var1, var2, new exposed___new__());
      }
   }
}
