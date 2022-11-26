package weblogic.persistence;

import org.jvnet.hk2.annotations.Service;
import weblogic.application.compiler.ToolsFactoryManager;
import weblogic.application.compiler.ToolsInitializer;

@Service
public class PersistenceToolsInitializer implements ToolsInitializer {
   public void init() {
      ToolsFactoryManager.addExtensionFactory(new PersistenceToolsExtensionFactory());
   }
}
