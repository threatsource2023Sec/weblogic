package javax.security.auth.message.callback;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;

public class GroupPrincipalCallback implements Callback {
   private Subject subject;
   private String[] groups;

   public GroupPrincipalCallback(Subject subject, String[] groups) {
      this.subject = subject;
      this.groups = groups;
   }

   public Subject getSubject() {
      return this.subject;
   }

   public String[] getGroups() {
      return this.groups;
   }
}
