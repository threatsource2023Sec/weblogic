package weblogic.messaging.path.helper;

import java.lang.annotation.Annotation;
import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.security.AccessController;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.transaction.Transaction;
import weblogic.cache.lld.ChangeListener;
import weblogic.cache.lld.LLDFactory;
import weblogic.cache.utils.ExpiredMap;
import weblogic.cluster.ClusterServicesActivator.Locator;
import weblogic.common.CompletionListener;
import weblogic.common.CompletionRequest;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.health.LowMemoryNotificationService;
import weblogic.health.MemoryEvent;
import weblogic.health.MemoryListener;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.jms.extensions.JMSOrderException;
import weblogic.jndi.Environment;
import weblogic.management.WebLogicMBean;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.ResourceGroupTemplateMBean;
import weblogic.management.utils.ActiveBeanUtil;
import weblogic.messaging.path.AsyncMapRemote;
import weblogic.messaging.path.AsyncMapWithId;
import weblogic.messaging.path.ExceptionAdapter;
import weblogic.messaging.path.Key;
import weblogic.messaging.path.Member;
import weblogic.rjvm.PeerGoneException;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.server.GlobalServiceLocator;
import weblogic.store.PersistentStoreException;
import weblogic.transaction.TransactionManager;
import weblogic.transaction.TxHelper;
import weblogic.utils.collections.ConcurrentHashMap;

public class PathHelper {
   public static final DebugLogger PathSvc = DebugLogger.getDebugLogger("DebugPathSvc");
   public static final DebugLogger PathSvcVerbose = DebugLogger.getDebugLogger("DebugPathSvcVerbose");
   public static boolean retired = false;
   private static Object lock = new Object();
   private final Map serverInfos = new HashMap();
   private static final Map partitionPathHelper = new HashMap();
   public static final int QOS_CLEAR_CACHE = 8;
   public static final int QOS_OWNED_CACHE = 64;
   public static final int QOS_DIRTY_CACHE = 512;
   public static final int QOS_STORE = 32768;
   public static final int QOS_CACHE_ON_EQUAL = 16384;
   private static final int QOS_local = 584;
   public static final String DEFAULT_PATH_SERVICE_JNDI = "weblogic.PathService.default";
   private static final String PATH_SERVICE_RESOURCE_GROUP_JNDI = "weblogic.jms.path.service.rg.";
   private static final String PATH_SERVICE_RESOURCE_TEMPLATE_JNDI = "weblogic.jms.path.service.rgt.";
   static final int CLUSTERED_STRIPE_COUNT = 17;
   private final JMSOrderExceptionAdapter jmsOrderExceptionAdapter = new JMSOrderExceptionAdapter();
   private final TransactionManager tranManager = TxHelper.getTransactionManager();
   private final Context context;
   private final String[] stripedJndiNames;
   private static ActiveBeanUtil activeBeanUtil;
   private static final int Retry_Put_If_Absent = 0;
   private static final int Retry_Remove = 1;
   private static final int Retry_Put = 2;
   private static final int Retry_Get = 3;
   private static final String PATH_SERVICE = "BEA.PathService";

   private PathHelper(Context context, String jndiName) {
      this.context = context;
      this.stripedJndiNames = new String[18];
      this.stripedJndiNames[17] = jndiName;

      for(int index = 0; index < 17; ++index) {
         this.stripedJndiNames[index] = this.getJndiName(index);
      }

   }

   public static PathHelper partitionAwareFindOrCreate(Context context, String candidateJNDIname, ComponentInvocationContext componentInvocationContext) throws NamingException {
      return internalFindOrCreate(context, candidateJNDIname, componentInvocationContext);
   }

   private static ActiveBeanUtil getActiveBeanUtil() {
      synchronized(lock) {
         if (activeBeanUtil == null) {
            activeBeanUtil = (ActiveBeanUtil)GlobalServiceLocator.getServiceLocator().getService(ActiveBeanUtil.class, new Annotation[0]);
         }
      }

      return activeBeanUtil;
   }

