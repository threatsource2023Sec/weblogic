package weblogic.security.auth.callback;

import javax.security.auth.callback.Callback;

public class URLCallback implements Callback {
   private String prompt;
   private String defaultURL;
   private String inputURL;

   public URLCallback(String prompt) {
      if (prompt != null && prompt.length() != 0) {
         this.prompt = prompt;
      } else {
         throw new IllegalArgumentException();
      }
   }

   public URLCallback(String prompt, String defaultURL) {
      if (prompt != null && prompt.length() != 0 && defaultURL != null && defaultURL.length() != 0) {
         this.prompt = prompt;
         this.defaultURL = defaultURL;
      } else {
         throw new IllegalArgumentException();
      }
   }

   public String getPrompt() {
      return this.prompt;
   }

   public String getdefaultURL() {
      return this.defaultURL;
   }

   public void setURL(String URL) {
      this.inputURL = URL;
   }

   public String getURL() {
      return this.inputURL;
   }
}
