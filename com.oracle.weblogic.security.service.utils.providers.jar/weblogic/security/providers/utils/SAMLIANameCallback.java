package weblogic.security.providers.utils;

import javax.security.auth.callback.Callback;

public class SAMLIANameCallback implements Callback {
   private String name = null;
   private String prompt = null;
   private String defaultName = null;
   private boolean allowVirtualUser = false;

   public SAMLIANameCallback(String prompt) {
      this.prompt = prompt;
   }

   public SAMLIANameCallback(String prompt, String defaultName) {
      this.prompt = prompt;
      this.defaultName = defaultName;
   }

   public String getDefaultName() {
      return this.defaultName;
   }

   public String getName() {
      return this.name;
   }

   public String getPrompt() {
      return this.prompt;
   }

   public void setName(String name) {
      this.name = name;
   }

   public boolean isAllowVirtualUser() {
      return this.allowVirtualUser;
   }

   public void setAllowVirtualUser(boolean allowVirtualUser) {
      this.allowVirtualUser = allowVirtualUser;
   }
}
