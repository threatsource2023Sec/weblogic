package weblogic.coherence.descriptor.wl;

import weblogic.descriptor.SettableBean;

public interface CoherenceClusterWellKnownAddressBean extends SettableBean {
   String getName();

   void setName(String var1);

   String getListenAddress();

   void setListenAddress(String var1);

   /** @deprecated */
   @Deprecated
   int getListenPort();

   /** @deprecated */
   @Deprecated
   void setListenPort(int var1);
}
