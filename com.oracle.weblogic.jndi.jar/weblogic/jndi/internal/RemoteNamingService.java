package weblogic.jndi.internal;

import java.lang.annotation.Annotation;
import java.rmi.RemoteException;
import java.util.Hashtable;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.NamingException;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.glassfish.hk2.extras.interception.Interceptor;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.ContractsProvided;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.image.ImageManager;
import weblogic.invocation.PartitionTable;
import weblogic.management.configuration.util.PartitionManagerInterceptorAdapter;
import weblogic.management.configuration.util.ServerServiceInterceptor;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.server.AbstractServerService;
import weblogic.server.GlobalServiceLocator;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(10)
public final class RemoteNamingService extends AbstractServerService {
   @Inject
   @Named("NamingService")
   private ServerService dependencyOnNamingService;
   @Inject
   @Named("SecurityService")
   private ServerService dependencyOnSecurityService;

   public void start() throws ServiceFailureException {
      try {
         bindStartupNames(RootNamingNode.getSingleton(), "DOMAIN", "0");
         ServerHelper.exportObject(RootNamingNode.getSingleton(), "");
         ServerHelper.exportObject(new RemoteContextFactoryImpl());
         ((ImageManager)GlobalServiceLocator.getServiceLocator().getService(ImageManager.class, new Annotation[0])).registerImageSource("JNDI_IMAGE_SOURCE", JNDIImageSource.getJNDIImageSource());
      } catch (NamingException | RemoteException var2) {
         throw new ServiceFailureException(var2);
      }
   }

   private static void bindStartupNames(ServerNamingNode partitionRootNode, String partitionName, String partitionId) throws RemoteException, NamingException {
      Hashtable env = new Hashtable();
      env.put("weblogic.jndi.createIntermediateContexts", "true");
      env.put("weblogic.jndi.replicateBindings", "false");
      env.put("weblogic.jndi.partitionInformation", partitionName);
      partitionRootNode.bind("weblogic.partitionName", partitionName, env);
      partitionRootNode.bind("weblogic.partitionId", partitionId, env);
      partitionRootNode.createSubcontext("weblogic.rmi", env, "");
   }

   @Service
   @Interceptor
   @ContractsProvided({RemoteJNDIPartitionManagerInterceptor.class, MethodInterceptor.class})
   @ServerServiceInterceptor(RemoteNamingService.class)
   public static class RemoteJNDIPartitionManagerInterceptor extends PartitionManagerInterceptorAdapter {
      public void startPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
         this.processBindings(partitionName);
         methodInvocation.proceed();
      }

      public void startPartitionInAdmin(MethodInvocation methodInvocation, String partitionName) throws Throwable {
         this.processBindings(partitionName);
         methodInvocation.proceed();
      }

      private void processBindings(String partitionName) throws RemoteException, NamingException {
         ServerNamingNode partitionRootNode = PartitionHandler.getPartitionRootNode(partitionName);
         String partitionId = PartitionTable.getInstance().lookupByName(partitionName).getPartitionID();
         RemoteNamingService.bindStartupNames(partitionRootNode, partitionName, partitionId);
      }
   }
}
