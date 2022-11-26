package weblogic.iiop;

import weblogic.iiop.ior.IOR;

class LocationForwardException extends Exception {
   private IOR ior;

   LocationForwardException(IOR ior) {
      this.ior = ior;
   }

   IOR getIor() {
      return this.ior;
   }
}
