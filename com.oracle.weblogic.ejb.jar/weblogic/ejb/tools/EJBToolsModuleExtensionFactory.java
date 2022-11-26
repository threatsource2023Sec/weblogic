package weblogic.ejb.tools;

import javax.enterprise.deploy.shared.ModuleType;
import weblogic.application.AnnotationProcessingException;
import weblogic.application.ModuleExtensionContext;
import weblogic.application.compiler.ToolsContext;
import weblogic.application.compiler.ToolsModule;
import weblogic.application.compiler.ToolsModuleExtension;
import weblogic.application.compiler.ToolsModuleExtensionFactory;
import weblogic.descriptor.Descriptor;
import weblogic.ejb.container.deployer.BaseModuleExtensionFactory;
import weblogic.utils.compiler.ToolFailureException;

public class EJBToolsModuleExtensionFactory extends BaseModuleExtensionFactory implements ToolsModuleExtensionFactory {
   public ToolsModuleExtension create(ModuleExtensionContext extCtx, ToolsContext ctx, ToolsModule toolsModule, Descriptor standardDD) throws ToolFailureException {
      try {
         return !this.hasEJBDescriptor(extCtx) && !this.hasAnnotatedEJBs(extCtx, standardDD, false) ? null : new EJBToolsModuleExtension(extCtx, ctx, toolsModule);
      } catch (IllegalArgumentException var6) {
         throw new ToolFailureException(var6.getMessage());
      } catch (AnnotationProcessingException var7) {
         throw new ToolFailureException("Error creating tools module extension.", var7);
      }
   }

   public ModuleType[] getSupportedModuleTypes() {
      return new ModuleType[]{ModuleType.WAR};
   }
}
