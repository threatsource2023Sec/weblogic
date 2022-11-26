package weblogic.application.internal.flow;

import java.lang.annotation.Annotation;
import java.net.URL;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.cluster.singleton.SingletonService;
import weblogic.cluster.singleton.SingletonServicesManager;
import weblogic.cluster.singleton.SingletonServicesManager.Util;
import weblogic.j2ee.J2EELogger;
import weblogic.j2ee.descriptor.wl.SingletonServiceBean;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBean;
import weblogic.logging.Loggable;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.classloaders.ClasspathClassFinder2;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.work.InheritableThreadContext;

public final class SingletonServicesFlow extends BaseFlow {
   private SingletonServicesManager manager = (SingletonServicesManager)GlobalServiceLocator.getServiceLocator().getService(SingletonServicesManager.class, new Annotation[0]);
   private SingletonServiceBean[] serviceBeans;
   private SingletonService[] services;
   private InheritableThreadContext[] context;

   public SingletonServicesFlow(ApplicationContextInternal appCtx) {
      super(appCtx);
   }

   public void prepare() throws DeploymentException {
      WeblogicApplicationBean bean = this.appCtx.getWLApplicationDD();
      if (bean != null) {
         this.serviceBeans = bean.getSingletonServices();
         if (this.serviceBeans != null && this.serviceBeans.length != 0) {
            this.services = new SingletonService[this.serviceBeans.length];
            this.context = new InheritableThreadContext[this.serviceBeans.length];

            for(int i = 0; i < this.serviceBeans.length; ++i) {
               String className = this.serviceBeans[i].getClassName();
               String jarUri = this.serviceBeans[i].getSingletonUri();
               GenericClassLoader gcl = this.appCtx.getAppClassLoader();
               if (jarUri != null) {
                  this.addListenerJarToLoader(gcl, jarUri);
               }

               this.services[i] = this.manager.constructSingletonService(className, gcl, this.appCtx.getInvocationContext());
               this.context[i] = InheritableThreadContext.getContext();
            }

         }
      }
   }

   public void activate() throws DeploymentException {
      if (this.serviceBeans != null) {
         String versionId = null;
         String partitionName = null;
         AppDeploymentMBean dmb = this.appCtx.getAppDeploymentMBean();
         if (dmb != null) {
            versionId = ApplicationVersionUtils.getVersionId(dmb.getApplicationIdentifier());
            partitionName = dmb.getPartitionName();
         }

         for(int i = 0; i < this.serviceBeans.length; ++i) {
            try {
               this.manager.addConfiguredService(Util.getAppscopedSingletonServiceName(this.serviceBeans[i].getName(), versionId, partitionName), this.services[i], this.context[i]);
            } catch (IllegalArgumentException var6) {
               throw new DeploymentException(var6);
            }
         }

      }
   }

   public void deactivate() throws DeploymentException {
      if (this.serviceBeans != null) {
         String versionId = null;
         String partitionName = null;
         AppDeploymentMBean dmb = this.appCtx.getAppDeploymentMBean();
         if (dmb != null) {
            versionId = ApplicationVersionUtils.getVersionId(dmb.getApplicationIdentifier());
            partitionName = dmb.getPartitionName();
         }

         for(int i = 0; i < this.serviceBeans.length; ++i) {
            this.manager.remove(Util.getAppscopedSingletonServiceName(this.serviceBeans[i].getName(), versionId, partitionName));
         }

      }
   }

   public void unprepare() throws DeploymentException {
   }

   private void addListenerJarToLoader(GenericClassLoader gcl, String jarUri) throws DeploymentException {
      URL u = gcl.getResource(this.appCtx.getApplicationId() + "#" + jarUri);
      if (u == null) {
         Loggable l = J2EELogger.logUnabletoFindSingletonJarLoggable(this.appCtx.getApplicationId(), jarUri);
         throw new DeploymentException(l.getMessage());
      } else {
         gcl.addClassFinder(new ClasspathClassFinder2(u.getFile()));
      }
   }
}