   private static PathHelper internalFindOrCreate(Context contextOrNull, String candidateJNDIname, ComponentInvocationContext componentInvocationContext) throws NamingException {
      if (candidateJNDIname == null) {
         throw new NamingException("can not expand jndi name");
      } else {
         String key = componentInvocationContext.getPartitionName() + "/partition,jndi/" + candidateJNDIname;
         synchronized(lock) {
            PathHelper pathHelper = (PathHelper)partitionPathHelper.get(key);
            if (pathHelper == null) {
               if (contextOrNull == null) {
                  contextOrNull = internalJNDIContext();
               }

               pathHelper = new PathHelper(contextOrNull, candidateJNDIname);
               partitionPathHelper.put(key, pathHelper);
            }

            return pathHelper;
         }
      }
   }

   public static Object getOriginalScopeMBean(WebLogicMBean activatedMBean) {
      if (activatedMBean == null) {
         return null;
      } else {
         WebLogicMBean bean = null;
         ResourceGroupMBean resourceGroupMBean = null;
         ResourceGroupTemplateMBean resourceGroupTemplateMBean = null;
         String resourceGroupName = null;
         String resourceGroupTemplateName = null;
         bean = getActiveBeanUtil().toOriginalBean((ConfigurationMBean)activatedMBean);

         for(int loop = 150; !(bean instanceof ResourceGroupMBean); bean = ((WebLogicMBean)bean).getParent()) {
            if (bean instanceof ResourceGroupTemplateMBean) {
               return bean;
            }

            if (bean instanceof DomainMBean) {
               return bean;
            }

            --loop;
         }

         return bean;
      }
   }

   public static String pathServiceJndiNameFromScopeMBean(Object originalScopeMBean) {
      String candidateJNDIname;
      ResourceGroupTemplateMBean rgtMBean;
      if (originalScopeMBean instanceof ResourceGroupMBean) {
         ResourceGroupMBean resourceGroupMBean = (ResourceGroupMBean)originalScopeMBean;
         rgtMBean = resourceGroupMBean.getResourceGroupTemplate();
         if (rgtMBean != null) {
            candidateJNDIname = jndiNameForRGT(rgtMBean);
         } else {
            candidateJNDIname = jndiNameForRG(resourceGroupMBean);
         }
      } else if (originalScopeMBean instanceof ResourceGroupTemplateMBean) {
         rgtMBean = (ResourceGroupTemplateMBean)originalScopeMBean;
         candidateJNDIname = jndiNameForRGT(rgtMBean);
      } else {
         candidateJNDIname = "weblogic.PathService.default";
      }

      return candidateJNDIname;
   }

   private static String jndiNameForRG(ResourceGroupMBean resourceGroupMBean) {
      return "weblogic.jms.path.service.rg." + resourceGroupMBean.getName();
   }

   private static String jndiNameForRGT(ResourceGroupTemplateMBean resourceGroupTemplateMBean) {
      return "weblogic.jms.path.service.rgt." + resourceGroupTemplateMBean.getName();
   }

   public static PathHelper manager() {
      try {
         return internalFindOrCreate(internalJNDIContext(), "weblogic.PathService.default", internalComponentInvocationContext());
      } catch (NamingException var1) {
         throw new UnsupportedOperationException(var1);
      }
   }

   private static Context internalJNDIContext() throws NamingException {
      Environment env = new Environment();
      env.setCreateIntermediateContexts(true);
      env.setReplicateBindings(true);
      return env.getInitialContext();
   }

   private static ComponentInvocationContext internalComponentInvocationContext() {
      return ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
   }

   public Context getContext() {
      return this.context;
   }

   public String[] getAllJndiNames() {
      return this.stripedJndiNames;
   }

   public String getJndiName() {
      return this.stripedJndiNames[17];
   }

   public String getJndiName(int x) {
      StringBuffer buf = new StringBuffer(this.getJndiName());
      buf.append("-");
      if (x < 10) {
         buf.append("0");
      }

      buf.append(x);
      return buf.toString();
   }

   public final void cachedGet(String IGNOREDjndiName, Key key, int qualityOfService, CompletionRequest completionRequest) throws NamingException {
      this.findOrCreateServerInfo(key).cachedGet(key, qualityOfService, completionRequest);
   }

   public final Member cachedGet(String IGNOREDjndiName, Key key, int qualityOfService) throws Throwable {
      return this.findOrCreateServerInfo(key).cachedGet(key, qualityOfService);
   }

   public final boolean cachedRemove(String IGNOREDjndiName, Key key, Member member, int qualityOfService) throws NamingException, PathServiceException {
      return this.findOrCreateServerInfo(key).cachedRemove(key, member, qualityOfService);
   }

