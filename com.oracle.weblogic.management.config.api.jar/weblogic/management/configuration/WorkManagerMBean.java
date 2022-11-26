package weblogic.management.configuration;

public interface WorkManagerMBean extends DeploymentMBean {
   FairShareRequestClassMBean getFairShareRequestClass();

   void setFairShareRequestClass(FairShareRequestClassMBean var1);

   ResponseTimeRequestClassMBean getResponseTimeRequestClass();

   void setResponseTimeRequestClass(ResponseTimeRequestClassMBean var1);

   ContextRequestClassMBean getContextRequestClass();

   void setContextRequestClass(ContextRequestClassMBean var1);

   MinThreadsConstraintMBean getMinThreadsConstraint();

   void setMinThreadsConstraint(MinThreadsConstraintMBean var1);

   MaxThreadsConstraintMBean getMaxThreadsConstraint();

   void setMaxThreadsConstraint(MaxThreadsConstraintMBean var1);

   CapacityMBean getCapacity();

   void setCapacity(CapacityMBean var1);

   boolean getIgnoreStuckThreads();

   void setIgnoreStuckThreads(boolean var1);

   WorkManagerShutdownTriggerMBean getWorkManagerShutdownTrigger();

   WorkManagerShutdownTriggerMBean createWorkManagerShutdownTrigger();

   void destroyWorkManagerShutdownTrigger();

   boolean isApplicationScope();

   void setApplicationScope(boolean var1);
}
