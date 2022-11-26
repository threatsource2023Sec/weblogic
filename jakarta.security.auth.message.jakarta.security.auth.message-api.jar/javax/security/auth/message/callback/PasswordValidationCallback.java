package javax.security.auth.message.callback;

import java.util.Arrays;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;

public class PasswordValidationCallback implements Callback {
   private Subject subject;
   private String username;
   private char[] password;
   private boolean result;

   public PasswordValidationCallback(Subject subject, String username, char[] password) {
      this.subject = subject;
      this.username = username;
      if (password != null) {
         this.password = (char[])password.clone();
      }

   }

   public Subject getSubject() {
      return this.subject;
   }

   public String getUsername() {
      return this.username;
   }

   public char[] getPassword() {
      return this.password;
   }

   public void clearPassword() {
      if (this.password != null) {
         Arrays.fill(this.password, ' ');
      }

   }

   public void setResult(boolean result) {
      this.result = result;
   }

   public boolean getResult() {
      return this.result;
   }
}
