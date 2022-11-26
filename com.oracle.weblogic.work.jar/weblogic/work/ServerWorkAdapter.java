package weblogic.work;

import weblogic.security.acl.internal.AuthenticatedSubject;

public abstract class ServerWorkAdapter extends WorkAdapter {
   private AuthenticatedSubject subject;

   public ServerWorkAdapter() {
   }

   public ServerWorkAdapter(AuthenticatedSubject subject) {
      this.subject = subject;
   }

   protected AuthenticatedSubject getAuthenticatedSubject() {
      return this.subject;
   }

   final void setWorkManager(SelfTuningWorkManagerImpl wm) {
      super.setWorkManager(wm);
      if (wm != null) {
         if (wm.getRequestClass() instanceof ContextRequestClass) {
            this.requestClass = ((ContextRequestClass)wm.getRequestClass()).getEffective(this.getAuthenticatedSubject());
         }

         this.subject = null;
      }
   }
}
