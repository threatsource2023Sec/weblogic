package weblogic.j2ee.descriptor;

public interface ConnectorBean extends JavaEEModuleNameBean, ModuleNameBean {
   String getJavaEEModuleName();

   String getModuleName();

   void setModuleName(String var1);

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

   String getVendorName();

   void setVendorName(String var1);

   String getEisType();

   void setEisType(String var1);

   String getResourceAdapterVersion();

   void setResourceAdapterVersion(String var1);

   LicenseBean getLicense();

   LicenseBean createLicense();

   void destroyLicense(LicenseBean var1);

   String[] getRequiredWorkContexts();

   void addRequiredWorkContext(String var1);

   void removeRequiredWorkContext(String var1);

   ResourceAdapterBean getResourceAdapter();

   ResourceAdapterBean createResourceAdapter();

   void destroyResourceAdapter(ResourceAdapterBean var1);

   String getVersion();

   void setVersion(String var1);

   boolean isMetadataComplete();

   void setMetadataComplete(boolean var1);

   String getId();

   void setId(String var1);
}
