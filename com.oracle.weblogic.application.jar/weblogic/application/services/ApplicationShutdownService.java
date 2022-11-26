package weblogic.application.services;

import java.lang.annotation.Annotation;
import java.security.AccessController;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.glassfish.hk2.extras.interception.Interceptor;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.ContractsProvided;
import org.jvnet.hk2.annotations.Service;
import weblogic.application.compiler.ToolsFactoryManager;
import weblogic.application.internal.ClassLoaders;
import weblogic.application.internal.OptionalPackageProviderImpl;
import weblogic.application.naming.NamingConstants;
import weblogic.application.utils.HaltListenerManager;
import weblogic.application.utils.LightWeightDeploymentViewFactory;
import weblogic.application.utils.ServerBasedToolsEnvironment;
import weblogic.diagnostics.image.ImageManager;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.j2ee.J2EELogger;
import weblogic.kernel.Kernel;
import weblogic.management.configuration.util.PartitionManagerInterceptorAdapter;
import weblogic.management.configuration.util.ServerServiceInterceptor;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.AbstractServerService;
import weblogic.server.GlobalServiceLocator;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;
import weblogic.utils.OptionalPackageProvider;
import weblogic.work.j2ee.J2EEWorkManager;

@Service
@Named
@RunLevel(10)
public final class ApplicationShutdownService extends AbstractServerService {
   @Inject
   @Named("DeploymentShutdownService")
   private ServerService dependencyOnDeploymentShutdownService;
   @Inject
   @Named("RemoteNamingService")
   private ServerService dependencyOnRemoteNamingService;
   private Object syncObj = new Object();
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public void start() throws ServiceFailureException {
      ToolsFactoryManager.addMergerFactory(new LightWeightDeploymentViewFactory());
      ToolsFactoryManager.setToolsEnvironment(new ServerBasedToolsEnvironment());
      OptionalPackageProvider.set(new OptionalPackageProviderImpl());
      ImageManager ims = (ImageManager)GlobalServiceLocator.getServiceLocator().getService(ImageManager.class, new Annotation[0]);
      if (ims != null) {
         ims.registerImageSource("APPLICATION", new ApplicationManagerImageSource());
      }

      addSharableJavaEEGlobalJNDINode();
      addJavaEEGlobalJNDINode();
   }

   private static void addSharableJavaEEGlobalJNDINode() throws ServiceFailureException {
      Context globalCtx = null;

      try {
         globalCtx = new InitialContext();
         globalCtx.addToEnvironment("weblogic.jndi.createIntermediateContexts", "true");
         globalCtx.addToEnvironment("weblogic.jndi.createUnderSharable", "true");
         globalCtx.createSubcontext("__WL_GlobalJavaApp");
         globalCtx.createSubcontext(NamingConstants.InternalGlobalNS);
      } catch (NamingException var9) {
         throw new ServiceFailureException("unable to create the JavaEE context", var9);
      } finally {
         try {
            globalCtx.close();
         } catch (Exception var8) {
            var8.printStackTrace();
         }

      }

   }

   private static void addJavaEEGlobalJNDINode() throws ServiceFailureException {
      Context globalCtx = null;

      try {
         globalCtx = new InitialContext();
         globalCtx.addToEnvironment("weblogic.jndi.createIntermediateContexts", "true");
         Context globalNSCtx = globalCtx.createSubcontext("java:global");
         ensureWMAvailabilityOnGlobalCtx(globalNSCtx);
      } catch (NamingException var9) {
         throw new ServiceFailureException("unable to create the java:global context", var9);
      } finally {
         try {
            globalCtx.close();
         } catch (Exception var8) {
            var8.printStackTrace();
         }

      }

   }

   private static void ensureWMAvailabilityOnGlobalCtx(Context javaGlobalCtx) throws NamingException {
      Context wm = javaGlobalCtx.createSubcontext("wm");
      wm.addToEnvironment("weblogic.jndi.replicateBindings", "false");
      wm.bind("default", J2EEWorkManager.getDefault());
   }

   public void stop() throws ServiceFailureException {
      try {
         ImageManager ims = (ImageManager)GlobalServiceLocator.getServiceLocator().getService(ImageManager.class, new Annotation[0]);
         if (ims != null) {
            ims.unregisterImageSource("APPLICATION");
         }
      } catch (Exception var4) {
         throw new ServiceFailureException(var4);
      }

      while(this.checkPendingWorkInQueues()) {
         try {
            synchronized(this.syncObj) {
               this.syncObj.wait(30000L);
            }
         } catch (InterruptedException var5) {
            break;
         }
      }

   }

   private boolean checkPendingWorkInQueues() {
      List dispatchList = Kernel.getApplicationDispatchPolicies();
      boolean workIsPending = false;
      if (dispatchList != null) {
         Iterator iter = dispatchList.iterator();

         while(iter.hasNext()) {
            String qName = (String)iter.next();
            int count = Kernel.getPendingTasksCount(qName);
            if (count > 0) {
               J2EELogger.logPendingWorkInQueues(qName, count);
               workIsPending = true;
            }
         }
      }

      return workIsPending;
   }

   public void halt() throws ServiceFailureException {
      HaltListenerManager.invokeListeners();
   }

   private void addPartitionJavaEEGlobalJNDINode() throws ServiceFailureException {
   }

   @Service
   @Interceptor
   @ContractsProvided({AppContainerPartitionManagerInterceptor.class, MethodInterceptor.class})
   @ServerServiceInterceptor(ApplicationShutdownService.class)
   public static class AppContainerPartitionManagerInterceptor extends PartitionManagerInterceptorAdapter {
      public void startPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
         this.createPartitionRelatedResource(partitionName);
         methodInvocation.proceed();
      }

      public void startPartitionInAdmin(MethodInvocation methodInvocation, String partitionName) throws Throwable {
         this.createPartitionRelatedResource(partitionName);
         methodInvocation.proceed();
      }

      private void createPartitionRelatedResource(String partitionName) throws ServiceFailureException {
         ComponentInvocationContextManager cicManager = ComponentInvocationContextManager.getInstance(ApplicationShutdownService.kernelId);
         ComponentInvocationContext cic = cicManager.createComponentInvocationContext(partitionName);
         ManagedInvocationContext mic = cicManager.setCurrentComponentInvocationContext(cic);
         Throwable var5 = null;

         try {
            ApplicationShutdownService.addJavaEEGlobalJNDINode();
         } catch (Throwable var14) {
            var5 = var14;
            throw var14;
         } finally {
            if (mic != null) {
               if (var5 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var13) {
                     var5.addSuppressed(var13);
                  }
               } else {
                  mic.close();
               }
            }

         }

      }

      public void shutdownPartition(MethodInvocation methodInvocation, String partitionName, int timeout, boolean ignoreSessions, boolean waitForAllSessions) throws Throwable {
         methodInvocation.proceed();
         this.destoryPartitionRelatedResource(partitionName);
      }

      public void forceShutdownPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
         methodInvocation.proceed();
         this.destoryPartitionRelatedResource(partitionName);
      }

      private void destoryPartitionRelatedResource(String partitionName) {
         if (ClassLoaders.instance.getPartitionClassLoader(partitionName) != null) {
            ClassLoaders.instance.destroyPartitionClassLoader(partitionName);
         }

      }
   }
}
