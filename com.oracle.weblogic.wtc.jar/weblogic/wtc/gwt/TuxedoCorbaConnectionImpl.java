package weblogic.wtc.gwt;

import com.bea.core.jatmi.common.ntrace;
import com.bea.core.jatmi.internal.TCRouteEntry;
import com.bea.core.jatmi.internal.TCTransactionHelper;
import com.bea.core.jatmi.internal.TuxedoXA;
import java.util.HashMap;
import java.util.List;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.xa.XAException;
import javax.transaction.xa.Xid;
import weblogic.wtc.jatmi.CallDescriptor;
import weblogic.wtc.jatmi.CorbaAtmi;
import weblogic.wtc.jatmi.Objinfo;
import weblogic.wtc.jatmi.Reply;
import weblogic.wtc.jatmi.ReqOid;
import weblogic.wtc.jatmi.TPException;
import weblogic.wtc.jatmi.TPReplyException;
import weblogic.wtc.jatmi.TuxRply;
import weblogic.wtc.jatmi.TypedBuffer;

public final class TuxedoCorbaConnectionImpl extends TuxedoConnectionImpl implements TuxedoCorbaConnection {
   private HashMap txInfoMap = new HashMap();

   public TuxedoCorbaConnectionImpl() throws TPException {
      super(8);
   }

