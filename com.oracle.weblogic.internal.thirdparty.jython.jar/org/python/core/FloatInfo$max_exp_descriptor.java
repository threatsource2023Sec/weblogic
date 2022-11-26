package org.python.core;

import org.python.expose.ExposeAsSuperclass;

public class FloatInfo$max_exp_descriptor extends PyDataDescr implements ExposeAsSuperclass {
   public FloatInfo$max_exp_descriptor() {
      super("max_exp", PyObject.class, (String)null);
   }

   public Object invokeGet(PyObject var1) {
      return ((FloatInfo)var1).max_exp;
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
