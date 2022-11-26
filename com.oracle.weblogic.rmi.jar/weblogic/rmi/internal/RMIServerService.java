package weblogic.rmi.internal;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.AccessController;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.common.internal.RMIBootServiceImpl;
import weblogic.corba.server.naming.ReferenceHelperImpl;
import weblogic.health.HealthMonitorService;
import weblogic.jndi.ObjectCopier;
import weblogic.jndi.internal.JNDIEnvironment;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JTAMBean;
import weblogic.management.provider.ManagementService;
import weblogic.rmi.extensions.server.ReferenceHelper;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.rmi.provider.ServerWorkContextAccessController;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.AbstractServerService;
import weblogic.server.RemoteLifeCycleOperationsImpl;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(10)
public final class RMIServerService extends AbstractServerService {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public void start() throws ServiceFailureException {
      try {
         ReferenceHelper.setReferenceHelper(new ReferenceHelperImpl());
         OIDManager.getInstance().initialize();
         ServerHelper.exportObject(new RMIBootServiceImpl());
         ServerWorkContextAccessController.initialize();
         ServerHelper.exportObject(RemoteLifeCycleOperationsImpl.getInstance());
         JNDIEnvironment.getJNDIEnvironment().addCopier(new T3ObjectCopier());
         BasicServerRef.initOOMENotifier(HealthMonitorService.getOomeNotifier());
      } catch (RemoteException var2) {
         throw new ServiceFailureException(var2);
      }
   }

   public static int getTransactionTimeoutMillis() {
      DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      JTAMBean jta = domain.getJTA();
      return jta.getTimeoutSeconds() * 1000;
   }

   public static class T3ObjectCopier implements ObjectCopier {
      public boolean mayCopy(Object objectToCopy) {
         return objectToCopy instanceof Remote;
      }

      public Object copyObject(Object objectToCopy) throws IOException {
         Remote r = (Remote)objectToCopy;
         return ServerHelper.replaceAndResolveRemoteObject(r);
      }

      public int getPriority() {
         return 60;
      }
   }
}
