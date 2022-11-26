package weblogic.management.configuration;

public interface ResponseTimeRequestClassMBean extends DeploymentMBean {
   int getGoalMs();

   void setGoalMs(int var1);
}