   public final void cachedPutIfAbsent(String IGNOREDjndiName, Key key, Member member, int qualityOfService, CompletionRequest completionRequest) throws NamingException {
      this.findOrCreateServerInfo(key).cachedPutIfAbsent(key, member, qualityOfService, completionRequest);
   }

   public final void update(String IGNOREDjndiName, Key key, Member member, CompletionRequest completionRequest) throws NamingException {
      this.findOrCreateServerInfo(key).update(key, member, completionRequest);
   }

   private static String broadcastJndiName(String jndi) {
      return "BEA.PathService" + jndi;
   }

   public ChangeListener getDirtyCacheUpdaterMap(String jndiName) {
      return this.findServerInfo(jndiName).invalidator;
   }

   private ServerInfo findServerInfo(String jndiName) {
      synchronized(lock) {
         return (ServerInfo)this.serverInfos.get(jndiName);
      }
   }

   private AsyncMapRemote jndiLookup(String jndiName) throws NamingException {
      AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      SecurityServiceManager.pushSubject(KERNEL_ID, KERNEL_ID);

      AsyncMapRemote var3;
      try {
         if (PathSvcVerbose.isDebugEnabled()) {
            PathSvcVerbose.debug("PathHelper.jndiLookup() " + jndiName);
         }

         var3 = (AsyncMapRemote)this.context.lookup(jndiName);
      } finally {
         SecurityServiceManager.popSubject(KERNEL_ID);
      }

      return var3;
   }

   public ServerInfo findOrCreateServerInfo(Key key) throws NamingException {
      int index = key.hashCode() % 17;
      if (index < 0) {
         index = -index;
      }

      if (PathSvcVerbose.isDebugEnabled()) {
         PathSvcVerbose.debug("PathHelper.findOrCreateServerInfo(" + key + "), index: " + index);
      }

      return this.createServerInfo(this.stripedJndiNames[index]);
   }

   public void removeServerInfo(Key key) throws NamingException {
      int index = key.hashCode() % 17;
      if (index < 0) {
         index = -index;
      }

      ServerInfo si = (ServerInfo)this.serverInfos.remove(this.stripedJndiNames[index]);
      if (PathSvcVerbose.isDebugEnabled()) {
         PathSvcVerbose.debug("PathHelper.removeServerInfo(" + key + "), index: " + index + " removing jndiName: " + this.stripedJndiNames[index] + "\n result: " + si + "\n serverInfos " + this.serverInfos);
      }

   }

   private ServerInfo createServerInfo(String jndiName) throws NamingException {
      synchronized(lock) {
         ServerInfo serverInfo = (ServerInfo)this.serverInfos.get(jndiName);
         if (PathSvcVerbose.isDebugEnabled()) {
            PathSvcVerbose.debug("PathHelper.createServerInfo() serverInfos.get(" + jndiName + ")  in serverInfos map, result " + serverInfo);
         }

         if (serverInfo != null) {
            return serverInfo;
         } else {
            serverInfo = new ServerInfo(this.getAdapterFromJndi(jndiName));
            if (PathSvcVerbose.isDebugEnabled()) {
               PathSvcVerbose.debug("PathHelper.createServerInfo() got new serverInfo  " + serverInfo + " from jndiName " + jndiName);
            }

            this.serverInfos.put(jndiName, serverInfo);
            if (PathSvcVerbose.isDebugEnabled()) {
               PathSvcVerbose.debug("PathHelper.createServerInfo() serverInfos.put(" + jndiName + "," + serverInfo + ") serverInfos: " + this.serverInfos);
            }

            return serverInfo;
         }
      }
   }

   private AsyncMapRemoteAdapter getAdapterFromJndi(String jndiName) throws NamingException {
      if (PathSvcVerbose.isDebugEnabled()) {
         PathSvcVerbose.debug("PathHelper.getAdapterFromJndi(" + jndiName + ")");
      }

      return new AsyncMapRemoteAdapter(this, jndiName, this.jndiLookup(jndiName), this.jmsOrderExceptionAdapter());
   }

   public JMSOrderExceptionAdapter jmsOrderExceptionAdapter() {
      return this.jmsOrderExceptionAdapter;
   }

