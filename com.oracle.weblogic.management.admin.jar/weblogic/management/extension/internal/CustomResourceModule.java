package weblogic.management.extension.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import weblogic.application.AdminModeCompletionBarrier;
import weblogic.application.ApplicationContext;
import weblogic.application.Module;
import weblogic.application.ModuleException;
import weblogic.application.UpdateListener;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.descriptor.DescriptorBean;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.extension.Resource;
import weblogic.management.runtime.ComponentRuntimeMBean;
import weblogic.utils.classloaders.GenericClassLoader;

public class CustomResourceModule implements Module, UpdateListener {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConfigurationRuntime");
   String id;
   DescriptorBean descriptor;
   String resourceClass;
   Resource resource;

   public CustomResourceModule(String id, DescriptorBean descriptor, String resourceClass) {
      this.id = id;
      this.descriptor = descriptor;
      this.resourceClass = resourceClass;
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Added custom resource with id " + id + " resource class " + resourceClass + " descriptor bean " + descriptor);
      }

   }

   public String getId() {
      return this.id;
   }

   public String getType() {
      return "Custom";
   }

   public ComponentRuntimeMBean[] getComponentRuntimeMBeans() {
      return new ComponentRuntimeMBean[0];
   }

   public DescriptorBean[] getDescriptors() {
      return new DescriptorBean[]{this.descriptor};
   }

   public GenericClassLoader init(ApplicationContext appCtx, GenericClassLoader parent, UpdateListener.Registration reg) throws ModuleException {
      this.initializeResource(parent);
      reg.addUpdateListener(this);
      return parent;
   }

   public void initUsingLoader(ApplicationContext appCtx, GenericClassLoader gcl, UpdateListener.Registration reg) throws ModuleException {
      this.initializeResource(gcl);
   }

   public void prepare() throws ModuleException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Custom Resource prepare() about to call resource prepare() " + this.resource);
      }

      this.resource.prepare();
      this.descriptor.addBeanUpdateListener(new BeanUpdateListener() {
         public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
            CustomResourceModule.this.resource.prepareUpdate(event);
         }

         public void activateUpdate(BeanUpdateEvent event) throws BeanUpdateFailedException {
            CustomResourceModule.this.resource.activateUpdate(event);
         }

         public void rollbackUpdate(BeanUpdateEvent event) {
            CustomResourceModule.this.resource.rollbackUpdate(event);
         }
      });
   }

   public void activate() throws ModuleException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Custom Resource activate() about to call resource activate() " + this.resource);
      }

      this.resource.activate();
   }

   public void start() throws ModuleException {
   }

   public void deactivate() throws ModuleException {
      this.resource.deactivate();
   }

   public void unprepare() throws ModuleException {
      this.resource.unprepare();
   }

   public void destroy(UpdateListener.Registration reg) throws ModuleException {
   }

   public void remove() throws ModuleException {
      this.resource.remove();
   }

   public void adminToProduction() {
   }

   public void gracefulProductionToAdmin(AdminModeCompletionBarrier barrier) throws ModuleException {
   }

   public void forceProductionToAdmin() throws ModuleException {
   }

   void initializeResource(ClassLoader cl) throws ModuleException {
      try {
         Class instanceClass = cl.loadClass(this.resourceClass);
         Constructor constructor = instanceClass.getConstructor((Class[])null);
         this.resource = (Resource)constructor.newInstance((Object[])null);
         this.resource.initialize(this.descriptor);
      } catch (ClassNotFoundException var4) {
         throw new ModuleException(var4);
      } catch (NoSuchMethodException var5) {
         throw new ModuleException(var5);
      } catch (IllegalAccessException var6) {
         throw new ModuleException(var6);
      } catch (InvocationTargetException var7) {
         throw new ModuleException(var7);
      } catch (InstantiationException var8) {
         throw new ModuleException(var8);
      }
   }

   public boolean acceptURI(String updateUri) {
      return true;
   }

   public void prepareUpdate(String uri) throws ModuleException {
   }

   public void activateUpdate(String uri) throws ModuleException {
   }

   public void rollbackUpdate(String uri) {
   }
}
