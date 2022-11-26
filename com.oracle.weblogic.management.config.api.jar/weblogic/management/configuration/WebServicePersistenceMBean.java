package weblogic.management.configuration;

public interface WebServicePersistenceMBean extends ConfigurationMBean {
   String getDefaultLogicalStoreName();

   void setDefaultLogicalStoreName(String var1);

   WebServiceLogicalStoreMBean createWebServiceLogicalStore(String var1);

   void destroyWebServiceLogicalStore(WebServiceLogicalStoreMBean var1);

   WebServiceLogicalStoreMBean[] getWebServiceLogicalStores();

   WebServiceLogicalStoreMBean lookupWebServiceLogicalStore(String var1);

   WebServicePhysicalStoreMBean createWebServicePhysicalStore(String var1);

   void destroyWebServicePhysicalStore(WebServicePhysicalStoreMBean var1);

   WebServicePhysicalStoreMBean[] getWebServicePhysicalStores();

   WebServicePhysicalStoreMBean lookupWebServicePhysicalStore(String var1);
}
