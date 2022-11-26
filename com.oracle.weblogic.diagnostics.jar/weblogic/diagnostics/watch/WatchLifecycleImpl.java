package weblogic.diagnostics.watch;

import java.lang.annotation.Annotation;
import java.security.AccessController;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.image.ImageManager;
import weblogic.diagnostics.lifecycle.DiagnosticComponentLifecycle;
import weblogic.diagnostics.lifecycle.DiagnosticComponentLifecycleException;
import weblogic.management.ManagementException;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.WLDFRuntimeMBean;
import weblogic.management.runtime.WLDFWatchNotificationSourceRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;

public class WatchLifecycleImpl implements DiagnosticComponentLifecycle {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticLifecycleHandlers");
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static WatchLifecycleImpl singleton = new WatchLifecycleImpl();
   private static final long WAIT_INCREMENT = 1000L;
   long maxImageCaptureWait = 30000L;

   public static final DiagnosticComponentLifecycle getInstance() {
      return singleton;
   }

   public int getStatus() {
      return 4;
   }

   public void initialize() throws DiagnosticComponentLifecycleException {
      RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
      this.maxImageCaptureWait = (long)((double)(runtimeAccess.getServer().getServerLifeCycleTimeoutVal() * 1000) * 0.75);
      WLDFRuntimeMBean parent = runtimeAccess.getServerRuntime().getWLDFRuntime();

      try {
         WatchManagerFactory wmFactory = WatchManagerFactory.getFactoryInstance("");
         WatchNotificationRuntimeMBeanImpl watchNotificationRuntime = new WatchNotificationRuntimeMBeanImpl(parent, wmFactory);
         parent.setWLDFWatchNotificationRuntime(watchNotificationRuntime);
         JMXNotificationProducer notificationProducer = new JMXNotificationProducer(watchNotificationRuntime);
         watchNotificationRuntime.setWatchJMXNotificationRuntime(notificationProducer);
         WLDFWatchNotificationSourceRuntimeMBean jmxNotificationSource = new JMXNotificationSource(watchNotificationRuntime);
         watchNotificationRuntime.setWatchJMXNotificationSource(jmxNotificationSource);
         wmFactory.setWatchNotificationRuntime(watchNotificationRuntime);
         ((ImageManager)GlobalServiceLocator.getServiceLocator().getService(ImageManager.class, new Annotation[0])).registerImageSource("WatchSource", new WatchSource(wmFactory, watchNotificationRuntime));
      } catch (ManagementException var7) {
         throw new DiagnosticComponentLifecycleException(var7);
      }
   }

   public void enable() throws DiagnosticComponentLifecycleException {
   }

   public void disable() throws DiagnosticComponentLifecycleException {
      this.waitForImageTasks();
   }

   private void waitForImageTasks() throws DiagnosticComponentLifecycleException {
      if (WatchManagerFactory.isImageNotificationActive()) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Waiting for any active DIMG notifications to complete");
         }

         try {
            Thread.sleep(1000L);
         } catch (InterruptedException var5) {
         }

         long totalWait = 0L;

         while(((ImageManager)GlobalServiceLocator.getServiceLocator().getService(ImageManager.class, new Annotation[0])).tasksInProgress((String)null) && totalWait < this.maxImageCaptureWait) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Image capture in progress, waiting...");
            }

            try {
               Thread.sleep(1000L);
               totalWait += 1000L;
            } catch (InterruptedException var4) {
            }
         }
      }

   }
}
