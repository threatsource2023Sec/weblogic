package weblogic.j2eeclient;

import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.application.ApplicationFactoryManager;
import weblogic.server.AbstractServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(10)
public final class AppClientDeploymentService extends AbstractServerService {
   public void start() throws ServiceFailureException {
      ApplicationFactoryManager afm = ApplicationFactoryManager.getApplicationFactoryManager();
      afm.addModuleFactory(new AppClientModuleFactory());
   }
}
