package weblogic.messaging.common;

import weblogic.messaging.kernel.Kernel;
import weblogic.messaging.kernel.Statistics;
import weblogic.messaging.kernel.Threshold;
import weblogic.messaging.kernel.ThresholdListener;

public abstract class ThresholdHandler implements ThresholdListener {
   protected Statistics statistics;
   protected Threshold messages;
   protected Threshold bytes;
   protected int count;
   protected long bytesLow = -1L;
   protected long bytesHigh = -1L;
   protected long messagesLow = -1L;
   protected long messagesHigh = -1L;
   protected final String targetName;

   protected ThresholdHandler(String targetName) {
      this.targetName = targetName;
   }

   protected ThresholdHandler(Kernel kernel, String targetName) {
      this.statistics = kernel.getStatistics();
      this.targetName = targetName;
   }

   public synchronized void close() {
      if (this.bytes != null) {
         this.statistics.removeThreshold(this.bytes);
      }

      if (this.messages != null) {
         this.statistics.removeThreshold(this.messages);
      }

   }

   public synchronized long getBytesThresholdHigh() {
      return this.bytesHigh;
   }

   public synchronized long getBytesThresholdLow() {
      return this.bytesLow;
   }

   public synchronized long getMessagesThresholdHigh() {
      return this.messagesHigh;
   }

   public synchronized long getMessagesThresholdLow() {
      return this.messagesLow;
   }

   protected void replaceBytesThreshold() {
      if (this.statistics != null) {
         if (this.bytesHigh >= 0L && this.bytesLow >= 0L) {
            if (this.bytes == null) {
               this.bytes = this.statistics.addByteThreshold(this.bytesHigh, this.bytesLow);
               this.bytes.addListener(this);
            } else {
               this.bytes.setThresholds(this.bytesLow, this.bytesHigh);
            }
         } else if (this.bytes != null) {
            this.statistics.removeThreshold(this.bytes);
            this.bytes = null;
         }

      }
   }

   public synchronized void setBytesThresholdHigh(long threshold) {
      this.bytesHigh = threshold;
      if (this.bytesHigh > this.bytesLow) {
         this.replaceBytesThreshold();
      }

   }

   public synchronized void setBytesThresholdLow(long threshold) {
      this.bytesLow = threshold;
      if (this.bytesHigh > this.bytesLow) {
         this.replaceBytesThreshold();
      }

   }

   protected void replaceMessagesThreshold() {
      if (this.statistics != null) {
         if (this.messagesHigh >= 0L && this.messagesLow >= 0L) {
            if (this.messages == null) {
               this.messages = this.statistics.addMessageThreshold(this.messagesHigh, this.messagesLow);
               this.messages.addListener(this);
            } else {
               this.messages.setThresholds(this.messagesLow, this.messagesHigh);
            }
         } else if (this.messages != null) {
            this.statistics.removeThreshold(this.messages);
            this.messages = null;
         }

      }
   }

   public synchronized void setMessagesThresholdHigh(long threshold) {
      this.messagesHigh = threshold;
      if (this.messagesHigh > this.messagesLow) {
         this.replaceMessagesThreshold();
      }

   }

   public synchronized void setMessagesThresholdLow(long threshold) {
      this.messagesLow = threshold;
      if (this.messagesHigh > this.messagesLow) {
         this.replaceMessagesThreshold();
      }

   }

   public synchronized boolean isArmed() {
      return this.count > 0;
   }

   public synchronized long getBytesThresholdTime() {
      return this.bytes == null ? 0L : this.bytes.getThresholdTime();
   }

   public synchronized long getMessagesThresholdTime() {
      return this.messages == null ? 0L : this.messages.getThresholdTime();
   }
}
