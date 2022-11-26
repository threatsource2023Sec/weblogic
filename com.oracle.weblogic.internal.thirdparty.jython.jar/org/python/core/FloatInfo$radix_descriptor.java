package org.python.core;

import org.python.expose.ExposeAsSuperclass;

public class FloatInfo$radix_descriptor extends PyDataDescr implements ExposeAsSuperclass {
   public FloatInfo$radix_descriptor() {
      super("radix", PyObject.class, (String)null);
   }

   public Object invokeGet(PyObject var1) {
      return ((FloatInfo)var1).radix;
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
