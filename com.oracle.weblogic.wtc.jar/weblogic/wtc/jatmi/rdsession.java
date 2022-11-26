package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.ntrace;
import com.bea.core.jatmi.internal.TCSecurityManager;
import com.bea.core.jatmi.internal.TCTaskHelper;
import com.bea.core.jatmi.intf.TCAuthenticatedUser;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Timer;
import javax.security.auth.login.LoginException;

public final class rdsession {
   private transient DataOutputStream ostream;
   private transient HashMap rplyObjMap;
   private transient HashMap rplyXidObjMap;
   private transient boolean is_term = false;
   private transient int dom_protocol;
   private transient String local_domain_name;
   private transient gwatmi myGwatmi;
   private transient InvokeSvc myInvoker;
   private transient dsession mySession;
   private int uid;
   private BetaFeatures useBetaFeatures;
   private transient Timer myTimeService;
   private transient long myBlocktime;
   private transient TuxXidRply unknownXidRply;
   private transient ArrayList tfmhList;
   private transient ArrayList objectArrayList;
   private transient ArrayList dispatchList;
   private transient HashMap conversationReplyObjects;
   private static final int MAXCACHE = 10;

   public rdsession(DataOutputStream dom_ostream, gwatmi ss, InvokeSvc invoke, int protocol, String ldn, Timer timeService, TuxXidRply anXidRply, BetaFeatures betaFeatures) {
      this.ostream = dom_ostream;
      this.rplyObjMap = new HashMap();
      this.rplyXidObjMap = new HashMap();
      this.myGwatmi = ss;
      this.dom_protocol = protocol;
      this.local_domain_name = ldn;
      this.myTimeService = timeService;
      this.unknownXidRply = anXidRply;
      this.myInvoker = invoke;
      this.tfmhList = new ArrayList();
      this.objectArrayList = new ArrayList();
      this.dispatchList = new ArrayList();
      this.conversationReplyObjects = new HashMap();
      this.useBetaFeatures = betaFeatures;
   }

   public void set_BlockTime(long blocktime) {
      this.myBlocktime = blocktime;
   }

   public InvokeSvc get_invoker() {
      return this.myInvoker;
   }

   public boolean isTerm() {
      return this.is_term;
   }

