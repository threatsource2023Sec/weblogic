package org.python.antlr.base;

import org.python.antlr.AST;
import org.python.antlr.PythonTree;
import org.python.antlr.runtime.Token;
import org.python.core.PyBuiltinMethod;
import org.python.core.PyDataDescr;
import org.python.core.PyNewWrapper;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PyType;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposeAsSuperclass;
import org.python.expose.ExposedType;

@ExposedType(
   name = "_ast.operator",
   base = AST.class
)
public abstract class operator extends PythonTree {
   public static final PyType TYPE;
   private static final PyString[] fields;
   private static final PyString[] attributes;

   public PyString[] get_fields() {
      return fields;
   }

   public PyString[] get_attributes() {
      return attributes;
   }

   public operator() {
   }

   public operator(PyType subType) {
   }

   public operator(int ttype, Token token) {
      super(ttype, token);
   }

   public operator(Token token) {
      super(token);
   }

   public operator(PythonTree node) {
      super(node);
   }

   static {
      PyType.addBuilder(operator.class, new PyExposer());
      TYPE = PyType.fromClass(operator.class);
      fields = new PyString[0];
      attributes = new PyString[]{new PyString("lineno"), new PyString("col_offset")};
   }

   private static class _fields_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public _fields_descriptor() {
         super("_fields", PyString[].class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((operator)var1).get_fields();
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
         return ((operator)var1).get_attributes();
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
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[0];
         PyDataDescr[] var2 = new PyDataDescr[]{new _fields_descriptor(), new _attributes_descriptor()};
         super("_ast.operator", operator.class, AST.class, (boolean)1, (String)null, var1, var2, (PyNewWrapper)null);
      }
   }
}
