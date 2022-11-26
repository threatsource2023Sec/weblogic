package weblogic.management.scripting.jsr88;

import java.io.FileNotFoundException;
import java.io.IOException;
import javax.enterprise.deploy.spi.DConfigBean;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.j2ee.descriptor.wl.ModuleDescriptorBean;
import weblogic.j2ee.descriptor.wl.ModuleOverrideBean;
import weblogic.j2ee.descriptor.wl.VariableAssignmentBean;
import weblogic.j2ee.descriptor.wl.VariableBean;

public interface WLSTPlan {
   VariableBean[] getVariables();

   void showVariables();

   void showVars();

   void setVariableValue(String var1, String var2);

   void setVarValue(String var1, String var2);

   String getVariableValue(String var1);

   String getVarValue(String var1);

   VariableBean getVariable(String var1);

   VariableBean getVar(String var1);

   VariableBean createVariable(String var1, String var2);

   VariableBean createVar(String var1, String var2);

   void destroyVariable(String var1);

   void destroyVar(String var1);

   DeploymentPlanBean getDeploymentPlan();

   void save() throws FileNotFoundException, ConfigurationException, IOException;

   ModuleOverrideBean getModuleOverride(String var1);

   ModuleOverrideBean getMO(String var1);

   ModuleOverrideBean[] getModuleOverrides();

   ModuleOverrideBean[] getMOs();

   void showModuleOverrides();

   void showMOs();

   ModuleDescriptorBean createModuleDescriptor(String var1, String var2);

   ModuleDescriptorBean createMD(String var1, String var2);

   ModuleDescriptorBean getModuleDescriptor(String var1, String var2);

   ModuleDescriptorBean getMD(String var1, String var2);

   VariableAssignmentBean createVariableAssignment(String var1, String var2, String var3);

   VariableAssignmentBean createVA(String var1, String var2, String var3);

   void showVariableAssignments();

   void showVAs();

   void showModuleDescriptors();

   void showMDs();

   VariableAssignmentBean getVariableAssignment(String var1, String var2, String var3);

   VariableAssignmentBean getVA(String var1, String var2, String var3);

   void destroyVariableAssignment(String var1, String var2, String var3);

   void destroyVA(String var1, String var2, String var3);

   void help(String var1);

   DConfigBean getDConfigBean(ModuleDescriptorBean var1);

   void applyOverride(String var1, String var2, String var3, String var4);
}
