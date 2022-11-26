package org.python.core;

import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@Untraversable
@ExposedType(
   name = "bool",
   isBaseType = false,
   doc = "bool(x) -> bool\n\nReturns True when the argument x is true, False otherwise.\nThe builtins True and False are the only two instances of the class bool.\nThe class bool is a subclass of the class int, and cannot be subclassed."
)
public class PyBoolean extends PyInteger {
   public static final PyType TYPE;
   private final boolean value;

   public boolean getBooleanValue() {
      return this.value;
   }

   public int getValue() {
      return this.getBooleanValue() ? 1 : 0;
   }

   public PyBoolean(boolean value) {
      super(TYPE, value ? 1 : 0);
      this.value = value;
   }

   @ExposedNew
   public static PyObject bool_new(PyNewWrapper new_, boolean init, PyType subtype, PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("bool", args, keywords, new String[]{"x"}, 0);
      PyObject obj = ap.getPyObject(0, (PyObject)null);
      if (obj == null) {
         return Py.False;
      } else {
         return obj.__nonzero__() ? Py.True : Py.False;
      }
   }

   public String toString() {
      return this.bool_toString();
   }

   final String bool_toString() {
      return this.getBooleanValue() ? "True" : "False";
   }

   public int hashCode() {
      return this.bool___hash__();
   }

   final int bool___hash__() {
      return this.getBooleanValue() ? 1 : 0;
   }

   public boolean __nonzero__() {
      return this.bool___nonzero__();
   }

   final boolean bool___nonzero__() {
      return this.getBooleanValue();
   }

   public Object __tojava__(Class c) {
      if (c != Boolean.TYPE && c != Boolean.class && c != Object.class) {
         if (c != Integer.TYPE && c != Number.class && c != Integer.class) {
            if (c != Byte.TYPE && c != Byte.class) {
               if (c != Short.TYPE && c != Short.class) {
                  if (c != Long.TYPE && c != Long.class) {
                     if (c != Float.TYPE && c != Float.class) {
                        return c != Double.TYPE && c != Double.class ? super.__tojava__(c) : (double)this.getValue();
                     } else {
                        return (float)this.getValue();
                     }
                  } else {
                     return (long)this.getValue();
                  }
               } else {
                  return (short)this.getValue();
               }
            } else {
               return (byte)this.getValue();
            }
         } else {
            return this.getValue();
         }
      } else {
         return this.getBooleanValue();
      }
   }

   public PyObject __and__(PyObject right) {
      return this.bool___and__(right);
   }

   final PyObject bool___and__(PyObject right) {
      if (right instanceof PyBoolean) {
         return Py.newBoolean(this.getBooleanValue() & ((PyBoolean)right).getBooleanValue());
      } else {
         return right instanceof PyInteger ? Py.newInteger(this.getValue() & ((PyInteger)right).getValue()) : null;
      }
   }

   public PyObject __xor__(PyObject right) {
      return this.bool___xor__(right);
   }

   final PyObject bool___xor__(PyObject right) {
      if (right instanceof PyBoolean) {
         return Py.newBoolean(this.getBooleanValue() ^ ((PyBoolean)right).getBooleanValue());
      } else {
         return right instanceof PyInteger ? Py.newInteger(this.getValue() ^ ((PyInteger)right).getValue()) : null;
      }
   }

   public PyObject __or__(PyObject right) {
      return this.bool___or__(right);
   }

   final PyObject bool___or__(PyObject right) {
      if (right instanceof PyBoolean) {
         return Py.newBoolean(this.getBooleanValue() | ((PyBoolean)right).getBooleanValue());
      } else {
         return right instanceof PyInteger ? Py.newInteger(this.getValue() | ((PyInteger)right).getValue()) : null;
      }
   }

   public PyObject __neg__() {
      return this.bool___neg__();
   }

   final PyObject bool___neg__() {
      return Py.newInteger(this.getBooleanValue() ? -1 : 0);
   }

   public PyObject __pos__() {
      return this.bool___pos__();
   }

