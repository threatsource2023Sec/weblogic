package weblogic.management.configuration;

public interface SNMPGaugeMonitorMBean extends SNMPJMXMonitorMBean {
   double getThresholdHigh();

   void setThresholdHigh(double var1);

   double getThresholdLow();

   void setThresholdLow(double var1);
}
