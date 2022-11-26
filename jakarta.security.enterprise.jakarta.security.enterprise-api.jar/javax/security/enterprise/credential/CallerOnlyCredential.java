package javax.security.enterprise.credential;

public class CallerOnlyCredential implements Credential {
   private final String caller;

   public CallerOnlyCredential(String caller) {
      this.caller = caller;
   }

   public String getCaller() {
      return this.caller;
   }
}