   final PyObject bool___pos__() {
      return Py.newInteger(this.getValue());
   }

   public PyObject __abs__() {
      return this.bool___abs__();
   }

   final PyObject bool___abs__() {
      return Py.newInteger(this.getValue());
   }

   static {
      PyType.addBuilder(PyBoolean.class, new PyExposer());
      TYPE = PyType.fromClass(PyBoolean.class);
   }

   private static class bool_toString_exposer extends PyBuiltinMethodNarrow {
      public bool_toString_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__str__() <==> str(x)";
      }

      public bool_toString_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__str__() <==> str(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bool_toString_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         String var10000 = ((PyBoolean)this.self).bool_toString();
         return (PyObject)(var10000 == null ? Py.None : Py.newString(var10000));
      }
   }

   private static class bool___hash___exposer extends PyBuiltinMethodNarrow {
      public bool___hash___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__hash__() <==> hash(x)";
      }

      public bool___hash___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__hash__() <==> hash(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bool___hash___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newInteger(((PyBoolean)this.self).bool___hash__());
      }
   }

   private static class bool___nonzero___exposer extends PyBuiltinMethodNarrow {
      public bool___nonzero___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__nonzero__() <==> x != 0";
      }

      public bool___nonzero___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__nonzero__() <==> x != 0";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bool___nonzero___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newBoolean(((PyBoolean)this.self).bool___nonzero__());
      }
   }

   private static class bool___and___exposer extends PyBuiltinMethodNarrow {
      public bool___and___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__and__(y) <==> x&y";
      }

      public bool___and___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__and__(y) <==> x&y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bool___and___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyBoolean)this.self).bool___and__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class bool___xor___exposer extends PyBuiltinMethodNarrow {
      public bool___xor___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__xor__(y) <==> x^y";
      }

      public bool___xor___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__xor__(y) <==> x^y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bool___xor___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyBoolean)this.self).bool___xor__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class bool___or___exposer extends PyBuiltinMethodNarrow {
      public bool___or___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__or__(y) <==> x|y";
      }

      public bool___or___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__or__(y) <==> x|y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bool___or___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyBoolean)this.self).bool___or__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class bool___neg___exposer extends PyBuiltinMethodNarrow {
      public bool___neg___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__neg__() <==> -x";
      }

      public bool___neg___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__neg__() <==> -x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bool___neg___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyBoolean)this.self).bool___neg__();
      }
   }

   private static class bool___pos___exposer extends PyBuiltinMethodNarrow {
      public bool___pos___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__pos__() <==> +x";
      }

      public bool___pos___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__pos__() <==> +x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bool___pos___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyBoolean)this.self).bool___pos__();
      }
   }

   private static class bool___abs___exposer extends PyBuiltinMethodNarrow {
      public bool___abs___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__abs__() <==> abs(x)";
      }

      public bool___abs___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__abs__() <==> abs(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bool___abs___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyBoolean)this.self).bool___abs__();
      }
   }

   private static class exposed___new__ extends PyNewWrapper {
      public exposed___new__() {
      }

      public PyObject new_impl(boolean var1, PyType var2, PyObject[] var3, String[] var4) {
         return PyBoolean.bool_new(this, var1, var2, var3, var4);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new bool_toString_exposer("__str__"), new bool_toString_exposer("__repr__"), new bool___hash___exposer("__hash__"), new bool___nonzero___exposer("__nonzero__"), new bool___and___exposer("__and__"), new bool___xor___exposer("__xor__"), new bool___or___exposer("__or__"), new bool___neg___exposer("__neg__"), new bool___pos___exposer("__pos__"), new bool___abs___exposer("__abs__")};
         PyDataDescr[] var2 = new PyDataDescr[0];
         super("bool", PyBoolean.class, Object.class, (boolean)0, "bool(x) -> bool\n\nReturns True when the argument x is true, False otherwise.\nThe builtins True and False are the only two instances of the class bool.\nThe class bool is a subclass of the class int, and cannot be subclassed.", var1, var2, new exposed___new__());
      }
   }
}
