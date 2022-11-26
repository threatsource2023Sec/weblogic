package weblogic.ejb.tools;

import javax.enterprise.deploy.shared.ModuleType;
import org.jvnet.hk2.annotations.Service;
import weblogic.application.ApplicationFactoryManager;
import weblogic.application.compiler.ToolsFactoryManager;
import weblogic.application.compiler.ToolsInitializer;
import weblogic.application.ddconvert.ConverterFactoryManager;
import weblogic.ejb.spi.EJBLibraryFactory;

@Service
public class EJBToolsInitializer implements ToolsInitializer {
   public void init() {
      ToolsFactoryManager.addModuleFactory(new EJBModuleFactory());
      ToolsFactoryManager.addStandaloneModuleFactory(new StandaloneEJBModuleFactory());
      ToolsFactoryManager.addModuleExtensionFactory(ModuleType.WAR.toString(), new EJBToolsModuleExtensionFactory());
      ApplicationFactoryManager.getApplicationFactoryManager().addLibraryFactory(new EJBLibraryFactory());
      ConverterFactoryManager.instance.addConverterFactory(new EJBConverterFactory());
   }
}
