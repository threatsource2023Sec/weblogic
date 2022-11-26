package javax.security.enterprise.credential;

public class RememberMeCredential implements Credential {
   private final String token;

   public RememberMeCredential(String token) {
      this.token = token;
   }

   public String getToken() {
      return this.token;
   }
}
