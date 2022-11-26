package weblogic.application.compiler;

import org.jvnet.hk2.annotations.Service;

@Service
public class GenVersionToolsInitializer implements ToolsInitializer {
   public void init() {
      ToolsFactoryManager.addMergerFactory(new GenVersionFactory());
   }
}
