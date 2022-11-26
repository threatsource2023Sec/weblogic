package weblogic.management.configuration;

public interface WebserviceTimestampMBean extends ConfigurationMBean {
   void setClockSynchronized(boolean var1);

   boolean isClockSynchronized();

   void setClockSkew(long var1);

   long getClockSkew();

   void setMaxProcessingDelay(long var1);

   long getMaxProcessingDelay();

   void setValidityPeriod(int var1);

   int getValidityPeriod();
}
