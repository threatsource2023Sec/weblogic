package weblogic.j2ee.descriptor.wl;

import weblogic.descriptor.SettableBean;

public interface MaxThreadsConstraintBean extends SettableBean {
   String getName();

   void setName(String var1);

   int getCount();

   void setCount(int var1);

   String getPoolName();

   void setPoolName(String var1);

   String getId();

   void setId(String var1);

   void setQueueSize(int var1);

   int getQueueSize();
}
