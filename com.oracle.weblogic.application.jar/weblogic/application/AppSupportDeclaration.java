package weblogic.application;

import javax.enterprise.deploy.shared.ModuleType;

public interface AppSupportDeclaration {
   ModuleType[] getSupportedModuleTypes();

   Class[] getSupportedClassLevelAnnotations();
}
