package weblogic.application;

import weblogic.descriptor.Descriptor;

public interface ModuleExtensionFactory extends AppSupportDeclaration {
   ModuleExtension create(ModuleExtensionContext var1, ApplicationContextInternal var2, Module var3, Descriptor var4) throws ModuleException;
}
