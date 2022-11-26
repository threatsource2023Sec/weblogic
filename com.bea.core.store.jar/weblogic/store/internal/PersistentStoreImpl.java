package weblogic.store.internal;

import commonj.work.Work;
import commonj.work.WorkException;
import commonj.work.WorkManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.common.CompletionRequest;
import weblogic.kernel.KernelStatus;
import weblogic.store.DefaultObjectHandler;
import weblogic.store.ObjectHandler;
import weblogic.store.OperationStatistics;
import weblogic.store.PersistentHandle;
import weblogic.store.PersistentMapAsyncTX;
import weblogic.store.PersistentStore;
import weblogic.store.PersistentStoreConnection;
import weblogic.store.PersistentStoreException;
import weblogic.store.PersistentStoreFatalException;
import weblogic.store.PersistentStoreRecord;
import weblogic.store.PersistentStoreTestException;
import weblogic.store.PersistentStoreTransaction;
import weblogic.store.RuntimeHandler;
import weblogic.store.RuntimeUpdater;
import weblogic.store.StoreLogger;
import weblogic.store.StoreStatistics;
import weblogic.store.StoreWritePolicy;
import weblogic.store.common.PartitionNameUtils;
import weblogic.store.common.StoreDebug;
import weblogic.store.io.IOListener;
import weblogic.store.io.PersistentStoreIO;
import weblogic.store.io.file.BaseStoreIO;
import weblogic.store.io.file.ReplicatedStoreIO;
import weblogic.timers.NakedTimerListener;
import weblogic.timers.Timer;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.Debug;
import weblogic.utils.concurrent.ConcurrentBlockingQueue;
import weblogic.utils.concurrent.ConcurrentFactory;

public class PersistentStoreImpl implements PersistentStore, Runnable {
   private static final boolean VERBOSE_DIAGNOSTICS = Debug.getCategory("weblogic.store.VerboseDiagnostics").isEnabled();
   public static final String DEFAULT_STORE_NAME_PREFIX = "_WLS_";
   public static final String JDBC_TLOG_DECORATION = "JTA_JDBCTLOGStore";
   private static final String THREAD_PREFIX = "weblogic.store.";
   private static final String SYNC_DESERIALIZERS = System.getProperty("weblogic.store.SynchronousDeserializationSet", "weblogic.store:weblogic.jms:weblogic.messaging");
   private static final HashSet syncDeserializerSet = new HashSet();
   private String storeName;
   private String storeShortName;
   private RuntimeHandler adminHandler;
   private RuntimeUpdater mbean;
   PersistentStoreIO ios;
   private final ConcurrentBlockingQueue pendingRequests;
   private final ReusableCompletionRequest shutdownTask;
   private final CallerClassUtil callerClassUtil;
   private Timer daemonPollTimer;
   private int devicePollInterval;
   private PersistentStoreConnectionImpl systemConnection;
   StoreStatisticsImpl statistics;
   private int nextTypeCode;
   static final int INVALID_TYPECODE = -1;
   protected static final byte MAP_TYPE = 1;
   protected static final byte DEFAULT_TYPE = 0;
   final Map connections;
   protected final HashMap maps;
   volatile boolean isOpen;
   private PersistentStoreException fatalException;
   private boolean healthSetterComplete;
   final HashMap config;
   private WorkManager wm;
   private static final int LOAD_DIST_SIZE = 100;
   private static final int HIGH_LOAD_THRESHOLD = 80;
   private static final int LOW_LOAD_THRESHOLD = 20;
   private boolean enforceWorkerFlush;

   private static int getDevicePollIntervalMillis(String name) {
      int interval = Math.abs(ReplicatedStoreIO.getIntConfiguration((HashMap)null, "DevicePollIntervalMillis", name, ".DevicePollIntervalMillis", 1000));
      if (interval == 0) {
         interval = 1000;
      }

      if (interval > 1000) {
         interval = 1000;
      }

      if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
         StoreDebug.storeIOPhysical.debug("PersistentStoreImpl: " + name + "." + ".DevicePollIntervalMillis" + ":" + interval);
      }

