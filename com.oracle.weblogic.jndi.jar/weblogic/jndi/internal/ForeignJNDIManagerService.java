package weblogic.jndi.internal;

import java.rmi.RemoteException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.NamingException;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.glassfish.hk2.extras.interception.Interceptor;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.ContractsProvided;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.configuration.util.PartitionManagerInterceptorAdapter;
import weblogic.management.configuration.util.ServerServiceInterceptor;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(10)
public final class ForeignJNDIManagerService extends AbstractServerService {
   @Inject
   @Named("RemoteNamingService")
   private ServerService dependencyOnRemoteNamingService;

   public void start() throws ServiceFailureException {
      ForeignJNDIManager.initialize();
   }

   @Service
   @Interceptor
   @ContractsProvided({ForeignJNDIPartitionManagerInterceptor.class, MethodInterceptor.class})
   @ServerServiceInterceptor(ForeignJNDIManagerService.class)
   public static class ForeignJNDIPartitionManagerInterceptor extends PartitionManagerInterceptorAdapter {
      public void startPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
         this.processBindings(partitionName);
         methodInvocation.proceed();
      }

      public void startPartitionInAdmin(MethodInvocation methodInvocation, String partitionName) throws Throwable {
         this.processBindings(partitionName);
         methodInvocation.proceed();
      }

      private void processBindings(String partitionName) throws RemoteException, NamingException {
         ForeignJNDIManager.getInstance().processForeignJNDIProviderLinks(partitionName);
      }
   }
}
