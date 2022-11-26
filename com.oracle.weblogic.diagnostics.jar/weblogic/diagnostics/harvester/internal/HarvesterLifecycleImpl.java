package weblogic.diagnostics.harvester.internal;

import java.lang.annotation.Annotation;
import java.security.AccessController;
import weblogic.diagnostics.harvester.WLDFHarvester;
import weblogic.diagnostics.image.ImageManager;
import weblogic.diagnostics.lifecycle.DiagnosticComponentLifecycle;
import weblogic.diagnostics.lifecycle.DiagnosticComponentLifecycleException;
import weblogic.management.ManagementException;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.WLDFRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;

public class HarvesterLifecycleImpl implements DiagnosticComponentLifecycle {
   private static final AuthenticatedSubject KERNELID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   public static final String HARV_IMAGE_SOURCE_NAME = "HarvesterImageSource";
   private static final HarvesterLifecycleImpl SINGLETON = new HarvesterLifecycleImpl();
   private MetricArchiver metricArchiver;

   public static final DiagnosticComponentLifecycle getInstance() {
      return SINGLETON;
   }

   public int getStatus() {
      return this.metricArchiver.getStatus();
   }

   public void initialize() throws DiagnosticComponentLifecycleException {
      WLDFRuntimeMBean wldfRuntime = ManagementService.getRuntimeAccess(KERNELID).getServerRuntime().getWLDFRuntime();

      try {
         this.metricArchiver = MetricArchiver.getInstance();
         HarvesterRuntimeMBeanImpl harvesterRuntime = new HarvesterRuntimeMBeanImpl(this.metricArchiver, wldfRuntime);
         ImageManager imageManager = (ImageManager)GlobalServiceLocator.getServiceLocator().getService(ImageManager.class, new Annotation[0]);
         imageManager.registerImageSource("HarvesterImageSource", new HarvesterImageSource(harvesterRuntime));
      } catch (ManagementException var4) {
         throw new DiagnosticComponentLifecycleException(var4);
      }
   }

   public void enable() throws DiagnosticComponentLifecycleException {
   }

   public void disable() throws DiagnosticComponentLifecycleException {
   }

   public MetricArchiver getMetricArchiver() {
      return this.metricArchiver;
   }

   public WLDFHarvester getWLDFHarvester() {
      return this.metricArchiver.getHarvester();
   }

   static boolean isAdminServer() {
      return ManagementService.getRuntimeAccess(KERNELID).getServerRuntime().isAdminServer();
   }
}
