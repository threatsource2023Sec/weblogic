package weblogic.coherence.descriptor.wl;

import weblogic.descriptor.SettableBean;

public interface CoherenceSocketAddressBean extends SettableBean {
   String getName();

   void setName(String var1);

   String getAddress();

   void setAddress(String var1);

   int getPort();

   void setPort(int var1);
}
