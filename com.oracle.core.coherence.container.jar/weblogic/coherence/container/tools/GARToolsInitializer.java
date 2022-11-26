package weblogic.coherence.container.tools;

import org.jvnet.hk2.annotations.Service;
import weblogic.application.compiler.ToolsFactoryManager;
import weblogic.application.compiler.ToolsInitializer;

@Service
public class GARToolsInitializer implements ToolsInitializer {
   public void init() {
      ToolsFactoryManager.addWLModuleFactory(new GARModuleFactory());
      ToolsFactoryManager.addStandaloneModuleFactory(new StandaloneGARModuleFactory());
   }
}
