package weblogic.coherence.descriptor.wl;

import weblogic.descriptor.SettableBean;

public interface CoherenceClusterWellKnownAddressesBean extends SettableBean {
   CoherenceClusterWellKnownAddressBean[] getCoherenceClusterWellKnownAddresses();

   CoherenceClusterWellKnownAddressBean createCoherenceClusterWellKnownAddress(String var1);

   CoherenceClusterWellKnownAddressBean lookupCoherenceClusterWellKnownAddress(String var1);

   void destroyCoherenceClusterWellKnownAddress(CoherenceClusterWellKnownAddressBean var1);
}
