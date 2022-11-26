package weblogic.application.compiler;

import java.util.Map;
import javax.enterprise.deploy.shared.ModuleType;
import weblogic.application.ModuleContext;
import weblogic.application.compiler.deploymentview.EditableDeployableObject;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.compiler.ToolFailureException;

public interface ToolsModule {
   String getURI();

   String getAltDD();

   ModuleType getModuleType();

   String getStandardDescriptorURI();

   String[] getApplicationNameXPath();

   ClassFinder init(ModuleContext var1, ToolsContext var2, GenericClassLoader var3) throws ToolFailureException;

   boolean needsClassLoader();

   Map merge() throws ToolFailureException;

   void populateValidationInfo(GenericClassLoader var1) throws ToolFailureException;

   Map compile(GenericClassLoader var1) throws ToolFailureException;

   void write() throws ToolFailureException;

   boolean isDeployableObject();

   void enhanceDeploymentView(EditableDeployableObject var1);

   void cleanup();
}