   public static Throwable wrapExtensionImpl(Throwable cause) {
      return (Throwable)(cause != null && !(cause instanceof RuntimeException) && !(cause instanceof Error) && !(cause instanceof JMSOrderException) ? new JMSOrderException(cause.getMessage(), cause) : cause);
   }

   public void register(boolean add, String jndiName, AsyncMapWithId asyncMapWithId) {
      ServerInfo serverInfo = null;
      ServerInfo debugValue2;
      synchronized(lock) {
         if (add) {
            serverInfo = new ServerInfo(asyncMapWithId);
         }

         if (add) {
            debugValue2 = (ServerInfo)this.serverInfos.put(jndiName, serverInfo);
            if (PathSvcVerbose.isDebugEnabled()) {
               PathSvcVerbose.debug("PathHelper.register() serverInfos.put(" + jndiName + "," + serverInfo + ") serverInfos: " + this.serverInfos);
            }
         } else {
            debugValue2 = (ServerInfo)this.serverInfos.remove(jndiName);
            if (PathSvcVerbose.isDebugEnabled()) {
               PathSvcVerbose.debug("PathHelper.remove() serverInfos.remove(" + jndiName + ") serverInfos: " + this.serverInfos);
            }
         }
      }

      if (add) {
         if (debugValue2 != null) {
            PathSvc.debug("\n\nPathService double registration? remote and local? " + jndiName, new Exception("debug deploy PathService " + debugValue2));
         }
      } else if (debugValue2 != null && debugValue2.delegate != asyncMapWithId) {
         PathSvc.debug("\n\nPathService double unregistration? remote and local? " + jndiName, new Exception("debug deploy PathService " + debugValue2));
      }

   }

   public void unRegister(AsyncMapWithId asyncMapWithId) {
      synchronized(lock) {
         ServerInfo serverInfo = (ServerInfo)this.serverInfos.remove(asyncMapWithId.getJndiName());
         if (PathSvcVerbose.isDebugEnabled()) {
            PathSvcVerbose.debug("PathHelper.unRegister() serverInfos.remove(" + asyncMapWithId.getJndiName() + ")\n serverInfo: " + serverInfo + "\n serverInfos: " + this.serverInfos);
         }

         if (serverInfo != null && serverInfo.invalidator != null) {
            serverInfo.dirty.clear();
            serverInfo.invalidator = null;
         }
      }
   }

   void handleException(Exception exception, AsyncMapWithId asyncMapWithId) {
      if (asyncMapWithId instanceof AsyncMapRemoteAdapter && exception instanceof RemoteException) {
         synchronized(lock) {
            ServerInfo serverInfo = this.findServerInfo(asyncMapWithId.getJndiName());
            if (serverInfo == null || serverInfo.delegate != asyncMapWithId) {
               return;
            }

            this.unRegister(asyncMapWithId);
         }
      }

   }

   private class LowMemoryForgetfulMap implements Map, MemoryListener, Cloneable {
      private int LOWMEM_SIZE = 4096;
      private int NORMAL_SIZE = 1048576;
      private long LOWMEM_DELAY = 60000L;
      private long NORMAL_DELAY = 480000L;
      private long currentDelay;
      private int currentSize;
      ExpiredMap expiredMap;

      LowMemoryForgetfulMap() {
         LowMemoryNotificationService.addMemoryListener(this);
         this.normalUOOMemory();
         this.allocateNormal();
      }

      private void allocateNormal() {
         this.expiredMap = new ExpiredMap(this.currentSize, new HashMap(16), this.currentDelay);
      }

      private void normalUOOMemory() {
         this.currentSize = this.NORMAL_SIZE;
         this.currentDelay = this.NORMAL_DELAY;
      }

      public void memoryChanged(MemoryEvent event) {
         if (event.getEventType() == 1) {
            synchronized(this.expiredMap) {
               this.currentSize = this.LOWMEM_SIZE;
               this.currentDelay = this.LOWMEM_DELAY;
               this.expiredMap.clear();
               this.allocateNormal();
            }
         } else if (event.getEventType() == 0) {
            synchronized(this.expiredMap) {
               Map oldCurrent = this.expiredMap;
               this.normalUOOMemory();
               this.allocateNormal();
               if (oldCurrent.size() > 0) {
                  this.expiredMap.putAll(oldCurrent);
               }
            }
         }

      }

      public int size() {
         return this.expiredMap.size();
      }

