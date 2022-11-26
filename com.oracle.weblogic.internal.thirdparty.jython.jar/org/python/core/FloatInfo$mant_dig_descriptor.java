package org.python.core;

import org.python.expose.ExposeAsSuperclass;

public class FloatInfo$mant_dig_descriptor extends PyDataDescr implements ExposeAsSuperclass {
   public FloatInfo$mant_dig_descriptor() {
      super("mant_dig", PyObject.class, (String)null);
   }

   public Object invokeGet(PyObject var1) {
      return ((FloatInfo)var1).mant_dig;
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
