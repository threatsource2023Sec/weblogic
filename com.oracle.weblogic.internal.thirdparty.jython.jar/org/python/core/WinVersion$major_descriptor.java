package org.python.core;

import org.python.expose.ExposeAsSuperclass;

public class WinVersion$major_descriptor extends PyDataDescr implements ExposeAsSuperclass {
   public WinVersion$major_descriptor() {
      super("major", PyObject.class, (String)null);
   }

   public Object invokeGet(PyObject var1) {
      return ((WinVersion)var1).major;
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
