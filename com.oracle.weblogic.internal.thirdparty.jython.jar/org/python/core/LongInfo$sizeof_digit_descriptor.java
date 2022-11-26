package org.python.core;

import org.python.expose.ExposeAsSuperclass;

public class LongInfo$sizeof_digit_descriptor extends PyDataDescr implements ExposeAsSuperclass {
   public LongInfo$sizeof_digit_descriptor() {
      super("sizeof_digit", PyObject.class, (String)null);
   }

   public Object invokeGet(PyObject var1) {
      return ((LongInfo)var1).sizeof_digit;
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
