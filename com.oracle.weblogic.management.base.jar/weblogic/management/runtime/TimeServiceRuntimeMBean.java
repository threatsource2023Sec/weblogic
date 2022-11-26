package weblogic.management.runtime;

public interface TimeServiceRuntimeMBean extends RuntimeMBean {
   int getScheduledTriggerCount();

   int getExecutionsPerMinute();

   int getExecutionCount();

   int getExceptionCount();
}
