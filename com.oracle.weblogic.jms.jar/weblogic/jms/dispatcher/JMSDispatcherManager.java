package weblogic.jms.dispatcher;

import java.lang.reflect.Method;
import java.rmi.UnmarshalException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.jms.JMSException;
import javax.naming.Context;
import javax.naming.NamingException;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.jms.common.JMSDebug;
import weblogic.kernel.KernelStatus;
import weblogic.messaging.dispatcher.CrossPartitionDispatcher;
import weblogic.messaging.dispatcher.Dispatcher;
import weblogic.messaging.dispatcher.DispatcherId;
import weblogic.messaging.dispatcher.DispatcherUtils;
import weblogic.messaging.dispatcher.DispatcherWrapperState;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public class JMSDispatcherManager implements AccessDispatcherManager {
   private static final JMSDispatcherManager rawSingleton = new JMSDispatcherManager();
   private static final String FE_JMS_REQUEST_QUEUE = "FEJmsDispatcher";
   private static final String BE_JMS_REQUEST_QUEUE = "BEJmsDispatcher";
   private static final String JMS_ASYNC_REQUEST_QUEUE = "JmsAsyncQueue";
   private static final String MIN_POOL_PROP_PREFIX = "weblogic.jms.MinPoolSize.";
   public static final String PREFIX_SERVER_NAME = "weblogic.jms.S:";
   public static final String SERVER_PREFIX = "weblogic.messaging.dispatcher.S:";
   private static final Map PARTITION_ID_CONTEXT_MAP = new ConcurrentHashMap();
   private static final Map PARTITION_NAME_CONTEXT_MAP = new ConcurrentHashMap();

   public static JMSDispatcherManager getRawSingleton() {
      return rawSingleton;
   }

   public DispatcherPartitionContext findDispatcherPartitionContextJMSException() throws JMSException {
      return this.findDispatcherPartitionContextJMSException(DispatcherUtils.getPartitionId());
   }

   public DispatcherPartitionContext findDispatcherPartitionContextJMSException(String partitionId) throws JMSException {
      DispatcherPartitionContext dpc = this.lookupDispatcherPartitionContextById(partitionId);
      if (null == dpc) {
         throw new weblogic.jms.common.JMSException("JMS partition not available for Id " + partitionId);
      } else {
         return dpc;
      }
   }

   public DispatcherPartitionContext findDispatcherPartitionContextDispatcherException(String partitionId) throws weblogic.messaging.dispatcher.DispatcherException {
      DispatcherPartitionContext dpc = this.lookupDispatcherPartitionContextById(partitionId);
      if (null == dpc) {
         throw new weblogic.messaging.dispatcher.DispatcherException("no available JMS partition, Id " + partitionId);
      } else {
         return dpc;
      }
   }

   public JMSDispatcher findDispatcherByPartitionIdUnmarshalException(String partitionId) throws UnmarshalException {
      DispatcherPartitionContext dpc = this.lookupDispatcherPartitionContextById(partitionId);
      if (null == dpc) {
         throw new UnmarshalException("no JMS partition available, Id " + partitionId);
      } else {
         return ((ContextImpl)dpc).localDispatcherAdapter;
      }
   }

   public DispatcherPartitionContext lookupDispatcherPartitionContextById(String partitionId) {
      if (partitionId == null) {
         partitionId = "0";
      }

      return (DispatcherPartitionContext)PARTITION_ID_CONTEXT_MAP.get(partitionId);
   }

   public DispatcherPartitionContext lookupDispatcherPartitionContextByName(String partitionName) {
      if (partitionName == null) {
         partitionName = "DOMAIN";
      }

      return (DispatcherPartitionContext)PARTITION_NAME_CONTEXT_MAP.get(partitionName);
   }

   static int getJMSThreadPoolSize() {
      try {
         Class kernel = Class.forName("weblogic.kernel.Kernel");
         Method initialize = kernel.getMethod("ensureInitialized");
         initialize.invoke((Object)null);
         Method getConfig = kernel.getMethod("getConfig");
         Object configMBean = getConfig.invoke((Object)null);
         Class ConfigMBean = Class.forName("weblogic.management.configuration.KernelMBean");
         Method getSize = ConfigMBean.getMethod("getJMSThreadPoolSize");
         Object size = getSize.invoke(configMBean);
         return (Integer)size;
      } catch (RuntimeException var7) {
         if (JMSDebug.JMSDispatherUtilsVerbose.isDebugEnabled()) {
            JMSDebug.JMSDispatherUtilsVerbose.debug(var7.getMessage(), var7);
         }

         throw var7;
      } catch (Throwable var8) {
         RuntimeException rte = new RuntimeException(var8);
         if (JMSDebug.JMSDispatherUtilsVerbose.isDebugEnabled()) {
            JMSDebug.JMSDispatherUtilsVerbose.debug(rte.getMessage(), rte);
         }

         throw rte;
      }
   }

   public static String getDispatcherJNDIName(DispatcherId dispatcherId) {
      return "weblogic.messaging.dispatcher.S:" + dispatcherId;
   }

   private static DispatcherWrapper internalJNDILookup(Context ctx, DispatcherId dispatcherId) throws weblogic.messaging.dispatcher.DispatcherException {
      if (JMSDebug.JMSDispatcherLifecycle.isDebugEnabled()) {
         JMSDebug.JMSDispatcherLifecycle.debug("internalJNDILookup; JNDI lookup for dispatcher: id:" + dispatcherId.getDetail());
      }

      DispatcherWrapper dispatcherWrapper;
      try {
         dispatcherWrapper = (DispatcherWrapper)ctx.lookup("weblogic.messaging.dispatcher.S:" + dispatcherId);
      } catch (NamingException var5) {
         if (JMSDebug.JMSDispatcherLifecycle.isDebugEnabled()) {
            JMSDebug.JMSDispatcherLifecycle.debug("internalJNDILookup; dispatcher not found: id:" + dispatcherId.getDetail(), var5);
         }

         weblogic.messaging.dispatcher.DispatcherException de = new weblogic.messaging.dispatcher.DispatcherException("could not find Server " + dispatcherId);
         de.initCause(var5);
         throw de;
      }

      if (JMSDebug.JMSDispatcherLifecycle.isDebugEnabled()) {
         JMSDebug.JMSDispatcherLifecycle.debug("internalJNDILookup; adding: id:" + dispatcherId.getDetail());
      }

      return dispatcherWrapper;
   }

   public ContextImpl createDispatcherPartitionContext(String partitionId, String partitionName, boolean oldExecuteQueueStyle, InitDispatcherManager initDispatcherManager, InvocableManagerDelegate invocableManagerDelegate, ComponentInvocationContext componentInvocationContext) {
      synchronized(PARTITION_ID_CONTEXT_MAP) {
         if (PARTITION_NAME_CONTEXT_MAP.containsKey(partitionName)) {
            RuntimeException rte = new RuntimeException("Already created JMS Dispatcher for partition " + partitionName);
            if (JMSDebug.JMSDispatherUtilsVerbose.isDebugEnabled()) {
               JMSDebug.JMSDispatherUtilsVerbose.debug(rte.getMessage(), rte);
            }

            throw rte;
         } else {
            ContextImpl contextImpl = new ContextImpl(partitionId, partitionName, oldExecuteQueueStyle, initDispatcherManager, invocableManagerDelegate, componentInvocationContext);
            PARTITION_ID_CONTEXT_MAP.put(partitionId, contextImpl);
            PARTITION_NAME_CONTEXT_MAP.put(partitionName, contextImpl);
            contextImpl.isPartitionActive = true;
            return contextImpl;
         }
      }
   }

   private static int getMinPoolSize(String poolName, int suggestedMin) {
      String prop = "weblogic.jms.MinPoolSize." + poolName;
      String propVal = null;
      String exc = null;

      int ret;
      try {
         if (System.getProperty(prop) == null) {
            prop = "weblogic.jms.MinPoolSize.Any";
         }

         propVal = System.getProperty(prop, "" + suggestedMin);
         ret = Math.max(Integer.parseInt(propVal), suggestedMin);
      } catch (Throwable var7) {
         ret = suggestedMin;
         exc = var7 + "";
      }

      if (JMSDebug.JMSDispatherUtilsVerbose.isDebugEnabled()) {
         JMSDebug.JMSDispatherUtilsVerbose.debug("Parsed thread poolprop:   poolName=" + poolName + " prop=" + prop + " propval=" + propVal + " suggestedMin=" + suggestedMin + " exc='" + exc + "' ret=" + ret);
      }

      return ret;
   }

   protected class ContextImpl implements DispatcherPartitionContext {
      private final HashMap dispatchers;
      private final DispatcherAdapter localDispatcherAdapter;
      private final Dispatcher localDispatcher;
      private final WorkManager feDispatcherWorkManager;
      private final WorkManager beDispatcherWorkManager;
      private final WorkManager oneWayWorkManager;
      private final WorkManager resumeRequestWorkManager;
      private TimerManager defaultTimerManager;
      protected boolean oldExecuteQueueStyle;
      private int threadPoolSize;
      private int configuredThreadPoolSizeForClient;
      private final InvocableManagerDelegate invocableManagerDelegate;
      private int exportCount;
      private final ComponentInvocationContext componentInvocationContext;
      private final String partitionId;
      private final String partitionName;
      private volatile boolean isPartitionActive;
      private final InitDispatcherManager initDispatcherManager;

      private ContextImpl(String partitionId, String partitionName, boolean oldExecuteQueueStyle, InitDispatcherManager initDispatcherManager, InvocableManagerDelegate invocableManagerDelegate, ComponentInvocationContext componentInvocationContext) {
         this.dispatchers = new HashMap();
         this.oldExecuteQueueStyle = false;
         this.threadPoolSize = 5;
         this.configuredThreadPoolSizeForClient = 0;
         DispatcherId localDispatcherId = initDispatcherManager.getLocalDispatcherId();
         if (JMSDebug.JMSDispatcherLifecycle.isDebugEnabled()) {
            JMSDebug.JMSDispatcherLifecycle.debug("JMSDispatcherManager.ContextImpl.initialize; partitionId: " + partitionId + " initializing dispatcher; dispatcherName: " + localDispatcherId.getDetail());
         }

         this.partitionId = partitionId;
         this.partitionName = partitionName;
         this.initDispatcherManager = initDispatcherManager;
         this.componentInvocationContext = componentInvocationContext;
         this.invocableManagerDelegate = invocableManagerDelegate;
         this.threadPoolSize = initDispatcherManager.abstractThreadPoolSize(oldExecuteQueueStyle);
         if (!initDispatcherManager.isServer()) {
            this.configuredThreadPoolSizeForClient = this.threadPoolSize;
         }

         this.feDispatcherWorkManager = WorkManagerFactory.getInstance().findOrCreate("FEJmsDispatcher", oldExecuteQueueStyle ? -1 : 100, JMSDispatcherManager.getMinPoolSize("FEJmsDispatcher", Math.max(3, this.threadPoolSize)), -1);
         if (initDispatcherManager.isServer()) {
            this.beDispatcherWorkManager = WorkManagerFactory.getInstance().findOrCreate("BEJmsDispatcher", 100, JMSDispatcherManager.getMinPoolSize("BEJmsDispatcher", this.threadPoolSize), -1);
         } else {
            this.beDispatcherWorkManager = this.feDispatcherWorkManager;
         }

         this.oneWayWorkManager = WorkManagerFactory.getInstance().findOrCreate("JmsAsyncQueue", 100, JMSDispatcherManager.getMinPoolSize("JmsAsyncQueue", 1), -1);
         this.resumeRequestWorkManager = WorkManagerFactory.getInstance().getSystem();
         if (JMSDebug.JMSDispatcherLifecycle.isDebugEnabled()) {
            JMSDebug.JMSDispatcherLifecycle.debug("JMSDispatcherManager.ContextImpl.createLocalDispatcher: partitionId: " + partitionId + " add local DispatcherImpl to cache; dispatcherId: " + localDispatcherId.getDetail());
         }

         this.localDispatcher = new weblogic.messaging.dispatcher.DispatcherImpl(localDispatcherId.getName(), localDispatcherId, this, initDispatcherManager.getObjectHandlerClassName(), partitionId, this.getPartitionName());
         this.dispatchers.put(localDispatcherId, this.localDispatcher);
         this.localDispatcherAdapter = new DispatcherAdapter(this.localDispatcher, this);
      }

      public String toString() {
         DispatcherAdapter myLocalDispatcherAdapter = this.localDispatcherAdapter;
         Dispatcher myDispatcher = null;
         return myLocalDispatcherAdapter != null && (myDispatcher = myLocalDispatcherAdapter.getDelegate()) != null ? "ID delegate=" + myDispatcher.getId() + " " + this.getClass().getSimpleName() + " " + myDispatcher : "unintialized " + this.getClass().getSimpleName();
      }

      public String getPartitionName() {
         return this.partitionName;
      }

      public String getPartitionId() {
         return this.partitionId;
      }

      public boolean isLocal(weblogic.messaging.dispatcher.DispatcherWrapper wrapper) {
         DispatcherId wrapperId = wrapper.getId();
         DispatcherId localDispatcherId = this.initDispatcherManager.getLocalDispatcherId();
         boolean isNameMatch = localDispatcherId.getName().equals(wrapperId.getName());
         boolean isIdMatch = localDispatcherId.equals(wrapperId);
         if (!isNameMatch && !isIdMatch) {
            if (JMSDebug.JMSDispatherUtilsVerbose.isDebugEnabled()) {
               JMSDebug.JMSDispatherUtilsVerbose.debug("debug JMSDispatcherManager.isLocal FALSE isNameMatch" + isNameMatch + " isIdMatch:" + isIdMatch + " (localID:" + localDispatcherId + " localName:" + localDispatcherId.getName() + " ) (wrapper ID:" + wrapper.getId() + " wrapperName:" + wrapper.getName() + " )");
            }

            return false;
         } else if (this.initDispatcherManager.isServer()) {
            return this.initDispatcherManager.isServerLocalRJVM(wrapper.getRemoteDispatcher());
         } else {
            if (JMSDebug.JMSDispatherUtilsVerbose.isDebugEnabled()) {
               JMSDebug.JMSDispatherUtilsVerbose.debug("debug JMSDispatcherManager.isLocal client TRUE isNameMatch" + isNameMatch + " isIdMatch:" + isIdMatch + " (localID:" + localDispatcherId + " localName:" + localDispatcherId.getName() + " ) (wrapper ID:" + wrapper.getId() + " wrapperName:" + wrapper.getName() + " )");
            }

            return true;
         }
      }

      public void removeJMSDispatcherManager() {
         if (this.partitionId != null && !"0".equals(this.partitionId)) {
            if (JMSDebug.JMSDispatcherLifecycle.isDebugEnabled()) {
               JMSDebug.JMSDispatcherLifecycle.debug("JMSDispatcherManager.ContextImpl.removeJMSDispatcherManager for partitionId=" + this.partitionId);
            }

            synchronized(JMSDispatcherManager.PARTITION_ID_CONTEXT_MAP) {
               ContextImpl dpc = (ContextImpl)JMSDispatcherManager.PARTITION_ID_CONTEXT_MAP.remove(this.partitionId);
               if (this != dpc && dpc != null) {
                  JMSDispatcherManager.PARTITION_ID_CONTEXT_MAP.put(this.partitionId, dpc);
               } else {
                  JMSDispatcherManager.PARTITION_NAME_CONTEXT_MAP.remove(this.partitionName);
               }

               this.isPartitionActive = false;
            }
         }
      }

      public ComponentInvocationContext getCIC(String partitionName) {
         DispatcherPartitionContext dpc = JMSDispatcherManager.this.lookupDispatcherPartitionContextByName(partitionName);
         return dpc instanceof ContextImpl ? ((ContextImpl)dpc).componentInvocationContext : null;
      }

      public TimerManager getDefaultTimerManager() {
         if (null != this.defaultTimerManager) {
            return this.defaultTimerManager;
         } else {
            ManagedInvocationContext mic = (ManagedInvocationContext)this.pushComponentInvocationContext();
            Throwable var2 = null;

            TimerManager var3;
            try {
               var3 = this.defaultTimerManager = TimerManagerFactory.getTimerManagerFactory().getDefaultTimerManager();
            } catch (Throwable var12) {
               var2 = var12;
               throw var12;
            } finally {
               if (mic != null) {
                  if (var2 != null) {
                     try {
                        mic.close();
                     } catch (Throwable var11) {
                        var2.addSuppressed(var11);
                     }
                  } else {
                     mic.close();
                  }
               }

            }

            return var3;
         }
      }

      public boolean isPartitionActive() {
         return this.isPartitionActive;
      }

      public int getConfiguredThreadPoolSizeForClient() {
         return this.configuredThreadPoolSizeForClient;
      }

      public WorkManager getFEWorkManager() {
         return this.feDispatcherWorkManager;
      }

      public WorkManager getBEWorkManager() {
         return this.beDispatcherWorkManager;
      }

      public WorkManager getOneWayWorkManager() {
         return this.oneWayWorkManager;
      }

      public WorkManager getDefaultWorkManager() {
         return this.resumeRequestWorkManager;
      }

      public InvocableManagerDelegate getInvocableManagerDelegate() throws JMSException {
         InvocableManagerDelegate.checkValid(this.getPartitionId(), this.invocableManagerDelegate);
         return this.invocableManagerDelegate;
      }

      public JMSDispatcher getLocalDispatcher() throws JMSException {
         if (this.isPartitionActive()) {
            return this.localDispatcherAdapter;
         } else {
            throw new weblogic.jms.common.JMSException("inactive JMS partition, Name " + this.partitionName);
         }
      }

      public void removeDispatcherReference(JMSDispatcher dispatcher) {
         Dispatcher toRemove = dispatcher.getDelegate();
         this.removeDispatcherReference(toRemove, false);
      }

      public void removeDispatcherWrapperStateReference() {
         Iterator i = this.dispatchers.values().iterator();

         while(i.hasNext()) {
            Object o = i.next();
            if (o instanceof DispatcherWrapperState) {
               ((DispatcherWrapperState)o).deleteNotify();
            }
         }

      }

      public void removeDispatcherReference(Dispatcher dispatcher, boolean force) {
         if (JMSDebug.JMSDispatcherLifecycle.isDebugEnabled()) {
            JMSDebug.JMSDispatcherLifecycle.debug("JMSDispatcherManager.ContextImpl.removeDispatcherReference; dispatcher.class: " + dispatcher.getClass().getName() + " dispatcherId: " + dispatcher.getId().getDetail());
         }

         if (this.initDispatcherManager.getLocalDispatcherId().equals(dispatcher.getId())) {
            if (JMSDebug.JMSDispatcherLifecycle.isDebugEnabled()) {
               JMSDebug.JMSDispatcherLifecycle.debug("JMSDispatcherManager.ContextImpl.removeDispatcherReference; partitionId: " + this.partitionId + " ignored; local dispatcher; dispatcherId: " + dispatcher.getId().getDetail());
            }

         } else {
            if (dispatcher instanceof DispatcherWrapperState) {
               DispatcherWrapperState dws = (DispatcherWrapperState)dispatcher;
               if (dws.removeRefCount() && !force) {
                  if (JMSDebug.JMSDispatcherLifecycle.isDebugEnabled()) {
                     JMSDebug.JMSDispatcherLifecycle.debug("JMSDispatcherManager.ContextImpl.removeDispatcherReference; partitionId: " + this.partitionId + " ignoring; dispatcherId: " + dispatcher.getId().getDetail() + " refCount: " + dws.getRefCount() + " force: " + force);
                  }

                  return;
               }

               if (JMSDebug.JMSDispatcherLifecycle.isDebugEnabled()) {
                  JMSDebug.JMSDispatcherLifecycle.debug("JMSDispatcherManager.ContextImpl.removeDispatcherReference; partitionId: " + this.partitionId + " deleteNotify; dispatcherId: " + dispatcher.getId().getDetail());
               }

               HashMap cachedMap = dws.deleteNotify();
               if (cachedMap == this.dispatchers) {
                  synchronized(this.dispatchers) {
                     if (JMSDebug.JMSDispatcherLifecycle.isDebugEnabled()) {
                        JMSDebug.JMSDispatcherLifecycle.debug("JMSDispatcherManager.ContextImpl.eDispatcherReference; partitionId: " + this.partitionId + " removing; dispatcherId: " + dispatcher.getId().getDetail());
                     }

                     Object removedDispatcher = this.dispatchers.remove(dispatcher.getId());
                     if (JMSDebug.JMSDispatcherLifecycle.isDebugEnabled()) {
                        if (removedDispatcher != null) {
                           JMSDebug.JMSDispatcherLifecycle.debug("JMSDispatcherManager.ContextImpl.removeDispatcherReference; partitionId: " + this.partitionId + " found and removed; dispatcherId: " + dispatcher.getId().getDetail());
                        } else {
                           JMSDebug.JMSDispatcherLifecycle.debug("JMSDispatcherManager.ContextImpl.removeDispatcherReference; partitionId: " + this.partitionId + " not found; dispatcherId: " + dispatcher.getId().getDetail());
                        }
                     }
                  }
               }
            } else if (JMSDebug.JMSDispatcherLifecycle.isDebugEnabled()) {
               JMSDebug.JMSDispatcherLifecycle.debug("JMSDispatcherManager.ContextImpl.removeDispatcherReference; partitionId: " + this.partitionId + " ignored; not a DispatcherWrapperState; dispatcherId: " + dispatcher.getId().getDetail());
            }

         }
      }

      private Dispatcher dispatcherFind(DispatcherId dispatcherId, Map dispatchers) throws weblogic.messaging.dispatcher.DispatcherException {
         if (JMSDebug.JMSDispatcherLifecycle.isDebugEnabled()) {
            JMSDebug.JMSDispatcherLifecycle.debug("JMSDispatcherManager.ContextImpl.dispatcherFind; partitionId: " + this.partitionId + " looking up dispatcher; dispatcherId: " + (dispatcherId == null ? "<null>" : dispatcherId.getDetail()));
         }

         Dispatcher retDispatcher;
         synchronized(dispatchers) {
            if (dispatcherId == null) {
               throw new weblogic.messaging.dispatcher.DispatcherException("Dispatcher not found: " + dispatcherId);
            }

            DispatcherId localDispatcherId = this.initDispatcherManager.getLocalDispatcherId();
            if (dispatcherId.getId() != null && localDispatcherId.getName().equals(dispatcherId.getName()) || localDispatcherId.equals(dispatcherId)) {
               if (JMSDebug.JMSDispatcherLifecycle.isDebugEnabled()) {
                  JMSDebug.JMSDispatcherLifecycle.debug("JMSDispatcherManager.ContextImpl.dispatcherFind; partitionId: " + this.partitionId + " returning local dispatcher; dispatcherId: " + this.localDispatcher.getId().getDetail());
               }

               return this.localDispatcher;
            }

            retDispatcher = (Dispatcher)dispatchers.get(dispatcherId);
         }

         if (retDispatcher == null) {
            weblogic.messaging.dispatcher.DispatcherException de = new weblogic.messaging.dispatcher.DispatcherException("Dispatcher not found: " + dispatcherId);
            if (JMSDebug.JMSDispatcherLifecycle.isDebugEnabled()) {
               JMSDebug.JMSDispatcherLifecycle.debug("JMSDispatcherManager.ContextImpl.dispatcherFind; partitionId: " + this.partitionId + " dispatcher not found for id: " + dispatcherId.getDetail(), de);
            }

            throw de;
         } else {
            if (JMSDebug.JMSDispatcherLifecycle.isDebugEnabled()) {
               JMSDebug.JMSDispatcherLifecycle.debug("JMSDispatcherManager.ContextImpl.dispatcherFind; partitionId: " + this.partitionId + " returning dispatcher: id:" + retDispatcher.getId().getDetail());
            }

            return retDispatcher;
         }
      }

      public JMSDispatcher dispatcherFindOrCreate(Context ctx, DispatcherId dispatcherId) throws weblogic.messaging.dispatcher.DispatcherException {
         Dispatcher d;
         try {
            d = this.dispatcherFind(dispatcherId, this.dispatchers);
         } catch (weblogic.messaging.dispatcher.DispatcherException var5) {
            d = this.dispatcherCreate(ctx, dispatcherId, true, this.dispatchers);
         }

         return new DispatcherAdapter(d, this);
      }

      public JMSDispatcher registerDispatcher(weblogic.messaging.dispatcher.DispatcherWrapper dispatcherWrapper) throws weblogic.messaging.dispatcher.DispatcherException {
         if (this.isLocal(dispatcherWrapper)) {
            ContextImpl desiredDPC = (ContextImpl)JMSDispatcherManager.this.findDispatcherPartitionContextDispatcherException(dispatcherWrapper.getPartitionId());
            return new CrossPartitionDispatcher((weblogic.messaging.dispatcher.DispatcherImpl)desiredDPC.localDispatcher);
         } else {
            Dispatcher d = this.localDispatcherOrDispWrapperState(dispatcherWrapper, true, this.dispatchers);
            return new DispatcherAdapter(d, this);
         }
      }

      private Dispatcher dispatcherCreate(Context ctx, DispatcherId dispatcherId, boolean cacheIt, HashMap dispatchers) throws weblogic.messaging.dispatcher.DispatcherException {
         DispatcherWrapper dispatcherWrapper = JMSDispatcherManager.internalJNDILookup(ctx, dispatcherId);
         if (JMSDebug.JMSDispatcherLifecycle.isDebugEnabled()) {
            JMSDebug.JMSDispatcherLifecycle.debug("JMSDispatcherManager.ContextImpl.dispatcherCreate; partitionId: " + this.partitionId + " adding: id:" + dispatcherId.getDetail());
         }

         return this.localDispatcherOrDispWrapperState(dispatcherWrapper, cacheIt, dispatchers);
      }

      public DispatcherWrapper getLocalDispatcherWrapper() throws JMSException {
         weblogic.messaging.dispatcher.DispatcherImpl dispatcher = (weblogic.messaging.dispatcher.DispatcherImpl)this.getLocalDispatcher().getDelegate();
         return dispatcher.constructPartitionAwareDispatcherWrapper();
      }

      public void exportLocalDispatcher() throws JMSException {
         synchronized(this) {
            Dispatcher d = this.getLocalDispatcher().getDelegate();
            ++this.exportCount;
            if (this.exportCount == 1 && d instanceof weblogic.messaging.dispatcher.DispatcherImpl) {
               ((weblogic.messaging.dispatcher.DispatcherImpl)d).export();
            }

         }
      }

      public synchronized void unexportLocalDispatcher() throws JMSException {
         synchronized(this) {
            Object d = this.getLocalDispatcher().getDelegate();
            --this.exportCount;
            if (this.exportCount == 0) {
               if (KernelStatus.isServer()) {
                  return;
               }

               if (d instanceof weblogic.messaging.dispatcher.DispatcherImpl) {
                  ((weblogic.messaging.dispatcher.DispatcherImpl)d).unexport();
               }
            }

         }
      }

      private Dispatcher localDispatcherOrDispWrapperState(weblogic.messaging.dispatcher.DispatcherWrapper dispatcherWrapper, boolean cacheIt, HashMap dispatchers) throws weblogic.messaging.dispatcher.DispatcherException {
         if (this.isLocal(dispatcherWrapper)) {
            if (JMSDebug.JMSDispatcherLifecycle.isDebugEnabled()) {
               JMSDebug.JMSDispatcherLifecycle.debug("JMSDispatcherManager.ContextImpl.localDispatcherOrDispWrapperState: partitionId: " + this.partitionId + " returning local dispatcher; id: " + this.localDispatcher.getId().getDetail());
            }

            return this.localDispatcher;
         } else {
            DispatcherWrapperState dws;
            if (cacheIt) {
               synchronized(dispatchers) {
                  DispatcherWrapperState existing = (DispatcherWrapperState)dispatchers.get(dispatcherWrapper.getId());
                  if (existing != null) {
                     existing.addRefCount();
                     dws = existing;
                  } else if (this.partitionName.equals(dispatcherWrapper.getConnectionPartitionName())) {
                     dws = new DispatcherWrapperState(dispatcherWrapper, this, dispatchers);
                     dispatchers.put(dws.getId(), dws);
                     if (JMSDebug.JMSDispatcherLifecycle.isDebugEnabled()) {
                        JMSDebug.JMSDispatcherLifecycle.debug("JMSDispatcherManager.ContextImpl.localDispatcherOrDispWrapperState: partitionId: " + this.partitionId + " add DispatcherWrapperState to cache; id: " + dws.getId().getDetail());
                     }
                  } else {
                     dws = new DispatcherWrapperState(dispatcherWrapper, this, (HashMap)null);
                     if (JMSDebug.JMSDispatcherLifecycle.isDebugEnabled()) {
                        JMSDebug.JMSDispatcherLifecycle.debug("JMSDispatcherManager.ContextImpl.localDispatcherOrDispWrapperState: partitionId: " + this.partitionId + " so do not add DispatcherWrapperState to cache; id: " + dws.getId().getDetail());
                     }
                  }
               }
            } else {
               dws = new DispatcherWrapperState(dispatcherWrapper, this, (HashMap)null);
               if (JMSDebug.JMSDispatcherLifecycle.isDebugEnabled()) {
                  JMSDebug.JMSDispatcherLifecycle.debug("JMSDispatcherManager.ContextImpl.localDispatcherOrDispWrapperState: partitionId: " + this.partitionId + " not adding DispatcherWrapperState to cache; id: " + dws.getId().getDetail());
               }
            }

            return dws;
         }
      }

      public JMSDispatcher dispatcherAdapterOrPartitionAdapter(DispatcherWrapper dispatcherWrapper) throws weblogic.messaging.dispatcher.DispatcherException {
         if (this.isLocal(dispatcherWrapper)) {
            ContextImpl desiredDPC = (ContextImpl)JMSDispatcherManager.this.findDispatcherPartitionContextDispatcherException(dispatcherWrapper.getPartitionId());
            return new CrossPartitionDispatcher((weblogic.messaging.dispatcher.DispatcherImpl)desiredDPC.localDispatcher);
         } else {
            return new DispatcherAdapter(new DispatcherWrapperState(dispatcherWrapper, this, (HashMap)null), this);
         }
      }

      public Object pushComponentInvocationContext() {
         return this.initDispatcherManager.pushCIC(this.componentInvocationContext);
      }

      public JMSDispatcher dispatcherCreateForCDS(Context ctx, DispatcherId dispatcherId) throws weblogic.messaging.dispatcher.DispatcherException {
         DispatcherWrapper dispatcherWrapper = JMSDispatcherManager.internalJNDILookup(ctx, dispatcherId);
         return this.dispatcherAdapterOrPartitionAdapter(dispatcherWrapper);
      }

      // $FF: synthetic method
      ContextImpl(String x1, String x2, boolean x3, InitDispatcherManager x4, InvocableManagerDelegate x5, ComponentInvocationContext x6, Object x7) {
         this(x1, x2, x3, x4, x5, x6);
      }
   }
}
