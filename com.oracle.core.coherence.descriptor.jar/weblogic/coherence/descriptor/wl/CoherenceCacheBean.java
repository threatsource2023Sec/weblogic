package weblogic.coherence.descriptor.wl;

import weblogic.descriptor.SettableBean;

public interface CoherenceCacheBean extends SettableBean {
   String getName();

   void setName(String var1);

   String getPartition();

   void setPartition(String var1);
}
