package weblogic.security.principal;

public final class IDCSClientImpl extends WLSAbstractPrincipal implements IDCSClient {
   private static final long serialVersionUID = 1L;

   public IDCSClientImpl(String clientName) {
      this.setName(clientName);
   }

   public IDCSClientImpl(String clientName, String clientTenant, String clientId) {
      this.setName(clientName);
      this.setIdentityDomain(clientTenant);
      this.setGuid(clientId);
   }

   public boolean equals(Object another) {
      return another instanceof IDCSClientImpl && super.equals(another);
   }

   public String getId() {
      return this.getGuid();
   }
}
