package weblogic.coherence.descriptor.wl;

import weblogic.descriptor.SettableBean;

public interface CoherenceAddressProviderBean extends SettableBean {
   String getName();

   void setName(String var1);

   CoherenceSocketAddressBean[] getCoherenceSocketAddresses();

   CoherenceSocketAddressBean createCoherenceSocketAddress(String var1);

   CoherenceSocketAddressBean lookupCoherenceSocketAddress(String var1);

   void destroyCoherenceSocketAddress(CoherenceSocketAddressBean var1);
}