      public boolean isEmpty() {
         return this.expiredMap.isEmpty();
      }

      public boolean containsKey(Object key) {
         return this.expiredMap.containsKey(key);
      }

      public boolean equals(Object o) {
         return this.expiredMap.equals(o);
      }

      public int hashCode() {
         return this.expiredMap.hashCode();
      }

      public void putAll(Map map) {
         this.expiredMap.putAll(map);
      }

      public Object clone() {
         return this.expiredMap.clone();
      }

      public void clear() {
         this.expiredMap.clear();
      }

      public boolean containsValue(Object value) {
         return this.expiredMap.containsValue(value);
      }

      public Object get(Object key) {
         return this.expiredMap.get(key);
      }

      public Object put(Object k, Object v) {
         return this.expiredMap.put(k, v);
      }

      public Object putIfAbsent(Object k, Object v) {
         return this.expiredMap.putIfAbsent(k, v);
      }

      public Object remove(Object key) {
         return this.expiredMap.remove(key);
      }

      public Set keySet() {
         return this.expiredMap.keySet();
      }

      public Collection values() {
         return this.expiredMap.values();
      }

      public Set entrySet() {
         return this.expiredMap.entrySet();
      }
   }

   public static class JMSOrderExceptionAdapter implements ExceptionAdapter {
      public Throwable wrapException(Throwable cause) {
         return PathHelper.wrapExtensionImpl(cause);
      }

      public Throwable unwrapException(Throwable normalException) {
         while(normalException instanceof JMSOrderException && normalException.getCause() != null) {
            normalException = normalException.getCause();
         }

         return normalException;
      }
   }

   public class ServerInfo {
      private final ConcurrentHashMap owned = new ConcurrentHashMap();
      private final LowMemoryForgetfulMap dirty = PathHelper.this.new LowMemoryForgetfulMap();
      private AsyncMapWithId delegate;
      private ChangeListener invalidator;

      ServerInfo(AsyncMapWithId argDelegate) {
         this.delegate = argDelegate;
         if (Locator.locateClusterServices() != null) {
            this.invalidator = LLDFactory.getInstance().createLLDInvalidator(PathHelper.broadcastJndiName(this.delegate.getJndiName()), this.dirty);
         }

      }

      public String getJndiName() {
         return this.delegate.getJndiName();
      }

      private Member useCache(Key key, int qualityOfService) {
         Member found;
         if ((64 & qualityOfService) != 0) {
            if ((8 & qualityOfService) != 0) {
               found = this.ownedRemove(key);
            } else {
               found = this.ownedGet(key);
               if (found != null) {
                  return found;
               }
            }
         } else {
            found = null;
         }

         Member found2;
         if ((512 & qualityOfService) != 0) {
            if ((8 & qualityOfService) != 0) {
               found2 = this.dirtyRemove(key);
            } else {
               found2 = this.dirtyGet(key);
               if (found2 != null) {
                  return found2;
               }
            }
         } else {
            found2 = null;
         }

         return found == null ? found2 : found;
      }

      private Member dirtyPut(Key key, Member newValue) {
         Member returnValue = (Member)this.dirty.put(key, newValue);
         return returnValue == null ? null : returnValue;
      }

      private Member dirtyPutIfAbsent(Key key, Member newValue) {
         Member returnValue = (Member)this.dirty.putIfAbsent(key, newValue);
         return returnValue == null ? null : returnValue;
      }

      private Member dirtyGet(Key key) {
         Member returnValue = (Member)this.dirty.get(key);
         return returnValue == null ? null : returnValue;
      }

      private Member dirtyRemove(Key key) {
         Member returnValue = (Member)this.dirty.remove(key);
         return returnValue == null ? null : returnValue;
      }

      private Member ownedPut(Key key, Member newValue) {
         return (Member)this.owned.put(key, newValue);
      }

      private Member ownedPutIfAbsent(Key key, Member newValue) {
         return (Member)this.owned.putIfAbsent(key, newValue);
      }

      private Member ownedGet(Key key) {
         Member member = (Member)this.owned.get(key);
         if (member == null && PathHelper.retired && PathHelper.PathSvcVerbose.isDebugEnabled()) {
            PathHelper.PathSvcVerbose.debug("Missing owned key " + key + " from " + this.owned.keySet());
         }

         return member;
      }

