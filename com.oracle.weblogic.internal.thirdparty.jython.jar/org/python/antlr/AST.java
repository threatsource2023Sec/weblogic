package org.python.antlr;

import org.python.core.Py;
import org.python.core.PyBuiltinMethod;
import org.python.core.PyDataDescr;
import org.python.core.PyException;
import org.python.core.PyNewWrapper;
import org.python.core.PyObject;
import org.python.core.PyType;
import org.python.core.Untraversable;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposedType;

@Untraversable
@ExposedType(
   name = "_ast.AST",
   base = PyObject.class
)
public class AST extends PyObject {
   public static final PyType TYPE;

   public AST() {
   }

   public AST(PyType objtype) {
      super(objtype);
   }

   public static boolean check(int nargs, int expected, boolean takesZeroArgs) {
      if (nargs == expected) {
         return true;
      } else {
         return takesZeroArgs && nargs == 0;
      }
   }

   public static PyException unexpectedCall(int expected, String name) {
      String message = " constructor takes 0 positional arguments";
      if (expected != 0) {
         message = " constructor takes either 0 or " + expected + " arguments";
      }

      return Py.TypeError(name + message);
   }

   static {
      PyType.addBuilder(AST.class, new PyExposer());
      TYPE = PyType.fromClass(AST.class);
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[0];
         PyDataDescr[] var2 = new PyDataDescr[0];
         super("_ast.AST", AST.class, PyObject.class, (boolean)1, (String)null, var1, var2, (PyNewWrapper)null);
      }
   }
}
