package weblogic.jms.tools;

import org.jvnet.hk2.annotations.Service;
import weblogic.application.compiler.ToolsFactoryManager;
import weblogic.application.compiler.ToolsInitializer;

@Service
public class JMSToolsInitializer implements ToolsInitializer {
   public void init() {
      ToolsFactoryManager.addWLModuleFactory(new JMSModuleFactory());
      ToolsFactoryManager.addStandaloneModuleFactory(new StandaloneJMSModuleFactory());
   }
}
