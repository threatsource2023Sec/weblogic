package org.python.core;

import org.python.expose.ExposeAsSuperclass;

public class FloatInfo$epsilon_descriptor extends PyDataDescr implements ExposeAsSuperclass {
   public FloatInfo$epsilon_descriptor() {
      super("epsilon", PyObject.class, (String)null);
   }

   public Object invokeGet(PyObject var1) {
      return ((FloatInfo)var1).epsilon;
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
