package weblogic.kodo;

import org.jvnet.hk2.annotations.Service;
import weblogic.application.compiler.ToolsFactoryManager;
import weblogic.application.compiler.ToolsInitializer;

@Service
public class KodoToolsInitializer implements ToolsInitializer {
   public void init() {
      ToolsFactoryManager.addExtensionFactory(new KodoToolsExtension.KodoToolsExtensionFactory());
   }
}
