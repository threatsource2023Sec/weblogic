package weblogic.management.configuration;

public interface CapacityMBean extends DeploymentMBean {
   int getCount();

   void setCount(int var1);
}
