package org.python.core;

import org.python.expose.ExposeAsSuperclass;

public class FloatInfo$min_descriptor extends PyDataDescr implements ExposeAsSuperclass {
   public FloatInfo$min_descriptor() {
      super("min", PyObject.class, (String)null);
   }

   public Object invokeGet(PyObject var1) {
      return ((FloatInfo)var1).min;
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
