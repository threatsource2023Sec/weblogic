package weblogic.kodo;

import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.application.ApplicationFactoryManager;
import weblogic.application.compiler.ToolsFactoryManager;
import weblogic.persistence.PersistenceDeployment;
import weblogic.persistence.PersistenceToolsExtensionFactory;
import weblogic.server.AbstractServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(10)
public class KodoService extends AbstractServerService {
   public void start() throws ServiceFailureException {
      ApplicationFactoryManager.getApplicationFactoryManager().addAppDeploymentExtensionFactory(new PersistenceDeployment.PersistenceDeploymentExtensionFactory());
      ToolsFactoryManager.addExtensionFactory(new PersistenceToolsExtensionFactory());
      ToolsFactoryManager.addExtensionFactory(new KodoToolsExtension.KodoToolsExtensionFactory());
   }
}
