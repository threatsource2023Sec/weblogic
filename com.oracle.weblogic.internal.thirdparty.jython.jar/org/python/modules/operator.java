package org.python.modules;

import java.util.Iterator;
import org.python.core.ArgParser;
import org.python.core.ClassDictInit;
import org.python.core.Py;
import org.python.core.PyBuiltinCallable;
import org.python.core.PyBuiltinMethod;
import org.python.core.PyDataDescr;
import org.python.core.PyIgnoreMethodTag;
import org.python.core.PyNewWrapper;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.PyType;
import org.python.core.PyUnicode;
import org.python.core.Traverseproc;
import org.python.core.Untraversable;
import org.python.core.Visitproc;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposeAsSuperclass;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@Untraversable
public class operator extends PyObject implements ClassDictInit {
   public static PyString __doc__ = new PyString("Operator interface.\n\nThis module exports a set of functions implemented in C corresponding\nto the intrinsic operators of Python.  For example, operator.add(x, y)\nis equivalent to the expression x+y.  The function names are those\nused for special class methods; variants without leading and trailing\n'__' are also provided for convenience.\n");

   public static void classDictInit(PyObject dict) throws PyIgnoreMethodTag {
      dict.__setitem__((String)"__add__", new OperatorFunctions("__add__", 0, 2));
      dict.__setitem__((String)"add", new OperatorFunctions("add", 0, 2));
      dict.__setitem__((String)"__concat__", new OperatorFunctions("__concat__", 0, 2));
      dict.__setitem__((String)"concat", new OperatorFunctions("concat", 0, 2));
      dict.__setitem__((String)"__and__", new OperatorFunctions("__and__", 1, 2));
      dict.__setitem__((String)"and_", new OperatorFunctions("and_", 1, 2));
      dict.__setitem__((String)"__div__", new OperatorFunctions("__div__", 2, 2));
      dict.__setitem__((String)"div", new OperatorFunctions("div", 2, 2));
      dict.__setitem__((String)"__lshift__", new OperatorFunctions("__lshift__", 3, 2));
      dict.__setitem__((String)"lshift", new OperatorFunctions("lshift", 3, 2));
      dict.__setitem__((String)"__mod__", new OperatorFunctions("__mod__", 4, 2));
      dict.__setitem__((String)"mod", new OperatorFunctions("mod", 4, 2));
      dict.__setitem__((String)"__mul__", new OperatorFunctions("__mul__", 5, 2));
      dict.__setitem__((String)"mul", new OperatorFunctions("mul", 5, 2));
      dict.__setitem__((String)"__repeat__", new OperatorFunctions("__repeat__", 5, 2));
      dict.__setitem__((String)"repeat", new OperatorFunctions("repeat", 5, 2));
      dict.__setitem__((String)"__or__", new OperatorFunctions("__or__", 6, 2));
      dict.__setitem__((String)"or_", new OperatorFunctions("or_", 6, 2));
      dict.__setitem__((String)"__rshift__", new OperatorFunctions("__rshift__", 7, 2));
      dict.__setitem__((String)"rshift", new OperatorFunctions("rshift", 7, 2));
      dict.__setitem__((String)"__sub__", new OperatorFunctions("__sub__", 8, 2));
      dict.__setitem__((String)"sub", new OperatorFunctions("sub", 8, 2));
      dict.__setitem__((String)"__xor__", new OperatorFunctions("__xor__", 9, 2));
      dict.__setitem__((String)"xor", new OperatorFunctions("xor", 9, 2));
      dict.__setitem__((String)"__abs__", new OperatorFunctions("__abs__", 10, 1));
      dict.__setitem__((String)"abs", new OperatorFunctions("abs", 10, 1));
      dict.__setitem__((String)"__inv__", new OperatorFunctions("__inv__", 11, 1));
      dict.__setitem__((String)"inv", new OperatorFunctions("inv", 11, 1));
      dict.__setitem__((String)"__neg__", new OperatorFunctions("__neg__", 12, 1));
      dict.__setitem__((String)"neg", new OperatorFunctions("neg", 12, 1));
      dict.__setitem__((String)"__not__", new OperatorFunctions("__not__", 13, 1));
      dict.__setitem__((String)"not_", new OperatorFunctions("not_", 13, 1));
      dict.__setitem__((String)"__pos__", new OperatorFunctions("__pos__", 14, 1));
      dict.__setitem__((String)"pos", new OperatorFunctions("pos", 14, 1));
      dict.__setitem__((String)"truth", new OperatorFunctions("truth", 15, 1));
      dict.__setitem__((String)"isCallable", new OperatorFunctions("isCallable", 16, 1));
      dict.__setitem__((String)"isMappingType", new OperatorFunctions("isMappingType", 17, 1));
      dict.__setitem__((String)"isNumberType", new OperatorFunctions("isNumberType", 18, 1));
      dict.__setitem__((String)"isSequenceType", new OperatorFunctions("isSequenceType", 19, 1));
      dict.__setitem__((String)"contains", new OperatorFunctions("contains", 20, 2));
      dict.__setitem__((String)"__contains__", new OperatorFunctions("__contains__", 20, 2));
      dict.__setitem__((String)"sequenceIncludes", new OperatorFunctions("sequenceIncludes", 20, 2));
      dict.__setitem__((String)"__delitem__", new OperatorFunctions("__delitem__", 21, 2));
      dict.__setitem__((String)"delitem", new OperatorFunctions("delitem", 21, 2));
      dict.__setitem__((String)"__delslice__", new OperatorFunctions("__delslice__", 22, 3));
      dict.__setitem__((String)"delslice", new OperatorFunctions("delslice", 22, 3));
      dict.__setitem__((String)"__getitem__", new OperatorFunctions("__getitem__", 23, 2));
      dict.__setitem__((String)"getitem", new OperatorFunctions("getitem", 23, 2));
      dict.__setitem__((String)"__getslice__", new OperatorFunctions("__getslice__", 24, 3));
      dict.__setitem__((String)"getslice", new OperatorFunctions("getslice", 24, 3));
      dict.__setitem__((String)"__setitem__", new OperatorFunctions("__setitem__", 25, 3));
      dict.__setitem__((String)"setitem", new OperatorFunctions("setitem", 25, 3));
      dict.__setitem__((String)"__setslice__", new OperatorFunctions("__setslice__", 26, 4));
      dict.__setitem__((String)"setslice", new OperatorFunctions("setslice", 26, 4));
      dict.__setitem__((String)"ge", new OperatorFunctions("ge", 27, 2));
      dict.__setitem__((String)"__ge__", new OperatorFunctions("__ge__", 27, 2));
      dict.__setitem__((String)"le", new OperatorFunctions("le", 28, 2));
      dict.__setitem__((String)"__le__", new OperatorFunctions("__le__", 28, 2));
      dict.__setitem__((String)"eq", new OperatorFunctions("eq", 29, 2));
      dict.__setitem__((String)"__eq__", new OperatorFunctions("__eq__", 29, 2));
      dict.__setitem__((String)"floordiv", new OperatorFunctions("floordiv", 30, 2));
      dict.__setitem__((String)"__floordiv__", new OperatorFunctions("__floordiv__", 30, 2));
      dict.__setitem__((String)"gt", new OperatorFunctions("gt", 31, 2));
      dict.__setitem__((String)"__gt__", new OperatorFunctions("__gt__", 31, 2));
      dict.__setitem__((String)"invert", new OperatorFunctions("invert", 32, 1));
      dict.__setitem__((String)"__invert__", new OperatorFunctions("__invert__", 32, 1));
      dict.__setitem__((String)"lt", new OperatorFunctions("lt", 33, 2));
      dict.__setitem__((String)"__lt__", new OperatorFunctions("__lt__", 33, 2));
      dict.__setitem__((String)"ne", new OperatorFunctions("ne", 34, 2));
      dict.__setitem__((String)"__ne__", new OperatorFunctions("__ne__", 34, 2));
      dict.__setitem__((String)"truediv", new OperatorFunctions("truediv", 35, 2));
      dict.__setitem__((String)"__truediv__", new OperatorFunctions("__truediv__", 35, 2));
      dict.__setitem__((String)"pow", new OperatorFunctions("pow", 36, 2));
      dict.__setitem__((String)"__pow__", new OperatorFunctions("pow", 36, 2));
      dict.__setitem__((String)"is_", new OperatorFunctions("is_", 37, 2));
      dict.__setitem__((String)"is_not", new OperatorFunctions("is_not", 38, 2));
      dict.__setitem__((String)"__iadd__", new OperatorFunctions("__iadd__", 39, 2));
      dict.__setitem__((String)"iadd", new OperatorFunctions("iadd", 39, 2));
      dict.__setitem__((String)"__iconcat__", new OperatorFunctions("__iconcat__", 39, 2));
      dict.__setitem__((String)"iconcat", new OperatorFunctions("iconcat", 39, 2));
      dict.__setitem__((String)"__iand__", new OperatorFunctions("__iand__", 40, 2));
      dict.__setitem__((String)"iand", new OperatorFunctions("iand", 40, 2));
      dict.__setitem__((String)"__idiv__", new OperatorFunctions("__idiv__", 41, 2));
      dict.__setitem__((String)"idiv", new OperatorFunctions("idiv", 41, 2));
      dict.__setitem__((String)"__ifloordiv__", new OperatorFunctions("__ifloordiv__", 42, 2));
      dict.__setitem__((String)"ifloordiv", new OperatorFunctions("ifloordiv", 42, 2));
      dict.__setitem__((String)"__ilshift__", new OperatorFunctions("__ilshift__", 43, 2));
      dict.__setitem__((String)"ilshift", new OperatorFunctions("ilshift", 43, 2));
      dict.__setitem__((String)"__imod__", new OperatorFunctions("__imod__", 44, 2));
      dict.__setitem__((String)"imod", new OperatorFunctions("imod", 44, 2));
      dict.__setitem__((String)"__imul__", new OperatorFunctions("__imul__", 45, 2));
      dict.__setitem__((String)"imul", new OperatorFunctions("imul", 45, 2));
      dict.__setitem__((String)"__irepeat__", new OperatorFunctions("__irepeat__", 45, 2));
      dict.__setitem__((String)"irepeat", new OperatorFunctions("irepeat", 45, 2));
      dict.__setitem__((String)"__ior__", new OperatorFunctions("__ior__", 46, 2));
      dict.__setitem__((String)"ior", new OperatorFunctions("ior", 46, 2));
      dict.__setitem__((String)"__ipow__", new OperatorFunctions("__ipow__", 47, 2));
      dict.__setitem__((String)"ipow", new OperatorFunctions("ipow", 47, 2));
      dict.__setitem__((String)"__irshift__", new OperatorFunctions("__irshift__", 48, 2));
      dict.__setitem__((String)"irshift", new OperatorFunctions("irshift", 48, 2));
      dict.__setitem__((String)"__isub__", new OperatorFunctions("__isub__", 49, 2));
      dict.__setitem__((String)"isub", new OperatorFunctions("isub", 49, 2));
      dict.__setitem__((String)"__itruediv__", new OperatorFunctions("__itruediv__", 50, 2));
      dict.__setitem__((String)"itruediv", new OperatorFunctions("itruediv", 50, 2));
      dict.__setitem__((String)"__ixor__", new OperatorFunctions("__ixor__", 51, 2));
      dict.__setitem__((String)"ixor", new OperatorFunctions("ixor", 51, 2));
      dict.__setitem__((String)"__index__", new OperatorFunctions("__index__", 52, 1));
      dict.__setitem__((String)"index", new OperatorFunctions("index", 52, 1));
      dict.__setitem__((String)"attrgetter", operator.PyAttrGetter.TYPE);
      dict.__setitem__((String)"itemgetter", operator.PyItemGetter.TYPE);
      dict.__setitem__((String)"methodcaller", operator.PyMethodCaller.TYPE);
   }

