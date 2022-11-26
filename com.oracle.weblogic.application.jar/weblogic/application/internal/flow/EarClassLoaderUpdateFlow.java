package weblogic.application.internal.flow;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.internal.ClassLoaders;
import weblogic.application.internal.classloading.ShareabilityException;
import weblogic.application.internal.classloading.ShareabilityManager;
import weblogic.application.utils.ClassLoaderUtils;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.j2ee.descriptor.wl.ShareableBean;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBean;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.ResourceGroupTemplateMBean;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.MultiClassFinder;

public class EarClassLoaderUpdateFlow extends BaseFlow {
   public EarClassLoaderUpdateFlow(ApplicationContextInternal appCtx) {
      super(appCtx);
   }

   public void prepare() throws DeploymentException {
      if (!this.isResourceGroupTemplateBasedDeployment()) {
         if (this.isDebugEnabled()) {
            this.debug("App deployment not from a resource group template, simply adding all library finders to instance class loader");
         }

         this.appCtx.getAppClassLoader().addClassFinder(this.appCtx.getAllAppFindersFromLibraries());
      } else {
         String appId = this.appCtx.getApplicationId();
         String sharedAppCLName = this.appCtx.getPartialApplicationId(false);
         WeblogicApplicationBean wlAppDD = this.appCtx.getWLApplicationDD();
         ShareableBean[] sBeans = null;
         if (wlAppDD != null) {
            sBeans = wlAppDD.getClassLoading().getShareables();
         }

         ShareabilityManager sManager;
         try {
            sManager = new ShareabilityManager(sBeans);
         } catch (ShareabilityException var12) {
            throw new DeploymentException(var12);
         }

         Object lock = sharedAppCLName.intern();
         synchronized(lock) {
            GenericClassLoader sharedAppCL = ClassLoaders.instance.getSharedAppClassLoader(sharedAppCLName, (String)null);
            if (sharedAppCL != null) {
               if (this.isDebugEnabled()) {
                  this.debug("Shared application class loader already exists, checking shareability");
               }

               if (ClassLoaders.instance.isShareable(sharedAppCLName, (String)null, this.appCtx)) {
                  if (this.isDebugEnabled()) {
                     this.debug("Shared application class loader is found to be shareable");
                  }

                  this.appCtx.markShareability();
                  sManager.extractShareableFinders((MultiClassFinder)this.appCtx.getAppClassLoader().getClassFinder(), new MultiClassFinder());
                  this.appCtx.getAppClassLoader().addClassFinder(this.appCtx.getInstanceAppClassFindersFromLibraries());
                  this.appCtx.getAppClassLoader().setAltParent(sharedAppCL);
                  ClassLoaders.instance.addReferenceToSharedAppClassLoader(sharedAppCLName, (String)null, appId);
               } else {
                  if (this.isDebugEnabled()) {
                     this.debug("Shared application class loader is not found to be shareable");
                  }

                  this.appCtx.getAppClassLoader().addClassFinder(this.appCtx.getAllAppFindersFromLibraries());
               }
            } else {
               if (this.isDebugEnabled()) {
                  this.debug("Shared application class loader not found, creating one");
               }

               this.appCtx.markShareability();
               sharedAppCL = ClassLoaders.instance.createSharedAppClassLoader(sharedAppCLName, (String)null, this.appCtx, (GenericClassLoader)null);
               this.appCtx.createdSharedAppClassLoader();
               MultiClassFinder sharedAppFindersFromApp = new MultiClassFinder();
               sManager.extractShareableFinders((MultiClassFinder)this.appCtx.getAppClassLoader().getClassFinder(), sharedAppFindersFromApp);
               if (this.isDebugEnabled()) {
                  this.debug("Number of finders found and added for the newly create shared application class loader: " + sharedAppFindersFromApp.size());
               }

               sharedAppCL.addClassFinder(sharedAppFindersFromApp);
               sharedAppCL.addClassFinder(this.appCtx.getSharedAppClassFindersFromLibraries());
               if (this.isDebugEnabled()) {
                  this.debug("Number of finders found and added from libraries for the newly create shared application class loader: " + this.appCtx.getSharedAppClassFindersFromLibraries().size());
               }

               this.appCtx.getAppClassLoader().addClassFinder(this.appCtx.getInstanceAppClassFindersFromLibraries());
               if (this.isDebugEnabled()) {
                  this.debug("Number of finders found and added from libraries for the application instance class loader: " + this.appCtx.getInstanceAppClassFindersFromLibraries().size());
               }

               ClassLoaders.instance.addReferenceToSharedAppClassLoader(sharedAppCLName, (String)null, appId);
               this.appCtx.getAppClassLoader().setAltParent(sharedAppCL);
               ClassLoaderUtils.initFilterPatterns(this.appCtx.getWLApplicationDD() != null ? this.appCtx.getWLApplicationDD().getPreferApplicationPackages() : null, this.appCtx.getWLApplicationDD() != null ? this.appCtx.getWLApplicationDD().getPreferApplicationResources() : null, sharedAppCL);
            }
         }
      }

      ClassLoaderUtils.initFilterPatterns(this.appCtx.getWLApplicationDD() != null ? this.appCtx.getWLApplicationDD().getPreferApplicationPackages() : null, this.appCtx.getWLApplicationDD() != null ? this.appCtx.getWLApplicationDD().getPreferApplicationResources() : null, this.appCtx.getAppClassLoader());
   }

   public void unprepare() throws DeploymentException {
      if (this.appCtx.checkShareability()) {
         String appIdSansPartitionName = this.appCtx.getPartialApplicationId(false);
         Object lock = appIdSansPartitionName.intern();
         synchronized(lock) {
            ClassLoaders.instance.removeReferenceOrDestroyOnLastReference(appIdSansPartitionName, (String)null, this.appCtx.getApplicationId());
         }
      }

   }

   private boolean isResourceGroupTemplateBasedDeployment() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.appCtx.getBasicDeploymentMBean();
      if (bean._isTransient()) {
         try {
            Method getterMethod = bean.getClass().getMethod("_getDelegateBean");

            try {
               AbstractDescriptorBean delegate = (AbstractDescriptorBean)getterMethod.invoke(bean);
               if (delegate != null) {
                  return delegate.getParentBean() instanceof ResourceGroupTemplateMBean;
               }
            } catch (IllegalAccessException var4) {
               if (this.isDebugEnabled()) {
                  this.debug("Basic deployment mbean's _getDelegateBean method, cannot be accessed properly, unable to check if it is from a template: " + bean);
               }
            } catch (InvocationTargetException var5) {
               if (this.isDebugEnabled()) {
                  this.debug("Basic deployment mbean's _getDelegateBean method could not be invoked properly, unable to check if it is from a template: " + bean);
               }
            }
         } catch (NoSuchMethodException var6) {
            if (this.isDebugEnabled()) {
               this.debug("Basic deployment mbean doesn't seem to have _getDelegateBean method, unable to check if it is from a template: " + bean);
            }
         }
      }

      return false;
   }
}
