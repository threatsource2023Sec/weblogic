package weblogic.connector.tools;

import org.jvnet.hk2.annotations.Service;
import weblogic.application.compiler.ToolsFactoryManager;
import weblogic.application.compiler.ToolsInitializer;
import weblogic.application.ddconvert.ConverterFactoryManager;

@Service
public class RarToolsInitializer implements ToolsInitializer {
   public void init() {
      ToolsFactoryManager.addModuleFactory(new RARModuleFactory());
      ToolsFactoryManager.addStandaloneModuleFactory(new StandaloneRARModuleFactory());
      ConverterFactoryManager.instance.addConverterFactory(new RarConverterFactory());
   }
}
