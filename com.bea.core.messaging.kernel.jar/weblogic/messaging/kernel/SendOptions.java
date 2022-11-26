package weblogic.messaging.kernel;

public final class SendOptions implements Cloneable {
   private boolean persistent;
   private long timeout;
   private Sequence sequence;
   private long sequenceNum = -1L;
   private long deliveryTime;
   private boolean noDeliveryDelay;
   private long expirationTime;
   private int redeliveryLimit = Integer.MAX_VALUE;
   private String group;
   private int deliveryCount = 0;
   private Sequence inboundSequence = null;
   private long inboundSequenceNum = -1L;

   public SendOptions copy() {
      try {
         return (SendOptions)this.clone();
      } catch (CloneNotSupportedException var2) {
         throw new AssertionError(var2);
      }
   }

   public void setPersistent(boolean persistent) {
      this.persistent = persistent;
   }

   public boolean isPersistent() {
      return this.persistent;
   }

   public void setSequence(Sequence sequence) {
      this.sequence = sequence;
   }

   public Sequence getSequence() {
      return this.sequence;
   }

   public void setInboundSequence(Sequence sequence) {
      this.inboundSequence = sequence;
   }

   public Sequence getInboundSequence() {
      return this.inboundSequence;
   }

   public void setInboundSequenceNum(long sequenceNum) {
      this.inboundSequenceNum = sequenceNum;
   }

   public long getInboundSequenceNum() {
      return this.inboundSequenceNum;
   }

   public void setSequenceNum(long sequenceNum) {
      this.sequenceNum = sequenceNum;
   }

   public long getSequenceNum() {
      return this.sequenceNum;
   }

   public void setTimeout(long timeout) {
      this.timeout = timeout;
   }

   public long getTimeout() {
      return this.timeout;
   }

   public void setDeliveryTime(long time) {
      this.deliveryTime = time;
   }

   public long getDeliveryTime() {
      return this.deliveryTime;
   }

   public void setNoDeliveryDelay(boolean noDeliveryDelay) {
      this.noDeliveryDelay = noDeliveryDelay;
   }

   public boolean isNoDeliveryDelay() {
      return this.noDeliveryDelay;
   }

   public void setExpirationTime(long time) {
      this.expirationTime = time;
   }

   public long getExpirationTime() {
      return this.expirationTime;
   }

   public void setRedeliveryLimit(int limit) {
      this.redeliveryLimit = limit;
   }

   public int getRedeliveryLimit() {
      return this.redeliveryLimit;
   }

   public void setGroup(String group) {
      this.group = group;
   }

   public String getGroup() {
      return this.group;
   }

   public void setDeliveryCount(int deliveryCount) {
      this.deliveryCount = deliveryCount;
   }

   public int getDeliveryCount() {
      return this.deliveryCount;
   }
}