   public static int countOf(PyObject seq, PyObject item) {
      int count = 0;
      Iterator var3 = seq.asIterable().iterator();

      while(var3.hasNext()) {
         PyObject tmp = (PyObject)var3.next();
         if (item._eq(tmp).__nonzero__()) {
            ++count;
         }
      }

      return count;
   }

   public static int indexOf(PyObject seq, PyObject item) {
      int i = 0;
      PyObject iter = seq.__iter__();

      for(PyObject tmp = null; (tmp = iter.__iternext__()) != null; ++i) {
         if (item._eq(tmp).__nonzero__()) {
            return i;
         }
      }

      throw Py.ValueError("sequence.index(x): x not in list");
   }

   private static String ensureStringAttribute(PyObject name) {
      String nameStr;
      if (name instanceof PyUnicode) {
         nameStr = ((PyUnicode)name).encode();
      } else {
         if (!(name instanceof PyString)) {
            throw Py.TypeError(String.format("attribute name must be string, not '%.200s'", name.getType().fastGetName()));
         }

         nameStr = name.asString();
      }

      return nameStr;
   }

   @ExposedType(
      name = "operator.methodcaller",
      isBaseType = false
   )
   static class PyMethodCaller extends PyObject implements Traverseproc {
      public static final PyType TYPE;
      public String name;
      public PyObject[] args;
      public String[] keywords;
      public static PyString __doc__;

