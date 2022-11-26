package weblogic.j2ee.descriptor.wl;

import weblogic.descriptor.DescriptorBean;

public interface ResourceDeploymentPlanBean {
   String getDescription();

   void setDescription(String var1);

   VariableDefinitionBean getVariableDefinition();

   boolean isGlobalVariables();

   void setGlobalVariables(boolean var1);

   ExternalResourceOverrideBean[] getExternalResourceOverrides();

   ExternalResourceOverrideBean createExternalResourceOverride();

   void destroyExternalResourceOverride(ExternalResourceOverrideBean var1);

   ConfigResourceOverrideBean[] getConfigResourceOverrides();

   ConfigResourceOverrideBean createConfigResourceOverride();

   void destroyConfigResourceOverride(ConfigResourceOverrideBean var1);

   ConfigResourceOverrideBean findConfigResourceOverride(String var1);

   ExternalResourceOverrideBean findExternalResourceOverride(String var1);

   boolean hasVariable(ConfigResourceOverrideBean var1, DescriptorBean var2, String var3) throws IllegalArgumentException;

   VariableBean findVariable(ConfigResourceOverrideBean var1, DescriptorBean var2, String var3) throws IllegalArgumentException;

   VariableAssignmentBean[] findVariableAssignments(VariableBean var1);

   VariableAssignmentBean findVariableAssignment(ConfigResourceOverrideBean var1, DescriptorBean var2, String var3) throws IllegalArgumentException;

   VariableBean findOrCreateVariable(ConfigResourceOverrideBean var1, DescriptorBean var2, String var3) throws IllegalArgumentException;

   VariableBean findOrCreateVariable(ConfigResourceOverrideBean var1, DescriptorBean var2, String var3, boolean var4);

   VariableBean findOrCreateVariable(ConfigResourceOverrideBean var1, DescriptorBean var2, String var3, boolean var4, Object var5) throws IllegalArgumentException;

   Object valueOf(VariableBean var1);

   VariableAssignmentBean assignVariable(VariableBean var1, ConfigResourceOverrideBean var2, DescriptorBean var3, String var4);

   boolean isRemovable(DescriptorBean var1) throws IllegalArgumentException;
}
