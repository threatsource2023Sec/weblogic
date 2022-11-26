package weblogic.jdbc.tools;

import org.jvnet.hk2.annotations.Service;
import weblogic.application.compiler.ToolsFactoryManager;
import weblogic.application.compiler.ToolsInitializer;

@Service
public class JDBCToolsInitializer implements ToolsInitializer {
   public void init() {
      ToolsFactoryManager.addWLModuleFactory(new JDBCModuleFactory());
      ToolsFactoryManager.addStandaloneModuleFactory(new StandaloneJDBCModuleFactory());
   }
}