      private Member ownedRemove(Key key) {
         return (Member)this.owned.remove(key);
      }

      public String toString() {
         return "jndiName=" + this.getJndiName();
      }

      public final void cachedRemove(Key key, Member member, int qualityOfService, CompletionRequest cr) throws NamingException {
         if (('耀' & qualityOfService) != 0) {
            qualityOfService |= 584;
         }

         Member found = this.useCache(key, qualityOfService);
         if (('耀' & qualityOfService) == 0) {
            cr.setResult(found);
         } else {
            AsyncMapWithId map = this.lookupAsyncMap();
            map.remove(key, member, PathHelper.this.new RetryOnce(1, key, member, map, cr));
         }
      }

      public final void cachedGet(Key key, int qualityOfService, CompletionRequest completionRequest) throws NamingException {
         Transaction tx = PathHelper.this.tranManager.forceSuspend();

         try {
            this.cachedGetNoTx(key, qualityOfService, completionRequest);
         } finally {
            PathHelper.this.tranManager.forceResume(tx);
         }

      }

      private void cachedGetNoTx(Key key, int qualityOfService, CompletionRequest completionRequest) throws NamingException {
         if ((584 & qualityOfService) != 0) {
            Member found = this.useCache(key, qualityOfService);
            if (found != null) {
               completionRequest.setResult(found);
               return;
            }
         }

         if (('耀' & qualityOfService) != 0) {
            AsyncMapWithId map = this.lookupAsyncMap();
            int qos = qualityOfService & -32777;
            CompletionRequest getCR;
            if (qos != 0) {
               getCR = this.updateCacheCR(key, (Member)null, qos, completionRequest);
            } else {
               getCR = completionRequest;
            }

            map.get(key, PathHelper.this.new RetryOnce(3, key, (Member)null, map, getCR));
         } else {
            completionRequest.setResult((Object)null);
         }

      }

      public final Member cachedGet(Key key, int qualityOfService) throws Throwable {
         if ((584 & qualityOfService) != 0) {
            Member found = this.useCache(key, qualityOfService);
            if (found != null) {
               return found;
            }
         }

         if (('耀' & qualityOfService) != 0) {
            AsyncMapWithId map = this.lookupAsyncMap();
            int qos = qualityOfService & -32777;
            CompletionRequest getCR;
            if (qos != 0) {
               getCR = this.updateCacheCR(key, (Member)null, qos, (CompletionRequest)null);
            } else {
               getCR = new CompletionRequest();
            }

            map.get(key, PathHelper.this.new RetryOnce(3, key, (Member)null, map, getCR));
            return (Member)getCR.getResult();
         } else {
            return null;
         }
      }

      public final boolean cachedRemove(Key key, Member member, int qualityOfService) throws NamingException, PathServiceException {
         if (('耀' & qualityOfService) != 0) {
            qualityOfService |= 584;
         }

         Member found = this.useCache(key, qualityOfService);
         if (('耀' & qualityOfService) == 0) {
            return found != null;
         } else {
            CompletionRequest cr = new CompletionRequest();

            try {
               AsyncMapWithId map = this.lookupAsyncMap();
               map.remove(key, member, PathHelper.this.new RetryOnce(1, key, member, map, cr));
            } catch (NamingException var10) {
               throw var10;
            }

            try {
               return Boolean.TRUE == cr.getResult();
            } catch (RuntimeException var7) {
               throw var7;
            } catch (Error var8) {
               throw var8;
            } catch (Throwable var9) {
               throw PathHelper.this.new PathServiceException(var9);
            }
         }
      }

      public final void cachedPutIfAbsent(Key key, Member member, int qualityOfService, CompletionRequest completionRequest) throws NamingException {
         Transaction tx = PathHelper.this.tranManager.forceSuspend();

         try {
            this.cachedPutIfAbsentNoTx(key, member, qualityOfService, completionRequest);
         } finally {
            PathHelper.this.tranManager.forceResume(tx);
         }

      }

