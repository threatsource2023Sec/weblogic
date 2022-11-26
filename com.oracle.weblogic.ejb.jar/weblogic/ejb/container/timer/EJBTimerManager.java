package weblogic.ejb.container.timer;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import javax.ejb.EJBException;
import javax.ejb.TimerConfig;
import weblogic.common.CompletionRequest;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.ejb.WLTimerInfo;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.dd.xml.DDUtils;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.BeanManager;
import weblogic.ejb.container.interfaces.DeploymentInfo;
import weblogic.ejb.container.interfaces.TimerHelper;
import weblogic.ejb.container.interfaces.TimerManager;
import weblogic.ejb.container.internal.MethodDescriptor;
import weblogic.ejb.container.monitoring.EJBTimerRuntimeMBeanImpl;
import weblogic.ejb.spi.WLDeploymentException;
import weblogic.management.runtime.EJBTimerRuntimeMBean;
import weblogic.store.PersistentHandle;
import weblogic.store.PersistentStoreConnection;
import weblogic.store.PersistentStoreException;
import weblogic.store.PersistentStoreManager;
import weblogic.store.PersistentStoreRecord;
import weblogic.store.PersistentStoreTransaction;
import weblogic.store.gxa.GXAException;
import weblogic.store.gxa.GXAOperation;
import weblogic.store.gxa.GXAOperationWrapper;
import weblogic.store.gxa.GXAResource;
import weblogic.store.gxa.GXATraceLogger;
import weblogic.store.gxa.GXATransaction;
import weblogic.store.gxa.GXid;
import weblogic.store.xa.PersistentStoreXA;
import weblogic.timers.ScheduleExpression;
import weblogic.timers.Timer;
import weblogic.timers.TimerManagerFactory;
import weblogic.work.WorkManagerFactory;

final class EJBTimerManager implements TimerManager {
   private static final DebugLogger debugLogger;
   private weblogic.timers.TimerManager timerManager;
   private final BeanManager beanManager;
   private final BeanInfo beanInfo;
   private EJBTimerRuntimeMBeanImpl timerRtMBean;
   private final Map pk2EJBTimerMap = new HashMap();
   private final Map id2EJBTimerMap = new HashMap();
   private final Map disabledTimers = new HashMap();
   private PersistentStoreXA persistentStore;
   private PersistentStoreConnection persistentConnection;
   private GXAResource gxa;
   private boolean isInitialized = false;
   private final AtomicLong timerIdCounter = new AtomicLong(0L);
   // $FF: synthetic field
   static final boolean $assertionsDisabled;
   static final long serialVersionUID = -5284596887979786840L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.ejb.container.timer.EJBTimerManager");
   static final DelegatingMonitor _WLDF$INST_FLD_EJB_Diagnostic_Timer_Manager_Around_High;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;
   static final JoinPoint _WLDF$INST_JPFLD_1;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_1;
   static final JoinPoint _WLDF$INST_JPFLD_2;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_2;
   static final JoinPoint _WLDF$INST_JPFLD_3;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_3;
   static final JoinPoint _WLDF$INST_JPFLD_4;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_4;
   static final JoinPoint _WLDF$INST_JPFLD_5;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_5;
   static final JoinPoint _WLDF$INST_JPFLD_6;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_6;

   EJBTimerManager(BeanManager beanManager) {
      this.beanManager = beanManager;
      this.beanInfo = beanManager.getBeanInfo();
   }

   public void setup(EJBTimerRuntimeMBean rtMBean) throws WLDeploymentException {
      this.timerRtMBean = (EJBTimerRuntimeMBeanImpl)rtMBean;

      try {
         this.timerManager = TimerManagerFactory.getTimerManagerFactory().getTimerManager(this.beanInfo.getFullyQualifiedName(), this.beanInfo.getDispatchPolicy());
         this.initializePersistentStore();
         Utils.register(this.beanInfo, TimerHelper.class, new TimerHelperImpl(this));
         Map persistedTimersMap = new HashMap();
         this.initializePersistedTimers(persistedTimersMap);
         TimerManagerHelper.initializeAutoCreatedTimers(this, this.beanInfo.getAutomaticTimerMDs(), persistedTimersMap);
      } catch (WLDeploymentException var3) {
         this.undeploy();
         throw var3;
      }
   }

   public EJBTimerRuntimeMBeanImpl getTimerRuntimeMBean() {
      return this.timerRtMBean;
   }

   public void disableTimer(TimerImpl ejbTimer) {
      if (ejbTimer.getTimer() != null) {
         ejbTimer.getTimer().cancel();
      }

      synchronized(this.disabledTimers) {
         this.disabledTimers.put(ejbTimer.getID(), ejbTimer);
         this.timerRtMBean.incrementDisabledTimerCount();
      }
   }

   public void enableDisabledTimers() {
      if (this.isInitialized) {
         synchronized(this.disabledTimers) {
            Iterator it = this.disabledTimers.values().iterator();

            while(it.hasNext()) {
               TimerImpl ejbTimer = (TimerImpl)it.next();
               if (!ejbTimer.isCancelled()) {
                  this.scheduleTimer(ejbTimer);
               }
            }

            this.disabledTimers.clear();
            if (this.timerRtMBean != null) {
               this.timerRtMBean.resetDisabledTimerCount();
            }

         }
      }
   }

