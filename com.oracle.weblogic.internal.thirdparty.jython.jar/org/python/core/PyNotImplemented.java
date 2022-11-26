package org.python.core;

import java.io.Serializable;

@Untraversable
public class PyNotImplemented extends PySingleton implements Serializable {
   PyNotImplemented() {
      super("NotImplemented");
   }

   public Object __tojava__(Class c) {
      if (c == PyObject.class) {
         return this;
      } else {
         return c.isPrimitive() ? Py.NoConversion : null;
      }
   }

   public boolean isMappingType() {
      return false;
   }

   public boolean isSequenceType() {
      return false;
   }

   private Object writeReplace() {
      return new Py.SingletonResolver("NotImplemented");
   }
}
