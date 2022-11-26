package org.python.core;

import org.python.expose.ExposeAsSuperclass;

public class FloatInfo$min_exp_descriptor extends PyDataDescr implements ExposeAsSuperclass {
   public FloatInfo$min_exp_descriptor() {
      super("min_exp", PyObject.class, (String)null);
   }

   public Object invokeGet(PyObject var1) {
      return ((FloatInfo)var1).min_exp;
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
