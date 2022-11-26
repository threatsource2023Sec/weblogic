package weblogic.management.configuration;

public interface SNMPCounterMonitorMBean extends SNMPJMXMonitorMBean {
   long getThreshold();

   void setThreshold(long var1);

   long getOffset();

   void setOffset(long var1);

   long getModulus();

   void setModulus(long var1);
}
