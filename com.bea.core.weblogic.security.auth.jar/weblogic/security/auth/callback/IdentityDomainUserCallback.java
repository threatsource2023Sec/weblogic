package weblogic.security.auth.callback;

import javax.security.auth.callback.Callback;

public class IdentityDomainUserCallback implements Callback {
   private String prompt;
   private IdentityDomainNames user;

   public IdentityDomainUserCallback(String prompt) {
      this((IdentityDomainNames)null, prompt);
   }

   public IdentityDomainUserCallback(IdentityDomainNames user) {
      this(user, (String)null);
   }

   public IdentityDomainUserCallback(IdentityDomainNames user, String prompt) {
      this.user = user;
      this.prompt = prompt;
   }

   public String getPrompt() {
      return this.prompt;
   }

   public IdentityDomainNames getUser() {
      return this.user;
   }

   public void setUser(IdentityDomainNames user) {
      this.user = user;
   }
}