   public void connectionHasTerminated() {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      this.is_term = true;
      this.myGwatmi.setIsTerminated();
      HashMap reqIds = new HashMap();
      synchronized(this.rplyObjMap) {
         Iterator li1 = this.rplyObjMap.keySet().iterator();

         while(true) {
            if (!li1.hasNext()) {
               break;
            }

            SessionAcallDescriptor tmpReqId = (SessionAcallDescriptor)li1.next();
            reqIds.put(tmpReqId, this.rplyObjMap.get(tmpReqId));
         }
      }

      Iterator li2 = reqIds.keySet().iterator();

      TdomTcb myTimeTask;
      while(li2.hasNext()) {
         rdtimer myTimeTask = null;
         SessionAcallDescriptor myReqid = (SessionAcallDescriptor)li2.next();
         Object[] myValue = (Object[])((Object[])reqIds.get(myReqid));
         if (myValue[1] != null) {
            myTimeTask = (rdtimer)myValue[1];
            myTimeTask.cancel();
            myValue[1] = null;
         }

         if (myReqid.hasCallBack() && myValue[2] != null) {
            GatewayTpacallAsyncReply cb = (GatewayTpacallAsyncReply)myValue[2];
            TPException tpe = new TPException(12, "ERROR: Lost session connection for session <" + this.mySession.get_local_domain_name() + ", " + this.mySession.getRemoteDomainId() + ">.");
            cb.failure(this.myGwatmi, myReqid, tpe);
            if (traceEnabled) {
               ntrace.doTrace("/rdsession/connectionHasTerminated/8: Async TPESYSTEM sent");
            }
         } else {
            myTimeTask = new TdomTcb(3, myReqid.getCd(), 4194304, (String)null);
            myTimeTask.set_diagnostic(12);
            tfmh fail_tmmsg = new tfmh(1);
            fail_tmmsg.tdom = new tcm((short)7, myTimeTask);
            if (this.remove_rplyObj(myReqid)) {
               ReplyQueue rplyObj = (ReplyQueue)myValue[0];
               rplyObj.add_reply(this.myGwatmi, myReqid, fail_tmmsg);
               if (traceEnabled) {
                  ntrace.doTrace("/rdsession/connectionHasTerminated/10: TPESYSTEM sent");
               }
            }
         }
      }

      HashMap txIds = new HashMap();
      synchronized(this.rplyXidObjMap) {
         Iterator li3 = this.rplyXidObjMap.keySet().iterator();

         while(true) {
            if (!li3.hasNext()) {
               break;
            }

            Txid tmpTxid = (Txid)li3.next();
            txIds.put(tmpTxid, this.rplyXidObjMap.get(tmpTxid));
         }
      }

      Iterator li4 = txIds.keySet().iterator();

      tfmh fail_tmmsg;
      TdomTcb fail_tmmsg_tdom;
      while(li4.hasNext()) {
         myTimeTask = null;
         Txid myTxid = (Txid)li4.next();
         fail_tmmsg_tdom = new TdomTcb(3, 0, 0, (String)null);
         fail_tmmsg_tdom.set_diagnostic(12);
         TdomTranTcb fail_tmmsg_tdom_tran = new TdomTranTcb(myTxid);
         fail_tmmsg = new tfmh(1);
         fail_tmmsg.tdom = new tcm((short)7, fail_tmmsg_tdom);
         fail_tmmsg.tdomtran = new tcm((short)10, fail_tmmsg_tdom_tran);
         Object[] myValue = (Object[])((Object[])txIds.get(myTxid));
         if (myValue[1] != null) {
            rdXtimer myTimeTask = (rdXtimer)myValue[1];
            myTimeTask.cancel();
            myValue[1] = null;
         }

         if (this.remove_rplyXidObj(myTxid)) {
            TuxXidRply rplyObj = (TuxXidRply)myValue[0];
            rplyObj.add_reply(this.myGwatmi, myTxid, fail_tmmsg);
            if (traceEnabled) {
               ntrace.doTrace("/rdsession/connectionHasTerminated/20: trans TPESYSTEM sent");
            }
         }
      }

      HashMap mySessionRMICallList = this.mySession.getRMICallList();
      if (mySessionRMICallList != null) {
         Iterator reqInfos = mySessionRMICallList.values().iterator();

         while(reqInfos.hasNext()) {
            Object[] reqInfo = (Object[])((Object[])reqInfos.next());
            int myReqid = (Integer)reqInfo[2];
            fail_tmmsg_tdom = new TdomTcb(3, myReqid, 4194304, (String)null);
            fail_tmmsg_tdom.set_diagnostic(12);
            fail_tmmsg = new tfmh(1);
            fail_tmmsg.tdom = new tcm((short)7, fail_tmmsg_tdom);
            reqInfos.remove();
            if (traceEnabled) {
               ntrace.doTrace("/rdsession/connectionHasTerminated/30: send exception as reply to RMI/IIOP call: GIOPreqId =" + myReqid);
            }

            RMIReplyRequest rmiRplyReq = new RMIReplyRequest(fail_tmmsg, reqInfo, this.mySession);
            rmiRplyReq.execute();
         }
      }

   }

