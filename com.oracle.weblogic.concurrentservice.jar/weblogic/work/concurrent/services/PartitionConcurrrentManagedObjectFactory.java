package weblogic.work.concurrent.services;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.concurrent.ContextService;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.enterprise.concurrent.ManagedThreadFactory;
import org.glassfish.hk2.api.Descriptor;
import org.glassfish.hk2.api.DynamicConfiguration;
import org.glassfish.hk2.api.DynamicConfigurationService;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.Filter;
import org.glassfish.hk2.api.PerLookup;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.binding.BindingBuilderFactory;
import org.glassfish.hk2.utilities.binding.ServiceBindingBuilder;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.server.GlobalServiceLocator;
import weblogic.work.concurrent.ConcurrencyLogger;
import weblogic.work.concurrent.partition.PartitionObjectAndRuntimeManager;
import weblogic.work.concurrent.runtime.RuntimeAccessUtils;
import weblogic.work.concurrent.utils.ConcurrentUtils;

public abstract class PartitionConcurrrentManagedObjectFactory {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConcurrent");
   protected final String name;
   private int refCount;
   static final PartitionObjectAndRuntimeManager partitionObjectAndRuntimeManager = PartitionObjectAndRuntimeManager.getInstance();
   private static final Map MESFactories = new HashMap();
   private static final Map MSESFactories = new HashMap();
   private static final Map MTFFactories = new HashMap();
   private static final Map CSFactories = new HashMap();
   private static final Map typeToFactoryMapMap = new HashMap();

   PartitionConcurrrentManagedObjectFactory(String name) {
      this.name = name;
      this.refCount = 1;
   }

   abstract Map getFactoryMap();

   abstract String getDefaultName();

   abstract Class getContractClass();

   private boolean isDefaultFactory() {
      return this.getDefaultName().equals(this.name);
   }

   public String toString() {
      return "PartitionConcurrrentManagedObjectFactory(type=" + this.getContractClass() + ", name=" + this.name + ", refCount=" + this.refCount + ")";
   }

   public static void createAndBindMTFFactory(String name) {
      synchronized(MTFFactories) {
         if (!increaseRefCount(MTFFactories, name)) {
            bind(new MTFFactory(name));
         }

      }
   }

   public static void createAndBindMESFactory(String name) {
      synchronized(MESFactories) {
         if (!increaseRefCount(MESFactories, name)) {
            bind(new MESFactory(name));
         }

      }
   }

   public static void createAndBindMSESFactory(String name) {
      synchronized(MSESFactories) {
         if (!increaseRefCount(MSESFactories, name)) {
            bind(new MSESFactory(name));
         }

      }
   }

   public static void createAndBindCSFactory(String name) {
      synchronized(CSFactories) {
         if (!increaseRefCount(CSFactories, name)) {
            bind(new ContextServiceFactory(name));
         }

      }
   }

