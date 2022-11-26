package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.ntrace;
import com.bea.core.jatmi.intf.TCTask;
import javax.transaction.SystemException;
import javax.transaction.Transaction;

class TpacallAsyncExecute implements TCTask {
   private gwatmi myAtmi;
   private tfmh myTmmsg;
   private CallDescriptor myCd;
   private GatewayTpacallAsyncReply callBack;
   private String myName;

   TpacallAsyncExecute(gwatmi myAtmi, tfmh myTmmsg, CallDescriptor myCd, GatewayTpacallAsyncReply callBack) {
      this.myAtmi = myAtmi;
      this.myTmmsg = myTmmsg;
      this.myCd = myCd;
      this.callBack = callBack;
   }

   public int execute() {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/rdsession/TpacallAsyncExecute/");
      }

      TdomTcb tdom = (TdomTcb)this.myTmmsg.tdom.body;
      int myTPException = tdom.get_diagnostic();
      int mytpurcode = tdom.getTpurcode();
      int mytperrordetail = tdom.get_errdetail();
      int opcode = tdom.get_opcode();
      UserTcb utcb = null;
      if (this.myTmmsg != null && this.callBack != null) {
         Transaction myTransaction = this.callBack.getTransaction();
         TypedBuffer tb;
         if (this.myTmmsg.user == null) {
            tb = null;
         } else {
            utcb = (UserTcb)this.myTmmsg.user.body;
            tb = utcb.user_data;
         }

         TuxedoReply ret = new TuxedoReply(tb, mytpurcode, (CallDescriptor)null);
         if (opcode == 3 && myTPException != 11 && myTPException != 10) {
            if ((myTPException == 18 || myTPException == 13) && myTransaction != null) {
               try {
                  myTransaction.setRollbackOnly();
               } catch (SystemException var13) {
                  if (traceEnabled) {
                     ntrace.doTrace("/rdsession/TpacallAsyncExecute/SystemException:" + var13);
                  }
               }
            }

            TPException exceptionReturn = new TPException(myTPException, 0, mytpurcode, mytperrordetail, ret);
            this.callBack.failure(this.myAtmi, this.myCd, exceptionReturn);
            if (traceEnabled) {
               ntrace.doTrace("]/rdsession/TpacallAsyncExecute/10/" + exceptionReturn);
            }

            return 0;
         } else {
            if (myTPException != 11 && myTPException != 10) {
               myTPException = 0;
            }

            if (myTPException == 0) {
               this.myAtmi.restoreTfmhToCache(this.myTmmsg);
               this.callBack.success(this.myAtmi, this.myCd, ret);
               if (traceEnabled) {
                  ntrace.doTrace("]/rdsession/TpacallAsyncExecute/30/" + ret);
               }

               return 0;
            } else {
               if (myTransaction != null) {
                  try {
                     myTransaction.setRollbackOnly();
                  } catch (SystemException var14) {
                     if (traceEnabled) {
                        ntrace.doTrace("/rdsession/TpacallAsyncExecute/SystemException:" + var14);
                     }
                  }
               }

               TPReplyException exceptionReturn = new TPReplyException(myTPException, 0, mytpurcode, mytperrordetail, ret);
               this.callBack.failure(this.myAtmi, this.myCd, exceptionReturn);
               if (traceEnabled) {
                  ntrace.doTrace("]/rdsession/TpacallAsyncExecute/20/" + exceptionReturn);
               }

               return 0;
            }
         }
      } else {
         return 0;
      }
   }

   public void setTaskName(String tname) {
      this.myName = new String("TpacallAsyncExecute$" + tname);
   }

   public String getTaskName() {
      return this.myName == null ? "TpacallAsyncExecute$unknown" : this.myName;
   }
}
