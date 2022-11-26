package weblogic.security.auth.login;

public class PasswordCredential {
   private String password;
   private String username;

   public PasswordCredential(String username, String password) {
      this.username = username;
      this.password = password;
   }

   public String getUsername() {
      return this.username;
   }

   public String getPassword() {
      return this.password;
   }
}
