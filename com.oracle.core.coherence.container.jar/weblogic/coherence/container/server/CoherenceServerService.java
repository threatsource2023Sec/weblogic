package weblogic.coherence.container.server;

import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.application.ApplicationFactoryManager;
import weblogic.server.AbstractServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(10)
public class CoherenceServerService extends AbstractServerService {
   public void start() throws ServiceFailureException {
      this.initialize();
   }

   private void initialize() throws ServiceFailureException {
      this.initFactories();
   }

   private void initFactories() throws ServiceFailureException {
      ApplicationFactoryManager afm = ApplicationFactoryManager.getApplicationFactoryManager();

      try {
         afm.addLibraryFactory(new GarLibraryFactory());
         afm.addDeploymentFactory(new CoherenceDeploymentFactory());
         afm.addWblogicModuleFactory(new CoherenceModuleFactory());
      } catch (Exception var3) {
         throw new ServiceFailureException(var3.getMessage(), var3);
      }
   }
}
