package org.python.core;

import org.python.expose.ExposeAsSuperclass;

public class WinVersion$minor_descriptor extends PyDataDescr implements ExposeAsSuperclass {
   public WinVersion$minor_descriptor() {
      super("minor", PyObject.class, (String)null);
   }

   public Object invokeGet(PyObject var1) {
      return ((WinVersion)var1).minor;
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
