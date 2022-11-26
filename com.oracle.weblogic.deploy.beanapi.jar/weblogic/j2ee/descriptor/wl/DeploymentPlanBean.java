package weblogic.j2ee.descriptor.wl;

import weblogic.descriptor.DescriptorBean;

public interface DeploymentPlanBean {
   String getDescription();

   void setDescription(String var1);

   String getApplicationName();

   void setApplicationName(String var1);

   String getVersion();

   void setVersion(String var1);

   VariableDefinitionBean getVariableDefinition();

   ModuleOverrideBean[] getModuleOverrides();

   ModuleOverrideBean createModuleOverride();

   void destroyModuleOverride(ModuleOverrideBean var1);

   ModuleOverrideBean findModuleOverride(String var1);

   ModuleDescriptorBean findModuleDescriptor(String var1, String var2);

   String getConfigRoot();

   void setConfigRoot(String var1);

   boolean rootModule(String var1);

   boolean hasVariable(ModuleDescriptorBean var1, DescriptorBean var2, String var3) throws IllegalArgumentException;

   VariableBean findVariable(ModuleDescriptorBean var1, DescriptorBean var2, String var3) throws IllegalArgumentException;

   VariableBean findOrCreateVariable(ModuleDescriptorBean var1, DescriptorBean var2, String var3, boolean var4) throws IllegalArgumentException;

   VariableBean findOrCreateVariable(ModuleDescriptorBean var1, DescriptorBean var2, String var3) throws IllegalArgumentException;

   VariableBean findOrCreateVariable(ModuleDescriptorBean var1, DescriptorBean var2, String var3, boolean var4, Object var5) throws IllegalArgumentException;

   VariableAssignmentBean[] findVariableAssignments(VariableBean var1);

   VariableAssignmentBean findVariableAssignment(ModuleDescriptorBean var1, DescriptorBean var2, String var3) throws IllegalArgumentException;

   Object valueOf(VariableBean var1);

   VariableAssignmentBean assignVariable(VariableBean var1, ModuleDescriptorBean var2, DescriptorBean var3, String var4);

   boolean isGlobalVariables();

   void setGlobalVariables(boolean var1);

   ModuleOverrideBean findRootModule();

   void findAndRemoveAllBeanVariables(ModuleDescriptorBean var1, DescriptorBean var2) throws IllegalArgumentException;

   boolean isRemovable(DescriptorBean var1) throws IllegalArgumentException;
}
