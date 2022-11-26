package weblogic.application.compiler;

import weblogic.application.AppSupportDeclaration;
import weblogic.application.ModuleExtensionContext;
import weblogic.descriptor.Descriptor;
import weblogic.utils.compiler.ToolFailureException;

public interface ToolsModuleExtensionFactory extends AppSupportDeclaration {
   ToolsModuleExtension create(ModuleExtensionContext var1, ToolsContext var2, ToolsModule var3, Descriptor var4) throws ToolFailureException;
}
