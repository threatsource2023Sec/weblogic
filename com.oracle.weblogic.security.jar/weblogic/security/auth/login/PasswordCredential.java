package weblogic.security.auth.login;

public class PasswordCredential {
   private char[] password;
   private String username;

   public PasswordCredential(String username) {
      this.username = username;
      this.password = null;
   }

   public PasswordCredential(String username, char[] password) {
      this.username = username;
      this.password = password;
   }

   /** @deprecated */
   @Deprecated
   public PasswordCredential(String username, String password) {
      this.username = username;
      this.password = password.toCharArray();
   }

   public String getUsername() {
      return this.username;
   }

   public char[] getPasswordCharArray() {
      return this.password;
   }

   /** @deprecated */
   @Deprecated
   public String getPassword() {
      return new String(this.password);
   }
}
