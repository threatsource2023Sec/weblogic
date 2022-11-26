package weblogic.j2ee.descriptor.wl.customizers;

import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.Customizer;
import weblogic.j2ee.descriptor.wl.ModuleDescriptorBean;
import weblogic.j2ee.descriptor.wl.ModuleOverrideBean;
import weblogic.j2ee.descriptor.wl.VariableAssignmentBean;
import weblogic.j2ee.descriptor.wl.VariableBean;

public interface DeploymentPlanBeanCustomizer extends Customizer {
   ModuleOverrideBean findModuleOverride(String var1);

   ModuleDescriptorBean findModuleDescriptor(String var1, String var2);

   boolean rootModule(String var1);

   boolean hasVariable(ModuleDescriptorBean var1, DescriptorBean var2, String var3) throws IllegalArgumentException;

   void findAndRemoveAllBeanVariables(ModuleDescriptorBean var1, DescriptorBean var2) throws IllegalArgumentException;

   VariableBean findVariable(ModuleDescriptorBean var1, DescriptorBean var2, String var3) throws IllegalArgumentException;

   VariableBean findOrCreateVariable(ModuleDescriptorBean var1, DescriptorBean var2, String var3) throws IllegalArgumentException;

   VariableBean findOrCreateVariable(ModuleDescriptorBean var1, DescriptorBean var2, String var3, boolean var4) throws IllegalArgumentException;

   VariableBean findOrCreateVariable(ModuleDescriptorBean var1, DescriptorBean var2, String var3, boolean var4, Object var5) throws IllegalArgumentException;

   VariableAssignmentBean[] findVariableAssignments(VariableBean var1);

   VariableAssignmentBean findVariableAssignment(ModuleDescriptorBean var1, DescriptorBean var2, String var3) throws IllegalArgumentException;

   Object valueOf(VariableBean var1);

   VariableAssignmentBean assignVariable(VariableBean var1, ModuleDescriptorBean var2, DescriptorBean var3, String var4);

   ModuleOverrideBean findRootModule();

   boolean isRemovable(DescriptorBean var1) throws IllegalArgumentException;
}
