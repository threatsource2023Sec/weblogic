package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.ntrace;
import java.util.LinkedList;

public class ConversationReply implements ReplyQueue {
   private LinkedList myList = new LinkedList();
   private LinkedList myWaiters = new LinkedList();

   public void add_reply(gwatmi where_from, CallDescriptor reqid, tfmh message) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/ConversationReply/add_reply/" + where_from + "/" + reqid);
      }

      if (message == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/ConversationReply/add_reply/10/not added");
         }

      } else {
         ReqOid aReqOid = new ReqOid(reqid, where_from);
         ReqMsg addElement = new ReqMsg(aReqOid, message);
         LockReq myLockReq = null;
         synchronized(this.myList) {
            myLockReq = this.getWaiter();
            if (myLockReq == null) {
               this.myList.addLast(addElement);
               if (traceEnabled) {
                  ntrace.doTrace("]/TuxRply/add_reply/15/added");
               }

               return;
            }
         }

         synchronized(myLockReq) {
            myLockReq.setMessage(message);
            myLockReq.setRplyForOid(aReqOid);
            myLockReq.notifyAll();
         }

         if (traceEnabled) {
            ntrace.doTrace("]/ConversationReply/add_reply/20/added");
         }

      }
   }

   public ReqMsg get_reply(boolean block) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/ConversationReply/get_reply/" + block);
      }

      LockReq myLockReq = new LockReq();
      synchronized(myLockReq) {
         ReqMsg ret;
         synchronized(this.myList) {
            ReqMsg var10000;
            if (this.myWaiters.size() == 0 && this.myList.size() > 0) {
               ret = (ReqMsg)this.myList.removeFirst();
               if (traceEnabled) {
                  ntrace.doTrace("]/ConversationReply/get_reply/05/" + ret);
               }

               var10000 = ret;
               return var10000;
            }

            if (!block) {
               if (traceEnabled) {
                  ntrace.doTrace("]/ConversationReply/get_reply/10/null");
               }

               var10000 = null;
               return var10000;
            }

            this.myWaiters.add(myLockReq);
         }

         try {
            myLockReq.wait();
         } catch (InterruptedException var11) {
            synchronized(this.myList) {
               this.myWaiters.remove(myLockReq);
            }

            if (myLockReq.getRplyForOid() == null) {
               if (traceEnabled) {
                  ntrace.doTrace("]/ConversationReply/get_reply/20/null");
               }

               return null;
            }
         }

         tfmh myMessage = myLockReq.getMessage();
         ReqOid myOid = myLockReq.getRplyForOid();
         ret = new ReqMsg(myOid, myMessage);
         if (traceEnabled) {
            ntrace.doTrace("]/ConversationReply/get_reply/30/" + ret);
         }

         return ret;
      }
   }

   public tfmh get_specific_reply(ReqOid request_oid, boolean block) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/ConversationReply/get_specific_reply/" + request_oid + "/" + block);
         ntrace.doTrace("]/ConversationReply/get_specific_reply/10/null");
      }

      return null;
   }

   private LockReq getWaiter() {
      synchronized(this.myList) {
         if (this.myWaiters.size() > 0) {
            LockReq lreq = (LockReq)this.myWaiters.removeFirst();
            return lreq;
         } else {
            return null;
         }
      }
   }

   private class LockReq {
      private tfmh message;
      private ReqOid rplyFor;

      private LockReq() {
         this.message = null;
         this.rplyFor = null;
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

      // $FF: synthetic method
      LockReq(Object x1) {
         this();
      }
   }
}
