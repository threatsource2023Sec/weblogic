package weblogic.osgi.internal;

import java.security.AccessController;
import java.util.LinkedList;
import java.util.List;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.application.ApplicationFactoryManager;
import weblogic.management.configuration.OsgiFrameworkMBean;
import weblogic.management.internal.DeploymentHandlerHome;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.AbstractServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(10)
public class OSGiServerService extends AbstractServerService {
   List serviceProviders = new LinkedList();
   private OSGiDeploymentHandlerExtended handler;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private OsgiFrameworkMBean[] osgiFrameworkMBeans;

   public OSGiServerService() {
      this.serviceProviders.add(new WorkManagerServiceProvider());
      this.serviceProviders.add(new DataSourceServiceProvider());
   }

   public void start() throws ServiceFailureException {
      this.handler = new OSGiDeploymentHandlerExtended(this.serviceProviders);
      DeploymentHandlerHome.getInstance().addDeploymentHandlerExtended(this.handler);
      ApplicationFactoryManager afm = ApplicationFactoryManager.getApplicationFactoryManager();
      OSGiAppDeploymentExtensionFactory oade = new OSGiAppDeploymentExtensionFactory();
      afm.addAppDeploymentExtensionFactory(oade);
   }

   public void stop() throws ServiceFailureException {
      if (this.handler != null) {
         DeploymentHandlerHome.getInstance().removedDeploymentHandlerExtended(this.handler);
      }

   }

   public String toString() {
      return "OSGiServerService( " + System.identityHashCode(this) + ")";
   }
}
