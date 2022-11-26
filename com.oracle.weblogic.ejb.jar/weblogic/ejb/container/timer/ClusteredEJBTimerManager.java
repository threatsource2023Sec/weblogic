package weblogic.ejb.container.timer;

import java.io.Serializable;
import java.security.AccessController;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import javax.ejb.ScheduleExpression;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TransactionAttributeType;
import weblogic.application.ApplicationAccess;
import weblogic.application.ApplicationContextInternal;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.ejb.WLTimerInfo;
import weblogic.ejb.container.dd.xml.DDUtils;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.BeanManager;
import weblogic.ejb.container.interfaces.TimerHandler;
import weblogic.ejb.container.interfaces.TimerManager;
import weblogic.ejb.container.internal.MethodDescriptor;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.EJBTimerRuntimeMBean;
import weblogic.scheduler.TimerAlreadyExistsException;
import weblogic.scheduler.ejb.ClusteredTimerManager;
import weblogic.scheduler.ejb.ClusteredTimerManagerFactory;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.classloaders.GenericClassLoader;

final class ClusteredEJBTimerManager implements TimerManager {
   private static final AuthenticatedSubject KERNEL_ID;
   private static final String UNIQUE_KEY_FIELD_DELIMITER = "|";
   private ClusteredTimerManager jscheduler;
   private final BeanInfo bi;
   private final BeanManager beanManager;
   private boolean isTransactional;
   private final Map isTransactionalMap = new HashMap();
   private final String domainName;
   private final String clusterOrPartitionName;
   static final long serialVersionUID = 6782911992374001910L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.ejb.container.timer.ClusteredEJBTimerManager");
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

   ClusteredEJBTimerManager(BeanManager beanManager) {
      this.beanManager = beanManager;
      this.bi = beanManager.getBeanInfo();
      RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(KERNEL_ID);
      this.domainName = runtimeAccess.getDomainName();
      this.clusterOrPartitionName = ClusteredTimerManagerFactory.getInstance().getClusterOrPartitionName(this.bi.getCIC());
   }

   public void setup(EJBTimerRuntimeMBean rtMBean) {
      if (this.bi.getEjbTimeoutMethodDescriptor() != null) {
         MethodDescriptor md = this.bi.getEjbTimeoutMethodDescriptor();
         this.isTransactional = md.requiresTransaction();
         md.getTransactionPolicy().setTxAttribute(TransactionAttributeType.SUPPORTS);
      }

      Iterator var4 = this.bi.getAutomaticTimerMDs().values().iterator();

      while(var4.hasNext()) {
         MethodDescriptor md = (MethodDescriptor)var4.next();
         this.isTransactionalMap.put(md, new Boolean(md.requiresTransaction()));
         md.getTransactionPolicy().setTxAttribute(TransactionAttributeType.SUPPORTS);
      }

      Utils.register(this.bi, TimerHandler.class, new TimerHandlerImpl(this.beanManager, this.bi));
   }

