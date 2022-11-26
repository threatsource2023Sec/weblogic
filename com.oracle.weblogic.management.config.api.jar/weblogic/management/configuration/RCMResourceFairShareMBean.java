package weblogic.management.configuration;

public interface RCMResourceFairShareMBean extends ConfigurationMBean {
   FairShareConstraintMBean getFairShareConstraint();

   FairShareConstraintMBean createFairShareConstraint(String var1);

   FairShareConstraintMBean createFairShareConstraint(String var1, int var2);

   void destroyFairShareConstraint(FairShareConstraintMBean var1);
}
