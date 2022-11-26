package weblogic.wtc.gwt;

import com.bea.core.jatmi.common.ntrace;
import com.bea.core.jatmi.internal.TuxedoXid;
import java.io.Serializable;
import java.util.TimerTask;
import weblogic.wtc.jatmi.InvokeInfo;
import weblogic.wtc.jatmi.Reply;
import weblogic.wtc.jatmi.SessionAcallDescriptor;
import weblogic.wtc.jatmi.TPException;
import weblogic.wtc.jatmi.TPReplyException;
import weblogic.wtc.jatmi.TPRequestAsyncReply;
import weblogic.wtc.jatmi.TdomTcb;
import weblogic.wtc.jatmi.TypedBuffer;
import weblogic.wtc.jatmi.UserTcb;
import weblogic.wtc.jatmi.dsession;
import weblogic.wtc.jatmi.rdsession;
import weblogic.wtc.jatmi.tcm;
import weblogic.wtc.jatmi.tfmh;

public class TPRequestAsyncReplyImpl implements TPRequestAsyncReply {
   static final long serialVersionUID = 9166408416488128781L;
   private ServiceParameters _params;
   private boolean _called;
   private TuxedoXid _xid;
   private OatmialServices _services;
   private TDMRemote _remoteDomain;
   private boolean _done = false;
   private TimerTask _task = null;

   protected TPRequestAsyncReplyImpl(ServiceParameters params, TDMRemote remoteDomain, TuxedoXid xid) {
      this._params = params;
      this._remoteDomain = remoteDomain;
      this._xid = xid;
      this._services = WTCService.getOatmialServices();
      this._called = false;
   }

   private void internalReply(Reply successReply, TPException failureReply) {
      this._done = true;
      if (this._task != null) {
         this._task.cancel();
      }

      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TPRequestAsyncReplyImpl/internalReply/" + Thread.currentThread());
      }

      synchronized(this) {
         if (this.getCalled()) {
            if (traceEnabled) {
               ntrace.doTrace("]/TPRequestAsyncReplyImpl/internalReply/05");
            }

            return;
         }
      }

      rdsession receivePlace = null;
      TypedBuffer data = null;
      int mytpurcode = 0;
      int myTPException = 0;
      int convid = -1;
      SessionAcallDescriptor myConvDesc = null;
      if (this._params == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/TPRequestAsyncReplyImpl/internalReply/10");
         }

      } else {
         InvokeInfo invokeInfo;
         if ((invokeInfo = this._params.get_invokeInfo()) == null) {
            if (traceEnabled) {
               ntrace.doTrace("]/TPRequestAsyncReplyImpl/internalReply/20");
            }

         } else {
            dsession rplyObj;
            if ((rplyObj = (dsession)this._params.get_gwatmi()) == null) {
               if (traceEnabled) {
                  ntrace.doTrace("]/TPRequestAsyncReplyImpl/internalReply/30");
               }

            } else {
               Serializable rd;
               if ((rd = invokeInfo.getReqid()) == null) {
                  if (traceEnabled) {
                     ntrace.doTrace("]/TPRequestAsyncReplyImpl/internalReply/40");
                  }

               } else {
                  tfmh service_tmmsg;
                  if ((service_tmmsg = invokeInfo.getServiceMessage()) == null) {
                     synchronized(this) {
                        rplyObj.send_failure_return(rd, new TPException(4), convid);
                        this.setCalled(true);
                     }

                     if (traceEnabled) {
                        ntrace.doTrace("]/TPRequestAsyncReplyImpl/internalReply/50");
                     }

                  } else {
                     TdomTcb tdom;
                     if (service_tmmsg.tdom != null && (tdom = (TdomTcb)service_tmmsg.tdom.body) != null) {
                        if ((convid = tdom.get_convid()) != -1) {
                           myConvDesc = new SessionAcallDescriptor(convid, true);
                           receivePlace = rplyObj.get_rcv_place();
                        }

                        if (successReply != null) {
                           data = successReply.getReplyBuffer();
                           mytpurcode = successReply.gettpurcode();
                        }

                        if (failureReply != null) {
                           if (traceEnabled) {
                              ntrace.doTrace("/TPRequestAsyncReplyImpl/internalRely/tpReplyerro " + failureReply);
                           }

                           myTPException = failureReply.gettperrno();
                           if (failureReply instanceof TPReplyException) {
                              Reply rtnObj = ((TPReplyException)failureReply).getExceptionReply();
                              if (rtnObj != null) {
                                 data = rtnObj.getReplyBuffer();
                                 mytpurcode = rtnObj.gettpurcode();
                              }
                           }
                        }

                        if ((tdom.get_flag() & 4) != 0) {
                           if (traceEnabled) {
                              ntrace.doTrace("]/TPRequestAsyncReplyImpl/internalReply/TPNOREPLY set");
                           }

                        } else {
                           tfmh tmmsg;
                           if (data == null) {
                              tmmsg = new tfmh(1);
                           } else {
                              tcm user = new tcm((short)0, new UserTcb(data));
                              tmmsg = new tfmh(data.getHintIndex(), user, 1);
                           }

                           try {
                              if (traceEnabled) {
                                 ntrace.doTrace("]/TPRequestAsyncReplyImpl/internalReply/sending success " + rd);
                              }

                              synchronized(this) {
                                 rplyObj.send_success_return(rd, tmmsg, myTPException, mytpurcode, convid);
                                 this.setCalled(true);
                              }
                           } catch (TPException var25) {
                              TPException te = var25;
                              if (convid == -1) {
                                 synchronized(this) {
                                    rplyObj.send_failure_return(rd, te, convid);
                                    this.setCalled(true);
                                 }
                              } else {
                                 receivePlace.remove_rplyObj(myConvDesc);
                              }

                              this._params.removeUser();
                              if (traceEnabled) {
                                 ntrace.doTrace("]/TPRequestAsyncReplyImpl/internalReply/70/" + var25);
                              }

                              return;
                           }

                           if (convid != -1) {
                              receivePlace.remove_rplyObj(myConvDesc);
                           }

                           this._params.removeUser();
                           if (traceEnabled) {
                              ntrace.doTrace("]/TPRequestAsyncReplyImpl/internalReply/80");
                           }

                        }
                     } else {
                        synchronized(this) {
                           rplyObj.send_failure_return(rd, new TPException(4), convid);
                           this.setCalled(true);
                        }

                        if (traceEnabled) {
                           ntrace.doTrace("]/TPRequestAsyncReplyImpl/internalReply/60");
                        }

                     }
                  }
               }
            }
         }
      }
   }

   public void success(Reply successReply) {
      this.internalReply(successReply, (TPException)null);
   }

   public void failure(TPException failureReply) {
      this.internalReply((Reply)null, failureReply);
   }

   protected void setCalled(boolean val) {
      this._called = val;
   }

   protected boolean getCalled() {
      return this._called;
   }

   protected void setTimeoutTask(TimerTask tt) {
      this._task = tt;
   }

   protected boolean isDone() {
      return this._done;
   }
}
