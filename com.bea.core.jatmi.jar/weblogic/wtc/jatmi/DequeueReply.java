package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.Utilities;

public final class DequeueReply extends TuxedoReply {
   private static final long serialVersionUID = -2300659571782962414L;
   byte[] myMsgid;
   byte[] myCorrid;
   String myReplyQueue;
   String myFailureQueue;
   Integer myAppkey;
   Integer myPriority;
   int myDelivery_qos;
   int myReply_qos;
   int myUrcode;

   public DequeueReply(TypedBuffer tb, int tpurcode, CallDescriptor cd, byte[] msgid, byte[] corrid, String replyqueue, String failurequeue, Integer appkey, Integer priority, int delivery_qos, int reply_qos, int urcode) {
      super(tb, tpurcode, cd);
      this.myMsgid = msgid;
      this.myCorrid = corrid;
      this.myReplyQueue = replyqueue;
      this.myFailureQueue = failurequeue;
      this.myAppkey = appkey;
      this.myPriority = priority;
      this.myDelivery_qos = delivery_qos;
      this.myReply_qos = reply_qos;
      this.myUrcode = urcode;
   }

   public Integer getappkey() {
      return this.myAppkey;
   }

   public Integer getpriority() {
      return this.myPriority;
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

   public int geturcode() {
      return this.myUrcode;
   }

   public String toString() {
      return new String(super.toString() + ":" + Utilities.prettyByteArray(this.myMsgid) + ":" + Utilities.prettyByteArray(this.myCorrid) + ":" + this.myReplyQueue + ":" + this.myFailureQueue + ":" + this.myAppkey + ":" + this.myPriority + ":" + this.myDelivery_qos + ":" + this.myReply_qos + ":" + this.myUrcode);
   }
}
