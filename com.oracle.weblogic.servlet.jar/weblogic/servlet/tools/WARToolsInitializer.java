package weblogic.servlet.tools;

import org.jvnet.hk2.annotations.Service;
import weblogic.application.ApplicationFactoryManager;
import weblogic.application.compiler.ToolsFactoryManager;
import weblogic.application.compiler.ToolsInitializer;
import weblogic.application.ddconvert.ConverterFactoryManager;
import weblogic.servlet.internal.WarLibraryFactory;

@Service
public class WARToolsInitializer implements ToolsInitializer {
   public void init() {
      ToolsFactoryManager.addModuleFactory(new WARModuleFactory());
      ToolsFactoryManager.addStandaloneModuleFactory(new StandaloneWARModuleFactory());
      ApplicationFactoryManager.getApplicationFactoryManager().addLibraryFactory(new WarLibraryFactory());
      ConverterFactoryManager.instance.addConverterFactory(new WarConverterFactory());
   }
}
