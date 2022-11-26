package weblogic.management.configuration;

public interface StartupClassMBean extends ClassDeploymentMBean {
   boolean getFailureIsFatal();

   void setFailureIsFatal(boolean var1);

   boolean getLoadBeforeAppDeployments();

   void setLoadBeforeAppDeployments(boolean var1);

   boolean getLoadBeforeAppActivation();

   void setLoadBeforeAppActivation(boolean var1);

   boolean getLoadAfterAppsRunning();

   void setLoadAfterAppsRunning(boolean var1);
}