   private static boolean increaseRefCount(Map map, String name) {
      PartitionConcurrrentManagedObjectFactory factory = (PartitionConcurrrentManagedObjectFactory)map.get(name);
      if (factory != null) {
         ++factory.refCount;
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("increased RefCount for " + factory);
         }

         return true;
      } else {
         return false;
      }
   }

   private static void bind(PartitionConcurrrentManagedObjectFactory factory) {
      ServiceBindingBuilder bindingBuilder = BindingBuilderFactory.newFactoryBinder((Factory)factory).to(factory.getContractClass());
      DynamicConfiguration config = getDynamicConfig();
      bindingBuilder.named(factory.name);
      bindingBuilder.ranked(factory.isDefaultFactory() ? Integer.MAX_VALUE : Integer.MIN_VALUE);
      BindingBuilderFactory.addBinding(bindingBuilder, config);
      config.commit();
      factory.getFactoryMap().put(factory.name, factory);
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("bound to hk2: " + factory);
      }

   }

   public static void unbindFactory(String className, String name) {
      Map map = (Map)typeToFactoryMapMap.get(className);
      synchronized(map) {
         PartitionConcurrrentManagedObjectFactory factory = (PartitionConcurrrentManagedObjectFactory)map.get(name);
         if (factory == null) {
            ConcurrencyLogger.logPartitionCMOFactoryUnbindError(className, name, new Exception());
         } else if (factory.refCount > 1) {
            --factory.refCount;
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("decreased RefCount for " + factory);
            }

         } else {
            unbind(factory);
         }
      }
   }

   private static void unbind(final PartitionConcurrrentManagedObjectFactory factory) {
      factory.getFactoryMap().remove(factory.name);
      DynamicConfiguration config = getDynamicConfig();
      config.addUnbindFilter(new Filter() {
         public boolean matches(Descriptor descriptor) {
            return descriptor.getImplementation().equals(factory.getClass().getName()) && ConcurrentUtils.isSameString(descriptor.getName(), factory.name);
         }
      });
      config.commit();
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("unbound from hk2: " + factory);
      }

   }

   private static DynamicConfiguration getDynamicConfig() {
      ServiceLocator locator = GlobalServiceLocator.getServiceLocator();
      DynamicConfigurationService dcs = (DynamicConfigurationService)locator.getService(DynamicConfigurationService.class, new Annotation[0]);
      return dcs.createDynamicConfiguration();
   }

   static {
      typeToFactoryMapMap.put(ManagedThreadFactory.class.getName(), MTFFactories);
      typeToFactoryMapMap.put(ManagedExecutorService.class.getName(), MESFactories);
      typeToFactoryMapMap.put(ManagedScheduledExecutorService.class.getName(), MSESFactories);
      typeToFactoryMapMap.put(ContextService.class.getName(), CSFactories);
   }

   static class ContextServiceFactory extends PartitionConcurrrentManagedObjectFactory implements Factory {
      public ContextServiceFactory(String name) {
         super(name);
      }

      @PerLookup
      public ContextService provide() {
         return partitionObjectAndRuntimeManager.getContextService(this.name, RuntimeAccessUtils.getCurrentPartitionName());
      }

      public void dispose(ContextService arg0) {
      }

      String getDefaultName() {
         return ConcurrentUtils.getDefaultCSInfo().getName();
      }

      Map getFactoryMap() {
         return PartitionConcurrrentManagedObjectFactory.CSFactories;
      }

      Class getContractClass() {
         return ContextService.class;
      }
   }

   static class MSESFactory extends PartitionConcurrrentManagedObjectFactory implements Factory {
      public MSESFactory(String name) {
         super(name);
      }

      @PerLookup
      public ManagedScheduledExecutorService provide() {
         return partitionObjectAndRuntimeManager.getManagedScheduledExecutorService(this.name, RuntimeAccessUtils.getCurrentPartitionName());
      }

      public void dispose(ManagedScheduledExecutorService arg0) {
      }

      String getDefaultName() {
         return ConcurrentUtils.getDefaultMSESInfo().getName();
      }

      Map getFactoryMap() {
         return PartitionConcurrrentManagedObjectFactory.MSESFactories;
      }

      Class getContractClass() {
         return ManagedScheduledExecutorService.class;
      }
   }

   static class MESFactory extends PartitionConcurrrentManagedObjectFactory implements Factory {
      public MESFactory(String name) {
         super(name);
      }

      @PerLookup
      public ManagedExecutorService provide() {
         return partitionObjectAndRuntimeManager.getManagedExecutorService(this.name, RuntimeAccessUtils.getCurrentPartitionName());
      }

      public void dispose(ManagedExecutorService arg0) {
      }

      String getDefaultName() {
         return ConcurrentUtils.getDefaultMESInfo().getName();
      }

      Map getFactoryMap() {
         return PartitionConcurrrentManagedObjectFactory.MESFactories;
      }

      Class getContractClass() {
         return ManagedExecutorService.class;
      }
   }

   static class MTFFactory extends PartitionConcurrrentManagedObjectFactory implements Factory {
      MTFFactory(String name) {
         super(name);
      }

      @PerLookup
      public ManagedThreadFactory provide() {
         return partitionObjectAndRuntimeManager.getManagedThreadFactory(this.name, RuntimeAccessUtils.getCurrentPartitionName());
      }

      public void dispose(ManagedThreadFactory arg0) {
      }

      String getDefaultName() {
         return ConcurrentUtils.getDefaultMTFInfo().getName();
      }

      Map getFactoryMap() {
         return PartitionConcurrrentManagedObjectFactory.MTFFactories;
      }

      Class getContractClass() {
         return ManagedThreadFactory.class;
      }
   }
}
