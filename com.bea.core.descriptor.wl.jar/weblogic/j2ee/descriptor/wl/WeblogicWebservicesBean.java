package weblogic.j2ee.descriptor.wl;

public interface WeblogicWebservicesBean {
   String DESCRIPTOR_VERSION = "1.3";

   WebserviceDescriptionBean[] getWebserviceDescriptions();

   WebserviceDescriptionBean createWebserviceDescription();

   void destroyWebserviceDescription(WebserviceDescriptionBean var1);

   WebserviceSecurityBean getWebserviceSecurity();

   WebserviceSecurityBean createWebserviceSecurity();

   void destroyWebserviceSecurity(WebserviceSecurityBean var1);

   String getVersion();

   void setVersion(String var1);
}
