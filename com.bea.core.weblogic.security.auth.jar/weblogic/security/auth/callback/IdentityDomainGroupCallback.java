package weblogic.security.auth.callback;

import java.util.Collection;
import javax.security.auth.callback.Callback;

public class IdentityDomainGroupCallback implements Callback {
   private Collection groups;
   private String prompt;

   public IdentityDomainGroupCallback(String prompt) {
      this((Collection)null, prompt);
   }

   public IdentityDomainGroupCallback(Collection groups) {
      this(groups, (String)null);
   }

   public IdentityDomainGroupCallback(Collection groups, String prompt) {
      this.groups = null;
      this.groups = groups;
      this.prompt = prompt;
   }

   public String getPrompt() {
      return this.prompt;
   }

   public Collection getGroups() {
      return this.groups;
   }

   public void setGroups(Collection groups) {
      this.groups = groups;
   }
}
