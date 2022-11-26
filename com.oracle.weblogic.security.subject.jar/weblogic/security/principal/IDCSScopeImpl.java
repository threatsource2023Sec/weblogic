package weblogic.security.principal;

public final class IDCSScopeImpl extends WLSAbstractPrincipal implements IDCSScope {
   private static final long serialVersionUID = 1L;

   public IDCSScopeImpl(String scopeName) {
      this.setName(scopeName);
   }

   public IDCSScopeImpl(String scopeName, String scopeTenant) {
      this.setName(scopeName);
      this.setIdentityDomain(scopeTenant);
   }

   public boolean equals(Object another) {
      return another instanceof IDCSScopeImpl && super.equals(another);
   }
}
