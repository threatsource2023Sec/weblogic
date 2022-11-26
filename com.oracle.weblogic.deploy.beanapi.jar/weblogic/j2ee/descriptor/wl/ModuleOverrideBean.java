package weblogic.j2ee.descriptor.wl;

public interface ModuleOverrideBean {
   String getModuleName();

   void setModuleName(String var1);

   String getModuleType();

   void setModuleType(String var1);

   ModuleDescriptorBean[] getModuleDescriptors();

   ModuleDescriptorBean createModuleDescriptor();

   void destroyModuleDescriptor(ModuleDescriptorBean var1);
}
