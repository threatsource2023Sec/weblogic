package weblogic.management.configuration;

public interface SelfTuningMBean extends ConfigurationMBean {
   FairShareRequestClassMBean[] getFairShareRequestClasses();

   FairShareRequestClassMBean createFairShareRequestClass(String var1);

   void destroyFairShareRequestClass(FairShareRequestClassMBean var1);

   FairShareRequestClassMBean lookupFairShareRequestClass(String var1);

   ResponseTimeRequestClassMBean[] getResponseTimeRequestClasses();

   ResponseTimeRequestClassMBean createResponseTimeRequestClass(String var1);

   void destroyResponseTimeRequestClass(ResponseTimeRequestClassMBean var1);

   ResponseTimeRequestClassMBean lookupResponseTimeRequestClass(String var1);

   ContextRequestClassMBean[] getContextRequestClasses();

   ContextRequestClassMBean createContextRequestClass(String var1);

   void destroyContextRequestClass(ContextRequestClassMBean var1);

   ContextRequestClassMBean lookupContextRequestClass(String var1);

   MinThreadsConstraintMBean[] getMinThreadsConstraints();

   MinThreadsConstraintMBean createMinThreadsConstraint(String var1);

   void destroyMinThreadsConstraint(MinThreadsConstraintMBean var1);

   MinThreadsConstraintMBean lookupMinThreadsConstraint(String var1);

   MaxThreadsConstraintMBean[] getMaxThreadsConstraints();

   MaxThreadsConstraintMBean createMaxThreadsConstraint(String var1);

   void destroyMaxThreadsConstraint(MaxThreadsConstraintMBean var1);

   MaxThreadsConstraintMBean lookupMaxThreadsConstraint(String var1);

   CapacityMBean[] getCapacities();

   CapacityMBean createCapacity(String var1);

   void destroyCapacity(CapacityMBean var1);

   CapacityMBean lookupCapacity(String var1);

   WorkManagerMBean[] getWorkManagers();

   WorkManagerMBean createWorkManager(String var1);

   void destroyWorkManager(WorkManagerMBean var1);

   WorkManagerMBean lookupWorkManager(String var1);

   int getPartitionFairShare();

   void setPartitionFairShare(int var1);
}
