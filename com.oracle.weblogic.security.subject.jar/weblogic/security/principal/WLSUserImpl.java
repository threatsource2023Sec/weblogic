package weblogic.security.principal;

import java.security.Principal;
import weblogic.security.spi.WLSUser;

public final class WLSUserImpl extends WLSAbstractPrincipal implements WLSUser {
   private static final long serialVersionUID = -4751797971105387435L;
   private transient Principal originalPrincipal = null;

   public WLSUserImpl(String userName) {
      this.setName(userName);
   }

   public WLSUserImpl(String userName, boolean createSalt) {
      super(createSalt);
      this.setName(userName);
   }

   public boolean equals(Object another) {
      return another instanceof WLSUserImpl && super.equals(another);
   }

   public Principal getOriginalPrincipal() {
      return this.originalPrincipal;
   }

   public void setOriginalPrincipal(Principal originalPrincipal) {
      this.originalPrincipal = originalPrincipal;
   }
}