   public void scheduleTimer(TimerImpl ejbTimer) {
      LocalHolder var7;
      if ((var7 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         InstrumentationSupport.preProcess(var7);
      }

      label198: {
         label199: {
            try {
               if (!this.isInitialized) {
                  if (debugLogger.isDebugEnabled()) {
                     debug("TimerManager not initialized, adding timer to disabledTimers");
                  }

                  synchronized(this.disabledTimers) {
                     if (!this.isInitialized) {
                        this.disabledTimers.put(ejbTimer.getID(), ejbTimer);
                        break label198;
                     }
                  }
               }

               Timer timer;
               if (ejbTimer.__WL_isCalendarTimer()) {
                  ScheduleExpression exp = Utils.asWeblogicScheduleExpression(ejbTimer.__WL_getSchedule());
                  if (ejbTimer.isInRetryState()) {
                     Timer timerOrg = ejbTimer.getTimer();
                     if (timerOrg != null) {
                        timerOrg.cancel();
                        timerOrg = null;
                     }

                     Date nextExpire = ejbTimer.getNextExpiration(false);
                     if (nextExpire == null) {
                        Calendar now = Calendar.getInstance();
                        now.add(13, 1);
                        nextExpire = now.getTime();
                     }

                     timer = this.timerManager.schedule(ejbTimer, nextExpire);
                  } else {
                     exp.setFirstTimeout(ejbTimer.getTimerData().getNextExpiration());
                     timer = this.timerManager.schedule(ejbTimer, exp);
                  }

                  ejbTimer.setTimer(timer);
                  if (!ejbTimer.isInRetryState() && ejbTimer.getNextExpiration(false) == null) {
                     ejbTimer.__WL_cancel();
                     break label199;
                  }
               } else if (!ejbTimer.isIntervalTimer()) {
                  if (!$assertionsDisabled && ejbTimer.getTimer() != null && ejbTimer.getTimer().cancel()) {
                     throw new AssertionError("cancel succeeded unexpectedly");
                  }

                  timer = this.timerManager.schedule(ejbTimer, ejbTimer.getNextExpiration(false));
                  ejbTimer.setTimer(timer);
               } else {
                  timer = ejbTimer.getTimer();
                  if (timer != null) {
                     timer.cancel();
                  }

                  ejbTimer.accountForSkippedIntervals();
                  timer = this.timerManager.scheduleAtFixedRate(ejbTimer, ejbTimer.getNextExpiration(false), ejbTimer.getTimerData().getIntervalDuration());
                  ejbTimer.setTimer(timer);
               }

               this.addTimerToMaps(ejbTimer);
            } catch (Throwable var11) {
               if (var7 != null) {
                  var7.th = var11;
                  InstrumentationSupport.postProcess(var7);
               }

               throw var11;
            }

            if (var7 != null) {
               InstrumentationSupport.postProcess(var7);
            }

            return;
         }

         if (var7 != null) {
            InstrumentationSupport.postProcess(var7);
         }

         return;
      }

      if (var7 != null) {
         InstrumentationSupport.postProcess(var7);
      }

   }

   public javax.ejb.Timer createTimer(Object pk, Date initialExpiration, long intervalDuration, TimerConfig conf, WLTimerInfo wlti) {
      LocalHolder var8;
      if ((var8 = LocalHolder.getInstance(_WLDF$INST_JPFLD_1, _WLDF$INST_JPFLD_JPMONS_1)) != null) {
         InstrumentationSupport.preProcess(var8);
      }

      TimerImpl var10000;
      try {
         if (debugLogger.isDebugEnabled()) {
            debug("Creating timer with pk: " + pk + " expiration: " + initialExpiration + " duration: " + intervalDuration + " info: " + (conf == null ? null : conf.getInfo()));
         }

         TimerImpl ejbTimer = new TimerImpl(this, this.beanManager, pk, conf, this.beanInfo.getEjbTimeoutMethodDescriptor(), initialExpiration, intervalDuration, this.nextTimerId(), wlti);
         this.addNewTimer(ejbTimer);
         var10000 = ejbTimer;
      } catch (Throwable var10) {
         if (var8 != null) {
            var8.th = var10;
            var8.ret = null;
            InstrumentationSupport.postProcess(var8);
         }

         throw var10;
      }

      if (var8 != null) {
         var8.ret = var10000;
         InstrumentationSupport.postProcess(var8);
      }

      return var10000;
   }

   private boolean ejbTimeoutMethodRequiresTransaction() {
      return this.beanInfo.getEjbTimeoutMethodDescriptor() != null && this.beanInfo.getEjbTimeoutMethodDescriptor().requiresTransaction();
   }

   public javax.ejb.Timer createTimer(Object pk, Date expiration, TimerConfig conf, WLTimerInfo wlti) {
      LocalHolder var6;
      if ((var6 = LocalHolder.getInstance(_WLDF$INST_JPFLD_2, _WLDF$INST_JPFLD_JPMONS_2)) != null) {
         InstrumentationSupport.preProcess(var6);
      }

      TimerImpl var10000;
      try {
         if (debugLogger.isDebugEnabled()) {
            debug("Creating timer with pk: " + pk + " expiration: " + expiration + " info: " + (conf == null ? null : conf.getInfo()));
         }

         TimerImpl ejbTimer = new TimerImpl(this, this.beanManager, pk, conf, this.beanInfo.getEjbTimeoutMethodDescriptor(), expiration, -1L, this.nextTimerId(), wlti);
         this.addNewTimer(ejbTimer);
         var10000 = ejbTimer;
      } catch (Throwable var8) {
         if (var6 != null) {
            var6.th = var8;
            var6.ret = null;
            InstrumentationSupport.postProcess(var6);
         }

         throw var8;
      }

      if (var6 != null) {
         var6.ret = var10000;
         InstrumentationSupport.postProcess(var6);
      }

      return var10000;
   }

   public javax.ejb.Timer createTimer(Object pk, long initialDuration, long intervalDuration, TimerConfig conf, WLTimerInfo wlti) {
      LocalHolder var9;
      if ((var9 = LocalHolder.getInstance(_WLDF$INST_JPFLD_3, _WLDF$INST_JPFLD_JPMONS_3)) != null) {
         InstrumentationSupport.preProcess(var9);
      }

      javax.ejb.Timer var10000;
      try {
         Date initialExpiration = new Date(System.currentTimeMillis() + initialDuration);
         var10000 = this.createTimer(pk, initialExpiration, intervalDuration, conf, wlti);
      } catch (Throwable var11) {
         if (var9 != null) {
            var9.th = var11;
            var9.ret = null;
            InstrumentationSupport.postProcess(var9);
         }

         throw var11;
      }

      if (var9 != null) {
         var9.ret = var10000;
         InstrumentationSupport.postProcess(var9);
      }

      return var10000;
   }

   public javax.ejb.Timer createTimer(Object pk, long duration, TimerConfig tc, WLTimerInfo wlti) {
      LocalHolder var7;
      if ((var7 = LocalHolder.getInstance(_WLDF$INST_JPFLD_4, _WLDF$INST_JPFLD_JPMONS_4)) != null) {
         InstrumentationSupport.preProcess(var7);
      }

      javax.ejb.Timer var10000;
      try {
         Date expiration = new Date(System.currentTimeMillis() + duration);
         var10000 = this.createTimer(pk, expiration, tc, wlti);
      } catch (Throwable var9) {
         if (var7 != null) {
            var7.th = var9;
            var7.ret = null;
            InstrumentationSupport.postProcess(var7);
         }

         throw var9;
      }

      if (var7 != null) {
         var7.ret = var10000;
         InstrumentationSupport.postProcess(var7);
      }

      return var10000;
   }

   public javax.ejb.Timer createTimer(Object pk, javax.ejb.ScheduleExpression schedule, TimerConfig tc) {
      LocalHolder var4;
      if ((var4 = LocalHolder.getInstance(_WLDF$INST_JPFLD_5, _WLDF$INST_JPFLD_JPMONS_5)) != null) {
         InstrumentationSupport.preProcess(var4);
      }

      javax.ejb.Timer var10000;
      try {
         var10000 = this.createTimer(pk, schedule, tc, this.ejbTimeoutMethodRequiresTransaction(), false, this.beanInfo.getEjbTimeoutMethodDescriptor());
      } catch (Throwable var6) {
         if (var4 != null) {
            var4.th = var6;
            var4.ret = null;
            InstrumentationSupport.postProcess(var4);
         }

         throw var6;
      }

      if (var4 != null) {
         var4.ret = var10000;
         InstrumentationSupport.postProcess(var4);
      }

      return var10000;
   }

   public javax.ejb.Timer createTimer(Object pk, javax.ejb.ScheduleExpression schedule, TimerConfig timerConfig, boolean isTransactional, boolean isAutoCreated, MethodDescriptor callbackMethodDesc) {
      LocalHolder var8;
      if ((var8 = LocalHolder.getInstance(_WLDF$INST_JPFLD_6, _WLDF$INST_JPFLD_JPMONS_6)) != null) {
         InstrumentationSupport.preProcess(var8);
      }

      TimerImpl var10000;
      try {
         if (debugLogger.isDebugEnabled()) {
            debug("Creating timer with pk: " + pk + " schedule: " + schedule + " timerConfig: " + timerConfig);
         }

         TimerImpl ejbTimer = new TimerImpl(this, this.beanManager, pk, isTransactional, schedule, timerConfig, this.nextTimerId(), isAutoCreated, DDUtils.getMethodSignature(callbackMethodDesc.getMethod()));
         this.addNewTimer(ejbTimer);
         var10000 = ejbTimer;
      } catch (Throwable var10) {
         if (var8 != null) {
            var8.th = var10;
            var8.ret = null;
            InstrumentationSupport.postProcess(var8);
         }

         throw var10;
      }

      if (var8 != null) {
         var8.ret = var10000;
         InstrumentationSupport.postProcess(var8);
      }

      return var10000;
   }

   public Collection getTimers(Object pk) {
      Set timers = null;
      synchronized(this.pk2EJBTimerMap) {
         timers = (Set)this.pk2EJBTimerMap.get(pk);
         HashSet timers;
         if (timers == null) {
            timers = new HashSet();
         } else {
            timers = new HashSet(timers);
         }

         return timers;
      }
   }

   public Collection getAllTimers() {
      synchronized(this.id2EJBTimerMap) {
         return new HashSet(this.id2EJBTimerMap.values());
      }
   }

   public void removeTimersForPK(Object pk) {
      Iterator var2 = this.getTimers(pk).iterator();

      while(var2.hasNext()) {
         javax.ejb.Timer timer = (javax.ejb.Timer)var2.next();
         ((TimerImpl)timer).remove();
      }

   }

   public static void removeAllTimers(BeanInfo bi) {
      if (debugLogger.isDebugEnabled()) {
         debug("Removing all EJB Timers from store for EJB: " + bi.getDisplayName());
      }

      try {
         PersistentStoreManager storeManager = PersistentStoreManager.getManager();
         String storeName = bi.getTimerStoreName();
         PersistentStoreXA persistentStore = null;
         if (storeName == null) {
            persistentStore = (PersistentStoreXA)storeManager.getEjbTimerStore();
            if (persistentStore == null) {
               return;
            }
         } else {
            if (!storeManager.storeExistsByLogicalName(storeName)) {
               return;
            }

            persistentStore = (PersistentStoreXA)storeManager.getStoreByLogicalName(storeName);
         }

         String key = getStoreConnectionKey(bi);
         persistentStore.createConnection(key).delete();
      } catch (Exception var5) {
         if (debugLogger.isDebugEnabled()) {
            debug("Error removing EJB Timer store objects", var5);
         }

         EJBLogger.logErrorRemovingEJBTimersFromStore(bi.getDisplayName(), var5);
      }

   }

   private void addNewTimer(TimerImpl timer) {
      try {
         GXATransaction gxaTx = this.gxa.enlist();
         if (gxaTx != null) {
            TimerCreationOperation tco = new TimerCreationOperation(timer, this.persistentConnection);
            this.gxa.addNewOperation(gxaTx, tco);
            timer.setState(4);
         } else {
            PersistentStoreTransaction tx = this.persistentStore.begin();
            if (timer.__WL_isPersistent()) {
               timer.setPersistentHandle(this.persistentConnection.create(tx, timer.getTimerData(), 0));
            }

            tx.commit();
            timer.finalizeCreate();
         }
      } catch (Exception var4) {
         EJBException ee = new EJBException("Error creating Timer.", var4);
         ee.initCause(var4);
         throw ee;
      }

      if (!timer.isCancelled()) {
         this.addTimerToMaps(timer);
      }

   }

   public javax.ejb.Timer getTimer(Long timerID) {
      synchronized(this.id2EJBTimerMap) {
         return (javax.ejb.Timer)this.id2EJBTimerMap.get(timerID);
      }
   }

   public void removePersistentStoreEntry(TimerImpl timer) throws PersistentStoreException {
      if (timer.__WL_isPersistent()) {
         PersistentStoreTransaction tx = this.persistentStore.begin();
         this.persistentConnection.delete(tx, timer.getPersistentHandle(), 0);
         tx.commit();
      }
   }

   public void updatePersistentStoreEntry(TimerImpl timer) throws PersistentStoreException {
      if (timer.__WL_isPersistent()) {
         PersistentStoreTransaction tx = this.persistentStore.begin();
         this.persistentConnection.update(tx, timer.getPersistentHandle(), timer.getTimerData(), 0);
         tx.commit();
      }
   }

   public boolean registerTimerExpirationOperation(TimerImpl timer) throws GXAException {
      GXATransaction gxaTx = this.gxa.enlist();
      if (gxaTx != null) {
         TimerExpirationOperation teo = new TimerExpirationOperation(timer, this, this.persistentConnection, this.beanInfo.getModuleClassLoader());
         this.gxa.addNewOperation(gxaTx, teo);
         if (debugLogger.isDebugEnabled()) {
            debug("Created TimerExpirationOperation: " + teo);
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean registerTimerCancellationOperation(TimerImpl timer) throws GXAException {
      GXATransaction gxaTx = this.gxa.enlist();
      if (gxaTx != null) {
         TimerCancellationOperation tco = new TimerCancellationOperation(timer, this, this.persistentConnection);
         this.gxa.addNewOperation(gxaTx, tco);
         return true;
      } else {
         return false;
      }
   }

   private void initializePersistedTimers(Map persistedTimersMap) throws WLDeploymentException {
      try {
         long highID = 0L;
         Set prepareRecords = null;
         PersistentStoreConnection.Cursor cursor = this.persistentConnection.createCursor(0);
         boolean serializationError = false;

         PersistentStoreRecord rec;
         while((rec = cursor.next()) != null) {
            Object data = null;

            try {
               data = rec.getData();
            } catch (PersistentStoreException var13) {
               if (serializationError) {
                  continue;
               }

               Throwable th = var13.getCause();
               if (th == null) {
                  th = var13;
               }

               EJBLogger.logEJBTimerSerializationError(this.beanInfo.getDisplayName(), ((Throwable)th).toString());
               serializationError = true;
               continue;
            }

            if (data instanceof TimerData) {
               TimerData td = (TimerData)data;
               TimerImpl timer = new TimerImpl(this, this.beanManager, td);
               timer.setPersistentHandle(rec.getHandle());
               timer.setState(1);
               this.addTimerToMaps(timer);
               Long id = timer.getID();
               if (this.disabledTimers.containsKey(id)) {
                  List timers = new ArrayList();
                  timers.add(this.disabledTimers.get(id));
                  timers.add(timer);
                  this.disabledTimers.put(id, timers);
               } else {
                  this.disabledTimers.put(id, timer);
               }

               if (timer.getID() > highID) {
                  highID = timer.getID();
               }

               if (td.isAutoCreated()) {
                  TimerConfig timerConfig = new TimerConfig(td.getInfo(), true);
                  persistedTimersMap.put(new AbstractMap.SimpleEntry(td.getTimerSchedule(), timerConfig), timer);
               }
            } else {
               if (!(data instanceof PrepareRecord)) {
                  throw new AssertionError("Unexpected object in EJB timer store!");
               }

               PrepareRecord pr = (PrepareRecord)data;
               pr.handle = rec.getHandle();
               if (prepareRecords == null) {
                  prepareRecords = new HashSet();
               }

               prepareRecords.add(pr);
            }
         }

         this.timerIdCounter.set(++highID);
         if (prepareRecords != null) {
            this.handleRecoveredOperations(prepareRecords);
         }

      } catch (Exception var14) {
         if (debugLogger.isDebugEnabled()) {
            debug("Error initializing TimerService", var14);
         }

         throw new WLDeploymentException("Error starting Timer service", var14);
      }
   }

   private void handleRecoveredOperations(Set prepareRecords) {
      Iterator var2 = prepareRecords.iterator();

      while(var2.hasNext()) {
         PrepareRecord pr = (PrepareRecord)var2.next();
         Long timerID = pr.timerID;
         if (!$assertionsDisabled && !this.disabledTimers.containsKey(timerID)) {
            throw new AssertionError();
         }

         switch (pr.operation) {
            case 1:
               TimerImpl createTimer = (TimerImpl)this.disabledTimers.remove(timerID);
               createTimer.perhapsMarkCreatePending();
               TimerCreationOperation tco = new TimerCreationOperation(createTimer, this.persistentConnection);
               tco.recover(pr.xid, pr.handle);
               this.gxa.addRecoveredOperation(tco);
               break;
            case 2:
               TimerImpl cancelTimer = (TimerImpl)this.disabledTimers.remove(timerID);
               cancelTimer.setState(3);
               TimerCancellationOperation tdo = new TimerCancellationOperation(cancelTimer, this, this.persistentConnection);
               tdo.recover(pr.xid, pr.handle);
               this.gxa.addRecoveredOperation(tdo);
               break;
            case 3:
            case 4:
               TimerExpirationOperation teo = null;
               Object obj = this.disabledTimers.remove(timerID);
               if (obj instanceof List) {
                  List timers = (List)obj;
                  TimerImpl t1 = (TimerImpl)timers.get(0);
                  TimerImpl t2 = (TimerImpl)timers.get(1);
                  if (!$assertionsDisabled && !t1.isIntervalTimer()) {
                     throw new AssertionError();
                  }

                  if (t1.getNextExpiration(false).getTime() > t2.getNextExpiration(false).getTime()) {
                     this.removeTimerFromMaps(t2);
                     t1.setState(5);
                     teo = new TimerExpirationOperation(t1, this, this.persistentConnection, this.beanInfo.getModuleClassLoader());
                     teo.recover(pr.xid, pr.handle, t2.getPersistentHandle(), t1.getPersistentHandle());
                  } else {
                     this.removeTimerFromMaps(t1);
                     t2.setState(5);
                     teo = new TimerExpirationOperation(t2, this, this.persistentConnection, this.beanInfo.getModuleClassLoader());
                     teo.recover(pr.xid, pr.handle, t1.getPersistentHandle(), t2.getPersistentHandle());
                  }
               } else {
                  TimerImpl t = (TimerImpl)obj;
                  if (!$assertionsDisabled && t.isIntervalTimer()) {
                     throw new AssertionError();
                  }

                  if (pr.operation == 3) {
                     t.setState(5);
                  } else {
                     t.setState(6);
                  }

                  teo = new TimerExpirationOperation(t, this, this.persistentConnection, this.beanInfo.getModuleClassLoader());
                  teo.recover(pr.xid, pr.handle, t.getPersistentHandle(), (PersistentHandle)null);
               }

               this.gxa.addRecoveredOperation(teo);
               break;
            default:
               throw new AssertionError("Unknown type: " + pr.operation);
         }
      }

   }

   public void perhapsStart() {
      if (debugLogger.isDebugEnabled()) {
         debug("invoking perhapsStart");
      }

      EJBTimerStarter.addTimerManagerStarter(this, this.beanInfo);
   }

   public void start() {
      if (debugLogger.isDebugEnabled()) {
         debug("invoking start");
      }

      this.isInitialized = true;
      this.enableDisabledTimers();
   }

   public void undeploy() {
      if (debugLogger.isDebugEnabled()) {
         debug("Shutting down EJB timer service");
      }

      this.isInitialized = false;
      EJBTimerStarter.removeTimerManagerStarter(this);

      try {
         this.timerManager.waitForStop(0L);
      } catch (InterruptedException var2) {
      }

      if (this.persistentConnection != null) {
         this.persistentConnection.close();
      }

      Utils.unregister(this.beanInfo, TimerHelper.class);
   }

   private void initializePersistentStore() throws WLDeploymentException {
      try {
         String storeName = this.beanInfo.getTimerStoreName();
         if (storeName == null) {
            this.persistentStore = (PersistentStoreXA)PersistentStoreManager.getManager().getEjbTimerStore();
         } else {
            this.persistentStore = (PersistentStoreXA)PersistentStoreManager.getManager().getStoreByLogicalName(storeName);
         }

         String key = getStoreConnectionKey(this.beanInfo);
         this.persistentConnection = this.persistentStore.createConnection(key);
         this.gxa = this.persistentStore.getGXAResource();
      } catch (Exception var3) {
         if (debugLogger.isDebugEnabled()) {
            debug("Error initializing Timer store", var3);
         }

         throw new WLDeploymentException("Error initializing Timer store", var3);
      }
   }

   private static String getStoreConnectionKey(BeanInfo bi) {
      DeploymentInfo di = bi.getDeploymentInfo();
      return "weblogic.ejb.timer." + di.getApplicationId() + "." + di.getModuleId() + "." + bi.getEJBName();
   }

   public void cancelTimer(TimerImpl timer) {
      Timer t = timer.getTimer();
      if (t != null) {
         t.cancel();
      }

   }

   private void addTimerToMaps(TimerImpl timer) {
      Object pk = timer.getPK();
      Long id = timer.getID();
      Object previousTimer = null;
      synchronized(this.id2EJBTimerMap) {
         previousTimer = this.id2EJBTimerMap.put(id, timer);
      }

      synchronized(this.pk2EJBTimerMap) {
         Set timers = (Set)this.pk2EJBTimerMap.get(pk);
         if (timers == null) {
            timers = new HashSet();
            this.pk2EJBTimerMap.put(pk, timers);
         }

         ((Set)timers).add(timer);
      }

      if (previousTimer == null) {
         this.timerRtMBean.incrementActiveTimerCount();
      }

   }

   public void removeTimerFromMaps(TimerImpl timer) {
      Object pk = timer.getPK();
      Long id = timer.getID();
      synchronized(this.id2EJBTimerMap) {
         this.id2EJBTimerMap.remove(id);
      }

      synchronized(this.pk2EJBTimerMap) {
         Set timers = (Set)this.pk2EJBTimerMap.get(pk);
         if (timers == null) {
            return;
         }

         timers.remove(timer);
         if (timers.isEmpty()) {
            this.pk2EJBTimerMap.remove(pk);
         }
      }

      this.timerRtMBean.decrementActiveTimerCount();
   }

   private Long nextTimerId() {
      return new Long(this.timerIdCounter.incrementAndGet());
   }

   private static void debug(String s) {
      debugLogger.debug("[EJBTimerManager] " + s);
   }

   private static void debug(String s, Throwable th) {
      debugLogger.debug("[EJBTimerManager] " + s, th);
   }

   static {
      _WLDF$INST_FLD_EJB_Diagnostic_Timer_Manager_Around_High = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "EJB_Diagnostic_Timer_Manager_Around_High");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "EJBTimerManager.java", "weblogic.ejb.container.timer.EJBTimerManager", "scheduleTimer", "(Lweblogic/ejb/container/timer/TimerImpl;)V", 134, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Timer_Manager_Around_High};
      _WLDF$INST_JPFLD_1 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "EJBTimerManager.java", "weblogic.ejb.container.timer.EJBTimerManager", "createTimer", "(Ljava/lang/Object;Ljava/util/Date;JLjavax/ejb/TimerConfig;Lweblogic/ejb/WLTimerInfo;)Ljavax/ejb/Timer;", 202, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_1 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Timer_Manager_Around_High};
      _WLDF$INST_JPFLD_2 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "EJBTimerManager.java", "weblogic.ejb.container.timer.EJBTimerManager", "createTimer", "(Ljava/lang/Object;Ljava/util/Date;Ljavax/ejb/TimerConfig;Lweblogic/ejb/WLTimerInfo;)Ljavax/ejb/Timer;", 221, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_2 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Timer_Manager_Around_High};
      _WLDF$INST_JPFLD_3 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "EJBTimerManager.java", "weblogic.ejb.container.timer.EJBTimerManager", "createTimer", "(Ljava/lang/Object;JJLjavax/ejb/TimerConfig;Lweblogic/ejb/WLTimerInfo;)Ljavax/ejb/Timer;", 235, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_3 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Timer_Manager_Around_High};
      _WLDF$INST_JPFLD_4 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "EJBTimerManager.java", "weblogic.ejb.container.timer.EJBTimerManager", "createTimer", "(Ljava/lang/Object;JLjavax/ejb/TimerConfig;Lweblogic/ejb/WLTimerInfo;)Ljavax/ejb/Timer;", 240, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_4 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Timer_Manager_Around_High};
      _WLDF$INST_JPFLD_5 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "EJBTimerManager.java", "weblogic.ejb.container.timer.EJBTimerManager", "createTimer", "(Ljava/lang/Object;Ljavax/ejb/ScheduleExpression;Ljavax/ejb/TimerConfig;)Ljavax/ejb/Timer;", 245, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_5 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Timer_Manager_Around_High};
      _WLDF$INST_JPFLD_6 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "EJBTimerManager.java", "weblogic.ejb.container.timer.EJBTimerManager", "createTimer", "(Ljava/lang/Object;Ljavax/ejb/ScheduleExpression;Ljavax/ejb/TimerConfig;ZZLweblogic/ejb/container/internal/MethodDescriptor;)Ljavax/ejb/Timer;", 252, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_6 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Timer_Manager_Around_High};
      $assertionsDisabled = !EJBTimerManager.class.desiredAssertionStatus();
      debugLogger = EJBDebugService.timerLogger;
   }

   private static final class TimerExpirationOperation implements GXAOperation {
      private final TimerImpl timer;
      private final EJBTimerManager timerManager;
      private final PersistentStoreConnection pConn;
      private final ClassLoader moduleClassLoader;
      private GXATransaction gxaTransaction;
      private GXid xid;
      private PersistentHandle prepareHandle;
      private PersistentHandle newTimerHandle;
      private PersistentHandle oldTimerHandle;
      private TimerData oldTimerState;

      TimerExpirationOperation(TimerImpl timer, EJBTimerManager timerManager, PersistentStoreConnection pConn, ClassLoader moduleClassLoader) {
         this.timer = timer;
         this.timerManager = timerManager;
         this.pConn = pConn;
         this.oldTimerHandle = timer.getPersistentHandle();
         this.moduleClassLoader = moduleClassLoader;
      }

      public void recover(GXid xid, PersistentHandle prepareHandle, PersistentHandle oldTimerHandle, PersistentHandle newTimerHandle) {
         this.xid = xid;
         this.prepareHandle = prepareHandle;
         this.oldTimerHandle = oldTimerHandle;
         this.newTimerHandle = newTimerHandle;
      }

      public void onInitialize(GXATraceLogger traceLogger, GXATransaction gxaTransaction, GXAOperationWrapper operationWrapper) {
         this.gxaTransaction = gxaTransaction;
         this.xid = gxaTransaction.getGXid();
         this.timer.setXid(this.xid.getXAXid());
      }

      public boolean onPrepare(int pass, boolean isOnePhase) {
         if (!this.timer.__WL_isPersistent()) {
            return true;
         } else {
            if (this.timer.isIntervalTimer() && !this.timer.isCancelled()) {
               if (pass == 1) {
                  this.newTimerHandle = this.pConn.create(this.gxaTransaction.getStoreTransaction(), this.timer.getTimerData(), 0);
               }

               if (pass == 3) {
                  this.timer.setPersistentHandle(this.newTimerHandle);
               }
            }

            if (pass == 2 && !isOnePhase) {
               PrepareRecord prepareRecord = null;
               if (this.timer.isCancelled() && this.timer.isIntervalTimer()) {
                  prepareRecord = new PrepareRecord(this.timer.getID(), 4, this.xid);
               } else {
                  prepareRecord = new PrepareRecord(this.timer.getID(), 3, this.xid);
               }

               this.prepareHandle = this.pConn.create(this.gxaTransaction.getStoreTransaction(), prepareRecord, 0);
            }

            return true;
         }
      }

      public void onCommit(int pass) {
         if (!this.timer.__WL_isPersistent()) {
            if (pass == 1) {
               this.handleTimerExpiration();
            }

         } else {
            if (pass == 1) {
               this.pConn.delete(this.gxaTransaction.getStoreTransaction(), this.oldTimerHandle, 0);
            } else if (pass == 2 && this.prepareHandle != null) {
               this.pConn.delete(this.gxaTransaction.getStoreTransaction(), this.prepareHandle, 0);
            } else if (pass == 3) {
               this.handleTimerExpiration();
            }

         }
      }

      private void handleTimerExpiration() {
         this.timerManager.getTimerRuntimeMBean().incrementTimeoutCount();
         if (this.timer.isIntervalTimer() && !this.timer.isCancelled()) {
            this.handleTimeoutSuccess(this.timer);
         } else {
            if (this.timer.isCancelled()) {
               this.timerManager.getTimerRuntimeMBean().incrementCancelledTimerCount();
            }

            this.timer.setState(7);
            this.timerManager.removeTimerFromMaps(this.timer);
         }

      }

      public void onRollback(int pass) {
         if (!this.timer.__WL_isPersistent()) {
            if (pass == 1) {
               this.handleTimeoutFailure(this.timer, this.oldTimerState);
            }

         } else {
            if (pass == 1) {
               PersistentStoreTransaction tran = this.gxaTransaction.getStoreTransaction();
               if (this.newTimerHandle != null) {
                  this.pConn.delete(tran, this.newTimerHandle, 0);
               }

               if (this.timer.exists()) {
                  CompletionRequest cr = new CompletionRequest();
                  this.pConn.read(tran, this.oldTimerHandle, cr);
                  Thread currentThread = Thread.currentThread();
                  ClassLoader clSave = currentThread.getContextClassLoader();
                  currentThread.setContextClassLoader(this.moduleClassLoader);

                  try {
                     PersistentStoreRecord pr = (PersistentStoreRecord)cr.getResult();
                     this.oldTimerState = (TimerData)pr.getData();
                  } catch (Throwable var10) {
                     throw new AssertionError("Error reading from persistent store: " + var10);
                  } finally {
                     if (clSave != null) {
                        currentThread.setContextClassLoader(clSave);
                     }

                  }
               } else {
                  this.pConn.delete(tran, this.oldTimerHandle, 0);
               }
            } else if (pass == 2 && this.prepareHandle != null) {
               this.pConn.delete(this.gxaTransaction.getStoreTransaction(), this.prepareHandle, 0);
            } else if (pass == 3) {
               this.timer.setPersistentHandle(this.oldTimerHandle);
               this.handleTimeoutFailure(this.timer, this.oldTimerState);
            }

         }
      }

      public GXid getGXid() {
         return this.xid;
      }

      public String getDebugPrefix() {
         return "TimerExpiration";
      }

      private void handleTimeoutSuccess(final TimerImpl timer) {
         WorkManagerFactory.getInstance().getSystem().schedule(new Runnable() {
            public void run() {
               timer.handleTimeoutSuccess();
            }
         });
      }

      private void handleTimeoutFailure(final TimerImpl timer, final TimerData timerState) {
         WorkManagerFactory.getInstance().getSystem().schedule(new Runnable() {
            public void run() {
               timer.handleTimeoutFailure(timerState);
            }
         });
      }
   }

   private static final class TimerCancellationOperation implements GXAOperation {
      private final TimerImpl timer;
      private final EJBTimerManager timerManager;
      private final PersistentStoreConnection pConn;
      private GXATransaction gxaTransaction;
      private GXid xid;
      private PersistentHandle prepareHandle;

      TimerCancellationOperation(TimerImpl timer, EJBTimerManager timerManager, PersistentStoreConnection pConn) {
         this.timer = timer;
         this.timerManager = timerManager;
         this.pConn = pConn;
      }

      public void recover(GXid xid, PersistentHandle prepareHandle) {
         this.xid = xid;
         this.prepareHandle = prepareHandle;
      }

      public void onInitialize(GXATraceLogger traceLogger, GXATransaction gxaTransaction, GXAOperationWrapper operationWrapper) {
         this.gxaTransaction = gxaTransaction;
         this.xid = gxaTransaction.getGXid();
         this.timer.setXid(this.xid.getXAXid());
      }

      public boolean onPrepare(int pass, boolean isOnePhase) {
         if (!this.timer.__WL_isPersistent()) {
            return true;
         } else {
            if (pass == 2 && !isOnePhase) {
               PrepareRecord pr = new PrepareRecord(this.timer.getID(), 2, this.xid);
               this.prepareHandle = this.pConn.create(this.gxaTransaction.getStoreTransaction(), pr, 0);
            }

            return true;
         }
      }

      public void onCommit(int pass) {
         if (!this.timer.__WL_isPersistent()) {
            if (pass == 1) {
               this.timerManager.getTimerRuntimeMBean().incrementCancelledTimerCount();
               this.timer.finalizeCancel();
            }

         } else {
            if (pass == 1) {
               this.pConn.delete(this.gxaTransaction.getStoreTransaction(), this.timer.getPersistentHandle(), 0);
            } else if (pass == 2 && this.prepareHandle != null) {
               this.pConn.delete(this.gxaTransaction.getStoreTransaction(), this.prepareHandle, 0);
            } else if (pass == 3) {
               this.timerManager.getTimerRuntimeMBean().incrementCancelledTimerCount();
               this.timer.finalizeCancel();
            }

         }
      }

      public void onRollback(int pass) {
         if (!this.timer.__WL_isPersistent()) {
            if (pass == 1) {
               this.timer.undoCancel();
            }

         } else {
            if (pass == 2 && this.prepareHandle != null) {
               this.pConn.delete(this.gxaTransaction.getStoreTransaction(), this.prepareHandle, 0);
            } else if (pass == 3) {
               this.timer.undoCancel();
            }

         }
      }

      public GXid getGXid() {
         return this.xid;
      }

      public String getDebugPrefix() {
         return "TimerCancellation";
      }
   }

   private static final class TimerCreationOperation implements GXAOperation {
      private final TimerImpl timer;
      private final PersistentStoreConnection pConn;
      private GXATransaction gxaTransaction;
      private GXid xid;
      private PersistentHandle prepareHandle;

      TimerCreationOperation(TimerImpl timer, PersistentStoreConnection pConn) {
         this.timer = timer;
         this.pConn = pConn;
      }

      public void recover(GXid xid, PersistentHandle prepareHandle) {
         this.xid = xid;
         this.prepareHandle = prepareHandle;
      }

      public void onInitialize(GXATraceLogger traceLogger, GXATransaction gxaTransaction, GXAOperationWrapper operationWrapper) {
         this.gxaTransaction = gxaTransaction;
         this.xid = gxaTransaction.getGXid();
         this.timer.setXid(this.xid.getXAXid());
      }

      public boolean onPrepare(int pass, boolean isOnePhase) {
         if (!this.timer.__WL_isPersistent()) {
            return true;
         } else {
            if (pass == 1) {
               PersistentHandle timerHandle = this.pConn.create(this.gxaTransaction.getStoreTransaction(), this.timer.getTimerData(), 0);
               this.timer.setPersistentHandle(timerHandle);
            } else if (pass == 2 && !isOnePhase) {
               PrepareRecord pr = new PrepareRecord(this.timer.getID(), 1, this.xid);
               this.prepareHandle = this.pConn.create(this.gxaTransaction.getStoreTransaction(), pr, 0);
            }

            return true;
         }
      }

      public void onCommit(int pass) {
         if (!this.timer.__WL_isPersistent()) {
            if (pass == 1) {
               this.timer.finalizeCreate();
            }

         } else {
            if (pass == 2 && this.prepareHandle != null) {
               this.pConn.delete(this.gxaTransaction.getStoreTransaction(), this.prepareHandle, 0);
            } else if (pass == 3) {
               this.timer.finalizeCreate();
            }

         }
      }

      public void onRollback(int pass) {
         if (!this.timer.__WL_isPersistent()) {
            if (pass == 1) {
               this.timer.undoCreate();
            }

         } else {
            if (pass == 1 && this.timer.getPersistentHandle() != null) {
               this.pConn.delete(this.gxaTransaction.getStoreTransaction(), this.timer.getPersistentHandle(), 0);
            } else if (pass == 2 && this.prepareHandle != null) {
               this.pConn.delete(this.gxaTransaction.getStoreTransaction(), this.prepareHandle, 0);
            } else if (pass == 3) {
               this.timer.undoCreate();
            }

         }
      }

      public GXid getGXid() {
         return this.xid;
      }

      public String getDebugPrefix() {
         return "TimerCreation";
      }
   }

   private static final class TimerHelperImpl implements TimerHelper {
      private final EJBTimerManager timerManager;

      TimerHelperImpl(EJBTimerManager timerManager) {
         this.timerManager = timerManager;
      }

      public javax.ejb.Timer getTimer(Long timerID) {
         return this.timerManager.getTimer(timerID);
      }
   }
}
