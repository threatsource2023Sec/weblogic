package weblogic.ejb.container.monitoring;

import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.TimerManager;
import weblogic.ejb.spi.EJBRuntimeHolder;
import weblogic.management.ManagementException;
import weblogic.management.runtime.EJBPoolRuntimeMBean;
import weblogic.management.runtime.EJBTimerRuntimeMBean;
import weblogic.management.runtime.StatelessEJBRuntimeMBean;

public final class StatelessEJBRuntimeMBeanImpl extends EJBRuntimeMBeanImpl implements StatelessEJBRuntimeMBean {
   private final EJBPoolRuntimeMBean poolRtMBean;
   private final EJBTimerRuntimeMBean timerRtMBean;

   public StatelessEJBRuntimeMBeanImpl(BeanInfo bi, EJBRuntimeHolder component, TimerManager timerManager) throws ManagementException {
      super(bi.getEJBName(), bi.getEJBName(), component);
      this.poolRtMBean = new EJBPoolRuntimeMBeanImpl(this.name, bi, this);
      this.timerRtMBean = timerManager != null ? new EJBTimerRuntimeMBeanImpl(this.name, bi, this, timerManager) : null;
   }

   public EJBPoolRuntimeMBean getPoolRuntime() {
      return this.poolRtMBean;
   }

   public EJBTimerRuntimeMBean getTimerRuntime() {
      return this.timerRtMBean;
   }
}
