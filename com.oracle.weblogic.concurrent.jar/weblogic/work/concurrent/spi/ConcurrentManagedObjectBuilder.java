package weblogic.work.concurrent.spi;

import javax.enterprise.concurrent.ContextService;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.enterprise.concurrent.ManagedThreadFactory;
import weblogic.utils.classloaders.AugmentableClassLoaderManager;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.work.WorkManager;
import weblogic.work.concurrent.ContextServiceImpl;
import weblogic.work.concurrent.ManagedExecutorServiceImpl;
import weblogic.work.concurrent.ManagedScheduledExecutorServiceImpl;
import weblogic.work.concurrent.ManagedThreadFactoryImpl;
import weblogic.work.concurrent.context.ContextSetupProcessor;
import weblogic.work.concurrent.context.DelegateAppContextHelper;
import weblogic.work.concurrent.partition.PartitionContextProcessor;
import weblogic.work.concurrent.utils.ConcurrentUtils;

public class ConcurrentManagedObjectBuilder {
   public static final int DEFAULT_PRIORITY = 5;
   public static final int DEFAULT_MAX_THREAD = 10;
   private String name;
   private String partitionName;
   private String appId;
   private String moduleId;
   private ContextProvider contextProcessor;
   private int priority = 5;
   private WorkManager workManager;
   private DaemonThreadManager daemonThreadManager;
   private GenericClassLoader parCL;
   private int maxThreads = 10;

   public DaemonThreadManager getDaemonThreadManager() {
      return this.daemonThreadManager;
   }

   public ConcurrentManagedObjectBuilder daemonThreadManager(DaemonThreadManager daemonThreadManager) {
      this.daemonThreadManager = daemonThreadManager;
      return this;
   }

   public ConcurrentManagedObjectBuilder name(String name) {
      if (name != null && name.trim().length() != 0) {
         this.name = name.trim();
         return this;
      } else {
         throw new IllegalArgumentException("The name of concurrent managed objects can not be null or empty.");
      }
   }

   public ConcurrentManagedObjectBuilder partitionName(String partitionName) {
      this.partitionName = partitionName;
      return this;
   }

   public ConcurrentManagedObjectBuilder appId(String appId) {
      this.appId = appId;
      return this;
   }

   public ConcurrentManagedObjectBuilder moduleId(String moduleId) {
      this.moduleId = moduleId;
      return this;
   }

   public ConcurrentManagedObjectBuilder priority(int priority) {
      this.priority = priority;
      return this;
   }

   public ConcurrentManagedObjectBuilder maxThreads(int maxThreads) {
      this.maxThreads = maxThreads;
      return this;
   }

   public ConcurrentManagedObjectBuilder workManager(WorkManager workManager) {
      this.workManager = workManager;
      return this;
   }

   public ConcurrentManagedObjectBuilder partitionClassLoader(GenericClassLoader parCL) {
      this.parCL = parCL;
      return this;
   }

   public String getName() {
      return this.name;
   }

   public String getPartitionName() {
      return this.partitionName;
   }

   public String getAppId() {
      return this.appId;
   }

   public String getModuleId() {
      return this.moduleId;
   }

   public ContextProvider getContextProcessor() {
      return this.contextProcessor;
   }

   public int getPriority() {
      return this.priority;
   }

   public int getMaxThreads() {
      return null == this.daemonThreadManager ? this.maxThreads : this.daemonThreadManager.getLimit();
   }

   public WorkManager getWorkManager() {
      return this.workManager;
   }

   public GenericClassLoader getPartitionClassLoader() {
      return this.parCL;
   }

   public ConcurrentManagedObjectBuilder contextProcessor(ContextProvider contextProcessor) {
      this.contextProcessor = contextProcessor;
      return this;
   }

   private void initContexts(int concurrentType) {
      if (this.parCL == null) {
         if (ConcurrentUtils.isDomainPartitionName(this.partitionName)) {
            this.parCL = AugmentableClassLoaderManager.getAugmentableSystemClassLoader();
         } else {
            this.parCL = DelegateAppContextHelper.getOrCreatePartitionClassLoader(this.partitionName);
         }
      }

      if (this.contextProcessor == null && concurrentType != 1) {
         if (this.appId != null) {
            this.contextProcessor = new ContextSetupProcessor(this.appId, this.moduleId, concurrentType);
         } else {
            this.contextProcessor = new PartitionContextProcessor(this.partitionName, this.parCL, concurrentType);
         }
      }

      if (this.daemonThreadManager == null && concurrentType != 1) {
         this.daemonThreadManager = new DaemonThreadManagerImpl(this.maxThreads, this.name, this.partitionName);
      }

   }

   public ManagedThreadFactory buildManagedThreadFactory() {
      if (this.name == null) {
         throw new IllegalArgumentException("The name of ManagedThreadFactory can not be null");
      } else {
         this.initContexts(2);
         return new ManagedThreadFactoryImpl(this);
      }
   }

   public ContextService buildContextService() {
      if (this.name == null) {
         throw new IllegalArgumentException("The name of ContextService can not be null");
      } else {
         this.initContexts(1);
         return new ContextServiceImpl(this);
      }
   }

   public ManagedExecutorService buildManagedExecutorService() {
      if (this.workManager != null && this.name != null) {
         this.initContexts(4);
         return new ManagedExecutorServiceImpl(this);
      } else {
         throw new IllegalArgumentException("The workManager or name of ManagedExecutorService can not be null");
      }
   }

   public ManagedScheduledExecutorService buildManagedScheduledExecutorService() {
      if (this.workManager != null && this.name != null) {
         this.initContexts(8);
         return new ManagedScheduledExecutorServiceImpl(this);
      } else {
         throw new IllegalArgumentException("The workManager or name of ManagedScheduledExecutorService can not be null");
      }
   }
}
