package weblogic.security.principal;

public final class WLSServerIdentity extends WLSAbstractPrincipal {
   private static final long serialVersionUID = -5922851288141495402L;

   public WLSServerIdentity(String name) {
      this.setName(name);
   }

   public byte[] getSignedData() {
      return this.getSignedDataCaseSensitive();
   }
}
