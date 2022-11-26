package weblogic.management.configuration;

public interface ContextCaseMBean extends DeploymentMBean {
   String getUserName();

   void setUserName(String var1);

   String getGroupName();

   void setGroupName(String var1);

   String getRequestClassName();

   void setRequestClassName(String var1);

   ResponseTimeRequestClassMBean getResponseTimeRequestClass();

   ResponseTimeRequestClassMBean createResponseTimeRequestClass(String var1);

   void destroyResponseTimeRequestClass(ResponseTimeRequestClassMBean var1);

   FairShareRequestClassMBean getFairShareRequestClass();

   FairShareRequestClassMBean createFairShareRequestClass(String var1);

   void destroyFairShareRequestClass(FairShareRequestClassMBean var1);
}
