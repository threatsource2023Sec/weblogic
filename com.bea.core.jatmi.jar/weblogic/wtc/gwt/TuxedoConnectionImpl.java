package weblogic.wtc.gwt;

import com.bea.core.jatmi.common.Utilities;
import com.bea.core.jatmi.common.ntrace;
import com.bea.core.jatmi.config.TuxedoConnectorRAP;
import com.bea.core.jatmi.internal.ConfigHelper;
import com.bea.core.jatmi.internal.TCResourceHelper;
import com.bea.core.jatmi.internal.TCRouteEntry;
import com.bea.core.jatmi.internal.TCRouteManager;
import com.bea.core.jatmi.internal.TCTransactionHelper;
import com.bea.core.jatmi.internal.TuxedoDummyXA;
import com.bea.core.jatmi.internal.TuxedoXA;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import javax.naming.Context;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.xa.Xid;
import weblogic.wtc.jatmi.CallDescriptor;
import weblogic.wtc.jatmi.Conversation;
import weblogic.wtc.jatmi.DequeueReply;
import weblogic.wtc.jatmi.EnqueueRequest;
import weblogic.wtc.jatmi.Reply;
import weblogic.wtc.jatmi.ReqMsg;
import weblogic.wtc.jatmi.ReqOid;
import weblogic.wtc.jatmi.TPException;
import weblogic.wtc.jatmi.TPReplyException;
import weblogic.wtc.jatmi.TdomTcb;
import weblogic.wtc.jatmi.TpacallAsyncReply;
import weblogic.wtc.jatmi.TuxRply;
import weblogic.wtc.jatmi.TuxedoReply;
import weblogic.wtc.jatmi.TypedBuffer;
import weblogic.wtc.jatmi.UserRec;
import weblogic.wtc.jatmi.UserTcb;
import weblogic.wtc.jatmi.gwatmi;
import weblogic.wtc.jatmi.tfmh;

public class TuxedoConnectionImpl implements TuxedoConnection {
   private Transaction myTransaction;
   private boolean isRolledBack = false;
   private UserRec myId = null;
   private int tmsndprio = 50;
   private static final int TM_PRIORANGE = 100;
   private static final int TM_SENDBASE = 536870912;
   protected TuxRply myRplyObj;
   protected boolean is_term = false;
   protected TuxedoXA myXAResource;
   private HashMap touchedDomains;
   private gwatmi[] listOfDomains;
   protected OatmialServices tos;
   private TuxedoDummyXA myDummyXAResource = null;
   private ConcurrentHashMap currImpSvc;
   private ConcurrentHashMap cdToImpSvc;
   private ConcurrentHashMap tcdToIcd;

