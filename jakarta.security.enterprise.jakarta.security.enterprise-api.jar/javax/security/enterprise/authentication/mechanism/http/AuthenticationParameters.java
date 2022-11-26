package javax.security.enterprise.authentication.mechanism.http;

import javax.security.enterprise.credential.Credential;

public class AuthenticationParameters {
   private Credential credential;
   private boolean newAuthentication;
   private boolean rememberMe;

   public static AuthenticationParameters withParams() {
      return new AuthenticationParameters();
   }

   public AuthenticationParameters credential(Credential credential) {
      this.setCredential(credential);
      return this;
   }

   public AuthenticationParameters newAuthentication(boolean newAuthentication) {
      this.setNewAuthentication(newAuthentication);
      return this;
   }

   public AuthenticationParameters rememberMe(boolean rememberMe) {
      this.setRememberMe(rememberMe);
      return this;
   }

   public Credential getCredential() {
      return this.credential;
   }

   public void setCredential(Credential credential) {
      this.credential = credential;
   }

   public boolean isNewAuthentication() {
      return this.newAuthentication;
   }

   public void setNewAuthentication(boolean newAuthentication) {
      this.newAuthentication = newAuthentication;
   }

   public boolean isRememberMe() {
      return this.rememberMe;
   }

   public void setRememberMe(boolean rememberMe) {
      this.rememberMe = rememberMe;
   }
}
