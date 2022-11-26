package javax.security.enterprise.identitystore;

import java.security.BasicPermission;

public class IdentityStorePermission extends BasicPermission {
   public IdentityStorePermission(String name) {
      super(name);
   }

   public IdentityStorePermission(String name, String action) {
      super(name, action);
   }
}
