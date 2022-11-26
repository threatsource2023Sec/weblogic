package weblogic.management.configuration;

public interface PartitionWorkManagerMBean extends ConfigurationMBean {
   int getSharedCapacityPercent();

   void setSharedCapacityPercent(int var1);

   int getFairShare();

   void setFairShare(int var1);

   int getMinThreadsConstraintCap();

   void setMinThreadsConstraintCap(int var1);

   int getMaxThreadsConstraint();

   void setMaxThreadsConstraint(int var1);

   void setMaxThreadsConstraintQueueSize(int var1);

   int getMaxThreadsConstraintQueueSize();
}
