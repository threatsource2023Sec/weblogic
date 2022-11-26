package weblogic.wtc.gwt;

import com.bea.core.jatmi.common.ntrace;
import com.bea.core.jatmi.internal.TCTransactionHelper;
import com.bea.core.jatmi.intf.TuxedoLoggable;
import java.util.Timer;
import java.util.TimerTask;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import weblogic.kernel.ExecuteThread;
import weblogic.wtc.WTCLogger;
import weblogic.wtc.jatmi.TPException;
import weblogic.wtc.jatmi.TdomTcb;
import weblogic.wtc.jatmi.TdomTranTcb;
import weblogic.wtc.jatmi.Txid;
import weblogic.wtc.jatmi.gwatmi;
import weblogic.wtc.jatmi.tcm;
import weblogic.wtc.jatmi.tfmh;

class OatmialInboundRecover extends TimerTask {
   private Xid myXid;
   private int myOperation;
   private TDMRemote myRemoteDomain;
   private Timer myTimeService;
   private OatmialServices tos;
   private TuxedoLoggable myTransactionLoggable;
   protected static final int RECOVERED_NOLOG = 0;
   protected static final int RECOVERED_PREPARED = 1;
   protected static final int RECOVERED_COMMITTING = 2;
   protected static final int NOT_RECOVERED_PREPARED = 3;
   protected static final int NOT_RECOVERED_COMMITTING = 4;

   public OatmialInboundRecover(Xid anXid, int operation, TDMRemote rDom, Timer aTimer, TuxedoLoggable aTransactionLoggable) {
      this.myXid = anXid;
      this.myOperation = operation;
      this.myRemoteDomain = rDom;
      this.myTimeService = aTimer;
      this.myTransactionLoggable = aTransactionLoggable;
      this.tos = WTCService.getOatmialServices();
   }

   public void execute(ExecuteThread thd) throws Exception {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/OatmialInboundRecover/execute/" + Thread.currentThread());
      }

      XAResource wlsXaResource = null;
      TuxedoLoggable tl = null;
      int addFlags = 0;
      gwatmi myAtmi = null;
      int new_opcode = true;
      if ((wlsXaResource = TCTransactionHelper.getXAResource()) == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/OatmialInboundRecover/execute/10");
         }

      } else {
         byte new_opcode;
         switch (this.myOperation) {
            case 0:
               if (traceEnabled) {
                  ntrace.doTrace("RECOVERED_NOLOG");
               }

               try {
                  if (traceEnabled) {
                     ntrace.doTrace("myXid = " + this.myXid);
                  }

                  wlsXaResource.rollback(this.myXid);
               } catch (XAException var13) {
                  WTCLogger.logWarningRecoverRollbackFailure(var13.toString());
               }

               if (traceEnabled) {
                  ntrace.doTrace("]/OatmialInboundRecover/execute/20");
               }

               return;
            case 1:
               if (traceEnabled) {
                  ntrace.doTrace("RECOVERED_PREPARED");
               }

               new_opcode = 8;
               break;
            case 2:
               if (traceEnabled) {
                  ntrace.doTrace("RECOVERED_COMMITTING");
               }

               tl = this.myTransactionLoggable;

               try {
                  if (traceEnabled) {
                     ntrace.doTrace("myXid = " + this.myXid);
                  }

                  wlsXaResource.commit(this.myXid, false);
               } catch (XAException var14) {
                  switch (var14.errorCode) {
                     case -7:
                     case -6:
                     case -5:
                     case -3:
                     case -2:
                     case -1:
                     case 0:
                     case 1:
                     case 2:
                     case 3:
                     case 4:
                     case 7:
                     case 8:
                     default:
                        addFlags = 16;
                     case -4:
                        break;
                     case 5:
                     case 6:
                        addFlags = 8;
                  }
               }

               new_opcode = 10;
               break;
            case 3:
               if (traceEnabled) {
                  ntrace.doTrace("NOT_RECOVERED_PREPARED");
               }

               tl = this.myTransactionLoggable;
               tl.forget();
               return;
            case 4:
               if (traceEnabled) {
                  ntrace.doTrace("NOT_RECOVERED_COMMITTING");
               }

               tl = this.myTransactionLoggable;
               new_opcode = 10;
               break;
            default:
               if (traceEnabled) {
                  ntrace.doTrace("]/OatmialInboundRecover/execute/30/" + this.myOperation);
               }

               return;
         }

         if (this.myRemoteDomain == null) {
            if (traceEnabled) {
               ntrace.doTrace("]/OatmialInboundRecover/execute/40");
            }

         } else if ((myAtmi = this.myRemoteDomain.getTsession(true)) == null) {
            if (tl != null) {
               tl.forget();
            }

            if (new_opcode == 10) {
               this.tos.deleteInboundRdomsAssociatedWithXid(this.myXid);
               if (traceEnabled) {
                  ntrace.doTrace("]/OatmialInboundRecover/execute/50");
               }

            } else {
               this.myTimeService.schedule(this, 300000L);
               if (traceEnabled) {
                  ntrace.doTrace("]/OatmialInboundRecover/execute/60/rescheduled for five minutes");
               }

            }
         } else {
            tfmh new_tmmsg = new tfmh(1);
            TdomTcb new_tmmsg_tdom = new TdomTcb(new_opcode, 0, 0, (String)null);
            new_tmmsg_tdom.set_info(32 | addFlags);
            new_tmmsg.tdom = new tcm((short)7, new_tmmsg_tdom);
            TdomTranTcb new_tmmsg_tdomtran = new TdomTranTcb(new Txid(this.myXid.getGlobalTransactionId()));
            new_tmmsg_tdomtran.setNwtranidparent(new String(this.myXid.getBranchQualifier()));
            new_tmmsg.tdomtran = new tcm((short)10, new_tmmsg_tdomtran);

            try {
               myAtmi.send_transaction_reply(new_tmmsg);
               if (traceEnabled) {
                  ntrace.doTrace("reply for transaction recovery sent");
               }
            } catch (TPException var12) {
               WTCLogger.logTPEsendTran(var12);
            }

            if (tl != null) {
               tl.forget();
            }

            if (new_opcode == 10) {
               this.tos.deleteInboundRdomsAssociatedWithXid(this.myXid);
            }

            if (traceEnabled) {
               ntrace.doTrace("]/OatmialInboundRecover/execute/70");
            }

         }
      }
   }

   public void run() {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/OatmialInboundRecover/run/");
      }

      if (this.tos.getInboundRdomsAssociatedWithXid(this.myXid) == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/OatmialInboundRecover/run/10");
         }

      } else {
         try {
            this.execute((ExecuteThread)null);
         } catch (Exception var3) {
            if (traceEnabled) {
               ntrace.doTrace("]/OatmialInboundRecover/run/20/" + var3);
            }

            return;
         }

         if (traceEnabled) {
            ntrace.doTrace("]/OatmialInboundRecover/run/30");
         }

      }
   }
}
