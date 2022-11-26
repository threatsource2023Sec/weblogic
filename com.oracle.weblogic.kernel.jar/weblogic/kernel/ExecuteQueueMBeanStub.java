package weblogic.kernel;

import weblogic.management.configuration.ExecuteQueueMBean;

public final class ExecuteQueueMBeanStub extends MBeanStub implements ExecuteQueueMBean {
   private int queueLength = 65536;
   private int threadPriority = 5;
   private int threadCount = 5;
   private int queueLengthThresholdPercent = 90;
   private int threadsIncrease = 0;
   private int threadsMaximum = 65536;
   private int threadsMinimum = 5;

   public ExecuteQueueMBeanStub() {
      if (Kernel.isServer()) {
         this.threadCount = 15;
      }

   }

   public final String getName() {
      return "default";
   }

   public int getQueueLength() {
      return this.queueLength;
   }

   public void setQueueLength(int value) {
      this.queueLength = value;
   }

   public int getThreadPriority() {
      return 5;
   }

   public void setThreadPriority(int value) {
      this.threadPriority = value;
   }

   public int getThreadCount() {
      return this.threadCount;
   }

   public void setThreadCount(int value) {
      this.threadCount = value;
   }

   public int getQueueLengthThresholdPercent() {
      return this.queueLengthThresholdPercent;
   }

   public void setQueueLengthThresholdPercent(int value) {
      this.queueLengthThresholdPercent = value;
   }

   public int getThreadsIncrease() {
      return this.threadsIncrease;
   }

   public void setThreadsIncrease(int value) {
      this.threadsIncrease = value;
   }

   public int getThreadsMaximum() {
      return this.threadsMaximum;
   }

   public void setThreadsMaximum(int value) {
      this.threadsMaximum = value;
   }

   public int getThreadsMinimum() {
      return this.threadsMinimum;
   }

   public void setThreadsMinimum(int value) {
      this.threadsMinimum = value;
   }
}