   public void restoreTfmhToCache(tfmh msg) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/rdsession(" + this.uid + ")/restoreTfmhToCache/");
      }

      if (msg == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/rdsession(" + this.uid + ")/restoreTfmhToCache/10/");
         }

      } else if (!msg.prepareForCache()) {
         if (traceEnabled) {
            ntrace.doTrace("]/rdsession(" + this.uid + ")/restoreTfmhToCache/20/");
         }

      } else {
         synchronized(this.tfmhList) {
            if (this.tfmhList.size() < 10) {
               this.tfmhList.add(msg);
               if (traceEnabled) {
                  ntrace.doTrace("/rdsession(" + this.uid + ")/addedtocache=" + msg);
               }
            } else if (traceEnabled) {
               ntrace.doTrace("/rdsession(" + this.uid + ")/notaddedtocache=" + msg);
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/rdsession(" + this.uid + ")/restoreTfmhToCache/30/");
         }

      }
   }

   public tfmh allocTfmh() {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/rdsession(" + this.uid + ")/allocTfmh/");
      }

      tfmh ret = null;
      synchronized(this.tfmhList) {
         int size;
         if ((size = this.tfmhList.size()) != 0) {
            --size;
            ret = (tfmh)this.tfmhList.remove(size);
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("/rdsession(" + this.uid + ")/allocTfmh/" + ret);
      }

      if (ret == null) {
         ret = new tfmh(1);
      }

      if (traceEnabled) {
         ntrace.doTrace("]/rdsession(" + this.uid + ")/allocTfmh/" + ret);
      }

      return ret;
   }

   public void restoreExecuteRequestToCache(MuxableExecute me) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/rdsession(" + this.uid + ")/restoreExecuteRequestToCache/");
      }

      if (me == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/rdsession(" + this.uid + ")/restoreExecuteRequestToCache/10");
         }

      } else {
         synchronized(this.dispatchList) {
            if (this.dispatchList.size() < 10) {
               this.dispatchList.add(me);
               if (traceEnabled) {
                  ntrace.doTrace("/rdsession(" + this.uid + ")/restoreExecuteRequestToCache/addedtocache=" + me);
               }
            } else if (traceEnabled) {
               ntrace.doTrace("/rdsession(" + this.uid + ")/restoreExecuteRequestToCache/notaddedtocache=" + me);
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/rdsession(" + this.uid + ")/restoreExecuteRequestToCache/20");
         }

      }
   }

   private void restoreObjectArray(Object[] oarr) {
      oarr[0] = null;
      oarr[1] = null;
      oarr[2] = null;
      oarr[3] = null;
      synchronized(this.objectArrayList) {
         if (this.objectArrayList.size() < 10) {
            this.objectArrayList.add(oarr);
         }

      }
   }

   private Object[] allocObjectArray() {
      Object[] ret = null;
      synchronized(this.objectArrayList) {
         int size = this.objectArrayList.size();
         if (size > 0) {
            --size;
            ret = (Object[])((Object[])this.objectArrayList.remove(size));
         }
      }

      if (ret == null) {
         ret = new Object[4];
      }

      return ret;
   }

   public synchronized void add_rplyObj(SessionAcallDescriptor cd, ReplyQueue rplyObj, int blocktime, TpacallAsyncReply callBack) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/rdsession(" + this.uid + ")/add_rplyObj/" + cd + "/" + rplyObj + "/" + blocktime);
      }

      long use_blocktime = -1L;
      rdtimer myTimeTask = null;
      if (cd != null && rplyObj != null) {
         if (blocktime == 0) {
            use_blocktime = this.myBlocktime;
         } else if (blocktime > 0) {
            use_blocktime = (long)blocktime * 1000L;
         } else {
            if (blocktime != -1) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/rdsession(" + this.uid + ")/add_rplyObj/20/");
               }

               throw new TPException(4, "Invalid blocking time " + blocktime);
            }

            use_blocktime = -1L;
         }

         Object[] myValue = this.allocObjectArray();
         if (use_blocktime != -1L) {
            myTimeTask = new rdtimer(this, this.myGwatmi, cd, rplyObj);
         }

         myValue[0] = rplyObj;
         myValue[1] = myTimeTask;
         myValue[2] = callBack;
         myValue[3] = null;
         this.rplyObjMap.put(cd, myValue);
         if (myTimeTask != null) {
            try {
               this.myTimeService.schedule(myTimeTask, use_blocktime);
            } catch (IllegalArgumentException var11) {
               this.rplyObjMap.remove(cd);
               throw new TPException(4, "Could not schedule block time " + var11);
            } catch (IllegalStateException var12) {
               this.rplyObjMap.remove(cd);
               throw new TPException(4, "Could not schedule block time " + var12);
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/rdsession(" + this.uid + ")/add_rplyObj/40/");
         }

      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/rdsession(" + this.uid + ")/add_rplyObj/10/");
         }

      }
   }

   public synchronized void addTimeoutRequest(int giopReqId, int reqId, long blocktime) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(60000);
      if (traceEnabled) {
         ntrace.doTrace("[/rdsession(" + this.uid + ")/addTimeoutRequest/10/" + reqId + "/" + blocktime);
      }

      rdCtimer myTimeTask = new rdCtimer(this.mySession, reqId, giopReqId);

      try {
         this.myTimeService.schedule(myTimeTask, blocktime);
         if (traceEnabled) {
            ntrace.doTrace("[/rdsession(" + this.uid + ")/addTimeoutRequest/20/" + reqId + "/" + blocktime + "/Time out scheduled.");
         }

      } catch (IllegalArgumentException var8) {
         if (traceEnabled) {
            ntrace.doTrace("[/rdsession(" + this.uid + ")/addTimeoutRequest/30/" + reqId + "/" + blocktime + "/Could not schedule time out: " + var8);
         }

         throw new TPException(4, "Could not schedule block time " + var8);
      } catch (IllegalStateException var9) {
         if (traceEnabled) {
            ntrace.doTrace("[/rdsession(" + this.uid + ")/addTimeoutRequest/40/" + reqId + "/" + blocktime + "/Could not schedule time out: " + var9);
         }

         throw new TPException(4, "Could not schedule block time " + var9);
      }
   }

   public synchronized boolean addRplyObjTimeout(SessionAcallDescriptor cd, int blocktime) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/rdsession(" + this.uid + ")/addRplyObjTimeout/" + cd + "/" + blocktime);
      }

      long use_blocktime = -1L;
      rdtimer myTimeTask = null;
      if (cd == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/rdsession(" + this.uid + ")/addRplyObjTimeout/10/false");
         }

         return false;
      } else {
         if (blocktime == 0) {
            use_blocktime = this.myBlocktime;
         } else {
            if (blocktime <= 0) {
               if (blocktime == -1) {
                  if (traceEnabled) {
                     ntrace.doTrace("]/rdsession(" + this.uid + ")/addRplyObjTimeout/20/true");
                  }

                  return true;
               }

               if (traceEnabled) {
                  ntrace.doTrace("]/rdsession(" + this.uid + ")/addRplyObjTimeout/30/false");
               }

               return false;
            }

            use_blocktime = (long)blocktime * 1000L;
         }

         Object[] myValue;
         if ((myValue = (Object[])((Object[])this.rplyObjMap.get(cd))) == null) {
            if (traceEnabled) {
               ntrace.doTrace("]/rdsession(" + this.uid + ")/addRplyObjTimeout/40/true");
            }

            return true;
         } else {
            ReplyQueue rplyObj;
            if ((rplyObj = (ReplyQueue)myValue[0]) == null) {
               if (traceEnabled) {
                  ntrace.doTrace("]/rdsession(" + this.uid + ")/addRplyObjTimeout/50/false");
               }

               return false;
            } else {
               if ((myTimeTask = (rdtimer)myValue[1]) != null) {
                  myTimeTask.cancel();
                  myValue[1] = null;
               }

               myTimeTask = new rdtimer(this, this.myGwatmi, cd, rplyObj);
               myValue[1] = myTimeTask;

               try {
                  this.myTimeService.schedule(myTimeTask, use_blocktime);
               } catch (IllegalArgumentException var10) {
                  if (traceEnabled) {
                     ntrace.doTrace("]/rdsession(" + this.uid + ")/addRplyObjTimeout/60/false/" + var10);
                  }

                  return false;
               } catch (IllegalStateException var11) {
                  if (traceEnabled) {
                     ntrace.doTrace("]/rdsession(" + this.uid + ")/addRplyObjTimeout/70/false/" + var11);
                  }

                  return false;
               }

               if (traceEnabled) {
                  ntrace.doTrace("]/rdsession(" + this.uid + ")/addRplyObjTimeout/80/true");
               }

               return true;
            }
         }
      }
   }

   public synchronized boolean remove_rplyObj(SessionAcallDescriptor cd) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/rdsession(" + this.uid + ")/remove_rplyObj/" + cd);
      }

      if (cd == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/rdsession(" + this.uid + ")/remove_rplyObj/10/false");
         }

         return false;
      } else if (this.rplyObjMap.remove(cd) != null) {
         if (traceEnabled) {
            ntrace.doTrace("]/rdsession(" + this.uid + ")/remove_rplyObj/20/true");
         }

         return true;
      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/rdsession(" + this.uid + ")/remove_rplyObj/30/false");
         }

         return false;
      }
   }

   public synchronized Object[] removeReplyObj(SessionAcallDescriptor cd) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/rdsession(" + this.uid + ")/removeReplyObj/" + cd);
      }

      if (cd == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/rdsession(" + this.uid + ")/removeReplyObj/10/null");
         }

         return null;
      } else {
         Object[] retObj = (Object[])((Object[])this.rplyObjMap.remove(cd));
         if (traceEnabled) {
            ntrace.doTrace("]/rdsession(" + this.uid + ")/removeReplyObj/20/" + Arrays.toString(retObj));
         }

         return retObj;
      }
   }

   private synchronized void addlist(tfmh tmmsg, int opcode) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/rdsession(" + this.uid + ")/addlist/" + tmmsg + "/" + opcode);
      }

      rdtimer myTimeTask = null;
      TdomTcb tdom = (TdomTcb)tmmsg.tdom.body;
      int conversationIdentifier = tdom.get_convid();
      SessionAcallDescriptor tdomkey;
      if (opcode != 5 && opcode != 6 && conversationIdentifier == -1) {
         int reqid = tdom.get_reqid();
         tdomkey = new SessionAcallDescriptor(reqid, false);
      } else {
         tdomkey = new SessionAcallDescriptor(conversationIdentifier, true);
      }

      if (traceEnabled) {
         ntrace.doTrace("/rdsession(" + this.uid + ")/addlist/" + tdomkey);
      }

      Object[] myValue;
      if (opcode == 5) {
         myValue = (Object[])((Object[])this.rplyObjMap.get(tdomkey));
      } else {
         myValue = (Object[])((Object[])this.rplyObjMap.remove(tdomkey));
      }

      if (myValue != null) {
         if (opcode != 5 && (myTimeTask = (rdtimer)myValue[1]) != null) {
            myTimeTask.cancel();
         }

         ReplyQueue rplyObj = (ReplyQueue)myValue[0];
         GatewayTpacallAsyncReply callBack = (GatewayTpacallAsyncReply)myValue[2];
         if (callBack == null) {
            rplyObj.add_reply(this.myGwatmi, tdomkey, tmmsg);
         } else {
            TpacallAsyncExecute tae = new TpacallAsyncExecute(this.myGwatmi, tmmsg, tdomkey, callBack);
            TCTaskHelper.schedule(tae);
         }

         if (opcode != 5) {
            this.restoreObjectArray(myValue);
         }

         if (traceEnabled) {
            ntrace.doTrace("]/rdsession(" + this.uid + ")/addlist/10/");
         }

      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/rdsession(" + this.uid + ")/addlist/20/");
         }

      }
   }

   public synchronized void add_rplyXidObj(Txid txid, TuxXidRply rplyXidObj, int blocktime) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/rdsession(" + this.uid + ")/add_rplyXidObj/" + txid + "/" + rplyXidObj);
      }

      long use_blocktime = -1L;
      rdXtimer myXTimeTask = null;
      if (txid != null && rplyXidObj != null) {
         if (blocktime == 0) {
            use_blocktime = this.myBlocktime;
         } else if (blocktime > 0) {
            use_blocktime = (long)blocktime * 1000L;
         } else {
            if (blocktime != -1) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/rdsession(" + this.uid + ")/add_rplyXidObj/20/");
               }

               throw new TPException(4, "Invalid blocking time " + blocktime);
            }

            use_blocktime = -1L;
         }

         if (use_blocktime != -1L) {
            myXTimeTask = new rdXtimer(this, this.myGwatmi, txid, rplyXidObj);
         }

         Boolean replyProcessed = new Boolean(false);
         Object[] myValue = (Object[])((Object[])this.rplyXidObjMap.get(txid));
         if (myValue == null) {
            myValue = this.allocObjectArray();
         } else {
            replyProcessed = (Boolean)myValue[3];
         }

         myValue[0] = rplyXidObj;
         myValue[1] = myXTimeTask;
         myValue[3] = replyProcessed;
         this.rplyXidObjMap.put(txid, myValue);
         if (myXTimeTask != null) {
            try {
               this.myTimeService.schedule(myXTimeTask, use_blocktime);
            } catch (IllegalArgumentException var11) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/rdsession(" + this.uid + ")/add_rplyXidObj/30/" + var11);
               }

               throw new TPException(12, "Unable to set transaction timeout " + var11);
            } catch (IllegalStateException var12) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/rdsession(" + this.uid + ")/add_rplyXidObj/40/" + var12);
               }

               throw new TPException(12, "Unable to set transaction timeout " + var12);
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/rdsession(" + this.uid + ")/add_rplyXidObj/50/");
         }

      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/rdsession(" + this.uid + ")/add_rplyXidObj/10/");
         }

      }
   }

   public synchronized boolean remove_rplyXidObj(Txid txid) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/rdsession(" + this.uid + ")/remove_rplyXidObj/" + txid);
      }

      if (txid == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/rdsession(" + this.uid + ")/remove_rplyXidObj/10/false");
         }

         return false;
      } else if (this.rplyXidObjMap.remove(txid) != null) {
         if (traceEnabled) {
            ntrace.doTrace("]/rdsession(" + this.uid + ")/remove_rplyXidObj/20/true");
         }

         return true;
      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/rdsession(" + this.uid + ")/remove_rplyXidObj/30/false");
         }

         return false;
      }
   }

   private synchronized void addXidlist(tfmh tmmsg) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/rdsession(" + this.uid + ")/addXidlist/" + tmmsg);
      }

      boolean ignoreDuplicateOpcodes = false;
      Boolean replyProcessed = new Boolean(false);
      TdomTranTcb tdomtran = (TdomTranTcb)tmmsg.tdomtran.body;
      Txid tdomkey = new Txid(tdomtran.getGlobalTransactionId());
      if (traceEnabled) {
         ntrace.doTrace("/rdsession(" + this.uid + ")/addXidlist/" + tdomkey);
      }

      TdomTcb tdom = (TdomTcb)tmmsg.tdom.body;
      int opcode = tdom.get_opcode();
      Object[] myValue;
      if (opcode == 8) {
         ignoreDuplicateOpcodes = true;
         myValue = (Object[])((Object[])this.rplyXidObjMap.get(tdomkey));
      } else {
         myValue = (Object[])((Object[])this.rplyXidObjMap.remove(tdomkey));
      }

      if (myValue != null) {
         if (ignoreDuplicateOpcodes) {
            replyProcessed = (Boolean)myValue[3];
         }

         TuxXidRply rplyXidObj;
         rdXtimer myXTimeTask;
         if (!replyProcessed) {
            rplyXidObj = (TuxXidRply)myValue[0];
            myXTimeTask = (rdXtimer)myValue[1];
         } else {
            rplyXidObj = null;
            myXTimeTask = null;
         }

         if (myXTimeTask != null) {
            myXTimeTask.cancel();
         }

         if (ignoreDuplicateOpcodes && !replyProcessed) {
            myValue[3] = new Boolean(true);
            this.rplyXidObjMap.put(tdomkey, myValue);
         }

         if (rplyXidObj != null) {
            rplyXidObj.add_reply(this.myGwatmi, tdomkey, tmmsg);
            if (!ignoreDuplicateOpcodes) {
               this.restoreObjectArray(myValue);
            }
         } else if (traceEnabled) {
            ntrace.doTrace("]/rdsession(" + this.uid + ")/addXidlist/ignoring duplicate opcode " + TdomTcb.print_opcode(opcode) + "/");
         }

         if (traceEnabled) {
            ntrace.doTrace("]/rdsession(" + this.uid + ")/addXidlist/10/");
         }

      } else {
         this.unknownXidRply.add_reply(this.myGwatmi, tdomkey, tmmsg);
         if (traceEnabled) {
            ntrace.doTrace("]/rdsession(" + this.uid + ")/addXidlist/20/");
         }

      }
   }

   private ReplyQueue allocConversationReplyObject(int convid) {
      ConversationReply newQueue = new ConversationReply();
      Integer key = new Integer(convid);
      synchronized(this.conversationReplyObjects) {
         this.conversationReplyObjects.put(key, newQueue);
         return newQueue;
      }
   }

   public ConversationReply getConversationReply(int convid) {
      Integer key = new Integer(convid);
      synchronized(this.conversationReplyObjects) {
         ConversationReply ret = (ConversationReply)this.conversationReplyObjects.remove(key);
         return ret;
      }
   }

   public void dispatch(tfmh tmmsg) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/rdsession(" + this.uid + ")/dispatch/");
      }

      TPServiceRequest newRequest = null;
      HashMap mySessionRMICallList = this.mySession.getRMICallList();
      Object[] reqInfo = null;
      TCAuthenticatedUser subj = null;
      if (tmmsg.tdom == null) {
         this.is_term = true;
         this.myGwatmi.setIsTerminated();
         if (traceEnabled) {
            ntrace.doTrace("]/rdsession(" + this.uid + ")/dispatch/10/");
         }

      } else {
         this.mySession.updateLastReceiveTime();
         TdomTcb tdom = (TdomTcb)tmmsg.tdom.body;
         int reqId = tdom.get_reqid();
         int opcode = tdom.get_opcode();
         if (traceEnabled) {
            ntrace.doTrace("/rdsession(" + this.uid + ")/dispatch/TdomTcb=" + tdom);
            if (tmmsg.tdomtran != null) {
               ntrace.doTrace("/rdsession(" + this.uid + ")/dispatch/TdomTranTcb=" + (TdomTranTcb)tmmsg.tdomtran.body);
            }
         }

         if (traceEnabled && tmmsg.meta != null) {
            MetaTcb meta = (MetaTcb)tmmsg.meta.body;
            ntrace.doTrace("/rdsession(" + this.uid + ")/dispatch/ECID=" + meta.getECID());
         }

         if (opcode == 1 || opcode == 4) {
            String username;
            if (this.dom_protocol >= 15 && this.mySession.getAclPolicy() == 1) {
               if (tmmsg.AAA != null) {
                  AaaTcb aaa = (AaaTcb)tmmsg.AAA.body;
                  username = aaa.getATZUserName();
               } else {
                  username = null;
               }
            } else {
               username = this.mySession.getRemoteDomainId();
            }

            try {
               subj = TCSecurityManager.impersonate(username);
            } catch (LoginException var27) {
               tfmh fail_tmmsg = new tfmh(1);
               TdomTcb fail_tmmsg_tdom = new TdomTcb(3, reqId, 0, (String)null);
               fail_tmmsg_tdom.set_diagnostic(8);
               fail_tmmsg.tdom = new tcm((short)7, fail_tmmsg_tdom);
               synchronized(this.ostream) {
                  try {
                     if (this.dom_protocol >= 15) {
                        fail_tmmsg.write_tfmh(this.ostream, this.mySession.getCompressionThreshold());
                     } else {
                        fail_tmmsg.write_dom_65_tfmh(this.ostream, this.local_domain_name, this.dom_protocol, this.mySession.getCompressionThreshold());
                     }
                  } catch (IOException var25) {
                     if (traceEnabled) {
                        ntrace.doTrace("]/rdsession(" + this.uid + ")/dispatch/15/fail to send failure reply: " + var25);
                     }
                  }
               }

               if (traceEnabled) {
                  ntrace.doTrace("]/rdsession(" + this.uid + ")/dispatch/20/Failed to get user identity: " + var27);
               }

               return;
            }
         }

         switch (opcode) {
            case 2:
            case 3:
            case 5:
            case 6:
               if (traceEnabled) {
                  ntrace.doTrace("/rdsession/dispatch/25/reqId = " + reqId);
               }

               if (mySessionRMICallList != null) {
                  synchronized(mySessionRMICallList) {
                     if ((reqInfo = (Object[])((Object[])mySessionRMICallList.remove(new Integer(reqId)))) != null) {
                        if (traceEnabled) {
                           ntrace.doTrace("/rdsession/dispatch/30:reply to RMI/IIOP call: reqId =" + reqId);
                        }

                        RMIReplyRequest rmiRplyReq = new RMIReplyRequest(tmmsg, reqInfo, this.mySession);
                        rmiRplyReq.execute();
                        break;
                     }
                  }
               }

               this.addlist(tmmsg, opcode);
               break;
            case 4:
               int conversationIdentifier = tdom.get_convid();
               SessionAcallDescriptor convDesc = new SessionAcallDescriptor(conversationIdentifier, true);
               ReplyQueue convReplyQueue = this.allocConversationReplyObject(conversationIdentifier);

               try {
                  this.add_rplyObj(convDesc, convReplyQueue, 0, (TpacallAsyncReply)null);
               } catch (TPException var24) {
                  if (traceEnabled) {
                     ntrace.doTrace("]/rdsession(" + this.uid + ")/dispatch/Error dispatching conversational service" + var24);
                  }

                  return;
               }
            case 1:
               try {
                  newRequest = new TPServiceRequest(this.myGwatmi, this.dom_protocol, this.local_domain_name, this.ostream, this.myInvoker, tmmsg, this.useBetaFeatures);
               } catch (TPException var23) {
                  if (traceEnabled) {
                     ntrace.doTrace("]/rdsession(" + this.uid + ")/dispatch/Error dispatching service" + var23);
                  }

                  return;
               }

               if (subj != null) {
                  newRequest.setTargetSubject(subj);
               }

               TCTaskHelper.schedule(newRequest);
               break;
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 24:
               if (traceEnabled) {
                  ntrace.doTrace("/rdsession/dispatch/35/reqId = " + reqId);
               }

               if (mySessionRMICallList != null) {
                  Txid xid = null;
                  tcm tdtran = tmmsg.tdomtran;
                  if (tdtran != null) {
                     TdomTranTcb tdomtran = (TdomTranTcb)tmmsg.tdomtran.body;
                     if (tdomtran != null) {
                        xid = new Txid(tdomtran.getGlobalTransactionId());
                        if (traceEnabled) {
                           ntrace.doTrace("/rdsession/dispatch/37/Txid = " + xid);
                        }
                     }
                  }

                  synchronized(mySessionRMICallList) {
                     Iterator reqInfos = mySessionRMICallList.values().iterator();

                     while(reqInfos.hasNext()) {
                        reqInfo = (Object[])((Object[])reqInfos.next());
                        if (reqInfo[3] != null && xid.equals(reqInfo[3])) {
                           reqInfos.remove();
                           if (traceEnabled) {
                              ntrace.doTrace("/rdsession/dispatch/40: reply to RMI/IIOP call: GIOPreqId =" + reqInfo[2]);
                           }

                           RMIReplyRequest rmiRplyReq = new RMIReplyRequest(tmmsg, reqInfo, this.mySession);
                           rmiRplyReq.execute();
                        }
                     }
                  }
               }

               this.addXidlist(tmmsg);
               break;
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            default:
               this.is_term = true;
               this.myGwatmi.doLocalTerminate();
               if (traceEnabled) {
                  ntrace.doTrace("]/rdsession(" + this.uid + ")/dispatch/50/");
               }

               return;
            case 23:
               if (tdom.get_diagnostic() == 0) {
                  this.mySession.sendKeepAliveAcknowledge();
               }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/rdsession(" + this.uid + ")/dispatch/60/");
         }

      }
   }

   public void setSessionReference(dsession session) {
      this.mySession = session;
      this.uid = session.getUid();
   }

   private byte[] getTranXID(tfmh tmmsg) {
      TdomTranTcb tdomtran = (TdomTranTcb)tmmsg.tdomtran.body;
      return tdomtran.getGlobalTransactionId();
   }
}
