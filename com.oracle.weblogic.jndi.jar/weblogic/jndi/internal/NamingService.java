package weblogic.jndi.internal;

import java.util.Properties;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.NamingException;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.glassfish.hk2.extras.interception.Interceptor;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.ContractsProvided;
import org.jvnet.hk2.annotations.Service;
import weblogic.jndi.WLInitialContextFactory;
import weblogic.management.configuration.util.PartitionManagerInterceptorAdapter;
import weblogic.management.configuration.util.ServerServiceInterceptor;
import weblogic.management.provider.RuntimeAccess;
import weblogic.server.AbstractServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(10)
public final class NamingService extends AbstractServerService {
   @Inject
   private RuntimeAccess runtimeAccess;
   private static NamingService singleton;
   private String urlPkgPrefixes;
   private int state = 0;

   private static void setSingleton(NamingService oneOnly) {
      singleton = oneOnly;
   }

   public static NamingService getNamingService() {
      return singleton;
   }

   public NamingService() {
      setSingleton(this);
   }

   public void start() throws ServiceFailureException {
      Properties sysProps = System.getProperties();
      sysProps.put("java.naming.factory.initial", WLInitialContextFactory.class.getName());
      this.addURLFactoriesToSearchPath(sysProps);
      WLNamingManager.initialize();
      RootNamingNode.initialize();
      RemoteContextFactoryImpl.initialize();
      this.changeState(2);
   }

   private void addURLFactoriesToSearchPath(Properties sysProps) {
      String prefixes = (String)sysProps.get("java.naming.factory.url.pkgs");
      if (prefixes != null && prefixes.length() > 0) {
         this.urlPkgPrefixes = prefixes + ":weblogic.jndi.factories:weblogic.corba.j2ee.naming.url";
      } else {
         this.urlPkgPrefixes = "weblogic.jndi.factories:weblogic.corba.j2ee.naming.url";
      }

      sysProps.put("java.naming.factory.url.pkgs", this.urlPkgPrefixes);
   }

   public String getUrlPkgPrefixes() {
      return this.urlPkgPrefixes;
   }

   public void stop() throws ServiceFailureException {
      this.changeState(0);
   }

   public void halt() throws ServiceFailureException {
      this.changeState(0);
   }

   private synchronized void changeState(int newState) {
      this.state = newState;
      this.notifyAll();
   }

   boolean isRunning() {
      if (this.runtimeAccess == null) {
         return true;
      } else {
         return !this.runtimeAccess.getServerRuntime().isShuttingDown();
      }
   }

   @Service
   @Interceptor
   @ContractsProvided({JNDIPartitionManagerInterceptor.class, MethodInterceptor.class})
   @ServerServiceInterceptor(NamingService.class)
   public static class JNDIPartitionManagerInterceptor extends PartitionManagerInterceptorAdapter {
      public void startPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
         this.createPartitionJNDITree(partitionName);
         methodInvocation.proceed();
      }

      public void startPartitionInAdmin(MethodInvocation methodInvocation, String partitionName) throws Throwable {
         this.createPartitionJNDITree(partitionName);
         methodInvocation.proceed();
      }

      private void createPartitionJNDITree(String partitionName) throws NamingException {
         PartitionHandler.checkPartition(partitionName);
         PartitionHandler.addPartitionRootNode(partitionName);
      }

      public void shutdownPartition(MethodInvocation methodInvocation, String partitionName, int timeout, boolean ignoreSessions, boolean waitForAllSessions) throws Throwable {
         methodInvocation.proceed();
         this.destoryPartitionJNDITree(partitionName);
      }

      public void forceShutdownPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
         methodInvocation.proceed();
         this.destoryPartitionJNDITree(partitionName);
      }

      private void destoryPartitionJNDITree(String partitionName) {
         ServerNamingNode node = PartitionHandler.removePartitionRootNode(partitionName);
         if (node != null) {
            node.cascadeDestroySubNode();
         }

      }
   }
}
