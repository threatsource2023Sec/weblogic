package weblogic.jaxrs.server.extension;

import javax.enterprise.deploy.shared.ModuleType;
import weblogic.application.ModuleExtensionContext;
import weblogic.application.compiler.ToolsContext;
import weblogic.application.compiler.ToolsModule;
import weblogic.application.compiler.ToolsModuleExtension;
import weblogic.application.compiler.ToolsModuleExtensionFactory;
import weblogic.descriptor.Descriptor;
import weblogic.j2ee.descriptor.WebAppBean;
import weblogic.servlet.internal.WebBaseModuleExtensionContext;
import weblogic.utils.compiler.ToolFailureException;

public class JaxRsToolsModuleExtensionFactory implements ToolsModuleExtensionFactory {
   public ModuleType[] getSupportedModuleTypes() {
      return new ModuleType[]{ModuleType.WAR};
   }

   public final Class[] getSupportedClassLevelAnnotations() {
      return DeployHelper.JAXRS_ANNOTATIONS;
   }

   public ToolsModuleExtension create(ModuleExtensionContext extensionCtx, ToolsContext ctx, ToolsModule extensibleModule, Descriptor standardDD) throws ToolFailureException {
      return standardDD != null && standardDD.getRootBean() instanceof WebAppBean && extensionCtx instanceof WebBaseModuleExtensionContext ? new JaxRsToolsModuleExtension((WebBaseModuleExtensionContext)extensionCtx, ctx, extensibleModule, standardDD) : null;
   }
}
