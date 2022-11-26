package weblogic.j2ee.descriptor.wl;

import weblogic.descriptor.SettableBean;

public interface ManagedExecutorServiceBean extends SettableBean {
   String getName();

   void setName(String var1);

   String getDispatchPolicy();

   void setDispatchPolicy(String var1);

   int getMaxConcurrentLongRunningRequests();

   void setMaxConcurrentLongRunningRequests(int var1);

   int getLongRunningPriority();

   void setLongRunningPriority(int var1);

   String getId();

   void setId(String var1);
}
