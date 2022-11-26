package weblogic.application.compiler;

import org.jvnet.hk2.annotations.Service;
import weblogic.application.ddconvert.ConverterFactoryManager;
import weblogic.application.ddconvert.EarConverterFactory;

@Service
public class EARToolsInitializer implements ToolsInitializer {
   public void init() {
      ToolsFactoryManager.addCompilerFactory(new EARCompilerFactory());
      ToolsFactoryManager.addMergerFactory(new EARMergerFactory());
      ConverterFactoryManager.instance.addConverterFactory(new EarConverterFactory());
   }
}
