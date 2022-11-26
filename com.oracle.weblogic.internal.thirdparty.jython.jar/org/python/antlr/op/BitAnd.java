package org.python.antlr.op;

import org.python.antlr.PythonTree;
import org.python.antlr.base.operator;
import org.python.core.Py;
import org.python.core.PyBuiltinCallable;
import org.python.core.PyBuiltinMethod;
import org.python.core.PyBuiltinMethodNarrow;
import org.python.core.PyDataDescr;
import org.python.core.PyObject;
import org.python.core.PyOverridableNew;
import org.python.core.PyString;
import org.python.core.PyType;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposeAsSuperclass;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@ExposedType(
   name = "_ast.BitAnd",
   base = operator.class
)
public class BitAnd extends PythonTree {
   public static final PyType TYPE;
   private static final PyString[] fields;
   private static final PyString[] attributes;

   public BitAnd() {
   }

   public BitAnd(PyType subType) {
      super(subType);
   }

   @ExposedNew
   public void BitAnd___init__(PyObject[] args, String[] keywords) {
   }

   public PyString[] get_fields() {
      return fields;
   }

   public PyString[] get_attributes() {
      return attributes;
   }

   public PyObject __int__() {
      return this.BitAnd___int__();
   }

   final PyObject BitAnd___int__() {
      return Py.newInteger(11);
   }

   static {
      PyType.addBuilder(BitAnd.class, new PyExposer());
      TYPE = PyType.fromClass(BitAnd.class);
      fields = new PyString[0];
      attributes = new PyString[0];
   }

   private static class BitAnd___init___exposer extends PyBuiltinMethod {
      public BitAnd___init___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public BitAnd___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new BitAnd___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((BitAnd)this.self).BitAnd___init__(var1, var2);
         return Py.None;
      }
   }

   private static class __int___exposer extends PyBuiltinMethodNarrow {
      public __int___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public __int___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new __int___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((BitAnd)this.self).__int__();
      }
   }

   private static class _fields_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public _fields_descriptor() {
         super("_fields", PyString[].class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((BitAnd)var1).get_fields();
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

   private static class _attributes_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public _attributes_descriptor() {
         super("_attributes", PyString[].class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((BitAnd)var1).get_attributes();
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
         BitAnd var4 = new BitAnd(this.for_type);
         if (var1) {
            var4.BitAnd___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new BitAndDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new BitAnd___init___exposer("__init__"), new __int___exposer("__int__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new _fields_descriptor(), new _attributes_descriptor()};
         super("_ast.BitAnd", BitAnd.class, operator.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
      }
   }
}
