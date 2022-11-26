package weblogic.servlet.security.internal;

final class Basic2SecurityModule extends BasicSecurityModule {
   static final String BASIC_PLAIN_AUTH = "BASIC_PLAIN";
   static final String BASIC_ENFORCE_AUTH = "BASIC_ENFORCE";
   private final boolean enforceCredentials;

   public Basic2SecurityModule(ServletSecurityContext ctx, WebAppSecurity was, boolean controller, String mode) {
      super(ctx, was, controller);
      this.enforceCredentials = mode.equals("BASIC_ENFORCE");
   }

   protected boolean enforceValidBasicAuthCredentials() {
      return this.enforceCredentials;
   }
}
