package org.python.core;

import org.python.expose.ExposeAsSuperclass;

public class FloatInfo$dig_descriptor extends PyDataDescr implements ExposeAsSuperclass {
   public FloatInfo$dig_descriptor() {
      super("dig", PyObject.class, (String)null);
   }

   public Object invokeGet(PyObject var1) {
      return ((FloatInfo)var1).dig;
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