      return interval;
   }

   public PersistentStoreImpl(String storeName, PersistentStoreIO ios) throws PersistentStoreException {
      this(storeName, ios, (RuntimeHandler)null);
   }

   public PersistentStoreImpl() {
      this.pendingRequests = ConcurrentFactory.createConcurrentBlockingQueue();
      this.shutdownTask = new ReusableCompletionRequest();
      this.callerClassUtil = new CallerClassUtil();
      this.nextTypeCode = 1;
      this.connections = Collections.synchronizedMap(new HashMap());
      this.maps = new HashMap();
      this.healthSetterComplete = true;
      this.config = new HashMap();
      this.enforceWorkerFlush = false;
   }

   public PersistentStoreImpl(String name, PersistentStoreIO ios, RuntimeHandler adminHandler) throws PersistentStoreException {
      this.pendingRequests = ConcurrentFactory.createConcurrentBlockingQueue();
      this.shutdownTask = new ReusableCompletionRequest();
      this.callerClassUtil = new CallerClassUtil();
      this.nextTypeCode = 1;
      this.connections = Collections.synchronizedMap(new HashMap());
      this.maps = new HashMap();
      this.healthSetterComplete = true;
      this.config = new HashMap();
      this.enforceWorkerFlush = false;
      this.init_internal(name, ios);
      this.adminHandler = adminHandler;
      if (adminHandler != null) {
         this.mbean = adminHandler.createStoreMBean(this);
      }

   }

   private void init_internal(String name, PersistentStoreIO ios) {
      this.storeName = name;
      this.storeShortName = PartitionNameUtils.stripDecoratedPartitionName(name);
      this.devicePollInterval = getDevicePollIntervalMillis(this.storeName);
      this.statistics = new StoreStatisticsImpl(name);
      this.ios = ios;
      if (ios.supportsFastReads()) {
         ((BaseStoreIO)ios).setStats(this.statistics);
      }

   }

   protected void init(String storeName, PersistentStoreIO ios, RuntimeHandler handler) throws PersistentStoreException {
      this.adminHandler = handler;
      this.init(storeName, ios);
   }

   protected void init(String storeName, PersistentStoreIO ios) throws PersistentStoreException {
      if (this.storeName != null) {
         throw new PersistentStoreException("Already constructed store " + storeName);
      } else {
         this.init_internal(storeName, ios);
      }
   }

   public void setWorkManager(WorkManager wm) {
      this.wm = wm;
   }

   public boolean supportsFastReads() {
      return this.ios.supportsFastReads();
   }

   public RuntimeUpdater getMBean() {
      return this.mbean;
   }

   private void setFatalException(PersistentStoreFatalException e) {
      PersistentStoreException exceptionToLog = null;
      if (StoreDebug.storeAdmin.isDebugEnabled()) {
         StoreDebug.storeAdmin.debug("setFatalException: e: " + e.getMessage());
      }

      synchronized(this) {
         if (e == null) {
            this.fatalException = null;
         } else if (this.fatalException == null) {
            if (e.getCause() != null && e.getCause() instanceof PersistentStoreException) {
               this.fatalException = (PersistentStoreException)((PersistentStoreException)e.getCause());
            } else {
               this.fatalException = e;
            }

            exceptionToLog = this.fatalException;
         }
      }

      if (StoreDebug.storeAdmin.isDebugEnabled()) {
         StoreDebug.storeAdmin.debug("setFatalException: exceptionToLog: " + exceptionToLog.getMessage());
      }

      if (exceptionToLog != null) {
         if (!this.isSpecialStore()) {
            StoreLogger.logFatalExceptionEncountered(this.storeName, exceptionToLog.toString(), exceptionToLog);
         }

         this.healthFailed(exceptionToLog);
      }

   }

   public synchronized PersistentStoreException getFatalException() {
      return this.fatalException;
   }

   public synchronized boolean isHealthSetterComplete() {
      return this.healthSetterComplete;
   }

   public synchronized void setHealthSetterComplete(boolean healthSetterComplete) {
      this.healthSetterComplete = healthSetterComplete;
   }

   private boolean isTLOGJDBCStore() {
      return this.storeName.contains("JTA_JDBCTLOGStore");
   }

   private boolean isDefaultStoreOfOtherServer() {
      return this.storeName.startsWith("_WLS_") && !this.isDefaultStore();
   }

   private boolean isMigratable() {
      if (!this.config.containsKey("IsMigratable")) {
         return false;
      } else {
         try {
            return (Boolean)this.config.get("IsMigratable");
         } catch (Exception var2) {
            if (StoreDebug.storeIOLogical.isDebugEnabled()) {
               StoreDebug.storeIOLogical.debug("Bad value in config for PersistentStore.IS_MIGRATABLE; defaulting to FALSE", var2);
            }

            return false;
         }
      }
   }

   private boolean isRPEnabled() {
      if (!this.config.containsKey("IsRPEnabled")) {
         return false;
      } else {
         try {
            return (Boolean)this.config.get("IsRPEnabled");
         } catch (Exception var2) {
            if (StoreDebug.storeIOLogical.isDebugEnabled()) {
               StoreDebug.storeIOLogical.debug("Bad value in config for PersistentStore.IS_RP_ENABLED; defaulting to FALSE", var2);
            }

            return false;
         }
      }
   }

   private boolean isDefaultStore() {
      if (!this.config.containsKey("IsDefaultStore")) {
         return false;
      } else {
         try {
            return (Boolean)this.config.get("IsDefaultStore");
         } catch (Exception var2) {
            if (StoreDebug.storeIOLogical.isDebugEnabled()) {
               StoreDebug.storeIOLogical.debug("Bad value in config for PersistentStore.IS_DEFAULT_STORE; defaulting to FALSE", var2);
            }

            return false;
         }
      }
   }

   public boolean isSpecialStore() {
      if (StoreDebug.storeAdmin.isDebugEnabled()) {
         StoreDebug.storeAdmin.debug("PersistentStoreImpl.isSpecialStore(): name: " + this.storeName + " isTLOGJDBCStore: " + this.isTLOGJDBCStore() + " isDefaultStoreOfOtherServer: " + this.isDefaultStoreOfOtherServer() + " isMigratable: " + this.isMigratable() + " isRPEnabled: " + this.isRPEnabled());
      }

      return this.isTLOGJDBCStore() || this.isDefaultStoreOfOtherServer() || this.isMigratable() || this.isRPEnabled();
   }

   public PersistentStoreConnection createConnection(String name) throws PersistentStoreException {
      return this.createConnectionInternal(name, (byte)0);
   }

   private static boolean deserializeOnline(Class caller) {
      String callerName = caller.getName();

      for(int nextDot = callerName.indexOf(46); nextDot >= 0; nextDot = callerName.indexOf(46, nextDot + 1)) {
         String prefix = callerName.substring(0, nextDot);
         if (syncDeserializerSet.contains(prefix)) {
            return true;
         }
      }

      return syncDeserializerSet.contains(callerName);
   }

   public PersistentStoreConnection createConnection(String name, ObjectHandler handler) throws PersistentStoreException {
      PersistentStoreConnectionImpl ret = (PersistentStoreConnectionImpl)this.createConnectionInternal(name, (byte)0);
      ret.setObjectHandler(handler);
      return ret;
   }

   public PersistentStoreConnection getConnection(String name) {
      return this.getConnectionInternal(name, (byte)0);
   }

   public PersistentStoreConnection getMapConnection(String name) {
      return this.getConnectionInternal(name, (byte)1);
   }

   private PersistentStoreConnection getConnectionInternal(String name, byte kind) {
      ConnectionKey key = new ConnectionKey(name, kind);
      synchronized(this.connections) {
         ConnectionInfo connInfo = (ConnectionInfo)this.connections.get(key);
         return connInfo == null ? null : connInfo.getConnection();
      }
   }

   public PersistentStoreConnection createConnectionInternal(String name, byte kind) throws PersistentStoreException {
      this.checkOpen();
      if (!checkName(name)) {
         throw new PersistentStoreException(StoreLogger.logInvalidStoreConnectionNameLoggable(name));
      } else {
         ConnectionKey key = new ConnectionKey(name, kind);
         synchronized(this.connections) {
            ConnectionInfo connInfo = (ConnectionInfo)this.connections.get(key);
            if (connInfo == null) {
               connInfo = new ConnectionInfo(this.nextTypeCode++, name, kind);
               this.connections.put(key, connInfo);
               PersistentStoreTransaction ptx = this.begin();
               PersistentHandle handle = this.systemConnection.create(ptx, connInfo, 0);
               ptx.commit();
               connInfo.setHandle((PersistentHandleImpl)handle);
            }

            PersistentStoreConnectionImpl conn = connInfo.getConnection();
            if (conn == null) {
               conn = new PersistentStoreConnectionImpl(key, this, connInfo.typeCode);
               int initialObjectCount = this.ios.getNumObjects(connInfo.typeCode);
               conn.getStatisticsImpl().setInitialObjectCount((long)initialObjectCount);
               connInfo.setConnection(conn);
               this.registerConnection(conn);
            }

            conn.setObjectHandler(DefaultObjectHandler.THE_ONE);
            Class caller = this.callerClassUtil.getCallerClass();
            conn.onlineDeserializationPossible = this.supportsFastReads() && deserializeOnline(caller);
            return conn;
         }
      }
   }

   public PersistentMapAsyncTX createPersistentMap(String name) throws PersistentStoreException {
      synchronized(this.maps) {
         PersistentMapAsyncTX pMap = (PersistentMapAsyncTX)this.maps.get(name);
         if (pMap == null) {
            PersistentStoreConnection keys = this.createConnectionInternal(name, (byte)1);
            PersistentStoreConnection values = this.createConnectionInternal(name + ".values", (byte)1);
            pMap = new PersistentMapImpl(keys, values);
            this.maps.put(name, pMap);
         }

         return (PersistentMapAsyncTX)pMap;
      }
   }

   public PersistentMapAsyncTX createPersistentMap(String name, ObjectHandler handler) throws PersistentStoreException {
      synchronized(this.maps) {
         PersistentMapAsyncTX pMap = (PersistentMapAsyncTX)this.maps.get(name);
         if (pMap == null) {
            PersistentStoreConnection keys = this.createConnectionInternal(name, (byte)1);
            PersistentStoreConnection values = this.createConnectionInternal(name + ".values", (byte)1);
            pMap = new PersistentMapImpl(keys, values, handler);
            this.maps.put(name, pMap);
         }

         return (PersistentMapAsyncTX)pMap;
      }
   }

   PersistentStoreIO.Cursor createCursor(int typeCode, int flags) throws PersistentStoreException {
      return this.ios.createCursor(typeCode, flags);
   }

   public PersistentStoreTransaction begin() {
      return new PersistentStoreTransactionImpl(this);
   }

   /** @deprecated */
   @Deprecated
   public void open(StoreWritePolicy wp) throws PersistentStoreException {
      HashMap config = new HashMap();
      config.put("SynchronousWritePolicy", wp);
      this.open(config);
   }

   public synchronized void open(HashMap config) throws PersistentStoreException {
      if (this.isOpen) {
         throw new PersistentStoreException(StoreLogger.logStoreAlreadyOpenLoggable(this.storeName));
      } else {
         ClassLoader ccl = Thread.currentThread().getContextClassLoader();
         Thread.currentThread().setContextClassLoader(PersistentStoreImpl.class.getClassLoader());

         try {
            this.recoverStoreConnections(config);
         } catch (PersistentStoreException var11) {
            Boolean openFailuresAreFatal = Boolean.TRUE;
            if (config.containsKey("OpenFailuresAreFatal")) {
               try {
                  openFailuresAreFatal = (Boolean)config.get("OpenFailuresAreFatal");
               } catch (Exception var10) {
                  openFailuresAreFatal = Boolean.TRUE;
                  if (StoreDebug.storeIOLogical.isDebugEnabled()) {
                     StoreDebug.storeIOLogical.debug("Bad value in config for PersistentStore.OPEN_FAILURES_ARE_FATAL; defaulting to TRUE", var10);
                  }
               }
            }

            PersistentStoreFatalException psfe;
            if (var11 instanceof PersistentStoreFatalException) {
               psfe = (PersistentStoreFatalException)var11;
            } else {
               psfe = new PersistentStoreFatalException(var11);
            }

            if (openFailuresAreFatal) {
               this.setFatalException(psfe);
            } else {
               if (StoreDebug.storeIOLogical.isDebugEnabled()) {
                  StoreDebug.storeIOLogical.debug("Non-fatal error while openeing store " + this.storeName + " mbean: " + this.mbean, var11);
               }

               StoreLogger.logNonFatalFailureWhileOpening(this.storeName);
               if (this.mbean != null) {
                  if (StoreDebug.storeIOLogical.isDebugEnabled()) {
                     StoreDebug.storeIOLogical.debug("Calling mbean.setHealthWarn()");
                  }

                  this.mbean.setHealthWarn(StoreLogger.logNonFatalFailureWhileOpeningLoggable(this.storeName).getMessage());
               }
            }

            throw psfe;
         } finally {
            Thread.currentThread().setContextClassLoader(ccl);
         }

         if (this.ios instanceof ReplicatedStoreIO) {
            this.daemonPollTimer = TimerManagerFactory.getTimerManagerFactory().getDefaultTimerManager().schedule(new DevicePollTimerListener(), (long)this.devicePollInterval, (long)this.devicePollInterval);
         }

      }
   }

   private void recoverStoreConnections(HashMap externalConfig) throws PersistentStoreException {
      if (StoreDebug.storeIOLogical.isDebugEnabled()) {
         StoreDebug.storeIOLogical.debug("PersistentStoreImpl.recoverStoreConnections");
      }

      if (externalConfig != null) {
         this.setConfigInternal(externalConfig);
      }

      int recovered = this.ios.open(externalConfig);
      this.statistics.setInitialObjectCount((long)recovered);
      ConnectionKey systemKey = new ConnectionKey("wlsystem", (byte)-1);
      this.systemConnection = new PersistentStoreConnectionImpl(systemKey, this, 0);
      PersistentStoreConnection.Cursor cursor = this.systemConnection.createCursor(64);
      ArrayList deletedConnList = new ArrayList();

      for(PersistentStoreRecord rec = cursor.next(); rec != null; rec = cursor.next()) {
         ConnectionInfo connInfo = (ConnectionInfo)rec.getData();
         connInfo.setHandle((PersistentHandleImpl)rec.getHandle());
         if (connInfo.typeCode >= this.nextTypeCode) {
            this.nextTypeCode = connInfo.typeCode + 1;
         }

         ConnectionKey key = new ConnectionKey(connInfo.connectionName, connInfo.kind);
         connInfo.connection = new PersistentStoreConnectionImpl(key, this, connInfo.typeCode);
         int initialObjectCount = this.ios.getNumObjects(connInfo.typeCode);
         connInfo.connection.getStatisticsImpl().setInitialObjectCount((long)initialObjectCount);
         if (connInfo.getDeleted()) {
            DropRequest dr = new DropRequest(connInfo.connection, this.systemConnection, connInfo.getHandle());
            deletedConnList.add(dr);
         } else {
            this.registerConnection(connInfo.connection);
            this.connections.put(key, connInfo);
         }
      }

      if (this.wm == null) {
         Thread storeThread = new Thread(this);
         if (StoreDebug.storeIOLogical.isDebugEnabled()) {
            StoreDebug.storeIOLogical.debug("PersistentStoreImpl.recoverStoreConnections: workmanager undefined; starting daemon thread " + storeThread);
         }

         boolean useClientDaemon = false;
         if (KernelStatus.isEmbedded()) {
            useClientDaemon = true;
         } else {
            Object daemonValue = this.config.get("DaemonThreadInClientJVM");
            if (daemonValue != null) {
               useClientDaemon = (Boolean)daemonValue;
            }
         }

         storeThread.setDaemon(useClientDaemon);
         storeThread.start();
      } else {
         try {
            if (StoreDebug.storeIOLogical.isDebugEnabled()) {
               StoreDebug.storeIOLogical.debug("PersistentStoreImpl.recoverStoreConnections: using defined workmanager");
            }

            this.wm.schedule(new Work() {
               public void run() {
                  PersistentStoreImpl.this.run();
               }

               public boolean isDaemon() {
                  return true;
               }

               public void release() {
               }

               public String toString() {
                  return "weblogic.store." + PersistentStoreImpl.this.storeName;
               }
            });
         } catch (WorkException var11) {
            throw new AssertionError(var11);
         }
      }

      this.isOpen = true;
      Iterator iter = deletedConnList.iterator();

      while(iter.hasNext()) {
         this.schedule((DropRequest)iter.next());
      }

   }

   PersistentHandleImpl allocateHandle(int typeCode) {
      int handle = this.ios.allocateHandle(typeCode);
      if (handle == -1) {
         throw new AssertionError();
      } else {
         return new PersistentHandleImpl(typeCode, handle);
      }
   }

   boolean isHandleReadable(PersistentHandleImpl phi) {
      return this.ios.isHandleReadable(phi.getTypeCode(), phi.getStoreHandle());
   }

   void ensureHandleAllocated(PersistentHandleImpl phi) {
      this.ios.ensureHandleAllocated(phi.getTypeCode(), phi.getStoreHandle());
   }

   void releaseHandle(int typeCode, PersistentHandleImpl handle) {
      if (handle.getStoreHandle() != -1) {
         this.ios.releaseHandle(typeCode, handle.getStoreHandle());
      }

   }

   void schedule(StoreRequest sr) {
      boolean storeClosed = false;
      synchronized(this.pendingRequests) {
         if (this.isOpen) {
            this.pendingRequests.offer(sr);
         } else {
            storeClosed = true;
         }
      }

      if (storeClosed) {
         sr.handleError(new PersistentStoreException(StoreLogger.logStoreNotOpenLoggable(this.storeName)));
      }

   }

   void scheduleSelf(StoreRequest sr) {
      if (this.isOpen) {
         this.pendingRequests.offer(sr);
      }

   }

   void schedule(List newRequests, CompletionRequest cr) {
      if (newRequests.isEmpty()) {
         cr.setResult((Object)null);
      } else {
         StoreRequest first = (StoreRequest)newRequests.get(0);
         first.setCompletionRequest(cr);
         boolean storeClosed = false;
         synchronized(this.pendingRequests) {
            if (this.isOpen) {
               this.pendingRequests.offer(newRequests);
            } else {
               storeClosed = true;
            }
         }

         if (storeClosed) {
            first.handleError(new PersistentStoreException(StoreLogger.logStoreNotOpenLoggable(this.storeName)));
         }

      }
   }

   private ArrayList coalesceRequests(ArrayList requests) {
      HashMap handleMap = new HashMap(requests.size());
      Iterator i = requests.iterator();

      while(true) {
         while(true) {
            StoreRequest ri;
            while(i.hasNext()) {
               StoreRequest sr = (StoreRequest)i.next();
               StoreRequest tmp;
               switch (sr.getType()) {
                  case 2:
                     ri = (StoreRequest)handleMap.get(sr.getHandle());
                     if (ri == null) {
                        handleMap.put(sr.getHandle(), sr);
                        break;
                     }

                     ri.coalesce(sr);

                     for(tmp = ri; tmp.next != null; tmp = tmp.next) {
                     }

                     tmp.next = sr;
                     break;
                  case 3:
                     ri = (StoreRequest)handleMap.get(sr.getHandle());
                     if (ri == null) {
                        handleMap.put(sr.getHandle(), sr);
                        break;
                     }

                     ri.coalesce(sr);

                     for(tmp = ri; tmp.next != null && tmp.next.getType() != 2; tmp = tmp.next) {
                     }

                     sr.next = tmp.next;
                     tmp.next = sr;
                     break;
                  default:
                     ri = (StoreRequest)handleMap.put(sr.getHandle(), sr);
                     if (ri != null) {
                        ri.coalesce(sr);
                     }

                     sr.next = ri;
               }
            }

            int prevdel = -1;

            for(int i = 0; i < requests.size(); ++i) {
               ri = (StoreRequest)requests.get(i);
               if (ri.getType() == 4) {
                  ++prevdel;
                  requests.set(i, requests.get(prevdel));
                  requests.set(prevdel, ri);
               }
            }

            return requests;
         }
      }
   }

   ArrayList getOutstandingWork() {
      ArrayList requests = new ArrayList();
      Object next = null;

      while(next == null) {
         try {
            next = this.pendingRequests.take();
         } catch (InterruptedException var7) {
         }
      }

      while(next != this.shutdownTask) {
         if (next instanceof List) {
            requests.addAll((List)next);
         } else {
            requests.add(next);
         }

         next = this.pendingRequests.poll();
         if (next == null) {
            return this.coalesceRequests(requests);
         }
      }

      Exception closedEx = new PersistentStoreException(StoreLogger.logStoreNotOpenLoggable(this.storeName));
      Iterator i = requests.iterator();

      while(i.hasNext()) {
         StoreRequest sr = (StoreRequest)i.next();
         sr.handleError(closedEx);
      }

      try {
         this.closeSub();
         this.ios.close();
         this.shutdownTask.setResult(new Object());
      } catch (Throwable var6) {
         this.shutdownTask.setResult(var6);
      }

      return null;
   }

   private TransactionUnit coalesceOneRequest(Map handleMap, StoreRequest sr) {
      TransactionUnit unit = null;
      StoreRequest pending;
      if (sr.getType() != 3 && sr.getType() != 2) {
         pending = (StoreRequest)handleMap.put(sr.getHandle(), sr);
         if (pending != null) {
            unit = pending.getTransactionUnit();
            pending.coalesce(sr);
         }

         sr.next = pending;
      } else {
         pending = (StoreRequest)handleMap.get(sr.getHandle());
         if (pending == null) {
            handleMap.put(sr.getHandle(), sr);
         } else {
            unit = pending.getTransactionUnit();
            StoreRequest tmp = null;
            if (sr.getType() == 3) {
               if (pending.getType() == 2) {
                  handleMap.put(sr.getHandle(), sr);
                  sr.next = pending;
               } else {
                  pending.coalesce(sr);

                  for(tmp = pending; tmp.next != null && tmp.next.getType() != 2; tmp = tmp.next) {
                  }

                  sr.next = tmp.next;
                  tmp.next = sr;
               }
            } else {
               pending.coalesce(sr);

               for(tmp = pending; tmp.next != null; tmp = tmp.next) {
               }

               tmp.next = sr;
            }
         }
      }

      return unit;
   }

   private void linkTransactionUnit(StoreRequest sr, TransactionUnit thisUnit, TransactionUnit otherUnit) {
      if (otherUnit != null && otherUnit != thisUnit) {
         thisUnit.addLink(otherUnit);
         otherUnit.addLink(thisUnit);
      }

      sr.setTransactionUnit(thisUnit);
   }

   private void linkTopicUnit(StoreRequest sr, TransactionUnit thisUnit, Map topicFlushGroups) {
      if (sr.getFlushGroup() != -1L) {
         TransactionUnit topicUnit = (TransactionUnit)topicFlushGroups.get(sr.getFlushGroup());
         if (topicUnit == null) {
            topicUnit = new TransactionUnit();
            topicFlushGroups.put(sr.getFlushGroup(), topicUnit);
         }

         thisUnit.addLink(topicUnit);
         topicUnit.addLink(thisUnit);
      }

   }

   List getWorkInCoalesceSets() {
      Object next = null;

      while(next == null) {
         try {
            next = this.pendingRequests.take();
         } catch (InterruptedException var17) {
         }
      }

      List tuList = new ArrayList(100);
      int totalRequests = 0;

      StoreRequest sr;
      Iterator var23;
      while(next != this.shutdownTask) {
         if (next instanceof List) {
            List listOfReq = (List)next;
            tuList.add(new TransactionUnit(listOfReq));
            totalRequests += listOfReq.size();
         } else {
            tuList.add(new TransactionUnit((StoreRequest)next));
            ++totalRequests;
         }

         next = this.pendingRequests.poll();
         if (next == null) {
            Map handleRequestMap = new HashMap(totalRequests * 4 / 3 + 1);
            Map topicFlushGroups = new HashMap();
            Iterator var6 = tuList.iterator();

            while(true) {
               while(var6.hasNext()) {
                  TransactionUnit unit = (TransactionUnit)var6.next();
                  if (unit.getRequests() != null) {
                     List batch = unit.getRequests();
                     Iterator var27 = batch.iterator();

                     while(var27.hasNext()) {
                        StoreRequest sr = (StoreRequest)var27.next();
                        TransactionUnit connectedUnit = this.coalesceOneRequest(handleRequestMap, sr);
                        this.linkTransactionUnit(sr, unit, connectedUnit);
                        this.linkTopicUnit(sr, unit, topicFlushGroups);
                     }
                  } else {
                     sr = unit.getOneRequest();
                     TransactionUnit connectedUnit = this.coalesceOneRequest(handleRequestMap, sr);

                     assert connectedUnit != unit;

                     this.linkTransactionUnit(sr, unit, connectedUnit);
                     this.linkTopicUnit(sr, unit, topicFlushGroups);
                  }
               }

               List allSets = new LinkedList();
               var23 = tuList.iterator();

               while(true) {
                  while(var23.hasNext()) {
                     TransactionUnit tu = (TransactionUnit)var23.next();
                     FlushUnit fu = tu.getFlushUnit();
                     if (fu == null) {
                        fu = new FlushUnit();
                        fu.addTransactionUnit(tu);
                        tu.setFlushUnit(fu);
                        Set links = tu.getLinks();
                        if (links != null) {
                           Queue nodeQueue = new LinkedList();
                           Iterator var12 = links.iterator();

                           while(var12.hasNext()) {
                              TransactionUnit oneLink = (TransactionUnit)var12.next();
                              if (oneLink.getFlushUnit() == null) {
                                 nodeQueue.add(oneLink);
                                 oneLink.setFlushUnit(fu);
                                 if (oneLink.isTopicUnit()) {
                                    fu.markTopicLoad();
                                 }
                              } else {
                                 assert false : "transaction unit already marked";
                              }
                           }

                           label129:
                           while(true) {
                              TransactionUnit iter;
                              do {
                                 if ((iter = (TransactionUnit)nodeQueue.poll()) == null) {
                                    break label129;
                                 }
                              } while(iter.getLinks() == null);

                              Iterator var32 = iter.getLinks().iterator();

                              while(var32.hasNext()) {
                                 TransactionUnit oneLink = (TransactionUnit)var32.next();
                                 FlushUnit oneFU = oneLink.getFlushUnit();
                                 if (oneFU == null) {
                                    nodeQueue.add(oneLink);
                                    oneLink.setFlushUnit(fu);
                                    if (oneLink.isTopicUnit()) {
                                       fu.markTopicLoad();
                                    }
                                 } else {
                                    assert oneFU == fu : "transaction unit is marked wrong";
                                 }
                              }
                           }
                        }

                        allSets.add(fu);
                     } else {
                        fu.addTransactionUnit(tu);
                     }
                  }

                  var23 = allSets.iterator();

                  while(var23.hasNext()) {
                     FlushUnit fu = (FlushUnit)var23.next();
                     fu.sortRequests();
                  }

                  return allSets;
               }
            }
         }
      }

      Exception closedEx = new PersistentStoreException(StoreLogger.logStoreNotOpenLoggable(this.storeName));
      Iterator var20 = tuList.iterator();

      while(true) {
         while(var20.hasNext()) {
            TransactionUnit unit = (TransactionUnit)var20.next();
            if (unit.getRequests() != null) {
               var23 = unit.getRequests().iterator();

               while(var23.hasNext()) {
                  sr = (StoreRequest)var23.next();
                  sr.handleError(closedEx);
               }
            } else {
               unit.getOneRequest().handleError(closedEx);
            }
         }

         try {
            this.closeSub();
            this.ios.close();
            this.shutdownTask.setResult(new Object());
         } catch (Throwable var16) {
            this.shutdownTask.setResult(var16);
         }

         return null;
      }
   }

   public void run() {
      if (this.ios.supportsAsyncIO()) {
         this.asynchronousFlush();
      } else {
         this.synchronousFlush();
      }

   }

   private void synchronousFlush() {
      while(true) {
         ArrayList ioWork = this.getOutstandingWork();
         if (ioWork == null) {
            return;
         }

         boolean requiresFlush = false;
         boolean isFailureFatal = true;
         Throwable error = null;
         Iterator var5 = ioWork.iterator();

         while(var5.hasNext()) {
            StoreRequest req = (StoreRequest)var5.next();
            req.doTheIO(this.ios);
            req.finishIO();
            requiresFlush |= req.requiresFlush();
            error = req.getError();
            if (error != null) {
               if (req instanceof ReadRequest) {
                  isFailureFatal = req.isRequestFailureFatal();
                  if (StoreDebug.storeIOLogical.isDebugEnabled()) {
                     StoreDebug.storeIOLogical.debug("synchronousFlush: isFailureFatal " + isFailureFatal);
                  }
               }
               break;
            }
         }

         PersistentStoreFatalException assignFatalException = null;
         if (error == null && requiresFlush) {
            if (StoreDebug.storeIOLogical.isDebugEnabled()) {
               StoreDebug.storeIOLogical.debug("synchronousFlush: error == null && requiresFlush");
            }

            PersistentStoreFatalException fatalPse;
            try {
               this.ios.flush();
               this.statistics.incrementPhysicalWriteCount();
            } catch (PersistentStoreFatalException var9) {
               assignFatalException = var9;
               this.setFatalException(var9);
               error = var9;
            } catch (PersistentStoreException var10) {
               error = var10;
            } catch (Error var11) {
               fatalPse = new PersistentStoreFatalException(new PersistentStoreException(var11.toString(), var11));
               assignFatalException = fatalPse;
               this.setFatalException(fatalPse);
               error = fatalPse;
            } catch (RuntimeException var12) {
               fatalPse = new PersistentStoreFatalException(new PersistentStoreException(var12.toString(), var12));
               assignFatalException = fatalPse;
               this.setFatalException(fatalPse);
               error = fatalPse;
            }
         }

         if (error != null && assignFatalException == null && isFailureFatal) {
            if (StoreDebug.storeIOLogical.isDebugEnabled()) {
               StoreDebug.storeIOLogical.debug("synchronousFlush: error != null && assignFatalException == null");
            }

            synchronized(this) {
               if (this.fatalException == null) {
                  if (error instanceof PersistentStoreTestException) {
                     PersistentStoreTestException pste = (PersistentStoreTestException)error;
                     if (StoreDebug.storeIOLogical.isDebugEnabled()) {
                        StoreDebug.storeIOLogical.debug("synchronousFlush: PersistentStoreTestException: " + pste.toString());
                     }

                     if (pste.shouldFailOnFlush()) {
                        assignFatalException = new PersistentStoreFatalException((PersistentStoreException)error);
                     }
                  } else if (error instanceof PersistentStoreFatalException) {
                     assignFatalException = (PersistentStoreFatalException)error;
                  } else if (error instanceof PersistentStoreException) {
                     assignFatalException = new PersistentStoreFatalException((PersistentStoreException)error);
                  } else {
                     assignFatalException = new PersistentStoreFatalException(new PersistentStoreException((Throwable)error));
                  }
               }
            }

            if (StoreDebug.storeIOLogical.isDebugEnabled()) {
               StoreDebug.storeIOLogical.debug("synchronousFlush: assignFatalException: " + (assignFatalException == null ? "null" : assignFatalException.getMessage()));
            }

            if (assignFatalException != null) {
               this.setFatalException(assignFatalException);
            }
         }

         if (StoreDebug.storeIOLogical.isDebugEnabled()) {
            StoreDebug.storeIOLogical.debug("synchronousFlush: for all work: " + (error != null ? "assigning error" : "handle results"));
         }

         Iterator var15 = ioWork.iterator();

         while(var15.hasNext()) {
            StoreRequest req = (StoreRequest)var15.next();
            if (error != null) {
               req.handleError((Throwable)error);
            } else {
               req.handleResult();
            }
         }
      }
   }

   private void asynchronousFlush() {
      int chunkSize = this.ios.getPreferredFlushLoadSize();
      int workerCount = this.ios.getWorkerCount();
      int loadDetector = 0;
      int[] loadDistribution = new int[100];
      int loadIndex = 0;
      boolean workerFlush = false;

      while(true) {
         label199:
         while(true) {
            List allCoalesceSets = this.getWorkInCoalesceSets();
            if (allCoalesceSets == null) {
               return;
            }

            int totalLoads = 0;
            List allWorks = new ArrayList(workerCount);
            List topicLoads = new LinkedList();
            Iterator var11 = allCoalesceSets.iterator();

            while(true) {
               FlushUnit fu;
               int index;
               Iterator var14;
               do {
                  if (!var11.hasNext()) {
                     int maxCount = Math.max(workerCount, topicLoads.size());
                     int[] allLoads = new int[maxCount];
                     index = 0;

                     int c;
                     for(var14 = topicLoads.iterator(); var14.hasNext(); allLoads[index++] = c) {
                        c = (Integer)var14.next();
                     }

                     index = 0;
                     var14 = allCoalesceSets.iterator();

                     while(true) {
                        Iterator var17;
                        FlushUnit fu;
                        do {
                           if (!var14.hasNext()) {
                              if (allWorks.size() >= 2 && totalLoads >= chunkSize) {
                                 if (loadDistribution[loadIndex] == 0) {
                                    ++loadDetector;
                                    loadDistribution[loadIndex] = 1;
                                 }
                              } else if (loadDistribution[loadIndex] == 1) {
                                 --loadDetector;
                                 loadDistribution[loadIndex] = 0;
                              }

                              loadIndex = (loadIndex + 1) % 100;
                              if (loadDetector >= 80 && !workerFlush) {
                                 workerFlush = true;
                              } else if (loadDetector <= 20 && workerFlush) {
                                 workerFlush = false;
                              }

                              boolean fatalExceptionHappened;
                              if (!this.enforceWorkerFlush && !workerFlush && this.ios.isIdle()) {
                                 fatalExceptionHappened = false;
                                 Throwable error = null;
                                 Iterator var37 = allWorks.iterator();

                                 List work;
                                 Iterator var36;
                                 StoreRequest req;
                                 while(var37.hasNext()) {
                                    work = (List)var37.next();
                                    var36 = work.iterator();

                                    while(var36.hasNext()) {
                                       req = (StoreRequest)var36.next();
                                       req.doTheIO(this.ios);
                                       req.finishIO();
                                       fatalExceptionHappened |= req.requiresFlush();
                                       error = req.getError();
                                       if (error != null) {
                                          break;
                                       }
                                    }

                                    if (error != null) {
                                       break;
                                    }
                                 }

                                 if (error == null && fatalExceptionHappened) {
                                    try {
                                       this.ios.flush();
                                       this.statistics.incrementPhysicalWriteCount();
                                    } catch (PersistentStoreFatalException var21) {
                                       this.setFatalException(var21);
                                       error = var21;
                                    } catch (PersistentStoreException var22) {
                                       error = var22;
                                    }
                                 }

                                 try {
                                    var37 = allWorks.iterator();

                                    while(var37.hasNext()) {
                                       work = (List)var37.next();
                                       var36 = work.iterator();

                                       while(var36.hasNext()) {
                                          req = (StoreRequest)var36.next();
                                          if (error != null) {
                                             req.handleError((Throwable)error);
                                          } else {
                                             req.handleResult();
                                          }
                                       }
                                    }
                                    continue label199;
                                 } catch (IllegalStateException var25) {
                                    throw var25;
                                 }
                              }

                              fatalExceptionHappened = false;
                              boolean requiresFlush = false;
                              Throwable error = null;
                              var17 = allWorks.iterator();

                              while(true) {
                                 Iterator var19;
                                 StoreRequest req;
                                 List work;
                                 while(true) {
                                    if (!var17.hasNext()) {
                                       continue label199;
                                    }

                                    work = (List)var17.next();
                                    if (fatalExceptionHappened) {
                                       break;
                                    }

                                    requiresFlush = false;
                                    error = null;
                                    var19 = work.iterator();

                                    while(var19.hasNext()) {
                                       req = (StoreRequest)var19.next();
                                       req.doTheIO(this.ios);
                                       req.finishIO();
                                       requiresFlush |= req.requiresFlush();
                                       error = req.getError();
                                       if (error != null) {
                                          break;
                                       }
                                    }

                                    if (error != null || !requiresFlush) {
                                       break;
                                    }

                                    try {
                                       this.ios.flush(new LoadCompletionListener(work));
                                       this.statistics.incrementPhysicalWriteCount();
                                    } catch (PersistentStoreFatalException var23) {
                                       this.setFatalException(var23);
                                       error = var23;
                                       fatalExceptionHappened = true;
                                       break;
                                    } catch (PersistentStoreException var24) {
                                       error = var24;
                                       break;
                                    }
                                 }

                                 var19 = work.iterator();

                                 while(var19.hasNext()) {
                                    req = (StoreRequest)var19.next();
                                    if (error != null) {
                                       req.handleError((Throwable)error);
                                    } else {
                                       req.handleResult();
                                    }
                                 }
                              }
                           }

                           fu = (FlushUnit)var14.next();
                        } while(fu.hasTopicLoad());

                        int thisLoad = 0;
                        var17 = fu.getRequests().iterator();

                        while(var17.hasNext()) {
                           StoreRequest sr = (StoreRequest)var17.next();
                           if (!sr.isIOFinished()) {
                              ++thisLoad;
                           }
                        }

                        totalLoads += thisLoad;
                        if (allWorks.size() <= index) {
                           allWorks.add(fu.getRequests());
                        } else {
                           ((List)allWorks.get(index)).addAll(fu.getRequests());
                        }

                        allLoads[index] += thisLoad;
                        if (allLoads[index] >= chunkSize) {
                           index = (index + 1) % maxCount;
                        }
                     }
                  }

                  fu = (FlushUnit)var11.next();
               } while(!fu.hasTopicLoad());

               index = 0;
               var14 = fu.getRequests().iterator();

               while(var14.hasNext()) {
                  StoreRequest sr = (StoreRequest)var14.next();
                  if (!sr.isIOFinished()) {
                     ++index;
                  }
               }

               topicLoads.add(index);
               allWorks.add(fu.getRequests());
               totalLoads += index;
            }
         }
      }
   }

   public StoreStatistics getStatistics() {
      return this.statistics;
   }

   StoreStatisticsImpl getStatisticsImpl() {
      return this.statistics;
   }

   public String getName() {
      return this.storeName;
   }

   public String getShortName() {
      return this.storeShortName;
   }

   void delete(PersistentStoreConnectionImpl conn) throws PersistentStoreException {
      this.checkOpen();
      ConnectionInfo connectionInfo = null;
      PersistentHandleImpl handle;
      synchronized(this.connections) {
         ConnectionKey key = conn.getKey();
         connectionInfo = (ConnectionInfo)this.connections.remove(key);
         if (connectionInfo == null) {
            return;
         }

         handle = connectionInfo.getHandle();
      }

      this.unregisterConnection(conn);
      connectionInfo.setDeleted();
      PersistentStoreTransaction ptx = this.begin();
      this.systemConnection.update(ptx, handle, connectionInfo, 0);
      ptx.commit();
      this.schedule(new DropRequest(conn, this.systemConnection, handle));
   }

   public void unregisterStoreMBean() throws PersistentStoreException {
      if (this.mbean != null && this.adminHandler != null) {
         this.adminHandler.unregisterStoreMBean(this.mbean);
      }

   }

   public void close() throws PersistentStoreException {
      this.unregisterStoreMBean();
      synchronized(this.pendingRequests) {
         if (!this.isOpen) {
            return;
         }

         this.isOpen = false;
         this.pendingRequests.offer(this.shutdownTask);
      }

      if (this.daemonPollTimer != null) {
         this.daemonPollTimer.cancel();
      }

      this.ios.prepareToClose();
      Throwable shutdownException = null;
      boolean var63 = false;

      Iterator i;
      ConnectionInfo connInfo;
      label1145: {
         try {
            var63 = true;
            this.shutdownTask.getResult();
            var63 = false;
            break label1145;
         } catch (Throwable var73) {
            shutdownException = var73;
            var63 = false;
         } finally {
            if (var63) {
               this.shutdownTask.reset();

               try {
                  synchronized(this.connections) {
                     Iterator i = this.connections.values().iterator();

                     while(i.hasNext()) {
                        ConnectionInfo connInfo = (ConnectionInfo)i.next();
                        if (connInfo.getConnection() != null) {
                           this.unregisterConnection(connInfo.getConnection());
                        }
                     }
                  }
               } catch (Throwable var65) {
                  if (shutdownException == null) {
                     shutdownException = var65;
                  }
               } finally {
                  if (this.connections != null) {
                     this.connections.clear();
                  }

                  if (this.maps != null) {
                     this.maps.clear();
                  }

                  if (shutdownException instanceof PersistentStoreException) {
                     throw (PersistentStoreException)shutdownException;
                  }

                  if (shutdownException != null) {
                     throw new PersistentStoreException(shutdownException);
                  }

               }

            }
         }

         this.shutdownTask.reset();

         try {
            synchronized(this.connections) {
               i = this.connections.values().iterator();

               while(i.hasNext()) {
                  connInfo = (ConnectionInfo)i.next();
                  if (connInfo.getConnection() != null) {
                     this.unregisterConnection(connInfo.getConnection());
                  }
               }

               return;
            }
         } catch (Throwable var68) {
            if (shutdownException == null) {
               shutdownException = var68;
            }

            return;
         } finally {
            if (this.connections != null) {
               this.connections.clear();
            }

            if (this.maps != null) {
               this.maps.clear();
            }

            if (shutdownException instanceof PersistentStoreException) {
               throw (PersistentStoreException)shutdownException;
            }

            if (shutdownException != null) {
               throw new PersistentStoreException(shutdownException);
            }

         }
      }

      this.shutdownTask.reset();

      try {
         synchronized(this.connections) {
            i = this.connections.values().iterator();

            while(i.hasNext()) {
               connInfo = (ConnectionInfo)i.next();
               if (connInfo.getConnection() != null) {
                  this.unregisterConnection(connInfo.getConnection());
               }
            }
         }
      } catch (Throwable var71) {
         if (shutdownException == null) {
            shutdownException = var71;
         }
      } finally {
         if (this.connections != null) {
            this.connections.clear();
         }

         if (this.maps != null) {
            this.maps.clear();
         }

         if (shutdownException instanceof PersistentStoreException) {
            throw (PersistentStoreException)shutdownException;
         }

         if (shutdownException != null) {
            throw new PersistentStoreException(shutdownException);
         }

      }

   }

   static boolean checkName(String name) {
      int i = 0;

      while(i < name.length()) {
         char c = name.charAt(i);
         switch (c) {
            case '"':
            case '*':
            case ',':
            case ':':
            case '=':
            case '?':
               return false;
            default:
               ++i;
         }
      }

      return true;
   }

   final synchronized void checkOpen() throws PersistentStoreException {
      if (!this.isOpen) {
         throw new PersistentStoreException(StoreLogger.logStoreNotOpenLoggable(this.storeName));
      }
   }

   public Iterator getConnectionNames() {
      return this.getConnectionNamesInternal(0);
   }

   public Iterator getMapConnectionNames() {
      return this.getConnectionNamesInternal(1);
   }

   private Iterator getConnectionNamesInternal(int kind) {
      LinkedList list = new LinkedList();
      synchronized(this.connections) {
         Iterator i = this.connections.values().iterator();

         while(i.hasNext()) {
            ConnectionInfo connInfo = (ConnectionInfo)i.next();
            if (connInfo.kind == kind) {
               list.addFirst(connInfo.connectionName);
            }
         }

         return list.iterator();
      }
   }

   public Object getConfigValue(Object key) throws PersistentStoreException {
      synchronized(this) {
         this.checkOpen();
         return this.config.get(key);
      }
   }

   public void setConfigValue(Object key, Object value) throws PersistentStoreException {
      synchronized(this) {
         this.checkOpen();
         Object oldValue = this.config.get(key);
         if (oldValue == null) {
            if (value == null) {
               return;
            }
         } else if (oldValue.equals(value)) {
            return;
         }
      }

      HashMap newConfig = new HashMap(this.config);
      newConfig.put(key, value);
      StoreRequest sr = new ReopenRequest(this.systemConnection, newConfig);
      CompletionRequest cr = new CompletionRequest();
      this.schedule(Arrays.asList(sr), cr);

      try {
         cr.getResult();
      } catch (PersistentStoreException var7) {
         throw var7;
      } catch (RuntimeException var8) {
         throw var8;
      } catch (Throwable var9) {
         throw new AssertionError(var9);
      }
   }

   public void dump(XMLStreamWriter xsw) throws XMLStreamException {
      this.dump(xsw, true);
   }

   public void dump(XMLStreamWriter xsw, boolean complete) throws XMLStreamException {
      xsw.writeStartElement("PersistentStore");
      xsw.writeAttribute("Name", this.getName());
      xsw.writeAttribute("Open", "" + this.isOpen);
      xsw.writeStartElement("IOLayer");
      this.ios.dump(xsw);
      xsw.writeEndElement();
      xsw.writeStartElement("Statistics");
      xsw.writeStartElement("NumObjects");
      xsw.writeCharacters("" + this.statistics.getObjectCount());
      xsw.writeEndElement();
      xsw.writeStartElement("Creates");
      xsw.writeCharacters("" + this.statistics.getCreateCount());
      xsw.writeEndElement();
      xsw.writeStartElement("Reads");
      xsw.writeCharacters("" + this.statistics.getReadCount());
      xsw.writeEndElement();
      xsw.writeStartElement("Updates");
      xsw.writeCharacters("" + this.statistics.getUpdateCount());
      xsw.writeEndElement();
      xsw.writeStartElement("Deletes");
      xsw.writeCharacters("" + this.statistics.getDeleteCount());
      xsw.writeEndElement();
      xsw.writeStartElement("PhysicalWrites");
      xsw.writeCharacters("" + this.statistics.getPhysicalWriteCount());
      xsw.writeEndElement();
      xsw.writeStartElement("PhysicalReads");
      xsw.writeCharacters("" + this.statistics.getPhysicalReadCount());
      xsw.writeEndElement();
      xsw.writeEndElement();
      xsw.writeStartElement("Connections");
      if (complete) {
         Iterator i = this.connections.values().iterator();

         while(i.hasNext()) {
            ConnectionInfo connInfo = (ConnectionInfo)i.next();
            this.dumpConnectionInternal(xsw, connInfo, false);
         }

         xsw.writeEndElement();
         xsw.writeEndElement();
      }
   }

   public void dumpConnection(XMLStreamWriter xsw, String name, boolean dumpContents) throws XMLStreamException {
      this.checkAndDumpConnection(xsw, name, (byte)0, dumpContents);
   }

   public void dumpPersistentMap(XMLStreamWriter xsw, String name, boolean dumpContents) throws XMLStreamException {
      this.checkAndDumpConnection(xsw, name, (byte)1, dumpContents);
   }

   private void checkAndDumpConnection(XMLStreamWriter xsw, String connName, byte kind, boolean dumpContents) throws XMLStreamException {
      ConnectionKey connKey = new ConnectionKey(connName, kind);
      ConnectionInfo connInfo = (ConnectionInfo)this.connections.get(connKey);
      if (connInfo == null) {
         connInfo = new ConnectionInfo(-1, connName, kind);
      }

      this.dumpConnectionInternal(xsw, connInfo, dumpContents);
   }

   private void dumpConnectionInternal(XMLStreamWriter xsw, ConnectionInfo connInfo, boolean dumpContents) throws XMLStreamException {
      xsw.writeStartElement("Connection");
      xsw.writeAttribute("Name", connInfo.connectionName);
      String kind = connInfo.kind == 1 ? "map" : "normal";
      xsw.writeAttribute("Kind", kind);
      int typeCode = connInfo.typeCode;
      if (typeCode == -1) {
         xsw.writeStartElement("ErrorMessage");
         xsw.writeCharacters("Invalid or unknown connection");
         xsw.writeEndElement();
         xsw.writeEndElement();
      } else {
         xsw.writeAttribute("Typecode", "" + typeCode);
         OperationStatistics stats = connInfo.connection.getStatistics();
         xsw.writeStartElement("Statistics");
         xsw.writeStartElement("NumObjects");
         xsw.writeCharacters("" + stats.getObjectCount());
         xsw.writeEndElement();
         xsw.writeStartElement("Creates");
         xsw.writeCharacters("" + stats.getCreateCount());
         xsw.writeEndElement();
         xsw.writeStartElement("Reads");
         xsw.writeCharacters("" + stats.getReadCount());
         xsw.writeEndElement();
         xsw.writeStartElement("Updates");
         xsw.writeCharacters("" + stats.getUpdateCount());
         xsw.writeEndElement();
         xsw.writeStartElement("Deletes");
         xsw.writeCharacters("" + stats.getDeleteCount());
         xsw.writeEndElement();
         xsw.writeEndElement();
         if (VERBOSE_DIAGNOSTICS || dumpContents) {
            xsw.writeStartElement("Records");
            if (this.ios instanceof BaseStoreIO) {
               ((BaseStoreIO)this.ios).dump(xsw, typeCode, dumpContents);
            } else {
               this.ios.dump(xsw, typeCode);
            }

            xsw.writeEndElement();
         }

         xsw.writeEndElement();
      }
   }

   void setConfigInternal(HashMap config) {
      synchronized(this) {
         this.config.putAll(config);
      }
   }

   protected void closeSub() throws PersistentStoreException {
   }

   void healthFailed(PersistentStoreException exceptionToLog) {
      if (this.mbean != null) {
         this.mbean.setHealthFailed(exceptionToLog);
      }

   }

   void registerConnection(PersistentStoreConnection conn) throws PersistentStoreException {
      if (this.adminHandler != null) {
         this.adminHandler.registerConnectionMBean(this.mbean, conn);
      }

   }

   void unregisterConnection(PersistentStoreConnection conn) throws PersistentStoreException {
      if (this.adminHandler != null) {
         this.adminHandler.unregisterConnectionMBean(this.mbean, conn);
      }

   }

   protected RuntimeHandler getRuntimeHandler() {
      return this.adminHandler;
   }

   static {
      String[] syncPackages = SYNC_DESERIALIZERS.split(":");
      String[] var1 = syncPackages;
      int var2 = syncPackages.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         String sp = var1[var3];
         syncDeserializerSet.add(sp);
      }

   }

   private final class DevicePollTimerListener implements NakedTimerListener {
      private DevicePollTimerListener() {
      }

      public void timerExpired(Timer timer) {
         if (PersistentStoreImpl.this.pendingRequests.isEmpty()) {
            PersistentStoreImpl.this.pendingRequests.offer(new PollRequest());
         }

      }

      // $FF: synthetic method
      DevicePollTimerListener(Object x1) {
         this();
      }
   }

   private static final class ConnectionInfo implements Serializable {
      private static final long serialVersionUID = 2566438085361406921L;
      private final int typeCode;
      private final byte kind;
      private final String connectionName;
      private boolean isDel;
      private transient PersistentHandleImpl handle;
      private transient PersistentStoreConnectionImpl connection;

      private ConnectionInfo(int typeCode, String connectionName, byte kind) {
         this.typeCode = typeCode;
         this.connectionName = connectionName;
         this.kind = kind;
      }

      private void setHandle(PersistentHandleImpl handle) {
         this.handle = handle;
      }

      private PersistentHandleImpl getHandle() {
         return this.handle;
      }

      private void setConnection(PersistentStoreConnectionImpl conn) {
         this.connection = conn;
      }

      private PersistentStoreConnectionImpl getConnection() {
         return this.connection;
      }

      public int hashCode() {
         return this.connectionName.hashCode();
      }

      public boolean equals(Object o) {
         if (!(o instanceof ConnectionInfo)) {
            return false;
         } else {
            ConnectionInfo other = (ConnectionInfo)o;
            return other.typeCode == this.typeCode && other.kind == this.kind && other.isDel == this.isDel && this.connectionName.equals(other.connectionName);
         }
      }

      public String toString() {
         return "Store connection " + this.typeCode + (this.kind == 1 ? " map, " : " default, ") + (this.isDel ? "(deleted) " : "") + this.connectionName.hashCode();
      }

      private synchronized boolean getDeleted() {
         return this.isDel;
      }

      private synchronized void setDeleted() {
         this.isDel = true;
      }

      // $FF: synthetic method
      ConnectionInfo(int x0, String x1, byte x2, Object x3) {
         this(x0, x1, x2);
      }
   }

   static final class ConnectionKey {
      private final String connectionName;
      private final byte kind;

      ConnectionKey(String connectionName, byte kind) {
         this.connectionName = connectionName;
         this.kind = kind;
      }

      String getName() {
         return this.connectionName;
      }

      public int hashCode() {
         return this.connectionName.hashCode() ^ this.kind;
      }

      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (!(o instanceof ConnectionKey)) {
            return false;
         } else {
            ConnectionKey other = (ConnectionKey)o;
            return this.kind == other.kind && this.connectionName.equals(other.connectionName);
         }
      }
   }

   private class LoadCompletionListener implements IOListener {
      private List ioWork;

      private LoadCompletionListener(List ioWork) {
         this.ioWork = ioWork;
      }

      public void ioCompleted(Object result) {
         Iterator var2 = this.ioWork.iterator();

         while(var2.hasNext()) {
            StoreRequest req = (StoreRequest)var2.next();
            if (result instanceof PersistentStoreFatalException) {
               PersistentStoreImpl.this.setFatalException((PersistentStoreFatalException)result);
               req.handleError((PersistentStoreFatalException)result);
            } else if (result instanceof Throwable) {
               req.handleError((Throwable)result);
            } else {
               try {
                  req.handleResult();
               } catch (IllegalStateException var5) {
                  throw var5;
               }
            }
         }

      }

      // $FF: synthetic method
      LoadCompletionListener(List x1, Object x2) {
         this(x1);
      }
   }

   private static class CallerClassUtil extends SecurityManager {
      private CallerClassUtil() {
      }

      private Class getCallerClass() {
         Class[] stack = this.getClassContext();
         int i = 2;

         for(Class cls = stack[i]; i < stack.length; cls = stack[i++]) {
            if (cls != this.getClass() && !cls.getName().startsWith("weblogic.store.")) {
               return cls;
            }
         }

         return stack[stack.length - 1];
      }

      // $FF: synthetic method
      CallerClassUtil(Object x0) {
         this();
      }
   }

   private static final class ReusableCompletionRequest {
      private Object result;

      private ReusableCompletionRequest() {
         this.result = this;
      }

      private synchronized void setResult(Object result) {
         this.result = result;
         this.notify();
      }

      private synchronized void getResult() throws Throwable {
         while(this.result == this) {
            try {
               this.wait();
            } catch (InterruptedException var2) {
               Thread.currentThread().interrupt();
               return;
            }
         }

         if (this.result instanceof Throwable) {
            throw (Throwable)this.result;
         }
      }

      private synchronized void reset() {
         this.result = this;
      }

      // $FF: synthetic method
      ReusableCompletionRequest(Object x0) {
         this();
      }
   }
}
