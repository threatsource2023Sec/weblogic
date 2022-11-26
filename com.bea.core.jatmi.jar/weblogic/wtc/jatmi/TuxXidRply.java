package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.ntrace;
import com.bea.core.jatmi.internal.TCTaskHelper;
import com.bea.core.jatmi.intf.TCTask;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Set;

public final class TuxXidRply {
   private HashMap myMap = new HashMap();
   private LinkedList genWaiters = new LinkedList();
   private LinkedList specWaiters = new LinkedList();
   private TXidERFactory myERFactory;

   public TuxXidRply() {
   }

   public TuxXidRply(TXidERFactory anERFactory) {
      this.myERFactory = anERFactory;
   }

   public void add_reply(gwatmi where_from, Txid txid, tfmh message) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxXidRply/add_reply/" + where_from + "/" + message);
      }

      if (message == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/TuxXidRply/add_reply/10/not added");
         }

      } else {
         ReqXidOid aReqXidOid = new ReqXidOid(txid, where_from);
         LockReqXid matchingLockReqXid = null;
         TCTask handler;
         synchronized(this.myMap) {
            if (this.myMap.containsKey(aReqXidOid)) {
               if (traceEnabled) {
                  ntrace.doTrace("]/TuxXidRply/add_reply/20/not added");
               }

               return;
            }

            matchingLockReqXid = this.getEligibleWaiter(aReqXidOid);
            if (matchingLockReqXid == null) {
               this.myMap.put(aReqXidOid, message);
               if (this.myERFactory != null && (handler = this.myERFactory.getWork(this)) != null) {
                  TCTaskHelper.schedule(handler);
               }

               if (traceEnabled) {
                  ntrace.doTrace("}/TuxXidRply/add_reply/30/added: " + aReqXidOid);
               }

               return;
            }
         }

         synchronized(matchingLockReqXid) {
            matchingLockReqXid.setMessage(message);
            matchingLockReqXid.setRplyForXidOid(aReqXidOid);
            if (traceEnabled) {
               ntrace.doTrace("TuxXidRply/add_reply: found eligible waiter: " + matchingLockReqXid + "for XidOid" + aReqXidOid);
            }

            matchingLockReqXid.notifyAll();
            if (this.myERFactory != null && (handler = this.myERFactory.getWork(this)) != null) {
               TCTaskHelper.schedule(handler);
            }

            if (traceEnabled) {
               ntrace.doTrace("]/TuxXidRply/add_reply/40/added");
            }

         }
      }
   }

   public ReqXidMsg get_reply(boolean block) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxXidRply/get_reply/" + block);
      }

      LockReqXid myLockReqXid = new LockReqXid((ReqXidOid)null);
      synchronized(myLockReqXid) {
         ReqXidOid myXidOid;
         tfmh myMessage;
         ReqXidMsg ret;
         synchronized(this.myMap) {
            ReqXidMsg var10000;
            if (this.genWaiters.size() == 0 && this.myMap.size() > 0) {
               Set keySet = this.myMap.keySet();
               Iterator keyIterator = keySet.iterator();
               myXidOid = (ReqXidOid)keyIterator.next();
               myMessage = (tfmh)this.myMap.remove(myXidOid);
               ret = new ReqXidMsg(myXidOid, myMessage);
               if (traceEnabled) {
                  ntrace.doTrace("]/TuxRply/get_reply/30/" + ret);
               }

               var10000 = ret;
               return var10000;
            }

            if (!block) {
               if (traceEnabled) {
                  ntrace.doTrace("]/TuxRply/get_reply/10/null");
               }

               var10000 = null;
               return var10000;
            }

            this.genWaiters.add(myLockReqXid);
         }

         try {
            myLockReqXid.wait();
         } catch (InterruptedException var15) {
            synchronized(this.myMap) {
               this.genWaiters.remove(myLockReqXid);
            }

            if (myLockReqXid.getRplyForXidOid() == null) {
               if (traceEnabled) {
                  ntrace.doTrace("]/TuxXidRply/get_reply/20/null");
               }

               return null;
            }
         }

         myMessage = myLockReqXid.getMessage();
         myXidOid = myLockReqXid.getRplyForXidOid();
         ret = new ReqXidMsg(myXidOid, myMessage);
         if (traceEnabled) {
            ntrace.doTrace("]/TuxXidRply/get_reply/30/" + ret);
         }

         return ret;
      }
   }

   public tfmh get_specific_reply(ReqXidOid request_oid, boolean block) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxXidRply/get_specific_reply/" + request_oid + "/" + block);
      }

      if (request_oid == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/TuxXidRply/get_specific_reply/10/null");
         }

         return null;
      } else {
         LockReqXid myLockReqXid = new LockReqXid(request_oid);
         synchronized(myLockReqXid) {
            tfmh myMessage;
            label98: {
               tfmh var10000;
               synchronized(this.myMap) {
                  if (!this.myMap.containsKey(request_oid)) {
                     if (!block) {
                        if (traceEnabled) {
                           ntrace.doTrace("]/TuxXidRply/get_specific_reply/20/null");
                        }

                        var10000 = null;
                        return var10000;
                     }

                     this.specWaiters.add(myLockReqXid);
                     break label98;
                  }

                  if ((myMessage = (tfmh)this.myMap.remove(request_oid)) == null) {
                     if (traceEnabled) {
                        ntrace.doTrace("]/TuxXidRply/get_specific_reply/28/null");
                     }

                     var10000 = null;
                     return var10000;
                  }

                  if (traceEnabled) {
                     ntrace.doTrace("]/TuxXidRply/get_specific_reply/25/" + myMessage);
                  }

                  var10000 = myMessage;
               }

               return var10000;
            }

            try {
               myLockReqXid.wait();
            } catch (InterruptedException var12) {
               synchronized(this.myMap) {
                  this.specWaiters.remove(myLockReqXid);
               }

               if (myLockReqXid.getRplyForXidOid() == null) {
                  if (traceEnabled) {
                     ntrace.doTrace("]/TuxXidRply/get_specific_reply/30/null");
                  }

                  return null;
               }
            }

            myMessage = myLockReqXid.getMessage();
            if (traceEnabled) {
               ntrace.doTrace("]/TuxXidRply/get_specific_reply/40/" + myMessage);
            }

            return myMessage;
         }
      }
   }

   private LockReqXid getEligibleWaiter(ReqXidOid reqXidOid) {
      synchronized(this.myMap) {
         if (reqXidOid != null) {
            ListIterator lit = this.specWaiters.listIterator(0);

            while(lit.hasNext()) {
               LockReqXid lreq = (LockReqXid)lit.next();
               ReqXidOid lroid = lreq.getWaitReqXidOid();
               if (lroid != null && lroid.equals(reqXidOid)) {
                  this.specWaiters.remove(lreq);
                  return lreq;
               }
            }
         }

         if (this.genWaiters.size() > 0) {
            LockReqXid lreq = (LockReqXid)this.genWaiters.removeFirst();
            return lreq;
         } else {
            return null;
         }
      }
   }

   private class LockReqXid {
      private ReqXidOid waitOn = null;
      private tfmh message = null;
      private ReqXidOid rplyFor = null;

      LockReqXid(ReqXidOid waitOn) {
         this.waitOn = waitOn;
      }

      ReqXidOid getWaitReqXidOid() {
         return this.waitOn;
      }

      void setMessage(tfmh inmsg) {
         this.message = inmsg;
      }

      tfmh getMessage() {
         return this.message;
      }

      void setRplyForXidOid(ReqXidOid inOid) {
         this.rplyFor = inOid;
      }

      ReqXidOid getRplyForXidOid() {
         return this.rplyFor;
      }
   }
}