   public TuxedoConnectionImpl() throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxedoConnection/TuxedoConnection/");
      }

      this.tos = ConfigHelper.getTuxedoServices();
      Context nameService = this.tos.getNameService();
      if ((this.myTransaction = TCTransactionHelper.getTransaction()) != null) {
         this.myXAResource = new TuxedoXA(this.tos);

         try {
            this.myTransaction.enlistResource(this.myXAResource);
         } catch (SystemException var5) {
            if (traceEnabled) {
               ntrace.doTrace("*]/TuxedoConnection/TuxedoConnection/TPESYSTEM/10");
            }

            throw new TPException(12, "ERROR: Could not enlist in transaction");
         } catch (RollbackException var6) {
            this.isRolledBack = true;
         } catch (IllegalStateException var7) {
            if (traceEnabled) {
               ntrace.doTrace("*]/TuxedoConnection/TuxedoConnection/TPESYSTEM/20");
            }

            throw new TPException(12, "ERROR: Transaction already prepared");
         }

         Xid foreignXid = null;
         Xid xid = null;
         xid = TCTransactionHelper.getXidFromTransaction(this.myTransaction);
         if (TCResourceHelper.isTightlyCoupledTransactionsEnabled()) {
            foreignXid = (Xid)TCResourceHelper.getTransactionProperty(this.myTransaction, "weblogic.transaction.foreignXid");
         }

         if (foreignXid == null) {
            foreignXid = xid;
         } else {
            this.tos.addOutboundXidToFXid(foreignXid, xid);
         }

         if (!this.isRolledBack) {
            this.myXAResource.start(foreignXid);
         }

         if (traceEnabled) {
            ntrace.doTrace("transaction enlisted " + this.isRolledBack);
         }
      } else if (traceEnabled) {
         ntrace.doTrace("no transaction");
      }

      this.myRplyObj = new TuxRply();
      this.touchedDomains = new HashMap();
      this.listOfDomains = new gwatmi[10];
      this.currImpSvc = new ConcurrentHashMap();
      this.cdToImpSvc = new ConcurrentHashMap();
      this.tcdToIcd = new ConcurrentHashMap();
      if (traceEnabled) {
         ntrace.doTrace("]/TuxedoConnection/TuxedoConnection/30");
      }

   }

   public TuxedoConnectionImpl(int flags) throws TPException {
      if (flags != 8) {
         throw new TPException(4);
      } else {
         this.tos = ConfigHelper.getTuxedoServices();
         this.myRplyObj = new TuxRply();
         this.currImpSvc = new ConcurrentHashMap();
         this.cdToImpSvc = new ConcurrentHashMap();
         this.tcdToIcd = new ConcurrentHashMap();
      }
   }

   public List getProviderRoute(String svc, TypedBuffer data, Xid xid, int flags) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxedoConnection/getProviderRoute/" + svc);
      }

      ArrayList[] sg_array;
      try {
         sg_array = TCRouteManager.selectTargetRoutes(svc, data, xid, flags);
      } catch (TPException var8) {
         if (var8.gettperrno() == 6) {
            this.setRollbackOnly(flags);
         }

         if (traceEnabled) {
            ntrace.doTrace("*]/TuxedoConnection/getProviderRoute/10");
         }

         throw var8;
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TuxedoConnection/getProviderRoute/20");
      }

      return sg_array[0];
   }

   protected CallDescriptor createCallDescriptor(gwatmi myAtmi, CallDescriptor icd, boolean isAsync, boolean inTransaction) {
      synchronized(this.touchedDomains) {
         int index = true;
         int index;
         if (this.touchedDomains.containsKey(myAtmi)) {
            index = (Integer)this.touchedDomains.get(myAtmi);
         } else {
            for(index = 0; index < this.listOfDomains.length && this.listOfDomains[index] != null; ++index) {
            }

            if (index >= this.listOfDomains.length) {
               gwatmi[] newArray = new gwatmi[this.listOfDomains.length + 10];

               for(int lcv = 0; lcv < this.listOfDomains.length; ++lcv) {
                  newArray[lcv] = this.listOfDomains[lcv];
                  this.listOfDomains[lcv] = null;
               }
            }

            this.listOfDomains[index] = myAtmi;
            Integer value = new Integer(index);
            this.touchedDomains.put(myAtmi, value);
         }

         TuxedoCallDescriptor retObj = new TuxedoCallDescriptor(icd, index, isAsync, inTransaction);
         return retObj;
      }
   }

   public CallDescriptor tpacall(String svc, TypedBuffer data, int flags) throws TPException {
      return this.tpacall(svc, data, flags, (TpacallAsyncReply)null);
   }

   public CallDescriptor tpacall(String svc, TypedBuffer data, int flags, TpacallAsyncReply callBack) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxedoConnection/tpacall/" + svc);
      }

      gwatmi myAtmi = null;
      CallDescriptor retObj = null;
      Xid useXid = null;
      int useTimeout = 0;
      TpacallAsyncReplyImpl asyncReply = null;
      boolean inTransaction = false;
      if (this.is_term) {
         if (traceEnabled) {
            ntrace.doTrace("*]/TuxedoConnection/tpacall/10/");
         }

         throw new TPException(9, "ERROR: Session terminated");
      } else if (svc != null && !svc.equals("")) {
         if ((flags & -46) != 0) {
            if (this.myTransaction != null) {
               try {
                  this.myTransaction.setRollbackOnly();
               } catch (SystemException var26) {
                  if (traceEnabled) {
                     ntrace.doTrace("/TuxedoConnection/tpacall/SystemException:" + var26);
                  }
               }

               this.isRolledBack = true;
            }

            if (traceEnabled) {
               ntrace.doTrace("*]/TuxedoConnection/tpacall/30/");
            }

            throw new TPException(4);
         } else if (this.myTransaction != null && this.isRolledBack && (flags & 8) == 0) {
            if (traceEnabled) {
               ntrace.doTrace("*]/TuxedoConnection/tpacall/30/TPEINVAL");
            }

            throw new TPException(4, "Transaction rolled back but TPNOTRAN not specified");
         } else {
            if (this.myXAResource != null && (flags & 8) == 0) {
               inTransaction = true;
               Xid myXid;
               if ((myXid = this.myXAResource.getXid()) == null) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/TuxedoConnection/tpacall/33/TPEPROTO (transaction)");
                  }

                  throw new TPException(9, "ERROR: TuxedoConnection transaction has ended but TPNOTRAN is not set");
               }

               useXid = myXid;
               useTimeout = this.myXAResource.getRealTransactionTimeout();
               flags &= -33;
               if (useTimeout <= 0) {
                  try {
                     this.myTransaction.setRollbackOnly();
                  } catch (SystemException var27) {
                     if (traceEnabled) {
                        ntrace.doTrace("SystemException:" + var27);
                     }
                  }

                  this.isRolledBack = true;
                  if (traceEnabled) {
                     ntrace.doTrace("*]/TuxedoConnection/tpacall/35/TPETIME (transaction)");
                  }

                  throw new TPException(13, "ERROR: This transaction has already timed out");
               }
            }

            List myRoute = this.getProviderRoute(svc, data, useXid, flags);
            TCRouteEntry myEntry = (TCRouteEntry)myRoute.get(0);
            myAtmi = (gwatmi)myEntry.getSessionGroup();
            if (this.myXAResource != null && (flags & 8) == 0 && this.myDummyXAResource == null) {
               TuxedoConnectorRAP[] rdoms = this.tos.getOutboundRdomsAssociatedWithXid(this.myXAResource.getXid());
               if (rdoms.length > 1) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/TuxedoConnection/tpacall/transaction involes at least " + rdoms.length + " branches, add a dummy XA Resource");
                  }

                  this.myDummyXAResource = new TuxedoDummyXA();

                  try {
                     this.myTransaction.enlistResource(this.myDummyXAResource);
                  } catch (SystemException var22) {
                     throw new TPException(12, "ERROR: SystemException dummy resource");
                  } catch (RollbackException var23) {
                     this.isRolledBack = true;
                  } catch (IllegalStateException var24) {
                     throw new TPException(12, "ERROR: IllegalStateException dummy resource");
                  }
               }
            }

            String[] imp_info = new String[]{svc, myEntry.getImpSvc()[0], myEntry.getImpSvc()[1]};
            this.currImpSvc.put(myEntry.getRemoteName(), imp_info);
            asyncReply = callBack == null ? null : new TpacallAsyncReplyImpl(callBack, this, this.myTransaction, myAtmi);
            TuxRply rplyObj;
            if ((flags & 4) == 0) {
               rplyObj = this.myRplyObj;
            } else {
               rplyObj = null;
            }

            CallDescriptor myAtmiReturn;
            try {
               if (this.tmsndprio != 50) {
                  myAtmi.tpsprio(this.tmsndprio, 64);
                  this.tmsndprio = 50;
               }

               myAtmiReturn = myAtmi.tprplycall(rplyObj, myEntry.getRemoteName(), data, flags, useXid, useTimeout, asyncReply, this);
            } catch (TPException var28) {
               String property = System.getProperty("weblogic.wtc.trans_ignore_tpenoent");
               boolean ignoreTPENOENT = false;
               if (property != null && (property.startsWith("y") || property.startsWith("Y"))) {
                  ignoreTPENOENT = true;
               }

               if (this.myTransaction != null && (var28.gettperrno() == 13 || var28.gettperrno() == 6 && !ignoreTPENOENT) && (flags & 8) == 0) {
                  try {
                     this.myTransaction.setRollbackOnly();
                  } catch (SystemException var25) {
                     if (traceEnabled) {
                        ntrace.doTrace("/TuxedoConnection/tpacall/SystemException:" + var25);
                     }
                  }

                  this.isRolledBack = true;
               }

               if (traceEnabled) {
                  ntrace.doTrace("*]/TuxedoConnection/tpacall/40/" + var28);
               }

               throw var28;
            }

            retObj = this.createCallDescriptor(myAtmi, myAtmiReturn, asyncReply != null, inTransaction);
            this.tcdToIcd.put(retObj, myAtmiReturn);
            this.cdToImpSvc.put(myAtmiReturn, this.currImpSvc);
            if (traceEnabled) {
               ntrace.doTrace("]/TuxedoConnection/tpacall/50/" + retObj);
            }

            return retObj;
         }
      } else {
         if (this.myTransaction != null && (flags & 8) == 0) {
            try {
               this.myTransaction.setRollbackOnly();
            } catch (SystemException var29) {
               if (traceEnabled) {
                  ntrace.doTrace("/TuxedoConnection/tpacall/SystemException:" + var29);
               }
            }

            this.isRolledBack = true;
         }

         if (traceEnabled) {
            ntrace.doTrace("*]/TuxedoConnection/tpacall/20/");
         }

         throw new TPException(4);
      }
   }

   public void tpcancel(CallDescriptor cd, int flags) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxedoConnection/tpcancel/" + cd + "/" + flags);
      }

      TuxedoCallDescriptor real_tcd = null;
      if (this.is_term) {
         if (traceEnabled) {
            ntrace.doTrace("*]/TuxedoConnection/tpcancel/10/TPEPROTO");
         }

         throw new TPException(9, "ERROR: Session terminated");
      } else if (cd != null && flags == 0) {
         if (!(cd instanceof TuxedoCallDescriptor)) {
            if (traceEnabled) {
               ntrace.doTrace("*]/TuxedoConnection/tpcancel/30/TPEINVAL");
            }

            throw new TPException(4);
         } else {
            real_tcd = (TuxedoCallDescriptor)cd;
            gwatmi myAtmi = this.listOfDomains[real_tcd.getIndex()];
            CallDescriptor internalDescriptor = real_tcd.getCallDescriptor();
            if (real_tcd.isAssociatedWithATransaction()) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/TuxedoConnection/tpcancel/40/TPETRAN");
               }

               throw new TPException(14);
            } else {
               try {
                  myAtmi.tpcancel(internalDescriptor, 0);
               } catch (TPException var8) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/TuxedoConnection/tpcancel/50/" + var8);
                  }

                  throw var8;
               } catch (Exception var9) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/TuxedoConnection/tpcancel/60/" + var9);
                  }

                  throw new TPException(7, "ERROR: Gateway tpcancel threw: " + var9);
               }

               if (traceEnabled) {
                  ntrace.doTrace("]/TuxedoConnection/tpcancel/70/");
               }

            }
         }
      } else {
         if (traceEnabled) {
            ntrace.doTrace("*]/TuxedoConnection/tpcancel/20/TPEINVAL");
         }

         throw new TPException(4);
      }
   }

   public Reply tpgetrply(CallDescriptor cd, int flags) throws TPException, TPReplyException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxedoConnection/tpgetrply/" + cd + "/" + flags);
      }

      ReqOid real_cd = null;
      TuxedoCallDescriptor real_tcd = null;
      boolean inTransaction = true;
      if (this.is_term) {
         if (traceEnabled) {
            ntrace.doTrace("*]/TuxedoConnection/tpgetrply/05/TPEPROTO");
         }

         throw new TPException(9, "ERROR: Session terminated");
      } else if ((flags & -162) != 0) {
         if (traceEnabled) {
            ntrace.doTrace("*]/TuxedoConnection/tpgetrply/10/TPEINVAL");
         }

         throw new TPException(4);
      } else if (cd == null && (flags & 128) == 0) {
         if (traceEnabled) {
            ntrace.doTrace("*]/TuxedoConnection/tpgetrply/20/TPEINVAL");
         }

         throw new TPException(4, "cd must not be null if TPGETANY is not set");
      } else if (cd != null && (flags & 128) != 0) {
         if (traceEnabled) {
            ntrace.doTrace("*]/TuxedoConnection/tpgetrply/30/TPEINVAL");
         }

         throw new TPException(4, "cd must be null if TPGETANY is set");
      } else {
         boolean block = (flags & 1) == 0;
         if (cd != null) {
            if (cd instanceof ReqOid) {
               real_cd = (ReqOid)cd;
            } else {
               if (!(cd instanceof TuxedoCallDescriptor)) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/TuxedoConnection/tpgetrply/35/TPEINVAL");
                  }

                  throw new TPException(4, "cd has an invalid object type");
               }

               real_tcd = (TuxedoCallDescriptor)cd;
               inTransaction = real_tcd.isAssociatedWithATransaction();
               if (real_tcd.isAsynchronous()) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/TuxedoConnection/tpgetrply/35/TPEINVAL");
                  }

                  throw new TPException(4, "cd is from an asynchronous tpacall");
               }

               real_cd = new ReqOid(real_tcd.getCallDescriptor(), this.listOfDomains[real_tcd.getIndex()]);
            }
         }

         tfmh tmmsg;
         if (real_cd != null) {
            if ((tmmsg = this.myRplyObj.get_specific_reply(real_cd, block)) == null) {
               if (!block) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/TuxedoConnection/tpgetrply/50/TPEBLOCK");
                  }

                  throw new TPException(3);
               }

               if (this.myTransaction != null && inTransaction) {
                  try {
                     this.myTransaction.setRollbackOnly();
                  } catch (SystemException var24) {
                     if (traceEnabled) {
                        ntrace.doTrace("/TuxedoConnection/tpgetrply/SystemException:" + var24);
                     }
                  }

                  this.isRolledBack = true;
               }

               if (traceEnabled) {
                  ntrace.doTrace("*]/TuxedoConnection/tpgetrply/40/TPESYSTEM");
               }

               throw new TPException(12, "ERROR: Connection dropped");
            }
         } else {
            ReqMsg full_reply;
            if ((full_reply = this.myRplyObj.get_reply(block)) == null) {
               if (!block) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/TuxedoConnection/tpgetrply/70/TPEBLOCK");
                  }

                  throw new TPException(3);
               }

               if (this.myTransaction != null) {
                  try {
                     this.myTransaction.setRollbackOnly();
                  } catch (SystemException var25) {
                     if (traceEnabled) {
                        ntrace.doTrace("/TuxedoConnection/tpgetrply/SystemException:" + var25);
                     }
                  }

                  this.isRolledBack = true;
               }

               if (traceEnabled) {
                  ntrace.doTrace("*]/TuxedoConnection/tpgetrply/60/TPESYSTEM");
               }

               throw new TPException(12, "ERROR: Connection dropped");
            }

            tmmsg = full_reply.getReply();
            real_cd = full_reply.getReqOid();
         }

         gwatmi myAtmi = real_cd.getAtmiObject();
         CallDescriptor mycd;
         if (cd != null) {
            mycd = (CallDescriptor)this.tcdToIcd.get(cd);
         } else {
            mycd = (CallDescriptor)this.tcdToIcd.get(real_cd.getReqReturn());
         }

         TdomTcb tdom = (TdomTcb)tmmsg.tdom.body;
         int myTPException = tdom.get_diagnostic();
         int mytpurcode = tdom.getTpurcode();
         int mytperrordetail = tdom.get_errdetail();
         int opcode = tdom.get_opcode();
         TypedBuffer tb;
         if (tmmsg.user == null) {
            tb = null;
         } else {
            UserTcb utcb = (UserTcb)tmmsg.user.body;
            tb = utcb.user_data;
         }

         TuxedoReply ret = new TuxedoReply(tb, mytpurcode, (CallDescriptor)(real_tcd != null ? real_tcd : real_cd));
         if (opcode == 3 && myTPException != 11 && myTPException != 10) {
            String property = System.getProperty("weblogic.wtc.trans_ignore_tpenoent");
            boolean ignoreTPENOENT = false;
            if (property != null && (property.startsWith("y") || property.startsWith("Y"))) {
               ignoreTPENOENT = true;
            }

            if ((myTPException == 18 || myTPException == 13 || myTPException == 6 && !ignoreTPENOENT) && this.myTransaction != null && inTransaction) {
               try {
                  this.myTransaction.setRollbackOnly();
               } catch (SystemException var26) {
                  if (traceEnabled) {
                     ntrace.doTrace("/TuxedoConnection/tpgetrply/SystemException:" + var26);
                  }
               }

               this.isRolledBack = true;
            }

            TPException exceptionReturn = new TPException(myTPException, 0, mytpurcode, mytperrordetail, ret);
            if (traceEnabled) {
               ntrace.doTrace("*]/TuxedoConnection/tpgetrply/80/" + exceptionReturn);
            }

            myAtmi.collect_stat_end(mycd, 0L, false, true);
            if (cd != null) {
               this.tcdToIcd.remove(cd);
            } else {
               this.tcdToIcd.remove(real_cd.getReqReturn());
            }

            this.cdToImpSvc.remove(mycd);
            throw exceptionReturn;
         } else {
            if (myTPException != 11 && myTPException != 10) {
               myTPException = 0;
            }

            if (myTPException == 0) {
               myAtmi.restoreTfmhToCache(tmmsg);
               myAtmi.collect_stat_end(mycd, 0L, true, true);
               if (cd != null) {
                  this.tcdToIcd.remove(cd);
               } else {
                  this.tcdToIcd.remove(real_cd.getReqReturn());
               }

               this.cdToImpSvc.remove(mycd);
               if (traceEnabled) {
                  ntrace.doTrace("]/TuxedoConnection/tpgetrply/100/" + ret);
               }

               return ret;
            } else {
               if (this.myTransaction != null && inTransaction) {
                  try {
                     this.myTransaction.setRollbackOnly();
                  } catch (SystemException var27) {
                     if (traceEnabled) {
                        ntrace.doTrace("/TuxedoConnection/tpgetrply/SystemException:" + var27);
                     }
                  }

                  this.isRolledBack = true;
               }

               TPReplyException exceptionReturn = new TPReplyException(myTPException, 0, mytpurcode, mytperrordetail, ret);
               if (traceEnabled) {
                  ntrace.doTrace("*]/TuxedoConnection/tpgetrply/90/" + exceptionReturn);
               }

               myAtmi.collect_stat_end(mycd, 0L, false, true);
               if (cd != null) {
                  this.tcdToIcd.remove(cd);
               } else {
                  this.tcdToIcd.remove(real_cd.getReqReturn());
               }

               this.cdToImpSvc.remove(mycd);
               throw exceptionReturn;
            }
         }
      }
   }

   public byte[] tpenqueue(String qspace, String qname, EnqueueRequest ctl, TypedBuffer data, int flags) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxedoConnection/tpenqueue/" + qspace + "/" + qname + "/" + ctl + "/" + data + "/" + flags);
      }

      gwatmi myAtmi = null;
      Xid useXid = null;
      int useTimeout = 0;
      byte[] retMsgid = null;
      if (this.is_term) {
         if (traceEnabled) {
            ntrace.doTrace("*]/TuxedoConnection/tpenqueue/10/TPEPROTO");
         }

         throw new TPException(9, "ERROR: Session terminated");
      } else if (qspace != null && !qspace.equals("") && qname != null && !qname.equals("") && ctl != null) {
         if (this.myTransaction != null && this.isRolledBack && (flags & 8) == 0) {
            if (traceEnabled) {
               ntrace.doTrace("*]/TuxedoConnection/tpenqueue/30/TPEINVAL");
            }

            throw new TPException(4, "Transaction rolled back but TPNOTRAN not specified");
         } else {
            if (this.myXAResource != null && (flags & 8) == 0) {
               Xid myXid;
               if ((myXid = this.myXAResource.getXid()) == null) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/TuxedoConnection/tpenqueue/33/TPEPROTO (transaction)");
                  }

                  throw new TPException(9, "ERROR: TuxedoConnection transaction has ended but TPNOTRAN is not set");
               }

               useXid = myXid;
               useTimeout = this.myXAResource.getRealTransactionTimeout();
               flags &= -33;
               if (useTimeout <= 0) {
                  try {
                     this.myTransaction.setRollbackOnly();
                  } catch (SystemException var22) {
                     if (traceEnabled) {
                        ntrace.doTrace("/TuxedoConnection/tpenqueue/SystemException:" + var22);
                     }
                  }

                  this.isRolledBack = true;
                  if (traceEnabled) {
                     ntrace.doTrace("*]/TuxedoConnection/tpenqueue/35/TPETIME (transaction)");
                  }

                  throw new TPException(13, "ERROR: This transaction has already timed out");
               }
            }

            List myRoute = this.getProviderRoute(qspace, data, useXid, flags);
            TCRouteEntry myEntry = (TCRouteEntry)myRoute.get(0);
            myAtmi = (gwatmi)myEntry.getSessionGroup();
            if (this.myXAResource != null && (flags & 8) == 0 && this.myDummyXAResource == null) {
               TuxedoConnectorRAP[] rdoms = this.tos.getOutboundRdomsAssociatedWithXid(this.myXAResource.getXid());
               if (rdoms.length > 1) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/TuxedoConnection/tpenqueue/transaction involes at least " + rdoms.length + " branches, add a dummy XA Resource");
                  }

                  this.myDummyXAResource = new TuxedoDummyXA();

                  try {
                     this.myTransaction.enlistResource(this.myDummyXAResource);
                  } catch (SystemException var18) {
                     throw new TPException(12, "ERROR: SystemException dummy resource");
                  } catch (RollbackException var19) {
                     this.isRolledBack = true;
                  } catch (IllegalStateException var20) {
                     throw new TPException(12, "ERROR: IllegalStateException dummy resource");
                  }
               }
            }

            byte[] retMsgid;
            try {
               if (this.tmsndprio != 50) {
                  myAtmi.tpsprio(this.tmsndprio, 64);
                  this.tmsndprio = 50;
               }

               retMsgid = myAtmi.tpenqueue(myEntry.getRemoteName(), qname, ctl, data, flags, useXid, useTimeout, this);
            } catch (TPException var23) {
               String property = System.getProperty("weblogic.wtc.trans_ignore_tpenoent");
               boolean ignoreTPENOENT = false;
               if (property != null && (property.startsWith("y") || property.startsWith("Y"))) {
                  ignoreTPENOENT = true;
               }

               if (this.myTransaction != null && (var23.gettperrno() == 13 || var23.gettperrno() == 6 && !ignoreTPENOENT || var23.gettperrno() == 24 && (var23.getdiagnostic() == -8 || var23.getdiagnostic() == -4)) && (flags & 8) == 0) {
                  try {
                     this.myTransaction.setRollbackOnly();
                  } catch (SystemException var21) {
                     if (traceEnabled) {
                        ntrace.doTrace("/TuxedoConnection/tpenqueue/SystemException:" + var21);
                     }
                  }

                  this.isRolledBack = true;
               }

               if (traceEnabled) {
                  ntrace.doTrace("*]/TuxedoConnection/tpenqueue/30/" + var23);
               }

               throw var23;
            }

            if (traceEnabled) {
               ntrace.doTrace("]/TuxedoConnection/tpenqueue/40/" + Utilities.prettyByteArray(retMsgid));
            }

            return retMsgid;
         }
      } else {
         if (traceEnabled) {
            ntrace.doTrace("*]/TuxedoConnection/tpenqueue/20/TPEINVAL");
         }

         throw new TPException(4);
      }
   }

   public DequeueReply tpdequeue(String qspace, String qname, byte[] msgid, byte[] corrid, boolean doWait, boolean doPeek, int flags) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxedoConnection/tpdequeue/" + qspace + "/" + qname + "/" + Utilities.prettyByteArray(msgid) + "/" + Utilities.prettyByteArray(corrid) + "/" + doWait + "/" + doPeek + "/" + flags);
      }

      gwatmi myAtmi = null;
      Xid useXid = null;
      int useTimeout = 0;
      DequeueReply dRet = null;
      if (this.is_term) {
         if (traceEnabled) {
            ntrace.doTrace("*]/TuxedoConnection/tpdequeue/10/TPEPROTO");
         }

         throw new TPException(9, "ERROR: Session terminated");
      } else if (qspace != null && !qspace.equals("") && qname != null && !qname.equals("")) {
         if (doPeek) {
            flags |= 8;
         }

         if (this.myTransaction != null && this.isRolledBack && (flags & 8) == 0) {
            if (traceEnabled) {
               ntrace.doTrace("*]/TuxedoConnection/tpdequeue/30/TPEINVAL");
            }

            throw new TPException(4, "Transaction rolled back but TPNOTRAN not specified");
         } else {
            if (this.myXAResource != null && (flags & 8) == 0) {
               Xid myXid;
               if ((myXid = this.myXAResource.getXid()) == null) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/TuxedoConnection/tpdequeue/33/TPEPROTO (transaction)");
                  }

                  throw new TPException(9, "ERROR: TuxedoConnection transaction has ended but TPNOTRAN is not set");
               }

               useXid = myXid;
               useTimeout = this.myXAResource.getRealTransactionTimeout();
               flags &= -33;
               if (useTimeout <= 0) {
                  try {
                     this.myTransaction.setRollbackOnly();
                  } catch (SystemException var25) {
                     if (traceEnabled) {
                        ntrace.doTrace("/TuxedoConnection/tpacall/SystemException:" + var25);
                     }
                  }

                  this.isRolledBack = true;
                  if (traceEnabled) {
                     ntrace.doTrace("*]/TuxedoConnection/tpdequeue/35/TPETIME (transaction)");
                  }

                  throw new TPException(13, "ERROR: This transaction has already timed out");
               }
            }

            List myRoute = this.getProviderRoute(qspace, (TypedBuffer)null, useXid, flags);
            TCRouteEntry myEntry = (TCRouteEntry)myRoute.get(0);
            myAtmi = (gwatmi)myEntry.getSessionGroup();
            if (this.myXAResource != null && (flags & 8) == 0 && this.myDummyXAResource == null) {
               TuxedoConnectorRAP[] rdoms = this.tos.getOutboundRdomsAssociatedWithXid(this.myXAResource.getXid());
               if (rdoms.length > 1) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/TuxedoConnection/tpdequeue/transaction involes at least " + rdoms.length + " branches, add a dummy XA Resource");
                  }

                  this.myDummyXAResource = new TuxedoDummyXA();

                  try {
                     this.myTransaction.enlistResource(this.myDummyXAResource);
                  } catch (SystemException var20) {
                     throw new TPException(12, "ERROR: SystemException dummy resource");
                  } catch (RollbackException var21) {
                     this.isRolledBack = true;
                  } catch (IllegalStateException var22) {
                     throw new TPException(12, "ERROR: IllegalStateException dummy resource");
                  }
               }
            }

            try {
               dRet = myAtmi.tpdequeue(myEntry.getRemoteName(), qname, msgid, corrid, doWait, doPeek, flags, useXid, useTimeout, this);
            } catch (TPException var24) {
               String property = System.getProperty("weblogic.wtc.trans_ignore_tpenoent");
               boolean ignoreTPENOENT = false;
               if (property != null && (property.startsWith("y") || property.startsWith("Y"))) {
                  ignoreTPENOENT = true;
               }

               if (this.myTransaction != null && (var24.gettperrno() == 13 || var24.gettperrno() == 6 && !ignoreTPENOENT || var24.gettperrno() == 18 || var24.gettperrno() == 24 && (var24.getdiagnostic() == -8 || var24.getdiagnostic() == -4)) && (flags & 8) == 0) {
                  try {
                     this.myTransaction.setRollbackOnly();
                  } catch (SystemException var23) {
                     if (traceEnabled) {
                        ntrace.doTrace("/TuxedoConnection/tpdequeue/SystemException:" + var23);
                     }
                  }

                  this.isRolledBack = true;
               }

               if (traceEnabled) {
                  ntrace.doTrace("*]/TuxedoConnection/tpdequeue/30/" + var24);
               }

               throw var24;
            }

            if (traceEnabled) {
               ntrace.doTrace("]/TuxedoConnection/tpdequeue/40/" + dRet);
            }

            return dRet;
         }
      } else {
         if (traceEnabled) {
            ntrace.doTrace("*]/TuxedoConnection/tpdequeue/20/TPEINVAL");
         }

         throw new TPException(4);
      }
   }

   public DequeueReply tpdequeue(String qspace, String qname, int flags) throws TPException {
      return this.tpdequeue(qspace, qname, (byte[])null, (byte[])null, false, false, flags);
   }

   public Reply tpcall(String svc, TypedBuffer data, int flags) throws TPException, TPReplyException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxedoConnection/tpcall/" + svc + "/" + flags);
      }

      gwatmi myAtmi = null;
      Xid useXid = null;
      int useTimeout = 0;
      if (this.is_term) {
         if (traceEnabled) {
            ntrace.doTrace("*]/TuxedoConnection/tpcall/10/TPEPROTO");
         }

         throw new TPException(9, "ERROR: Session terminated");
      } else if (svc != null && !svc.equals("")) {
         if (this.myTransaction != null && this.isRolledBack && (flags & 8) == 0) {
            if (traceEnabled) {
               ntrace.doTrace("*]/TuxedoConnection/tpcall/30/TPEINVAL");
            }

            throw new TPException(4, "Transaction rolled back but TPNOTRAN not specified");
         } else {
            if (this.myXAResource != null && (flags & 8) == 0) {
               Xid myXid;
               if ((myXid = this.myXAResource.getXid()) == null) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/TuxedoConnection/tpcall/33/TPEPROTO (transaction)");
                  }

                  throw new TPException(9, "ERROR: TuxedoConnection transaction has ended but TPNOTRAN is not set");
               }

               useXid = myXid;
               useTimeout = this.myXAResource.getRealTransactionTimeout();
               flags &= -33;
               if (useTimeout <= 0) {
                  try {
                     this.myTransaction.setRollbackOnly();
                  } catch (SystemException var22) {
                     if (traceEnabled) {
                        ntrace.doTrace("/TuxedoConnection/tpacall/SystemException:" + var22);
                     }
                  }

                  this.isRolledBack = true;
                  if (traceEnabled) {
                     ntrace.doTrace("*]/TuxedoConnection/tpcall/35/TPETIME (transaction)");
                  }

                  throw new TPException(13, "ERROR: This transaction has already timed out");
               }
            }

            List myRoute = this.getProviderRoute(svc, data, useXid, flags);
            TCRouteEntry myEntry = (TCRouteEntry)myRoute.get(0);
            myAtmi = (gwatmi)myEntry.getSessionGroup();
            if (this.myXAResource != null && (flags & 8) == 0 && this.myDummyXAResource == null) {
               TuxedoConnectorRAP[] rdoms = this.tos.getOutboundRdomsAssociatedWithXid(this.myXAResource.getXid());
               if (rdoms.length > 1) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/TuxedoConnection/tpcall/transaction involes at least " + rdoms.length + " branches, add a dummy XA Resource");
                  }

                  this.myDummyXAResource = new TuxedoDummyXA();

                  try {
                     this.myTransaction.enlistResource(this.myDummyXAResource);
                  } catch (SystemException var17) {
                     throw new TPException(12, "ERROR: SystemException dummy resource");
                  } catch (RollbackException var18) {
                     this.isRolledBack = true;
                  } catch (IllegalStateException var19) {
                     throw new TPException(12, "ERROR: IllegalStateException dummy resource");
                  }
               }
            }

            String[] imp_info = new String[]{svc, myEntry.getImpSvc()[0], myEntry.getImpSvc()[1]};
            this.currImpSvc.put(myEntry.getRemoteName(), imp_info);

            Reply ret;
            try {
               if (this.tmsndprio != 50) {
                  myAtmi.tpsprio(this.tmsndprio, 64);
                  this.tmsndprio = 50;
               }

               ret = myAtmi.tpcall(myEntry.getRemoteName(), data, flags, useXid, useTimeout, this);
            } catch (TPReplyException var23) {
               if (this.myTransaction != null && (flags & 8) == 0) {
                  try {
                     this.myTransaction.setRollbackOnly();
                  } catch (SystemException var21) {
                     if (traceEnabled) {
                        ntrace.doTrace("/TuxedoConnection/tpcall/SystemException:" + var21);
                     }
                  }

                  this.isRolledBack = true;
               }

               if (traceEnabled) {
                  ntrace.doTrace("*]/TuxedoConnection/tpcall/20/" + var23);
               }

               throw var23;
            } catch (TPException var24) {
               String property = System.getProperty("weblogic.wtc.trans_ignore_tpenoent");
               boolean ignoreTPENOENT = false;
               if (property != null && (property.startsWith("y") || property.startsWith("Y"))) {
                  ignoreTPENOENT = true;
               }

               if (this.myTransaction != null && (var24.gettperrno() == 13 || var24.gettperrno() == 6 && !ignoreTPENOENT || var24.gettperrno() == 18) && (flags & 8) == 0) {
                  try {
                     this.myTransaction.setRollbackOnly();
                  } catch (SystemException var20) {
                     if (traceEnabled) {
                        ntrace.doTrace("/TuxedoConnection/tpcall/SystemException:" + var20);
                     }
                  }

                  this.isRolledBack = true;
               }

               if (traceEnabled) {
                  ntrace.doTrace("*]/TuxedoConnection/tpcall/30/" + var24);
               }

               throw var24;
            }

            if (traceEnabled) {
               ntrace.doTrace("]/TuxedoConnection/tpcall/40/" + ret);
            }

            return ret;
         }
      } else {
         if (traceEnabled) {
            ntrace.doTrace("*]/TuxedoConnection/tpcall/20/TPEINVAL");
         }

         throw new TPException(4);
      }
   }

   public synchronized void tpterm() {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxedoConnection/tpterm/");
      }

      this.is_term = true;
      if (traceEnabled) {
         ntrace.doTrace("]/TuxedoConnection/tpterm/10");
      }

   }

   public Conversation tpconnect(String svc, TypedBuffer data, int flags) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxedoConnection/tpconnect/" + svc + "/" + flags);
      }

      gwatmi myAtmi = null;
      Xid useXid = null;
      int useTimeout = 0;
      Conversation internalConversation = null;
      if (this.is_term) {
         if (traceEnabled) {
            ntrace.doTrace("*]/TuxedoConnection/tpconnect/10/TPEPROTO");
         }

         throw new TPException(9, "ERROR: Session terminated");
      } else if (svc != null && !svc.equals("")) {
         if ((flags & 6144) == 0) {
            if (traceEnabled) {
               ntrace.doTrace("*]/TuxedoConnection/tpconnect/25/TPEINVAL");
            }

            throw new TPException(4, "Must specify a flag TPSENDONLY or TPRECVONLY");
         } else {
            if (this.myXAResource != null && (flags & 8) == 0) {
               Xid myXid;
               if ((myXid = this.myXAResource.getXid()) == null) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/TuxedoConnection/tpconnect/30/TPEPROTO (transaction)");
                  }

                  throw new TPException(9, "ERROR: TuxedoConnection transaction has ended but TPNOTRAN is not set");
               }

               useXid = myXid;
               useTimeout = this.myXAResource.getRealTransactionTimeout();
               flags &= -33;
               if (useTimeout <= 0) {
                  try {
                     this.myTransaction.setRollbackOnly();
                  } catch (SystemException var22) {
                     if (traceEnabled) {
                        ntrace.doTrace("/TuxedoConnection/tpconnect/SystemException:" + var22);
                     }
                  }

                  this.isRolledBack = true;
                  if (traceEnabled) {
                     ntrace.doTrace("*]/TuxedoConnection/tpconnect/55/TPETIME (transaction)");
                  }

                  throw new TPException(13, "ERROR: This transaction has already timed out");
               }
            }

            List myRoute = this.getProviderRoute(svc, data, useXid, flags);
            TCRouteEntry myEntry = (TCRouteEntry)myRoute.get(0);
            myAtmi = (gwatmi)myEntry.getSessionGroup();
            if (this.myXAResource != null && (flags & 8) == 0 && this.myDummyXAResource == null) {
               TuxedoConnectorRAP[] rdoms = this.tos.getOutboundRdomsAssociatedWithXid(this.myXAResource.getXid());
               if (rdoms.length > 1) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/TuxedoConnection/tpconnect/transaction involes at least " + rdoms.length + " branches, add a dummy XA Resource");
                  }

                  this.myDummyXAResource = new TuxedoDummyXA();

                  try {
                     this.myTransaction.enlistResource(this.myDummyXAResource);
                  } catch (SystemException var17) {
                     throw new TPException(12, "ERROR: SystemException dummy resource");
                  } catch (RollbackException var18) {
                     this.isRolledBack = true;
                  } catch (IllegalStateException var19) {
                     throw new TPException(12, "ERROR: IllegalStateException dummy resource");
                  }
               }
            }

            try {
               internalConversation = myAtmi.tpconnect(myEntry.getRemoteName(), data, flags, useXid, useTimeout, this);
            } catch (TPException var21) {
               String property = System.getProperty("weblogic.wtc.trans_ignore_tpenoent");
               boolean ignoreTPENOENT = false;
               if (property != null && (property.startsWith("y") || property.startsWith("Y"))) {
                  ignoreTPENOENT = true;
               }

               if (this.myTransaction != null && (var21.gettperrno() == 13 || var21.gettperrno() == 6 && !ignoreTPENOENT) && (flags & 8) == 0) {
                  try {
                     this.myTransaction.setRollbackOnly();
                  } catch (SystemException var20) {
                     if (traceEnabled) {
                        ntrace.doTrace("/TuxedoConnection/tpconnect/SystemException:" + var20);
                     }
                  }

                  this.isRolledBack = true;
               }

               if (traceEnabled) {
                  ntrace.doTrace("*]/TuxedoConnection/tpconnect/85/" + var21);
               }

               throw var21;
            }

            TuxedoConversation externalConversation = new TuxedoConversation(this, internalConversation, this.myTransaction, flags & 8);
            if (traceEnabled) {
               ntrace.doTrace("]/TuxedoConnection/tpconnect/95/");
            }

            return externalConversation;
         }
      } else {
         if (traceEnabled) {
            ntrace.doTrace("*]/TuxedoConnection/tpconnect/20/TPEINVAL");
         }

         throw new TPException(4);
      }
   }

   public boolean isTerminated() {
      return this.is_term;
   }

   public boolean getRollbackOnly() throws TPException {
      return this.isRolledBack;
   }

   public void setUserRecord(UserRec id) {
      this.myId = id;
   }

   public UserRec getUserRecord() {
      return this.myId;
   }

   public void updateViewMap(String viewname, Class viewclass, int flags) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxedoConnection/updateViewMap/" + viewname);
      }

      if (viewname == null) {
         if (traceEnabled) {
            ntrace.doTrace("*]/TuxedoConnection//updateViewMap/10/TPEINVAL");
         }

         throw new TPException(4, "ViewName argument not specified");
      } else if (viewclass == null) {
         if (traceEnabled) {
            ntrace.doTrace("*]/TuxedoConnection//updateViewMap/20/TPEINVAL");
         }

         throw new TPException(4, "ViewClass argument not specified");
      } else {
         ConfigHelper.updateRuntimeViewList(viewname, viewclass, 0);
         if (traceEnabled) {
            ntrace.doTrace("]/TuxedoConnection/updateViewMap/30/");
         }

      }
   }

   private void setRollbackOnly(int flags) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      String property = System.getProperty("weblogic.wtc.trans_ignore_tpenoent");
      if (property == null || !property.startsWith("y") && !property.startsWith("Y")) {
         if (this.myTransaction != null && (flags & 8) == 0) {
            try {
               this.myTransaction.setRollbackOnly();
            } catch (SystemException var5) {
               if (traceEnabled) {
                  ntrace.doTrace("SystemException:" + var5);
               }
            }

            this.isRolledBack = true;
            if (traceEnabled) {
               ntrace.doTrace("setRollbackOnly() called");
            }
         }
      } else if (traceEnabled) {
         ntrace.doTrace("setRollbackOnly() will not be called");
      }

   }

   public void tpsprio(int prio, int flags) throws TPException {
      if ((flags & -65) != 0) {
         throw new TPException(4, "Bad flags value");
      } else {
         if ((flags & 64) != 0) {
            if (prio >= 1 && prio <= 100) {
               this.tmsndprio = prio;
            } else {
               this.tmsndprio = 50;
            }
         } else if (prio > 100) {
            this.tmsndprio = 100;
         } else if (prio < 1) {
            this.tmsndprio = 1;
         } else {
            this.tmsndprio = prio;
         }

      }
   }

   public HashMap getCurrImpSvc() {
      return new HashMap(this.currImpSvc);
   }

   public ConcurrentHashMap getCurrImpSvc2() {
      return this.currImpSvc;
   }
}
