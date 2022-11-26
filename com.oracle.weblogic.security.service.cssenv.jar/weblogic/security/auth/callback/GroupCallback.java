package weblogic.security.auth.callback;

import java.util.ArrayList;
import java.util.Collection;
import javax.security.auth.callback.Callback;

public class GroupCallback implements Callback {
   private Collection value = null;
   private String prompt;

   public GroupCallback(String prompt) {
      this.prompt = prompt;
   }

   public String getPrompt() {
      return this.prompt;
   }

   public Collection getValue() {
      return this.value;
   }

   public void setValue(Collection value) {
      this.value = new ArrayList(value);
   }
}
