package org.python.core;

import org.python.expose.ExposeAsSuperclass;

public class WinVersion$platform_descriptor extends PyDataDescr implements ExposeAsSuperclass {
   public WinVersion$platform_descriptor() {
      super("platform", PyObject.class, (String)null);
   }

   public Object invokeGet(PyObject var1) {
      return ((WinVersion)var1).platform;
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
