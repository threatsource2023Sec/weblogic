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
   name = "_ast.slice",
   base = AST.class
)
public abstract class slice extends PythonTree {
   public static final PyType TYPE;
   private static final PyString[] fields;
   private static final PyString[] attributes;

   public PyString[] get_fields() {
      return fields;
   }

   public PyString[] get_attributes() {
      return attributes;
   }

   public slice() {
   }

   public slice(PyType subType) {
   }

   public slice(int ttype, Token token) {
      super(ttype, token);
   }

   public slice(Token token) {
      super(token);
   }

   public slice(PythonTree node) {
      super(node);
   }

   static {
      PyType.addBuilder(slice.class, new PyExposer());
      TYPE = PyType.fromClass(slice.class);
      fields = new PyString[0];
      attributes = new PyString[0];
   }

   private static class _fields_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public _fields_descriptor() {
         super("_fields", PyString[].class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((slice)var1).get_fields();
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
         return ((slice)var1).get_attributes();
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
         super("_ast.slice", slice.class, AST.class, (boolean)1, (String)null, var1, var2, (PyNewWrapper)null);
      }
   }
}
