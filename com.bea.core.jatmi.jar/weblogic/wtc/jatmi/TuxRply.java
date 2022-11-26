package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.ntrace;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Set;

public final class TuxRply implements ReplyQueue {
   private HashMap myMap = new HashMap();
   private LinkedList genWaiters = new LinkedList();
   private LinkedList specWaiters = new LinkedList();

   public void add_reply(gwatmi where_from, CallDescriptor reqid, tfmh message) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxRply/add_reply/" + where_from + "/" + reqid);
      }

      if (message == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/TuxRply/add_reply/10/not added");
         }

      } else {
         ReqOid aReqOid = new ReqOid(reqid, where_from);
         LockReq matchingLockReq = null;
         synchronized(this.myMap) {
            if (this.myMap.containsKey(aReqOid)) {
               if (traceEnabled) {
                  ntrace.doTrace("]/TuxRply/add_reply/20/not added");
               }

               return;
            }

            matchingLockReq = this.getEligibleWaiter(aReqOid);
            if (matchingLockReq == null) {
               if (traceEnabled) {
                  ntrace.doTrace("TuxRply/add_reply: no eligible waiter for oid " + aReqOid);
               }

               this.myMap.put(aReqOid, message);
               if (traceEnabled) {
                  ntrace.doTrace("]/TuxRply/add_reply/30/added");
               }

               return;
            }
         }

         synchronized(matchingLockReq) {
            matchingLockReq.setMessage(message);
            matchingLockReq.setRplyForOid(aReqOid);
            if (traceEnabled) {
               ntrace.doTrace("TuxRply/add_reply: found eligible waiter= " + matchingLockReq + " for oid " + aReqOid);
            }

            matchingLockReq.notifyAll();
         }
      }
   }

   public ReqMsg get_reply(boolean block) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxRply/get_reply/" + block);
      }

      LockReq myLockReq = new LockReq((ReqOid)null);
      synchronized(myLockReq) {
         ReqOid myOid;
         tfmh myMessage;
         ReqMsg ret;
         label114: {
            ReqMsg var10000;
            synchronized(this.myMap) {
               if (this.genWaiters.size() != 0 || this.myMap.size() <= 0) {
                  if (!block) {
                     if (traceEnabled) {
                        ntrace.doTrace("]/TuxRply/get_reply/10/null");
                     }

                     var10000 = null;
                     return var10000;
                  }

                  this.genWaiters.add(myLockReq);
                  break label114;
               }

               Set keySet = this.myMap.keySet();
               Iterator keyIterator = keySet.iterator();
               myOid = (ReqOid)keyIterator.next();
               myMessage = (tfmh)this.myMap.remove(myOid);
               ret = new ReqMsg(myOid, myMessage);
               if (traceEnabled) {
                  ntrace.doTrace("]/TuxRply/get_reply/30/" + ret);
               }

               var10000 = ret;
            }

            return var10000;
         }

         try {
            if (traceEnabled) {
               ntrace.doTrace("TuxRply/get_reply: Thr " + Thread.currentThread() + " waiting for message oid ANY on lockReq = " + myLockReq);
            }

            myLockReq.wait();
            if (traceEnabled) {
               ntrace.doTrace("TuxRply/get_reply: Thr " + Thread.currentThread() + " got message oid " + myLockReq.getRplyForOid() + " on lockReq = " + myLockReq);
            }
         } catch (InterruptedException var15) {
            if (traceEnabled) {
               ntrace.doTrace("]/TuxRply/get_reply/20/null");
            }

            synchronized(this.myMap) {
               this.genWaiters.remove(myLockReq);
            }

            if (myLockReq.getRplyForOid() == null) {
               if (traceEnabled) {
                  ntrace.doTrace("]/TuxRply/get_reply/20/null");
               }

               return null;
            }
         }

         myMessage = myLockReq.getMessage();
         myOid = myLockReq.getRplyForOid();
         ret = new ReqMsg(myOid, myMessage);
         if (traceEnabled) {
            ntrace.doTrace("]/TuxRply/get_reply/30/" + ret);
         }

         return ret;
      }
   }

   public tfmh get_specific_reply(ReqOid request_oid, boolean block) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxRply/get_specific_reply/" + request_oid + "/" + block);
      }

      if (request_oid == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/TuxRply/get_specific_reply/10/null");
         }

         return null;
      } else {
         LockReq myLockReq = new LockReq(request_oid);
         synchronized(myLockReq) {
            tfmh myMessage;
            label131: {
               tfmh var10000;
               synchronized(this.myMap) {
                  if (!this.myMap.containsKey(request_oid)) {
                     if (!block) {
                        if (traceEnabled) {
                           ntrace.doTrace("]/TuxRply/get_specific_reply/20/null");
                        }

                        var10000 = null;
                        return var10000;
                     }

                     this.specWaiters.add(myLockReq);
                     break label131;
                  }

                  if ((myMessage = (tfmh)this.myMap.remove(request_oid)) == null) {
                     if (traceEnabled) {
                        ntrace.doTrace("]/TuxRply/get_specific_reply/28/null");
                     }

                     var10000 = null;
                     return var10000;
                  }

                  if (traceEnabled) {
                     ntrace.doTrace("]/TuxRply/get_specific_reply/25/" + myMessage);
                  }

                  var10000 = myMessage;
               }

               return var10000;
            }

            try {
               if (traceEnabled) {
                  ntrace.doTrace("TuxRply/get_specific_reply: Thr " + Thread.currentThread() + " waiting for message oid " + request_oid + " on lockReq = " + myLockReq);
               }

               myLockReq.wait();
               if (traceEnabled) {
                  ntrace.doTrace("TuxRply/get_specific_reply: Thr " + Thread.currentThread() + " got message oid " + request_oid + " on lockReq = " + myLockReq);
               }
            } catch (InterruptedException var12) {
               synchronized(this.myMap) {
                  this.specWaiters.remove(myLockReq);
               }

               if (myLockReq.getRplyForOid() == null) {
                  if (traceEnabled) {
                     ntrace.doTrace("]/TuxRply/get_specific_reply/30/null");
                  }

                  return null;
               }
            }

            myMessage = myLockReq.getMessage();
            if (traceEnabled) {
               ntrace.doTrace("]/TuxRply/get_specific_reply/40/" + myMessage);
            }

            return myMessage;
         }
      }
   }

   private LockReq getEligibleWaiter(ReqOid reqOid) {
      synchronized(this.myMap) {
         if (reqOid != null) {
            ListIterator lit = this.specWaiters.listIterator(0);

            while(lit.hasNext()) {
               LockReq lreq = (LockReq)lit.next();
               ReqOid lroid = lreq.getWaitReqOid();
               if (lroid != null && lroid.equals(reqOid)) {
                  this.specWaiters.remove(lreq);
                  return lreq;
               }
            }
         }

         if (this.genWaiters.size() > 0) {
            LockReq lreq = (LockReq)this.genWaiters.removeFirst();
            return lreq;
         } else {
            return null;
         }
      }
   }

   private class LockReq {
      private ReqOid waitOn = null;
      private tfmh message = null;
      private ReqOid rplyFor = null;

      LockReq(ReqOid waitOn) {
         this.waitOn = waitOn;
      }

      ReqOid getWaitReqOid() {
         return this.waitOn;
      }

      void setMessage(tfmh inmsg) {
         this.message = inmsg;
      }

      tfmh getMessage() {
         return this.message;
      }

      void setRplyForOid(ReqOid inOid) {
         this.rplyFor = inOid;
      }

      ReqOid getRplyForOid() {
         return this.rplyFor;
      }
   }
}
