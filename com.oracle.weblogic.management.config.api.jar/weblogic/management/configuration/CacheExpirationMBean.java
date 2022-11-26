package weblogic.management.configuration;

public interface CacheExpirationMBean extends ConfigurationMBean {
   long getTTL();

   void setTTL(long var1);

   boolean isTTLSet();

   long getIdleTime();

   void setIdleTime(long var1);

   boolean isIdleTimeSet();
}