      public PyMethodCaller(String name, PyObject[] args, String[] keywords) {
         this.name = name;
         this.args = args;
         this.keywords = keywords;
      }

      @ExposedNew
      static final PyObject methodcaller___new__(PyNewWrapper new_, boolean init, PyType subtype, PyObject[] args, String[] keywords) {
         if (args.length == 0) {
            throw Py.TypeError("methodcaller needs at least one argument, the method name");
         } else {
            String nameStr = operator.ensureStringAttribute(args[0]);
            PyObject[] newArgs = new PyObject[args.length - 1];
            System.arraycopy(args, 1, newArgs, 0, args.length - 1);
            return new PyMethodCaller(nameStr, newArgs, keywords);
         }
      }

      public PyObject __call__(PyObject[] args, String[] keywords) {
         return this.methodcaller___call__(args, keywords);
      }

      final PyObject methodcaller___call__(PyObject[] args, String[] keywords) {
         if (args.length > 1) {
            throw Py.TypeError("methodcaller expected 1 arguments, got " + args.length);
         } else {
            ArgParser ap = new ArgParser("methodcaller", args, Py.NoKeywords, "obj");
            PyObject obj = ap.getPyObject(0);
            return obj.invoke(this.name, this.args, this.keywords);
         }
      }

      public int traverse(Visitproc visit, Object arg) {
         if (this.args != null) {
            PyObject[] var3 = this.args;
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               PyObject ob = var3[var5];
               if (ob != null) {
                  int retVal = visit.visit(ob, arg);
                  if (retVal != 0) {
                     return retVal;
                  }
               }
            }
         }

