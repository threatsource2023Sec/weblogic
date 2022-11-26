package weblogic.security.service.internal;

public final class LoginFailureRecord extends SecurityMulticastRecord {
   private static final long serialVersionUID = -5495348326391500524L;
   String user_name = null;
   String identity_domain = null;

   LoginFailureRecord(String serverName, String realmName, int sequenceNumber, long timeOfFailure, String userName, String identityDomain) {
      super(serverName, realmName, sequenceNumber, timeOfFailure);
      this.user_name = userName;
      this.identity_domain = identityDomain;
   }

   String userName() {
      return this.user_name;
   }

   String identityDomain() {
      return this.identity_domain;
   }

   public String toString() {
      String result = super.toString() + " username: " + this.user_name;
      if (this.identity_domain != null && !this.identity_domain.isEmpty()) {
         result = result + " identity domain: " + this.identity_domain;
      }

      return result;
   }
}
