package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

public interface ExecuteQueueMBean extends ConfigurationMBean {
   String DEFAULT_QUEUE_NAME = "weblogic.kernel.Default";

   int getQueueLength();

   void setQueueLength(int var1) throws InvalidAttributeValueException;

   int getThreadPriority();

   void setThreadPriority(int var1) throws InvalidAttributeValueException;

   int getThreadCount();

   void setThreadCount(int var1) throws InvalidAttributeValueException;

   int getQueueLengthThresholdPercent();

   void setQueueLengthThresholdPercent(int var1) throws InvalidAttributeValueException;

   int getThreadsIncrease();

   void setThreadsIncrease(int var1) throws InvalidAttributeValueException;

   int getThreadsMaximum();

   void setThreadsMaximum(int var1) throws InvalidAttributeValueException;

   int getThreadsMinimum();

   void setThreadsMinimum(int var1) throws InvalidAttributeValueException;
}
