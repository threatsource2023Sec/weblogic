package weblogic.ejb.container.monitoring;

import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.SingletonLockStatisticsProvider;
import weblogic.ejb.container.interfaces.TimerManager;
import weblogic.ejb.spi.EJBRuntimeHolder;
import weblogic.management.ManagementException;
import weblogic.management.runtime.EJBTimerRuntimeMBean;
import weblogic.management.runtime.SingletonEJBRuntimeMBean;

public final class SingletonEJBRuntimeMBeanImpl extends EJBRuntimeMBeanImpl implements SingletonEJBRuntimeMBean {
   private final EJBTimerRuntimeMBean timerRtMBean;
   private final SingletonLockStatisticsProvider lockStatsProvider;

   public SingletonEJBRuntimeMBeanImpl(BeanInfo bi, EJBRuntimeHolder component, TimerManager timerManager, SingletonLockStatisticsProvider lockStatsProvider) throws ManagementException {
      super(bi.getEJBName(), bi.getEJBName(), component);
      this.lockStatsProvider = lockStatsProvider;
      this.timerRtMBean = timerManager != null ? new EJBTimerRuntimeMBeanImpl(this.name, bi, this, timerManager) : null;
   }

   public EJBTimerRuntimeMBean getTimerRuntime() {
      return this.timerRtMBean;
   }

   public long getReadLockTotalCount() {
      return this.lockStatsProvider == null ? -1L : this.lockStatsProvider.getReadLockTotalCount();
   }

   public long getWriteLockTotalCount() {
      return this.lockStatsProvider == null ? -1L : this.lockStatsProvider.getWriteLockTotalCount();
   }

   public int getReadLockTimeoutTotalCount() {
      return this.lockStatsProvider == null ? -1 : this.lockStatsProvider.getReadLockTimeoutTotalCount();
   }

   public int getWriteLockTimeoutTotalCount() {
      return this.lockStatsProvider == null ? -1 : this.lockStatsProvider.getWriteLockTimeoutTotalCount();
   }

   public int getWaiterCurrentCount() {
      return this.lockStatsProvider == null ? -1 : this.lockStatsProvider.getWaiterCurrentCount();
   }
}
