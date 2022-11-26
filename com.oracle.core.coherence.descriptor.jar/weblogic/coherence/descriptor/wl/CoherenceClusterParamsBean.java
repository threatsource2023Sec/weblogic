package weblogic.coherence.descriptor.wl;

import weblogic.descriptor.SettableBean;

public interface CoherenceClusterParamsBean extends SettableBean {
   String MULTICAST = "multicast";
   String UNICAST = "unicast";
   String UDP = "udp";
   String TCP = "tcp";
   String SSL = "ssl";
   String TMB = "tmb";
   String SDMB = "sdmb";
   String IMB = "imb";

   int getClusterListenPort();

   void setClusterListenPort(int var1);

   /** @deprecated */
   @Deprecated
   int getUnicastListenPort();

   /** @deprecated */
   @Deprecated
   void setUnicastListenPort(int var1);

   /** @deprecated */
   @Deprecated
   boolean isUnicastPortAutoAdjust();

   /** @deprecated */
   @Deprecated
   void setUnicastPortAutoAdjust(boolean var1);

   String getMulticastListenAddress();

   void setMulticastListenAddress(String var1);

   /** @deprecated */
   @Deprecated
   int getMulticastListenPort();

   /** @deprecated */
   @Deprecated
   void setMulticastListenPort(int var1);

   int getTimeToLive();

   void setTimeToLive(int var1);

   CoherenceClusterWellKnownAddressesBean getCoherenceClusterWellKnownAddresses();

   String getClusteringMode();

   void setClusteringMode(String var1);

   String getTransport();

   void setTransport(String var1);

   boolean isSecurityFrameworkEnabled();

   void setSecurityFrameworkEnabled(boolean var1);

   CoherenceIdentityAsserterBean getCoherenceIdentityAsserter();

   CoherenceKeystoreParamsBean getCoherenceKeystoreParams();

   CoherenceCacheBean[] getCoherenceCaches();

   CoherenceCacheBean createCoherenceCache(String var1);

   CoherenceCacheBean lookupCoherenceCache(String var1);

   void destroyCoherenceCache(CoherenceCacheBean var1);

   CoherenceServiceBean[] getCoherenceServices();

   CoherenceServiceBean createCoherenceService(String var1);

   CoherenceServiceBean lookupCoherenceService(String var1);

   void destroyCoherenceService(CoherenceServiceBean var1);
}