   public Timer createTimer(Object pk, Date initialExpiration, long intervalDuration, TimerConfig conf, WLTimerInfo var6) {
      LocalHolder var9;
      if ((var9 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         InstrumentationSupport.preProcess(var9);
      }

      TimerWrapper var10000;
      try {
         Serializable info = conf == null ? null : conf.getInfo();
         ClusteredTimerImpl timer = new ClusteredTimerImpl(pk, info, this.isTransactional, this.bi, this.bi.getEjbTimeoutMethodDescriptor());
         timer.setTimer(this.getEJBTimerManager().schedule(timer, initialExpiration, intervalDuration));
         var10000 = new TimerWrapper(timer);
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

   public Timer createTimer(Object pk, Date expiration, TimerConfig conf, WLTimerInfo var4) {
      LocalHolder var7;
      if ((var7 = LocalHolder.getInstance(_WLDF$INST_JPFLD_1, _WLDF$INST_JPFLD_JPMONS_1)) != null) {
         InstrumentationSupport.preProcess(var7);
      }

      TimerWrapper var10000;
      try {
         Serializable info = conf == null ? null : conf.getInfo();
         ClusteredTimerImpl timer = new ClusteredTimerImpl(pk, info, this.isTransactional, this.bi, this.bi.getEjbTimeoutMethodDescriptor());
         timer.setTimer(this.getEJBTimerManager().schedule(timer, expiration));
         var10000 = new TimerWrapper(timer);
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

   public Timer createTimer(Object pk, long initialDuration, long intervalDuration, TimerConfig conf, WLTimerInfo var7) {
      LocalHolder var9;
      if ((var9 = LocalHolder.getInstance(_WLDF$INST_JPFLD_2, _WLDF$INST_JPFLD_JPMONS_2)) != null) {
         InstrumentationSupport.preProcess(var9);
      }

      Timer var10000;
      try {
         Date initialExpiration = new Date(System.currentTimeMillis() + initialDuration);
         var10000 = this.createTimer(pk, initialExpiration, intervalDuration, conf, (WLTimerInfo)null);
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

   public Timer createTimer(Object pk, long duration, TimerConfig conf, WLTimerInfo var5) {
      LocalHolder var7;
      if ((var7 = LocalHolder.getInstance(_WLDF$INST_JPFLD_3, _WLDF$INST_JPFLD_JPMONS_3)) != null) {
         InstrumentationSupport.preProcess(var7);
      }

      Timer var10000;
      try {
         Date expiration = new Date(System.currentTimeMillis() + duration);
         var10000 = this.createTimer(pk, expiration, conf, (WLTimerInfo)null);
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

   public Collection getTimers(Object pk) {
      return this.getTimers(pk, true);
   }

   public Collection getAllTimers() {
      if (this.jscheduler == null) {
         throw new IllegalStateException("EJBTimerManager is not available yet");
      } else {
         weblogic.timers.Timer[] timers = this.getEJBTimerManager().getTimers();
         Collection wrappedTimers = new HashSet(timers.length);
         weblogic.timers.Timer[] var3 = timers;
         int var4 = timers.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            weblogic.timers.Timer timer = var3[var5];
            ClusteredTimerImpl cti = (ClusteredTimerImpl)timer.getListener();
            cti.setTimer(timer);
            wrappedTimers.add(new TimerWrapper(cti));
         }

         return wrappedTimers;
      }
   }

   private Collection getTimers(Object pk, boolean wrap) {
      if (this.jscheduler == null) {
         throw new IllegalStateException("EJBTimerManager is not available yet");
      } else {
         weblogic.timers.Timer[] timers = null;
         if (this.bi.isEntityBean()) {
            timers = this.getEJBTimerManager().getTimers(Integer.toString(pk.hashCode()));
         } else {
            timers = this.getEJBTimerManager().getTimers();
         }

         Collection matches = new HashSet(timers.length);
         weblogic.timers.Timer[] var5 = timers;
         int var6 = timers.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            weblogic.timers.Timer timer = var5[var7];
            ClusteredTimerImpl cti = (ClusteredTimerImpl)timer.getListener();
            if (cti != null && (!this.bi.isEntityBean() || pk.equals(cti.getPrimaryKey()))) {
               cti.setTimer(timer);
               matches.add(wrap ? new TimerWrapper(cti) : cti);
            }
         }

         return matches;
      }
   }

   public void removeTimersForPK(Object pk) {
      Iterator var2 = this.getTimers(pk, false).iterator();

      while(var2.hasNext()) {
         Timer timer = (Timer)var2.next();
         timer.cancel();
      }

   }

   public static void removeAllTimers(BeanInfo bi) {
      String appId = bi.getDeploymentInfo().getApplicationId();
      ApplicationContextInternal appCtx = ApplicationAccess.getApplicationAccess().getApplicationContext(appId);
      DomainMBean domain = appCtx.getProposedDomain();
      if (domain == null) {
         getEJBTimerManager(bi).cancelAllTimers();
      } else if (domain != null && domain.lookupAppDeployment(appId) == null) {
         getEJBTimerManager(bi).cancelAllTimers();
      }

   }

   public void perhapsStart() {
      this.getEJBTimerManager();
      EJBTimerStarter.addTimerManagerStarter(this, this.bi);
   }

   public void undeploy() {
      EJBTimerStarter.removeTimerManagerStarter(this);
      Utils.unregister(this.bi, TimerHandler.class);
      if (this.jscheduler != null) {
         this.jscheduler.stop();
      }

   }

   public void start() {
      TimerManagerHelper.initializeAutoCreatedTimers(this, this.bi.getAutomaticTimerMDs(), (Map)null, this.isTransactionalMap);
   }

   private ClusteredTimerManager getEJBTimerManager() {
      if (this.jscheduler == null) {
         this.jscheduler = getEJBTimerManager(this.bi);
      }

      return this.jscheduler;
   }

   private static ClusteredTimerManager getEJBTimerManager(BeanInfo bi) {
      GenericClassLoader gcl = (GenericClassLoader)bi.getModuleClassLoader();
      String annotation = gcl.getAnnotation().getAnnotationString();
      return ClusteredTimerManagerFactory.getInstance().create(bi.getFullyQualifiedName(), annotation, bi.getDispatchPolicy());
   }

   public void enableDisabledTimers() {
      throw new UnsupportedOperationException("Clustered EJB Timer implementation does not support disabling of Timers");
   }

   public Timer createTimer(Object pk, ScheduleExpression schedule, TimerConfig tc) {
      LocalHolder var4;
      if ((var4 = LocalHolder.getInstance(_WLDF$INST_JPFLD_4, _WLDF$INST_JPFLD_JPMONS_4)) != null) {
         InstrumentationSupport.preProcess(var4);
      }

      Timer var10000;
      try {
         var10000 = this.createTimer(pk, schedule, tc, this.isTransactional, false, this.bi.getEjbTimeoutMethodDescriptor());
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

   public Timer createTimer(Object pk, ScheduleExpression schedule, TimerConfig timerConfig, boolean isTransactional, boolean isAutoCreated, MethodDescriptor callbackMethodDesc) {
      LocalHolder var9;
      if ((var9 = LocalHolder.getInstance(_WLDF$INST_JPFLD_5, _WLDF$INST_JPFLD_JPMONS_5)) != null) {
         InstrumentationSupport.preProcess(var9);
      }

      TimerWrapper var10000;
      try {
         if (timerConfig != null && !timerConfig.isPersistent()) {
            throw new UnsupportedOperationException("Clustered EJB Timer implementation does not support non-persistent Timers");
         }

         String methodSig = DDUtils.getMethodSignature(callbackMethodDesc.getMethod());
         ClusteredTimerImpl timer = new ClusteredTimerImpl(pk, schedule, isTransactional, this.bi, timerConfig, isAutoCreated, methodSig, this.calculateClusterTimerUniqueKey(methodSig));
         this.scheduleTimer(timer);
         var10000 = new TimerWrapper(timer);
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

   private void scheduleTimer(ClusteredTimerImpl clusteredTimerImpl) {
      if (clusteredTimerImpl.isAutoCreated()) {
         weblogic.timers.Timer timer = null;

         try {
            timer = this.getEJBTimerManager().schedule(clusteredTimerImpl, Utils.asWeblogicScheduleExpression(clusteredTimerImpl.getSchedule()), clusteredTimerImpl.getUniqueKey());
         } catch (TimerAlreadyExistsException var4) {
            timer = this.getEJBTimerManager().getTimerByUserKey(clusteredTimerImpl.getUniqueKey());
         }

         clusteredTimerImpl.setTimer(timer);
      } else {
         clusteredTimerImpl.setTimer(this.getEJBTimerManager().schedule(clusteredTimerImpl, Utils.asWeblogicScheduleExpression(clusteredTimerImpl.getSchedule())));
      }

   }

   private String calculateClusterTimerUniqueKey(String methodSig) {
      String[] segments = new String[]{this.domainName, this.clusterOrPartitionName, this.bi.getDeploymentInfo().getApplicationId(), this.bi.getDeploymentInfo().getModuleId(), this.bi.getEJBName(), methodSig};
      StringBuilder result = new StringBuilder();
      String[] var4 = segments;
      int var5 = segments.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         String each = var4[var6];
         result.append(each).append("|");
      }

      result.deleteCharAt(result.length() - 1);
      return result.toString();
   }

   static {
      _WLDF$INST_FLD_EJB_Diagnostic_Timer_Manager_Around_High = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "EJB_Diagnostic_Timer_Manager_Around_High");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "ClusteredEJBTimerManager.java", "weblogic.ejb.container.timer.ClusteredEJBTimerManager", "createTimer", "(Ljava/lang/Object;Ljava/util/Date;JLjavax/ejb/TimerConfig;Lweblogic/ejb/WLTimerInfo;)Ljavax/ejb/Timer;", 94, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Timer_Manager_Around_High};
      _WLDF$INST_JPFLD_1 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "ClusteredEJBTimerManager.java", "weblogic.ejb.container.timer.ClusteredEJBTimerManager", "createTimer", "(Ljava/lang/Object;Ljava/util/Date;Ljavax/ejb/TimerConfig;Lweblogic/ejb/WLTimerInfo;)Ljavax/ejb/Timer;", 104, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_1 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Timer_Manager_Around_High};
      _WLDF$INST_JPFLD_2 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "ClusteredEJBTimerManager.java", "weblogic.ejb.container.timer.ClusteredEJBTimerManager", "createTimer", "(Ljava/lang/Object;JJLjavax/ejb/TimerConfig;Lweblogic/ejb/WLTimerInfo;)Ljavax/ejb/Timer;", 114, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_2 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Timer_Manager_Around_High};
      _WLDF$INST_JPFLD_3 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "ClusteredEJBTimerManager.java", "weblogic.ejb.container.timer.ClusteredEJBTimerManager", "createTimer", "(Ljava/lang/Object;JLjavax/ejb/TimerConfig;Lweblogic/ejb/WLTimerInfo;)Ljavax/ejb/Timer;", 119, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_3 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Timer_Manager_Around_High};
      _WLDF$INST_JPFLD_4 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "ClusteredEJBTimerManager.java", "weblogic.ejb.container.timer.ClusteredEJBTimerManager", "createTimer", "(Ljava/lang/Object;Ljavax/ejb/ScheduleExpression;Ljavax/ejb/TimerConfig;)Ljavax/ejb/Timer;", 222, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_4 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Timer_Manager_Around_High};
      _WLDF$INST_JPFLD_5 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "ClusteredEJBTimerManager.java", "weblogic.ejb.container.timer.ClusteredEJBTimerManager", "createTimer", "(Ljava/lang/Object;Ljavax/ejb/ScheduleExpression;Ljavax/ejb/TimerConfig;ZZLweblogic/ejb/container/internal/MethodDescriptor;)Ljavax/ejb/Timer;", 228, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_5 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Timer_Manager_Around_High};
      KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   }
}
