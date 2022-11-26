package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.ntrace;
import com.bea.core.jatmi.internal.TCSecurityManager;
import com.bea.core.jatmi.intf.TCAuthenticatedUser;
import java.io.Serializable;

public final class InvokeInfo extends TPServiceInformation {
   private static final long serialVersionUID = 5871100598307211336L;
   private tfmh service_message;
   private Serializable service_reqid;
   private TCAuthenticatedUser mySubject = null;

   public InvokeInfo() {
   }

   public InvokeInfo(String sn, TypedBuffer sd, int f, tfmh sm, Serializable sr, int sessionIdentifier, int conversationIdentifier) {
      super(sn, sd, f, sessionIdentifier, conversationIdentifier);
      this.service_message = sm;
      this.service_reqid = sr;
   }

   public tfmh getServiceMessage() {
      return this.service_message;
   }

   public Serializable getReqid() {
      return this.service_reqid;
   }

   public String toString() {
      return new String(super.toString() + ":" + this.service_message + ":" + this.service_reqid);
   }

   public void setUser() {
      if (this.mySubject != null) {
         TCSecurityManager.setAsCurrentUser(this.mySubject);
      }

   }

   public void removeUser() {
      if (this.mySubject != null) {
         TCSecurityManager.removeCurrentUser();
      }

   }

   public void setTargetSubject(TCAuthenticatedUser subj) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         if (subj != null) {
            ntrace.doTrace("[/InvokeInfo/setTargetSubject/(" + subj.toString() + ")");
         } else {
            ntrace.doTrace("[/InvokeInfo/setTargetSubject/subj is null");
         }
      }

      this.mySubject = subj;
      if (traceEnabled) {
         ntrace.doTrace("]/InvokeInfo/setTargetSubject/10");
      }

   }

   public TCAuthenticatedUser getUser() {
      return this.mySubject;
   }
}
