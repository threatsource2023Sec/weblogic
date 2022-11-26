package weblogic.j2ee.descriptor;

public interface WebservicesBean {
   String DESCRIPTOR_VERSION = "1.4";

   String[] getDescriptions();

   void addDescription(String var1);

   void removeDescription(String var1);

   void setDescriptions(String[] var1);

   String[] getDisplayNames();

   void addDisplayName(String var1);

   void removeDisplayName(String var1);

   void setDisplayNames(String[] var1);

   IconBean[] getIcons();

   IconBean createIcon();

   void destroyIcon(IconBean var1);

   WebserviceDescriptionBean[] getWebserviceDescriptions();

   WebserviceDescriptionBean createWebserviceDescription();

   void destroyWebserviceDescription(WebserviceDescriptionBean var1);

   WebserviceDescriptionBean lookupWebserviceDescription(String var1);

   String getVersion();

   void setVersion(String var1);

   String getId();

   void setId(String var1);
}
