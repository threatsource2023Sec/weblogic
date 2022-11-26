package weblogic.connector.deploy;

import javax.enterprise.deploy.shared.ModuleType;
import weblogic.application.Module;
import weblogic.application.ModuleException;
import weblogic.application.ModuleFactory;
import weblogic.connector.configuration.AdditionalAnnotatedClassesProvider;
import weblogic.j2ee.descriptor.ModuleBean;

public class ConnectorModuleFactory implements ModuleFactory {
   public Module createModule(ModuleBean moduleDD) throws ModuleException {
      return moduleDD.getConnector() != null ? ConnectorModule.createEmbededConnectorModule(moduleDD.getConnector()) : null;
   }

   public ModuleType[] getSupportedModuleTypes() {
      return new ModuleType[]{ModuleType.RAR};
   }

   public Class[] getSupportedClassLevelAnnotations() {
      return (Class[])AdditionalAnnotatedClassesProvider.ANNOTATIONS.toArray(new Class[AdditionalAnnotatedClassesProvider.ANNOTATIONS.size()]);
   }
}
