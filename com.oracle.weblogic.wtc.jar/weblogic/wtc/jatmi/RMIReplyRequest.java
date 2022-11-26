package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.ntrace;
import com.bea.core.jatmi.internal.TCSecurityManager;
import com.bea.core.jatmi.internal.TCTransactionHelper;
import com.bea.core.jatmi.intf.TCAuthenticatedUser;
import com.bea.core.jatmi.intf.TCTask;
import java.io.IOException;
import java.util.HashMap;
import javax.security.auth.login.LoginException;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.xa.Xid;
import weblogic.iiop.EndPoint;
import weblogic.iiop.messages.SequencedRequestMessage;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.tgiop.TGIOPConnection;
import weblogic.tgiop.TGIOPEndPointImpl;
import weblogic.wtc.gwt.MethodParameters;
import weblogic.wtc.gwt.ServiceParameters;
import weblogic.wtc.gwt.TuxedoCorbaConnection;

final class RMIReplyRequest implements TCTask {
   private tfmh myTmmsg;
   private HashMap myConnMap;
   private dsession myDsession;
   private TCAuthenticatedUser mySubject = null;
   private Object[] myRequestInfo;
   private String myName;

   RMIReplyRequest(tfmh tmmsg, Object[] reqInfo, dsession currDsession) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/RMIReplyRequest/");
      }

      this.myTmmsg = tmmsg;
      this.myRequestInfo = reqInfo;
      this.myConnMap = ((TuxedoCorbaConnection)reqInfo[0]).getTxInfoMap();
      if (reqInfo[1] != null) {
         try {
            this.mySubject = TCSecurityManager.impersonate((String)reqInfo[1]);
         } catch (LoginException var6) {
            if (traceEnabled) {
               ntrace.doTrace("/RMIReplyRequest/Failed to get user identity: " + var6);
            }
         }
      }

      this.myDsession = currDsession;
      if (traceEnabled) {
         ntrace.doTrace("]/RMIReplyRequest/10");
      }

   }

   public int execute() {
      Xid currXid = null;
      boolean traceEnabled = ntrace.isMixedTraceEnabled(12);
      if (traceEnabled) {
         ntrace.doTrace("[/RMIReplyRequest/execute/0");
      }

      MethodParameters currMethodParms = new MethodParameters((ServiceParameters)null, (Objrecv)null, (Object[])null, this.myDsession);
      TdomTcb tdom = (TdomTcb)this.myTmmsg.tdom.body;
      int opcode = tdom.get_opcode();
      int myTPException = tdom.get_diagnostic();
      if (opcode == 3 && myTPException == 0) {
         myTPException = 12;
      }

      if (opcode == 12) {
         myTPException = 1;
      }

      if (11 == myTPException && null != this.myTmmsg.user) {
         myTPException = 0;
      }

      if (myTPException == 0 && this.myTmmsg.user == null) {
         myTPException = 12;
         if (traceEnabled) {
            ntrace.doTrace("/RMIReplyRequest/execute/Receive a reply message without user data and error code");
         }
      }

      if (myTPException != 0) {
         if (traceEnabled) {
            ntrace.doTrace("/RMIReplyRequest/execute/1/diagnostic = " + myTPException);
         }

         Exception exc = TGIOPUtil.mapTPError(myTPException);
         int reqId = tdom.get_reqid();

         TGIOPConnection c;
         try {
            c = new TGIOPConnection(currMethodParms);
         } catch (IOException var23) {
            throw new RuntimeException(var23);
         }

         EndPoint ep = new TGIOPEndPointImpl(c, (AuthenticatedSubject)null);
         Integer GIOPRequestID = (Integer)this.myRequestInfo[2];
         SequencedRequestMessage msg = ep.removePendingResponse(GIOPRequestID);
         if (msg != null) {
            msg.notify(exc);
         } else if (traceEnabled) {
            ntrace.doTrace("RMIReplyRequest/execute/2/request not found");
         }
      }

      if (this.myTmmsg.user == null) {
         if (traceEnabled) {
            ntrace.doTrace("*]/RMIReplyRequest/execute/4");
         }

         return 0;
      } else {
         if (this.mySubject != null) {
            if (traceEnabled) {
               ntrace.doTrace("/RMIReplyRequest/execute/5");
            }

            TCSecurityManager.setAsCurrentUser(this.mySubject);
         }

         if (this.myTmmsg.tdomtran != null) {
            TdomTranTcb domtran = (TdomTranTcb)this.myTmmsg.tdomtran.body;
            Txid currTxid = new Txid(domtran.getGlobalTransactionId());
            if (currTxid != null) {
               if (traceEnabled) {
                  ntrace.doTrace("/RMIReplyRequest/execute/10 + currTxid = " + currTxid);
               }

               Object[] txInfo = new Object[3];
               synchronized(this.myConnMap) {
                  if ((txInfo = (Object[])((Object[])this.myConnMap.get(currTxid))) == null) {
                     if (traceEnabled) {
                        ntrace.doTrace("*/RMIReplyRequest/dispatch/12");
                     }
                  } else {
                     currXid = (Xid)txInfo[2];
                  }
               }

               if (currXid != null) {
                  if (traceEnabled) {
                     ntrace.doTrace("/RMIReplyRequest/execute/14 + currXid = " + currXid);
                  }

                  Transaction currTransaction;
                  if ((currTransaction = TCTransactionHelper.getTransaction(currXid)) == null) {
                     if (traceEnabled) {
                        ntrace.doTrace("*/RMIReplyRequest/execute/20");
                     }
                  } else {
                     try {
                        TCTransactionHelper.resumeTransaction(currTransaction);
                     } catch (Exception var21) {
                        throw new RuntimeException(var21);
                     }
                  }

                  if (opcode == 3 && myTPException != 11 && myTPException != 10) {
                     if (traceEnabled) {
                        ntrace.doTrace("/RMIReplyRequest/execute/30");
                     }

                     myTPException = 1;
                  } else if (myTPException != 11 && myTPException != 10) {
                     if (traceEnabled) {
                        ntrace.doTrace("/RMIReplyRequest/execute/40");
                     }

                     myTPException = 0;
                  }

                  if (myTPException != 0) {
                     if (traceEnabled) {
                        ntrace.doTrace("/RMIReplyRequest/execute/45");
                     }

                     txInfo = new Object[3];
                     synchronized(this.myConnMap) {
                        if ((txInfo = (Object[])((Object[])this.myConnMap.get(currXid.getGlobalTransactionId()))) == null) {
                           if (traceEnabled) {
                              ntrace.doTrace("*/RMIReplyRequest/dispatch/50");
                           }
                        } else {
                           txInfo[1] = new Boolean(true);
                           this.myConnMap.put(currXid.getGlobalTransactionId(), txInfo);
                        }
                     }

                     try {
                        if (currTransaction != null) {
                           currTransaction.setRollbackOnly();
                        }
                     } catch (SystemException var24) {
                        if (traceEnabled) {
                           ntrace.doTrace("*/RMIReplyRequest/dispatch/60/SystemException:" + var24);
                        }
                     }
                  }

                  if (traceEnabled) {
                     ntrace.doTrace("/RMIReplyRequest/dispatch/70");
                  }
               }
            }
         }

         if (myTPException == 0) {
            try {
               TGIOPUtil.injectMsgIntoRMI(this.myTmmsg, currMethodParms);
            } catch (IOException var19) {
               throw new RuntimeException(var19);
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/RMIReplyRequest/execute/80");
         }

         return 0;
      }
   }

   public void setTaskName(String tname) {
      this.myName = new String("RMIReplyRequest$" + tname);
   }

   public String getTaskName() {
      return this.myName == null ? "RMIReplyRequest$unknown" : this.myName;
   }
}
