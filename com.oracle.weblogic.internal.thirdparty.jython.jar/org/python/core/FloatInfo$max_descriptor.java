package org.python.core;

import org.python.expose.ExposeAsSuperclass;

public class FloatInfo$max_descriptor extends PyDataDescr implements ExposeAsSuperclass {
   public FloatInfo$max_descriptor() {
      super("max", PyObject.class, (String)null);
   }

   public Object invokeGet(PyObject var1) {
      return ((FloatInfo)var1).max;
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
