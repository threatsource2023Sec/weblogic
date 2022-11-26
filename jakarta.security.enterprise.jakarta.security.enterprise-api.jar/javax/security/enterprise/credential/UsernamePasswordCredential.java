package javax.security.enterprise.credential;

public class UsernamePasswordCredential extends AbstractClearableCredential {
   private final String caller;
   private final Password password;

   public UsernamePasswordCredential(String callerName, String password) {
      this.caller = callerName;
      this.password = new Password(password);
   }

   public UsernamePasswordCredential(String callerName, Password password) {
      this.caller = callerName;
      this.password = password;
   }

   public Password getPassword() {
      return this.password;
   }

   public String getPasswordAsString() {
      return String.valueOf(this.getPassword().getValue());
   }

   public void clearCredential() {
      this.password.clear();
   }

   public String getCaller() {
      return this.caller;
   }

   public boolean compareTo(String callerName, String password) {
      return this.getCaller().equals(callerName) && this.getPassword().compareTo(password);
   }
}
