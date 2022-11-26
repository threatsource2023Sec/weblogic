package org.python.core;

import org.python.expose.ExposeAsSuperclass;

public class WinVersion$service_pack_descriptor extends PyDataDescr implements ExposeAsSuperclass {
   public WinVersion$service_pack_descriptor() {
      super("service_pack", PyObject.class, (String)null);
   }

   public Object invokeGet(PyObject var1) {
      return ((WinVersion)var1).service_pack;
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
