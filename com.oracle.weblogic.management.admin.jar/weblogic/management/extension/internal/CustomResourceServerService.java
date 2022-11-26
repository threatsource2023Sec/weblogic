package weblogic.management.extension.internal;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.application.ApplicationFactoryManager;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(10)
public class CustomResourceServerService implements ServerService {
   public String getName() {
      return "CustomResourceServerService";
   }

   public String getVersion() {
      return "1.0.0.0";
   }

   @PostConstruct
   public void start() throws ServiceFailureException {
      ApplicationFactoryManager manager = ApplicationFactoryManager.getApplicationFactoryManager();
      manager.addDeploymentFactory(new CustomResourceDeploymentFactory());
   }

   public void stop() throws ServiceFailureException {
   }

   public void halt() throws ServiceFailureException {
   }
}
