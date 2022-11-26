package weblogic.managedbean;

import javax.enterprise.deploy.shared.ModuleType;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.application.ApplicationFactoryManager;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(10)
public final class ManagedBeanService extends AbstractServerService {
   @Inject
   @Named("DomainAccessService")
   private ServerService dependencyOnDomainAccessService;

   public synchronized void halt() throws ServiceFailureException {
   }

   public synchronized void stop() throws ServiceFailureException {
      this.halt();
   }

   public synchronized void start() throws ServiceFailureException {
      ApplicationFactoryManager afm = ApplicationFactoryManager.getApplicationFactoryManager();
      ManagedBeanModuleExtensionFactory mmef = new ManagedBeanModuleExtensionFactory();
      afm.addModuleExtensionFactory(ModuleType.EJB.toString(), mmef);
      afm.addModuleExtensionFactory(ModuleType.WAR.toString(), mmef);
   }

   public String getName() {
      return "Managed Beans Container";
   }

   public String getVersion() {
      return "1.0";
   }
}
