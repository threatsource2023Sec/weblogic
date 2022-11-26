package org.python.core;

import java.io.Serializable;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposedType;

@Untraversable
@ExposedType(
   name = "ellipsis",
   base = PyObject.class,
   isBaseType = false
)
public class PyEllipsis extends PySingleton implements Serializable {
   public static final PyType TYPE;

   PyEllipsis() {
      super("Ellipsis");
   }

   private Object writeReplace() {
      return new Py.SingletonResolver("Ellipsis");
   }

   static {
      PyType.addBuilder(PyEllipsis.class, new PyExposer());
      TYPE = PyType.fromClass(PyEllipsis.class);
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[0];
         PyDataDescr[] var2 = new PyDataDescr[0];
         super("ellipsis", PyEllipsis.class, PyObject.class, (boolean)0, (String)null, var1, var2, (PyNewWrapper)null);
      }
   }
}
