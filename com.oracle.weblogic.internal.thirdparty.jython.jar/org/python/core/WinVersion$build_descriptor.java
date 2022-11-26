package org.python.core;

import org.python.expose.ExposeAsSuperclass;

public class WinVersion$build_descriptor extends PyDataDescr implements ExposeAsSuperclass {
   public WinVersion$build_descriptor() {
      super("build", PyObject.class, (String)null);
   }

   public Object invokeGet(PyObject var1) {
      return ((WinVersion)var1).build;
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
