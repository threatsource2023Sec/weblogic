package weblogic.management.configuration;

public interface MinThreadsConstraintMBean extends DeploymentMBean {
   int getCount();

   void setCount(int var1);
}
