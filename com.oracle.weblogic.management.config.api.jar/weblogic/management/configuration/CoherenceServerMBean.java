package weblogic.management.configuration;

public interface CoherenceServerMBean extends ManagedExternalServerMBean {
   void setCoherenceClusterSystemResource(CoherenceClusterSystemResourceMBean var1);

   CoherenceClusterSystemResourceMBean getCoherenceClusterSystemResource();

   String getUnicastListenAddress();

   void setUnicastListenAddress(String var1);

   int getUnicastListenPort();

   void setUnicastListenPort(int var1);

   /** @deprecated */
   @Deprecated
   boolean isUnicastPortAutoAdjust();

   /** @deprecated */
   @Deprecated
   void setUnicastPortAutoAdjust(boolean var1);

   int getUnicastPortAutoAdjustAttempts();

   void setUnicastPortAutoAdjustAttempts(int var1);

   CoherenceServerStartMBean getCoherenceServerStart();
}
