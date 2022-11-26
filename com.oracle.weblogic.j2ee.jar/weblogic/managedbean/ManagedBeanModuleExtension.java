package weblogic.managedbean;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.Module;
import weblogic.application.ModuleException;
import weblogic.application.ModuleExtension;
import weblogic.application.ModuleExtensionContext;
import weblogic.application.UpdateListener;
import weblogic.descriptor.DescriptorManager;
import weblogic.j2ee.descriptor.wl.ManagedBeanBean;
import weblogic.j2ee.descriptor.wl.ManagedBeansBean;
import weblogic.utils.ErrorCollectionException;

public class ManagedBeanModuleExtension extends ModuleExtension {
   private final Set beanClasses;
   private final Set interceptorClasses;
   private EnvironmentManager environmentManager;

   ManagedBeanModuleExtension(ModuleExtensionContext modCtx, ApplicationContextInternal appCtx, Module extensibleModule, Set beanClasses, Set interceptorClasses) {
      super(modCtx, appCtx, extensibleModule);
      this.beanClasses = beanClasses;
      this.interceptorClasses = interceptorClasses;
   }

   public void postPrepare(UpdateListener.Registration reg) throws ModuleException {
      ManagedBeansBean managedBeansBean = (ManagedBeansBean)(new DescriptorManager()).createDescriptorRoot(ManagedBeansBean.class).getRootBean();
      ErrorCollectionException errors = new ErrorCollectionException();
      Set duplicates = new HashSet();
      Iterator var5 = this.beanClasses.iterator();

      while(var5.hasNext()) {
         Class beanClass = (Class)var5.next();
         ManagedBeanUtils.validateManagedBeanClass(beanClass, errors);
         String beanName = ManagedBeanUtils.calculateManagedBeanName(beanClass);
         ManagedBeanUtils.validateManagedBeanName(beanName, duplicates, errors);
         ManagedBeanBean bean = managedBeansBean.createManagedBean();
         bean.setManagedBeanClass(beanClass.getName());
         bean.setManagedBeanName(beanName);
      }

      if (!errors.isEmpty()) {
         throw new ModuleException(errors);
      } else {
         ManagedBeanAnnotationProcessor processor = new ManagedBeanAnnotationProcessor(this.modCtx.getClassLoader());
         processor.beginRecording();

         try {
            processor.processAnnotations(this.interceptorClasses, this.beanClasses, managedBeansBean);
         } catch (ClassNotFoundException var9) {
            throw new ModuleException(var9);
         }

         this.modCtx.getRegistry().addAnnotationProcessedClasses(processor.endRecording());
         this.environmentManager = new EnvironmentManager(this.appCtx, this.modCtx.getName(), this.modCtx, managedBeansBean, this.extensibleModule.getType());
      }
   }

   public void postActivate() throws ModuleException {
      if (this.environmentManager != null) {
         this.environmentManager.activate();
      }

   }

   public void preDeactivate() throws ModuleException {
      if (this.environmentManager != null) {
         this.environmentManager.destroy();
      }

   }

   public void preUnprepare(UpdateListener.Registration reg) throws ModuleException {
      this.beanClasses.clear();
   }

   public void postRefreshClassLoader() throws ModuleException {
   }
}
