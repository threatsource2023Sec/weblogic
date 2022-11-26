package weblogic.j2ee.descriptor.wl;

import weblogic.descriptor.SettableBean;

public interface WorkManagerBean extends SettableBean {
   String getName();

   void setName(String var1);

   ResponseTimeRequestClassBean getResponseTimeRequestClass();

   ResponseTimeRequestClassBean createResponseTimeRequestClass();

   void destroyResponseTimeRequestClass();

   FairShareRequestClassBean getFairShareRequestClass();

   FairShareRequestClassBean createFairShareRequestClass();

   void destroyFairShareRequestClass();

   ContextRequestClassBean getContextRequestClass();

   ContextRequestClassBean createContextRequestClass();

   void destroyContextRequestClass();

   String getRequestClassName();

   void setRequestClassName(String var1);

   MinThreadsConstraintBean getMinThreadsConstraint();

   MinThreadsConstraintBean createMinThreadsConstraint();

   void destroyMinThreadsConstraint();

   String getMinThreadsConstraintName();

   void setMinThreadsConstraintName(String var1);

   MaxThreadsConstraintBean getMaxThreadsConstraint();

   MaxThreadsConstraintBean createMaxThreadsConstraint();

   void destroyMaxThreadsConstraint();

   String getMaxThreadsConstraintName();

   void setMaxThreadsConstraintName(String var1);

   CapacityBean getCapacity();

   CapacityBean createCapacity();

   void destroyCapacity();

   String getCapacityName();

   void setCapacityName(String var1);

   WorkManagerShutdownTriggerBean getWorkManagerShutdownTrigger();

   WorkManagerShutdownTriggerBean createWorkManagerShutdownTrigger();

   void destroyWorkManagerShutdownTrigger();

   boolean getIgnoreStuckThreads();

   void setIgnoreStuckThreads(boolean var1);

   String getId();

   void setId(String var1);
}
