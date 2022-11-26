package javax.security.auth.message.callback;

import java.security.Principal;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;

public class CallerPrincipalCallback implements Callback {
   private Subject subject;
   private Principal principal;
   private String name;

   public CallerPrincipalCallback(Subject subject, Principal principal) {
      this.subject = subject;
      this.principal = principal;
   }

   public CallerPrincipalCallback(Subject subject, String name) {
      this.subject = subject;
      this.name = name;
   }

   public Subject getSubject() {
      return this.subject;
   }

   public Principal getPrincipal() {
      return this.principal;
   }

   public String getName() {
      return this.name;
   }
}
