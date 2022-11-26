package org.python.core;

import org.python.expose.ExposeAsSuperclass;

public class FloatInfo$rounds_descriptor extends PyDataDescr implements ExposeAsSuperclass {
   public FloatInfo$rounds_descriptor() {
      super("rounds", PyObject.class, (String)null);
   }

   public Object invokeGet(PyObject var1) {
      return ((FloatInfo)var1).rounds;
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
