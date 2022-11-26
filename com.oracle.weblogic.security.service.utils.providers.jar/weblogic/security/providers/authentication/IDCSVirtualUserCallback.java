package weblogic.security.providers.authentication;

import javax.security.auth.callback.Callback;

public final class IDCSVirtualUserCallback implements Callback {
   private IDCSSubjectComponentData subjectComponentData;
   private boolean allowVirtualUser;

   public IDCSSubjectComponentData getSubjectComponentData() {
      return this.subjectComponentData;
   }

   public void setSubjectComponentData(IDCSSubjectComponentData subjectComponentData) {
      this.subjectComponentData = subjectComponentData;
   }

   public boolean isAllowVirtualUser() {
      return this.allowVirtualUser;
   }

   public void setAllowVirtualUser(boolean allowVirtualUser) {
      this.allowVirtualUser = allowVirtualUser;
   }
}
