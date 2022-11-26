package weblogic.messaging.interception.configuration;

import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.application.ApplicationFactoryManager;
import weblogic.messaging.interception.module.InterceptionDeploymentFactory;
import weblogic.messaging.interception.module.InterceptionModuleFactory;
import weblogic.server.AbstractServerService;

@Service
@Named
@RunLevel(10)
public final class Configurator extends AbstractServerService {
   public Configurator() {
      ApplicationFactoryManager afm = ApplicationFactoryManager.getApplicationFactoryManager();
      afm.addDeploymentFactory(new InterceptionDeploymentFactory());
      afm.addWblogicModuleFactory(new InterceptionModuleFactory());
   }

   public void start() {
   }
}
