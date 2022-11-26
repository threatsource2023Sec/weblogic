package weblogic.management.configuration;

public interface FairShareRequestClassMBean extends DeploymentMBean {
   int getFairShare();

   void setFairShare(int var1);
}
