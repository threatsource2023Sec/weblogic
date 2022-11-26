package weblogic.coherence.descriptor.wl;

import weblogic.descriptor.SettableBean;

public interface CoherenceAddressProvidersBean extends SettableBean {
   CoherenceAddressProviderBean[] getCoherenceAddressProviders();

   CoherenceAddressProviderBean createCoherenceAddressProvider(String var1);

   CoherenceAddressProviderBean lookupCoherenceAddressProvider(String var1);

   void destroyCoherenceAddressProvider(CoherenceAddressProviderBean var1);
}
