package org.python.core;

import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposedType;

@ExposedType(
   name = "basestring",
   base = PyObject.class,
   doc = "Type basestring cannot be instantiated; it is the base for str and unicode."
)
public abstract class PyBaseString extends PySequence implements CharSequence {
   public static final PyType TYPE;

   protected PyBaseString(PyType type) {
      super(type);
   }

   public char charAt(int index) {
      return this.toString().charAt(index);
   }

   public int length() {
      return this.toString().length();
   }

   public CharSequence subSequence(int start, int end) {
      return this.toString().subSequence(start, end);
   }

   static {
      PyType.addBuilder(PyBaseString.class, new PyExposer());
      TYPE = PyType.fromClass(PyBaseString.class);
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[0];
         PyDataDescr[] var2 = new PyDataDescr[0];
         super("basestring", PyBaseString.class, PyObject.class, (boolean)1, "Type basestring cannot be instantiated; it is the base for str and unicode.", var1, var2, (PyNewWrapper)null);
      }
   }
}