         return 0;
      }

      public boolean refersDirectlyTo(PyObject ob) {
         if (ob != null && this.args != null) {
            PyObject[] var2 = this.args;
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               PyObject obj = var2[var4];
               if (obj == ob) {
                  return true;
               }
            }

            return false;
         } else {
            return false;
         }
      }

      static {
         PyType.addBuilder(PyMethodCaller.class, new PyExposer());
         TYPE = PyType.fromClass(PyMethodCaller.class);
         __doc__ = new PyString("methodcaller(name, ...) --> methodcaller object\n\nReturn a callable object that calls the given method on its operand.\nAfter, f = methodcaller('name'), the call f(r) returns r.name().\nAfter, g = methodcaller('name', 'date', foo=1), the call g(r) returns\nr.name('date', foo=1)");
      }

      private static class methodcaller___call___exposer extends PyBuiltinMethod {
         public methodcaller___call___exposer(String var1) {
            super(var1);
            super.doc = "";
         }

         public methodcaller___call___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
            super(var1, var2, var3);
            super.doc = "";
         }

         public PyBuiltinCallable bind(PyObject var1) {
            return new methodcaller___call___exposer(this.getType(), var1, this.info);
         }

         public PyObject __call__(PyObject[] var1, String[] var2) {
            return ((PyMethodCaller)this.self).methodcaller___call__(var1, var2);
         }
      }

      private static class __doc___descriptor extends PyDataDescr implements ExposeAsSuperclass {
         public __doc___descriptor() {
            super("__doc__", PyString.class, (String)null);
         }

         public Object invokeGet(PyObject var1) {
            return ((PyMethodCaller)var1).__doc__;
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

      private static class exposed___new__ extends PyNewWrapper {
         public exposed___new__() {
         }

         public PyObject new_impl(boolean var1, PyType var2, PyObject[] var3, String[] var4) {
            return operator.PyMethodCaller.methodcaller___new__(this, var1, var2, var3, var4);
         }
      }

      private static class PyExposer extends BaseTypeBuilder {
         public PyExposer() {
            PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new methodcaller___call___exposer("__call__")};
            PyDataDescr[] var2 = new PyDataDescr[]{new __doc___descriptor()};
            super("operator.methodcaller", PyMethodCaller.class, Object.class, (boolean)0, (String)null, var1, var2, new exposed___new__());
         }
      }
   }

   @ExposedType(
      name = "operator.itemgetter",
      isBaseType = false
   )
   static class PyItemGetter extends PyObject implements Traverseproc {
      public static final PyType TYPE;
      public PyObject[] items;

      public PyItemGetter(PyObject[] items) {
         this.items = items;
      }

      @ExposedNew
      static final PyObject itemgetter___new__(PyNewWrapper new_, boolean init, PyType subtype, PyObject[] args, String[] keywords) {
         ArgParser ap = new ArgParser("itemgetter", args, keywords, "attr");
         ap.noKeywords();
         ap.getPyObject(0);
         return new PyItemGetter(args);
      }

      public PyObject __call__(PyObject[] args, String[] keywords) {
         return this.itemgetter___call__(args, keywords);
      }

      final PyObject itemgetter___call__(PyObject[] args, String[] keywords) {
         ArgParser ap = new ArgParser("itemgetter", args, Py.NoKeywords, "obj");
         PyObject obj = ap.getPyObject(0);
         if (this.items.length == 1) {
            return obj.__getitem__(this.items[0]);
         } else {
            PyObject[] result = new PyObject[this.items.length];
            int i = 0;
            PyObject[] var7 = this.items;
            int var8 = var7.length;

            for(int var9 = 0; var9 < var8; ++var9) {
               PyObject item = var7[var9];
               result[i++] = obj.__getitem__(item);
            }

            return new PyTuple(result);
         }
      }

      public int traverse(Visitproc visit, Object arg) {
         if (this.items != null) {
            PyObject[] var3 = this.items;
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               PyObject ob = var3[var5];
               if (ob != null) {
                  int retVal = visit.visit(ob, arg);
                  if (retVal != 0) {
                     return retVal;
                  }
               }
            }
         }

         return 0;
      }

      public boolean refersDirectlyTo(PyObject ob) {
         if (ob != null && this.items != null) {
            PyObject[] var2 = this.items;
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               PyObject obj = var2[var4];
               if (obj == ob) {
                  return true;
               }
            }

            return false;
         } else {
            return false;
         }
      }

      static {
         PyType.addBuilder(PyItemGetter.class, new PyExposer());
         TYPE = PyType.fromClass(PyItemGetter.class);
      }

      private static class itemgetter___call___exposer extends PyBuiltinMethod {
         public itemgetter___call___exposer(String var1) {
            super(var1);
            super.doc = "";
         }

         public itemgetter___call___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
            super(var1, var2, var3);
            super.doc = "";
         }

         public PyBuiltinCallable bind(PyObject var1) {
            return new itemgetter___call___exposer(this.getType(), var1, this.info);
         }

         public PyObject __call__(PyObject[] var1, String[] var2) {
            return ((PyItemGetter)this.self).itemgetter___call__(var1, var2);
         }
      }

      private static class exposed___new__ extends PyNewWrapper {
         public exposed___new__() {
         }

         public PyObject new_impl(boolean var1, PyType var2, PyObject[] var3, String[] var4) {
            return operator.PyItemGetter.itemgetter___new__(this, var1, var2, var3, var4);
         }
      }

      private static class PyExposer extends BaseTypeBuilder {
         public PyExposer() {
            PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new itemgetter___call___exposer("__call__")};
            PyDataDescr[] var2 = new PyDataDescr[0];
            super("operator.itemgetter", PyItemGetter.class, Object.class, (boolean)0, (String)null, var1, var2, new exposed___new__());
         }
      }
   }

   @ExposedType(
      name = "operator.attrgetter",
      isBaseType = false
   )
   static class PyAttrGetter extends PyObject implements Traverseproc {
      public static final PyType TYPE;
      public PyObject[] attrs;

      public PyAttrGetter(PyObject[] attrs) {
         this.attrs = attrs;
      }

      @ExposedNew
      static final PyObject attrgetter___new__(PyNewWrapper new_, boolean init, PyType subtype, PyObject[] args, String[] keywords) {
         ArgParser ap = new ArgParser("attrgetter", args, keywords, "attr");
         ap.noKeywords();
         ap.getPyObject(0);
         return new PyAttrGetter(args);
      }

      public PyObject __call__(PyObject[] args, String[] keywords) {
         return this.attrgetter___call__(args, keywords);
      }

      final PyObject attrgetter___call__(PyObject[] args, String[] keywords) {
         ArgParser ap = new ArgParser("attrgetter", args, Py.NoKeywords, "obj");
         PyObject obj = ap.getPyObject(0);
         if (this.attrs.length == 1) {
            return this.getattr(obj, this.attrs[0]);
         } else {
            PyObject[] result = new PyObject[this.attrs.length];
            int i = 0;
            PyObject[] var7 = this.attrs;
            int var8 = var7.length;

            for(int var9 = 0; var9 < var8; ++var9) {
               PyObject attr = var7[var9];
               result[i++] = this.getattr(obj, attr);
            }

            return new PyTuple(result);
         }
      }

      private PyObject getattr(PyObject obj, PyObject name) {
         String nameStr = operator.ensureStringAttribute(name);
         String[] components = nameStr.split("\\.");
         String[] var5 = components;
         int var6 = components.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            String component = var5[var7];
            obj = obj.__getattr__(component.intern());
         }

         return obj;
      }

      public int traverse(Visitproc visit, Object arg) {
         if (this.attrs != null) {
            PyObject[] var3 = this.attrs;
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               PyObject ob = var3[var5];
               if (ob != null) {
                  int retVal = visit.visit(ob, arg);
                  if (retVal != 0) {
                     return retVal;
                  }
               }
            }
         }

         return 0;
      }

      public boolean refersDirectlyTo(PyObject ob) {
         if (ob != null && this.attrs != null) {
            PyObject[] var2 = this.attrs;
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               PyObject obj = var2[var4];
               if (obj == ob) {
                  return true;
               }
            }

            return false;
         } else {
            return false;
         }
      }

      static {
         PyType.addBuilder(PyAttrGetter.class, new PyExposer());
         TYPE = PyType.fromClass(PyAttrGetter.class);
      }

      private static class attrgetter___call___exposer extends PyBuiltinMethod {
         public attrgetter___call___exposer(String var1) {
            super(var1);
            super.doc = "";
         }

         public attrgetter___call___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
            super(var1, var2, var3);
            super.doc = "";
         }

         public PyBuiltinCallable bind(PyObject var1) {
            return new attrgetter___call___exposer(this.getType(), var1, this.info);
         }

         public PyObject __call__(PyObject[] var1, String[] var2) {
            return ((PyAttrGetter)this.self).attrgetter___call__(var1, var2);
         }
      }

      private static class exposed___new__ extends PyNewWrapper {
         public exposed___new__() {
         }

         public PyObject new_impl(boolean var1, PyType var2, PyObject[] var3, String[] var4) {
            return operator.PyAttrGetter.attrgetter___new__(this, var1, var2, var3, var4);
         }
      }

      private static class PyExposer extends BaseTypeBuilder {
         public PyExposer() {
            PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new attrgetter___call___exposer("__call__")};
            PyDataDescr[] var2 = new PyDataDescr[0];
            super("operator.attrgetter", PyAttrGetter.class, Object.class, (boolean)0, (String)null, var1, var2, new exposed___new__());
         }
      }
   }
}