      private void cachedPutIfAbsentNoTx(Key key, Member member, int qualityOfService, CompletionRequest completionRequest) throws NamingException {
         if ((584 & qualityOfService) != 0) {
            Member found = this.useCache(key, qualityOfService);
            if (('耀' & qualityOfService) == 0) {
               if ((512 & qualityOfService) != 0) {
                  completionRequest.setResult(this.dirtyPutIfAbsent(key, member));
               } else if ((64 & qualityOfService) != 0) {
                  completionRequest.setResult(this.ownedPutIfAbsent(key, member));
               }

               return;
            }

            if (found != null) {
               completionRequest.setResult(found);
               return;
            }
         }

         if (('耀' & qualityOfService) != 0) {
            AsyncMapWithId map = this.lookupAsyncMap();
            int qos = qualityOfService & -32777;
            CompletionRequest putCR;
            if (qos != 0) {
               putCR = this.updateCacheCR(key, member, qos, completionRequest);
            } else {
               putCR = completionRequest;
            }

            map.putIfAbsent(key, member, PathHelper.this.new RetryOnce(0, key, member, map, putCR));
         } else {
            completionRequest.setResult((Object)null);
         }

      }

      public final void update(Key key, Member member, CompletionRequest completionRequest) throws NamingException {
         Transaction tx = PathHelper.this.tranManager.forceSuspend();

         try {
            this.updateNoTx(key, member, completionRequest);
         } finally {
            PathHelper.this.tranManager.forceResume(tx);
         }

      }

      private void updateNoTx(Key key, Member member, CompletionRequest completionRequest) throws NamingException {
         this.useCache(key, 584);
         AsyncMapWithId map = this.lookupAsyncMap();
         map.put(key, member, PathHelper.this.new RetryOnce(2, key, member, map, completionRequest));
      }

      private CompletionRequest updateCacheCR(final Key key, final Member member, final int qos, CompletionRequest completionRequest) {
         return new UpdateCache(completionRequest) {
            public void onException(CompletionRequest completionRequest, Throwable reason) {
               if (this.userCompletionRequest != null) {
                  this.userCompletionRequest.setResult(reason);
               }

            }

            public void onCompletion(CompletionRequest completionRequest, Object result) {
               if ((512 & qos) != 0) {
                  if ((result != null || member != null) && ((16384 & qos) == 0 || member.equals(result))) {
                     ServerInfo.this.dirtyPut(key, result == null ? member : (Member)result);
                  } else {
                     ServerInfo.this.dirtyRemove(key);
                  }
               } else if ((64 & qos) != 0) {
                  if ((result != null || member != null) && ((16384 & qos) == 0 || member.equals(result))) {
                     ServerInfo.this.ownedPut(key, result == null ? member : (Member)result);
                  } else {
                     ServerInfo.this.ownedRemove(key);
                  }
               }

               if (this.userCompletionRequest != null) {
                  boolean old = this.userCompletionRequest.runListenersInSetResult(true);

                  try {
                     this.userCompletionRequest.setResult(result);
                  } finally {
                     if (old) {
                        return;
                     }

                     this.userCompletionRequest.runListenersInSetResult(false);
                  }
               }

            }
         };
      }

      private AsyncMapWithId lookupAsyncMap() throws NamingException {
         AsyncMapWithId asyncMap = this.delegate;
         if (asyncMap != null) {
            return asyncMap;
         } else {
            synchronized(PathHelper.lock) {
               if (this.delegate != null) {
                  return this.delegate;
               } else {
                  this.delegate = PathHelper.this.getAdapterFromJndi(this.getJndiName());
                  return this.delegate;
               }
            }
         }
      }
   }

   private abstract class UpdateCache extends CompletionRequest implements CompletionListener {
      CompletionRequest userCompletionRequest;

      UpdateCache(CompletionRequest userCompletionRequest) {
         this.userCompletionRequest = userCompletionRequest;
         this.addListener(this);
      }
   }

   private class RetryOnce extends CompletionRequest implements CompletionListener {
      int type;
      Key key;
      Member member;
      AsyncMapWithId oldMap;
      CompletionRequest userCompletionRequest;

      RetryOnce(int kind, Key k, Member mbr, AsyncMapWithId old, CompletionRequest completionRequest) {
         this.type = kind;
         this.key = k;
         this.member = mbr;
         this.oldMap = old;
         this.userCompletionRequest = completionRequest;
         this.addListener(this);
      }

