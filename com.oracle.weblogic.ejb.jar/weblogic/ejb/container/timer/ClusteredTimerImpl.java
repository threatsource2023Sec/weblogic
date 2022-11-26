package weblogic.ejb.container.timer;

import java.io.Serializable;
import java.util.Date;
import javax.ejb.ScheduleExpression;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerHandle;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.dd.xml.DDUtils;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.DeploymentInfo;
import weblogic.ejb.container.interfaces.TimerHandler;
import weblogic.ejb.container.interfaces.TimerIntf;
import weblogic.ejb.container.internal.MethodDescriptor;
import weblogic.ejb.container.internal.TransactionService;
import weblogic.scheduler.ejb.ClusteredTimerListener;

public final class ClusteredTimerImpl implements Timer, TimerIntf, ClusteredTimerListener {
   private static final long serialVersionUID = 6677202392738444860L;
   private final Object pk;
   private final Serializable info;
   private final boolean isTransactional;
   private final String applicationId;
   private final String moduleId;
   private final String ejbName;
   private final boolean isNonPersistent;
   private final ScheduleExpression schedule;
   private final boolean isAutoCreated;
   private final String methodSignature;
   private final String uniqueKey;
   private int transactionTimeout;
   private transient weblogic.scheduler.Timer timer;

   ClusteredTimerImpl(Object pk, Serializable info, boolean isTransactional, BeanInfo bi, MethodDescriptor md) {
      this(pk, (ScheduleExpression)null, isTransactional, bi, new TimerConfig(info, true), false, DDUtils.getMethodSignature(md.getMethod()), (String)null);
   }

   ClusteredTimerImpl(Object pk, ScheduleExpression schedule, boolean isTransactional, BeanInfo bi, TimerConfig timerConfig, boolean isAutoCreated, String methodSignature, String uniqueKey) {
      this.pk = pk;
      this.info = timerConfig == null ? null : timerConfig.getInfo();
      this.isNonPersistent = timerConfig == null ? false : !timerConfig.isPersistent();
      this.schedule = schedule;
      this.isTransactional = isTransactional;
      this.isAutoCreated = isAutoCreated;
      this.methodSignature = methodSignature;
      this.uniqueKey = uniqueKey;
      DeploymentInfo di = bi.getDeploymentInfo();
      this.applicationId = di.getApplicationId();
      this.moduleId = di.getModuleId();
      this.ejbName = bi.getEJBName();
      this.transactionTimeout = bi.getTransactionTimeoutSeconds();
   }

   public void setTimer(weblogic.timers.Timer timer) {
      this.timer = (weblogic.scheduler.Timer)timer;
   }

   public void timerExpired(weblogic.timers.Timer timer) {
      this.setTimer(timer);
      TimerHandler th = (TimerHandler)Utils.getRegisteredValue(this.applicationId, this.moduleId, this.ejbName, TimerHandler.class);
      if (th == null) {
         EJBLogger.logClusteredTimerFailedToLookupTimerHandler(this.ejbName, this.moduleId, this.applicationId);
         if (this.isTransactional) {
            try {
               TransactionService.getTransaction().setRollbackOnly();
            } catch (Exception var4) {
               EJBLogger.logErrorMarkingRollback(var4);
            }
         }
      } else {
         th.executeTimer(this);
      }

   }

   public void cancel() {
      this.timer.cancel();
   }

   public TimerHandle getHandle() {
      return new ClusteredTimerHandleImpl(this.timer);
   }

   public Serializable getInfo() {
      return this.info;
   }

   public Date getNextTimeout() {
      return new Date(this.timer.getTimeout());
   }

   public long getTimeRemaining() {
      return this.timer.getTimeout() - System.currentTimeMillis();
   }

   public Object getPrimaryKey() {
      return this.pk;
   }

   public String getGroupName() {
      return this.pk == null ? null : Integer.toString(this.pk.hashCode());
   }

   public boolean isTransactional() {
      return this.isTransactional;
   }

   public boolean exists() {
      return !this.timer.isCancelled();
   }

   public boolean isCalendarTimer() {
      return this.schedule != null;
   }

   public boolean isPersistent() {
      return !this.isNonPersistent;
   }

   public ScheduleExpression getSchedule() {
      return this.schedule;
   }

   public boolean isAutoCreated() {
      return this.isAutoCreated;
   }

   public String getCallbackMethodSignature() {
      return this.methodSignature;
   }

   public String getUniqueKey() {
      return this.uniqueKey;
   }

   public int getTransactionTimeoutSeconds() {
      return this.transactionTimeout;
   }

   public String getEjbName() {
      return this.ejbName;
   }
}
