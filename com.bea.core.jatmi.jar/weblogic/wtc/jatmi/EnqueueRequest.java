package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.Utilities;
import java.io.Serializable;

public final class EnqueueRequest implements Serializable {
   private static final long serialVersionUID = 3053813208281488550L;
   private QueueTimeField myDeq_time;
   private Integer myPriority;
   private QueueTimeField myExp_time;
   private int myDelivery_qos;
   private int myReply_qos;
   private byte[] myMsgid;
   private byte[] myCorrid;
   private String myReplyQueue;
   private String myFailureQueue;
   private boolean myIsTPQEXPTIME_NONE;
   private boolean myIsTPQTOP;
   private int myUrcode;
   private static final int MCLEN = 32;
   public static final int TPQQOSDEFAULTPERSIST = 1;
   public static final int TPQQOSPERSISTENT = 2;
   public static final int TPQQOSNONPERSISTENT = 4;

   public EnqueueRequest() {
      this.myDelivery_qos = 1;
      this.myReply_qos = 1;
   }

   public EnqueueRequest(QueueTimeField deq_time, Integer priority, QueueTimeField exp_time, int delivery_qos, int reply_qos, byte[] msgid, byte[] corrid, String replyqueue, String failurequeue, boolean isTPQEXPTIME_NONE, boolean isTPQTOP, int urcode) throws TPException {
      this.myDeq_time = deq_time;
      if (priority != null) {
         int aPriority = priority;
         if (aPriority < 1 || aPriority > 100) {
            throw new TPException(4, "Invalid priority value " + aPriority);
         }
      }

      this.myPriority = priority;
      this.myExp_time = exp_time;
      switch (delivery_qos) {
         case 1:
         case 2:
         case 4:
            this.myDelivery_qos = delivery_qos;
            switch (reply_qos) {
               case 1:
               case 2:
               case 4:
                  this.myReply_qos = reply_qos;
                  if ((this.myMsgid = msgid) != null && msgid.length != 32) {
                     throw new TPException(4, "Message ID must be 32 bytes long");
                  } else if ((this.myCorrid = corrid) != null && corrid.length != 32) {
                     throw new TPException(4, "Correlation ID must be 32 bytes long");
                  } else {
                     this.myReplyQueue = replyqueue;
                     this.myFailureQueue = failurequeue;
                     if (isTPQEXPTIME_NONE && exp_time != null) {
                        throw new TPException(4, "isTPQEXPTIME_NONE is true but exp_time is not null");
                     } else {
                        this.myIsTPQEXPTIME_NONE = isTPQEXPTIME_NONE;
                        if (isTPQTOP && msgid != null) {
                           throw new TPException(4, "isTPQTOP is true but msgid is not null");
                        }

                        this.myIsTPQTOP = isTPQTOP;
                        this.myUrcode = urcode;
                        return;
                     }
                  }
               case 3:
               default:
                  throw new TPException(4, "Invalid reply_qos value " + reply_qos);
            }
         case 3:
         default:
            throw new TPException(4, "Invalid delivery_qos value " + delivery_qos);
      }
   }

   public QueueTimeField getdeq_time() {
      return this.myDeq_time;
   }

   public Integer getpriority() {
      return this.myPriority;
   }

   public QueueTimeField getexp_time() {
      return this.myExp_time;
   }

   public int getdelivery_qos() {
      return this.myDelivery_qos;
   }

   public int getreply_qos() {
      return this.myReply_qos;
   }

   public byte[] getmsgid() {
      return this.myMsgid;
   }

   public byte[] getcorrid() {
      return this.myCorrid;
   }

   public String getreplyqueue() {
      return this.myReplyQueue;
   }

   public String getfailurequeue() {
      return this.myFailureQueue;
   }

   public boolean isTPQTOP() {
      return this.myIsTPQTOP;
   }

   public boolean isTPQEXPTIME_NONE() {
      return this.myIsTPQEXPTIME_NONE;
   }

   public int geturcode() {
      return this.myUrcode;
   }

   public void setdeq_time(QueueTimeField deq_time) throws TPException {
      this.myDeq_time = deq_time;
   }

   public void setpriority(Integer priority) throws TPException {
      if (priority != null) {
         int aPriority = priority;
         if (aPriority < 1 || aPriority > 100) {
            throw new TPException(4, "Invalid priority value " + aPriority);
         }
      }

      this.myPriority = priority;
   }

   public void setexp_time(QueueTimeField exp_time) throws TPException {
      if (exp_time != null && this.myIsTPQEXPTIME_NONE) {
         throw new TPException(4, "Cannot set expiration time if isTPQEXPTIME_NONE is true");
      } else {
         this.myExp_time = exp_time;
      }
   }

   public void setdelivery_qos(int delivery_qos) throws TPException {
      switch (delivery_qos) {
         case 1:
         case 2:
         case 4:
            this.myDelivery_qos = delivery_qos;
            return;
         case 3:
         default:
            throw new TPException(4, "Invalid delivery_qos value " + delivery_qos);
      }
   }

   public void setreply_qos(int reply_qos) throws TPException {
      switch (reply_qos) {
         case 1:
         case 2:
         case 4:
            this.myReply_qos = reply_qos;
            return;
         case 3:
         default:
            throw new TPException(4, "Invalid reply_qos value " + reply_qos);
      }
   }

   public void setmsgid(byte[] msgid) throws TPException {
      if (msgid != null && msgid.length != 32) {
         throw new TPException(4, "msgid must be 32 bytes long " + msgid.length);
      } else {
         this.myMsgid = msgid;
      }
   }

   public void setcorrid(byte[] corrid) throws TPException {
      if (corrid != null && corrid.length != 32) {
         throw new TPException(4, "corrid must be 32 bytes long " + corrid.length);
      } else {
         this.myCorrid = corrid;
      }
   }

   public void setreplyqueue(String replyqueue) throws TPException {
      this.myReplyQueue = replyqueue;
   }

   public void setfailurequeue(String failurequeue) throws TPException {
      this.myFailureQueue = failurequeue;
   }

   public void setTPQTOP(boolean TPQTOP) throws TPException {
      this.myIsTPQTOP = TPQTOP;
   }

   public void setTPQEXPTIME_NONE(boolean TPQEXPTIME_NONE) throws TPException {
      if (TPQEXPTIME_NONE && this.myExp_time != null) {
         throw new TPException(4, "Attempt to set TPQEXPTIME_NONE when an expiration time exists");
      } else {
         this.myIsTPQEXPTIME_NONE = TPQEXPTIME_NONE;
      }
   }

   public void seturcode(int urcode) throws TPException {
      this.myUrcode = urcode;
   }

   public String toString() {
      return new String("deq_time(" + this.myDeq_time + "):" + this.myPriority + ":exp_time(" + this.myExp_time + "):" + this.myDelivery_qos + ":" + this.myReply_qos + ":" + Utilities.prettyByteArray(this.myMsgid) + ":" + Utilities.prettyByteArray(this.myCorrid) + ":" + this.myReplyQueue + ":" + this.myFailureQueue + ":" + this.myIsTPQEXPTIME_NONE + ":" + this.myIsTPQTOP + ":" + this.myUrcode);
   }
}
