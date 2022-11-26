package weblogic.j2ee.descriptor.wl;

public interface WeblogicExtensionBean {
   ModuleProviderBean[] getModuleProviders();

   ModuleProviderBean createModuleProvider();

   void destroyModuleProvider(ModuleProviderBean var1);

   CustomModuleBean[] getCustomModules();

   CustomModuleBean createCustomModule();

   void destroyCustomModule(CustomModuleBean var1);

   String getVersion();

   void setVersion(String var1);
}
