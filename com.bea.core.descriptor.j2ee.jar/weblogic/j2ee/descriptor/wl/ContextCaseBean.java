package weblogic.j2ee.descriptor.wl;

public interface ContextCaseBean {
   String getUserName();

   void setUserName(String var1);

   String getGroupName();

   void setGroupName(String var1);

   String getRequestClassName();

   void setRequestClassName(String var1);

   String getId();

   void setId(String var1);

   ResponseTimeRequestClassBean getResponseTimeRequestClass();

   ResponseTimeRequestClassBean createResponseTimeRequestClass();

   void destroyResponseTimeRequestClass();

   FairShareRequestClassBean getFairShareRequestClass();

   FairShareRequestClassBean createFairShareRequestClass();

   void destroyFairShareRequestClass();
}
