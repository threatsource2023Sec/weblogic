package weblogic.connector.tools;

import java.util.List;
import javax.enterprise.deploy.shared.ModuleType;
import weblogic.application.compiler.ToolsModule;
import weblogic.application.compiler.ToolsModuleFactory;
import weblogic.connector.configuration.AdditionalAnnotatedClassesProvider;
import weblogic.j2ee.descriptor.ModuleBean;

public class RARModuleFactory implements ToolsModuleFactory {
   public Boolean claim(ModuleBean m) {
      return m.getConnector() != null ? true : null;
   }

   public Boolean claim(ModuleBean m, List allClaimants) {
      return this.claim(m);
   }

   public ToolsModule create(ModuleBean m) {
      return RARModule.createEmbededRARModule(m.getConnector(), m.getAltDd());
   }

   public ModuleType[] getSupportedModuleTypes() {
      return new ModuleType[]{ModuleType.RAR};
   }

   public Class[] getSupportedClassLevelAnnotations() {
      return (Class[])AdditionalAnnotatedClassesProvider.ANNOTATIONS.toArray(new Class[AdditionalAnnotatedClassesProvider.ANNOTATIONS.size()]);
   }
}
