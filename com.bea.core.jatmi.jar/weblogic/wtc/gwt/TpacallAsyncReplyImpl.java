package weblogic.wtc.gwt;

import com.bea.core.jatmi.common.ntrace;
import com.bea.core.jatmi.internal.TCSecurityManager;
import com.bea.core.jatmi.intf.TCAuthenticatedUser;
import javax.transaction.Transaction;
import weblogic.wtc.jatmi.ApplicationToMonitorInterface;
import weblogic.wtc.jatmi.CallDescriptor;
import weblogic.wtc.jatmi.GatewayTpacallAsyncReply;
import weblogic.wtc.jatmi.Reply;
import weblogic.wtc.jatmi.TPException;
import weblogic.wtc.jatmi.TpacallAsyncReply;
import weblogic.wtc.jatmi.gwatmi;

public final class TpacallAsyncReplyImpl implements GatewayTpacallAsyncReply {
   private TpacallAsyncReply originalAsyncReply;
   private TuxedoConnectionImpl originalAtmi;
   private Transaction myTransaction;
   private gwatmi myGateway;
   private TCAuthenticatedUser mySubject;
   private static final long serialVersionUID = -6137371717611931357L;

   public TpacallAsyncReplyImpl(TpacallAsyncReply originalAsyncReply, TuxedoConnectionImpl originalAtmi, Transaction myTransaction, gwatmi myGateway) {
      this.originalAsyncReply = originalAsyncReply;
      this.originalAtmi = originalAtmi;
      this.myTransaction = myTransaction;
      this.myGateway = myGateway;
   }

   public void success(ApplicationToMonitorInterface atmi, CallDescriptor cd, Reply successReply) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TpacallAsyncReplyImpl/success/" + atmi + "/" + successReply);
      }

      boolean didPush = false;
      if (this.originalAsyncReply == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/TpacallAsyncReplyImpl/success/10/");
         }

      } else {
         CallDescriptor realCd = this.originalAtmi.createCallDescriptor(this.myGateway, cd, true, this.myTransaction != null);

         try {
            if (this.mySubject != null) {
               TCSecurityManager.setAsCurrentUser(this.mySubject);
               didPush = true;
            }

            this.originalAsyncReply.success(this.originalAtmi, realCd, successReply);
         } catch (Exception var11) {
            if (traceEnabled) {
               ntrace.doTrace("/TpacallAsyncReplyImpl/success/20/User TpacallAsyncReply success callback threw " + var11);
            }
         } finally {
            if (didPush && this.mySubject != null) {
               TCSecurityManager.removeCurrentUser();
            }

         }

         if (traceEnabled) {
            ntrace.doTrace("]/TpacallAsyncReplyImpl/success/30/");
         }

      }
   }

   public void failure(ApplicationToMonitorInterface atmi, CallDescriptor cd, TPException failureReply) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TpacallAsyncReplyImpl/failure/" + atmi + "/" + failureReply);
      }

      boolean didPush = false;
      if (this.originalAsyncReply == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/TpacallAsyncReplyImpl/failure/10/");
         }

      } else {
         CallDescriptor realCd = this.originalAtmi.createCallDescriptor(this.myGateway, cd, true, this.myTransaction != null);

         try {
            if (this.mySubject != null) {
               TCSecurityManager.setAsCurrentUser(this.mySubject);
               didPush = true;
            }

            this.originalAsyncReply.failure(this.originalAtmi, realCd, failureReply);
         } catch (Exception var11) {
            if (traceEnabled) {
               ntrace.doTrace("/TpacallAsyncReplyImpl/failure/20/User TpacallAsyncReply failure callback threw " + var11);
            }
         } finally {
            if (didPush && this.mySubject != null) {
               TCSecurityManager.removeCurrentUser();
            }

         }

         if (traceEnabled) {
            ntrace.doTrace("]/TpacallAsyncReplyImpl/failure/30/");
         }

      }
   }

   public Transaction getTransaction() {
      return this.myTransaction;
   }

   public void setTargetSubject(TCAuthenticatedUser subj) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TpacallAsyncReplyImpl/setTargetSubject/");
      }

      this.mySubject = subj;
      if (traceEnabled) {
         ntrace.doTrace("]/TpacallAsyncReplyImpl/setTargetSubject/10");
      }

   }
}
