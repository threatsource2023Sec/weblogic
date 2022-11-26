package weblogic.j2eeclient.tools;

import org.jvnet.hk2.annotations.Service;
import weblogic.application.compiler.ToolsFactoryManager;
import weblogic.application.compiler.ToolsInitializer;

@Service
public class CARModuleInitializer implements ToolsInitializer {
   public void init() {
      ToolsFactoryManager.addStandaloneModuleFactory(new StandaloneCARModuleFactory());
      ToolsFactoryManager.addModuleFactory(new CARModuleFactory());
   }
}
