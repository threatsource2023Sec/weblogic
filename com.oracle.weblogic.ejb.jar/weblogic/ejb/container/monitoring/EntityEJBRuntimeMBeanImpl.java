package weblogic.ejb.container.monitoring;

import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.TimerManager;
import weblogic.ejb.spi.EJBRuntimeHolder;
import weblogic.management.ManagementException;
import weblogic.management.runtime.EJBCacheRuntimeMBean;
import weblogic.management.runtime.EJBLockingRuntimeMBean;
import weblogic.management.runtime.EJBPoolRuntimeMBean;
import weblogic.management.runtime.EJBTimerRuntimeMBean;
import weblogic.management.runtime.EntityEJBRuntimeMBean;
import weblogic.management.runtime.QueryCacheRuntimeMBean;

public final class EntityEJBRuntimeMBeanImpl extends EJBRuntimeMBeanImpl implements EntityEJBRuntimeMBean {
   private final EJBPoolRuntimeMBean poolRtMBean;
   private final EJBCacheRuntimeMBean cacheRtMBean;
   private final EJBLockingRuntimeMBean lockingRtMBean;
   private final EJBTimerRuntimeMBean timerRtMBean;
   private final QueryCacheRuntimeMBean queryCacheRtMBean;

   public EntityEJBRuntimeMBeanImpl(BeanInfo bi, EJBRuntimeHolder component, boolean usesExclusiveConcurrency, TimerManager timerManager) throws ManagementException {
      super(bi.getEJBName(), bi.getEJBName(), component);
      this.poolRtMBean = new EJBPoolRuntimeMBeanImpl(this.name, bi, this);
      this.cacheRtMBean = new EJBCacheRuntimeMBeanImpl(this.name, bi, this);
      this.lockingRtMBean = usesExclusiveConcurrency ? new EJBLockingRuntimeMBeanImpl(this.name, this) : null;
      this.timerRtMBean = timerManager != null ? new EJBTimerRuntimeMBeanImpl(this.name, bi, this, timerManager) : null;
      this.queryCacheRtMBean = new QueryCacheRuntimeMBeanImpl(this.name, this);
   }

   public EJBPoolRuntimeMBean getPoolRuntime() {
      return this.poolRtMBean;
   }

   public EJBCacheRuntimeMBean getCacheRuntime() {
      return this.cacheRtMBean;
   }

   public EJBLockingRuntimeMBean getLockingRuntime() {
      return this.lockingRtMBean;
   }

   public EJBTimerRuntimeMBean getTimerRuntime() {
      return this.timerRtMBean;
   }

   public QueryCacheRuntimeMBean getQueryCacheRuntime() {
      return this.queryCacheRtMBean;
   }
}
