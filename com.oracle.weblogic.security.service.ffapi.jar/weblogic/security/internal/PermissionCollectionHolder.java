package weblogic.security.internal;

import java.security.PermissionCollection;

class PermissionCollectionHolder {
   private PermissionCollection s;

   public PermissionCollection get() {
      return this.s;
   }

   public void set(PermissionCollection s) {
      this.s = s;
   }
}