      public void onCompletion(CompletionRequest request, Object result) {
         if (PathHelper.PathSvcVerbose.isDebugEnabled()) {
            PathHelper.PathSvcVerbose.debug("PathHelper.onCompletion() request " + request + " result " + result + " userCompletionRequest " + this.userCompletionRequest);
         }

         if (this.userCompletionRequest != null) {
            boolean old = this.userCompletionRequest.runListenersInSetResult(true);

            try {
               this.userCompletionRequest.setResult(result);
            } finally {
               if (old) {
                  return;
               }

               this.userCompletionRequest.runListenersInSetResult(false);
            }
         }

      }

      public void onException(CompletionRequest request, Throwable reason) {
         if (PathHelper.PathSvcVerbose.isDebugEnabled()) {
            PathHelper.PathSvcVerbose.debug("PathHelper.onException() request " + request + " reason " + reason + " userCompletionRequest " + this.userCompletionRequest);
         }

         if (this.userCompletionRequest != null) {
            Throwable realReason = reason;
            if (reason instanceof JMSOrderException) {
               realReason = ((JMSOrderException)reason).getCause();
               if (PathHelper.PathSvcVerbose.isDebugEnabled()) {
                  PathHelper.PathSvcVerbose.debug("PathHelper.onException() reason instanceof JMSOrderException, get realReason " + realReason);
               }
            }

            if (!(realReason instanceof PeerGoneException) && !(realReason instanceof ConnectException) && !(realReason instanceof PersistentStoreException)) {
               if (PathHelper.PathSvcVerbose.isDebugEnabled()) {
                  PathHelper.PathSvcVerbose.debug("PathHelper.onException() bypassed doRetry with cause: " + realReason + " reason " + reason);
               }

               this.onCompletion(this, reason);
            } else {
               if (PathHelper.PathSvcVerbose.isDebugEnabled()) {
                  PathHelper.PathSvcVerbose.debug("PathHelper.onException() calling doRetry(" + reason + ") due to: " + realReason);
               }

               this.doRetry(reason);
            }

         }
      }

      private void doRetry(Throwable oldThrowable) {
         AsyncMapWithId newMap;
         try {
            if (PathHelper.PathSvcVerbose.isDebugEnabled()) {
               PathHelper.PathSvcVerbose.debug("PathHelper.doRetry() reason:" + oldThrowable);
            }

            PathHelper.this.unRegister(this.oldMap);
            PathHelper.this.removeServerInfo(this.key);
            ServerInfo serverInfo = PathHelper.this.findOrCreateServerInfo(this.key);
            newMap = serverInfo.delegate;
            if (newMap == this.oldMap) {
               PathHelper.PathSvc.debug("retry has same map instance", oldThrowable);
               this.onCompletion(this, oldThrowable);
               return;
            }
         } catch (NamingException var8) {
            if (PathHelper.PathSvcVerbose.isDebugEnabled()) {
               PathHelper.PathSvcVerbose.debug("PathHelper.doRetry() failed: " + var8);
            }

            Throwable newThrowable = oldThrowable;
            Throwable oldCause = null;
            if (oldThrowable instanceof JMSOrderException) {
               oldCause = ((JMSOrderException)oldThrowable).getCause();
               PathHelper.PathSvcVerbose.debug("PathHelper.doRetry() oldCause " + oldCause);
            }

            if (oldCause != null && oldCause instanceof PersistentStoreException || oldThrowable instanceof PersistentStoreException) {
               newThrowable = new JMSOrderException("path service not available, " + var8.getMessage(), var8);
               PathHelper.PathSvcVerbose.debug("PathHelper.doRetry() getting newThrowable : " + newThrowable);
            }

            this.onCompletion(this, newThrowable);
            return;
         }

         try {
            switch (this.type) {
               case 0:
                  newMap.putIfAbsent(this.key, this.member, this.userCompletionRequest);
                  return;
               case 1:
                  newMap.remove(this.key, this.member, this.userCompletionRequest);
                  return;
               case 2:
                  newMap.put(this.key, this.member, this.userCompletionRequest);
                  return;
               case 3:
                  newMap.get(this.key, this.userCompletionRequest);
                  return;
            }
         } catch (Throwable var7) {
            PathHelper.PathSvc.debug("returning first exception, encountered retry exception:", var7);
            this.onCompletion(this, oldThrowable);
         }

      }
   }

   public class PathServiceException extends Exception {
      static final long serialVersionUID = -4564823747310961840L;

      PathServiceException(Throwable throwable) {
         super(throwable);
      }
   }
}
