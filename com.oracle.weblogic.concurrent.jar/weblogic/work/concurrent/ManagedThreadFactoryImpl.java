package weblogic.work.concurrent;

import java.util.HashMap;
import java.util.Map;
import javax.enterprise.concurrent.ManagedThreadFactory;
import javax.naming.Context;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.i18n.logging.Loggable;
import weblogic.work.concurrent.context.ApplicationContextProcessor;
import weblogic.work.concurrent.context.FixedContextProcessor;
import weblogic.work.concurrent.spi.ConcurrentManagedObjectBuilder;
import weblogic.work.concurrent.spi.ContextProvider;
import weblogic.work.concurrent.spi.DaemonThreadManager;
import weblogic.work.concurrent.spi.RejectException;
import weblogic.work.concurrent.spi.ServiceShutdownException;
import weblogic.work.concurrent.utils.ConcurrentUtils;
import weblogic.work.concurrent.utils.LogUtils;
import weblogic.work.concurrent.utils.ManagedTaskUtils;

public class ManagedThreadFactoryImpl extends AbstractConcurrentManagedObject implements ManagedThreadFactory {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConcurrentMTF");
   private int priority;
   private final DaemonThreadManager daemonThreadManager;
   private String compName;
   private Context jndiContext;
   private ClassLoader classLoader;
   private Map delegators;

   public ManagedThreadFactoryImpl(ConcurrentManagedObjectBuilder builder) {
      super(builder);
      this.delegators = new HashMap();
      this.priority = builder.getPriority();
      this.daemonThreadManager = builder.getDaemonThreadManager();
      this.compName = null;
      this.jndiContext = null;
      this.classLoader = null;
   }

   private ManagedThreadFactoryImpl(ManagedThreadFactoryImpl delegate) {
      super(delegate, delegate.getContextSetupProcessor());
      this.delegators = new HashMap();
      this.priority = delegate.priority;
      this.daemonThreadManager = delegate.daemonThreadManager;
      this.compName = delegate.compName;
      this.jndiContext = delegate.jndiContext;
      this.classLoader = delegate.classLoader;
   }

   public ManagedThreadFactoryImpl(ManagedThreadFactoryImpl delegate, ClassLoader classLoader, Context jndiContext) {
      this(delegate);
      this.moduleId = null;
      this.compName = null;
      this.classLoader = classLoader;
      this.jndiContext = jndiContext;
      this.setContextSetupProcessor(new ApplicationContextProcessor(this.appId, classLoader, jndiContext, 2));
   }

   public ManagedThreadFactoryImpl(ManagedThreadFactoryImpl delegate, String moduleId, String comp, ClassLoader classLoader, Context jndiContext) {
      this(delegate);
      this.moduleId = moduleId;
      this.compName = comp;
      this.jndiContext = jndiContext;
      this.classLoader = classLoader;
      this.setContextSetupProcessor(this.createFixCP());
   }

   private ContextProvider createFixCP() {
      return new FixedContextProcessor(this.appId, this.moduleId, this.compName, 2, this.classLoader, this.jndiContext);
   }

   private void warnUserObjectCheckSkipped(Runnable r) {
      if (this.warnIfUserObjectCheckSkipped) {
         LogUtils.warnSkipClassloaderCheck(this.name, ManagedTaskUtils.getTaskName(r));
         this.warnIfUserObjectCheckSkipped = false;
      }

   }

   public Thread newThread(Runnable r) {
      try {
         this.rejectIfOutOfScope();
      } catch (RejectException var5) {
         ConcurrencyLogger.logMTFRejectNewThread(this.getName(), var5.toString());
         return null;
      }

      try {
         this.rejectIfSubmittingCompNotStarted();
      } catch (RejectException var4) {
         ConcurrencyLogger.logMTFRejectNewThread(this.getName(), var4.toString());
         throw new IllegalStateException(var4);
      }

      if (this.cmoType == 1) {
         if (ManagedTaskUtils.isCheckUserObject((Object)r)) {
            try {
               ConcurrentUtils.serializeByClassloader(r, this.parCL);
            } catch (Exception var8) {
               ConcurrencyLogger.logMTFRejectNewThread(this.getName(), var8.toString());
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("newThread rejected", var8);
               }

               return null;
            }
         } else {
            this.warnUserObjectCheckSkipped(r);
         }
      }

      try {
         Thread t = this.daemonThreadManager.create(r, this.getContextSetupProcessor(), this.priority);
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug(this + " created thread " + t + "@" + Integer.toHexString(t.hashCode()) + " for " + r);
         }

         return t;
      } catch (ServiceShutdownException var6) {
         Loggable loggable = ConcurrencyLogger.logNewThreadStateErrorLoggable(this.name);
         throw new IllegalStateException(loggable.getMessage(), var6);
      } catch (RejectException var7) {
         ConcurrencyLogger.logMTFRejectNewThread(this.getName(), var7.toString());
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("newThread rejected", var7);
         }

         return null;
      }
   }

   public boolean start() {
      return this.daemonThreadManager.start();
   }

   public boolean stop() {
      return this.daemonThreadManager.stop();
   }

   public boolean isStarted() {
      return this.daemonThreadManager.isStarted();
   }

   public long getCompletedThreadsCount() {
      return this.daemonThreadManager.getCompletedThreads();
   }

   public int getRunningThreadsCount() {
      return this.daemonThreadManager.getRunningThreadCount();
   }

   public long getRejectedNewThreadRequests() {
      return this.daemonThreadManager.getRejectedThreads();
   }

   public String getJSR236Class() {
      return ManagedThreadFactory.class.getName();
   }

   AbstractConcurrentManagedObject.ConcurrentOpaqueReference createApplicationDelegator(ClassLoader classLoader, Context jndiContext) {
      return new AbstractConcurrentManagedObject.ConcurrentOpaqueReference(new ManagedThreadFactoryImpl(this, classLoader, jndiContext));
   }

   public Object getOrCreateJSR236Delegator(String module, String comp, ClassLoader classLoader, Context jndiContext) {
      String key = ConcurrentUtils.genKey(this.appId, module, comp);
      AbstractConcurrentManagedObject.ConcurrentOpaqueReference delegator = null;
      synchronized(this.delegators) {
         delegator = (AbstractConcurrentManagedObject.ConcurrentOpaqueReference)this.delegators.get(key);
         if (delegator == null) {
            delegator = new AbstractConcurrentManagedObject.ConcurrentOpaqueReference(new ManagedThreadFactoryImpl(this, module, comp, classLoader, jndiContext));
            this.delegators.put(key, delegator);
         }

         return delegator;
      }
   }

   public void updateContexts(String module, String comp, ClassLoader classLoader) {
      if (ConcurrentUtils.isSameString(module, this.moduleId) && ConcurrentUtils.isSameString(comp, this.compName)) {
         this.classLoader = classLoader;
         this.setContextSetupProcessor(this.createFixCP());
      } else {
         String key = ConcurrentUtils.genKey(this.appId, module, comp);
         AbstractConcurrentManagedObject.ConcurrentOpaqueReference delegator = null;
         synchronized(this.delegators) {
            delegator = (AbstractConcurrentManagedObject.ConcurrentOpaqueReference)this.delegators.get(key);
            if (delegator != null) {
               delegator.getObject().updateContexts(module, comp, classLoader);
            }
         }
      }
   }

   public void shutdownThreadsSubmittedBy(String applicationId) {
      if (this.cmoType == 0) {
         this.daemonThreadManager.shutdownThreadsSubmittedBy(applicationId);
      }

   }
}
