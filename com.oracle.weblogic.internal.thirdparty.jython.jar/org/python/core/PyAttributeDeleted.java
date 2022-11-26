package org.python.core;

@Untraversable
class PyAttributeDeleted extends PyObject {
   static final PyAttributeDeleted INSTANCE = new PyAttributeDeleted();

   private PyAttributeDeleted() {
   }

   public String toString() {
      return "";
   }

   public Object __tojava__(Class c) {
      if (c == PyObject.class) {
         return this;
      } else {
         return c.isPrimitive() ? Py.NoConversion : null;
      }
   }
}