   public CallDescriptor tpMethodReq(TypedBuffer data, Objinfo objinfo, MethodParameters methodParms, int flags) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxedoCorbaConnection/tpMethodReq/0");
      }

      CorbaAtmi myAtmi = null;
      int useTimeout = 0;
      Xid currXid = null;
      TuxedoXA currXAResource = null;
      Transaction currTransaction = null;
      boolean txRolledBack = false;
      Object[] txInfo = null;
      byte[] globalTransactionId = null;
      UID uid = null;
      if ((flags & -16430) != 0) {
         if (traceEnabled) {
            ntrace.doTrace("*]/TuxedoCorbaConnection/tpMethodReq/10");
         }

         throw new TPException(4);
      } else if (data != null && objinfo != null) {
         String targetSvc = "//" + objinfo.getDomainId();
         if (traceEnabled) {
            ntrace.doTrace("/TuxedoCorbaConnection/tpMethodReq/30" + targetSvc);
         }

         TuxRply rplyObj;
         if ((flags & 4) == 0 && (flags & 16384) == 0) {
            rplyObj = this.myRplyObj;
         } else {
            rplyObj = null;
         }

         if ((currTransaction = TCTransactionHelper.getTransaction()) != null) {
            currXid = TCTransactionHelper.getXidFromTransaction(currTransaction);
            globalTransactionId = currXid.getGlobalTransactionId();
            uid = TuxedoCorbaConnectionImpl.UID.attach(globalTransactionId);
         }

         if (!this.is_term) {
            if (currTransaction != null) {
               synchronized(this.txInfoMap) {
                  if ((txInfo = (Object[])((Object[])this.txInfoMap.get(uid))) == null) {
                     currXAResource = new TuxedoXA(this.tos, this);

                     try {
                        currTransaction.enlistResource(currXAResource);
                     } catch (SystemException var25) {
                        throw new TPException(12, "ERROR: Could not enlist in transaction");
                     } catch (RollbackException var26) {
                        txRolledBack = true;
                     } catch (IllegalStateException var27) {
                        throw new TPException(12, "ERROR: Transaction already prepared");
                     }

                     if (!txRolledBack) {
                        txInfo = new Object[]{currXAResource, new Boolean(txRolledBack), currXid};
                        if (traceEnabled) {
                           ntrace.doTrace("Adding currXid = " + currXid + " to txInfoMap");
                        }

                        this.txInfoMap.put(uid, txInfo);
                     }
                  } else {
                     currXAResource = (TuxedoXA)txInfo[0];
                     txRolledBack = (Boolean)txInfo[1];
                  }
               }

               if ((flags & 8) == 0) {
                  if (txRolledBack) {
                     if (traceEnabled) {
                        ntrace.doTrace("*]/TuxedoCorbaConnection/tpMethodReq/60/TPEINVAL");
                     }

                     throw new TPException(4, "Transaction rolled back but TPNOTRAN not specified");
                  }

                  useTimeout = currXAResource.getRealTransactionTimeout();
                  flags &= -33;
               }
            }

            List myRoute = this.getProviderRoute(targetSvc, data, currXid, flags);
            TCRouteEntry myEntry = (TCRouteEntry)myRoute.get(0);
            myAtmi = (CorbaAtmi)myEntry.getSessionGroup();
            CallDescriptor myAtmiReturn = myAtmi.tpMethodReq(data, objinfo, methodParms, this, flags, rplyObj, currXid, useTimeout, this);
            ReqOid retObj = new ReqOid(myAtmiReturn, myAtmi, currXid);
            if (traceEnabled) {
               ntrace.doTrace("]/TuxedoCorbaConnection/tpMethodReq/70" + retObj);
            }

            return retObj;
         } else {
            if (currTransaction != null) {
               try {
                  currTransaction.setRollbackOnly();
               } catch (SystemException var29) {
                  if (traceEnabled) {
                     ntrace.doTrace("/TuxedoCorbaConnection/tpMethodReq/40 SystemException:" + var29);
                  }
               }

               txRolledBack = true;
               synchronized(this.txInfoMap) {
                  if ((txInfo = (Object[])((Object[])this.txInfoMap.get(globalTransactionId))) != null) {
                     txInfo[1] = txRolledBack;
                     this.txInfoMap.put(uid, txInfo);
                  }
               }
            }

            if (traceEnabled) {
               ntrace.doTrace("*]/TuxedoCorbaConnection/tpMethodReq/50");
            }

            throw new TPException(9, "Session terminated");
         }
      } else {
         if (traceEnabled) {
            ntrace.doTrace("*]/TuxedoCorbaConnection/tpMethodReq/20");
         }

         throw new TPException(4);
      }
   }

   public Reply tpgetrply(CallDescriptor cd, int flags) throws TPException, TPReplyException {
      Xid currXid = null;
      boolean txRolledBack = false;
      Transaction currTransaction = null;
      Object[] txInfo = new Object[3];
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxedoCorbaConnection/tpgetrply/0" + cd + "/" + flags);
      }

      Reply ret;
      try {
         ret = super.tpgetrply(cd, flags);
      } catch (TPException var16) {
         if (cd != null) {
            currXid = ((ReqOid)cd).getXID();
         }

         if (traceEnabled) {
            ntrace.doTrace("currXid = " + currXid);
         }

         if (currXid != null) {
            if ((currTransaction = TCTransactionHelper.getTransaction()) != null) {
               if (currXid != TCTransactionHelper.getXidFromTransaction(currTransaction)) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/TuxedoCorbaConnection/tpgetrply/10");
                  }

                  throw new TPException(9, "Internal Error: Xid stored in Reply Handle doesn't match current Transaction");
               }
            } else if ((currTransaction = TCTransactionHelper.getTransaction(currXid)) == null) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/TuxedoCorbaConnection/tpgetrply/20");
               }

               throw new TPException(12, "Internal Error: Could not find Transaction matching Xid in Reply Handle");
            }

            try {
               currTransaction.setRollbackOnly();
            } catch (SystemException var15) {
               if (traceEnabled) {
                  ntrace.doTrace("SystemException:" + var15);
               }
            }

            txRolledBack = true;
            synchronized(this.txInfoMap) {
               byte[] globalTransactionId = currXid.getGlobalTransactionId();
               UID uid = TuxedoCorbaConnectionImpl.UID.attach(globalTransactionId);
               if ((txInfo = (Object[])((Object[])this.txInfoMap.get(uid))) == null) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/TuxedoCorbaConnection/tpgetrply/30");
                  }

                  throw new TPException(12, "Internal Error: Unknown Xid(" + currXid + ")");
               }

               txInfo[1] = new Boolean(txRolledBack);
               this.txInfoMap.put(uid, txInfo);
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("*]/TuxedoCorbaConnection/tpgetrply/40");
         }

         throw var16;
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TuxedoCorbaConnection/tpgetrply/50");
      }

      return ret;
   }

   public boolean getRollbackOnly() throws TPException {
      throw new TPException(9);
   }

   public void end(Xid currXid, int flags) throws XAException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxedoCorbaConnection/end/currXid = " + currXid + ", flags: " + flagsToString(flags));
      }

      if (flags == 33554432) {
         if (traceEnabled) {
            ntrace.doTrace("]/TuxedoCorbaConnection/end/5");
         }
      } else {
         synchronized(this.txInfoMap) {
            byte[] globalTransactionId = currXid.getGlobalTransactionId();
            UID uid = TuxedoCorbaConnectionImpl.UID.attach(globalTransactionId);
            if (this.txInfoMap.get(uid) == null) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/TuxedoCorbaConnection/end/20");
               }

               throw new XAException(-3);
            }

            this.txInfoMap.remove(uid);
            if (traceEnabled) {
               ntrace.doTrace("]/TuxedoCorbaConnection/end/10");
            }
         }
      }

   }

   public HashMap getTxInfoMap() {
      return this.txInfoMap;
   }

   private static String flagsToString(int flags) {
      switch (flags) {
         case 0:
            return "TMNOFLAGS";
         case 2097152:
            return "TMJOIN";
         case 33554432:
            return "TMSUSPEND";
         case 67108864:
            return "TMSUCCESS";
         case 134217728:
            return "TMRESUME";
         case 536870912:
            return "TMFAIL";
         case 1073741824:
            return "TMONEPHASE";
         default:
            return Integer.toHexString(flags).toUpperCase();
      }
   }

   public static class UID {
      public static final int UIDLEN = 6;
      private byte[] uid = new byte[6];

      private UID() {
      }

      public static UID attach(byte[] gtrid) {
         UID ret = null;
         if (gtrid != null && gtrid.length >= 6) {
            ret = new UID();
            System.arraycopy(gtrid, 0, ret.uid, 0, 6);
         }

         return ret;
      }

      public byte[] value() {
         return this.uid;
      }

      public int hashCode() {
         int key = 0;

         for(int i = 0; i < this.uid.length; ++i) {
            key += this.uid[i];
         }

         return key;
      }

      public boolean equals(Object o) {
         if (o != null && o instanceof UID) {
            byte[] src = ((UID)o).value();

            for(int i = 0; i < this.uid.length; ++i) {
               if (src[i] != this.uid[i]) {
                  return false;
               }
            }

            return true;
         } else {
            return false;
         }
      }
   }
}
