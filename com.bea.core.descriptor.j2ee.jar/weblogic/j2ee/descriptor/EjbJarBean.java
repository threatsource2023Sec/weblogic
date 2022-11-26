package weblogic.j2ee.descriptor;

public interface EjbJarBean extends JavaEEModuleNameBean, ModuleNameBean {
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

   EnterpriseBeansBean getEnterpriseBeans();

   EnterpriseBeansBean createEnterpriseBeans();

   void destroyEnterpriseBeans(EnterpriseBeansBean var1);

   InterceptorsBean getInterceptors();

   InterceptorsBean createInterceptors();

   void destroyInterceptors(InterceptorsBean var1);

   RelationshipsBean getRelationships();

   RelationshipsBean createRelationships();

   void destroyRelationships(RelationshipsBean var1);

   AssemblyDescriptorBean getAssemblyDescriptor();

   AssemblyDescriptorBean createAssemblyDescriptor();

   void destroyAssemblyDescriptor(AssemblyDescriptorBean var1);

   String getEjbClientJar();

   void setEjbClientJar(String var1);

   String getVersion();

   void setVersion(String var1);

   boolean isMetadataComplete();

   void setMetadataComplete(boolean var1);

   String getId();

   void setId(String var1);
}
