package weblogic.management.runtime;

public interface TimerRuntimeMBean extends RuntimeMBean {
   Timer[] getTimers();
}
